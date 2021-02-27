package domains.Title

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import slick.jdbc.H2Profile.api._
import java.sql.Date
import scala.reflect.ClassTag

case class Title(
    id: String,
    name: String,
    description: String,
    image: String,
    tags: String,
    updated: String
)

class TitleRepo(tag: Tag) extends Table[Title](tag, "TITLE") {
  def id = column[String]("ID", O.PrimaryKey)
  def name = column[String]("NAME")
  def description = column[String]("DESCRIPTION")
  def image = column[String]("IMAGE")
  def tags = column[String]("TAGS")
  def updated = column[String]("UPDATED")
  def * = (id, name, description, image, tags, updated).<>(
    Title.tupled,
    Title.unapply
  )
}

object titles extends TableQuery(new TitleRepo(_)) {}
