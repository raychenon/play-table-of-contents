import sbt.Keys._

lazy val GatlingTest = config("gatling") extend Test

scalaVersion := "2.12.6"

libraryDependencies += "com.googlecode.htmlcompressor" % "htmlcompressor" % "1.5.2"
// for public assets
libraryDependencies += "org.webjars" % "bootstrap" % "4.1.0"
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.3.1" % Test
libraryDependencies += "io.gatling" % "gatling-test-framework" % "2.3.1" % Test

// The Play project itself
lazy val root = (project in file("."))
  .enablePlugins(Common, PlayScala,PlayNettyServer, GatlingPlugin)
  .disablePlugins(PlayAkkaHttpServer)
  .configs(GatlingTest)
  .settings(inConfig(GatlingTest)(Defaults.testSettings): _*)
  .settings(
    name := """play-table-of-contents""",
    scalaSource in GatlingTest := baseDirectory.value / "/gatling/simulation"
  )
