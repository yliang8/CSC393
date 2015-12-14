/*
--------------------------------------------------------------
Party2.java:     
    Party2 represents the receiver of message in a Symmetric 
    Encryption Scheme. Party1 and Party2 interchange data 
    over a Plain TCP 
    Socket Channel. 

    For CSC 393, Fall 2015
    YUAN LIANG
--------------------------------------------------------------
*/


import edu.biu.scapi.midLayer.symmetricCrypto.encryption.*;
import javax.crypto.SecretKey;
import edu.biu.scapi.midLayer.ciphertext.*;
import edu.biu.scapi.midLayer.plaintext.*;
import edu.biu.scapi.exceptions.FactoriesException;
import java.security.InvalidKeyException;
import java.util.Arrays;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeoutException;
import edu.biu.scapi.comm.Channel;
import static edu.biu.scapi.comm.twoPartyComm.PlainTCPSocketChannel.Message;
import java.util.List;
import java.util.Map;
import edu.biu.scapi.exceptions.DuplicatePartyException;
import edu.biu.scapi.comm.twoPartyComm.*;

public class Party2{

    public static void main(String args[]) throws FactoriesException, InvalidKeyException, TimeoutException, DuplicatePartyException, java.lang.ClassNotFoundException, java.io.IOException {
        Party2 p2 = new Party2 ();
        try{
            // Establishes a plain TCP socket channel 
            PlainTCPSocketChannel c = p2.setCommunication();
            // Receives the message that encapsulates the cipher text from the sender
            Message s = (Message) c.receive();
            byte[] cipher_bytearray =  s.getData();
            // Get the plain text
            p2.getPlainText(cipher_bytearray);
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


    public void getPlainText(byte[] cipher_bytearray) throws FactoriesException, InvalidKeyException {
        try {
            // Create the same SymmetricEnc object as the sender’s encryption object, and set the key
            SymmetricEnc decryptor = new ScCTREncRandomIV("AES");
            // Generate a SecretKey using the created object and set it
            SecretKey key = decryptor.generateKey(128);
            decryptor.setKey(key);
            // Create a IVCiphertext a object that implements Symmetric Ciphertext Interface and a byte array as arguments
            IVCiphertext ciphertext = new IVCiphertext(new ByteArraySymCiphertext(cipher_bytearray), cipher_bytearray);
            // Decrypt the message
            ByteArrayPlaintext plaintext = (ByteArrayPlaintext) decryptor.decrypt(ciphertext);
            byte[] plaintext_bytearray = plaintext.getText();
            System.out.println(Arrays.toString(plaintext_bytearray));
        } catch (FactoriesException e) {
            throw e;
        } catch (InvalidKeyException e) {
            throw e;
        }
    }
}