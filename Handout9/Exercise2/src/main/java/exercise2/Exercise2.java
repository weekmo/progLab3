package exercise2;

import java.io.File;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;
import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.measure.ResultsTable;
import ij.plugin.filter.ParticleAnalyzer;
import ij.process.ImageConverter;
/**
 * A class to solve Handout9 - exercise2, the exercise is about Particle Analyses Pipeline. it takes
 * images and counts the contents of these images.
 * the application takes several command line arguments:
 * 		input: It can be a file or an image that specified by argument --type or simply -t,
 * 				the folder name that contains original images or a single images name has to be submitted.
 * 		output: folder name or output image name that will contain processed images or a single image
 * 
 * Example: -i data -o out_images
 * 		or: -i data/20P1_POS0002_F_2UL.tif -o out_image
 * 
 * @author laplace
 *
 */
public class Exercise2 {

	public static void main(String[] args) {

		// Create and initialise objects
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		
		// setup options
		Option inputFolder = new Option("i", "input", true, "Input folder contains images or a single image name!");
		inputFolder.setRequired(true);
		options.addOption(inputFolder);

		Option outputFolder = new Option("o", "output", true, "Output folder to save processed images or a single image name!");
		outputFolder.setRequired(true);
		options.addOption(outputFolder);

		// Initialise command line
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp("Exercise2 - Particle Analyses Pipeline", options);
			System.exit(1);
			return;
		}

		// Read values from command line
		String inputName = cmd.getOptionValue("input");
		String outputName = cmd.getOptionValue("output");

		analyzImages(inputName,outputName);
		System.exit(0);
	}

	/**
	 * Function to check image file is supported or not
	 * @param fileExtension
	 * @return isSupported
	 */
	static boolean isSupported(String fileExtension) {
		String[] formats = new String[] { "tiff", "tif", "gif", "jpeg", "jpg", "png", "dicom", "bmp", "pgm", "pbm",
				"ppm", "fits" };
		for (String extension : formats) {
			if (fileExtension.equalsIgnoreCase(extension)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Function to read images and analyse them
	 * @param input
	 * @param output
	 */
	static void analyzImages(String input,String output) {
		File file = new File(input);
		if(file.isDirectory()) {
			new File(output).mkdir();
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				File imageFile = files[i];
				analyzeImage(imageFile.toString(),output+"/"+FilenameUtils.getName(imageFile.toString()));
			}
		}else {
			analyzeImage(input,output);
		}
		
	}
	
	/**
	 * Function to read image and analyse it
	 * @param input
	 * @param output
	 */
	static void analyzeImage(String input,String output) {
		
		if (new File(input).isFile() && 
				isSupported(FilenameUtils.getExtension(input))) {
			ImagePlus img = new ImagePlus(input);
			ImageConverter ic = new ImageConverter(img);
			ic.convertToGray8();
			img.updateImage();
			/* Threshold values:
			 * Default,Huang,Intermodes,IsoData,IJ_IsoData,Li,MaxEntropy,Mean,MinError,Minimum,Moments,
			 * Otsu,Percentile,RenyiEntropy,Shanbhag,Triangle,Yen
			 */
			IJ.setAutoThreshold(img, "Default");
			IJ.run(img, "Convert to Mask", "Black Background");
			Prefs.blackBackground = true;
			IJ.run(img, "Watershed", "only");
			ParticleAnalyzer analyzer = new ParticleAnalyzer();
			analyzer.showDialog();
			analyzer.analyze(img);
			System.out.println("Number of cells in ["+img.getTitle()+"]:"+ResultsTable.getResultsTable().getCounter());
			IJ.save(img,output);
		}
	}
}
