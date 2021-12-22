package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class AlreadyLogedInException extends Exception {

	public AlreadyLogedInException(int id) {
		super("Has entrat com a usuari amb id = " + id + ". Primer has de sortir.");
	}

}