/*
--------------------------------------------------------------
Party2.java:     
    Party2 represents the sender that sends Party2 data in
    a byte array over a Plain TCP Socket Channel. In the run
    time, Party1 enters two integers in the console to send. 

    For CSC 393, Fall 2015
    YUAN LIANG
--------------------------------------------------------------
*/


import java.net.InetSocketAddress;
import java.util.concurrent.TimeoutException;
import edu.biu.scapi.comm.Channel;
import static edu.biu.scapi.comm.twoPartyComm.PlainTCPSocketChannel.Message;
import java.util.List;
import java.util.Map;
import edu.biu.scapi.exceptions.DuplicatePartyException;
import edu.biu.scapi.comm.twoPartyComm.*;
import java.util.Scanner;

public class Party2{

    public static void main(String args[]) throws TimeoutException, DuplicatePartyException, java.lang.ClassNotFoundException, java.io.IOException {
        Party2 p2 = new Party2 ();
        Scanner sc = new Scanner (System.in);
        try{
            // Establishes a plain TCP socket channel 
            PlainTCPSocketChannel c = p2.setCommunication();
            // Get the data (two integers) to send from the user
            byte[] data = new byte[2];
            data[0] = (byte) sc.nextInt();
            data[1] = (byte) sc.nextInt();
            Message msg = new Message (data);
            // Sends it over
            c.send(msg);
            c.close();
        }catch(Error e){
            System.out.println(e);
        }
        
    }
    
    private static PlainTCPSocketChannel setCommunication() throws TimeoutException, DuplicatePartyException {

        //Prepare the parties list.
        LoadSocketParties loadParties = new LoadSocketParties("SocketParties1.properties");
        List<PartyData> listOfParties = loadParties.getPartiesList();

        TwoPartyCommunicationSetup commSetup = new SocketCommunicationSetup(listOfParties.get(0), listOfParties.get(1));

        //Call the prepareForCommunication function to establish one connection within 2000000 milliseconds.
        Map<String, Channel> connections = commSetup.prepareForCommunication(1, 2000000);

        //Return the channel to the calling application. There is only one created channel.
        return (PlainTCPSocketChannel) connections.values().toArray()[0];
        }

}