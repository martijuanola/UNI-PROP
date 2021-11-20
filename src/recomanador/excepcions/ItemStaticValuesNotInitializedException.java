package src.recomanador.excepcions;

public class ItemStaticValuesNotInitializedException extends Exception {

	public ItemStaticValuesNotInitializedException(String s) {
		super("Som static data of the class Item is not inicialized yet, in particular the atribute " + s + ". Fist you need to initialize this atributes to construct a new instance of the class.");
	}

}