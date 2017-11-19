//Package name
package exercise2;

//Import Libraries
import java.io.File;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.VertexProvider;
import org.jgrapht.ext.EdgeProvider;
import org.jgrapht.ext.GmlImporter;
import org.jgrapht.ext.GraphMLImporter;
import org.jgrapht.ext.ImportException;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

/**
 * Class to execute exercise 2
 * @author Laplace
 *
 */
public class Exercise2Executor {
	
	//Create Graph
	DirectedGraph<String, DefaultEdge> graph;
	/**
	 * Constructor function uses to execute exercise 2
	 */
	public Exercise2Executor() {
		try {
			System.out.println("Enter GML file name include extension (.gml):");
			Scanner reader=new Scanner(System.in);
			//"social_network.gml"
			graph=readGmlFile(reader.nextLine());
			System.out.println("\nConnectance: "+connectance(graph));
			System.out.println("Complexity: "+complexity(graph));
			reader.close();
		}
		catch(InputMismatchException e) {e.printStackTrace();}
	}
	
	/**
	 * Read {@code DirectedGraph} from GML file.
	 * @param fileName Name of GML file include extension (.gml)
	 * @return Return directed graph [{@code DirectedGraph}].
	 */
	public static DirectedGraph<String, DefaultEdge> readGmlFile(String fileName){
		final DirectedGraph<String, DefaultEdge> ecoNetwork = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		VertexProvider<String> vp=new VertexProvider<String>() {
			
			public String buildVertex(String label, Map<String, String> attributes) {
				// TODO Auto-generated method stub
				return label;
			}
		};
		
		EdgeProvider<String,DefaultEdge> ep=new EdgeProvider<String, DefaultEdge>() {
			
			public DefaultEdge buildEdge(String from, String to, String label, Map<String, String> attributes) {
				// TODO Auto-generated method stub
				return ecoNetwork.getEdgeFactory().createEdge(from, to);
			}
		};
		
		GmlImporter<String, DefaultEdge> importer = new GmlImporter<String,DefaultEdge>(vp, ep);
		try {
			importer.importGraph(ecoNetwork, new File("data/"+fileName));
		}catch(ImportException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return ecoNetwork;
	}
	
	/**
	 * Compute the connectance of a graph
	 * @param graph Directed graph
	 * @return Return connectance as {@code float}.
	 */
	public float connectance(DirectedGraph<String, DefaultEdge> graph) {
		if(graph==null)
			graph=this.graph;
		return (float)graph.edgeSet().size()/(float)(Math.pow(graph.vertexSet().size(), 2));
	}
	
	/**
	 * 
	 * @param graph Directed graph
	 * @return Return complexity as {@code float}.
	 */
	public float complexity(DirectedGraph<String, DefaultEdge> graph) {
		if(graph==null)
			graph=this.graph;
		int complexitySum=0;
		for(String vertex:graph.vertexSet()) {
			complexitySum+=graph.inDegreeOf(vertex);
			complexitySum+=graph.outDegreeOf(vertex);
		}
		return (float)complexitySum/(float)graph.vertexSet().size();
	}
	
	/**
	 * Read GML/xml file format as {@code DirectedGraph}.
	 * @param fileName Name of GML file include extension (.gml)
	 * @return Return directed graph [{@code DirectedGraph}].
	 */
	public DirectedGraph<String, DefaultEdge> readGmlFileXML(String fileName){
		/* Ignore it
		 * Just a try
		 */
		final DirectedGraph<String, DefaultEdge> ecoNetwork = new DefaultDirectedGraph<String, DefaultEdge>(DefaultEdge.class);
		VertexProvider<String> vp=new VertexProvider<String>() {
			
			public String buildVertex(String label, Map<String, String> attributes) {
				// TODO Auto-generated method stub
				return label;
			}
		};
		
		EdgeProvider<String,DefaultEdge> ep=new EdgeProvider<String, DefaultEdge>() {
			
			public DefaultEdge buildEdge(String from, String to, String label, Map<String, String> attributes) {
				// TODO Auto-generated method stub
				return ecoNetwork.getEdgeFactory().createEdge(from, to);
			}
		};
		
		GraphMLImporter<String, DefaultEdge> importer = new GraphMLImporter<String,DefaultEdge>(vp, ep);
		try {
			importer.importGraph(ecoNetwork, new File("data/"+fileName));
		}catch(ImportException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return ecoNetwork;
	}
}
