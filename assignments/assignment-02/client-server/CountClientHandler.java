/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-02-part-2: client-server: the server client handler
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 *   group: 3 - no.: 24
 * 
 * program description:
 *    this program allow client user to enter a text
 * and analyse it, count the number of digits, number of
 * letters and number of other chars.
 *    This is a part of 5 parts of the whole program.
 * This part is a thread that handles clients inputs 
 * and process it and return and send the respose to 
 * the client
 * 
 */

import java.io.IOException;
import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class CountClientHandler implements Runnable {

    // ## constants ##
    // console colors
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";

    // ## class attributes ##
    // connections
    private Socket socket;
    private DataInputStream din;
    private ObjectOutputStream oos;
    // identifiers
    private static int numberOfClients = 0;
    private int clientId;

    // constructor
    public CountClientHandler(Socket socket) {
        this.socket = socket;
        this.clientId = (++numberOfClients);
    }

    @Override
    public void run() {
        System.out.println(ANSI_GREEN + "client " + this.clientId + " started" + ANSI_RESET);
        String clientStr = readStringFromClient();
        MessageImpl msg = countChars(clientStr);
        sendMessageToClient(msg);
        closeConnections();
        System.out.println(ANSI_YELLOW + "client " + this.clientId + " finished" + ANSI_RESET);
    }

    // this method is responsable for reading the message
    // from the client
    private String readStringFromClient() {
        try {
            this.din = new DataInputStream(this.socket.getInputStream());
            String str = din.readUTF();
            return str;
        } catch (IOException e) {
            System.err.println("Exception: read-string-from-client: " + e);
        }
        return "";
    }

    // this method is responsable for sending the response 
    // to the client
    private void sendMessageToClient(MessageImpl msg) {
        try {
            this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            oos.writeObject(msg);
            this.oos.close();
        } catch (IOException e) {
            System.err.println("Exception: send-message-to-client: " + e);
            System.out.println(ANSI_RED + "client " + this.clientId + " connection is broken" + ANSI_RESET);
        }
        
    }

    // this method recieves count letters and digits
    // in the client message and return the response object
    private MessageImpl countChars(String str) {
        int letters = 0, digits = 0, others = 0;

        for (char c : str.toCharArray()) {
            if (Character.isLetter(c)) {
                letters++;
            } else if (Character.isDigit(c)) {
                digits++;
            } else {
                others++;
            }
        }

        return new MessageImpl(letters, digits, others);
    }

    // this method close the connections
    private void closeConnections() {
        try {
            this.socket.close();
            this.din.close();
        } catch (Exception e) {
            System.err.println("Exception: close-connections: " + e);
        }
    }
}