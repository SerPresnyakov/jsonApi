package demo.utils

import play.api.libs.json._
import spray.http.{HttpEntity, HttpResponse}
import spray.http.MediaTypes._
import spray.http.StatusCodes._
import spray.routing.Directives._
import spray.routing._

import Unmarshallers._

object Directives {

  def jsonEither[E, D](res: () => Either[E, D])(implicit errorWrites: Writes[E], dataWrites: Writes[D]) = {
    respondWithMediaType(`application/json`) {
      complete {
        res() match {
          case Left(err) => HttpResponse(400, HttpEntity(Json.prettyPrint(Json.obj("error" -> err))))
          case Right(data) => HttpResponse(200, HttpEntity(Json.prettyPrint(Json.obj("data" -> data))))
        }
      }
    }
  }

  def badRequest[T](res: () => T)(implicit writes: Writes[T]) = respondWithStatus(BadRequest) {
    respondWithMediaType(`application/json`) {
      complete(Json.prettyPrint(Json.obj("error" -> Json.toJson(res()))))
    }
  }


  def withJson[B](innerRoute: (B) => Route)(implicit reads: Reads[B]) = {
    entity(as[Either[String, JsValue]]) {
      case Right(json) => json.validate[B].fold(
        err => respondWithStatus(BadRequest) {
          respondWithMediaType(`application/json`) {
            complete(JsError.toJson(err).toString())
          }
        },
        e => innerRoute(e)
      )
      case Left(err) => badRequest(() => s"Wrong json: $err")
    }
  }

}
