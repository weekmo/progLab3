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
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import ij.IJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.io.FileSaver;
import ij.measure.ResultsTable;
import ij.plugin.filter.ParticleAnalyzer;
import ij.process.ImageConverter;
/**
 * A class to solve Handout8 - exercise2, the exercise is about Particle Analyses. it takes
 * images and counts the contents of these images depends on threshold method used.
 * the application takes several command line arguments:
 * 		input-folder: the folder name that contains original images
 * 		output-folder: folder name that will contain processed images
 * 		stats-format: Statistical format like: full, count, max or min
 * 					full mode will show minimum number of particles among the images,
 * 					maximum, average and standard deviation.
 * 		actin: doesn't require value, if the argument provided it will process
 * 				only actin images
 * 		dna: doesn't require value, if the argument provided it will process
 * 				only DNA images
 * Example: -i data -o out_images -s full
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
		/*
		 * the application takes several command line arguments:
		 * 		input-folder: the folder name that contains original images
		 * 		output-folder: folder name that will contain processed images
		 * 		stats-format: Statistical format like: full, count, max or min
		 * 					full mode will show minimum number of particles among the images,
		 * 					maximum, average and standard deviation.
		 * 		actin: doesn't require value, if the argument provided it will process
		 * 				only actin images
		 * 		dna: doesn't require value, if the argument provided it will process
		 * 				only DNA images
		 * Example: -i data -o out_images -s full
		 */

		// setup options
		Option inputFolder = new Option("i", "input-folder", true, "Input folder contains images!");
		inputFolder.setRequired(true);
		options.addOption(inputFolder);

		Option outputFolder = new Option("o", "output-folder", true, "Output folder to save processed images!");
		outputFolder.setRequired(true);
		options.addOption(outputFolder);

		Option stats = new Option("s", "stats-format", true, "Statistical format like: full, count, max or min!");
		stats.setRequired(true);
		options.addOption(stats);

		Option actin = new Option("a", "actin", false, "Filter only actin images!");
		options.addOption(actin);

		Option dna = new Option("d", "dna", false, "Filter only DNA images!");
		options.addOption(dna);

		// Initialise command line
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			e.printStackTrace();
			formatter.printHelp("Exercise2 - Reading images from a folder", options);
			System.exit(1);
			return;
		}

		// Read values from command line
		String inFolder = cmd.getOptionValue("input-folder");
		String outFolder = cmd.getOptionValue("output-folder");
		String statValue = cmd.getOptionValue("stats-format", "full");
		
		//Select stats-format
		DescriptiveStatistics statistic=null;
		if (!cmd.hasOption("actin") && !cmd.hasOption("dna")) {
			double[] values = analyzImages("d", inFolder, outFolder).getValues();
			statistic = analyzImages("f", inFolder, outFolder);
			for(double val:values) {
				statistic.addValue(val);
			}
		}
		else {
			if (cmd.hasOption("dna")) {
				statistic = analyzImages("d", inFolder, outFolder);
			}
			if (cmd.hasOption("actin")) {
				statistic = analyzImages("f", inFolder, outFolder);
			}
		}
		statistics(statValue,statistic);

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
	 * Function to filter images by DNA or Actin
	 * @param fileName
	 * @param dna_actin
	 * @return
	 */
	static boolean isDNA_Actin(String fileName, String dna_actin) {
		if (fileName.split("_")[2].equalsIgnoreCase(dna_actin)) {
			return true;
		}
		return false;
	}

	/**
	 * Function to read images and analyse them
	 * @param dna_actin
	 * @param inFolder
	 * @param outFolder
	 * @return DescriptiveStatistics
	 */
	static DescriptiveStatistics analyzImages(String dna_actin,String inFolder,String outFolder) {
		if(dna_actin.equalsIgnoreCase("d")) {
			System.out.println("\n\t--- Result for DNA images ---\n");
		}
		else if(dna_actin.equalsIgnoreCase("f")) {
			System.out.println("\n\t--- Result for actin images ---\n");
		}
		File path = new File(inFolder);
		File[] files = path.listFiles();
		ImagePlus img;
		ImageConverter ic;
		File imageFile;
		DescriptiveStatistics stats=new DescriptiveStatistics();
		for (int i = 0; i < files.length; i++) {
			imageFile = files[i];
			if (imageFile.isFile() && 
					isSupported(FilenameUtils.getExtension(imageFile.getName())) &&
					isDNA_Actin(imageFile.getName(), dna_actin)) {
				img = new ImagePlus(inFolder+"/" + imageFile.getName());
				ic = new ImageConverter(img);
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
				stats.addValue(ResultsTable.getResultsTable().getCounter());
				System.out.println("Number of cells in ["+img.getTitle()+"]: "+ResultsTable.getResultsTable().getCounter());
				imageSaver(img, outFolder);
			}
		}
		return stats;
	}

	/**
	 * Function to save images depends on there extension
	 * @param img
	 * @param outFolder
	 */
	static void imageSaver(ImagePlus img, String outFolder) {
		String extension = FilenameUtils.getExtension(img.getTitle());
		FileSaver saver = new FileSaver(img);
		File outFolderPath = new File(outFolder);
		outFolderPath.mkdir();
		String imagePath = outFolder + "/" + img.getTitle();
		if (extension.equalsIgnoreCase("tif") || extension.equalsIgnoreCase("tiff")) {
			saver.saveAsTiff(imagePath);
		} else if (extension.equalsIgnoreCase("gif")) {
			saver.saveAsGif(imagePath);
		} else if (extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg")) {
			saver.saveAsJpeg(imagePath);
		} else if (extension.equalsIgnoreCase("bmp")) {
			saver.saveAsBmp(imagePath);
		} else if (extension.equalsIgnoreCase("pgm") || extension.equalsIgnoreCase("pbm")
				|| extension.equalsIgnoreCase("ppm")) {
			saver.saveAsPgm(imagePath);
		} else if (extension.equalsIgnoreCase("png")) {
			saver.saveAsPng(imagePath);
		} else if (extension.equalsIgnoreCase("fits")) {
			saver.saveAsFits(imagePath);
		} else if (extension.equalsIgnoreCase("lut")) {
			saver.saveAsLut(imagePath);
		} else if (extension.equalsIgnoreCase("raw")) {
			saver.saveAsRaw(imagePath);
		} else {
			System.err.println("Sorry, Unsoppurted format!");
		}
	}

	/**
	 * function to filter stats-format parameter and print selected measure(s)
	 * @param statsFormat
	 * @param stats
	 */
	static void statistics(String statsFormat,DescriptiveStatistics stats) {
		System.out.println("\n\t --- Statistical Analysis ---");
		if (statsFormat.equalsIgnoreCase("max")) {
			System.out.println("Max: " + stats.getMax());
		} else if (statsFormat.equalsIgnoreCase("min")) {
			System.out.println("Min: " + stats.getMin());
		} else if (statsFormat.equalsIgnoreCase("mean")) {
			System.out.println("Average: " + stats.getMean());
		} else if (statsFormat.equalsIgnoreCase("std")) {
			System.out.println("Standard Deviation: " + stats.getStandardDeviation());
		} else if (statsFormat.equalsIgnoreCase("full")) {
			System.out.println("Max: " + stats.getMax());
			System.out.println("Min: " + stats.getMin());
			System.out.println("Average: " + stats.getMean());
			System.out.println("Standard Deviation: " + stats.getStandardDeviation());
		} else {
			System.out.println("Wrong --stats-format arguments (ex: max,min,mean,std or full)");
		}
	}
}
