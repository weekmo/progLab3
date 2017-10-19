package task2;
//Import DecimalFormat class
import java.text.DecimalFormat;

public class BmiList {

	public static void main(String[] args) {
		//Object to format the result
		DecimalFormat df =new DecimalFormat("#.#");
		//float variable for height
		float height=1.6f;
		double result;
		//Loop to calculate BMI for weight from 50kg to 80kg
		for(int mass=50;mass<81;mass++){
			//condition to detect current BMI
			if(mass==65)
				System.out.print("Current ------> ");
			//Format and printout the result for BMI
			//optimal 18.5 - 24.9
			result=mass/Math.pow(height, 2);
			if(result>=18.5 && result <=24.9)
				System.out.println("BMI for "+mass+"kg (optimal) : "+df.format(result));
			else
				System.out.println("BMI for "+mass+"kg : "+df.format(result));
		}
	}

}
