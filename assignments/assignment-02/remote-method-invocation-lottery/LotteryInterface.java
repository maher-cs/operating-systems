/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-02-part-1: remote-method-invocation-lottery: the interface
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 *   group: 3 - no.: 24
 * 
 * program description:
 *    this program is a lottery game, it allows a client to
 * guess numbers and enter a lottery to win or loose.
 * It is developed with remote method invokation approach
 *    this is a part of 4 parts of the program, this part
 * is just an interface to make communication policy between
 * client and server
 * 
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LotteryInterface extends Remote {

    // purpose: the method that can be called from the client that will 
    //          start the lottery game
    // input: array of numbers that the client guessed and entered
    // return: the result message (win, loose, invalid ...)
    // throws: general communication-related exceptions that may occur
    //          during the execution of a remote method call.
    public String startLottery(int[] guessedNumbers) throws RemoteException;
}