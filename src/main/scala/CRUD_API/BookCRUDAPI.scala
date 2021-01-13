package CRUD_API
import HTTPPractice._
import akka.actor.TypedActor.dispatcher
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteResult.Complete
import com.typesafe.scalalogging.LazyLogging

import scala.util.Success

class BookCRUDAPI extends JsonSupport with LazyLogging{

  def createBookRoute():Route={
    path("create-book"){
      post{
        entity(as[Book]){ book=>
          logger.info(s"Creating Book= $book")
          onSuccess(BookCRUD.insertBook(book)){ commBook=>
            complete(StatusCodes.Created,s"Created book= $book")
          }
        }
      }
    }
  }

  def getAllBooks():Route={
    path("getAllBooks"){
      get{
        logger.info("Fetching all books.")
        onSuccess(BookCRUD.getCollection){ books=>
          complete(books)
        }
      }
    }
  }

  def updateBooks():Route={
    path("updateBookQuantity"){
      parameters("name".withDefault(""),"quantity".withDefault(0)){(name,quantity)=>
        patch{
            onSuccess(BookCRUD.updateQuantity(name, quantity)) { updatedColss =>
              complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, s"$updatedColss documents are updated."))
            }
          }
        }
      }
  }

  def deleteBookByQuantity():Route={
    path("deleteBook"){
      delete{
        parameters("quantity".withDefault(0)){quantity=>
          onSuccess(BookCRUD.deleteBook(quantity)){deleteBooks=>
            complete(deleteBooks)
          }
        }
      }
    }
  }

  def findBookByPrice():Route={
    path("findBookByPrice"){
      get{
        parameters("price".withDefault(0.0)){price=>
          onSuccess(BookCRUD.findByPrice(price)){foundBooks=>
            complete(foundBooks)
          }
        }
      }
    }
  }

}
