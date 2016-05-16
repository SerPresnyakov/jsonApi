package demo.categories

import com.github.kondaurovdev.jsonApi
import com.github.kondaurovdev.jsonApi.core.field.types.StrField
import com.github.kondaurovdev.jsonApi.core.field.types.number.NumberField
import jsonApi.core.dbs.iDbConn
import jsonApi.core.field.{Field, iField}
import com.github.kondaurovdev.jsonApi.core.table.iTableCrud
import com.github.kondaurovdev.jsonApi.core.table.relation.iRelation
import demo.AppDbConn

object CategoryTable extends iTableCrud {
  def db: iDbConn = AppDbConn
  def tableName: String = "ts.categories"
  def relations: List[iRelation] = List()
  def fields: List[iField] = List(
    Field("id", "id", NumberField.pk),
    Field("name", "name", StrField())
  )
}
