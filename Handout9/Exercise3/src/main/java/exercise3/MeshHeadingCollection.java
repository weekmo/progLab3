package exercise3;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A class to manage a collection of MeSH terms.
 * @author laplace
 *
 */
public class MeshHeadingCollection {
	private ArrayList<MeshHeading> meshHeadingListCollection;
	private int longestName;

	/**
	 * The constructor
	 */
	public MeshHeadingCollection() {
		this.meshHeadingListCollection = new ArrayList<MeshHeading>();
	}

	/**
	 * MeSH list getter
	 * @return MeSH list
	 */
	public ArrayList<MeshHeading> getMeshHedingList() {
		Collections.sort(this.meshHeadingListCollection);
		return this.meshHeadingListCollection;
	}

	/**
	 * Function to add MeSH term, if it is exist
	 * it will increase by one.
	 * @param meshHeading
	 */
	public void addMeshHeding(MeshHeading meshHeading) {
		if (this.getMeashHeading(meshHeading.getUi()) == null) {
			this.meshHeadingListCollection.add(meshHeading);
			this.setLongestName(meshHeading.getName().length());
		} else {
			this.getMeashHeading(meshHeading.getUi()).increase();
		}
	}

	/**
	 * A function to check if the the MeSH exist.
	 * @param ui
	 * @return boolean
	 */
	public boolean hasMeshHeading(String ui) {
		for (MeshHeading mesh : this.meshHeadingListCollection) {
			if (mesh.getUi().equalsIgnoreCase(ui)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get a MeSH by UI if it is exist.
	 * @param ui
	 * @return
	 */
	public MeshHeading getMeashHeading(String ui) {
		for (MeshHeading mesh : this.meshHeadingListCollection) {
			if (mesh.getUi().equalsIgnoreCase(ui)) {
				return mesh;
			}
		}
		return null;
	}
	
	/**
	 * Get largest length of MeSH term name
	 * @return int
	 */
	public int getLongestName() {
		return longestName;
	}
	/* 
	 * LongestName setter
	 */
	private void setLongestName(int max) {
		if(max>this.longestName)
			this.longestName=max;
	}
}
