//Package name
package exercise1;

import java.util.HashSet;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
/**
 * Class to execute exercise 1
 * @author Laplace
 */
public class Exercise1Executor {
	DirectedGraph<String, DefaultEdge> foodChain;
	Set<String> bases;
	/*
	 * List<String> animals =  Arrays.asList("Osprey","Bald Eagle","Gulls and Terns","Wading Birds","Large Piscivorous Fish",
			"Sea Ducks","Tundra Swan","Small Planktivorous Fish","Bivalves","Zooplankton","Benthic Invertebrates",
			"Herbivorous Ducks","Geese and Mute Swans","Phytoplankton","Submerged Aquatic Vegetation (SAV)","Vegetation");
	*/
	/**
	 * Constructor function uses to execute exercise 1
	 */
	
	public Exercise1Executor() {
		/**
		 * Initialise foodChain and add vertices
		 */
		  foodChain=new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		  foodChain.addVertex ("Phytoplankton");
		  foodChain.addVertex ("SAV");
		  foodChain.addVertex ("Vegetation");
		  
		  foodChain.addVertex("Zooplankton");
		  foodChain.addVertex ("Benthic Invertebrates");
		  foodChain.addVertex ("Herbivorous Ducks");
		  foodChain.addVertex ("Geese and Mute Swans");
		  
		  foodChain.addVertex("Small Planktivorous Fish");		   
		  foodChain.addVertex ("Bivalves");
		  
		  foodChain.addVertex("Gulls and Terns");
		  foodChain.addVertex("Wading Birds");
		  foodChain.addVertex("Large Piscivorous Fish");
		  foodChain.addVertex("Sea Ducks");
		  foodChain.addVertex ("Tundra Swan");
		  
		  foodChain.addVertex("Osprey");
		  foodChain.addVertex("Bald Eagle");
		  
		  /**
		   * Add edges
		   */
		  foodChain.addEdge ("Phytoplankton", "Benthic Invertebrates");
		  foodChain.addEdge ("Phytoplankton", "Bivalves");
		  foodChain.addEdge("Phytoplankton", "Small Planktivorous Fish");
		  
		  foodChain.addEdge("SAV", "Herbivorous Ducks");
		  
		  foodChain.addEdge ("Vegetation", "Tundra Swan");
		  foodChain.addEdge ("Vegetation", "Geese and Mute Swans");
		  
		  foodChain.addEdge("Zooplankton", "Small Planktivorous Fish");
		  foodChain.addEdge("Zooplankton", "Bivalves");
		  
		  foodChain.addEdge("Benthic Invertebrates", "Sea Ducks");
		  
		  foodChain.addEdge("Small Planktivorous Fish", "Wading Birds");
		  foodChain.addEdge("Small Planktivorous Fish", "Large Piscivorous Fish");
		  foodChain.addEdge("Small Planktivorous Fish", "Gulls and Terns");
		  
		  foodChain.addEdge("Bivalves", "Sea Ducks");
		  foodChain.addEdge("Bivalves", "Herbivorous Ducks");
		  foodChain.addEdge("Bivalves", "Tundra Swan");
		  
		  foodChain.addEdge("Large Piscivorous Fish", "Osprey");
		  foodChain.addEdge("Large Piscivorous Fish", "Bald Eagle");
		  
		  foodChain.addEdge("Sea Ducks", "Bald Eagle");
		  
		bases= computeBase();
		System.out.println("\n***** (List of Base of Food Chain) ******");
		for(String animal:bases) {
			System.out.println(animal);
		}
		System.out.println("\n***** (List of Apex of Food Chain) ******");
		for(String animal:computeApex()) {
			System.out.println(animal);
		}
		
		System.out.println("\n***** (List of Nodes and hights) ******");
		printNodesHeight();
	}
	/**
	 * Compute base of food chain
	 * @return {@code Set<String>} Return set of base food chain 
	 */
	public Set<String> computeBase(){
		Set<String> base=new HashSet<String>();
		for(String node:foodChain.vertexSet()) {
			if(foodChain.inDegreeOf(node)==0) {
				base.add(node);
			}
		}
		return base;
	}
	
	/**
	 * Compute Apex of food chain
	 * @return {@code Set<String>} Return set of apex
	 */
	public Set<String> computeApex(){
		Set<String> apex=new HashSet<String>();
		for(String node:foodChain.vertexSet()) {
			if(foodChain.outDegreeOf(node)==0) {
				apex.add(node);
			}
		}
		return apex;
	}
	
	/**
	 * Print nodes and their heights
	 */
	public void printNodesHeight() {
		for (String vertex : foodChain.vertexSet() ) {
		     DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath <>(foodChain);
		     int height = Integer.MAX_VALUE; 
		     for (String b : bases) {
		     	SingleSourcePaths <String , DefaultEdge > paths = dijkstraAlg.getPaths(b);
				if (paths.getPath(vertex) != null) {
					if (paths.getPath(vertex).getLength() < height) {
						height = paths.getPath(vertex).getLength();
					}
		     	} 
		     }
		     System.out.println("\nNode:"+vertex+"\nHeight:"+height);
		}
	}
}
