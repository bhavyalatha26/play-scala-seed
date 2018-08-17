name := """play-scala-demo"""

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

//library settings
libraryDependencies ++= Seq(
  ehcache,
  ws,
  specs2 % Test,
  filters,
  guice,
  openId,
  "com.typesafe.play" %% "play-json" % "2.6.0",
  "com.typesafe.play" %% "play-json-joda" % "2.6.0",
  "joda-time" % "joda-time" % "2.7",
  "org.joda" % "joda-convert" % "1.7",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.13.0-play26",
  "org.logback-extensions" % "logback-ext-loggly" % "0.1.2"
)

//Scala Style Settings
scalastyleFailOnWarning := false
scalastyleFailOnError := true