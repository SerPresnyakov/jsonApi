package com.github.kondaurovdev.jsonApi.field

case class Field(
                name: String,
                fieldName: String,
                fieldType: fieldType,
                nullable: Boolean = false,
                hasDefault: Boolean = true) extends iField
