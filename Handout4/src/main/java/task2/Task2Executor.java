//Package name
package task2;
//Import libraries
import java.util.ArrayList;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import task1.*;

/*
 * Beginning of the class
 * It is a class to execute task2 of handout4 programming lab3
 */
public class Task2Executor {
	//fileName property
	private String fileName;
	//Class constructors
	public Task2Executor(String fileName) {this.fileName=fileName;}
	
	//Class constructors that use statistic library from Apache
	public Task2Executor() {
		ArrayList<Participant> patients = Task1Executor.readParticipantsCSVFile("patients_data.csv");
		DescriptiveStatistics weights=new DescriptiveStatistics();
		DescriptiveStatistics heights=new DescriptiveStatistics();
		double bmi;
		System.out.println("\n*** Participants with BMI greater than 24 ***");
		for(Participant p:patients) {
			weights.addValue(p.getWeight());
			heights.addValue(p.getHeight());
			bmi=p.getWeight()/Math.pow((p.getHeight()/100),2);
			//System.out.println(bmi);
			if(bmi>24) {
				System.out.println(p.getFirstName()+" "+p.getLastName()+": "+bmi);
			}
		}
		System.out.println("\n*** Averages ***");
		System.out.println("Average weight: "+weights.getMean());
		System.out.println("Average height: "+heights.getMean());
		System.out.println("\n*** Variances ***");
		System.out.println("Variance of weight: "+weights.getPopulationVariance());
		System.out.println("Variance of height: "+heights.getPopulationVariance());
	}
	
	//Function to calculate mean and variance without using library
	public void fromScratch() {
		ArrayList<Participant> patients = Task1Executor.readParticipantsCSVFile(this.fileName);
		int counter=0;
		double sumWeight=0;
		double sumHeight=0;
		for(Participant p:patients) {
			sumWeight+=p.getWeight();
			sumHeight+=p.getHeight();
			counter++;
		}
		System.out.println("Average weight: "+sumWeight/counter);
		System.out.println("Average height: "+sumHeight/counter);
		double tempWeight=0;
		double tempHeight=0;
		for(Participant p:patients) {
			tempWeight+=Math.pow(p.getWeight()-sumWeight/counter,2);
			tempHeight+=Math.pow(p.getHeight()-sumHeight/counter,2);
		}
		System.out.println("-------------------");
		System.out.println("Variance of weight: "+tempWeight/counter);
		System.out.println("Variance of height: "+tempHeight/counter);
	}
}
