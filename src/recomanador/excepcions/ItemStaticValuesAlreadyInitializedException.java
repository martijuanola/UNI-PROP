package src.recomanador.excepcions;

public class ItemStaticValuesAlreadyInitializedException extends Exception {

	public ItemStaticValuesAlreadyInitializedException() {
		super("S'ha provat de canviar un valor estàtic que ja estava definit.");
	}

}