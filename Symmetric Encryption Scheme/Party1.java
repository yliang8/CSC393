/*
--------------------------------------------------------------
Party1.java:     
    Party1 represents the sender of message in a Symmetric 
    Encryption Scheme. Party1 and Party2 interchange data over a Plain TCP 
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



public class Party1 {

	public static void main(String args[]) throws FactoriesException, InvalidKeyException, TimeoutException, DuplicatePartyException, java.lang.ClassNotFoundException, java.io.IOException {
        Party1 p1 = new Party1 ();
        try{
        	// Establishes a plain TCP socket channel 
            PlainTCPSocketChannel c = p1.setCommunication();
            // Get the byte array variable of the cipher text
            byte[] cipher_bytearray =  p1.getCipher();
            Message msg = new Message (cipher_bytearray);
            // Sends the message that encapsulates the cipher text over to the other party
            c.send(msg);
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

    // A method that returns the byte array variable of the cipher text
    public byte[] getCipher () throws FactoriesException, InvalidKeyException {
		byte[] cipher_bytearray = null;
		try {
			// Create an encryption object
			SymmetricEnc encryptor = new ScCTREncRandomIV("AES");
			// Generate a SecretKey using the created object and set it.
			SecretKey key = encryptor.generateKey(128);
			encryptor.setKey(key);
			// Get a plaintext to encrypt
			byte[] text = new byte [2];
			text[0] = (byte) 0;
	        text[1] = (byte) 1;
			Plaintext plaintext = new ByteArrayPlaintext(text);
			// Encrypt the plaintext and get the byte array variable of the cipher text
			IVCiphertext cipher = (IVCiphertext) encryptor.encrypt(plaintext);
			cipher_bytearray = cipher.getIv();
			System.out.println(Arrays.toString(text));
		} catch (FactoriesException e) {
			throw e;
		} catch (InvalidKeyException e) {
			throw e;
		}
		return cipher_bytearray;
	}

}
