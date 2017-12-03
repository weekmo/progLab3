package exercise3;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.plugin.RGBStackMerge;
import ij.process.ImageConverter;

/**
 * Executor class has main function to start the application<br/>
 * it contain solution for Handout7-exercise3
 * 
 * @author laplace
 */
public class Executor {

	public static void main(String[] args) {

		// Create and initialise objects
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		
		/*
		 * There are two command line parameters:
		 * (1) dna: has to follow by DNA image file name
		 * (2) actin: has to follow by Actin image file name
		 */
		
		Option dna = new Option("d", "dna", true, "DNA image file name");
		dna.setRequired(true);
		options.addOption(dna);

		Option actin = new Option("a", "actin", true, "Actin image file name");
		actin.setRequired(true);
		options.addOption(actin);

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp("Exercise3-Merge channels", options);
			System.exit(1);
			return;
		}

		// Read files names from command line
		String dnaFilename = cmd.getOptionValue("dna");
		String actinFilename = cmd.getOptionValue("actin");
		//System.out.println(dnaFilename +" - "+actinFilename);
		
		// Read images (and convert them to gray scale)
		ImagePlus img1 = new ImagePlus("data/"+dnaFilename);
		ImageConverter imgConverter1 = new ImageConverter(img1);
		imgConverter1.convertToGray8();

		ImagePlus img2 = new ImagePlus("data/"+actinFilename);
		ImageConverter imgConverter2 = new ImageConverter(img2);
		imgConverter2.convertToGray8();

		// Merge two images in one image and save it
		ImagePlus[] images = { img1, img2 };
		ImagePlus img3 = RGBStackMerge.mergeChannels(images, false);
		FileSaver fileSaver = new FileSaver(img3);
		fileSaver.saveAsPng("data/dualchannel.png");
		fileSaver.saveAsTiff("data/dualchannel.tif");
	}

}
