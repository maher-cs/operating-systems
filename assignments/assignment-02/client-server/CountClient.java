/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-02-part-2: client-server: the client
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
 * This part is the interface to the client. It is responsable
 * for interacting with user and sending messages to the server
 * 
 */

import java.io.ObjectInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;


public class CountClient {

    // ## constants ##
    // connection constants
    private static final String SERVER = "localhost";
    private static final int PORT = 6100;

    public static void main(String[] args) {

        try {
            // establish a connection
            Socket s = new Socket(SERVER, PORT);
            
            // open stream to send data to the server
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            // reading data from the user
            String str;
            str = JOptionPane.showInputDialog("Enter a string: ", "");
            if(str == null) {
                str = "";
            }

            // sending data to the server
            dout.writeUTF(str);
            
            // open stream to receive response from the server
            ObjectInputStream din = new ObjectInputStream(s.getInputStream());

            // receiving response form the server
            Message msg = (Message) din.readObject();

            // parsing response object
            String response = "letters: " + msg.getLetterCount() + "\n"
                + "digits: " + msg.getDigitCount() + "\n"
                + "others: " + msg.getOtherCount();

            // display response
            JOptionPane.showMessageDialog(null, response);

            // close connections and streams
            dout.close();
            din.close();
            s.close();

        } catch (Exception e) {
            System.out.println("Exception: Client: " + e);
        }
    }
}