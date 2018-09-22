package blockchain.data

import java.io.IOException

import blockchain.json.{BlockchainType1, BlockchainType2}
import play.api.Configuration
import play.api.libs.json.{JsResult, JsValue, Json}

import scala.io.Source


class BlockReader(config: Configuration) {

  
  def parseBlockType1(): Seq[BlockchainType1] = {
    Json.parse(readType1).validate[Seq[BlockchainType1]] match{
      case js: JsResult[Seq[BlockchainType1]] => js.get
      case _ =>  throw new IOException("Cannot parse blockchain type 1")
    }
  }

  def parseBlockType2(): Seq[BlockchainType2] = {
    Json.parse(readType2).validate[Seq[BlockchainType2]] match{
      case js: JsResult[Seq[BlockchainType2]] => js.get
      case _ =>  throw new IOException("Cannot parse blockchain type 2")
    }
  }
  
  def readType1(): String = {
    read(config.get[String]("blockchain.type1"))
  }

  def readType2(): String = {
    read(config.get[String]("blockchain.type2"))
  }

  private def read(source: String): String = {
    Source.fromFile(source).getLines.mkString
  }
}
