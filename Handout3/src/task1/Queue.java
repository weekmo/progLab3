package task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Queue implements Iterable<Person>{
	//create empty list of type Person
	private ArrayList<Person> people;
	
	//Constructor
	public Queue() {people = new ArrayList<Person>();}
	
	//Push a new item to the Queue
	public void pushItemToQueue(Person newPerson){
		this.people.add(newPerson);
	}
	
	//Pop an item from the Queue
	public Person popItemFromQueue(){
		return this.people.remove(people.size()-1);
	}
	
	//Iterate the Queue
	@Override
	public Iterator<Person> iterator() {
		return this.people.iterator();
	}
	
	//Save items in the Queue as CSV file by passing the file name
	public void saveQueueAsCSV(String fileName){
		@SuppressWarnings("rawtypes")
		Iterator peopleIter = this.iterator();
		if(!peopleIter.hasNext()){
			System.out.println("The queue is empty!");
			return;
		}
		try{
			FileWriter fileWriter = new FileWriter("data/"+fileName,false);
			//fileWriter.write("\"Last Name\",\"First Name\",\"Matriculation Number\",\"Study Program\"\n");
			Student student;
			String data;
			while(peopleIter.hasNext()){
				student=(Student)peopleIter.next();
				
				data="\""+student.getLastName()+"\",\""+student.getFirstName()+"\","+
				student.getMatriculationNumber()+",\""+student.getStudyProgram()+"\"\n";
				
				fileWriter.write(data);
			}
			fileWriter.flush();
			fileWriter.close();
		}
		catch(IOException e){System.out.println(e.getMessage());}
		catch(Exception e) {System.out.println(e.getMessage());}
	}
	
	//Fill the Queue from CSV file
	public void readDataFromCSV(String filename) {
		try {
			BufferedReader reader=new BufferedReader(new FileReader("data/"+filename));
			String lineReader;
			String[] line;
			while((lineReader=reader.readLine()) != null){
				line=lineReader.split(",");
				this.people.add(new Student(line[0].replaceAll("\"", ""),
						line[1].replaceAll("\"", ""),Integer.parseInt(line[2]),
						line[3].replaceAll("\"", "")));
			}
			reader.close();
		}
		catch(IOException e) {System.out.println(e.getMessage());}
		catch(Exception e) {System.out.println(e.getMessage());}
	}
	
	//Sort items by matriculation number
	public void sortItemsInTheQueue() {
		Person person;
		for(int i =0;i<this.people.size() - 1;i++) {
            for (int j = i + 1; j < this.people.size(); j++) {
                if (((Student)this.people.get(j)).getMatriculationNumber() < ((Student)this.people.get(i)).getMatriculationNumber()) {
                    person = this.people.get(j);
                    this.people.set(j, this.people.get(i));
                    this.people.set(i, person);
                }
            }
        }
	}
}