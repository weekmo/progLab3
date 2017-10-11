package task3;

import java.text.DecimalFormat;
import java.util.Scanner;

public class BmiInteractive {

	public static void main(String[] args) {
		//Object to format the result
		DecimalFormat df =new DecimalFormat("#.#");
		//Suppress Warnings
		@SuppressWarnings("resource")
		//Create Scanner object to read values from the user
		Scanner reader = new Scanner(System.in);
		//Print a message for the user to enter his/her mass
		System.out.println("Please enter your weight (kg):");
		//Read the mass value entered by the user
		float mass = reader.nextFloat();
		//Print a message for the user to enter his/her height
		System.out.println("Please enter your height (m):");
		//Read the height value entered by the user
		float height=reader.nextFloat();
		//Format and printout the result for BMI
		System.out.println("Your BMI is: "+df.format(mass/Math.pow(height, 2)));
	}

}
