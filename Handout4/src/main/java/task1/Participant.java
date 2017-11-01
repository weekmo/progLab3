package task1;

public class Participant {
	private String id;
	private String lastName;
	private String firstName;
	private String dateOfBirth;
	private double weight;
	private double height;
	private double iq;
	
	public Participant(String id,String lastName,String firstName,String dateOfBirth) {
		this.lastName=lastName;
		this.firstName=firstName;
		this.dateOfBirth=dateOfBirth;
		this.id=id;
	}
	public Participant(String id,String lastName,String firstName,String dateOfBirth,
			double weight, double height, double iq) {
		this(id,lastName, firstName, dateOfBirth);
		this.weight=weight;
		this.height=height;
		this.iq=iq;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id= id;
	}
	
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public double getIq() {
		return iq;
	}
	public void setIq(double iq) {
		this.iq = iq;
	}
}
