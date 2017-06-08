import sbt.Keys._

lazy val GatlingTest = config("gatling") extend Test

scalaVersion := "2.11.11"

lazy val libraryDependencies = Seq(
  "com.netaporter" %% "scala-uri" % "0.4.14",
  "net.codingwell" %% "scala-guice" % "4.1.0",
  "com.googlecode.htmlcompressor" % "htmlcompressor" % "1.5.2",
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test,
  "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.2.2" % Test,
  "io.gatling" % "gatling-test-framework" % "2.2.2" % Test
)

// The Play project itself
lazy val root = (project in file("."))
  .enablePlugins(Common, PlayScala, GatlingPlugin)
  .configs(GatlingTest)
  .settings(inConfig(GatlingTest)(Defaults.testSettings): _*)
  .settings(
    name := """play-table-of-contents""",
    scalaSource in GatlingTest := baseDirectory.value / "/gatling/simulation"
  )

// Documentation for this project:
//    sbt "project docs" "~ paradox"
//    open docs/target/paradox/site/index.html
lazy val docs = (project in file("docs")).enablePlugins(ParadoxPlugin).
  settings(
    paradoxProperties += ("download_url" -> "https://example.lightbend.com/v1/download/play-rest-api")
  )
