package src.recomanador.excepcions;

public class ConjuntItemsAtributeNotInitializedException extends Exception {

	public ConjuntItemsAtributeNotInitializedException() {
		super("S'ha provat d'accedir a un atribut de Conjunt d'Items que encara no s'ha inicialitzat.");
	}

}