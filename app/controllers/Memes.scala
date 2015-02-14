package controllers

import models.Mem
import play.api.mvc.{Action, Controller}
import play.twirl.api.Html

object Memes extends Controller {
  val memesList = List(
    Mem(1, "nazwa1", "opis1", "autor1"),
    Mem(2, "nazwa2", "opis2", "autor2"),
    Mem(3, "nazwa3", "opis3", "autor3")
  )

  def list = Action {
    val content = views.html.memes_list.render(memesList)
    val divWrapper = "<div class=\"starter-template\">"+content+"</div>"
    Ok(views.html.main.render("tytul", Html(divWrapper)))
  }

  def show(id: Long) = Action {
    val mem = memesList.find(x => x.id==id).get
    Ok(views.html.mem_page(mem))

  }
}
