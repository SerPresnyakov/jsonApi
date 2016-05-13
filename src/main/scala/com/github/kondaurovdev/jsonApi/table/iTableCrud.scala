package com.github.kondaurovdev.jsonApi.table

import com.github.kondaurovdev.jsonApi.dbs.iDbConn
import com.github.kondaurovdev.jsonApi.table.methods.delete.delete
import com.github.kondaurovdev.jsonApi.table.methods.retrieve.retrieve
import com.github.kondaurovdev.jsonApi.table.methods.insert.insertMethods
import com.github.kondaurovdev.jsonApi.table.methods.update.update

trait iTableCrud extends iTable with retrieve with insertMethods with update with delete {
  def db: iDbConn
}
