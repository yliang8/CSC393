--------------------------------------------------------------
README.txt:    
    For CSC 393, Fall 2015
    YUAN LIANG
--------------------------------------------------------------

1. Brief Introduction of the Program
	The program implements a Symmetric Encryption Scheme using SCAPI and enables two parties to send each other messages securely using this scheme. A symmetric encryption scheme involves 
		1) Generation of the key
		2) Encryption of the plaintext
		3) Decryption of the ciphertext
	Yet, there are certain limitations of this program. More details see Seciton 4. 


2. File List and Description
	1) Party1.java: represents the sender of message in a Symmetric Encryption Scheme. Party1 and Party2 interchange data over a Plain TCP Socket Channel. 
	2) Party2.java: Party2 represents the receiver of message in a Symmetric Encryption Scheme. Party1 and Party2 interchange data over a Plain TCP Socket Channel. 
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


4. Limitations
In this program, a sender can successfully encrypt a byte array type message and sends it over to a receiver, but a receiver cannot successfully decrypt the message. The problem is brought by setting and getting the byte array data of an IVCiphertext object. The way to get the byte array data is to use the getIv() method in the IVCiphertext class (Party1, line 79), and the only way to set the byte array data is to call the IVCiphertext constructor with a object that implements Symmetric Ciphertext Interface and a byte array as arguments (Party2.java, line 72). However, by doing this, it does not give the right cipher text back. This is either because of an internal API implementation logic error, or this is not the right way to implement the basic usage Symmetric Encryption Scheme (yet this way is suggested by the documentation of SCAPI and the implementation of source code: http://scapi.readthedocs.org/en/latest/mid_layer/symmetric_enc.html#basic-usage) 