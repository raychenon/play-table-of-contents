package blockchain.blockfactory

import blockchain.json.{BlockchainType1, BlockchainType2, TransactionResponse}


trait BlockTypeFactory[A]{

  def calculateBalance4BlockType(blocks: Seq[A], address: String): Int

  def findTransactions( blocks: Seq[A], address: String): Seq[TransactionResponse]

}


// https://stackoverflow.com/questions/39682752/scala-factory-for-generic-types-using-the-apply-method?rq=1
object BlockTypeFactory{

  def getBlockExplorer[A](blockType: A): BlockTypeFactory[A] = {
    blockType match {
      case type1: BlockchainType1 => new BlockTypeFactory[BlockchainType1](new BlockType1Impl)
      case type2: BlockchainType2 => new BlockTypeFactory[BlockchainType2](new BlockType2Impl)
    }
  }

  def apply[A](implicit ev: BlockTypeFactory[A]):BlockTypeFactory[A] = ev

}