package com.github.kondaurovdev.jsonApi.dbs

import scalikejdbc.ConnectionPool

trait iDb {
  def connName: String
  def host: String
  def dbName: String

  def getConnString: String = s"jdbc:postgresql://$host/$dbName"

  def addConn(user: iDbUser): Unit = {
    ConnectionPool.add(connName, getConnString, user.login, user.password)
  }

}

