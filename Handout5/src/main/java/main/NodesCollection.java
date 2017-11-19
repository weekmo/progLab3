package main;

import java.util.ArrayList;

public class NodesCollection {
	private ArrayList<Node> nodes;
	public NodesCollection() {
		nodes = new ArrayList<Node>();
	}
	public void addNode(Node node) {
		nodes.add(node);
	}
	public Node getNode(int nodeId) {
		for(Node node:nodes) {
			if(node.getId()==nodeId)
				return node;
		}
		return null;
	}
	public ArrayList<Node> getNodes(){
		return nodes;
	}
}
