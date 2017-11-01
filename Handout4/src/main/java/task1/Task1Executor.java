package task1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Task1Executor {
	public Task1Executor()
    {
    	ArrayList<Participant> participantsList=readParticipantsCSVFile("patients_data.csv");
    	for(Participant p:participantsList) {
    		System.out.println(p.getId());
    	}
    	writeToParticipantsCSVFile(participantsList, "patients_data_withIDs.csv");
    }
    
    private static String generateID(String lastName,String firstName,String dateOfBirth) {
		String keyText=lastName+firstName+dateOfBirth;
		MessageDigest messageDigest=null;
		try {
			messageDigest = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        messageDigest.update(keyText.getBytes(),0,keyText.length());
        return new BigInteger(1,messageDigest.digest()).toString(16);
	}
    
    public static ArrayList<Participant> readParticipantsCSVFile(String fileName){
    	ArrayList<Participant> participantsList = new ArrayList<Participant>();
    	try {
			BufferedReader reader = new BufferedReader(new FileReader("data/"+fileName));
			String line;
			String[] data;
			while((line=reader.readLine()) !=null) {
				data=line.split(";");
				participantsList.add(new Participant(generateID(data[0], data[1], data[2]),
						data[0], data[1], data[2],Double.parseDouble(data[3]),
						Double.parseDouble(data[4]),Double.parseDouble(data[5])));
			}
			//System.out.println(participantsList.size());
			reader.close();
			return participantsList;
		} 
    	catch (IOException e) {System.out.println(e.getMessage());}
    	catch (Exception e) {System.out.println(e.getMessage());}
    	
    	return null;
    }
    
    public static void writeToParticipantsCSVFile(ArrayList<Participant> participants,String fileName) {
    	try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("data/"+fileName));
			String data;
			for(Participant p:participants) {
				data=p.getId()+";"+p.getLastName()+";"+p.getFirstName()+";"+p.getDateOfBirth()+
						";"+p.getWeight()+";"+p.getHeight()+";"+p.getIq()+"\n";
	    		writer.write(data);
	    	}
			writer.flush();
    		writer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
    	
    }
}