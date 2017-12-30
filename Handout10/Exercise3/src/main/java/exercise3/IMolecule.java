package exercise3;

/**
 * An interface to manage NCBISequenceApi methods appearance.
 * @author laplace
 *
 */
public interface IMolecule {
	/**
	 * MetaData getter
	 * @return MetaData
	 */
	public String getMetaData();
	
	/**
	 * Accession Version getter
	 * @return AccessionVersion
	 */
	public String getAccessionVersion();
	
	/**
	 * Accession Version setter
	 * @param accessionVersion
	 */
	public void setAccessionVersion(String accessionVersion);
	
	/**
	 * Locus getter
	 * @return Locus
	 */
	public String getLocus();
	
	/**
	 * Locus setter
	 * @param locus
	 */
	public void setLocus(String locus);
	
	/**
	 * Sequence Length getter
	 * @return Length
	 */
	public int getLength();
	
	/**
	 * Sequence Length setter
	 * @param length
	 */
	public void setLength(int length) ;
	
	
	
	/**
	 * Molecule Type getter
	 * @return MoleculeType
	 */
	public String getMoleculeType();
	
	/**
	 * Molecule Type setter
	 * @param moleculeType
	 */
	public void setMoleculeType(String moleculeType);
	
	/**
	 * Topology setter
	 * @return Topology
	 */
	public String getTopology();
	
	/**
	 * Topology setter
	 * @param topology
	 */
	public void setTopology(String topology);
	
	/**
	 * Division getter
	 * @return Division
	 */
	public String getDivision();
	
	/**
	 * Division setter
	 * @param division
	 */
	public void setDivision(String division);
	
	/**
	 * UpdateDate getter
	 * @return UpdateDate
	 */
	public String getUpdateDate();
	
	/**
	 * UpdateDate setter
	 * @param updateDate
	 */
	public void setUpdateDate(String updateDate);
	
	/**
	 * CreateDate getter
	 * @return CreateDate
	 */
	public String getCreateDate();
	
	/**
	 * CreateDate setter
	 * @param createDate
	 */
	public void setCreateDate(String createDate);
	
	/**
	 * Primary Accession getter 
	 * @return PrimaryAccession
	 */
	public String getPrimaryAccession();
	
	/**
	 * Primary Accession setter
	 * @param primaryAccession
	 */
	public void setPrimaryAccession(String primaryAccession);
	
	/**
	 * Definition getter
	 * @return Definition
	 */
	public String getDefinition();

	/**
	 * Definition setter
	 * @param definition
	 */
	public void setDefinition(String definition);

	/**
	 * Organism getter
	 * @return Organism
	 */
	public String getOrganism();

	/**
	 * Organism setter
	 * @param organism
	 */
	public void setOrganism(String organism);

	/**
	 * Taxonomy getter
	 * @return Taxonomy
	 */
	public String getTaxonomy();

	/**
	 * Taxonomy setter
	 * @param taxonomy
	 */
	public void setTaxonomy(String taxonomy);

	/**
	 * Source setter
	 * @param source
	 */
	public void setSource(String source);
	
	/**
	 * Source getter
	 * @return Source
	 */
	public String getSource();
	/**
	 * Summary getter
	 * @return
	 */
	public String getSummary();

	/**
	 * Summary setter
	 * @param summary
	 */
	public void setSummary(String summary);

	/**
	 * Sequence setter
	 * @param sequence
	 */
	public void setSequence(String sequence);
	
	/**
	 * Sequence getter
	 * @return Sequence
	 */
	public String getSequence();
	
	/**
	 * Fasta getter
	 * @return Fasta
	 */
	public String getFasta();
	
	/**
	 * Fasta setter
	 * @param fasta
	 */
	public void setFasta(String fasta);
}
