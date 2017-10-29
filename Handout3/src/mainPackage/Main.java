package mainPackage;

import task1.ExecuteTask1;
import task2.ExecuteTask2;
import task3.QuestionsCreator;

public class Main {

	public static void main(String[] args) {
		// ***** Task 1 *****
		new ExecuteTask1();
		
		// ***** Task 2 *****
		new ExecuteTask2();
		
		// ***** Task 3 *****
		QuestionsCreator q = new QuestionsCreator();
		q.startQuiz();
	}
}