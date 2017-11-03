package task2;

import java.util.ArrayList;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import task1.*;

public class Task2Executor {
	public Task2Executor() {}
	
	public Task2Executor(String fileName) {
		ArrayList<Participant> patients = Task1Executor.readParticipantsCSVFile(fileName);
		DescriptiveStatistics weights=new DescriptiveStatistics();
		DescriptiveStatistics heights=new DescriptiveStatistics();
		double bmi;
		for(Participant p:patients) {
			weights.addValue(p.getWeight());
			heights.addValue(p.getHeight());
			bmi=p.getWeight()/Math.pow(p.getHeight()/100,2);
			//System.out.println(bmi);
			if(bmi>24) {
				System.out.println(p.getFirstName()+" "+p.getLastName());
			}
		}
		System.out.println("Average weight: "+weights.getMean());
		System.out.println("Average height: "+heights.getMean());
		System.out.println("-------------------");
		System.out.println("Variance of weight: "+weights.getPopulationVariance());
		System.out.println("Variance of height: "+heights.getPopulationVariance());
		
		//fromScratch(fileName);
	}
	
	public void fromScratch(String fileName) {
		ArrayList<Participant> patients = Task1Executor.readParticipantsCSVFile(fileName);
		//double[] weights,heights;
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
