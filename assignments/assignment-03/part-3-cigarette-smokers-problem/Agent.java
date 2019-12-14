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
 * This part simulates the agent that provide the 
 * complemantary ingredients for the smokers
 * 
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Agent extends Thread {

    // array of ingredients
    private final String[] ingredients = {"PAPER", "MATCHES", "TOBACCO"};

    // the table that connect the smokers and agent
    private Table table;

    private Smoker[] smokers;

    public Semaphore sem;

    public Agent(Table table) {
        this.table = table;
        this.smokers = new Smoker[3];
        this.sem = new Semaphore(1);
    }

    // link the agent and smokers
    public void addSmokers(Smoker... smokers) {
        for(int i = 0; i < this.smokers.length; i++) {
            this.smokers[i] = smokers[i];
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("( Agent ) starts...! generate 3 random numbers of ingredients\n");

            // iterate for specific times
            for(int i = 0; i < 4; i++) {

                // make sure no one is smoking
                this.sem.acquire();

                // generate random pairs and put it on the table
                String[] generated = this.putIngredientsOnTable();
                System.out.println("Agent generated: " + "[" + generated[0] + ", " + generated[1] + "]");

                // select the complamentary smoker
                Smoker smoker = this.selectSmoker(generated);
                System.out.println("Agent is waiting\n");

                // give the smoker a signal to start smoking
                smoker.sem.release();
            }
            // wait for the last smoker
            this.sem.acquire();

            // releas smokers before end
            for(int i = 0; i < smokers.length; i++) {
                smokers[i].sem.release();
            }

            System.out.println("\nAgent ends ..............!");
        } catch(Exception e) {
            System.err.println(e + ": Agent: run");
        }
    }

    // select the complemantary smoker
    private Smoker selectSmoker(String[] ingr) {
        ArrayList<String> ingrList = new ArrayList<String>(Arrays.asList(ingr));
        for(int i = 0; i < smokers.length; i++) {
            if(! ingrList.contains(smokers[i].getIngredient())) {
                return smokers[i];
            }
        }
        return null;
    }

    // generate random pairs and put it on the table
    private String[] putIngredientsOnTable() throws InterruptedException {
        ArrayList<String> ingrList = new ArrayList<String>(Arrays.asList(this.ingredients));
        int range = this.ingredients.length;
        String[] generated = new String[2];
        for(int i = 0; i < generated.length; i++) {
            int index = (int)(Math.random() * range--);
            generated[i] = ingrList.remove(index);
        }
        table.put(generated);
        return generated;
    }
}