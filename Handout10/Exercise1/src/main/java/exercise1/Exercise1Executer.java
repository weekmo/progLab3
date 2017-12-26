package exercise1;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * The Exercise1Executer class is to solve Handout10-exercise1, the exercise is about Using web APIs.
 * It reads ENA identifier as a command line parameter and gets a document as xml and collects meta data,
 * sequence, protein(s) information, and etc and exhibit the information in the screen.
 * There are some arguments:
 * 		ena-id    : ENA identifier (necessary).
 * 		proteins  : Exhibit protein(s) information.
 * 		sequence  : Exhibit sequence.
 * 		amino-acid: Exhibit Amino Acid(s) sequence(s) of protein(s).
 * 
 * Example: -id BN000065 -p -a
 * or	  : -id AF163095 -p -a -s
 * @author laplace
 *
 */

public class Exercise1Executer {

	public static void main(String[] args) {
		// Create and initialise command line objects
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		
		//setup options
		Option enaId=new Option("id", "ena-id", true, "ENA identifier!");
		enaId.setRequired(true);
		options.addOption(enaId);

		options.addOption(new Option("p", "proteins", false, "Exhibit protein(s) information!"));
		
		options.addOption(new Option("s", "sequence", false, "Exhibit sequence"));
		
		options.addOption(new Option("a", "amino-acid", false, "Exhibit Amino Acid(s) sequence(s) of protein(s)"));

		//Initialise command line
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp("Exercise1 - Using web APIs\n"
					+ "Please provide parameters as shown below, ENA identifier is necessary!", options);
			System.exit(1);
			return;
		}

		// Read value from command line
		String id = cmd.getOptionValue("ena-id");
		ENADocument document=getENADocument(id);
		
		printDocument(document, "metad-data");
		if(cmd.hasOption("p")) {
			printDocument(document, "proteins");
		}
		if(cmd.hasOption("a")) {
			printDocument(document, "proteinsAminoAcids");
		}
		if(cmd.hasOption("s")) {
			printDocument(document, "sequence");
		}
	}
	
	/**
	 * The function takes ENA ID and return information in {@code ENADocument}
	 * @param id
	 * @return ENADocument
	 */
	static ENADocument getENADocument(String id) {
		String url="https://www.ebi.ac.uk/ena/data/view/"+id+"&display=";
		URL urlMeta=null;
		URL urlFasta=null;
		Document docMeta=null;
		try {
			urlMeta=new URL(url+"xml");
			urlFasta=new URL(url+"fasta&download=fasta");
			docMeta =DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(urlMeta.openStream());
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		ENADocument document=new ENADocument(docMeta.getElementsByTagName("description").item(0).getTextContent());
		
		document.setSequence(docMeta.getElementsByTagName("sequence").item(0).getTextContent());
		document.setFasa(getFasta(urlFasta));
		document.setOrganism(getDescription(docMeta.getElementsByTagName("qualifier")));
		
		Node data1 = docMeta.getElementsByTagName("entry").item(0);
		document.setTopology(data1.getAttributes().getNamedItem("taxonomicDivision").getNodeValue());
		document.setMoleculeType(data1.getAttributes().getNamedItem("moleculeType").getNodeValue());
		document.setSequenceLength(Integer.parseInt(data1.getAttributes().getNamedItem("sequenceLength").getNodeValue()));
		
		NodeList feature = docMeta.getElementsByTagName("feature");
		
		for(int i=0;i<feature.getLength();i++) {
			if(feature.item(i).getAttributes().getNamedItem("name").getNodeValue().equals("CDS")) {
				String gene="";
				String protein_id="";
				String protein="";
				String translation="";
				String note="";
				NodeList proteins=feature.item(i).getChildNodes();
				for(int j=0;j<proteins.getLength();j++) {
					if(proteins.item(j).getNodeName().equals("qualifier")) {
						if(proteins.item(j).getAttributes().getNamedItem("name").getNodeValue().equals("gene")) {
							gene=proteins.item(j).getTextContent().split("\n")[2];
						}
						else if(proteins.item(j).getAttributes().getNamedItem("name").getNodeValue().equals("product")) {
							protein=proteins.item(j).getTextContent().split("\n")[2];
						}
						else if(proteins.item(j).getAttributes().getNamedItem("name").getNodeValue().equals("protein_id")) {
							protein_id=proteins.item(j).getTextContent().split("\n")[2];
						}
						else if(proteins.item(j).getAttributes().getNamedItem("name").getNodeValue().equals("translation")) {
							translation=proteins.item(j).getTextContent().split("\n")[2];
						}
						else if(proteins.item(j).getAttributes().getNamedItem("name").getNodeValue().equals("note")) {
							note=proteins.item(j).getTextContent().split("\n")[2];
						}
					}
				}
				document.addProtein(gene, protein_id, protein, translation,note);
			}
		}
		return document;
	}
	
	/**
	 * The function takes url and return fasta sequence!
	 * @param url
	 * @return sequence
	 */
	static String getFasta(URL url) {
		String sequence="";
		Scanner reader=null;
		try {
			reader=new Scanner(url.openStream());
			while(reader.hasNext()) {
				sequence+=reader.nextLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			reader.close();
		}
		return sequence;
	}
	
	/**
	 * Get organism name
	 * @param nodeList
	 * @return organism
	 */
	static String getDescription(NodeList nodeList) {
		for(int j=0;j<nodeList.getLength();j++) {
			if(nodeList.item(j).getAttributes().getNamedItem("name").getNodeValue().equals("organism")) {
				return nodeList.item(j).getTextContent().split("\n")[2];
			}
		}
		return null;
	}
	
	/**
	 * Print out document information
	 * @param document
	 * @param selectedInfo
	 */
	static void printDocument(ENADocument document,String selectedInfo) {
		if(selectedInfo.equals("metad-data")) {
			System.out.println(String.format("%-15s|%s","Description",document.getDescription()));
			System.out.println(String.format("%-15s|%s","Organism",document.getOrganism()));
			System.out.println(String.format("%-15s|%s","Molecule Type",document.getMoleculeType()));
			System.out.println(String.format("%-15s|%s","Topology",document.getTopology()));
			System.out.println(String.format("%-15s|%s","Sequence Length",document.getSequenceLength())+"\n");
		}
		else if(selectedInfo.equals("proteins")) {
			printLine();
			System.out.println(String.format("%-5s|%-7s|%-30s|%s" , "Gene","Protein ID","Protein Name","Note"));
			printLine();
			for(Protein prot:document.getProteins()) {
				System.out.println(String.format("%-5s|%-7s|%-30s|%s" , prot.getGene(),
						prot.getProtein_id(),prot.getProtein(),prot.getNote()));
			}
			printLine();
		}
		else if(selectedInfo.equals("sequence")) {
			System.out.println(document.getSequence().toUpperCase());
		}
		else if(selectedInfo.equals("proteinsAminoAcids")) {
			for(Protein prot:document.getProteins()) {
				System.out.println("\nProtein ID:"+prot.getProtein_id());
				System.out.println("Protein Name:"+prot.getProtein());
				System.out.println("Amino Acid Sequence:");
				System.out.println(prot.getTranslation());
				printLine();
			}
		}
	}
	
	/**
	 * Helper function to print a line
	 */
	static void printLine() {
		for(int i=0;i<70;i++) {
			System.out.print("-");
		}
		System.out.print("\n");
	}
}
