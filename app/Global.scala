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
      if (MTable.getTables("MEMES").list.isEmpty) {
        memes.ddl.create

        memes += Mem(1, "nazwa1", "opis1", "autor1")
        memes += Mem(2, "nazwa2", "opis2", "autor2")
        memes += Mem(3, "nazwa3", "opis3", "autor3")
        memes += Mem(4, "nazwa4", "opis4", "autor4")
      }
    }
  }
}
