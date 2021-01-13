package HTTPPractice
import CRUD_API._

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  import spray.json._

  implicit val printer = PrettyPrinter
  implicit val bookFormat = jsonFormat4(Book)
  implicit val booksFormat=jsonFormat1(Books)
}
