package com.github.kondaurovdev.jsonApi.core.utils

import play.api.libs.json._

import scala.collection.mutable.ListBuffer
import scala.language.implicitConversions

object Helper {

  implicit def either2Json[A1, A2](e: Either[A1, A2])(implicit a1Writes: Writes[A1], a2Writes: Writes[A2]): JsValue = {
    e match {
      case Left(a1) => Json.toJson(a1)
      case Right(a2) => Json.toJson(a2)
    }
  }

  def str2symbol(lst: Seq[(String, Any)]): Seq[(Symbol, Any)] = {
    lst.map(t => Symbol(t._1) -> t._2)
  }

  def cameCaseToSnakeCase(s: String): String = {

    val result = ListBuffer.empty[Char]

    s.zipWithIndex.foreach {
      case (c, i) => if (c.isUpper) {
        if (result.lastOption.exists(_.isLower)) result ++= Seq('_', c.toLower)
      } else {
        result += c.toLower
      }
    }

    result.mkString

  }

  def snakeCaseToCamelCase(s: String): String = {

    val result = ListBuffer.empty[Char]

    s.foreach(c => {
      if (result.lastOption.contains('_')) {
        result += c.toUpper
      } else {
        result += c
      }
    })

    result.filterNot(c => c == '_').mkString

  }

  def normalizeArrFields(v: JsArray): JsArray = {

    val result = ListBuffer.empty[JsValue]

    v.value.map {
      case o: JsObject => result += normalizeObjFields(o)
      case arr: JsArray => result += normalizeArrFields(arr)
      case JsNull =>
      case i => result += i
    }

    JsArray(result)

  }

  def normalizeObjFields(o: JsObject): JsObject = {

    val result = ListBuffer.empty[(String, JsValue)]

    o.fields.foreach {
      case (key, value) => result += snakeCaseToCamelCase(key) -> {
        value match {
          case obj: JsObject => normalizeObjFields(obj)
          case arr: JsArray => normalizeArrFields(arr)
          case _ => value
        }
      }
    }

    JsObject(result)

  }

  def clearStr(str: String): String = {

    val spacesRegex = """\s{2,}"""
    val controlSymsRegex = """(\n|\r)""".r
    val step1 = controlSymsRegex.replaceAllIn(str, " ")

    step1.replaceAll(spacesRegex, " ").trim

  }

}
