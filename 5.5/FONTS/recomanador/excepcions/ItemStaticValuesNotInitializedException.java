package src.recomanador.excepcions;

public class ItemStaticValuesNotInitializedException extends Exception {

	public ItemStaticValuesNotInitializedException(String s) {
		super("Alguns valors estàtics de la classe Item encara no estan inicialitzats, en particular l'atribut '" + s + "'. S'han de tenir inicialitzats abans de construïr una instància nova de la classe.");
	}

}