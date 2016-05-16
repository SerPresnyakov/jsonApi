package demo

import akka.actor.Props
import demo.categories.CategoryTable
import spray.routing.HttpServiceActor

import com.github.kondaurovdev.jsonApi.core.api.{crudRequest, CrudApi}

import demo.utils.Directives._

object Router {
  def props = Props(new Router)
}

class Router extends HttpServiceActor {
  def receive: Receive = runRoute {
    path("category") {
      post {
        withJson[crudRequest] { req =>
          jsonEither(() => CrudApi(CategoryTable).doRequest(req))
        }(crudRequest.jsonReads(CategoryTable))
      }
    }
  }
}
