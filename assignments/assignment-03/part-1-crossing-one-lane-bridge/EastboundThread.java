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
 * This part simulates the east-bound.
 *    This part extends the bound class and define 
 * the east direction as its direction.
 * 
 */

public class EastboundThread extends BoundThread {

    public EastboundThread(Bridge bridge) {
        super(bridge);
        this.direction = "Eastbound";
    }

}