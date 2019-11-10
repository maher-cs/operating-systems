/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-02-part-1: remote-method-invocation-lottery: the lottery client app
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
 * responsible for creating the client app that interact with 
 * the user and communicate with the server.
 * 
 */

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import java.util.Scanner;

public class LotteryCleint {

    // ## constants ##
    // connection constants
    private static int PORT = 6100;
    private static String SERVER = "localhost";
    private static String BIND_KEY = "lottery";
    // range constants
    private static final int START_RANGE = 1;
    private static final int END_RANGE = 50;
    // console colors
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    // messages constants
    private static final String VALID_ERR_MSG = ANSI_YELLOW + "Invalid received numbers" + ANSI_RESET + ", try again!";

    // ## class attributes ##
    // remote object
    private static LotteryInterface lottery;
    // scanner
    private static Scanner console = new Scanner(System.in);;

    public static void main(String[] args) {

        try {
            // connecting to server
            Registry reg = LocateRegistry.getRegistry(SERVER, PORT);
            // search for method by binding key
            lottery = (LotteryInterface) reg.lookup(BIND_KEY);

            int inputSize = getInputSize();
            int[] guessedNumbers = getInputs(inputSize);
            String response = lottery.startLottery(guessedNumbers);
            response = handleResponse(response, inputSize);
            
        } catch (NotBoundException | RemoteException e) {
            System.out.println("Exception: " + e);
        }

        // close scanner
        console.close();
    }

    // ## private utils method ##

    // prompte the user to input the number or inputs 
    // that will be entered and return that number
    private static int getInputSize() {
        int result;        
        System.out.print("How many numbers you will be selecting? ");
        result = console.nextInt();
        int range = END_RANGE - START_RANGE + 1;

        // if invalid
        while(result < 1 || result > range) {
            System.out.print(ANSI_RED + "invalid number" + ANSI_RESET + ", please enter between 1 and " + range + ": ");
            result = console.nextInt();
        }

        return result;
    }

    // prompte the user to input the numbers
    // return an array contains these numbers
    private static int[] getInputs(int inputSize) {
        // if invalid
        if(inputSize < 1) {
            return null;
        }

        int[] result = new int[inputSize];
        for(int i = 0; i < inputSize; i++) {
            System.out.print("Enter number " + (i+1) + ": ");
            result[i] = console.nextInt();
        }
        return result;
    }

    // handle the response:
    // if win, or loose: show the response
    // if invalid input: get the input form the user again
    private static String handleResponse(String previosResponse, int inputSize) {
        showResponse(previosResponse);
        String newResponse = previosResponse;
        while(newResponse.equals(VALID_ERR_MSG)) {
            int[] guessedNumbers = getInputs(inputSize);
            try {
                newResponse = lottery.startLottery(guessedNumbers);
                showResponse(newResponse);
                
            } catch (RemoteException e) {
                System.out.println("Exception: " + e);
            }
        }
        return newResponse;
    }

    private static void showResponse(String response) {
        System.out.println(response);
    }
}