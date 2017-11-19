package exercise1;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import main.*;
/**
 * Class to execute exercise 1
 * @author Laplace
 */
public class Exercise1Executor {
	/*
	 * List<String> animals =  Arrays.asList("Osprey","Bald Eagle","Gulls and Terns","Wading Birds","Large Piscivorous Fish",
			"Sea Ducks","Tundra Swan","Small Planktivorous Fish","Bivalves","Zooplankton","Benthic Invertebrates",
			"Herbivorous Ducks","Geese and Mute Swans","Phytoplankton","Submerged Aquatic Vegetation (SAV)","Vegetation");
	*/
	/**
	 * Constructor function use to execute exercise 1
	 */
	public Exercise1Executor() {
		DirectedGraph<Node, DefaultEdge> foodChain=
				readGraphFromFiles("VertexFoodChain.csv","edgsFoodChain.csv");
		Set<Node> bases = computeBase(foodChain);
		for(Node animal:bases) {
			System.out.println(animal.getId()+","+animal.getName()+" - in");
		}
		for(Node animal:computeApex(foodChain)) {
			System.out.println(animal.getId()+","+animal.getName()+" - out");
		}
		/*
		DijkstraShortestPath<Node, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(foodChain);
		
		for(Node base:bases) {
			//int height = Integer.MAX_VALUE;
			SingleSourcePaths<Node, DefaultEdge> paths = dijkstraAlg.getPaths(base);
			for(Node animal:foodChain.vertexSet()) {
				if(paths.getPath(animal) !=null) {
					System.out.println(base.getId()+","+base.getName()+" | "+
				paths.getPath(animal).getLength()+" | "+
							animal.getId()+","+animal.getName());
				}
			}
			//System.out.println(height);
		}*/
	}
	public static Set<Node> computeBase(DirectedGraph<Node, DefaultEdge> foodChain){
		Set<Node> base=new HashSet<Node>();
		for(Node node:foodChain.vertexSet()) {
			if(foodChain.inDegreeOf(node)==0) {
				base.add(node);
			}
		}
		return base;
	}
	public static Set<Node> computeApex(DirectedGraph<Node, DefaultEdge> foodChain){
		Set<Node> apex=new HashSet<Node>();
		for(Node node:foodChain.vertexSet()) {
			if(foodChain.outDegreeOf(node)==0) {
				apex.add(node);
			}
		}
		return apex;
	}
	/**
	 * Function to create {@code DirectedGraph} from files.
	 * @param nodesFile file name that contains nodes (IDs,Names)
	 * Separated by comma (CSV file).
	 * @param edgesFile file name that contains edges (IDs of nodes)
	 * Separated by comma (CSV file).
	 * @return DirectedGraph 
	 */
	public static DirectedGraph<Node, DefaultEdge> readGraphFromFiles(String nodesFile,String edgesFile){
		DirectedGraph<Node, DefaultEdge> directedGraph = 
				new DefaultDirectedGraph<Node, DefaultEdge>(DefaultEdge.class);
		BufferedReader nodesReader=null;
		BufferedReader edgesReader=null;
		try {
			nodesReader=new BufferedReader(new FileReader("data/"+nodesFile));
			edgesReader=new BufferedReader(new FileReader("data/"+edgesFile));
			String line;
			String[] lineItems;
			while((line=nodesReader.readLine())!=null) {
				lineItems=line.split(",");
				directedGraph.addVertex(new Node(Integer.parseInt(lineItems[0]),lineItems[1].toString()));
			}
			NodesCollection nodes=new NodesCollection();
			for(Node node:directedGraph.vertexSet()) {
				nodes.addNode(node);
			}
			while((line=edgesReader.readLine())!=null) {
				lineItems=line.split(",");
				directedGraph.addEdge(nodes.getNode(Integer.parseInt(lineItems[0])),
						nodes.getNode(Integer.parseInt(lineItems[1])));
			}
		}catch(IOException ex) {ex.printStackTrace(); System.out.println(ex.getMessage());}
		finally{
			try {
				nodesReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}}
		return directedGraph;
	}
}
