package task2;

import java.util.ArrayList;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import task1.*;

public class Task2Executor {
	public Task2Executor() {
		ArrayList<Participant> patients = Task1Executor.readParticipantsCSVFile("patients_data.csv");
		DescriptiveStatistics weights=new DescriptiveStatistics();
		DescriptiveStatistics height=new DescriptiveStatistics();
		for(Participant p:patients) {
			weights.addValue(p.getWeight());
			height.addValue(p.getHeight());
		}
		System.out.println(weights.getN());
	}
}
