/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-03-part-1: crossing-one-lane-bridge
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 *   group: 3 - no.: 24
 * 
 * program description:
 *    This program simulates a traffic senario where 
 * we need a semaphores to control the traffic.
 *    The program simulates two bounds connected with a bridge
 * that have only one lane
 *    This is a part of 6 parts of the whole program.
 * This part run each part of the simulation and connect
 * them (bridge, two bounds).
 * 
 */

public class OneLaneBridgeTester {
    public static void main(String[] args) throws InterruptedException {
        Bridge bridge = new Bridge();
        EastboundThread eastboundThread = new EastboundThread(bridge);
        WestboundThread westboundThread = new WestboundThread(bridge);

        // start bounds threads
        eastboundThread.start();
        westboundThread.start();

        // waiting bounds theads before ending the main thread
        eastboundThread.join();
        westboundThread.join();
    }
}