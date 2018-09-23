package blockchain.services

import blockchain.data.BlockReader
import blockchain.json._
import javax.inject.{Inject, Singleton}

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

@Singleton
class BlockchainExplorerService @Inject() (blockReader: BlockReader){

  /**
    * Improvement: if the address doesn't exist could return a null in the JSON response instead of 0 in the balance
    * could return Map[String,Option[BalanceResponse]]
    * @param address
    * @return
    */
  def calculateBalance(address: String): Map[String,BalanceResponse] = {
    Map(
      "type1" -> BalanceResponse(calculateBalance4BlockType1(blockReader.parseBlockType1(),address)),
      "type2" -> BalanceResponse(calculateBalance4BlockType2(blockReader.parseBlockType2(),address))
    )
  }



  /**
    * What happens if a transaction, the address of the sender and the receiver is the same ?
    * ex :
    * {
    * sender: "Dolor",
    * recipient: "Dolor",
    * amount: 7,
    * fees: 69
    * }
    * Here in this method in that case, the amount is not deduced from the sender and added to the recipient.
    * The amount is only added to the recipient
    *
    * @param blocks
    * @param address
    * @return
    */
  private def calculateBalance4BlockType1(blocks: Seq[BlockchainType1],address: String): Int = {
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
  
  private def calculateBalance4BlockType2(blocks: Seq[BlockchainType2],address: String): Int = {
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


  def collectTransactions(address: String): Map[String,Seq[TransactionResponse]] = {
    Map(
      "type1" -> findTransactions4BlockType1(blockReader.parseBlockType1(),address),
      "type2" -> findTransactions4BlockType2(blockReader.parseBlockType2(),address)
    )
  }

  private def findTransactions4BlockType1(blocks: Seq[BlockchainType1],address: String): Seq[TransactionResponse] = {
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

  private def findTransactions4BlockType2(blocks: Seq[BlockchainType2],address: String): Seq[TransactionResponse] = {
    val listBuffer = new ListBuffer[TransactionResponse]()

    for(block <- blocks){

      val transactionsContainingAddress = block.transactions.filter(t => t.recipient == address || t.sender == address)
      for(trx <- transactionsContainingAddress){
        val amount =  if (trx.sender == trx.recipient) 0 else trx.recipientBalanceChange
        val fee = calculateFeeType2(trx)
        val response = TransactionResponse(trx.sender,trx.recipient, amount,fee, block.date)
        listBuffer += response
      }

    }
    listBuffer
  }

  private def calculateFeeType2(trx: Transaction2): Int ={
    // there is no fee for the genesis block
    if(trx.senderbalanceChange == 0) 0
    else {
      // senderbalanceChange is always negative, substract from it equals to add a positive number
      if (trx.sender == trx.recipient) -trx.senderbalanceChange
      else (Math.abs(trx.senderbalanceChange) - trx.recipientBalanceChange)
    }
  }
}
