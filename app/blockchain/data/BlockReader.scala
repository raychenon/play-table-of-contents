package blockchain.data

import java.io.IOException

import blockchain.json.BlockchainType1

//import blockchain.json.{Block, Transaction1}
import play.api.libs.json.{JsResult, JsValue, Json}

import scala.io.Source


class BlockReader() {

  
  def readTransactions1(): Seq[BlockchainType1] = {
    Json.parse(readType1).validate[Seq[BlockchainType1]] match{
      case js: JsResult[Seq[BlockchainType1]] => js.get
      case _ =>  throw new IOException("Cannot parse blockchain type1")
    }
  }
  
  def readType1(): String = {
    read("public/assets/json/blockchain_type_1_with_4_blocks.json")
  }

  def readType2(): String = {
    read("public/assets/json/blockchain_type_2_with_4_blocks.json")
  }

  private def read(source: String): String = {
    Source.fromFile(source).getLines.mkString
  }
}
