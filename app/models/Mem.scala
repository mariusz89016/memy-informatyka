package models

import scala.slick.driver.PostgresDriver.simple._

case class Mem(name: String, author: String, imageUrl: String, id: Option[Long] = None)

class Memes(tag: Tag) extends Table[Mem](tag, "memes") {
  def id: Column[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name: Column[String] = column[String]("name")
  def author: Column[String] = column[String]("author")
  def image: Column[String] = column[String]("image")
  def * = (name, author, image, id.?) <> (Mem.tupled, Mem.unapply)
}