package controllers

import models.Memes
import play.api.Play.current
import play.api.db.DB
import play.api.mvc.{Action, Controller}
import play.twirl.api.Html

import scala.slick.driver.PostgresDriver.simple._

object Memes extends Controller {
  lazy val database = Database.forDataSource(DB.getDataSource())
  val memes = TableQuery[Memes]

  def list = Action {
    val memesList = database.withSession(implicit session => memes.list)
    val content = views.html.memes_list.render(memesList)
    val divWrapper = "<div class=\"starter-template\">"+content+"</div>"
    Ok(views.html.main.render("tytul", Html(divWrapper)))
  }

  def show(id: Long) = Action {
    val memesList = database.withSession(implicit session => memes.list)
    val mem = memesList.find(x => x.id.get==id).get
    Ok(views.html.mem_page(mem))

  }
}
