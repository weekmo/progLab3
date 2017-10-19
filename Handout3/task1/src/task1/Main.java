package task1;

import java.util.Iterator;

public class Main {

	public static void main(String[] args) {
		Queue queue = new Queue();
		Student student1=new Student("Moahmmed", "Abdelgadir", 1001, "LSI");
		Student student2=new Student("Farid", "Khan", 1002, "LSKD");
		Student student3=new Student("Lipika", "Sharma", 1003, "SSK");
		Student student4=new Student("Haritha", "Thummagunta", 1004, "KSA");
		queue.push(student1);
		queue.push(student2);
		queue.push(student3);
		queue.push(student4);
		@SuppressWarnings("rawtypes")
		Iterator students = queue.iterator();
		while(students.hasNext()){
			Student s = (Student) students.next();
			System.out.println(s.getFirstName() +" "+s.getLastName());
		}
		queue.saveAsCSV("test.csv");
	}

}
