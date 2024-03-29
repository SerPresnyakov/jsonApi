import sbt.Keys._
import sbt._

lazy val demo = (project in file("demo"))
    .settings(
      scalaVersion := Deps.scalaV,
      name := "demo",
      libraryDependencies ++= Deps.sprayAndAkka
    )
    .dependsOn(root)

lazy val root = (project in file("."))
    .settings(
      name := "jsonApi",
      organization := "com.github.kondaurovdev",
      version := "0.1-SNAPSHOT",
      scalaVersion := Deps.scalaV,
      libraryDependencies ++= Seq(
        Deps.logback,
        Deps.specs2,
        Deps.playJson,
        Deps.postgres94,
        Deps.schemaValidator,
        Deps.scalikejdbc
      ),
      scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
      resolvers ++= Seq(
        "Nexus" at "https://nexus.hb.vmc.loc/repository/maven-public/",
        "emueller-bintray" at "http://dl.bintray.com/emueller/maven"
      )
    )
  .settings(publishSettings: _*)

lazy val publishSettings = {
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
  }

  publishMavenStyle := true
  publishArtifact in Test := false
  pomIncludeRepository := { _ => false }
  credentials += Credentials(Path.userHome / ".ivy2" / ".centralCred")

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
}

