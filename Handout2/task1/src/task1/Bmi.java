//Package name for the current class
package task1;

import java.text.DecimalFormat;

//Creation of the class Bmi
public class Bmi {
	//Main function of class Bmi
	public static void main(String[] args) {
		//Object to format the result
		DecimalFormat df =new DecimalFormat("#.#");
		//Float variable for first height
		float height1=1.80f;
		//Float variable for first mass
		float mass1=73;
		
		//Float variable for second height
		float height2=1.70f;
		//Float variable for second mass
		float mass2=80;
		//print out the rounded result of the first BMI
		System.out.println("BMI 1: "+df.format(mass1/Math.pow(height1, 2)));
		//print out the rounded result of the second BMI
		System.out.println("BMI 2: "+df.format(mass2/Math.pow(height2, 2)));
	}

}
