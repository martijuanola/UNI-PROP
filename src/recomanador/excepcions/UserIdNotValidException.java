package src.recomanador.excepcions;

/**
 * Exception for signaling invalid user id from ratings file errors.
 */
public class UserIdNotValidException extends Exception {

	public UserIdNotValidException(String s) {
		super("El valor " + s + " no és valid com a id de usuari. Ha de ser un int.");
	}

	public UserIdNotValidException(int id) {
		super("L'id " + id + " ja està en ús.");
	}
}