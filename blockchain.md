![Build Status - Master](https://travis-ci.org/raychenon/play-table-of-contents.svg?branch=master)


# Considerations

First, I considered `the two types of blockchain as different currencies. 
The responses in the REST endpoints separate between **type1** and **type2** 

Second, for simplification to do everything in 3 hours time (excluding the pauses), there is no persistence layer to avoid infrastructure set-up.
I suppose getting the endpoints response was more important

> The blockchain explorer must be able to import new blockchain files.
> This can be done
either during runtime or **ahead of execution**

The JSON files are hardcoded in the application. If you want to change the source, go to ```conf/blockchain.conf``` and change the path

# How to run

This project is built with Play! framework in Scala.
Go the root of the project and launch the command
> sbt run

# Things I'd do if I had more time

## Architecture for different block types
> This challenge only handles two types of blockchain but you should design a
**software architecture** which will be able to handle other types of blockchain without
rewriting the explorer from scratch every time we need to add a new type of
blockchain.

I'd use a Factory Pattern depending the blockchain type to calculate the balance (see methods ```calculateBalance4BlockType*```) and transactions (see method ```findTransactions4BlockType*```) 

## Persistence
As the number of blocks increases, storing in a persistent database is critical.
A SQL database is a choice. The table structure will be :
- 1 table for balance, saving the latest balance for an address
- 1 table for transactions on the format of blockchain type 1, with an extra column for the blockchain type. The recipient and sender columns will be indexed for fast search.  

Since the transactions are time related, a Time Series Database could be a good alternative.

## Tests
The project didn't say anything about the importance of tests. 

My test toolbox would have been [Specs2](https://github.com/etorreborre/specs2) 

Especially on calculating the transactions for type 2, in some cases where the address of the sender and recipient are the same.
For ex :
```
{
   sender: "Dolor",
   recipient: "Dolor",
   senderBalanceChange: -13,
   recipientBalanceChange: -13
}
```
Does it mean the amount is zero and everything went to the transaction fee ?