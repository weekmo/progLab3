package exercise3;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.biojava.nbio.alignment.Alignments;
import org.biojava.nbio.alignment.Alignments.PairwiseSequenceAlignerType;
import org.biojava.nbio.alignment.SimpleGapPenalty;
import org.biojava.nbio.alignment.SubstitutionMatrixHelper;
import org.biojava.nbio.alignment.template.GapPenalty;
import org.biojava.nbio.alignment.template.PairwiseSequenceAligner;
import org.biojava.nbio.alignment.template.SequencePair;
import org.biojava.nbio.alignment.template.SubstitutionMatrix;
import org.biojava.nbio.core.exceptions.CompoundNotFoundException;
import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.ProteinSequence;
import org.biojava.nbio.core.sequence.compound.AminoAcidCompound;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;

/**
 * The Exercise3 class is to solve Handout10-exercise3, the exercise is about Using Sequence Alignment.
 * It reads NCBI identifiers as a command line parameters and gets documents as xml and collects meta data,
 * sequence, protein(s) information, and etc and Sequence Alignment.
 * There are some arguments:
 * 		first-identifier: NCBI identifier (necessary).
 * 		second-identifier: NCBI identifier (necessary).
 * 		sequence  : Exhibit the sequence.
 * 		dna		  : Exhibit the DNA alignment.
 * 		protein	  : Exhibit the protein(s) alignment.
 * 
 * Example: -id1 NM_002524.4 -id2 NG_007524.1 -d
 * or	  : -id1 NM_002524.4 -id2 NG_007524.1 -p
 * 
 * @author laplace
 *
 */
public class Exercise3 {

	public static void main(String[] args) {
		// Create and initialise command line objects
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		
		//setup options
		Option id1=new Option("id1", "first-identifier", true, "NCBI identifier!");
		id1.setRequired(true);
		options.addOption(id1);
		
		Option id2=new Option("id2", "second-identifier", true, "NCBI identifier!");
		id2.setRequired(true);
		options.addOption(id2);

		options.addOption(new Option("d", "dna", false, "Print DNA Alignment!"));
		
		options.addOption(new Option("p", "protein", false, "Print protein Alignment!"));

		//Initialise command line
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp("Exercise3 - Sequence Alignment\n"
					+ "Please provide parameters as shown below, NCBI identifierier is necessary!", options);
			System.exit(1);
			return;
		}

		// Read value from command line
		String id1Value = cmd.getOptionValue("id1");
		String id2Value = cmd.getOptionValue("id2");
		
		IDNA dna1=new NCBISequenceApi(id1Value);
		IDNA dna2=new NCBISequenceApi(id2Value);
		
		if(cmd.hasOption("dna")) {
			printDNAAlignment(dna1.getSequence(), dna2.getSequence());
		}
		if(cmd.hasOption("protein") || cmd.getOptions().length==2) {
			printAAAlignment(dna1.getProtein().get(0).getSequence(), dna2.getProtein().get(0).getSequence());
		}
		
	}
	
	/**
	 * Print DNA alignment result
	 * @param seq1
	 * @param seq2
	 */
	static void printDNAAlignment(String seq1,String seq2) {
	
		DNASequence dna1Sequence=null;
		DNASequence dna2Sequence=null;
		try {
			dna1Sequence = new DNASequence(seq1);
			dna2Sequence = new DNASequence(seq2);
		} catch (CompoundNotFoundException e) {
			e.printStackTrace();
		}

		SubstitutionMatrix<NucleotideCompound> matrix = SubstitutionMatrixHelper.getNuc4_2();

		GapPenalty penalty = new SimpleGapPenalty();

		int gop = 8;
		int extend = 1;
		penalty.setOpenPenalty(gop);
		penalty.setExtensionPenalty(extend);

		PairwiseSequenceAligner<DNASequence, NucleotideCompound> aligner =
				Alignments.getPairwiseAligner(dna1Sequence, dna2Sequence, PairwiseSequenceAlignerType.LOCAL, penalty, matrix);
		SequencePair<DNASequence, NucleotideCompound> pair = aligner.getPair();
		System.out.println("Local alignment (Smith-Waterman algorithm)");
		System.out.println(pair.toString(60));
		
		aligner =Alignments.getPairwiseAligner(dna1Sequence, dna2Sequence, PairwiseSequenceAlignerType.GLOBAL, penalty, matrix);
		pair = aligner.getPair();
		System.out.println("Global alignment (Needleman-Wunsch algorithm)");
		System.out.println(pair.toString(60));
	}
	
	/**
	 * Print Amino Acids alignment result
	 * @param seq1
	 * @param seq2
	 */
	static void printAAAlignment(String seq1,String seq2) {
		ProteinSequence protein1Sequence=null;
		ProteinSequence protein2Sequence=null;
		try {
			protein1Sequence = new ProteinSequence(seq1);
			protein2Sequence = new ProteinSequence(seq2);
		} catch (CompoundNotFoundException e) {
			e.printStackTrace();
		}
		
		SubstitutionMatrix<AminoAcidCompound> matrix = SubstitutionMatrixHelper.getBlosum65();

		GapPenalty penalty = new SimpleGapPenalty();

		int gop = 8;
		int extend = 1;
		penalty.setOpenPenalty(gop);
		penalty.setExtensionPenalty(extend);


		PairwiseSequenceAligner<ProteinSequence, AminoAcidCompound> aligner =
				Alignments.getPairwiseAligner(protein1Sequence, protein2Sequence, PairwiseSequenceAlignerType.LOCAL, penalty, matrix);
		SequencePair<ProteinSequence, AminoAcidCompound> pair = aligner.getPair();
		System.out.println(pair.toString(60));
		
		aligner =Alignments.getPairwiseAligner(protein1Sequence, protein2Sequence, PairwiseSequenceAlignerType.GLOBAL, penalty, matrix);
		pair = aligner.getPair();
		System.out.println(pair.toString(60));
	}
}
