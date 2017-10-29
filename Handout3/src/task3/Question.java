package task3;
//Question class
public class Question {
	
	//Properties for the class
	private String text;
	private String[] answers;
	private int correctAnswer;
	
	//Class constructor takes question, answers (array) and correct answer
	// as parameters
	public Question(String text,String[] answers,int correctAnswer) {
		assert answers.length == 4;
		this.setText(text);
		this.setAnswers(answers);
		this.setCorrectAnswer(correctAnswer);
	}
	
	//getters and setters for class properties
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String[] getAnswers() {
		return answers;
	}
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}
	public int getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
}
