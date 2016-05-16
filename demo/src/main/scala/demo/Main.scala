package demo

import akka.actor.ActorSystem
import akka.io.IO
import org.slf4j.LoggerFactory
import spray.can.Http

object Main extends App {

  val d = java.sql.DriverManager.getDrivers

  logSql()

  while (d.hasMoreElements) {
    println("Driver: " + d.nextElement().getClass.getName)
  }

  val log = LoggerFactory.getLogger(this.getClass)

  implicit val system = ActorSystem("jsonApiDemo")

  val listener = system.actorOf(Router.props, "router")

  IO(Http) ! Http.Bind(listener, "0.0.0.0", 9000)

  def logSql() = {
    import scalikejdbc.{GlobalSettings, LoggingSQLAndTimeSettings}
    GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
      enabled = true,
      singleLineMode = false,
      printUnprocessedStackTrace = false,
      stackTraceDepth= 15,
      logLevel = 'debug,
      warningEnabled = false,
      warningThresholdMillis = 3000L,
      warningLogLevel = 'warn
    )
  }

}
