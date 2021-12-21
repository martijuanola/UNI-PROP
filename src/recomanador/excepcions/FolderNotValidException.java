package src.recomanador.excepcions;

public class FolderNotValidException extends Exception {

	public FolderNotValidException() {
		super("No estàs en una carpeta vàlida per guardar dades.");
	}
	
	public FolderNotValidException(String s) {
		super("La carpeta " + s + " no és vàlida.");
	}
	
	public FolderNotValidException(String s, String f) {
		super("La carpeta " + s + " no conté el fitxer " + f + " o no és vàlida.");
	}

	public FolderNotValidException(String s, boolean b) {
		super(s);
	}
	
	public FolderNotValidException(boolean b) {
		super("Alguna cosa ha anat malament quan es guardava el fitxer.");
	}
}
