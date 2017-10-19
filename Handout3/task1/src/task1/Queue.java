package task1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Queue implements Iterable<Person>{
	private ArrayList<Person> people = new ArrayList<Person>();
	
	public void push(Person value){
		people.add(value);
	}
	public Person pop(Person value){
		people.remove(value);
		return value;
	}
	@Override
	public Iterator<Person> iterator() {
		return people.iterator();
	}
	public void saveAsCSV(String fileName){
		@SuppressWarnings("rawtypes")
		Iterator peopleIter = this.iterator();
		if(!peopleIter.hasNext()){
			System.out.println("The queue is empty!");
			return;
		}
		try{
			FileWriter fileWriter = new FileWriter("data/"+fileName,false);
			while(peopleIter.hasNext()){
				Student student=(Student)peopleIter.next();
				fileWriter.append('"'+student.getFirstName()+'"'+",");
				fileWriter.append('"'+student.getLastName()+'"'+",");
				fileWriter.append(student.getMatriculationNumber()+",");
				fileWriter.append('"'+student.getStudyProgram()+'"'+"\n");
			}
			fileWriter.flush();
			fileWriter.close();
		}catch(IOException e){System.out.println(e.getMessage());}
	}
}
