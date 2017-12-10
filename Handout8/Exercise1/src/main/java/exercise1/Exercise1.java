package exercise1;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;

import ij.ImagePlus;
import ij.io.FileSaver;
import ij.process.ImageProcessor;

/**
 * A class to solve Handout8 - exercise1, the exercise is about resizing images to percentage value
 * that has to submit as an argument parameter. In addition, the user has to submit two other parameters,
 * first one is the folder name (--input-folder or simply -i) that contain images we need to resize,
 * the second one is the folder name (--output-folder or simply -o) where the user want to save resized
 * images.
 * @author laplace
 *
 */
public class Exercise1 {

	/**
	 * Main function
	 */
	public static void main(String[] args) {

		// Create and initialise objects
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;
		/*
		 * There are three command line parameters:
		 * 		input-folder:   A folder contain all images that we need to process.
		 * 		output-folder:  A folder where we need to save processed images.
		 * 		resize:			A percentage value to resize images.
		 * Example: -i data -o resized_images -r 50
		 */
		
		//setup options
		Option inputFolder = new Option("i", "input-folder", true, "Input folder contains images!");
		inputFolder.setRequired(true);
		options.addOption(inputFolder);

		Option outputFolder = new Option("o", "output-folder", true, "Output folder to save resized images!");
		outputFolder.setRequired(true);
		options.addOption(outputFolder);

		Option resize = new Option("r", "resize", true, "Persentage value (ex: 50) to resize images!");
		resize.setRequired(true);
		options.addOption(resize);

		//Initialise command line
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
		double resizeValue = Double.parseDouble(cmd.getOptionValue("resize"));
		
		//Tow Files for input and output folders
		File inFolderPath = new File(inFolder);
		
		//Get all files in input folders
		File[] files = inFolderPath.listFiles();
		
		ImagePlus img;
		ImageProcessor imgProcessor;
		ImagePlus resizedImage;
		int newWidth,newHeight;
		
		/*
		 * Read all files and filter images by extensions, resize them and
		 * save them to output folder
		 */
		for(int i=0;i<files.length;i++) {
			if (files[i].isFile() && isSupported(FilenameUtils.getExtension(files[i].getName()))){ 
		       img= new ImagePlus(inFolder+"/"+files[i].getName());
		       imgProcessor= img.getProcessor();
		       newWidth=(int) (img.getWidth()*(resizeValue/100));
		       newHeight=(int) (img.getHeight()*(resizeValue/100));
		       resizedImage=new ImagePlus(img.getTitle(),imgProcessor.resize(newWidth,newHeight));
		       imageSaver(resizedImage,outFolder);
		    }
		}
		System.out.println("Images saved in: "+outFolder);
	}
	
	/**
	 * A function to filter images, if the file extension is image extension,
	 * it will return true, otherwise it will return false. 
	 * @param fileExtension
	 * @return boolean
	 */
	public static boolean isSupported(String fileExtension) {
		String[] formats=new String[]
				{"tiff","tif","gif","jpeg","jpg","png","bmp","pgm","pbm","ppm","raw","fits","lut"};
		for(String extension:formats) {
			if(fileExtension.equalsIgnoreCase(extension)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * A function to save image as it is type, it will check image type
	 * and save it as it is.
	 * @param img
	 * @param outFolder
	 */
	public static void imageSaver(ImagePlus img,String outFolder) {
		String extension = FilenameUtils.getExtension(img.getTitle());
		FileSaver saver = new FileSaver(img);
		File outFolderPath = new File(outFolder);
		// If the output folder is not exist this code will create it
		outFolderPath.mkdir();
		String imagePath=outFolder+"/"+img.getTitle();
		if(extension.equalsIgnoreCase("tif") || 
				extension.equalsIgnoreCase("tiff")) {
			saver.saveAsTiff(imagePath);
		}
		else if(extension.equalsIgnoreCase("gif")) {
			saver.saveAsGif(imagePath);
		}
		else if(extension.equalsIgnoreCase("jpg") || 
				extension.equalsIgnoreCase("jpeg")) {
			saver.saveAsJpeg(imagePath);
		}
		else if(extension.equalsIgnoreCase("bmp")) {
			saver.saveAsBmp(imagePath);
		}
		else if(extension.equalsIgnoreCase("pgm") || 
				extension.equalsIgnoreCase("pbm") || 
				extension.equalsIgnoreCase("ppm")) {
			saver.saveAsPgm(imagePath);
		}
		else if(extension.equalsIgnoreCase("png")) {
			saver.saveAsPng(imagePath);
		}
		else if(extension.equalsIgnoreCase("fits")) {
			saver.saveAsFits(imagePath);
		}
		else if(extension.equalsIgnoreCase("lut")) {
			saver.saveAsLut(imagePath);
		}
		else if(extension.equalsIgnoreCase("raw")) {
			saver.saveAsRaw(imagePath);
		}
		else {
			System.err.println("Sorry, Unsoppurted format!");
		}
	}
}
