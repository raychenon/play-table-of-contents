import sbt.Keys._

lazy val GatlingTest = config("gatling") extend Test

scalaVersion := "2.11.11"

libraryDependencies += "com.googlecode.htmlcompressor" % "htmlcompressor" % "1.5.2"
// for public assets
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.6"
libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % Test
libraryDependencies += "io.gatling" % "gatling-test-framework" % "2.2.2" % Test

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
