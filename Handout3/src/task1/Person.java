package task1;

//Person class
public class Person {
	
	//Two properties for name
	private String lastName;
	private String firstName;
	
	//Constructor method
	public Person() {}
	
	//Constructor method with parameters
	public Person(String firstName,String lastName) {
		this.firstName=firstName;
		this.lastName=lastName;
	}
	
	//Method to set last name
	public void setLastName(String lastName){
		this.lastName=lastName;
	}
	
	//Method to get last name
	public String getLastName(){
		return this.lastName;
	}
	
	//Method to set fist name
	public void setFirstName(String firstName){
		this.firstName=firstName;
	}
	
	//Method to get fist name
	public String getFirstName(){
		return this.firstName;
	}
}
