package blockchain.blockfactory
import blockchain.json.{BlockchainType1, TransactionResponse}

import scala.collection.mutable.ListBuffer


class BlockType1Impl extends BlockTypeFactory[BlockchainType1] {

  override def calculateBalance4BlockType(blocks: Seq[BlockchainType1], address: String): Int = {
    var accBalance: Int = 0
    for(block <- blocks){
      // if the address is the recipient, add amount
      accBalance = accBalance + block.transactions.filter(t => t.recipient == address)
        .map(_.amount).sum
      // subtract the fee from the sender
      accBalance = accBalance + block.transactions.filter(t => t.sender == address).foldLeft(0)(_ - _.fees)
    }

    accBalance
  }

  override def findTransactions(blocks: Seq[BlockchainType1], address: String): Seq[TransactionResponse] = {
    val listBuffer = new ListBuffer[TransactionResponse]()

    for(block <- blocks){

      val transactionsContainingAddress = block.transactions.filter(t => t.recipient == address || t.sender == address)
      for(trx <- transactionsContainingAddress){
        val response = TransactionResponse(trx.sender,trx.recipient, trx.amount,trx.fees, block.date)
        listBuffer += response
      }

    }
    listBuffer
  }

}
