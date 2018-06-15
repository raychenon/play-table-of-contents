import sbt.Keys._
import play.sbt.routes.RoutesKeys
RoutesKeys.routesImport := Seq.empty

scalacOptions ++= Seq(
  "-unchecked", "-deprecation", "-target:jvm-1.8", "-encoding", "utf8",
  "-Ywarn-dead-code", "-Ywarn-unused-import", "-Ywarn-unused"
)


scalaVersion := "2.12.6"

libraryDependencies += "com.googlecode.htmlcompressor" % "htmlcompressor" % "1.5.2"
// for public assets
libraryDependencies += "org.webjars" % "bootstrap" % "4.1.0"


// The Play project itself
lazy val root = (project in file("."))
  .enablePlugins(Common, PlayScala,PlayNettyServer)
  .disablePlugins(PlayAkkaHttpServer)
  .settings(
    organization := "com.raychenon",
    name := """play-table-of-contents""",
    version := "0.1.1",
    scalaVersion := "2.12.6"
  )