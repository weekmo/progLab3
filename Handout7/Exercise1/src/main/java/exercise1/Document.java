package exercise1;

import java.util.ArrayList;

import org.json.JSONArray;

/**
 * @author mohammed
 *
 */
public class Document {
	
	private int pmcID;
	private String title;
	private int year;
	private ArrayList<String> authors;
	
	public Document() {
		authors=new ArrayList<String>();
	}
	public Document(int pmcID,String title,int year,ArrayList<String> authors) {
		this();
		this.setPmcID(pmcID);
		this.setTitle(title);
		this.setYear(year);
		this.setAuthors(authors);
	}
	
	public Document(int pmcID,String title,int year,JSONArray authors) {
		this();
		this.setPmcID(pmcID);
		this.setTitle(title);
		this.setYear(year);
		this.setAuthors(authors);
	}

	public boolean hasAuthor(String auhor) {
		return this.getAuthors().contains(auhor);
	}
	
	public ArrayList<String> getAuthors() {
		return authors;
	}

	public void setAuthors(ArrayList<String> authors) {
		this.authors = authors;
	}
	
	public void setAuthors(JSONArray authors) {
		for(int i=0;i<authors.length();i++) {
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
