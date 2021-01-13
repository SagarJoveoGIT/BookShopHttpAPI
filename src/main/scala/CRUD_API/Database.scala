package CRUD_API

import ch.rasc.bsoncodec.math.{BigDecimalStringCodec, BigIntegerStringCodec}
import org.bson.codecs.configuration.CodecProvider
import org.bson.codecs.configuration.CodecRegistries.{fromCodecs, fromProviders, fromRegistries}
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.bson.codecs.configuration.CodecRegistries.{fromProviders, fromRegistries}
import org.mongodb.scala.bson.codecs._

import java.util.Arrays.asList

object Database {

  private val codecProvider:CodecProvider=Macros.createCodecProvider(classOf[Book])
  private val customCodec =fromProviders(codecProvider)

  private val javaCodec = org.bson.codecs.configuration.CodecRegistries.fromCodecs(new BigDecimalStringCodec(),new BigIntegerStringCodec())

  private val codecReg = fromRegistries(customCodec,javaCodec, DEFAULT_CODEC_REGISTRY)

  private val DB: MongoDatabase = MongoClient().getDatabase("BookInventory").withCodecRegistry(codecReg)
  val bookDetails: MongoCollection[Book] = DB.getCollection("BookDetails")
}
