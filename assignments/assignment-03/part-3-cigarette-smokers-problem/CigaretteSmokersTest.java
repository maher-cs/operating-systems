/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-03-part-3: cigarette-smokers
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 * - Anas Nawawi
 *   438008655
 * 
 * program description:
 *    This program is a solution for the traditional 
 * cigarette smokers problem
 *    This problem simulates 3 smokers with a missing
 * ingredient, and agent that put ingredient for a 
 * smoker each time
 *    This is a part of 4 parts of the whole program.
 * This part run and connect all threads togother
 * 
 */

public class CigaretteSmokersTest {
    public static void main(String[] args) throws InterruptedException {
        
        Table table = new Table();
        Agent agent = new Agent(table);
        Smoker papersSmoker = new Smoker("PAPER", agent, table);
        Smoker matchesSmoker = new Smoker("MATCHES", agent, table);
        Smoker tobaccoSmoker = new Smoker("TOBACCO", agent, table);
        agent.addSmokers(papersSmoker, matchesSmoker, tobaccoSmoker);

        // start all threads
        agent.start();
        papersSmoker.start();
        matchesSmoker.start();
        tobaccoSmoker.start();

        // wait for threads befor ending main thread
        agent.join();
        papersSmoker.join();
        matchesSmoker.join();
        tobaccoSmoker.join();
    }
}