//Package name
package main;
/**
 * Class for graph node contain ID and name
 * @author Laplace
 *
 */
public class Node {
	/**
	 * {@code id}
	 * {@code name}
	 */
	private int id;
	private String name;
	/**
	 * Constructor function takes two parameters:
	 * @param id ID of the node
	 * @param name Name of the node
	 */
	public Node(int id,String name) {
		this.id=id;
		this.name=name;
	}
	/**
	 * Getter for the node's ID
	 * @return id Return id of the node
	 */
	public int getId() {
		return id;
	}
	/**
	 * Setter for node ID
	 * @param id Node's ID
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * Getter for node's name
	 * @return name Node's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Setter for node's name
	 * @param name Node's name
	 */
	public void setName(String name) {
		this.name = name;
	}
}
