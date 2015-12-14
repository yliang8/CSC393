/*
--------------------------------------------------------------
Party2.java:     
    Party2 represents the receiver in the Protocol. Party1 and 
    Party2 are both semi honest parties, executing the Oblivious 
    Transfer Protocol over a Plain TCP Socket Channel using 
    byte array inputs.

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
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import edu.biu.scapi.interactiveMidProtocols.ot.semiHonest.OTSemiHonestDDHOnByteArrayReceiver;
import edu.biu.scapi.interactiveMidProtocols.ot.*;

public class Party2{

    public static void main(String args[]) throws TimeoutException, DuplicatePartyException, java.lang.ClassNotFoundException, java.io.IOException {
        Party2 p2 = new Party2 (); 
        try{
            // Establishes a plain TCP socket channel 
            PlainTCPSocketChannel channel = p2.setCommunication();
            // Creates the OT receiver object which uses byte array as output
            OTSemiHonestDDHOnByteArrayReceiver receiver = new OTSemiHonestDDHOnByteArrayReceiver();
            // Creates input for the receiver (index for the data item that the receiver wants)
            byte sigma = 0;
            OTRBasicInput input = new OTRBasicInput(sigma);
            // Receives the corresponding output from the sender
            OTOnByteArrayROutput output = null;
            output = (OTOnByteArrayROutput)receiver.transfer(channel, input);
            byte[] result = output.getXSigma();
            System.out.println("Result:" + result[0]);
            channel.close();
        } catch(Error e){
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