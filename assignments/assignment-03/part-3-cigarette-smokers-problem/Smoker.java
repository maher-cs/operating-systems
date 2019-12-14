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
 * This part simulates a smoker
 * 
 */


import java.util.concurrent.Semaphore;

public class Smoker extends Thread {

    // smoker ingredient
    private String ingredient;

    // the table that connect the smokers and agent
    private Table table;

    private Agent agent;

    // count number of smoking times
    private int counter;

    // to receive a signal from the agent
    public Semaphore sem;

    public Smoker(String ingr, Agent agent, Table table) {
        this.ingredient = ingr;
        this.agent = agent;
        this.table = table;
        this.counter = 0;
        this.sem = new Semaphore(0);
    }

    // ingredient getter
    public String getIngredient() {
        return this.ingredient;
    }

    @Override
    public void run() {
        String identity = "( " + this.getIngredient() + " ) smoker";
        try {
            System.out.println(identity + " has started ...!");
            while(agent.isAlive()) {

                // wait a signal from the agent
                this.sem.acquire();

                // take the complemantary ingredient form the table
                String[] ingr = this.table.take();

                // if no items, break
                if(ingr[0] == null) {
                    break;
                }
                System.out.println(identity + " has " + "[" + ingr[0] + ", " + ingr[1] + "]");

                // start smoking
                System.out.println("Preparing cigarette");
                this.smoke();
                this.agent.sem.release();
            }
            System.out.println("\n" + identity + " smoked " + this.counter + " times ... ends ..!");
        } catch(Exception e) {
            System.err.println(e + ": smoker: run");
        }
    }

    // simulate smoking
    private void smoke() throws InterruptedException {
        System.out.println("Smoking ...........!\n");
        this.counter++;
        sleep(1000);
    }
}