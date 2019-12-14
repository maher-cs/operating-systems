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
 * we need a semaphores to control the traffic.
 *    The program simulates two bounds connected with a bridge
 * that have only one lane
 *    This is a part of 6 parts of the whole program.
 * This part simulates the one-lane-bridge that is connecting
 * the two bounds. And simulates two traffic lights with semaphores.
 *    This part uses semaphores to control traffic and avoid
 * deadlock and starvation.
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

    // semaphores
    private Semaphore semMutex, semEast, semWest, semEastWest;

    // initail premit for traffic lights semaphores
    private final int INIT_PREMIT = Integer.MAX_VALUE;

    // reporter stuff
    private int eastCounter;
    private int westCounter;

    // defaul constructor
    public Bridge() {
        this.semMutex = new Semaphore(1);
        this.semEastWest = new Semaphore(1);
        this.semEast = new Semaphore(INIT_PREMIT);
        this.semWest = new Semaphore(INIT_PREMIT);
        this.toggleThread();
        this.reportThread();
    }

    // the public method to simulate a vehicle crossing the bridge
    public void cross(Vehicle vehicle) {

        // classifing the vehicle by its source direction
        try {
            if (vehicle.getDirection().equals("Eastbound")) {
                this.crossEast(vehicle);
            }
            if (vehicle.getDirection().equals("Westbound")) {
                this.crossWest(vehicle);
            }
        } catch (Exception e) {
            System.err.println(e + ": Bridge: cross");
        }
    }

    // helper method simulate crossing bridge with mutex
    private void crossVehicle(Vehicle vehicle) {
        try {
            String colorDir = "";
            if (vehicle.getDirection().equals("Eastbound")) {
                colorDir = ANSI_GREEN + vehicle.getDirection() + ANSI_RESET;
            } else if (vehicle.getDirection().equals("Westbound")) {
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

    // toggler method that create a toggler thread
    // that change the traffic lights of vehicles
    // The toggler thread toggle the direction every while
    private void toggleThread() {

        final int TOGGLE_INTERVAL = 15 * 1000;

        // variable represent the turn of the east bound
        // it is inside the array to have a reference to it
        // so it can be passed to the inner class
        boolean[] turnEast = { false };

        Thread toggler = new Thread() {
            @Override
            public void run() {

                // store the amount of threads avaliable in each semaphore
                int prevEast = 0;
                int prevWest = 0;
                try {
                    while (true) {
                        semMutex.acquire();
                        System.out.println("### changing direction ###");
                        if (turnEast[0]) {
                            prevEast = semEast.drainPermits();
                            semWest.release(prevWest);
                        } else {
                            prevWest = semWest.drainPermits();
                            semEast.release(prevEast);
                        }
                        turnEast[0] = !turnEast[0];
                        semMutex.release();
                        sleep(TOGGLE_INTERVAL);
                    }
                } catch (Exception e) {
                    System.err.println(e + ": Bridge: reporter: run");
                }
            };
        };

        toggler.start();

    }

    // simulate a vehicle comming from the east bound
    // with traffic light simulated by a semaphore
    public void crossEast(Vehicle vehicle) {
        try {
            semEast.acquire();

            this.crossVehicle(vehicle);

            semEast.release();
        } catch (Exception e) {
            System.err.println(e + ": Bridge: cross-east");
        }
    }

    // simulate a vehicle comming from the west bound
    // with traffic light simulated by a semaphore
    public void crossWest(Vehicle vehicle) {
        try {
            semWest.acquire();
            this.crossVehicle(vehicle);
            semWest.release();
        } catch (Exception e) {
            System.err.println(e + ": Bridge: cross-west");
        }
    }

    // #### reproter ####

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