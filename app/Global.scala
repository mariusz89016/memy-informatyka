import models.Mem
import play.api.GlobalSettings
import play.api.Application
import play.api.Play.current
import play.api.db.DB
import scala.slick.driver.PostgresDriver.simple._
import models.Memes
import scala.slick.jdbc.meta.MTable

object Global extends GlobalSettings {
  val memes = TableQuery[Memes]
  lazy val database = Database.forDataSource(DB.getDataSource())
  override def onStart(app: Application): Unit = {
    database.withSession { implicit session =>
      if (MTable.getTables("memes").list.isEmpty) {
        memes.ddl.create

        memes += Mem("nazwa1", "opis1", "dawkins.jpg")
        memes += Mem("nazwa2", "opis2", "kilroy.gif")
        memes += Mem("nazwa3", "opis3", "dawkins.jpg")
        memes += Mem("nazwa4", "opis4", "kilroy.gif")
      }
    }
  }
}
