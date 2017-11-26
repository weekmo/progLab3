package exercise3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.ext.ComponentNameProvider;
import org.jgrapht.ext.GmlExporter;
import org.jgrapht.ext.IntegerEdgeNameProvider;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Exercise 3 executor
 * It is a bit confusing when we want to filter the file, if follow the instruction,
 *  it means only annotation that start with activat, catalyz and inhibit.
 *  and only has "complex". then we will end up with file "filterredFIsInGene.csv".
 *  Or if we say that annotation column that has these values, then we will end up with
 *  "filterredFIsInGene2.csv".
 *  
 * @author Laplace
 *
 */
@SuppressWarnings("deprecation")
public class Exercise3 {

	public Exercise3(String fileName,String gene1,String gene2) {
		//"FIsInGene.csv"
		SimpleDirectedWeightedGraph<String, ProteinInteractionEdge> protInterGraph = 
				generateDirectedGraph(fileName);
		
		DijkstraShortestPath<String,ProteinInteractionEdge> dijkstraAlg= 
				new DijkstraShortestPath<String,ProteinInteractionEdge>(protInterGraph);
		//VAMP2 BAG3
		SingleSourcePaths<String,ProteinInteractionEdge> path;
		GraphPath<String, ProteinInteractionEdge> direction;
		try {
			path= dijkstraAlg.getPaths(gene1);
			direction= path.getPath(gene2);
			if(direction !=null) {
				for(String node:direction.getVertexList())
					if(node.equals(direction.getEndVertex()))
						System.out.print(node);
					else
						System.out.print(node+" -> ");
			}
			else {
				System.out.println("There is no path from "+gene1+" and "+gene2);
			}
		}
		catch(IllegalArgumentException e) {System.err.println(gene2+" is not a sorce path");}
		
		
		ComponentNameProvider<String> protName=new ComponentNameProvider<String>() {
			
			public String getName(String component) {
				// TODO Auto-generated method stub
				return component;
			}
		};
		
		ComponentNameProvider<String> protID = new IntegerEdgeNameProvider<String>();
		
		ComponentNameProvider<ProteinInteractionEdge> edgeLabel = new ComponentNameProvider<ProteinInteractionEdge>() {
			
			public String getName(ProteinInteractionEdge component) {
				// TODO Auto-generated method stub
				return component.getAnnotation();
			}
		};
		ComponentNameProvider<ProteinInteractionEdge> edgeId=new IntegerEdgeNameProvider<ProteinInteractionEdge>();
		GmlExporter<String, ProteinInteractionEdge> exporter = new 
				GmlExporter<String, ProteinInteractionEdge>(protID,protName,edgeId,edgeLabel);
		exporter.setParameter(GmlExporter.Parameter.EXPORT_EDGE_LABELS, true);
		exporter.setParameter(GmlExporter.Parameter.EXPORT_VERTEX_LABELS,true);
		exporter.setParameter(GmlExporter.Parameter.EXPORT_EDGE_WEIGHTS,true);
		try {
			exporter.exportGraph(protInterGraph, new FileWriter("data/ProteinInteractionGraph.gml"));
		}
		catch(IOException e) {e.printStackTrace();}
	}
	//Function to read PPI file and return ArrayList of PPI class
	public SimpleDirectedWeightedGraph<String, ProteinInteractionEdge> generateDirectedGraph(String fileName){
		SimpleDirectedWeightedGraph<String, ProteinInteractionEdge> protInterGraph=new 
				SimpleDirectedWeightedGraph<String, ProteinInteractionEdge>(ProteinInteractionEdge.class);
		try {
			BufferedReader reader = new BufferedReader(new FileReader("data/"+fileName));
			String line;
			String[] data;
			while((line=reader.readLine()) !=null) {
				//[0]Gene1,[1]Gene2,[2]Annotation,[3]Direction,[4]Score
				data=line.split("\t");
				protInterGraph.addVertex(data[0]);
				protInterGraph.addVertex(data[1]);
				if(data[3].equals("->") || data[3].equals("-|")){
					protInterGraph.addEdge(data[0],data[1],
							new ProteinInteractionEdge(data[2], Double.parseDouble(data[4])));
				}
				else if(data[3].equals("<-") || data[3].equals("|-")) {
					
				protInterGraph.addEdge(data[1],data[0],
						new ProteinInteractionEdge(data[2], Double.parseDouble(data[4])));
				}
				else if(data[3].equals("-")) {
					protInterGraph.addEdge(data[0],data[1],
							new ProteinInteractionEdge(data[2], Double.parseDouble(data[4])));
					protInterGraph.addEdge(data[1],data[0],
							new ProteinInteractionEdge(data[2], Double.parseDouble(data[4])));
				}
			}
			reader.close();
			return protInterGraph;
		} 
    	catch (IOException e) {System.out.println(e.getMessage());}
    	catch (Exception e) {System.out.println(e.getMessage());}
    	
    	return null;
	}
	
}
