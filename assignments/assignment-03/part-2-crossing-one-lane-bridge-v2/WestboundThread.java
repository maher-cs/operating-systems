/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-03-part-2: crossing-one-lane-bridge-v2
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 * - Anas Nawawi
 *   438008655
 * 
 * program description:
 *    This program simulates a traffic senario where 
 * we need two semaphores to control the traffic.
 *    The program simulates two bounds connected with a bridge
 * that have only one lane
 *    This is a part of 6 parts of the whole program.
 * This part simulates the east-bound.
 *    This part extends the bound class and define 
 * the west direction as its direction.
 * 
 */


public class WestboundThread extends BoundThread {

    public WestboundThread(Bridge bridge) {
        super(bridge);
        this.direction = "Westbound";
    }

}