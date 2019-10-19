/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-01-part-2: shared-memory - part-2-MyClient
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
 *    This program is one of 2 parts of the whole program
 * the goal of the program is to guess values
 * and let the second part to check to the right guess.
 *    The two parts will communicate via POSIX-shared memory.
 *    This part will generate random numbers and replace -1 with it.
 *    If the right value was founded by the server, then this program will
 * display a message and detach shared memory, then it
 * will be terminated.
 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/ipc.h> 
#include <sys/shm.h> 
#include <sys/types.h> 
#include <unistd.h>

// size of the array of values
#define SH_SIZE 32

// control keys
#define IS_FOUNDED_KEY 'f'
#define SERVER_KEY 's'
#define CLIENT_KEY 'c'

// signal register index in the sh-mem array
#define SIGNAL_INDEX SH_SIZE

// default value replaced with non-lucky number
#define NOT_LUCKY -1

// range of random number of guess
#define RANDOM_NUMBER_RANGE 20

int main(int argc, char** argv) {

    // specify keys
    key_t key = 's' * 'h';

    // get id of shared memory
    // add 1 for signal register
    int shmid = shmget(key, (SH_SIZE + 1) * sizeof(int), 0666);
    if (shmid < 0) {
        perror("shmget error - client");
        exit(1);
    }

    // attach buffer memory
    int *mem = shmat(shmid, NULL, 0);  
    if (mem == (int *) - 1) {
        perror("shmat error - client");
        exit(1);
    }

    int luckyNumber, isFounded = 0;

    printf("initial start of the client - no search for the lucky number\n\n");

    while(1) {
        // check signal register, if control with client
        if(mem[SIGNAL_INDEX] == CLIENT_KEY) {

            // check lucky number and replacing with random numbers
            for(int i = 0; i < SH_SIZE; i++) {
                if(mem[i] != NOT_LUCKY) {
                    luckyNumber = mem[i];
                    isFounded = 1;
                } else {
                    mem[i] = ((rand() % RANDOM_NUMBER_RANGE) + RANDOM_NUMBER_RANGE) % RANDOM_NUMBER_RANGE + 1;
                }
            }

            // print values after replacing
            printf("after replacing values of %d to new random numbers.\n", NOT_LUCKY);
            for(int i = 0; i < SH_SIZE; i++) {
                printf("%d, ", mem[i]);
            }
            printf("\n\n");

            // give control to the server
            mem[SIGNAL_INDEX] = SERVER_KEY;

        // if control with server
        } else if(mem[SIGNAL_INDEX] == SERVER_KEY) {
            sleep(1);
        }
        
        // if lucky number is founded
        if(isFounded) {
            printf("The lucky number [%d] is found –the cleint will be terminated.\n\n", luckyNumber);
            mem[SIGNAL_INDEX] = IS_FOUNDED_KEY;
            break;
        }
    }

    //detach from shared memory 
    shmdt(mem);

    printf("the client says goodbye!\n\n");

    return (EXIT_SUCCESS);
}
