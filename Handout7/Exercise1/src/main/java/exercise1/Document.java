package exercise1;

import java.util.ArrayList;

import org.json.JSONArray;

/**
 * Class for document, it has PMC ID, Title, Publication Year and Authors as
 * properties and their getter(s) and setter(s).<br/>
 * It has three constructors with different parameters number and type.
 * 
 * @author laplace
 *
 */
public class Document {
	/*
	 * Properties
	 */
	private int pmcID;
	private String title;
	private int year;
	private ArrayList<String> authors;

	/*
	 * Constructors
	 */
	public Document() {
		authors = new ArrayList<String>();
	}

	public Document(int pmcID, String title, int year, ArrayList<String> authors) {
		this();
		this.setPmcID(pmcID);
		this.setTitle(title);
		this.setYear(year);
		this.setAuthors(authors);
	}

	public Document(int pmcID, String title, int year, JSONArray authors) {
		this();
		this.setPmcID(pmcID);
		this.setTitle(title);
		this.setYear(year);
		this.setAuthors(authors);
	}

	/*
	 * Setters and getters
	 */
	public ArrayList<String> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}

	public void setAuthors(JSONArray authors) {
		for (int i = 0; i < authors.length(); i++) {
			this.authors.add(authors.getJSONObject(i).getString("name"));
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getPmcID() {
		return pmcID;
	}

	public void setPmcID(int pmcID) {
		this.pmcID = pmcID;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

}
