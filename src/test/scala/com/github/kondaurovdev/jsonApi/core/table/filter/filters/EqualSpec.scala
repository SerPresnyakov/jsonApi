package com.github.kondaurovdev.jsonApi.core.table.filter.filters

import org.specs2.mutable.Specification
import com.github.kondaurovdev.jsonApi.core.fake.products.ProductFields
import com.github.kondaurovdev.jsonApi.core.field.types.StrField
import play.api.libs.json.JsString

class EqualSpec extends Specification {

  "Equals test" should {

    "extract test" in {

      "case 1" in {
        EqualExtractor.extract(StrField(), "path", Some(JsString("asd"))) must beRight(EqualFilter("path", StrField.getPgObject("asd")) )
      }

    }

  }

}
