![Build Status - Master](https://travis-ci.org/raychenon/play-table-of-contents.svg?branch=master)


# Considerations

First, I considered `the two types of blockchain as different currencies. 
The responses in the REST endpoints separate between **type1** and **type2** 

Second, for simplification to do everything in 3 hours time (excluding the pauses), there is no persistence layer to avoid infrastructure set-up.
I suppose getting the endpoints response was more important

> The blockchain explorer must be able to import new blockchain files.
> This can be done
either during runtime or **ahead of execution**

The JSON files are hardcoded in the application. If you want to change the source, go to ```conf/blockchain.conf``` and change the path.

I took an existing project of mine because :
- a new github project name blockchain-explorer will be easier to find on Google rather in branch of an existing project
- it was easier to deploy on an existing base

# How to run

This project is built with Play! framework in Scala.
Go the root of the project and launch the command
> sbt run

# Endpoints

I added two new endpoints to check the data source for 
- GET /blockchain/type1 will return the JSON inside blockchain_type_1_with_1000_blocks.json by default
- GET /blockchain/type2

The format of the two endpoints is as defined in the test.
GET /blockchain/:address:/balance
GET /blockchain/:address:/transactions

In order to separate the two types of cryptocurrency , the response is wrapped in a JSON dictionary 

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

## REST API documentation

The REST API documentation could be done with [Swagger 2](https://swagger.io/docs/specification/2-0/basic-structure/)