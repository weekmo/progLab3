package task1;

//Student class inherited from Person class 
public class Student extends Person{
	
	//Two properties for matriculation number
	//and study program respectively
	private int matriculationNumber;
	private String studyProgram;
	
	//Constructor
	public Student(){}
	
	//constructor with parameters
	public Student(String firstName,String lastName,int matriculationNumber,String studyProgram){
		super(firstName,lastName);
		this.matriculationNumber=matriculationNumber;
		this.studyProgram=studyProgram;
	}
	
	//Set matriculation number method
	public void setMatriculationNumber(int matriculationNumber){
		this.matriculationNumber=matriculationNumber;
	}
	
	//Get matriculation number method
	public int getMatriculationNumber(){
		return this.matriculationNumber;
	}
	
	//Set study program
	public void setStudyProgram(String studyProgram){
		this.studyProgram=studyProgram;
	}
	
	//Get study program
	public String getStudyProgram(){
		return this.studyProgram;
	}
}
