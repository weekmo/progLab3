package exercise3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * A class to solve Handout9 - exercise3, the exercise is about PubMed Metadata. It reads PMIDs from
 * file or command line and gets the documents as xml(s) and collects MeSH term form them. Then, count the terms' numbers
 * and print sorted list to screen or a file by request of the user.
 * 
 * There are some (optional) arguments:
 * 		input-file : A file contains PMIDs.
 * 		output-file: A file to write output (MeSH terms and there frequencies).
 * 		output	   : An argument to define the output(csv or screen).
 * 		pmid-list  : An argument contains PMIDs separated by |.
 * 
 * Example: -i data/pmids -o data/output.csv -op screen
 * or	  : -o data/output.csv -op cv -pl 23830913|22434822|16696577
 * @author laplace
 *
 */
public class Exercise3 {

	static ArrayList<String> notFoundArticles;
	public static void main(String[] args) {
		// Create and initialise command line objects
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		
		//setup options
		Option inputFile = new Option("i", "input-file", true, "Input folder contains images!");
		options.addOption(inputFile);

		Option outputFile = new Option("o", "output-file", true, "Output folder to save output images!");
		options.addOption(outputFile);

		Option output = new Option("op", "output", true, "Image format for output image(s)");
		options.addOption(output);
		
		Option pmidList = new Option("pl", "pmid-list", true, "Image format for output image(s)");
		options.addOption(pmidList);

		//Initialise command line
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp("Exercise3 - PubMed Metadata", options);
			System.exit(1);
			return;
		}

		// Read values from command line
		String inFileName = cmd.getOptionValue("input-file");
		String outFileName = cmd.getOptionValue("output-file","data/output.csv");
		String outputValue = cmd.getOptionValue("output","csv");
		String pmidListValue = cmd.getOptionValue("pmid-list");
		/*
		 * A list of all MeSH terms for all documents
		 */
		Map<String,MeshHeadingCollection> meshList=null;
		
		try {
			if(isReadPmidsFromFile(pmidListValue, inFileName)) {
				if(inFileName==null) {
					inFileName="data/pmids";
				}
				// Read PMIDs from a file
				meshList=readPMIDsFromFile(inFileName);
			}
			else {
				// Read PMIDs from command line
				meshList=readPMIDsCommandLine(pmidListValue);
			}
		}catch(Exception e) {
			// If both pmid-list and input-file provided
			System.err.println(e.getMessage());
			System.exit(1);
		}
		
		/*
		 * An additional list with all used MeSH terms and their number.
		 */
		MeshHeadingCollection meshHedingListCollection=getMeshHeadingListCollection(meshList);
		
		if(outputValue.equalsIgnoreCase("csv")) {
			// Save file to csv file
			csvFileWriter(meshHedingListCollection, outFileName);
		}else if(outputValue.equalsIgnoreCase("screen")) {
			// Print to screen
			int space = meshHedingListCollection.getLongestName();
			System.out.println(String.format("%-13s|%-"+space+"s|%s" , "UI", "MeSH Name","Total"));
			for(int i=0;i<(20+space);i++)System.out.print("-");
			System.out.println("");
			for(MeshHeading mesh:meshHedingListCollection.getMeshHedingList()) {
				System.out.println(String.format("%-13s|%-"+space+"s|%s" , mesh.getUi(), mesh.getName(),mesh.getCounter()));
			}
		}
	}
	
	/**
	 * A function to save {@code MeshHeadingCollection} to csv file
	 * @param meshList
	 * @param fileName
	 */
	static void csvFileWriter(MeshHeadingCollection meshList,String fileName) {
		BufferedWriter writer=null;
		try {
			writer=new BufferedWriter(new FileWriter(fileName));
			for(MeshHeading mesh:meshList.getMeshHedingList()) {
				writer.write("\""+mesh.getName()+"\";"+mesh.getCounter()+"\n");
			}
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * A function build a url depends on {@code pubmedID}
	 * @param pubmedID
	 * @return PubMed URL
	 */
	static URL getURL(String pubmedID) {
		String urlString="https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=pubmed&id="+
				pubmedID+"&retmode=xml";
		URL url=null;
		try {
			url= new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * A function to decide writing to a file or print to screen
	 * @param pmidlist
	 * @param fileName
	 * @return boolean
	 * @throws Exception
	 */
	static boolean isReadPmidsFromFile(String pmidlist,String fileName) throws Exception {
		if((pmidlist==null&&fileName==null)||
				(pmidlist==null&&fileName!=null)) {
			return true;
		}else if(pmidlist!=null&&fileName!=null) {
			throw new Exception("Setup parameters for input file, PMID-list or nothing!"); 
		}
		return false;
	}
	
	/**
	 * A function to get MeSH terms by using PMIDs provided in a file
	 * @param fileName
	 * @return A collection of PMIDs and there MeSH terms
	 */
	static Map<String,MeshHeadingCollection> readPMIDsFromFile(String fileName) {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		Map<String,MeshHeadingCollection> meshTermList=new HashMap<String, MeshHeadingCollection>();
		notFoundArticles=new ArrayList<String>();
		BufferedReader reader=null;
		try {
			DocumentBuilder builder = dfactory.newDocumentBuilder();
			reader = new BufferedReader(new FileReader(fileName));
			String pubmedID;
			while((pubmedID=reader.readLine())!=null) {
				InputSource source = new InputSource(getURL(pubmedID).openStream());
				Document doc = builder.parse(source);
				NodeList MeshHeadingList = doc.getElementsByTagName("MeshHeading");
				if(MeshHeadingList.getLength()<1) {
					notFoundArticles.add(pubmedID);
				}else {
					MeshHeadingCollection tempMeshCollection=new MeshHeadingCollection();
					for(int i=0;i<MeshHeadingList.getLength();i++) {
						Node MeshHeadline = ((Element)MeshHeadingList.item(i)).getElementsByTagName("DescriptorName").item(0);
						String ui=MeshHeadline.getAttributes().getNamedItem("UI").getTextContent();
						String name=MeshHeadline.getTextContent();
						tempMeshCollection.addMeshHeding(new MeshHeading(ui, name));
					}
					meshTermList.put(pubmedID, tempMeshCollection);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return meshTermList;
	}
	
	/**
	 * A function to get MeSH terms by using PMIDs provided in the command line
	 * @param pmidList
	 * @return A collection of PMIDs and there MeSH terms
	 */
	static Map<String,MeshHeadingCollection> readPMIDsCommandLine(String pmidList) {
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		Map<String,MeshHeadingCollection> meshTermList=new HashMap<String, MeshHeadingCollection>();
		notFoundArticles=new ArrayList<String>();
		try {
			DocumentBuilder builder = dfactory.newDocumentBuilder();
			String[] pmidListList=pmidList.split("\\|");
			for(String pubmedID:pmidListList) {
				InputSource source = new InputSource(getURL(pubmedID).openStream());
				Document doc = builder.parse(source);
				NodeList MeshHeadingList = doc.getElementsByTagName("MeshHeading");
				if(MeshHeadingList.getLength()<1) {
					notFoundArticles.add(pubmedID);
				}else {
					MeshHeadingCollection tempMeshCollection=new MeshHeadingCollection();
					for(int i=0;i<MeshHeadingList.getLength();i++) {
						Node MeshHeadline = ((Element)MeshHeadingList.item(i)).getElementsByTagName("DescriptorName").item(0);
						String ui=MeshHeadline.getAttributes().getNamedItem("UI").getTextContent();
						String name=MeshHeadline.getTextContent();
						tempMeshCollection.addMeshHeding(new MeshHeading(ui, name));
					}
					meshTermList.put(pubmedID, tempMeshCollection);
				}
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return meshTermList;
	}
	
	/**
	 * A function to get a list with all used MeSH terms and their number.
	 * @param meshList
	 * @return all used MeSH terms and their number
	 */
	static MeshHeadingCollection getMeshHeadingListCollection(Map<String,MeshHeadingCollection> meshList) {
		MeshHeadingCollection meshHeadingCollection=new MeshHeadingCollection();
		for(String pmid:meshList.keySet()) {
			for(MeshHeading mesh:meshList.get(pmid).getMeshHedingList()) {
				meshHeadingCollection.addMeshHeding(mesh);
			}
		}
		return meshHeadingCollection;
	}
}
