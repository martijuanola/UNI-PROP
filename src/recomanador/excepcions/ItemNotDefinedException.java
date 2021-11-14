package src.recomanador.excepcions;


public class ItemNotDefinedException extends Exception { 
    public ItemNotDefinedException(String errorMessage) {
        super(errorMessage);
    }
}