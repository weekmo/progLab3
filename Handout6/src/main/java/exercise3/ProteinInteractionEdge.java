package exercise3;

import org.jgrapht.graph.DefaultWeightedEdge;

/**
 * {@code ProteinInteractionEdge} inherites from DefaultWeightedEdge
 * 
 * @author Laplace
 *
 */
public class ProteinInteractionEdge extends DefaultWeightedEdge {

	private static final long serialVersionUID = 1L;
	private String annotation;
	private double score;

	/**
	 * Class constructor takes:
	 * 
	 * @param annotation
	 * @param score
	 */
	public ProteinInteractionEdge(String annotation, double score) {
		this.setAnnotation(annotation);
		this.setScore(score);
	}

	/**
	 * Get edge Weight
	 * 
	 * @return Weight
	 */
	@Override
	public double getWeight() {
		return this.score;
	}

	/**
	 * 
	 * @return Annotation
	 */
	public String getAnnotation() {
		return annotation;
	}

	/**
	 * 
	 * @param annotation
	 */
	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	/**
	 * 
	 * @param score
	 */
	public void setScore(double score) {
		this.score = score;
	}

}
