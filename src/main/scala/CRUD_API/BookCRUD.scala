package CRUD_API

import org.mongodb.scala.Completed
import org.mongodb.scala.model.Filters.equal
import org.mongodb.scala.model.{Filters, Updates}

import java.math.{BigDecimal, BigInteger}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.{Duration, DurationInt}

object BookCRUD {

  def getCollection: Future[Seq[Book]] = {
    val data=Database.bookDetails.find().toFuture()
    Await.result(data,Duration.Inf)
    data
  }

  def insertBook(book:Book): Future[Completed] ={
    val commBook=Database.bookDetails.insertOne(book).toFuture()
    Await.result(commBook,Duration.Inf)
    commBook
  }

  def updateQuantity(name: String, quantity: Integer):Future[Long] = {
    val count=Database.bookDetails.count(equal("book_name", s"$name"))
    val data = Database.bookDetails.updateMany(equal("book_name", s"$name"),
      Updates.set("book_quantity", quantity)).toFuture()
    Await.result(data,Duration.Inf)
    count.toFuture()
  }

  def deleteBook(quantity: Int): Future[Seq[Book]] = {
    val data = Database.bookDetails.find(equal("book_quantity",quantity))
    Await.result(data.toFuture(),Duration.Inf)
    data.foreach(book=>{
    Await.result(Database.bookDetails.deleteOne(equal("book_name",book.book_name)).toFuture,Duration.Inf)})
    data.toFuture()
  }

  def findByPrice(price: Double): Future[Seq[Book]] = {
    val data =Database.bookDetails.find(Filters.lte("book_price",price)).toFuture()
    Await.result(data,Duration.Inf)
    data
  }

  def main(args: Array[String]): Unit = {
//
//    val book1: Book = Book("Scala:Build to be extensible", 200, 250.0, Some(123))
//    val book2: Book = Book("Scala:Build to be extensible", 200, 250.0, Some(12))
//    Await.result(Database.bookDetails.insertOne(book1).toFuture(), 2.seconds)

    //val book2:Book=Book("Java Refernece",new BigInteger("500"),new BigDecimal("890.0"))
    //    Await.result(Database.bookDetails.insertOne(book2).toFuture(),Duration.Inf)
    //
    //    Await.result(Database.bookDetails.find().toFuture(),Duration.Inf)
    //
    //    val books:Seq[Book]=Seq(
    //      Book("Design Patterns",new BigInteger("20"),new BigDecimal("900.0")),
    //      Book("Software Architecture",new BigInteger("50"),new BigDecimal("850.0")),
    //      Book("MongoDB:No-SQL",new BigInteger("101"),new BigDecimal("1200"))
    //    )
    //
    //    val data=/*Await.result(*/Database.bookDetails.insertMany(books)//.toFuture(),Duration.Inf)

    //data.collect().foreach(println)
//    Thread.sleep(1000)
//    println("Data table:")
    //printCollection
    //    updateQuantity("Design Patterns",12)
    //    println("Data table after update:")
    //    printCollection
    //    Thread.sleep(100)
    //    deleteBook(30)
    //    println("Data table after delete:")
    //    printCollection
    //    Thread.sleep(100)
    //    println("Data table after filter:")
    //    findByPrice(860.0)0
//    Thread.sleep(100)
//
//    Thread.sleep(2000)
  }
}
