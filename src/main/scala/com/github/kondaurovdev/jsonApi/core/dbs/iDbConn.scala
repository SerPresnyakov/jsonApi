package com.github.kondaurovdev.jsonApi.core.dbs

import play.api.libs.json.{JsString, JsValue}
import scalikejdbc.{NamedDB, DBSession, ConnectionPool}

import com.github.kondaurovdev.jsonApi.core.api._

trait iDbConn {

  def db: iDb
  def user: iDbUser

  def initConnection(): Unit = {

    util.Try {
      ConnectionPool.get(db.connName)
    } match {
      case util.Success(conn) =>
        println(conn)
        if (db.getConnString != conn.url) db.addConn(user)
      case _ =>
        db.addConn(user)
        println("init")
    }

  }

  def withReadOnly[A](block: (DBSession) => Either[JsValue, A]): Either[JsValue, A] = {
    util.Try {
      getDb.readOnly(block)
    } match {
      case util.Success(res) => res
      case util.Failure(err) => Left(errorObj(s"db.readOnly", isInternal = true, details = Some(JsString(err.getMessage))))
    }
  }

  def withAutoCommit[A](block: (DBSession) => Either[JsValue, A]): Either[JsValue, A] = {
    util.Try {
      getDb.autoCommit(block)
    } match {
      case util.Success(res) => res
      case util.Failure(err) => Left(errorObj(s"db.autoCommit", isInternal = true, details = Some(JsString(err.getMessage))))
    }
  }

  def autoCommit[A](block: (DBSession) => A): Either[JsValue, A] = {
    util.Try {
      getDb.autoCommit(block)
    } match {
      case util.Success(res) => Right(res)
      case util.Failure(err) => Left(errorObj(s"db.autoCommit", isInternal = true, details = Some(JsString(err.getMessage))))
    }
  }

  private def getDb: NamedDB = {
    initConnection()
    NamedDB(db.connName)
  }

}

