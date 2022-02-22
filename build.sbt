ThisBuild / scalaVersion     := "2.13.3"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "twittercli",
    libraryDependencies ++= Seq(
      "joda-time" % "joda-time" % "2.10.10",
      "org.scalatest" %% "scalatest" % "3.2.0" % Test
    )
  )