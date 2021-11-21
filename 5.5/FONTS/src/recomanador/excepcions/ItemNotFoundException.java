package src.recomanador.excepcions;


public class ItemNotFoundException extends Exception { 
    public ItemNotFoundException(String errorMessage) {
        super("Tried to access an Item which doesn't exist " + errorMessage);
    }
}