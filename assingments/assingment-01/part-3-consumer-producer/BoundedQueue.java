/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-01-part-3: producer-consumer - part-2-BoundedQueue
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
 *    This program will connect the producer and consumer
 */

import java.util.concurrent.LinkedBlockingQueue;
public class BoundedQueue {

    public static void main(String[] args) throws InterruptedException {

        // queue - the shared memory
        LinkedBlockingQueue<Integer> shm = new LinkedBlockingQueue<Integer>();

        // create producer
        Producer prod = new Producer(shm);
        // create consumer
        Consumer cons = new Consumer(shm);

        // start producer & consumer threads
        prod.start();
        cons.start();

        // wait producer & consumer threads
        prod.join();
        cons.join();
    }
}
