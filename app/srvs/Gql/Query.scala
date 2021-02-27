package srvs.Gql
import sangria.macros.derive._
import domains.Tag
import domains.Task
import domains.Title
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcBackend

class Query(
    db: JdbcBackend#DatabaseDef
) {
  val taskRepo = Task.tasks
  val tagRepo = Tag.tags
  val titleRepo = Title.titles

  @GraphQLField
  def tasks(titleId: String) = {
    db.run(taskRepo.filter(_.titleId === titleId).result)
  }

  @GraphQLField
  def titles() = { db.run(titleRepo.result) }

  @GraphQLField
  def title(id: String) = {
    db.run(titleRepo.filter(_.id === id).result.headOption)
  }

  @GraphQLField
  def tags() = { db.run(tagRepo.result) }
}
