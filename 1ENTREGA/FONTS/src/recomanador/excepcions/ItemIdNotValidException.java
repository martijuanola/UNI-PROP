package src.recomanador.excepcions;

/**
 * Exception for signaling invalid user id from ratings file errors.
 */
public class ItemIdNotValidException extends Exception {

	public ItemIdNotValidException(String s) {
		super("The value " + s + " is not valid as a item id. Needs to be a integer.");
	}

	public ItemIdNotValidException(int i) {
		super("The column with index="+i+" can not be the id of items");
	}

}