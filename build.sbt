name := """iversa-play"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.7"

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

libraryDependencies ++= Seq(
  jdbc,
  evolutions,
  javaJpa,
  "org.webjars" % "jquery" % "2.1.4",
  "org.webjars" % "bootstrap" % "3.3.5",
  "mysql" % "mysql-connector-java" % "5.1.21",
  "org.hibernate" % "hibernate-entitymanager" % "5.0.5.Final",
  "org.hibernate" % "hibernate-envers" % "5.0.5.Final"
)     

lazy val root = (project in file(".")).enablePlugins(PlayJava)

routesGenerator := InjectedRoutesGenerator

fork in run := true

PlayKeys.externalizeResources := false