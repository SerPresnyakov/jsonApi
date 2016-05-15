package com.github.kondaurovdev.jsonApi.core.field

trait iField {
  def name: String
  def fieldName: String
  def fieldType: fieldType
  def nullable: Boolean
  def hasDefault: Boolean
}
