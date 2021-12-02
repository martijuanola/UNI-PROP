package src.recomanador.excepcions;

public class ConjuntItemsAtributeNotInitializedException extends Exception {

	public ConjuntItemsAtributeNotInitializedException() {
		super("Tried to access an atribute from ConjuntItems that has not been initialized yet.");
	}

}