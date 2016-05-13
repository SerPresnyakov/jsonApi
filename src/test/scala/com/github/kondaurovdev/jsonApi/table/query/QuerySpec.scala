package com.github.kondaurovdev.jsonApi.table.query

import com.github.kondaurovdev.jsonApi.utils.Helper
import org.specs2.mutable.Specification
import com.github.kondaurovdev.jsonApi.fake.products.ProductsTable
import com.github.kondaurovdev.jsonApi.field.types.StrField
import com.github.kondaurovdev.jsonApi.field.types.number.NumNorm
import com.github.kondaurovdev.jsonApi.table.LeftJoin
import com.github.kondaurovdev.jsonApi.table.filter.filters.EqualFilter

class QuerySpec extends Specification {

  "QuerySpec" should {

    "getAggSubquery" in {

      val actual = Query.getAggSubquery(ProductsTable, ProductsTable.Relations.buyers, List(ProductsTable.tableName)).right.map(Helper.clearStr)
      val expected = Helper.clearStr(
        """
          |(
          |SELECT
          |product_id,
          |array_agg(base) as agg
          |FROM (
          |SELECT
          |base.id,
          |base.name,
          |base.product_id,
          |base
          |FROM buyers as base
          |) as agg
          |GROUP BY product_id
          |)
        """.stripMargin
      )

      actual must beRight(expected)

    }

    "getJoins test" in {

      Query.getJoins(ProductsTable, "base", List("categorySynonyms")) must beRight(List(
        LeftJoin("categories", "base_category", List("base.category_id" -> "base_category.id")),
        LeftJoin("campaign_subtypes", "base_category_campaignSubtype", List("base_category.campaign_subtype_id" -> "base_category_campaignSubtype.id"))
      ))

    }

    "getFilters" in {

      "case 1" in {
        Query.getFilters(EqualFilter("base.id", NumNorm.getPgObject(123))) must beEqualTo(("WHERE base.id = {base_id}", Seq(
          'base_id -> NumNorm.getPgObject(123)
        )))
      }

      "case 2" in {
        Query.getFilters(EqualFilter("base.id", NumNorm.getPgObject(123)), EqualFilter("base.name", StrField.getPgObject("alex"))) must beEqualTo(
          "WHERE base.id = {base_id} AND base.name = {base_name}" -> Seq(
            'base_id -> NumNorm.getPgObject(123),
            'base_name -> StrField.getPgObject("alex")
          ))
      }

    }

  }

}
