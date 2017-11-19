package main;

import exercise1.Exercise1Executor;
import exercise2.Exercise2Executor;
import exercise3.Exercise3Executor;

public class App {

	public static void main(String[] args) {
		
		System.out.println("\n\n\t[***** Ecercise 1 ******]\n");
		new Exercise1Executor();
		
		System.out.println("\n\n\t[***** Ecercise 2 ******]\n");
		new Exercise2Executor();
		
		System.out.println("\n\n\t[***** Ecercise 3 ******]");
		new Exercise3Executor();
		
	}

}
