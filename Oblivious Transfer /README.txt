--------------------------------------------------------------
README.txt:    
    For CSC 393, Fall 2015
    YUAN LIANG
--------------------------------------------------------------

1. Brief Introduction of the Program
	The program enables two parties to implement an Oblivious Transfer Protocol using SCAPI. In Oblivious Transfer, a party called the sender has n messages, and a party called the receiver has an index i. The receiver wishes to receive the i-th message of the sender, without the sender learning i, while the sender wants to ensure that the receiver receives only one of the n messages. 


2. File List and Description
	1) Party1.java: Party1 represents the sender in the Protocol. Party1 and Party2 are both semi honest parties, executing the Oblivious Transfer Protocol over a Plain TCP Socket Channel using byte array inputs.
	2) Party2.java: Party2 represents the receiver in the Protocol. Party1 and Party2 are both semi honest parties, executing the Oblivious Transfer Protocol over a Plain TCP Socket Channel using byte array inputs.
	3) SocketParties0.properties: A properties file includes connection informations that Party1 is read from
	4) SocketParties1.properties: A properties file includes connection informations that Party2 is read from
	5) README.txt


3. Compile and Run 
	0) Install SCAPI from: http://scapi.readthedocs.org/en/latest/install.html
	
	1) Party1: 
	>> scapic Party1.java
	>> scapi Party1

	2) Party2: 
	>> scapic Party2.java
	>> scapi Party2