package task1;

public class ExecuteTask1 {
	public ExecuteTask1() {
		// ***** Task 1 *****
		/* we don't need to cast Student instances because the queue accept objects from Person class
		 * and as Student class inherited from Person class, so it is Person class with more features 
		 */
		Queue queue = new Queue();
		queue.pushItemToQueue(new Student("Moahmmed", "Abdelgadir", 1007, "LSI"));
		queue.pushItemToQueue(new Student("Farid", "Khan", 1002, "LSKD"));
		queue.pushItemToQueue(new Student("Lipika", "Sharma", 1013, "SSK"));
		queue.pushItemToQueue(new Student("Haritha", "Thummagunta", 1004, "KSA"));
		
		/* The file is CSV format, so it is a table. columns separated by comma (,)
		 * and rows by line (\n).
		 * we choose this format because it easy to operate and there are many
		 * libraries can deal with it.
		 */
		queue.saveQueueAsCSV("studentsDataEntered.csv");
	}
}
