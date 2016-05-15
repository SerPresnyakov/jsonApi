package com.github.kondaurovdev.jsonApi.core.table

import com.github.kondaurovdev.jsonApi.core.fake.campaignSubtypes.CampaignSubtypeFields
import com.github.kondaurovdev.jsonApi.core.fake.products.{ProductFields, ProductsTable}
import org.specs2.mutable.Specification

class TableHelperSpec extends Specification {

  "TableHelper" should {

    "getFieldName" in {
      "case 'id'" in { TableHelper.getFieldName("id") must beEqualTo("base.id")}
      "case 'base.id'" in { TableHelper.getFieldName("base.id") must beEqualTo("base.id")}
      "case 'category.id'" in { TableHelper.getFieldName("category.id") must beEqualTo("base_category.id")}
      "case 'base.category.campaignSubtype.id'" in { TableHelper.getFieldName("base.category.campaignSubtype.id") must beEqualTo("base_category_campaignSubtype.id")}
    }

    "findField" in {

      "case 1" in {
        TableHelper.findField("base.id")(ProductsTable) must beEqualTo(Right(ProductFields.id))
      }

      "case 2" in {
        TableHelper.findField("base_category_campaignSubtype.name")(ProductsTable) must beEqualTo(Right(CampaignSubtypeFields.name))
      }

    }


  }

}
