//Package name
package task3;
/*
 * Protein Protein Interaction (PPI) class
 */
public class PPI {
	/*
	 * Properties for Gene1,Gene2,Annotation,Direction and Score respectively 
	 */
	private String gene1;
	private String gene2;
	private String annotation;
	private String direction;
	private double score;
	
	//Class constructors
	public PPI() {}
	public PPI(String gene1,String gene2,String annotation,String direction,double score) {
		this.gene1=gene1;
		this.gene2=gene2;
		this.annotation=annotation;
		this.direction=direction;
		this.score=score;
	}
	
	//Properties getters and setters
	public String getGene1() {
		return gene1;
	}

	public void setGene1(String gene1) {
		this.gene1 = gene1;
	}

	public String getGene2() {
		return gene2;
	}

	public void setGene2(String gene2) {
		this.gene2 = gene2;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

}
