import com.github.kondaurovdev.jsonApi.core.dbs.{iDb, iDbUser, iDbConn}

package object demo {

  object AppDbConn extends iDbConn {
    object db extends iDb {
      def connName: String = "acontext2"
      def dbName: String = "acontext2"
      def host: String = "hb.vmc.loc"
    }
    object user extends iDbUser {
      def login: String = "Alex"
      def password: String = "123123"
    }
  }

}
