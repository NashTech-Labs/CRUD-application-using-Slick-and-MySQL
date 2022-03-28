ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "CRUD-application-using-Slick-and-MySQL"
  )

libraryDependencies ++= Seq(
  // slick
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "mysql" % "mysql-connector-java" % "8.0.25",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  // testing
  "org.scalatest" %% "scalatest" % "3.2.11"
)