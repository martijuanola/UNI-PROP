package src.recomanador.excepcions;

public class FileNotValidException extends Exception {

	public FileNotValidException(String file) {
		super("The file named " + file + " is not valid.");
	}
	
	public FileNotValidException(String file, String type) {
		super("The file named " + file + "is not a valid "+ type + " file.");
	}

	/*public FileNotValidException(String file, String message) {
		super("The file named " + file + "is not valid. " + message);
	}*/

}
