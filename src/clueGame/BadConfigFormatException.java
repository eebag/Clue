package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception{
	
	public BadConfigFormatException() {
		super("Error: Bad config file format");
	}
	
	public BadConfigFormatException(String filename, String mName) {
		super("Error: Bad config file format in " +filename+" in method " +mName+ ".");
		try{
			//Try to find/ make file. Print the exceed amount
			PrintWriter file= new PrintWriter("logfile.txt");
			file.println("There is a formatting error in " + filename +" in method " +mName+ ".");
			file.close();
		}
		catch (FileNotFoundException e){
			//Print error if file not found
			System.out.println("Error: Could not find/ create file.");
		}	
	}
}
