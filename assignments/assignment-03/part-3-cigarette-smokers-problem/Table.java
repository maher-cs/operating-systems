/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-03-part-3: cigarette-smokers
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 *   group: 3 - no.: 24
 * 
 * program description:
 *    This program is a solution for the traditional 
 * cigarette smokers problem
 *    This problem simulates 3 smokers with a missing
 * ingredient, and agent that put ingredient for a 
 * smoker each time
 *    This is a part of 4 parts of the whole program.
 * This part simulates the table that the agent puts
 * the ingredients on it and smokers pick form it.
 * 
 */


import java.util.ArrayList;

public class Table extends Thread {
    public static final int BUFFER_SIZE = 2;
    private ArrayList<String> ingredients;

    public Table() {
        this.ingredients = new ArrayList<String>(BUFFER_SIZE);
    }

    // Agent calls this method    
    public void put(String... items) throws InterruptedException  {
        synchronized (this) {

            // add items to the list
            for(int i = 0; i < BUFFER_SIZE; i++) {
                this.ingredients.add(items[i]);
            }
                        
            notify();
        }
    }

    // Smoker calls this method
    public String[] take() throws InterruptedException {
        String[] items = new String[BUFFER_SIZE];
        synchronized (this) {

            // remove items from the list
            for(int i = 0; i < ingredients.size()+i; i++) {
                items[i] = this.ingredients.remove(0);
            }
            
            notify(); // notify producer
        }
        return items;
    }
}