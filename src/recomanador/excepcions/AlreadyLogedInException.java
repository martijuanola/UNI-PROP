package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class AlreadyLogedInException extends Exception {

	public AlreadyLogedInException(int id) {
		super("You are already loged in with the user id " + id + ". You first have to log out.");
	}

}