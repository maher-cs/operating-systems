/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-01-part-3: producer-consumer - part-1-Producer
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
 *    This program will produces the values to the queue.
 */

import java.util.concurrent.LinkedBlockingQueue;;
public class Producer extends Thread {

    private final LinkedBlockingQueue<Integer> queue;
    private final int SIZE = 50;

    public Producer(LinkedBlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for(int i = 0; i < SIZE; i++) {
                Integer next_produced = (int)(Math.random() * 200) + 1;
                queue.put(next_produced);
                System.out.println("next produced = " + next_produced);
            }
            queue.put(-1);
            System.out.println("Producer died quietly");
        } catch (InterruptedException D) {   }
    }
}
