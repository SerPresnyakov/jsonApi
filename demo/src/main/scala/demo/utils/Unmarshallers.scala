package demo.utils

import play.api.libs.json.{JsValue, Json}
import spray.http.HttpEntity
import spray.http.MediaTypes._
import spray.httpx.unmarshalling.Unmarshaller

object Unmarshallers {

  implicit val JsValueUnmarshaller = Unmarshaller[Either[String, JsValue]](`application/json`) {

    case HttpEntity.NonEmpty(contentType, data) =>
      util.Try {
        Json.parse(data.toByteArray)
      } match {
        case util.Success(jsVal) => Right(jsVal)
        case util.Failure(err) => Left(s"${err.getMessage}")
      }

  }

}
