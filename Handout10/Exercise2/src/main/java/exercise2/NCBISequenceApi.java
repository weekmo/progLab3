package exercise2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A class to hold NCBI information
 * @author laplace
 *
 */
public class NCBISequenceApi implements IDNA {
	private ArrayList<IMolecule> protein;
	private String strandedness;
	private String locus;
	private int length;
	private String moleculeType;
	private String topology;
	private String division;
	private String updateDate;
	private String createDate;
	private String definition;
	private String primaryAccession;
	private String accessionVersion;
	private String source;
	private String organism;
	private String taxonomy;
	private String sequence;
	private String fasta;
	private String summary;
	private String text;
	/**
	 * Constructor
	 */
	public NCBISequenceApi() {
		this.protein=new ArrayList<IMolecule>();
	}
	
	/**
	 * Constructor
	 * @param accessionVersion
	 */
	public NCBISequenceApi(String accessionVersion){
		this(accessionVersion,"nuccore");
	}
	
	/**
	 * Constructor
	 * @param accessionVersion
	 * @param database Name
	 */
	public NCBISequenceApi(String accessionVersion,String db) {
		this();
		this.setAccessionVersion(accessionVersion);
		this.getDataFromApi(accessionVersion,db);
	}
	
	/**
	 * Get data from NCBI
	 * @param id
	 * @param db
	 */
	public void getDataFromApi(String id,String db) {
		URL urlXML=null;
		URL urlFasta=null;
		Document docMeta=null;
		try {
			urlXML=new URL("https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db="+db+"&id="+id+"&retmode=xml");
			urlFasta=new URL("https://www.ncbi.nlm.nih.gov/sviewer/viewer.cgi?save=file&db="+db+"&report=fasta&val="+id);
			docMeta =DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(urlXML.openStream());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Please put right identifier, e.g: NM_002524.4 or NG_007524.1");
			//e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		this.setFasta(this.getFasta(urlFasta));
		
		Node comment=docMeta.getElementsByTagName("GBSeq_comment").item(0);
		this.setSummary(this.getSummary(comment.getTextContent()));
		
		NodeList nodeList2 = docMeta.getElementsByTagName("GBSeq").item(0).getChildNodes();
		for(int i=0;i<nodeList2.getLength();i++) {
			String fieldName=this.correctFieldName(nodeList2.item(i).getNodeName());
			if(isField(fieldName)) {
				this.setField(fieldName, nodeList2.item(i).getTextContent());
			}
		}
		if(db.equals("nuccore")){
			NodeList nodeList1 = docMeta.getElementsByTagName("GBQualifier");
			for(int i=0;i<nodeList1.getLength();i++) {
				NodeList qualifiers=nodeList1.item(i).getChildNodes();
				if(qualifiers.item(1).getTextContent().equals("protein_id")) {
					this.protein.add(new NCBISequenceApi(qualifiers.item(3).getTextContent(),"protein"));
				}
			}
		}
	}
	
	/**
	 * Call url and get fasta sequence
	 * @param url
	 * @return fasta
	 */
	private String getFasta(URL url) {
		String sequence="";
		Scanner reader=null;
		try {
			reader=new Scanner(url.openStream());
			while(reader.hasNext()) {
				sequence+=reader.nextLine()+"\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			reader.close();
		}
		return sequence;
	}

	/**
	 * Extract summary from xml
	 * @param pattern
	 * @return summary
	 */
	private String getSummary(String pattern) {
		for(String summary:pattern.split("~")) {
			if(summary.startsWith("Summary")) {
				return summary.split(": ")[1];
			}
		}
		return null;
	}
	/**
	 * Format output
	 * @param key
	 * @param value
	 */
	private void format(String key,String value) {
		this.text+=String.format("%-10s| %s\n", key,value);
	}
	
	/**
	 * correct fields names
	 * @param fieldName
	 * @return correctField
	 */
	private String correctFieldName(String fieldName) {
		fieldName=fieldName.replace("GBSeq_", "");
		if(fieldName.equals("moltype")) {
			fieldName="moleculeType";
		}
		else if(fieldName.equals("update-date")) {
			fieldName="updateDate";
		}
		else if(fieldName.equals("create-date")) {
			fieldName="createDate";
		}
		else if(fieldName.equals("primary-accession")) {
			fieldName="primaryAccession";
		}
		else if(fieldName.equals("accession-version")) {
			fieldName="accessionVersion";
		}
		return fieldName;
	}
	
	/**
	 * Check it the field is part of NCBISequenceApi class
	 * @param field
	 * @return boolean
	 */
	private boolean isField(String field) {
		try {
			this.getClass().getDeclaredField(field);
			return true;
		} catch (NoSuchFieldException e) {
			return false;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Set field value by sending field name as a key and its value as value
	 * @param key
	 * @param value
	 */
	private void setField(String key,String value) {
		if(key.equals("locus")) {
			this.setLocus(value);
		}
		else if(key.equals("length")) {
			this.setLength(Integer.parseInt(value));
		}
		else if(key.equals("moleculeType")) {
			this.setMoleculeType(value);
		}
		else if(key.equals("topology")) {
			this.setTopology(value);
		}
		else if(key.equals("division")) {
			this.setDivision(value);
		}
		else if(key.equals("updateDate")) {
			this.setUpdateDate(value);
		}
		else if(key.equals("createDate")) {
			this.setCreateDate(value);
		}
		else if(key.equals("definition")) {
			this.setDefinition(value);
		}
		else if(key.equals("primaryAccession")) {
			this.setPrimaryAccession(value);
		}
		else if(key.equals("accessionVersion")) {
			this.setAccessionVersion(value);
		}
		else if(key.equals("source")) {
			this.setSource(value);
		}
		else if(key.equals("organism")) {
			this.setOrganism(value);
		}
		else if(key.equals("taxonomy")) {
			this.setTaxonomy(value);
		}
		else if(key.equals("sequence")) {
			this.setSequence(value);
		}
		else if(key.equals("fasta")) {
			this.setFasta(value);
		}
	}
	
	/**
	 * Methods implementation from interface
	 */
	public String getMetaData() {
		this.text="";
		this.format("LOCUS", this.getLocus()+"\t"+
				this.getLength()+"\t"+
				this.getMoleculeType()+"\t"+
				this.getTopology()+"\t"+
				this.getDivision()+"\t"+
				this.getUpdateDate());
		this.format("DEFINITION", this.getDefinition());
		this.format("ACCESSION", this.getPrimaryAccession());
		this.format("VERSION", this.getAccessionVersion());
		this.format("SOURCE", this.getSource());
		this.format("Summary", this.getSummary());
		return this.text;
	}
	
	public String getAccessionVersion() {return this.accessionVersion;}
	
	public void setAccessionVersion(String accessionVersion) { this.accessionVersion=accessionVersion;}
	
	public String getLocus() {return this.locus;}
	
	public void setLocus(String locus) {this.locus=locus;}

	public int getLength() {return this.length;}

	public void setLength(int length) {this.length=length;}

	public String getMoleculeType() {return this.moleculeType;}

	public void setMoleculeType(String moleculeType) {this.moleculeType=moleculeType;}

	public String getTopology() {return this.topology;}

	public void setTopology(String topology) {this.topology=topology;}

	public String getDivision() {return this.division;}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getPrimaryAccession() {
		return primaryAccession;
	}

	public void setPrimaryAccession(String primaryAccession) {
		this.primaryAccession = primaryAccession;
	}

	public String getDefinition() {
		return definition;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getOrganism() {
		return organism;
	}

	public void setOrganism(String organism) {
		this.organism = organism;
	}

	public String getTaxonomy() {
		return taxonomy;
	}

	public void setTaxonomy(String taxonomy) {
		this.taxonomy = taxonomy;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
		
	}
	public String getSequence() {
		// TODO Auto-generated method stub
		return this.sequence;
	}
	public String getFasta() {
		// TODO Auto-generated method stub
		return this.fasta;
	}
	public void setFasta(String fasta) {
		this.fasta=fasta.toUpperCase();
	}
	public ArrayList<IMolecule> getProtein() {
		return this.protein;
	}

	public String getStrandedness() {
		return this.strandedness;
	}

	public void setStrandedness(String strandedness) {
		this.strandedness=strandedness;
	}
}
