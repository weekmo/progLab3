package main;

import java.util.Scanner;

import org.apache.commons.cli.*;
import exercise1.Exercise1;
import exercise2.Exercise2;
import exercise3.Exercise3;

/**
 * Handout6!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	if(args.length==0) {
    		try {
	    		Scanner reader=new Scanner(System.in);
	    		
	    		System.out.println("Please enter file name for EX1:");
	    		//new Exercise1("documents.csv");
	    		new Exercise1(reader.nextLine());
	    		reader.reset();
	    		
	    		System.out.println("\nPlease enter number of nodes for EX2:");
	    		//new Exercise2(20);
	    		new Exercise2(Integer.parseInt(reader.nextLine()));
	    		reader.reset();

	    		System.out.println("\nPlease enter file name, sorce gene and target gene respectivly\n"
	    				+ "and pleas leave space between them like!\n"
	    				+ "filterredFIsInGene2.csv AAAS EIF4A1");
	    		//new Exercise3("filterredFIsInGene2.csv","AAAS","EIF4A1");
	    		String[] attr = reader.nextLine().split(" ");
	    		new Exercise3(attr[0],attr[1],attr[2]);
	    		reader.close();
    		}
    		catch(Exception e) {e.printStackTrace();}
    	}
    	else {
	    	Options options = new Options();
	    	CommandLine cmd;
	    	/**
	    	 * We have 5 options for different arguments that use in CmmandLine.
	    	 * e1: to provide file name of CSV file and print out the journal publisher
	    	 * 		that has biggest number of publications.
	    	 * e2: to provide nodes number that use to generate random social network
	    	 * e3_filename: to provide file name for file use to create a network of
	    	 * 		protein interactions.
	    	 * e3_gene1: used to provide source gene for protein interaction path.
	    	 * e3_gene2: used to provide target gene for protein interaction path.
	    	 */
	    	options.addOption("e1", true, "Ecercise 1, File name");
	    	options.addOption("e2", true, "Ecercise 2, Number of nodes");
	    	options.addOption("e3_filename", true, "Ecercise 3, Reactom protein interaction file name");
	    	options.addOption("e3_gene1", true, "Ecercise 3, gene path source");
	    	options.addOption("e3_gene2", true, "Ecercise 3, gene path target");
	    	try {
				cmd = new BasicParser().parse(options, args);
				new Exercise1(cmd.getOptionValue("e1"));
		    	new Exercise2(Integer.parseInt(cmd.getOptionValue("e2")));
		    	new Exercise3(cmd.getOptionValue("e3_filename"),
		    			cmd.getOptionValue("e3_gene1"),
		    			cmd.getOptionValue("e3_gene2"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	
	}
}
