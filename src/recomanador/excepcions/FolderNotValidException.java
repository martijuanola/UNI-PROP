package src.recomanador.excepcions;

public class FolderNotValidException extends Exception {

	public FolderNotValidException() {
		super("You are not in a valid folder to save the data.");
	}
	
	public FolderNotValidException(String s) {
		super("The folder named " + s + " is not a valid one.");
	}
	
	public FolderNotValidException(String s, String f) {
		super("The folder named " + s + " does not contain the file " + f + ", or it's not valid.");
	}

	public FolderNotValidException(String s, boolean b) {
		super(s);
	}
	
	public FolderNotValidException(boolean b) {
		super("Something went wrong while saving the file.");
	}
}
