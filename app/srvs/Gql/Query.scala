package srvs.Gql
import sangria.macros.derive._
import domains.Tag
import domains.Task
import domains.Title
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcBackend
import scalaz.Scalaz._

class Query(
    db: JdbcBackend#DatabaseDef
) {
  val taskRepo = Task.tasks
  val tagRepo = Tag.tags
  val titleRepo = Title.titles

  @GraphQLField
  def tasks(titleId: String) = {
    taskRepo.filter(_.titleId === titleId).result |> db.run
  }

  @GraphQLField
  def titles() = { titleRepo.result |> db.run }

  @GraphQLField
  def title(id: String) = {
    titleRepo.filter(_.id === id).result.headOption |> db.run
  }

  @GraphQLField
  def tags() = { tagRepo.result |> db.run }
}
