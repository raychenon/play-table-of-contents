package blockchain.blockfactory

import blockchain.json.{BlockchainType2, TransactionResponse}

import scala.collection.mutable.ListBuffer

class BlockType2Impl extends BlockTypeFactory[BlockchainType2] {

  override def calculateBalance4BlockType(blocks: Seq[BlockchainType2], address: String): Int = {
    var accBalance: Int = 0
    for(block <- blocks){
      // sum the amounts received by the recipient
      accBalance = accBalance + block.transactions.filter(t => t.recipient == address)
        .map(_.recipientBalanceChange).sum
      // subtract the sender balance change, since the value is negative just add
      accBalance = accBalance + block.transactions.filter(t => t.sender == address)
        .foldLeft(0)(_ + _.senderbalanceChange)
    }

    accBalance
  }


  override def findTransactions(blocks: Seq[BlockchainType2], address: String): Seq[TransactionResponse] = {
    val listBuffer = new ListBuffer[TransactionResponse]()

    for(block <- blocks){

      val transactionsContainingAddress = block.transactions.filter(t => t.recipient == address || t.sender == address)
      for(trx <- transactionsContainingAddress){
        val amount = trx.recipientBalanceChange
        // there is no fee for the genesis block ,
        // senderbalanceChange is always negative, substract from it equals to add a positive number
        val fee = if(trx.senderbalanceChange == 0) 0 else (Math.abs(trx.senderbalanceChange) - trx.recipientBalanceChange)
        val response = TransactionResponse(trx.sender,trx.recipient, amount,fee, block.date)
        listBuffer += response
      }

    }
    listBuffer
  }

}
