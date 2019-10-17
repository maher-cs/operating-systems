/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-01-part-3: producer-consumer - part-2-Consumer
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 *   group: 3 - no.: 27
 * - Anas Mohammad Hashem Nawawi
 *   438008655
 *   group: 3 - no.: 25
 * 
 * program description:
 *    This program is one of 3 parts of the whole program
 * the goal of the program is to generate random numbers and 
 * and consume it by another program via shared memory (queue).
 *    This program will consumes the values form queue.
 */

import java.util.concurrent.LinkedBlockingQueue;

public class Consumer extends Thread {

    private final LinkedBlockingQueue<Integer> queue;

    public Consumer(LinkedBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            while (true) {
                Integer next_consumed = queue.take();
                if(next_consumed == -1) {
                    break;
                }
                if (next_consumed != null) {
                    System.out.println("next consumed = " + next_consumed);
                }
            }
            System.out.println("Consumer died quietly");
        } catch (InterruptedException D) {
            System.out.println("Consumer interrupted");
        }

    }
}
