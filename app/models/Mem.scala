package models

import scala.slick.driver.PostgresDriver.simple._

case class Mem(id: Int, name: String, description: String, author: String)

class Memes(tag: Tag) extends Table[Mem](tag, "MEMES") {
  def id: Column[Int] = column[Int]("MEM_ID", O.PrimaryKey)
  def name: Column[String] = column[String]("MEM_NAME")
  def description: Column[String] = column[String]("MEM_DESCR")
  def author: Column[String] = column[String]("MEM_AUTHOR")
  def * = (id, name, description, author) <> (Mem.tupled, Mem.unapply)
}