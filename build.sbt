lazy val lambda = (project in file(".")).
  settings(
    name := "lambda",
    organization := "com.github.tarao",
    version := "0.0.1",
    scalaVersion := "2.11.6",

    // Depenency
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.2.4" % "test"
    ),

    // Compilation
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-feature"
    )
  )
