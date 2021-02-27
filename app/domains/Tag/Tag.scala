package domains.Tag

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import slick.jdbc.H2Profile.api._
import slick.jdbc.H2Profile.api.{Tag => TagG}
import java.sql.Date
import scala.reflect.ClassTag

case class Tag(id: String, name: String)

class TagRepo(tag: TagG) extends Table[Tag](tag, "TAG") {
  def id = column[String]("ID", O.PrimaryKey)
  def name = column[String]("NAME")
  def * = (id, name).<>(Tag.tupled, Tag.unapply)
}

object tags extends TableQuery(new TagRepo(_)) {}
