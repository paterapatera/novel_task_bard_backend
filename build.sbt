name := """novel-task-board-backend"""
organization := "com.ntbb"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "org.sangria-graphql" %% "sangria" % "2.0.0"
libraryDependencies += "org.sangria-graphql" %% "sangria-play-json" % "2.0.0"
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "4.0.2",
  "com.typesafe.play" %% "play-slick-evolutions" % "4.0.2",
  "com.h2database" % "h2" % "1.4.200",
  "com.typesafe.slick" %% "slick-codegen" % "3.3.3"
)
libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.3.3"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.ntbb.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.ntbb.binders._"
