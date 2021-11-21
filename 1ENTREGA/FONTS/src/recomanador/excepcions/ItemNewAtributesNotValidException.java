package src.recomanador.excepcions;

public class ItemNewAtributesNotValidException extends Exception {

	public ItemNewAtributesNotValidException(int n) {
		super("The number of atributes is not correct. There should be " + n + " atributes for each item.");
	}

}