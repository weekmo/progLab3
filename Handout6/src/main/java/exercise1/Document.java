//Package name
package exercise1;
/**
 * Class Document has title, journal and year of publication
 * @author Laplace
 *
 */
public class Document {
	/**
	 * properties
	 */
	private String title,journal;
	private int year;
	
	/**
	 * Class constructor
	 * @param title Document title
	 * @param journal Publication journal
	 * @param year Year of publication
	 */
	public Document(String title,String journal,int year) {
		this.setTitle(title);
		this.setJournal(journal);
		this.setYear(year);
	}

	/**
	 * @return the title Document title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the journal
	 */
	public String getJournal() {
		return journal;
	}

	/**
	 * @param journal the journal to set
	 */
	public void setJournal(String journal) {
		this.journal = journal;
	}

	/**
	 * @return get year of publication
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year set year of publication
	 */
	public void setYear(int year) {
		this.year = year;
	}

}
