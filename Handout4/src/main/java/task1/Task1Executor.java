//Package name
package task1;

//Import libraries
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/*
 * Beginning of the class
 * It is a class to execute task1 of handout4 programming lab3
 */

public class Task1Executor {
	
	//Class constructor
	public Task1Executor()
    {
    	ArrayList<Participant> participantsList=readParticipantsCSVFile("patients_data.csv");
    	for(Participant p:participantsList) {
    		System.out.println(p.getId());
    	}
    	writeToParticipantsCSVFile(participantsList, "patients_data_withIDs.csv");
    }
	/*
	 * Function to generate IDs by taking almost constant data (lastName, firstName, dateOfBirth)
	 * and hash it.
	 * it uses SHA1 algorithm to hash the ID.
	 */
    public static String generateID(String lastName,String firstName,String dateOfBirth) {
    	// Get the almost constant data and use it as a key for participant
		String keyText=lastName+firstName+dateOfBirth;
		MessageDigest messageDigest=null;
		try {
			// Assign the hashing algorithm
			messageDigest = MessageDigest.getInstance("SHA1");
			// Update the message digester (stomach) by the data
			messageDigest.update(keyText.getBytes());
		} 
		catch (NoSuchAlgorithmException e) {System.out.println(e.getMessage());}
		catch (Exception e) {System.out.println(e.getMessage());}
		/*
		 * Hash (digest) the key and return it back as byte array,
		 * Because the byte array has negative numbers, we convert it to
		 * hexadecimal positive big integer number and then to string
		 */
		return new BigInteger(1,messageDigest.digest()).toString(16);
	}
    
    /*
     * Functions to read participants data from a CSV file and return ArrayList. 
     */
    public static ArrayList<Participant> readParticipantsCSVFile(String fileName,String separator){
    	ArrayList<Participant> participantsList = new ArrayList<Participant>();
    	try {
			BufferedReader reader = new BufferedReader(new FileReader("data/"+fileName));
			String line;
			String[] data;
			while((line=reader.readLine()) !=null) {
				data=line.split(separator);
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
    
    /*
     * Functions to read participants data from a CSV file and return ArrayList.
     * with predefined separator.
     * it override the previous function.
     */
    public static ArrayList<Participant> readParticipantsCSVFile(String fileName){
    	return readParticipantsCSVFile(fileName, ";");
    }
    
    /*
     * Function to write ArrayList of Participant class to CSV file
     */
    public void writeToParticipantsCSVFile(ArrayList<Participant> participants,String fileName) {
    	if(participants.size()<=0) {
    		System.out.println("There is no data to write!");
    		return;
    	}
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