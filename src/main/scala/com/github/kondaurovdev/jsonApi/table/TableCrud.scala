package com.github.kondaurovdev.jsonApi.table

import com.github.kondaurovdev.jsonApi.dbs.iDbConn
import com.github.kondaurovdev.jsonApi.field.iField
import com.github.kondaurovdev.jsonApi.table.relation.iRelation

case class TableCrud(
                    db: iDbConn,
                    tableName: String,
                    relations: List[iRelation],
                    fields: List[iField],
                    override val pks: List[String])  extends iTableCrud