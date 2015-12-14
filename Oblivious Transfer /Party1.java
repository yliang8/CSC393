/*
--------------------------------------------------------------
Party1.java:     
    Party1 represents the sender in the Protocol. Party1 and 
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
import java.util.Scanner;
import edu.biu.scapi.exceptions.DuplicatePartyException;
import edu.biu.scapi.comm.twoPartyComm.*;
import java.nio.ByteBuffer;
import java.nio.charset.*;
import edu.biu.scapi.primitives.dlog.*;
import edu.biu.scapi.primitives.dlog.openSSL.OpenSSLDlogECF2m;
import edu.biu.scapi.interactiveMidProtocols.ot.semiHonest.OTSemiHonestDDHOnByteArraySender;
import edu.biu.scapi.interactiveMidProtocols.ot.OTOnByteArraySInput;
import edu.biu.scapi.exceptions.*;
import java.io.IOException;
import edu.biu.scapi.tools.Factories.DlogGroupFactory;
import edu.biu.scapi.generals.ScapiDefaultConfiguration;

public class Party1 {

    public static void main(String args[]) throws TimeoutException, DuplicatePartyException, java.lang.ClassNotFoundException, IOException, CheatAttemptException, InvalidDlogGroupException {
        Party1 p1 = new Party1 (); 

        try{
            // Establishes a plain TCP socket channel 
            PlainTCPSocketChannel channel = p1.setCommunication();
            // Creates the OT sender object which uses byte array as input
            OTSemiHonestDDHOnByteArraySender sender = new OTSemiHonestDDHOnByteArraySender();
            // Creates input for the sender 
            byte[] x0 = {3};
            byte[] x1 = {4};
            OTOnByteArraySInput input = new OTOnByteArraySInput (x0,x1);
            // Sends the input over to the receiver
            sender.transfer(channel, input);
        }catch(Error e){
            e.printStackTrace();
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