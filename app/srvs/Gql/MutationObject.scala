package srvs.Gql
import sangria.schema._
import sangria.macros.derive._
import sangria.marshalling.FromInput
import sangria.marshalling.playJson._
import play.api.libs.json._
import domains.Tag.{Tag => TagT}
import domains.Task._
import domains.Title._
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcBackend
import scala.concurrent.Future

object MutationObject {
  implicit val TaskType = deriveObjectType[Ctx, Task]()
  implicit val taskFormat = Json.format[Task]
  implicit val TaskInputType =
    deriveInputObjectType[Task](InputObjectTypeName("TaskInput"))

  implicit val TagType = deriveObjectType[Ctx, TagT]()
  implicit val tagFormat = Json.format[TagT]
  implicit val TagInputType =
    deriveInputObjectType[TagT](InputObjectTypeName("TagInput"))

  implicit val TitleType = deriveObjectType[Ctx, Title]()
  implicit val titleFormat = Json.format[Title]
  implicit val TitleInputType =
    deriveInputObjectType[Title](InputObjectTypeName("TitleInput"))
  val MutationType = deriveContextObjectType[Ctx, Mutation, Unit](_.mutation)
}
