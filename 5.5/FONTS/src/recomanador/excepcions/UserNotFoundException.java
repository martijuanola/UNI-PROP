package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class UserNotFoundException extends Exception {

	public UserNotFoundException(int id) {
		super("The user with id " + id + " was not found in de set of users");
	}

}