package blockchain.blockfactory

import blockchain.json.{BlockchainType1, BlockchainType2, TransactionResponse}

import scala.collection.mutable.ListBuffer

trait BlockTypeFactory[A]{

  def calculateBalance4BlockType(blocks: Seq[A], address: String): Int

  def findTransactions( blocks: Seq[A], address: String): Seq[TransactionResponse]

}
