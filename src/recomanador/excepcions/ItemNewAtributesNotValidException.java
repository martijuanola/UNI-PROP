package src.recomanador.excepcions;

public class ItemNewAtributesNotValidException extends Exception {

	public ItemNewAtributesNotValidException(int n) {
		super("El nombre d'atributs no és correcte. Hi hauria d'haver " + n + " atributs per cada Item.");
	}

}