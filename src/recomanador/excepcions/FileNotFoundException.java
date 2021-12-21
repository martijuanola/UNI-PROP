package src.recomanador.excepcions;

public class FileNotFoundException extends Exception {

	public FileNotFoundException(String s) {
		super("El fitxer " + s + " no s'ha trobat.");
	}

}
