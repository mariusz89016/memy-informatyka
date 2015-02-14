name := "memy_informatyka"

version := "1.0"

lazy val `memy_informatyka` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws,
"com.typesafe.slick" %% "slick" % "2.1.0",
"com.h2database" % "h2" % "1.3.170")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  