name := "exemplosi1"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  javaWs,
  javaJpa,
  evolutions,
  jdbc,
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc42"
)

