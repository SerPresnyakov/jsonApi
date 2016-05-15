package com.github.kondaurovdev.jsonApi.core.table.relation

import com.github.kondaurovdev.jsonApi.core.field.iField
import org.postgresql.util.PGobject
import play.api.libs.json.Json.JsValueWrapper
import play.api.libs.json.{Json, JsString, JsValue, JsObject}
import com.github.kondaurovdev.jsonApi.core.table.{bindings, iTable}
import com.github.kondaurovdev.jsonApi.core.utils.Helper

import scala.collection.mutable.ListBuffer

trait iRelation {

  def name: String
  def onCondition: JsObject

  def hasMany: Boolean

  def rightTable: iTable

  def getAlias(leftTableAlias: String): String = s"${leftTableAlias}_${Helper.snakeCaseToCamelCase(name)}"

  def getOnClause(leftTable: iTable, leftTableAlias: String): Either[JsValue, (List[(String, String)], bindings)] = {

    val result = ListBuffer.empty[(String, String)]
    val bindings = ListBuffer.empty[(Symbol, PGobject)]
    val errors = ListBuffer.empty[JsValueWrapper]

    onCondition.value.foreach { case (key, value) =>

      extractField(key, leftTable, rightTable) match {
        case Left(err) => errors += err
        case Right((_leftTable, _leftField)) =>

          val leftPart = s"$leftTableAlias.${_leftField.fieldName}"

          value match {
            case JsString(s) if leftRightRegex.findFirstIn(s).isDefined =>
              extractField(s, leftTable, rightTable) match {
                case Left(err) => errors += s"$leftPart: $err"
                case Right((_rightTable, _rightField)) =>
                  val rightPart = s"${getAlias(leftTableAlias)}.${_rightField.fieldName}"
                  result += leftPart -> rightPart
              }
            case _ => _leftField.fieldType.parse(value) match {
              case Left(err) => errors += s"$leftPart: $err"
              case Right(res) =>
                result += leftPart -> s"{$leftPart}"
                bindings += Symbol(leftPart) -> res
            }
          }

      }

    }

    if (errors.isEmpty) {
      Right(result.result() -> bindings.result())
    } else {
      Left(Json.arr(errors: _*))
    }

  }

  def getOnFields(prefix: String, leftTable: iTable): Either[JsValue, List[iField]] = {

    val result = ListBuffer.empty[iField]
    val errors = ListBuffer.empty[JsValue]

    def extractAndAdd(s: String) = {
      extractField(s, leftTable, rightTable).fold(
        err => errors += err,
        res => result += res._2
      )
    }

    onCondition.value.foreach { case (key, value) =>
      if (key.contains(s"$prefix.")) extractAndAdd(key)
      value.asOpt[String].foreach {
        case s if s.contains(s"$prefix.") => extractAndAdd(s)
        case _ =>
      }
    }

    if (errors.isEmpty) Right(result.result()) else Left(Json.arr(errors))

  }

}