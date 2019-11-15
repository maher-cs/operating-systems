/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-02-part-2: client-server: the message interface
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 *   group: 3 - no.: 24
 * 
 * program description:
 *    this program allow client user to enter a text
 * and analyse it, count the number of digits, number of
 * letters and number of other chars.
 *    this is a part of n parts of the whole program.
 * This program is an interface that defines the message
 * interface that is used for communication between the
 * client and the server.
 * 
 */

public interface Message {

    // set the counts for letters, digits, and other characters
    // by the client
    public void setCounts(int letterCount, int digitCount, int otherCount);

    // return number of letters
    public int getLetterCount();

    // return number of digits
    public int getDigitCount();

    // return number of ther characters
    public int getOtherCount();

}