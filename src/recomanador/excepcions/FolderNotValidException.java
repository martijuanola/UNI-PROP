package src.recomanador.excepcions;

public class FolderNotValidException extends Exception {

	public FolderNotValidException(String s) {
		super("The folder named " + s + " is not a valid one.");
	}
	
	public FolderNotValidException(String s, String f) {
		super("The folder named " + s + " does not contain the file " + f + ".");
	}

	public FolderNotValidException(String s, boolean b) {
		super(s);
	}
}
