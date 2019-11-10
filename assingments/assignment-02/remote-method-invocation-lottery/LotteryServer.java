/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-02-part-1: remote-method-invocation-lottery: the lottery server
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 *   group: 3 - no.: 24
 * 
 * program description:
 *    this program is a lottery game, it allows a client to
 * guess numbers and enter a lottery to win or loose.
 * It is developed with remote method invokation approach
 *    this is a part of 4 parts of the program, this part is 
 * responsible for creating the server and binding the lottery
 * remote object
 * 
 */

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class LotteryServer {

    // ## constants ##
    // connection constants
    private static final String BIND_KEY = "lottery";
    private static final int PORT = 6100;

    public static void main(String[] args) {
        try {
            // creating the registry
            Registry reg = LocateRegistry.createRegistry(PORT);
            // binding the remote object to the key
            reg.rebind(BIND_KEY, new LotteryRemoteObject());

            System.out.println("Server is ready\n");

        } catch ( RemoteException e ) {
            System.out.println("Server exception: " + e);
        }
    }
}