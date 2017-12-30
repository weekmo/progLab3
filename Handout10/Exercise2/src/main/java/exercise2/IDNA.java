package exercise2;

import java.util.ArrayList;

/**
 * An interface to manage NCBISequenceApi methods appearance.
 * @author laplace
 *
 */
public interface IDNA extends IMolecule{
	public ArrayList<IMolecule> getProtein();
	/**
	 * Strandedness getter
	 * @return Strandedness
	 */
	public String getStrandedness();
	
	/**
	 * Strandedness setter
	 * @param strandedness
	 */
	public void setStrandedness(String strandedness);
}