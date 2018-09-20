package blockchain.services

import blockchain.data.BlockReader
import blockchain.json.BalanceResponse
import javax.inject.{Inject, Singleton}

@Singleton
class BlockchainExplorerService @Inject() (blockReader: BlockReader){

  def calculateBalance(address: String): Map[String,BalanceResponse] = {
    Map(
      "type1" -> BalanceResponse(calculateBalance4BlockType1(address)),
      "type2" -> BalanceResponse(calculateBalance4BlockType2(address))
    )
  }

  private def calculateBalance4BlockType1(address: String): Int = {
    var accBalance: Int = 0
    for(block <- blockReader.parseTransactions1()){
      // if the address is the recipient, add amount
      accBalance = accBalance + block.transactions.filter(t => t.recipient == address)
        .foldLeft(0)(_ + _.amount)
      // if the address is the recipient, substract amount
      accBalance = accBalance + block.transactions.filter(t => t.sender == address).foldLeft(0)(_ - _.amount)
    }

    accBalance
  }

  private def calculateBalance4BlockType2(address: String): Int = {
    var accBalance: Int = 0
    for(block <- blockReader.parseTransactions2()){
      // if the address is the recipient, add amount
      accBalance = accBalance + block.transactions.filter(t => t.recipient == address)
        .foldLeft(0)(_ + _.recipientBalanceChange)
      // if the address is the recipient, substract amount
      accBalance = accBalance + block.transactions.filter(t => t.sender == address)
        .foldLeft(0)(_ + _.senderbalanceChange)
    }

    accBalance
  }
}