package src.recomanador.excepcions;

public class ItemStaticValuesAlreadyInitializedException extends Exception {

	public ItemStaticValuesAlreadyInitializedException() {
		super("Tried to change a static value that was already defined.");
	}

}