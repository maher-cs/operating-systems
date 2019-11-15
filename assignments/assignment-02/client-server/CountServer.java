/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-02-part-2: client-server: the server
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
 * This part starts the server, and creating a thread
 * for each client 
 * 
 */

import java.net.ServerSocket;
import java.net.Socket;

public class CountServer {

    // ## constants ##
    // connection constants
    private static final int PORT = 6100;
    

    // ## class attributes ##
    // connections
    private static ServerSocket serverSocket;
    private static CountClientHandler countClientHandler;
    private static Thread thread;
    // clients counter
    private static int numberOfClients = 0;

    public static void main(String[] args) {

        // start server and waiting for clients
        startServer();

        // establishes connection for each client
        while(true) {
            acceptClient();
        }
        
    }

    // ## private utils methods ##

    // this method is responsable for starting the server
    // and return the server sockets
    private static ServerSocket startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started: waiting for a client");
            return serverSocket;
        } catch (Exception e) {
            System.err.println("Exception: start-server: " + e);
        }
        return null;
    }

    // this method is responsable for accpting one client
    // and start its thread
    private static void acceptClient() {
        try {
            Socket s = serverSocket.accept();
            System.out.println("Client " + (++numberOfClients) + " accepted");
            countClientHandler = new CountClientHandler(s);
            thread = new Thread(countClientHandler);
            thread.start();
        } catch (Exception e) {
            System.err.println("Exception: accept-client: " + e);
        }
    }
}