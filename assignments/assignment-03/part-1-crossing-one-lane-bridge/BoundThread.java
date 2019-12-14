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
 * This part is an abstract class that simulates a Bound
 * that vehicles will cross it in order to reach the bridge
 *    The children classes will define the direction of the bound.
 * 
 */

public abstract class BoundThread extends Thread {

    // ## class attributes ##
    String direction;
    Bridge bridge;

    // ## constructors ##

    // constructor that receive a bridge
    // that the bound is connected to
    public BoundThread(Bridge bridge) {
        this.bridge = bridge;
    }

    // override run method from Thread class
    @Override
    public void run() {
        while(true) {
            this.waitForVehicleArrival();

            // thread that create a vehicle and tell it to cross the
            // bound and then tell it to cross the bridge
            Thread vehicleCreator = new Thread() {
                @Override
                public void run() {
                    Vehicle vehicle = new Vehicle(direction);
                    vehicle.crossBound();
                    bridge.cross(vehicle);
                }
            };
            vehicleCreator.run();
        }
    }

    // method that simulate a bound that is waiting 
    // for a vehicle to arrive
    public void waitForVehicleArrival() {
        this.waitForArrival(0, 10);
    }

    // ## helpers methods ##

    // simulate waiting for arrival
    private void waitForArrival(int startRange, int endRange) {
        int interval = (int) (Math.random() * (endRange + 1) + startRange) * 1000;
        try {
            this.sleep(interval);
        } catch(Exception e) {
            System.err.println(e + ": Vehicle: vehicleCross");
        }
    }
}