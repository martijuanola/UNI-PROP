package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class PrivilegesException extends Exception {

	public PrivilegesException() {
		super("L'usuari actiu no té privilegis per executar aquesta funcionalitat de l'aplicació.");
	}

	public PrivilegesException(String s) {
		super("L'usuari actiu no té privilegis per executar aquesta funcionalitat de l'aplicació." + s);
	}

}