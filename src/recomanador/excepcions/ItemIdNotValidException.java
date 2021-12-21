package src.recomanador.excepcions;

/**
 * Exception for signaling invalid user id from ratings file errors.
 */
public class ItemIdNotValidException extends Exception {

	public ItemIdNotValidException(String s) {
		super("El valor " + s + " no és vàlid com a id. Ha de ser un int.");
	}

	public ItemIdNotValidException(int i) {
		super("La columna amb index "+i+" no pot representar l'id dels items.");
	}

}