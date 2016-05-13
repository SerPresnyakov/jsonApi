import sbt._

object Deps {

  val scalaV = "2.11.7"

  val httpClient = "org.scalaj" %% "scalaj-http" % "2.2.0"
  val csv = "com.github.tototoshi" %% "scala-csv" % "1.2.2"

  val sprayV = "1.3.3"
  val akkaV = "2.3.9"

  val specs2 = "org.specs2" %% "specs2-core" % "3.7.2" % "test"
  val playJson = "com.typesafe.play" %% "play-json" % "2.5.0"
  val bcrypt = "com.github.t3hnar" %% "scala-bcrypt" % "2.4"
  val schemaValidator = "com.eclipsesource" %% "play-json-schema-validator" % "0.7.0"

  val scalikejdbc = "org.scalikejdbc" %% "scalikejdbc" % "2.3.5"

  val postgres94 = "org.postgresql" % "postgresql" % "9.4.1208.jre7"

  val spray = Seq(
    "io.spray" %%  "spray-can" % sprayV,
    "io.spray" %%  "spray-routing-shapeless2" % sprayV,
    "io.spray" %%  "spray-testkit" % sprayV  % "test"
  )

  val akka = Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-slf4j" % akkaV
  )

  def sprayAndAkka = spray ++ akka

  val logback = "ch.qos.logback" % "logback-classic" % "1.1.3"
  val logstashLogback = "net.logstash.logback" % "logstash-logback-encoder" % "4.6"
  val jsonApi = "com.github.kondaurovdev" %% "jsonapi" % "0.1-SNAPSHOT"

}