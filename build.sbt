import com.lihaoyi.workbench.Plugin._
import UdashBuild._
import Dependencies._

name := "sprout2"

version in ThisBuild := "0.1.0-SNAPSHOT"
scalaVersion in ThisBuild := "2.11.8"
//scalaVersion in ThisBuild := "2.12.1"
organization in ThisBuild := "tk.monnef"
crossPaths in ThisBuild := false
scalacOptions in ThisBuild ++= Seq(
  "-feature",
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions",
  "-language:existentials",
  "-language:dynamics",
  "-Xfuture",
  "-Xfatal-warnings",
  "-Xlint:_,-missing-interpolator,-adapted-args"
)

val sprout2 = project.in(file(".")).enablePlugins(ScalaJSPlugin)
              .settings(
                libraryDependencies ++= deps.value,
                jsDependencies ++= depsJS.value,
                persistLauncher in Compile := true,

                compile <<= (compile in Compile).dependsOn(compileStatics).map(x => {
                  println("http://localhost:12345/target/UdashStatic/WebContent/index.html")
                  x
                }),
                compileStatics := {
                  IO.copyDirectory(
                    sourceDirectory.value / "main/assets/fonts",
                    crossTarget.value / StaticFilesDir / WebContent / "assets/fonts")
                  IO.copyDirectory(
                    sourceDirectory.value / "main/assets/images",
                    crossTarget.value / StaticFilesDir / WebContent / "assets/images")
                  val statics = compileStaticsForRelease.value
                  (crossTarget.value / StaticFilesDir).***.get
                },

                artifactPath in(Compile, fastOptJS) :=
                  (crossTarget in(Compile, fastOptJS)).value / StaticFilesDir / WebContent / "scripts" /
                    "frontend-impl-fast.js",
                artifactPath in(Compile, fullOptJS) :=
                  (crossTarget in(Compile, fullOptJS)).value / StaticFilesDir / WebContent / "scripts" /
                    "frontend-impl.js",
                artifactPath in(Compile, packageJSDependencies) :=
                  (crossTarget in(Compile, packageJSDependencies)).value / StaticFilesDir / WebContent / "scripts" /
                    "frontend-deps-fast.js",
                artifactPath in(Compile, packageMinifiedJSDependencies) :=
                  (crossTarget in(Compile, packageMinifiedJSDependencies)).value / StaticFilesDir / WebContent /
                    "scripts" / "frontend-deps.js",
                artifactPath in(Compile, packageScalaJSLauncher) :=
                  (crossTarget in(Compile, packageScalaJSLauncher)).value / StaticFilesDir / WebContent / "scripts" /
                    "frontend-init.js"
              ).settings(workbenchSettings: _*)
              .settings(
                bootSnippet := "tk.monnef.sprout.Init().main();",
                updatedJS := {
                  var files: List[String] = Nil
                  ((crossTarget in Compile).value / StaticFilesDir ** "*.js").get.foreach {
                    (x: File) =>
                      streams.value.log.info("workbench: Checking " + x.getName)
                      FileFunction.cached(
                        streams.value.cacheDirectory / x.getName, FilesInfo.lastModified, FilesInfo.lastModified) {
                        (f: Set[File]) =>
                          val fsPath = f.head.getAbsolutePath.drop(new File("").getAbsolutePath.length)
                          files = "http://localhost:12345" + fsPath :: files
                          f
                      }(Set(x))
                  }
                  files
                },
                //// use either refreshBrowsers OR updateBrowsers
                // refreshBrowsers <<= refreshBrowsers triggeredBy (compileStatics in Compile)
                updateBrowsers <<= updateBrowsers triggeredBy (compileStatics in Compile)
              )
