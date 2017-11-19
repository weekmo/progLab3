//Package name
package exercise3;

import java.awt.Desktop;
//Import libraries
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.jgrapht.alg.isomorphism.VF2SubgraphIsomorphismInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import main.*;

/**
 * Class to execute exercise 3
 * @author Laplace
 *
 */
public class Exercise3Executor {
	
	/**
	 * Constructor function uses to execute exercise 3
	 */
	public Exercise3Executor() {
		
		System.out.println("\n*** Exercise 3.1 solution ****\n");
		vf2Explanation();
		
		System.out.println("\n*** Exercise 3.2 solution ****\n");
		SimpleGraph<Node, DefaultEdge> aspirin= readGraphFromFiles("aspirinVertices.csv","aspirinEdges.csv");
		SimpleGraph<Node, DefaultEdge> ibuprofen=readGraphFromFiles("ibuprofenVertices.csv","ibuprofenEdges.csv");
		SimpleGraph<Node, DefaultEdge> benzene=readGraphFromFiles("benzeneVertices.csv","benzeneEdges.csv");
		
		VF2SubgraphIsomorphismInspector<Node, DefaultEdge> isoBenzeneInAspirin = 
				new VF2SubgraphIsomorphismInspector<>(aspirin, benzene);
		VF2SubgraphIsomorphismInspector<Node, DefaultEdge> isoBenzeneInIbuprofen = 
				new VF2SubgraphIsomorphismInspector<>(ibuprofen, benzene);
		
		checkAndPrintResult(isoBenzeneInAspirin.isomorphismExists(), "Aspirin");
		checkAndPrintResult(isoBenzeneInIbuprofen.isomorphismExists(), "Ibuprofen");
		
		/*
		 * Try to open pdf
		 */
		try {
			Desktop.getDesktop().open(new File("data/exercise3.1.pdf"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Solution for exercise3.1
	 * VF2 algorithm description
	 */
	public void vf2Explanation() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("data/exercise3.1.txt"));
			String line;
			while((line=reader.readLine())!=null) {
				System.out.println(line);
			}
		}
		catch(IOException e) {e.printStackTrace();System.err.println(e.getMessage());}
	}
	
	/**
	 * Function to create {@code DirectedGraph} from files.
	 * 
	 * @param nodesFile file name that contains nodes (IDs,Names)
	 * Separated by comma (CSV file).
	 * @param edgesFile file name that contains edges (IDs of nodes)
	 * Separated by comma (CSV file).
	 * @return DirectedGraph 
	 */
	public SimpleGraph<Node, DefaultEdge> readGraphFromFiles(String nodesFile,String edgesFile){
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
		}catch(IOException ex) {ex.printStackTrace(); System.err.println(ex.getMessage());}
		finally{
			try {
				nodesReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}}
		return directedGraph;
	}
	
	/**
	 * Print out the result
	 * it prints if a molecule has benzen as submolecule
	 * @param hasBenzen {@code Boolean} parameter, if molecule
	 *  has a benzen submolecule.
	 * @param moleculeName Molecule name
	 */
	public void checkAndPrintResult(boolean hasBenzen,String moleculeName) {
		if(hasBenzen)
			System.out.println(hasBenzen+": "+moleculeName+" contain a subgraph isomorph to Benzol");
		else
			System.out.println(hasBenzen+": "+moleculeName+" doesn't contain a subgraph isomorph to Benzol");
	}
}
