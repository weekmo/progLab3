package task3;


import java.util.Scanner;

//Quiz creator
public class QuestionsCreator {
	
	//Properties for class Questions creator
	QuestionsList questionList;
	Scanner reader;
	
	//Class constructors
	public QuestionsCreator() {
		questionList=new QuestionsList();
		questionList.ReadQuestionsFromFile("quizeData.csv");
	}
	public QuestionsCreator(String fileName) {
		questionList=new QuestionsList();
		questionList.ReadQuestionsFromFile(fileName);
	}
	
	//Method to start quiz game
	public void startQuiz() {
		reader = new Scanner(System.in);
		System.out.println("***** Welcome to the Quiz and good luck ;) ******");
		System.out.println("***** You will get 5 questions *****\n");
		System.out.println("Please select the correct answer by typing the number!\n(if you put wrong number, you will loose)\n");
		Question question;
		boolean result = true;
		int answerNum=0;
		int answersNumbers;
		for(int i =0; i<5;i++) {
			answersNumbers=1;
			question = questionList.getRandomQuestion();
			System.out.println("Question ("+(i+1)+"):\n"+question.getText());
			//System.out.println(question.getCorrectAnswer());
			for(String answer:question.getAnswers()) {
				System.out.println("["+answersNumbers+"]"+answer);
				answersNumbers++;
			}
			try {answerNum=reader.nextInt();}
			catch(Exception e) {System.out.println(e.getMessage());}
			result &= answerNum==question.getCorrectAnswer();
		}
		if(result) {
			System.out.println("Wow, Congratulation :). You Win");
		}
		else {
			System.out.println("Sorry :( , game over");
		}
	}
}
