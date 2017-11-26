package exercise2;

import org.jgraph.graph.DefaultEdge;

/**
 * SocialRelation class for social network
 * @author Laplace
 *
 */
public class SocialRelation extends DefaultEdge{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private boolean strongTie;
	
	/**
	 * Class constructor takes:
	 * @param strongTie
	 */
	public SocialRelation(boolean strongTie) {
		this.setStrongTie(strongTie);
	}
	
	/**
	 * 
	 * @return isStrong boolean value
	 */
	public boolean isStrongTie() {
		return strongTie;
	}
	
	/**
	 * Set strong tie
	 * @param strongTie
	 */
	public void setStrongTie(boolean strongTie) {
		this.strongTie = strongTie;
	}
}
