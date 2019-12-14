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
 * This part simulates a vehicle that can cross a bound 
 * or a bridge.
 * 
 */


public class Vehicle extends Thread {

    // class static variables
    private static int counter = 0;

    // class attributes
    private int vehicleId;
    private String direction;

    public Vehicle(String direction) {
        this.vehicleId = ++counter;
        this.direction = direction;
    }

    // ## setters ##
    public void setDirection(String direction) {
        this.direction = direction;
    }

    // ## getters ##
    public int getVehicleId() {
        return this.vehicleId;
    }

    public String getDirection() {
        return this.direction;
    }

    @Override
    public void run() {
       
    }

    // simulates crossing a bridge
    public void crossBridge() {
        this.vehicleCross(0, 5);
    }

    // simulates crossing a bound
    public void crossBound() {
        this.vehicleCross(0, 5);
    }

    // ## helpers methods ##

    // method that simulates crossing some road
    private void vehicleCross(int startRange, int endRange) {
        int interval = (int) (Math.random() * (endRange + 1) + startRange) * 1000;
        try {
            this.sleep(interval);
        } catch(Exception e) {
            System.err.println(e + ": Vehicle: vehicleCross");
        }
    }
}