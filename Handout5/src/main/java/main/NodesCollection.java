//Package name
package main;

//Import libraries
import java.util.ArrayList;
/**
 * Class create a collection of graph nodes
 * @author Laplace
 *
 */
public class NodesCollection {
	//ArrayList carry nodes of a graph
	private ArrayList<Node> nodes;
	/**
	 * Class constructor
	 */
	public NodesCollection() {
		nodes = new ArrayList<Node>();
	}
	/**
	 * Function to add nodes to the collection
	 * @param node Graph node as Node type
	 */
	public void addNode(Node node) {
		nodes.add(node);
	}
	/**
	 * Getter for the node from collection by node's ID
	 * @param nodeId Node ID
	 * @return node Return the node
	 */
	public Node getNode(int nodeId) {
		for(Node node:nodes) {
			if(node.getId()==nodeId)
				return node;
		}
		return null;
	}
	/**
	 * Getter for nodes' collection
	 * @return
	 */
	public ArrayList<Node> getNodes(){
		return nodes;
	}
}
