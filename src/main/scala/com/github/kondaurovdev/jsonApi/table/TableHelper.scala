package com.github.kondaurovdev.jsonApi.table

import com.github.kondaurovdev.jsonApi.field.iField
import com.github.kondaurovdev.jsonApi.table.relation.iRelation

import scala.collection.mutable.ListBuffer

object TableHelper {

  def getFieldName(fieldPath: String): String = {
    val parts = ListBuffer(fieldPath.split('.'): _*)

    if (!parts.headOption.exists(_.equalsIgnoreCase(fromAlias))) {
      fromAlias +=: parts
    }

    s"${parts.slice(0, parts.length - 1).mkString("_")}.${parts.last}"
  }

  def findField(fieldPath: String)(implicit table: iTable): Either[String, iField] = {

    val parts = fieldPath
      .replace(s"${fromAlias}_", "")
      .replace(s"$fromAlias.", "")
      .split('.').filter(_.nonEmpty).toList

    parts match {
      case List(path, fieldName) =>

        val pathParts = path.split('_')

        table.relations.find(_.name.equalsIgnoreCase(pathParts.head)) match {
          case Some(r) =>
            val nextFieldName = s"${pathParts.slice(1, pathParts.length).mkString("_")}.$fieldName"
            findField(nextFieldName)(r.rightTable)
          case _ => Left(s"Relation '${pathParts.head}' doesn't exist")
        }

      case List(fieldName) =>
        table.fields.find(_.name == fieldName).toRight(s"Field '$fieldName' doesn't exist")
    }

  }

}
