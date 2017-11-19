package exercise2;

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

public class Exercise2Executor {
	public Exercise2Executor() {
		try {
			Scanner reader=new Scanner(System.in);
			//"social_network.gml"
			DirectedGraph<String, DefaultEdge> graph=readGmlFile(reader.nextLine());
			System.out.println(connectance(graph));
			System.out.println(complexity(graph));
			reader.close();
		}
		catch(InputMismatchException e) {e.printStackTrace();}
		/*
		for(String node:graph.vertexSet()) {
			System.out.println(node);
		}
		for(DefaultEdge node:graph.edgeSet()) {
			System.out.println(node);
		}
		*/
	}
	public DirectedGraph<String, DefaultEdge> readGmlFileXML(String fileName){
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
	
	public DirectedGraph<String, DefaultEdge> readGmlFile(String fileName){
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
	public float connectance(DirectedGraph<String, DefaultEdge> graph) {
		//System.out.println(graph.edgeSet().size()+"e:v"+graph.vertexSet().size());
		return (float)graph.edgeSet().size()/(float)(Math.pow(graph.vertexSet().size(), 2));
	}
	public float complexity(DirectedGraph<String, DefaultEdge> graph) {
		int complexitySum=0;
		for(String vertex:graph.vertexSet()) {
			complexitySum+=graph.inDegreeOf(vertex);
			complexitySum+=graph.outDegreeOf(vertex);
		}
		return (float)complexitySum/(float)graph.vertexSet().size();
	}
}
