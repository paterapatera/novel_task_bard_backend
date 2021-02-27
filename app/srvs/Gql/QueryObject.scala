package srvs.Gql
import sangria.macros.derive._
import domains.Tag.{Tag => TagT}
import domains.Task._
import domains.Title._

object QueryObject {
  implicit val TagType = deriveObjectType[Ctx, TagT]()
  implicit val TaskType = deriveObjectType[Ctx, Task]()
  implicit val TitleType = deriveObjectType[Ctx, Title]()
  val QueryType = deriveContextObjectType[Ctx, Query, Unit](_.query)
}
