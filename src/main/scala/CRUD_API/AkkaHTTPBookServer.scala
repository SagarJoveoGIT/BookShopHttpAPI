package CRUD_API

import HTTPPractice.AkkaHTTPServer.{host, logger, port, routes}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{complete, get}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import ch.qos.logback.classic.LoggerContext
import com.typesafe.scalalogging.LazyLogging

import java.util.logging.Level
import scala.util.{Failure, Success}

object AkkaHTTPBookServer extends LazyLogging with App{

  import java.util.logging.Logger

  val mongoLogger = Logger.getLogger("org.mongodb.driver")
  mongoLogger.setLevel(Level.OFF)

  implicit val system = ActorSystem("Akka-HTTP-REST-Server")
  implicit val materalizer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val host = "127.0.0.1"
  val port = 8080

  val serverUpdateRoute: Route = get {
    complete("Akka HTTP server is up.")
  }

  val bookAPI=new BookCRUDAPI()

  val createRoute=bookAPI.createBookRoute()
  val getBooksRoute=bookAPI.getAllBooks()
  val updateBookQuantRoute=bookAPI.updateBooks()
  val deleteBookRoute=bookAPI.deleteBookByQuantity()
  val findBookByPriceRoute=bookAPI.findBookByPrice()

  val routes:Route=Directives.concat(findBookByPriceRoute,deleteBookRoute,updateBookQuantRoute,getBooksRoute,createRoute)

  val httpServerFuture = Http().bindAndHandle(routes, host, port)

  httpServerFuture.onComplete {
    case Success(binding) =>
      logger.info(s"Akka Http server is up and is bound to ${binding.localAddress}.")
    case Failure(exception) =>
      logger.info(s"Akka Http server is failed to start.")
  }
}
