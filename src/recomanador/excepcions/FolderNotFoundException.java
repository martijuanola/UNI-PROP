package src.recomanador.excepcions;

public class FolderNotFoundException extends Exception {

	public FolderNotFoundException(String s) {
		super("The folder named " + s + "was not found.");
	}

}
