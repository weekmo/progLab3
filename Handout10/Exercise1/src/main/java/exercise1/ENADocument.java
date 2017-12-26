package exercise1;

import java.util.ArrayList;

/**
 * A class to hold document information collected from ENA
 * @author laplace
 *
 */
public class ENADocument {

	private String organism;
	private String moleculeType;
	private int sequenceLength;
	private String topology;
	private String description;
	private String sequence;
	private String fasa;
	private ArrayList<Protein> proteins;
	
	/**
	 * Constructor
	 * @param description
	 */
	public ENADocument(String description) {
		this.setDescription(description);
		proteins=new ArrayList<Protein>();
	}
	
	/**
	 * Constructor
	 * @param organism
	 * @param moleculeType
	 * @param topology
	 * @param sequenceLength
	 * @param description
	 */
	public ENADocument(String organism,String moleculeType,String topology,int sequenceLength,String description) {
		this(description);
		this.setOrganism(organism);
		this.setMoleculeType(moleculeType);
		this.setTopology(topology);
		this.setSequenceLength(sequenceLength);
		
	}
	
	/**
	 * Add new protein
	 * @param gene
	 * @param protein_id
	 * @param protein
	 * @param translation
	 */
	public void addProtein(String gene,String protein_id,String protein,String translation) {
		if(!this.hasProtein(gene)) {
			this.proteins.add(new Protein(gene, protein_id, protein, translation));
		}
	}
	
	/**
	 * Add new protein
	 * @param gene
	 * @param protein_id
	 * @param protein
	 * @param translation
	 * @param note
	 */
	public void addProtein(String gene,String protein_id,String protein,String translation,String note) {
		if(!this.hasProtein(gene)) {
			this.proteins.add(new Protein(gene, protein_id, protein, translation,note));
		}
	}
	
	/**
	 * Get protein(s)
	 * @return proteins
	 */
	public ArrayList<Protein> getProteins() {
		return proteins;
	}
	
	/**
	 * Remove a protein and return if the protein removed or not
	 * @param protein_id
	 * @return boolean
	 */
	public boolean removeProtein(String protein_id) {
		for(Protein prot:this.proteins) {
			if(prot.getProtein_id().equals(protein_id)) {
				this.proteins.remove(prot);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if a protein exist or not
	 * @param gene
	 * @return
	 */
	public boolean hasProtein(String gene) {
		for(Protein prot:this.proteins) {
			if(prot.getGene().equals(gene)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Molecule Type getter
	 * @return moleculeType
	 */
	public String getMoleculeType() {
		return moleculeType;
	}
	
	/**
	 * Molecule Type setter
	 * @param moleculeType
	 */
	public void setMoleculeType(String moleculeType) {
		this.moleculeType = moleculeType;
	}
	
	/**
	 * Organism name getter
	 * @return organism
	 */
	public String getOrganism() {
		return organism;
	}
	
	/**
	 * Organism setter
	 * @param organism
	 */
	public void setOrganism(String organism) {
		this.organism = organism;
	}
	
	/**
	 * Sequence Length getter
	 * @return sequenceLength
	 */
	public int getSequenceLength() {
		return sequenceLength;
	}
	
	/**
	 * Sequence Length setter
	 * @param sequenceLength
	 */
	public void setSequenceLength(int sequenceLength) {
		this.sequenceLength = sequenceLength;
	}
	
	/**
	 * Topology getter
	 * @return topology
	 */
	public String getTopology() {
		return topology;
	}
	
	/**
	 * Topology setter
	 * @param topology
	 */
	public void setTopology(String topology) {
		this.topology = topology;
	}
	
	/**
	 * Description getter
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Description setter
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Sequence getter
	 * @return sequence
	 */
	public String getSequence() {
		return sequence;
	}
	
	/**
	 * Sequence setter
	 * @param sequence
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	/**
	 * Fasa getter
	 * @return fasa
	 */
	public String getFasa() {
		return fasa;
	}
	
	/**
	 * Fasa setter
	 * @param fasa
	 */
	public void setFasa(String fasa) {
		this.fasa = fasa;
	}

}
