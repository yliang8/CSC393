/*
--------------------------------------------------------------
Party1.java:     
    Party1 represents the receiver that receives Party1's data.

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

public class Party1{

    public static void main(String args[]) throws TimeoutException, DuplicatePartyException, java.lang.ClassNotFoundException, java.io.IOException {
        Party1 p1 = new Party1 ();
        try{
            // Establishes a plain TCP socket channel 
            PlainTCPSocketChannel c = p1.setCommunication();
            // Receives the message and prints it out
            Message s = (Message) c.receive();
            byte[] data =  s.getData();
            for(byte b: data){
                int i = b;
                System.out.println("Data in Bytes:" + i);
            }
            c.close();
        }catch(Error e){
            System.out.println(e);
        }
    }

    private static PlainTCPSocketChannel setCommunication() throws TimeoutException, DuplicatePartyException {

        //Prepare the parties list.
        LoadSocketParties loadParties = new LoadSocketParties("SocketParties0.properties");
        List<PartyData> listOfParties = loadParties.getPartiesList();

        TwoPartyCommunicationSetup commSetup = new SocketCommunicationSetup(listOfParties.get(0), listOfParties.get(1));

        //Call the prepareForCommunication function to establish one connection within 2000000 milliseconds.
        Map<String, Channel> connections = commSetup.prepareForCommunication(1, 2000000);

        //Return the channel to the calling application. There is only one created channel.
        return (PlainTCPSocketChannel) connections.values().toArray()[0];
        }

}