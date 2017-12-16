package exercise2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Class contains solution for Handout8-Exercise9 part 3&4
 * The class reads streamed data by class Exercise2 and run statistical
 * calculation to print out Min, Max and average for cells
 * to run the application, the user has to run Exercise2 in command line
 * and pipe the result to this class.
 * @author laplace
 *
 */
public class  ThirdApplication{

	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String text;
		double value;
		DescriptiveStatistics stats=new DescriptiveStatistics();
		try {
			while((text=reader.readLine())!=null) {
				value = Double.parseDouble(text.split(":")[1]);
				stats.addValue(value);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("\t--- Statistical Analysis of cells---");
		System.out.println("Min: "+stats.getMin());
		System.out.println("Max :"+stats.getMax());
		System.out.println("Average :"+stats.getMean());
	}

}
