package com.github.kondaurovdev.jsonApi.core.table

import com.github.kondaurovdev.jsonApi.core.dbs.iDbConn
import com.github.kondaurovdev.jsonApi.core.field.iField
import com.github.kondaurovdev.jsonApi.core.table.relation.iRelation

case class TableCrud(
                    db: iDbConn,
                    tableName: String,
                    relations: List[iRelation],
                    fields: List[iField],
                    override val pks: List[String])  extends iTableCrud