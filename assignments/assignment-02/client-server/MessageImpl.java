/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-02-part-2: client-server: the message class
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
 * This program is the blueprint of the actual messages
 * the will be sent as a response to the client.
 * 
 */

import java.io.Serializable;

public class MessageImpl implements Message, Serializable {

    // // serialization id from Serializable interface
    // private static final long serialVersionUID = 1L;

    // ## class attributes ##
    private int letterCount;
    private int digitCount;
    private int otherCount;

    // ## constructors ##

    // default constructor
    public MessageImpl() {
    }

    // constructor with parameters
    public MessageImpl(int letterCount, int digitCount, int otherCount) {
        this.setCounts(letterCount, digitCount, otherCount);
    }

    // ## setters ##

    // set letter count with validation
    public void setLetterCount(int letterCount) {
        if(!this.isValidCount(letterCount)) {
            return;
        }
        this.letterCount = letterCount;
    }

    // set digit count with validation
    public void setDigitCount(int digitCount) {
        if(!this.isValidCount(letterCount)) {
            return;
        }
        this.digitCount = digitCount;
    }

    // set other count with validation
    public void setOtherCount(int otherCount) {
        if(!this.isValidCount(letterCount)) {
            return;
        }
        this.otherCount = otherCount;
    }

    // ## Message interface methods ##

    // set counts variables
    @Override
    public void setCounts(int letterCount, int digitCount, int otherCount) {
        this.setLetterCount(letterCount);
        this.setDigitCount(digitCount);
        this.setOtherCount(otherCount);
    }

    // get letter count
    @Override
    public int getLetterCount() {
        return this.letterCount;
    }

    // get digit count
    @Override
    public int getDigitCount() {
        return this.digitCount;
    }

    // get other count
    @Override
    public int getOtherCount() {
        return this.otherCount;
    }

    // ## private utils methods ##

    // validate the count number
    // return true if valid
    private boolean isValidCount(int count) {
        // count cannot be less than zero
        if(count < 0) {
            return false;
        }
        return true;
    }
    
}