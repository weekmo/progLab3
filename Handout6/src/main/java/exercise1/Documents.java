/*
 * package name
 */
package exercise1;
/*
 * Import libraries
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

/**
 * Class of Documents collection from Document class
 * @author Laplace
 *
 */
public class Documents {
	/*
	 * properties
	 */
	private ArrayList<Document> documents;
	private ArrayList<String> groups;
	
	/**
	 * Class constructor
	 */
	public Documents() {
		this.documents=new ArrayList<Document>();
		this.groups=new ArrayList<String>();
	}
	
	/**
	 * 
	 * @param fileName File name to read for graph creation
	 */
	public Documents(String fileName) {
		this();
		this.readFile(fileName);
	}
	
	/**
	 * Add document as instance from class Document 
	 * @param document
	 */
	public void addDocument(Document document) {
		documents.add(document);
		if(!groups.contains(document.getJournal()))
			groups.add(document.getJournal());
	}
	
	/**
	 * Add document by passing arguments below:
	 * @param title Document title
	 * @param journal Publication journal
	 * @param year Year of publication
	 */
	public void addDocument(String title,String journal,int year) {
		this.addDocument(new Document(title, journal, year));
	}
	
	/**
	 * Get documents as {@code ArrayList<Document>}
	 * @return Documents
	 */
	public ArrayList<Document> getDocuments(){
		return documents;
	}
	
	/**
	 * Read file by passing:
	 * @param fileName File name
	 * @param separator File separator
	 */
	public void readFile(String fileName,String separator) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data/"+fileName));
			String line;
			String[] content;
			while((line=reader.readLine())!=null) {
				if(line.length()>1) {
					content=line.split(separator);
					this.addDocument(content[0].replaceAll("\"", ""),content[1].replaceAll("\"", ""),Integer.parseInt(content[2]));
				}
			}
			reader.close();
		}
		catch(IOException e) {e.printStackTrace();}
	}
	
	/**
	 * Read file by passing:
	 * @param fileName
	 */
	public void readFile(String fileName) {
		this.readFile(fileName, "\";");
	}
	
	/**
	 * Get publication journal
	 * @return documents {@code ArrayList<String>}
	 */
	public ArrayList<String> getJournals(){
		if(documents.size()>0)
			return this.groups;
		return null;
	}
	/*
	 * Check similarity between two documents by comparing journal
	 */
	private boolean similarity(Document doc1,Document doc2) {
		if(doc1.getJournal().equalsIgnoreCase(doc2.getJournal()))
			return true;
		return false;
	}
	
	/**
	 * Create {@code SimpleGraph<Document, DefaultEdge> } and return:
	 * @return SimpleGraph
	 */
	public SimpleGraph<Document, DefaultEdge> getSimpleGraph(){
		if(this.documents.size()<1) {
			System.err.println("*** There is no documents! ***\nPlease read a file or add Documents!");
			return null;
		}
		SimpleGraph<Document, DefaultEdge> docGraph=new SimpleGraph<Document, DefaultEdge>(DefaultEdge.class);
        for(Document doc:this.documents) {
        	docGraph.addVertex(doc);
        }
        for(int i=0;i<this.documents.size()-1;i++) {
        	for(int j=i+1;j<this.documents.size();j++) {
        		if(this.similarity(this.documents.get(i),this.documents.get(j))) {
        			docGraph.addEdge(this.documents.get(i), this.documents.get(j));
        		}
        	}
        }
		return docGraph;
	}
}
