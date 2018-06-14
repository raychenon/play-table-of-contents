import sbt.Keys._


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
