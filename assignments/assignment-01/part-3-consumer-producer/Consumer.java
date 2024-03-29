/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-01-part-3: producer-consumer - part-2-Consumer
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 *   s438017578@st.uqu.edu.sa
 *   group: 3 - no.: 27
 * - Anas Mohammad Hashem Nowawi
 *   438008655
 *   s438008655@st.uqu.edu.sa
 *   group: 3 - no.: 25
 * 
 * Date: 17-10-2019
 * program description:
 *    This program is one of 3 parts of the whole program
 * the goal of the program is to generate random numbers and 
 * and consume it by another program via shared memory (queue).
 *    This program will consumes the values form queue.
 */

import java.util.concurrent.LinkedBlockingQueue;

public class Consumer extends Thread {

    // queue that is filled by the producer
    private final LinkedBlockingQueue<Integer> queue;

    // poisin bill signal to terminate consumer
    private final int POISIN_BILL = -1;

    public Consumer(LinkedBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

        try {
            // consumes the data in the queue
            while (true) {
                Integer next_consumed = queue.take();

                // end loop if "poison bill" occur
                if(next_consumed == POISIN_BILL) {
                    break;
                }

                // display data
                if (next_consumed != null) {
                    System.out.println("next consumed = " + next_consumed);
                }
            }
            System.out.println("Consumer died quietly");
        } catch (InterruptedException D) {
            System.err.println("Consumer interrupted");
        }

    }
}
