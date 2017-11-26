//Package name
package exercise2;

/**
 * Person class has ID and name as properties
 * 
 * @author Laplace
 *
 */
public class Person {
	/*
	 * Properties for ID and name
	 */
	private int id;
	private String name;

	/**
	 * Constructor takes attributes bellow:
	 * 
	 * @param id
	 *            Person ID
	 * @param name
	 *            Person Name
	 */
	public Person(int id, String name) {
		this.setId(id);
		this.setName(name);
	}

	/**
	 * 
	 * @return name Person name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set Person name
	 * 
	 * @param name
	 *            Person Name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get ID
	 * 
	 * @return id Person ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set ID
	 * 
	 * @param id
	 *            Person ID
	 */
	public void setId(int id) {
		this.id = id;
	}
}
