package models

class Mem(var id: Int, var name: String, var description: String, var author: String)

object Mem {
  def apply(id: Int, name: String, description: String, author: String) = new Mem(id, name, description, author)
}