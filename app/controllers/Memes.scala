package controllers

import models.Memes
import play.api.Play.current
import play.api.db.DB
import play.api.mvc.{Action, Controller}

import scala.slick.driver.PostgresDriver.simple._

object Memes extends Controller {
  lazy val database = Database.forDataSource(DB.getDataSource())
  val memes = TableQuery[Memes]

  def list = Action {
    val memesList = database.withSession(implicit session => memes.list)
    Ok(views.html.main.render("tytul", views.html.memes_list.render(memesList)))
  }

  def show(id: Long) = Action {
    val mem = database.withSession(implicit session => memes.list.find(x => x.id.get==id).get)
    Ok(views.html.main.render("tytul_mem", views.html.mem_page(mem)))
  }

  def create(id: Long) = Action {
    val mem = database.withSession(implicit session => memes.list.find(x=>x.id.get==id).get)
    Ok(views.html.main.render("create_mem", views.html.create_mem(mem)))
  }
}
