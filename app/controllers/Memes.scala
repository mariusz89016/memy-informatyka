package controllers

import models.{MemData, Memes}
import play.api.Play.current
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.DB
import play.api.mvc.{Action, Controller}
import play.twirl.api.Html

import scala.slick.driver.PostgresDriver.simple._

object Memes extends Controller {
  lazy val database = Database.forDataSource(DB.getDataSource())
  val memes = TableQuery[Memes]

  val memData = Form(mapping(
    "upText" -> text,
    "downText" -> text,
    "image" -> text
  )(MemData.apply)(MemData.unapply))

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
    Ok(views.html.main.render("create_mem", views.html.create_mem(mem, memData)))
  }

  def save() = Action { implicit request =>
    val resultForm = memData.bindFromRequest.get
    Ok(Html("<img src=\"%s\"/>\n%s\n%s\n".format(resultForm.image, resultForm.upText, resultForm.downText)))
  }
}
