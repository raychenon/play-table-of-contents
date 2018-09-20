package blockchain.controllers

import akka.stream.scaladsl.FileIO
import akka.util.ByteString
import com.typesafe.config.ConfigFactory
import javax.inject.Inject
import play.api.Configuration
import play.api.libs.json.{JsValue, Json}

import scala.io.Source


class Reader() {

  def read(): String = {

    val source: String = Source.fromFile("public/assets/json/blockchain_type_1_with_4_blocks.json").getLines.mkString
    source
  }
}
