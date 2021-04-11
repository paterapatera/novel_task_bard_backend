package srvs.Gql
import sangria.macros.derive._
import domains.Tag
import domains.Task
import domains.Title
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcBackend
import scala.concurrent.ExecutionContext
import scalaz.Scalaz._

class Mutation(
    db: JdbcBackend#DatabaseDef
)(implicit
    ec: ExecutionContext
) {
  val taskRepo = Task.tasks
  val tagRepo = Tag.tags
  val titleRepo = Title.titles

  @GraphQLField
  def saveTask(task: Task.Task) = {
    task |> taskRepo.insertOrUpdate |> db.run
    task
  }

  @GraphQLField
  def saveTasks(titleId: String, tasks: Seq[Task.Task]) = {
    val deleteProc = taskRepo.filter(_.titleId === titleId).delete |> db.run
    val insertProc = (taskRepo ++= tasks) |> db.run
    deleteProc >>= (_ => insertProc)
    titleId
  }

  @GraphQLField
  def saveTitle(title: Title.Title) = {
    title |> titleRepo.insertOrUpdate |> db.run
    title
  }

  @GraphQLField
  def saveTag(tag: Tag.Tag) = {
    tag |> tagRepo.insertOrUpdate |> db.run
    tag
  }

  @GraphQLField
  def deleteTask(id: String) = {
    taskRepo.filter(_.id === id).delete |> db.run
    id
  }

  @GraphQLField
  def deleteTitle(id: String) = {
    titleRepo.filter(_.id === id).delete |> db.run
    id
  }

  @GraphQLField
  def deleteTag(id: String) = {
    tagRepo.filter(_.id === id).delete |> db.run
    id
  }
}
