package exercise3;

/**
 * A class for MeSH term
 * @author laplace
 *
 */
public class MeshHeading implements Comparable<MeshHeading>{
	
	private String ui;
	private String name;
	private int counter;

	/**
	 * The constructor
	 * @param ui
	 * @param name
	 */
	public MeshHeading(String ui,String name) {
		this.setUi(ui);
		this.setName(name);
		this.counter=1;
	}

	/**
	 * The UI getter
	 * @return UI
	 */
	public String getUi() {
		return ui;
	}

	/**
	 * The UI setter
	 * @param ui
	 */
	public void setUi(String ui) {
		this.ui = ui;
	}

	/**
	 * The name getter
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * The name setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Count getter
	 * @return count
	 */
	public int getCounter() {
		return counter;
	}
	
	/**
	 * A function to increase the counter by one.
	 */
	public void increase() {
		this.counter++;
	}
	
	/**
	 * A function to decrease the counter by one.
	 */
	public void decrease() {
		if(this.counter>1) {
			this.counter--;
		}
	}
	
	/**
	 * Comparer
	 */
	public int compareTo(MeshHeading mesh) {
		return mesh.getCounter()-this.getCounter();
	}
}
