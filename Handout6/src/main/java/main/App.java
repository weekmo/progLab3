package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.Edge;
import org.jgrapht.GraphMapping;
import org.jgrapht.alg.BronKerboschCliqueFinder;
import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.jgrapht.graph.SimpleGraph;

import ecxercise1.Exercise1;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	new Exercise1();
    }
    
    public static void play() {

    	SimpleGraph<Element, DefaultEdge> chemnet=new SimpleGraph<Element, DefaultEdge>(DefaultEdge.class);
        SimpleGraph<Element, DefaultEdge> chemnet2=new SimpleGraph<Element, DefaultEdge>(DefaultEdge.class);
        
        Element C1 = new Element("C1","C");
        Element O1 = new Element("O1","O");
        Element C3 = new Element("C3","C");
        
        Element C4 = new Element("C4","C");
        Element O2 = new Element("O2","O");
        Element C6 = new Element("C6","C");
        Element C7 = new Element("C7","C");
        
        chemnet.addVertex(C1);
        chemnet.addVertex(O1);
        chemnet.addVertex(C3);
        
        chemnet2.addVertex(C4);
        chemnet2.addVertex(O2);
        chemnet2.addVertex(C6);
        chemnet2.addVertex(C7);
        
        chemnet.addEdge(C1 , O1);
        chemnet.addEdge(C1 , O1);
        chemnet.addEdge(O1 , C3);
        
        chemnet2.addEdge(O2 , C4);
        chemnet2.addEdge(O2 , C6);
        chemnet2.addEdge(C7 , C6);
        /*
        VF2SubgraphIsomorphismInspector<Element, DefaultEdge> iso = 
        		new VF2SubgraphIsomorphismInspector<Element, DefaultEdge>(chemnet2, chemnet);
        System.out.println(iso.isomorphismExists());
        java.util.Iterator<GraphMapping<Element, DefaultEdge>> mappings = iso.getMappings();
        while(mappings.hasNext()) {
        	GraphMapping<Element, DefaultEdge> map = mappings.next();
        	System.out.println(map);
        	for(Element el:chemnet2.vertexSet()) {
        		Element corresponse = map.getVertexCorrespondence(el, true);
        		if(corresponse !=null) {
        			//if(corresponse.getElement()==el.getElement())
        			System.out.println(corresponse.getElement()+
        					":"+el.getElement());
        		}
        	}
        }
        */
        for(DefaultEdge e:chemnet2.edgeSet()) {
        	System.out.println(e);
        }
        for(Element v:chemnet2.vertexSet()) {
        	System.out.println(v);
        }
    }
    public void backUp() {
    	SimpleGraph<Document, DefaultEdge> docGraph=new SimpleGraph<Document, DefaultEdge>(DefaultEdge.class);
    	//int counter=0;
    	ArrayList<String> unique=new ArrayList<String>();
        Documents docs=new Documents();
        docs.readFile("data/documents.csv", "\";");
        for(Document doc:docs.getDocuments()) {
        	docGraph.addVertex(doc);
        	if(!unique.contains(doc.getJournal()))
        		unique.add(doc.getJournal());
        }
        Document doc1,doc2;
        for(int i=0;i<docs.getDocuments().size()-1;i++) {
        	doc1=docs.getDocuments().get(i);
        	for(int j=i+1;j<docs.getDocuments().size();j++) {
        		doc2=docs.getDocuments().get(j);
        		//if(i<5 || i>1610)
        		//System.out.println(i+":"+j);
        		if(doc1.getJournal().equalsIgnoreCase(doc2.getJournal())) {
        			//System.out.println(d.getDocuments().get(i).getJournal()+":"+d.getDocuments().get(i).getYear()+","+d.getDocuments().get(j).getYear());
        			//counter++;
        			docGraph.addEdge(doc1, doc2);
        			
        		}
        	}
        }
        //System.out.println(counter);
        
        //Collection<Set<Document>> cliques=new BronKerboschCliqueFinder<Document, DefaultEdge>(docGraph).getAllMaximalCliques();
        Collection<Set<Document>> biggestClique=new BronKerboschCliqueFinder<Document, DefaultEdge>(docGraph).getBiggestMaximalCliques();
        //System.out.println(biggestClique.size());
        for(Set<Document> set:biggestClique) {
        	for(Document doc:set) {
        		System.out.println(doc.getYear()+":"+doc.getTitle());
        	}
        }
    }
}
