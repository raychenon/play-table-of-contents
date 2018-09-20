![Build Status - Master](https://travis-ci.org/raychenon/play-table-of-contents.svg?branch=master)


# Acknowledgements

> Basically the two types of blockchain are holding the same information but the way they
deliver this information is a bit different.

Surprise ! `blockchain_type_1_with_4_blocks.json` and `blockchain_type_2_with_4_blocks.json` don't contain the same information.
 

First, I considered `the two types of blockchain as different currencies. 
The responses in the REST endp`oints separate as **type1** and **type2** 
Anyway the blocks in the two files don't match to the others.

Second, for simplification to do everything in 3 hours time (excluding the pauses), there is no persistence layer.
The JSON files are hardcoded in the application. If you want to change the source, go to the class ```BlockReader#readType1``` and change the path

# Introduction
