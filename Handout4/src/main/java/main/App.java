package main;

import task1.Task1Executor;
import task2.Task2Executor;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//**** Task 1 ****
    	new Task1Executor();
    	
    	//**** Task 1 ****
    	new Task2Executor("patients_data.csv");
    	//new Task2Executor().fromScratch("patients_data.csv");
    }
}