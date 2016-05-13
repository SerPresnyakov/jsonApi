package com.github.kondaurovdev.jsonApi.table.relation

import play.api.libs.json.JsObject
import com.github.kondaurovdev.jsonApi.table.iTable

case class Relation(
                   name: String,
                   onCondition: JsObject,
                   rightTable: iTable,
                   hasMany: Boolean = false) extends iRelation
