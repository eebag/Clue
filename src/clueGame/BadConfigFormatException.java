package clueGame;

public class BadConfigFormatException extends Exception{
	
	public BadConfigFormatException() {
		super("Error: Bad config file format");
	}
	
	public BadConfigFormatException(String message) {
		super(message);
	}
}
