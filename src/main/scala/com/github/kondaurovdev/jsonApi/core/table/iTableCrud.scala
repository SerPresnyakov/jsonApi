package com.github.kondaurovdev.jsonApi.core.table

import com.github.kondaurovdev.jsonApi.core.dbs.iDbConn
import com.github.kondaurovdev.jsonApi.core.table.methods.delete.delete
import com.github.kondaurovdev.jsonApi.core.table.methods.retrieve.retrieve
import com.github.kondaurovdev.jsonApi.core.table.methods.insert.insertMethods
import com.github.kondaurovdev.jsonApi.core.table.methods.update.update

trait iTableCrud extends iTable with retrieve with insertMethods with update with delete {
  def db: iDbConn
}
