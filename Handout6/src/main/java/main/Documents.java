package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class Documents {
	private ArrayList<Document> documents;
	private ArrayList<String> groups;
	public Documents() {
		this.documents=new ArrayList<Document>();
		this.groups=new ArrayList<String>();
	}
	public Documents(String fileName) {
		this();
		this.readFile(fileName);
	}
	public void addDocument(Document document) {
		documents.add(document);
		if(!groups.contains(document.getJournal()))
			groups.add(document.getJournal());
	}
	
	public void addDocument(String title,String journal,int year) {
		this.addDocument(new Document(title, journal, year));
	}
	public ArrayList<Document> getDocuments(){
		return documents;
	}
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
	public void readFile(String fileName) {
		this.readFile(fileName, "\";");
	}
	public ArrayList<String> getJournals(){
		if(documents.size()>0)
			return this.groups;
		return null;
	}
	private boolean similarity(Document doc1,Document doc2) {
		if(doc1.getJournal().equalsIgnoreCase(doc2.getJournal()))
			return true;
		return false;
	}
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
