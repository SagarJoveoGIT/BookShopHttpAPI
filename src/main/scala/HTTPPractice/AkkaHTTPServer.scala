package HTTPPractice

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{complete, concat, get}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging

import scala.io.StdIn
import scala.util.{Failure, Success}

object AkkaHTTPServer extends App with LazyLogging {
  implicit val system = ActorSystem("Akka-HTTP-REST-Server")
  implicit val materalizer = ActorMaterializer()
  implicit val ec = system.dispatcher

  val host = "127.0.0.1"
  val port = 8080

  val serverUpdateRoute: Route = get {
    complete("Akka HTTP server is up.")
  }

  val serverVersion = new ServerVersion()
  val serverVersionRoute = serverVersion.route()
  val serverVersionJsonRoute = serverVersion.routeForJson()
  val bookEncodeJsonRoute = serverVersion.routeForEncodeJson()
  val bookCreateRoute=serverVersion.bookCreateRoute()

  val routes: Route = concat(bookCreateRoute,bookEncodeJsonRoute, serverVersionJsonRoute, serverVersionRoute, serverUpdateRoute)

  val httpServerFuture = Http().bindAndHandle(routes, host, port)

  httpServerFuture.onComplete {
    case Success(binding) =>
      logger.info(s"Akka Http server is UP and is bound to ${binding.localAddress}.")
    case Failure(exception) =>
      logger.info(s"Akka Http server is failed to start.")
  }

  StdIn.readLine()
  val asb = httpServerFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
}
