package src.recomanador.excepcions;

public class FolderNotFoundException extends Exception {

	public FolderNotFoundException(String s) {
		super("La carpeta " + s + " no s'ha trobat.");
	}

}
