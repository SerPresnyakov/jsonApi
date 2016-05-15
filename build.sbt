import sbt._

name := "jsonApi"
organization := "com.github.kondaurovdev"
version := "0.1-SNAPSHOT"
scalaVersion := Deps.scalaV

libraryDependencies ++= Seq(
  Deps.logback,
  Deps.specs2,
  Deps.playJson,
  Deps.postgres94,
  Deps.schemaValidator,
  Deps.scalikejdbc
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

resolvers ++= Seq(
  "Nexus" at "https://nexus.hb.vmc.loc/repository/maven-public/",
  "emueller-bintray" at "http://dl.bintray.com/emueller/maven"
)

publishTo := {
  val nexus = "http://192.168.99.100:8081/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "repository/maven-snapshots/")
  else
    Some("releases" at nexus + "repository/maven-releases/")
}

publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ => false }
credentials += Credentials(Path.userHome / ".ivy2" / ".credentials_local")

pomExtra :=
  <url>https://github.com/kondaurovDev/jsonApi</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://www.opensource.org/licenses/bsd-license.php</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:kondaurovDev/jsonApi.git</url>
      <connection>scm:git:git@github.com:kondaurovDev/jsonApi.git</connection>
    </scm>
    <developers>
      <developer>
        <id>kondaurovDev</id>
        <name>Alexander Kondaurov</name>
        <url>https://github.com/kondaurovDev</url>
      </developer>
    </developers>