/*
 * UQU - CS - Operating Systems
 * pr. Abdulbaset Gaddah
 * subject: assignment-01-part-1: process-creation
 * authors:
 * - MHD Maher Azkoul
 *   438017578
 *   group: 3 - no.: 27
 * - Anas Mohammad Hashem Nowawi
 *   438008655
 *   group: 3 - no.: 25
 * 
 * program description:
 *    The goal of this pogram is to create two children processes
 * each child will iterate and print a text on the screen
 * and the parent will track thier status
 *    Processes is created via fork() system call
 */

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

int main(int argc, char** argv) {

   // children id variables
   pid_t pid1, pid2;

   // create first child
   pid1 = fork();
   
   // first child
   if(pid1 == 0) {

      // get first child pid
      pid_t childPid = getpid();
      printf("First child is born, my pid is: %d\n", childPid);

      // get parent pid
      pid_t parentPid = getppid();
      printf("My parent pid is: %d\n", parentPid);

      // first child iterations
      for(int i = 0; i < 5; ++i) {
         printf("First child executes iteration %d\n", i);
      }

      printf("First child dies quietly\n");

   // parent
   } else {

      // create second child
      pid2 = fork();

      // second child
      if(pid2 == 0) {
         // get second child pid
         pid_t childPid = getpid();
         printf("Second child is born, my pid is: %d\n", childPid);

         // get parent pid
         pid_t parentPid = getppid();
         printf("My parent pid is: %d\n", parentPid);

         // second child iterations
         for(int i = 0; i < 5; ++i) {
            printf("Second child executes iteration %d\n", i);
         }

         printf("Second child dies quietly\n");

      // parent
      } else {
         // get pid of parent
         pid_t parentPid = getpid();
         printf("Parent process is born, my pid is: %d\n", parentPid);

         // wait for all children
         pid_t wpid;
         int wstatus = 50;
         while((wpid = wait(&wstatus)) > 0) {
            printf("Child completed: pid = %d\tstatus = %d\n", wpid, wstatus);
         }
         
         printf("Parent process dies quietly\n");
      }
   }

   return 0;
}
