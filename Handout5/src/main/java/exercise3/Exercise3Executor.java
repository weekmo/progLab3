package exercise3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import main.*;

public class Exercise3Executor {
	public Exercise3Executor() {
		SimpleGraph<Node, DefaultEdge> aspirin= readGraphFromFiles("aspirinVertices.csv","aspirinEdges.csv");
		SimpleGraph<Node, DefaultEdge> ibuprofen=readGraphFromFiles("ibuprofenVertices.csv","ibuprofenEdges.csv");
		SimpleGraph<Node, DefaultEdge> benzene=readGraphFromFiles("benzeneVertices.csv","benzeneEdges.csv");
		VF2SubgraphIsomorphismInspector<Node, DefaultEdge> isoBenzeneInAspirin = 
				new VF2SubgraphIsomorphismInspector<>(aspirin, benzene);
		VF2SubgraphIsomorphismInspector<Node, DefaultEdge> isoBenzeneInIbuprofen = 
				new VF2SubgraphIsomorphismInspector<>(ibuprofen, benzene);
		System.out.println(isoBenzeneInAspirin.isomorphismExists());
		System.out.println(isoBenzeneInIbuprofen.isomorphismExists());
	}
	public static SimpleGraph<Node, DefaultEdge> readGraphFromFiles(String nodesFile,String edgesFile){
		SimpleGraph<Node, DefaultEdge> directedGraph = 
				new SimpleGraph<Node, DefaultEdge>(DefaultEdge.class);
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
