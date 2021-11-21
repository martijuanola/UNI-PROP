package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class PrivilegesException extends Exception {

	public PrivilegesException() {
		super("The active user doesn't have the privileges to execute this functionality of the application.");
	}

}