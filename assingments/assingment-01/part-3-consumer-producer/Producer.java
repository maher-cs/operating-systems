/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-01-part-3: producer-consumer - part-1-Producer
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
 *    This program will produces the values to the queue.
 */

import java.util.concurrent.LinkedBlockingQueue;;
public class Producer extends Thread {

    // the queue that is consumed by the consumer
    private final LinkedBlockingQueue<Integer> queue;

    // the size of the queue
    private final int SIZE = 20;

    // producer constructor
    public Producer(LinkedBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            // fill queue with random number in range 1 to 200
            for(int i = 0; i < SIZE; i++) {
                Integer next_produced = (int)(Math.random() * 200) + 1;
                queue.put(next_produced);
                System.out.println("next produced = " + next_produced);
            }
            
            // send a signal "poison bill" to terminate the consumer
            queue.put(-1);

            System.out.println("Producer died quietly");
        } catch (InterruptedException D) {
            System.err.println("Producer interrupted");
        }
    }
}
