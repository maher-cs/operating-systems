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
 * This part simulates the one-lane-bridge that is connecting
 * the two bounds.
 *    This part uses semaphores to control traffic and avoid
 * deadlock and starvation
 * 
 */

import java.util.concurrent.Semaphore;

public class Bridge {

    // console colors
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RESET = "\u001B[0m";

    // semaphore
    private Semaphore semMutex;

    // reporter stuff
    private int eastCounter;
    private int westCounter;

    // default constructor
    public Bridge() {
        this.semMutex = new Semaphore(1);
        this.reportThread();
    }

    // crossing bridge by vehicle simulation method
    public void cross(Vehicle vehicle) {
        try {
            String colorDir = "";
            if(vehicle.getDirection().equals("Eastbound")) {
                colorDir = ANSI_GREEN + vehicle.getDirection() + ANSI_RESET;
            } else if(vehicle.getDirection().equals("Westbound")) {
                colorDir = ANSI_RED + vehicle.getDirection() + ANSI_RESET;
            }
            // string variable that holds the direction and id of the vehicle
            String identity = colorDir + " vehicle: " + vehicle.getVehicleId() + " ";

            // message that tell the user that there is a vehicle
            // waiting for its turn to cross the bridge
            System.out.println(identity + "is WAITING to cross the bridge.");

            // entry setion, only one vehicle at a the 
            // same time can go after this line
            semMutex.acquire();

            // start critical section

            // start crossing the bridge
            System.out.println(identity + "is " + ANSI_YELLOW + "CROSSING" + ANSI_RESET + " the bridge now.");
            vehicle.crossBridge();
            System.out.println(identity + "has " + ANSI_BLUE + "COMPLETED" + ANSI_RESET + " crossing the bridge.");

            // count the car that crossed the bridge
            // the reporter will use counters in the report
            this.increaseCounters(vehicle);
            // end critical section

            // tell the bridge that the vehicle
            // is leaving and the bridge is ready 
            // to receive a new vehilce
            semMutex.release();
        } catch (Exception e) {
            System.err.println(e + ": Bridge: cross");
        }

    }

    // reporter method that create a reporter thread
    // that report the number of vehicles that have 
    // crossed the bridge for last period of time
    // The reporter thread give a report every while
    private void reportThread() {
        this.initCounters();

        final int REPORT_INTERVAL = 30 * 1000;

        Thread reporter = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        semMutex.acquire();

                        System.out.println("Number of eastbound vehicles passed the bridge: " + eastCounter);
                        System.out.println("Number of westbound vehicles passed the bridge: " + westCounter);
                        initCounters();

                        semMutex.release();
                        sleep(REPORT_INTERVAL);
                    }
                } catch (Exception e) {
                    System.err.println(e + ": Bridge: reporter: run");
                }
            };
        };

        reporter.start();

    }

    // ## reproter helpers private methods ##

    // set counters to initial values
    private void initCounters() {
        this.eastCounter = 0;
        this.westCounter = 0;
    }

    // increase counter for each vehicle
    private void increaseCounters(Vehicle vehicle) {
        switch (vehicle.getDirection()) {
        case "Eastbound":
            this.eastCounter++;
            break;
        case "Westbound":
            this.westCounter++;
            break;
        }
    }
}