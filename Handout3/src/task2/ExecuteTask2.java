package task2;

import task1.Queue;

public class ExecuteTask2 {
	public ExecuteTask2() {
		// ***** Task 2 *****
		Queue queue = new Queue();
		//Read data from file provided
		queue.readDataFromCSV("students.csv");
		queue.sortItemsInTheQueue();
		/* The file is CSV format, so it is a table. columns separated by comma (,)
		 * and rows by line (\n).
		 * we choose this format because it easy to operate and there are many
		 * libraries can deal with it.
		 */
		queue.saveQueueAsCSV("studentsDataFromFile.csv");
	}
}
