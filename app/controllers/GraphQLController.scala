package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db.slick._
import scala.concurrent.ExecutionContext
import scala.util._
import play.api.libs.json._
import scala.concurrent.Future

import sangria.renderer._
import sangria.macros._
import sangria.ast._
import sangria.execution._
import sangria.parser._
import sangria.schema._
import sangria.marshalling.{ResultMarshaller}
import sangria.marshalling.playJson._
import domains.Tag._
import domains.Task._
import domains.Title._
import srvs.Gql
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcProfile
import scalaz.Scalaz._

@Singleton
class GraphQLController @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider,
    cc: ControllerComponents
)(implicit
    ec: ExecutionContext
) extends AbstractController(cc)
    with HasDatabaseConfigProvider[JdbcProfile] {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(SchemaRenderer.renderSchema(Schema(Gql.MutationObject.MutationType)))
  }

  def gql() = Action.async(parse.json) { implicit request =>
    QueryParser.parse((request.body \ "query").as[String]) match {
      case Success(query) =>
        Executor
          .execute(
            Schema(
              Gql.QueryObject.QueryType,
              Some(Gql.MutationObject.MutationType)
            ),
            query,
            Gql.Ctx(query = new Gql.Query(db), mutation = new Gql.Mutation(db)),
            variables = variablesToJson(request) | Json.obj(),
            operationName = (request.body \ "operationName").asOpt[String]
          )
          .map(Ok(_))
      case Failure(error: SyntaxError) => Future(BadRequestWithError(error))
      case Failure(error)              => throw error
    }
  // Future(
  //   Ok(SchemaRenderer.renderSchema(Schema(Gql.MutationObject.MutationType)))
  // )
  }

  def BadRequestWithError(error: SyntaxError) = BadRequest(
    Json.obj(
      "syntaxError" -> error.getMessage,
      "locations" -> Json.arr(
        Json.obj(
          "line" -> error.originalError.position.line,
          "column" -> error.originalError.position.column
        )
      )
    )
  )

  private def parseVariables(variables: String) =
    if (variables.trim == "" || variables.trim == "null") Json.obj()
    else Json.parse(variables).as[JsObject]

  val variablesToJson = (request: Request[JsValue]) =>
    (request.body \ "variables").toOption >>= {
      case JsString(vars) => Some(parseVariables(vars))
      case obj: JsObject  => Some(obj)
      case _              => None
    }
}
