package main;

public class Document {
	private String title,journal;
	private int year;
	
	public Document(String title,String journal,int year) {
		this.setTitle(title);
		this.setJournal(journal);
		this.setYear(year);
	}

	/**
	 * @return the title
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
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

}
