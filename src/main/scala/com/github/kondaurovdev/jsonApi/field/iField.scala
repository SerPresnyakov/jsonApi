package com.github.kondaurovdev.jsonApi.field

trait iField {
  def name: String
  def fieldName: String
  def fieldType: fieldType
  def nullable: Boolean
  def hasDefault: Boolean
}
