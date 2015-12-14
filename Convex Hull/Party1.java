/*
--------------------------------------------------------------
Party1.java:     
    Party1 represents Bob in the Protocol. Party1 and Party2
    interchange data over a Plain TCP Socket Channel. 

    For CSC 393, Fall 2015
    YUAN LIANG
--------------------------------------------------------------
*/


import java.net.InetSocketAddress;
import java.util.concurrent.TimeoutException;
import edu.biu.scapi.comm.Channel;
import edu.biu.scapi.comm.twoPartyComm.PlainTCPSocketChannel;
import static edu.biu.scapi.comm.twoPartyComm.PlainTCPSocketChannel.Message;
import edu.biu.scapi.comm.twoPartyComm.NativeSocketCommunicationSetup;
import java.util.List;
import java.util.Map;
import edu.biu.scapi.exceptions.DuplicatePartyException;
import edu.biu.scapi.comm.twoPartyComm.LoadSocketParties;
import edu.biu.scapi.comm.twoPartyComm.PartyData;
import edu.biu.scapi.comm.twoPartyComm.SocketCommunicationSetup;
import edu.biu.scapi.comm.twoPartyComm.SocketPartyData;
import edu.biu.scapi.comm.twoPartyComm.TwoPartyCommunicationSetup;
import java.util.Scanner;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Party1{
    
    static Point[] bobPoints = new Point [8];

    public static void initialize (){
        bobPoints[0] = new Point (0,3);
        bobPoints[1] = new Point (2,2);
        bobPoints[2] = new Point (3,0);
        bobPoints[3] = new Point (2,-2);
        bobPoints[4] = new Point (0,-5);
        bobPoints[5] = new Point (-2,-2);
        bobPoints[6] = new Point (-3,0);
        bobPoints[7] = new Point (-2,2);
    }

    public static void main(String args[]) throws TimeoutException, DuplicatePartyException, java.lang.ClassNotFoundException, java.io.IOException {
        Party1 p1 = new Party1 (); // Party1 is Bob
        initialize();
        String msgIn = "";
        byte[] inData = new byte [8];
        Point pointIn = new Point (0,0);
        Point pointOut = new Point (0,0);
        Point leftMost = new Point (0,0);
        byte[] outData = new byte[8];
        String msgOut = "";

        try{
            PlainTCPSocketChannel c = p1.setCommunication();
            Jarvis jv = new Jarvis ();
            /*
             * Bob receives the leftmost point from Alice.
             */
            Message in = (Message) c.receive();
            inData = in.getData();
            if (inData.length>0){
                msgIn = new String(inData, StandardCharsets.UTF_8);
                pointIn = jv.toPoint (msgIn);
                System.out.println("RECEIVED: " + msgIn);
            }

            /*
             * Bob compares Alice's leftmost point with his, 
             * and then sends the global leftmost point to Alice
             */
            System.out.print("SEND:      ");
            pointOut = jv.getLeftMost(bobPoints);
            pointOut = jv.getLeftMost(pointIn,pointOut);
            leftMost = pointOut;
            msgOut = pointOut.toString();
            outData = msgOut.getBytes(Charset.forName("UTF-8"));
            Message out = new Message(outData);
            c.send(out);
            System.out.println(pointOut);

            /*
              * Bob finds the next point according to Jarvis march from his
              * collection of points, and then compares it with Alice's.
              * And then sends the global next point to Alice. 
              * The exchanges continue until the convex hull is found. 
              */
            while (true) {
                /*
                 * Bob receives the next point from Alice.
                */
                in = (Message) c.receive();
                inData = in.getData();
                if (inData.length>0){
                    msgIn = new String(inData, StandardCharsets.UTF_8);
                    pointIn = jv.toPoint (msgIn);
                    System.out.println("RECEIVED: " + msgIn);
                }
                /*
                 * Sends the global next point to Alice.
                 */
                System.out.print("SEND:      ");
                Point lastPoint = pointOut;
                pointOut = jv.getNextPoint(bobPoints, leftMost, lastPoint);
                pointOut = jv.getNextPoint(pointIn, pointOut, lastPoint);
                msgOut = pointOut.toString();
                outData = msgOut.getBytes(Charset.forName("UTF-8"));
                out = new Message(outData);
                c.send(out);
                System.out.println(msgOut);
            }
            // c.close();
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