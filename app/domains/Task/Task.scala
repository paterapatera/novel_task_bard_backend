package domains.Task

import sangria.marshalling.playJson._
import play.api.libs.json._
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import slick.jdbc.H2Profile.api._
import java.sql.Date
import scala.reflect.ClassTag

case class Task(
    id: String,
    titleId: String,
    groupName: String,
    priority: Int,
    memo: String
)

class TaskRepo(tag: Tag) extends Table[Task](tag, "TASK") {
  def id = column[String]("ID", O.PrimaryKey)
  def titleId = column[String]("TITLE_ID")
  def groupName = column[String]("GROUP_NAME")
  def priority = column[Int]("PRIORITY")
  def memo = column[String]("MEMO")
  def * =
    (id, titleId, groupName, priority, memo).<>(Task.tupled, Task.unapply)
}

object tasks extends TableQuery(new TaskRepo(_)) {}
