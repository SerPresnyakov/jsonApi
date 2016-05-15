package com.github.kondaurovdev.jsonApi.core.field.types

import org.specs2.mutable.Specification
import play.api.libs.json.{Json, JsNumber, JsString}
import com.github.kondaurovdev.jsonApi.core.field.types.array.{ArrStrField, ArrJsonField}
import com.github.kondaurovdev.jsonApi.core.field.types.number.{NumberField, NumNorm}

class FieldTypeSpec extends Specification {

  "FieldType test" should {

    "parse" in {
      "NumberField" in {
        NumberField(NumNorm).parse(JsString("asd")) must beLeft
        NumberField(NumNorm).parse(JsNumber(123)).right.map(_.getValue) must beRight("123")
      }

      "StrField, test" in {
        StrField().parse(JsString("Alex")).right.map(_.getValue) must beRight("Alex")
      }

      "ArrJsonField, test" in {
        ArrJsonField(StrField.getSchema, isUnique = true, 0, 10)
          .parse(Json.toJson(List("One", "Two")))
          .right.map(_.getValue) must beRight("{\"One\",\"Two\"}")
      }

      "ArrStrField, test" in {
        ArrStrField(isUnique = true, 0, 10)
          .parse(Json.toJson(List("One", "Two")))
          .right.map(_.getValue) must beRight("{\"One\",\"Two\"}")
      }

    }
  }

}
