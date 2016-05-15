package com.github.kondaurovdev.jsonApi

import com.github.kondaurovdev.jsonApi.core.dbs.{iDbConn, iDb, iDbUser}

package object fake {

  object TestDbConn extends iDbConn {

    object user extends iDbUser {
      def login: String = "testUser"
      def password: String = "123"
    }

    object db extends iDb {
      def connName: String = "testDb"
      def dbName: String = "testDb"
      def host: String = "localhost:5432"
    }

  }



}
