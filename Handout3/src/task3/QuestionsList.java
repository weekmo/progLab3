package task3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class QuestionsList extends ArrayList<Question>{
	/**
	 * ArrayList class for questions
	 */
	private static final long serialVersionUID = 1L;
	
	List<Integer> lastSelectedQuestions;
	
	//Class constructor
	public QuestionsList() {
		this.lastSelectedQuestions= new ArrayList<Integer>();
	}
	
	//Method read questions from a CSV file
	public void ReadQuestionsFromFile(String fileName){
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data/"+fileName));
			String line;
			String[] data;
			while((line=reader.readLine()) !=null) {
				data=line.split(",");
				this.add(new Question(data[0], Arrays.copyOfRange(data, 1, 5), Integer.parseInt(data[5])));
			}
			reader.close();
		}
		catch(IOException e) {System.out.println(e.getMessage());}
		catch(Exception e) {System.out.println(e.getMessage());}
	}
	
	//Method to get random question
	public Question getRandomQuestion() {
		try {
			int index = ThreadLocalRandom.current().nextInt(0,this.size()-1);
			while(lastSelectedQuestions.contains(index)) {
				index = ThreadLocalRandom.current().nextInt(0,this.size()-1);
			}
			if(lastSelectedQuestions.size()<5) {
				lastSelectedQuestions.add(index);
			}
			else {
				lastSelectedQuestions.remove(lastSelectedQuestions.size()-1);
				lastSelectedQuestions.add(index);
			}
			return this.get(index);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			System.exit(0);
			return null;
		}
	}
}
