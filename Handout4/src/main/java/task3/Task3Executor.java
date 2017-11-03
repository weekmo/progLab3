package task3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Task3Executor {
	
	public Task3Executor() {
		for(PPI p:findProteins("A", "FIsInGene.txt","\t")){
			System.out.println(p.getGene1()+"\t"+p.getGene2() +"\t"+p.getAnnotation()+"\t"+p.getDirection()+"\t"+p.getScore());
		}
	}
	
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
	
	public ArrayList<PPI> readPPIFile(String fileName){
		return this.readPPIFile(fileName,"\t");
	}
	
	public ArrayList<PPI> findProteins(String protienName,String fileName, String separator){
		ArrayList<PPI> interactions = readPPIFile(fileName,separator);
		ArrayList<PPI> result = new ArrayList<PPI>();
		for(PPI p:interactions) {
			if(this.compareProtein(protienName, p.getGene1()) || this.compareProtein(protienName, p.getGene2())) {
				result.add(p);
			}
		}
		return result;
	}
	
	public ArrayList<PPI> findProteins(String protienName,String fileName){
		return this.findProteins(protienName, fileName, "\t");
	}
	
	private boolean compareProtein(String pattern,String proteinName) {
		for(int i =0;i<proteinName.length()-pattern.length()+1;i++) {
			if(proteinName.substring(i, i+pattern.length()).equalsIgnoreCase(pattern))
				return true;
		}
		return false;
	}
	
}
