import sbt.Keys._
import play.sbt.routes.RoutesKeys
RoutesKeys.routesImport := Seq.empty

scalaVersion := "2.12.6"

libraryDependencies += "com.googlecode.htmlcompressor" % "htmlcompressor" % "1.5.2"
// for public assets
libraryDependencies += "org.webjars" % "bootstrap" % "4.1.0"

dependencyOverrides ++= Seq(
  "com.google.code.findbugs" % "jsr305" % "3.0.2",
  "org.apache.commons" % "commons-lang3" % "3.6"
)


// The Play project itself
lazy val toc = (project in file("."))
  .enablePlugins(Common, PlayScala,PlayNettyServer)
  .disablePlugins(PlayAkkaHttpServer)
  .settings(conflictManager := ConflictManager.strict)
  .settings(
    organization := "com.raychenon",
    name := """play-table-of-contents""",
    version := "0.1.1",
    scalaVersion := "2.12.6" ,
    scalacOptions := {
      val orig = scalacOptions.value
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, 12)) => orig.map {
          case "-Xlint"               => "-Xlint:-unused,_"
          case "-Ywarn-unused-import" => "-Ywarn-unused:imports,-patvars,-privates,-locals,-params,-implicits"
          case other                  => other
        }
        case _             => orig
      }
    }
  )

