name := """rme-web"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "com.h2database" % "h2" % "1.4.196"
libraryDependencies += filters
libraryDependencies += "org.webjars" % "jquery" % "3.2.1"
libraryDependencies += "org.webjars" % "bootstrap" % "4.0.0-beta-1"
libraryDependencies += "com.adrianhurt" %% "play-bootstrap" % "1.2-P26-B4"
libraryDependencies += "org.scoverage" % "scalac-scoverage-runtime_2.12" % "1.3.0"
