package HTTPPractice

import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import CRUD_API._
import com.typesafe.scalalogging.LazyLogging

class ServerVersion extends JsonSupport with LazyLogging{

  def route():Route={
    path("server-version") {
      get {
        val serverVersion = "2.13.4"
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, serverVersion))
      }
    }
  }

  def routeForJson():Route={
    path("server-version-json"){
      get{
        val serverVersion=
          """
            |{
            |App: Scala Driver
            |Version: 2.13.4
            |}"""
            .stripMargin
        complete(HttpEntity(ContentTypes.`application/json`,serverVersion))
      }
    }
  }

  def routeForEncodeJson():Route={
    path("server-version-encode-json"){
      get{
        val book=Book("Scala:Build to be extensible",200,250.0,Some(123))
        complete(book)
      }
    }
  }

  def bookCreateRoute():Route={
    path("create-book"){
      post{
        entity(as[Book]){ book=>
          logger.info(s"Creating Book= $book")
          complete(StatusCodes.Created,s"Created Book= $book")
        }
      }
    }
  }
}
