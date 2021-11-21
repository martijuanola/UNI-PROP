package src.recomanador.excepcions;

/**
 * Exception for signaling invalid user id from ratings file errors.
 */
public class UserIdNotValidException extends Exception {

	public UserIdNotValidException(String s) {
		super("The value " + s + " is not valid as a user id. Needs to be a integer.");
	}

}