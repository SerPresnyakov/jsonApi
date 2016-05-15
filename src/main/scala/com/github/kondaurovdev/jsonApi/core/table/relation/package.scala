package com.github.kondaurovdev.jsonApi.core.table

import play.api.libs.json.{JsString, JsValue}
import com.github.kondaurovdev.jsonApi.core.field.iField

import scala.util.matching.Regex

package object relation {

  val leftRightRegex = new Regex("""(?i)(left\.\w{1,}|right\.\w{1,})""")

  def extractField(key: String, leftTable: iTable, rightTable: iTable): Either[JsValue, (iTable, iField)] = {

    key.split('.').toList match {
      case List(tableName, fieldName) =>
        for (
          table <- (tableName.toLowerCase match {
            case "left" => Right(leftTable)
            case "right" => Right(rightTable)
            case _ => Left(JsString("left or right expected"))
          }).right;
          field <- table.fields.find(_.name.equalsIgnoreCase(fieldName))
            .toRight(JsString(s"Field '$fieldName' doesn't exists in $tableName table '${table.tableName}'"))
            .right
        ) yield table -> field
      case _ => Left(JsString("Key of format 'tableName.fieldName' expected"))
    }

  }

}
