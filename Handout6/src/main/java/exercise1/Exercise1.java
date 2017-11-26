/*
 * Package name
 */
package exercise1;
/*
 * Import libraries
 */
import java.util.Collection;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.graph.SimpleGraph;

/**
 * Class to execute Exercise 1
 * @author laplace
 *
 */
public class Exercise1 {
	
	/*
	 * Collection to save biggest clique
	 */
	Collection<Set<Document>> biggestClique;
	
	/**
	 * Class constructor
	 */
	public Exercise1(String fileName) {
		//"documents.csv"
		SimpleGraph<Document, DefaultEdge> docGraph=new Documents(fileName).getSimpleGraph();
	    this.biggestClique=new BronKerboschCliqueFinder<Document, DefaultEdge>(docGraph).getBiggestMaximalCliques();
	    System.out.println("\n\t****** Documents from the jouranl that has maximoun number of publications *******\n");
	    System.out.println("Last year of publication: "+getMaxYear());
        System.out.println("First year of publication: "+getMinYear());
	    double avg=getAverageYear();
        System.out.print("Average of publication years: ");
        if(avg%1==0)
        	System.out.println((int)avg);
        else
        	System.out.println((int)avg+" and "+(int)avg+1);
        System.out.print("\n");
		for(Set<Document> set:this.biggestClique) {
        	for(Document doc:set) {
        		System.out.println("\""+doc.getTitle()+"\";\""+doc.getJournal()+"\";\""+doc.getYear()+"\"");
        	}
        }
	}
	private int getMaxYear() {
		int max=0;
		for(Set<Document> set:this.biggestClique) {
        	for(Document doc:set) {
        		if(doc.getYear()>max)
        			max=doc.getYear();
        	}
        }
		return max;
	}
	private int getMinYear() {
		int min=Integer.MAX_VALUE;
		for(Set<Document> set:this.biggestClique) {
        	for(Document doc:set) {
        		if(doc.getYear()<min)
        			min=doc.getYear();
        	}
        }
		return min;
	}
	private double getAverageYear() {
		int counter=0,sumYears=0;
		for(Set<Document> set:this.biggestClique) {
        	for(Document doc:set) {
        		sumYears+=doc.getYear();
        		counter++;
        	}
        }
		return sumYears/counter;
	}
}
