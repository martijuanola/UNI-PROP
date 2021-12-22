package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class UserNotFoundException extends Exception {

	public UserNotFoundException(int id) {
		super("L'usuari amb id " + id + " no s'ha trobat al conjunt d'usuaris.");
	}

}