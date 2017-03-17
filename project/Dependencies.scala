import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt._

object Dependencies {
  val udashVersion = "0.4.0"
  val udashJQueryVersion = "1.0.0"
  val logbackVersion = "1.1.3"

  val deps = Def.setting(Seq[ModuleID](
    "io.udash" %%% "udash-core-frontend" % udashVersion,
    "io.udash" %%% "udash-jquery" % udashJQueryVersion,
    "io.udash" %%% "udash-rest-shared" % udashVersion,
    "io.udash" %%% "udash-bootstrap" % udashVersion,
    "com.github.japgolly.scalacss" %%% "core" % "0.5.0",
    "com.github.japgolly.scalacss" %%% "ext-scalatags" % "0.5.0",
    "org.typelevel" %%% "cats" % "0.7.2"
  ))

  val depsJS = Def.setting(Seq[org.scalajs.sbtplugin.JSModuleID](
  ))
}
