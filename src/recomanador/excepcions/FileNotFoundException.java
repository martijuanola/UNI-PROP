package src.recomanador.exception;

public class FileNotFoundException extends Exception {

	public FileNotFoundException(String s) {
		super("The file named " + s + "was not found.");
	}

}
