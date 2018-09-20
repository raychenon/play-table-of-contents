package blockchain.data

import scala.io.Source


class BlockReader() {

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
