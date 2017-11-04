//Package name
package task3;

//Import libraries
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;



/*
 * Beginning of the class
 * It is a class to execute task3 of handout4 programming lab3
 */
public class Task3Executor {
	
	//Class constructor
	public Task3Executor(String[] args) {
		ArrayList<PPI> ppiList=null;
		if(args.length==0) ppiList=this.findProteins("A1CF", "FIsInGene.txt");
		else if(args.length==1)ppiList=this.findProteins(args[0], "FIsInGene.txt");
		else ppiList=this.findProteins(this.findProteins(args[0], "FIsInGene.txt"), args[1]);
		this.writeToFile(ppiList, "filteredPPI.txt");
		for(PPI p:ppiList)System.out.println(p.getGene1()+"\t"+p.getGene2() +
				"\t"+p.getAnnotation()+"\t"+p.getDirection()+"\t"+p.getScore());
	}
	
	//Function to read PPI file and return ArrayList of PPI class
	public ArrayList<PPI> readPPIFile(String fileName,String separator){
		ArrayList<PPI> ppiList = new ArrayList<PPI>();
    	try {
			BufferedReader reader = new BufferedReader(new FileReader("data/"+fileName));
			String line;
			String[] data;
			int counter=0;
			while((line=reader.readLine()) !=null) {
				data=line.split(separator);
				if(counter>0)
					ppiList.add(new PPI(data[0], data[1], data[2],data[3],Double.parseDouble(data[4])));
				counter++;
			}
			reader.close();
			return ppiList;
		} 
    	catch (IOException e) {System.out.println(e.getMessage());}
    	catch (Exception e) {System.out.println(e.getMessage());}
    	
    	return null;
	}
	
	//Override the previous function by using default separator (tab).
	public ArrayList<PPI> readPPIFile(String fileName){
		return this.readPPIFile(fileName,"\t");
	}
	
	//Function to filter PPI file by protein name
	public ArrayList<PPI> findProteins(String proteinName,String fileName, String separator){
		ArrayList<PPI> interactions = readPPIFile(fileName,separator);
		ArrayList<PPI> result = new ArrayList<PPI>();
		for(PPI ppi:interactions) {
			if(this.compareText(proteinName, ppi.getGene1()) ||
					this.compareText(proteinName, ppi.getGene2())) {
				result.add(ppi);
			}
		}
		return result;
	}
	
	//Override the previous function by using default separator (tab).
	public ArrayList<PPI> findProteins(String proteinName,String fileName){
		return this.findProteins(proteinName, fileName, "\t");
	}
	
	//Override previous Function to filter PPI file by protein name and direction
	public ArrayList<PPI> findProteins(ArrayList<PPI> ppiList,String direction){
		ArrayList<PPI> result=new ArrayList<PPI>();
		for(PPI p:ppiList) {
			if(p.getDirection().equals(direction)) {
				result.add(p);
			}
		}
		return result;
	}
	
	//Function to compare pattern with text and return boolean
	public boolean compareText(String pattern,String text) {
		boolean atTheBeginnigStart=pattern.substring(0, 1).equals("*"),
				atTheEnd=pattern.substring(pattern.length()-1).equals("*");
		
		if(atTheBeginnigStart&&atTheEnd) {
			//System.out.println("Both");
			int patternLenght=pattern.length()-2;
			if(patternLenght<=text.length()) {
				for(int i =0;i<text.length()-patternLenght+1;i++) {
					if(text.substring(i, i+patternLenght).
							equalsIgnoreCase(pattern.substring(1,pattern.length()-1)))
						return true;
				}
			}
		}
		else if(atTheBeginnigStart) {
			//System.out.println("Beginning");
			if(pattern.length()-1<=text.length()&&
					text.substring(text.length()-pattern.length()+1).
					equalsIgnoreCase(pattern.substring(1)))
				return true;
		}
		else if(atTheEnd) {
			//System.out.println("End");
			if(pattern.length()-1<=text.length()&&
			text.substring(0,pattern.length()-1).
			equalsIgnoreCase(pattern.substring(0,pattern.length()-1)))
				return true;
		}
		else {
			//System.out.println("Nothing");
			if(text.equalsIgnoreCase(pattern))
				return true;
		}
		return false;
	}
	
	//write PPI ArrayList to a file
	public void writeToFile(ArrayList<PPI> ppiList, String fileName) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("data/"+fileName));
			String data;
			for(PPI ppi:ppiList) {
				//Gene1	Gene2	Annotation	Direction	Score
				data=ppi.getGene1()+"\t"+ppi.getGene2()+"\t"+ppi.getAnnotation()+
						"\t"+ppi.getDirection()+"\t"+ppi.getScore()+"\n";
	    		writer.write(data);
	    	}
			writer.flush();
    		writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
