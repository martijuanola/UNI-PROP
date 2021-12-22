package src.recomanador.excepcions;

public class FileNotValidException extends Exception {

	public FileNotValidException(String s) {
		super("El fitxer " + s + " no és vàlid.");
	}
	
	public FileNotValidException(String s, String type) {
		super("El fitxer " + s + " no és valid com a fitxer de " + type);
	}

}
