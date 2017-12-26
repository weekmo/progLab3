package exercise1;

/**
 * Protein class to hold protein info
 * @author laplace
 *
 */
public class Protein {

	private String gene;
	private String protein;
	private String protein_id;
	private String translation;
	private String note;
	
	/**
	 * Constructor
	 * @param gene
	 * @param protein_id
	 * @param protein
	 * @param translation
	 */
	public Protein(String gene,String protein_id,String protein,String translation) {
		this.setGene(gene);
		this.setProtein_id(protein_id);
		this.setProtein(protein);
		this.setTranslation(translation);
	}
	
	/**
	 * constructor
	 * @param gene
	 * @param protein
	 * @param protein_id
	 * @param translation
	 * @param organism
	 * @param note
	 */
	public Protein(String gene,String protein_id,String protein,String translation,String note) {
		this(gene,protein_id,protein,translation);
		this.setNote(note);
	}
	
	/**
	 * Gene getter
	 * @return gene
	 */
	public String getGene() {
		return gene;
	}
	
	/**
	 * Gene setter
	 * @param gene
	 */
	public void setGene(String gene) {
		this.gene = gene;
	}
	
	/**
	 * Protein ID getter
	 * @return protein_id
	 */
	public String getProtein_id() {
		return protein_id;
	}
	
	/**
	 * Protein ID setter
	 * @param protein_id
	 */
	public void setProtein_id(String protein_id) {
		this.protein_id = protein_id;
	}
	
	/**
	 * Get protein amino acids sequence
	 * @return translation
	 */
	public String getTranslation() {
		return translation;
	}
	
	/**
	 * Set protein amino acids sequence
	 * @param translation
	 */
	public void setTranslation(String translation) {
		this.translation = translation;
	}
	
	/**
	 * Get note if it is available
	 * @return note
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * Set note if it is available
	 * @param note
	 */
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * Get protein name
	 * @return protein
	 */
	public String getProtein() {
		return protein;
	}
	
	/**
	 * Protein name setter
	 * @param protein
	 */
	public void setProtein(String protein) {
		this.protein = protein;
	}
}
