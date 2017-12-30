package exercise2;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * The Exercise2 class is to solve Handout10-exercise2, the exercise is about Using web APIs.
 * It reads NCBI identifier as a command line parameter and gets a document as xml and collects meta data,
 * sequence, protein(s) information, and etc and exhibit the information in the screen.
 * There are some arguments:
 * 		identifier: NCBI identifier (necessary).
 * 		sequence  : Exhibit the sequence.
 * 		info	  : Exhibit the relevant meta information.
 * 		protein	  : Exhibit the connected protein sequence.
 * 
 * Example: -id NM_002524.4 -i -s -p
 * or	  : -id NG_007524.1 -i -s
 * or	  : -id NG_007524.1
 * 
 * @author laplace
 *
 */
public class Exercise2 {
	
	/**
	 * Main function
	 * @param args
	 */
	public static void main(String[] args) {
		// Create and initialise command line objects
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		
		//setup options
		Option Id=new Option("id", "identifier", true, "NCBI identifier!");
		Id.setRequired(true);
		options.addOption(Id);

		options.addOption(new Option("s", "sequence", false, "Print the sequence!"));
		
		options.addOption(new Option("i", "info", false, "Print relevant meta information!"));
		
		options.addOption(new Option("p", "protein", false, "Print the connected protein sequence!"));

		//Initialise command line
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp("Exercise2 - Using web APIs II\n"
					+ "Please provide parameters as shown below, NCBI identifier is necessary!", options);
			System.exit(1);
			return;
		}

		// Read value from command line
		String idValue = cmd.getOptionValue("identifier");
		
		//Create an object from MRna interface and initialise it by NCBISequenceApi constructor
		IDNA data= new NCBISequenceApi(idValue);
		//If info parameter provided or only identifier
		if(cmd.hasOption("info") || cmd.getOptions().length==1) {
			System.out.println(data.getMetaData());
		}
		//If sequence parameter provided
		if(cmd.hasOption("sequence")) {
			/*
			 * we can use data.getSequence() as that provided in the XML,
			 * so we don't need to call the API again, But we need to fulfil exercise requirement.
			 */
			System.out.println(data.getFasta());
		}
		//If protein parameter provided
		if(cmd.hasOption("protein")) {
			/*
			 * we can use data.getTranslation() as that provided in the XML,
			 * so we don't need to call the API again, But we need to fulfil exercise requirement.
			 */
			for(IMolecule molecule:data.getProtein()) {
				System.out.println(molecule.getFasta());
			}
		}
	}
}
