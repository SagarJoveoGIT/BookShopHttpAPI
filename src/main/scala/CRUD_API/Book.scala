package CRUD_API

//case class Book(book_name:String="Some Book", book_quantity:Int=10, book_price:Double=100.0,book_option:Option[Long]=None)
case class Book(book_name:String, book_quantity:Int, book_price:Double,book_option:Option[Long])
case class Books(books:Seq[Book])