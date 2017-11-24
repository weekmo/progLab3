package exercise1;

import java.util.Collection;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.graph.SimpleGraph;

import main.Document;
import main.Documents;

public class Exercise1 {
	Collection<Set<Document>> biggestClique;
	public Exercise1() {
        SimpleGraph<Document, DefaultEdge> docGraph=new Documents("documents.csv").getSimpleGraph();
        biggestClique=new BronKerboschCliqueFinder<Document, DefaultEdge>(docGraph).getBiggestMaximalCliques();
        for(Set<Document> set:biggestClique) {
        	for(Document doc:set) {
        		System.out.println(doc.getJournal()+","+doc.getYear()+":"+doc.getTitle());
        	}
        }
        double avg=getAverageYear();
        if(avg%1==0)
        	System.out.println((int)avg);
        else
        	System.out.println((int)avg+" and "+(int)avg+1);
        
        System.out.println(getMaxYear());
        System.out.println(getMinYear());
	}
	private int getMaxYear() {
		int max=0;
		for(Set<Document> set:biggestClique) {
        	for(Document doc:set) {
        		if(doc.getYear()>max)
        			max=doc.getYear();
        	}
        }
		return max;
	}
	private int getMinYear() {
		int min=Integer.MAX_VALUE;
		for(Set<Document> set:biggestClique) {
        	for(Document doc:set) {
        		if(doc.getYear()<min)
        			min=doc.getYear();
        	}
        }
		return min;
	}
	private double getAverageYear() {
		int counter=0,sumYears=0;
		for(Set<Document> set:biggestClique) {
        	for(Document doc:set) {
        		sumYears+=doc.getYear();
        		counter++;
        	}
        }
		return sumYears/counter;
	}
}
