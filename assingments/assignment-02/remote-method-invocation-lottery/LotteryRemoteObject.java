/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-02-part-1: remote-method-invocation-lottery: the lottery remote object
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 *   group: 3 - no.: 24
 * 
 * program description:
 *    this program is a lottery game, it allows a client to
 * guess numbers and enter a lottery to win or loose.
 * It is developed with remote method invokation approach
 *    this is a part of 4 parts of the program, this part handle
 * all the magic and logic of the program.
 * it receive the numbers from the client and generate numbers 
 * randomly, then it check number and give the clein the result.
 * also it validate cleint input.
 * 
 */


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.Collections;

public class LotteryRemoteObject extends UnicastRemoteObject implements LotteryInterface {

    protected LotteryRemoteObject() throws RemoteException {
    }
    private static final long serialVersionUID = 1L;

    // ## constants ##
    // range constants
    private final int START_RANGE = 1;
    private final int END_RANGE = 50;
    // console colors
    private final String ANSI_GREEN = "\u001B[32m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_YELLOW = "\u001B[33m";
    // messages constants
    private final String LOOSE_MSG = "You are a " + ANSI_RED + "LOOSER" + ANSI_RESET + ", try again!";
    private final String WIN_MSG = "You are a " + ANSI_GREEN + "WINNER" + ANSI_RESET + ", your matched number is: ";
    private final String VALID_ERR_MSG = ANSI_YELLOW + "Invalid received numbers" + ANSI_RESET + ", try again!";

    // ## class attributes ##
    private int[] serverLottNumbers;

    // ## public methods ##
    // start lottery method that will be invoked by client
    public String startLottery(int[] clientNumbers) {
        System.out.println();
        int validation = this.validateLottNumbers(clientNumbers);
        if(validation < 0) {
            return this.VALID_ERR_MSG;
        }
        this.generateLottNumbers(clientNumbers.length);
        this.printLottNumbers(this.serverLottNumbers, "server lottery numbers");
        this.printLottNumbers(clientNumbers, "client lottery numbers");
        System.out.println();
        int compareResult = this.compareLottNumbers(clientNumbers);
        if(compareResult < 0) {
            return this.LOOSE_MSG;
        }
        
        return this.WIN_MSG + compareResult;
    }

    // ## private utils method ##

    // validate the numbers sent by the client
    private int validateLottNumbers(int[] clientNumbers) {
        // result codes
        final int ERR_RANGE = -1;
        final int ERR_DUPLICATES = -2;
        final int SUCCESS = 1;

        if(!this.areInRange(clientNumbers))
            return ERR_RANGE;

        if(this.thereIsDuplicates(clientNumbers))
            return ERR_DUPLICATES;

        return SUCCESS;
    }
    
    // gengrate random numbers in range
    private void generateLottNumbers(int numberOfRandoms) {
        // initialize serverLottNumbers array
        this.serverLottNumbers = new int[numberOfRandoms];

        // creating random numbers uniquely by 
        // add each number in the range to a list
        // and suffle it, then take out the first numbers

        // create a list that contains numbers in range
        ArrayList<Integer> range = new ArrayList<Integer>();
        for (int i = this.START_RANGE; i <= this.END_RANGE; i++) {
            range.add(i);
        }
        // shuffle the list of range numbers
        Collections.shuffle(range);
    
        // slice the list to the number of random numbers
        for(int i = 0; i < numberOfRandoms; i++) {
            this.serverLottNumbers[i] = range.get(i);
        }
    }

    // compare client numbers with server generated numbers
    // return the first number that match, else return negative
    private int compareLottNumbers(int[] clientNumbers) {
        final int NO_MATCH = -1;
        final int ERR = -2;

        if(this.serverLottNumbers.length != clientNumbers.length)
            return ERR; 

        for(int i = 0; i < clientNumbers.length; i++) {
            if(this.serverLottNumbers[i] == clientNumbers[i])
                return clientNumbers[i];
        }

        return NO_MATCH;
    }

    // print an array of numbers and return string that printed
    private String printLottNumbers(int[] array, String prefixMsg) {
        String output = prefixMsg + ": ";
        for(int i = 0; i < array.length; i++) {
            output += array[i] + " ";
        }
        System.out.println(output);
        return output;
    }

    // ## validation utils methods ##

    // check if there is duplicates in the array
    // return true if there is duplicates
    private boolean thereIsDuplicates(int[] array) {
        for(int i = 0; i < array.length; i++) {
            for(int j = i+1; j < array.length; j++) {
                if(array[i] == array[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // check if all numbers i range
    // return true if all numbers in range
    private boolean areInRange(int[] array) {
        for(int i = 0; i < array.length; i++) {
            if(array[i] < this.START_RANGE || array[i] > this.END_RANGE) {
                return false;
            }
        }
        return true;
    }
    
}