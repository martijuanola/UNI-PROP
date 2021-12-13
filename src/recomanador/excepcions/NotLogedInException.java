package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class NotLogedInException extends Exception {

	public NotLogedInException() {
		super("You are not loged in, precondition to perform this action.");
	}

}