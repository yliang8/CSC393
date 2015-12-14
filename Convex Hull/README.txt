--------------------------------------------------------------
README.txt:    
    For CSC 393, Fall 2015
    YUAN LIANG
--------------------------------------------------------------

1. Brief Introduction of the Program
	The program enables two parties to implement a protocol using SCAPI proposed by Dr. Muthuramakrishnan Venkitasubramaniam.
		1) The protocol is between two parties, call them Alice and Bob
		2) Alice has a set of points (x1,y1),...,(xn,yn) where you can assume each xi and yi are of type int
		3) Bob has a set of points (w1,z1),...,(wn,zn) where are again all values are of type int
		4) Alice and Bob want to compute the Convex Hull of the set of points {(x1,y1),…,(xn,yn),(w1,z1),…,(wn,zn)}, namely the union of their collection of points


2. File List and Description
	1) Point.java: Definition of Point object
	2) Jarvis.java: Helper class that includes helpers methods for finding convex hull using Jarvis algorithm
	3) Party1.java: Party1 represents Bob in the Protocol. Party1 and Party2 interchange data over a Plain TCP Socket Channel. 
	4) Party2.java: Party2 represents Alice in the Protocol. Party1 and Party2 interchange data over a Plain TCP Socket Channel. 
	5) SocketParties0.properties: A properties file includes connection informations that Party1 is read from
	6) SocketParties1.properties: A properties file includes connection informations that Party2 is read from
	7) README.txt


3. Compile and Run 
	0) Install SCAPI from: http://scapi.readthedocs.org/en/latest/install.html
	
	1) Party1: 
	>> scapic Party1.java
	>> scapi Party1

	2) Party2: 
	>> scapic Party2.java
	>> scapi Party2