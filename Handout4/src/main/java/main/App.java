package main;

import task1.Task1Executor;
import task2.Task2Executor;
import task3.Task3Executor;


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
    	
    	//**** Task 2 ****
    	//There is no BMI greater than 24.
    	new Task2Executor();
    	
    	//**** Task 2 ****
    	new Task3Executor(args);
    }
}