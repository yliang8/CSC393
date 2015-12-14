--------------------------------------------------------------
README.txt:    
    For CSC 393, Fall 2015
    YUAN LIANG
--------------------------------------------------------------


1. Brief Introduction of the Program
	The program is simple demonstration of the communication between two parties over a Plain TCP Socket Channel using SCAPI. The sender simply inputs two integers and sends it over to the receiver. 


2. File List and Description
	1) Party1.java: Party1 represents the receiver that receives Party2's data
	2) Party2.java: Party2 represents the sender that sends data in a byte array over a Plain TCP Socket Channel. In run time, Party2 enters two integers in the console to send. 
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
	>> Enter one integer to send
	>> Enter another integer to send