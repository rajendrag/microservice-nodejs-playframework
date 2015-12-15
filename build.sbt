name := """iversa-play"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  jdbc,
  evolutions,
  "org.webjars" % "jquery" % "2.1.4",
  "org.webjars" % "bootstrap" % "3.3.5",
  "mysql" % "mysql-connector-java" % "5.1.21"
)     

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

routesGenerator := InjectedRoutesGenerator

fork in run := true
