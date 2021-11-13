package src.recomanador.exception;

public class UserNotFoundException extends Exception {

	public UserNotFoundException(int id) {
		super("The user with id " + id + "was not found in de set of users");
	}

}