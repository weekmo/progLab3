package task1;

public class Student extends Person{
	
	private int matriculation_number;
	private String study_program;
	public Student(){}
	public Student(String firstName,String lastName,int matriculationNumber,String studyProgram){
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setMatriculationNumber(matriculationNumber);
		this.setStudyProgram(studyProgram);
	}
	public void setMatriculationNumber(int value){
		this.matriculation_number=value;
	}
	public int getMatriculationNumber(){
		return this.matriculation_number;
	}
	
	public void setStudyProgram(String value){
		this.study_program=value;
	}
	public String getStudyProgram(){
		return this.study_program;
	}
}
