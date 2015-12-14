/*
--------------------------------------------------------------
Party2.java:     
    Party2 represents Alice in the Protocol. Party1 and Party2
    interchange data over a Plain TCP Socket Channel. 

    For CSC 393, Fall 2015
    YUAN LIANG
--------------------------------------------------------------
*/


import java.net.InetSocketAddress;
import java.util.concurrent.TimeoutException;
import edu.biu.scapi.comm.Channel;
import edu.biu.scapi.comm.twoPartyComm.*;
import static edu.biu.scapi.comm.twoPartyComm.PlainTCPSocketChannel.Message;
import java.util.List;
import java.util.Map;
import edu.biu.scapi.exceptions.DuplicatePartyException;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Party2{

    static LinkedList<Point> results = new LinkedList<Point> ();
    static boolean finished = false;
    static Point[] alicePoints= new Point [8];

    public static void initialize() {
        alicePoints[0] = new Point (0,2);
        alicePoints[1] = new Point (1,1);
        alicePoints[2] = new Point (2,0);
        alicePoints[3] = new Point (1,-1);
        alicePoints[4] = new Point (0,-2);
        alicePoints[5] = new Point (-1,-1);
        alicePoints[6] = new Point (-2,0);
        alicePoints[7] = new Point (-1,1);
    }

    public static void main(String args[]) throws TimeoutException, DuplicatePartyException, java.lang.ClassNotFoundException, java.io.IOException {
        Party2 p2 = new Party2 (); 
        initialize();
        // initialize some variables
        Point pointOut = new Point (0,0);
        String msgOut = "";
        byte[] outData = new byte[8];
        byte[] inData = new byte[8];
        String msgIn = "";
        Point pointIn = new Point (0,0);
        Point leftMost = new Point (0,0);

        try{
            PlainTCPSocketChannel c = p2.setCommunication();
            Jarvis jv = new Jarvis ();
            /*
             * Alice sends the leftmost point to Bob
             */
            System.out.print("SEND:      ");
            pointOut = jv.getLeftMost(alicePoints);
            msgOut = pointOut.toString();
            outData = msgOut.getBytes(Charset.forName("UTF-8"));
            Message out = new Message (outData);
            c.send(out);
            System.out.println(msgOut);

            /*
             * Alice gets the global leftmost point from Bob
             */
            Message in = (Message) c.receive();
            inData = in.getData();
            if (inData.length>0){
                msgIn = new String(inData, StandardCharsets.UTF_8);
                pointIn = jv.toPoint(msgIn);
                results.add(pointIn);
                leftMost = pointIn;
                System.out.println("RECEIVED: " + msgIn);
            }

            /*
             * Alice finds the next point according to Jarvis march from her
             * collection of points and sends it to Bob. The exchanges continue 
             * until the convex hull is found. 
             */
            while (!finished) {
                /*
                 * Alice sends the next point to Bob.
                 */
                System.out.print("SEND:      ");
                pointOut = jv.getNextPoint(alicePoints, leftMost, pointIn);
                msgOut = pointOut.toString();
                outData = msgOut.getBytes(Charset.forName("UTF-8"));
                out = new Message (outData);
                c.send(out);
                System.out.println(msgOut);

                /*
                 * Alice gets the global next point from Bob.
                 */
                in = (Message) c.receive();
                inData = in.getData();
                if (inData.length>0){
                    msgIn = new String(inData, StandardCharsets.UTF_8);
                    pointIn = jv.toPoint(msgIn);
                    System.out.println("RECEIVED: " + msgIn);
                    /*
                     * If the global next point is the leftmost point, 
                     * we have found the convex hull. 
                     */
                    if (pointIn.getX() == leftMost.getX() && pointIn.getY() == leftMost.getY() ) { 
                        c.close();
                        System.out.println("Convex Hull Found. Connection closed.");
                        finished = true;
                    }else {
                        results.add(pointIn);
                    }
                }
            }
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