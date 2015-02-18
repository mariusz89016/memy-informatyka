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
    "Up text" -> text,
    "Down text" -> text,
    "Data image" -> text
  )(MemData.apply)(MemData.unapply))

  def list = Action {
    val memesList = database.withSession(implicit session => memes.list)
    Ok(views.html.memes_list.render("Home", memesList))
  }

  def show(id: Long) = Action {
    val mem = database.withSession(implicit session => memes.list.find(x => x.id.get==id).get)
    Ok(views.html.mem_page.render(s"Show mem: $id", mem))
  }

  def create(id: Long) = Action {
    val mem = database.withSession(implicit session => memes.list.find(x=>x.id.get==id).get)
    Ok(views.html.create_mem.render("Create mem", mem, memData))
  }

  def save() = Action { implicit request =>
    val resultForm = memData.bindFromRequest.get
    Ok(Html("<img src=\"%s\"/>\n%s\n%s\n".format(resultForm.image, resultForm.upText, resultForm.downText)))
  }

  def add() = TODO
}
