package src.recomanador.exception;

public class FileNotValidException extends Exception {

	public FileNotValidException(String s) {
		super("The file named " + s + " is not valid.");
	}
	
	public FileNotValidException(String s, String type) {
		super("The file named " + s + "is not a valid "+ type + " file.");
	}

}
