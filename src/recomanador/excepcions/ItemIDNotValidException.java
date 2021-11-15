package src.recomanador.excepcions;


public class ItemIdNotValidException extends Exception { 
    public ItemIdNotValidException(String errorMessage) {
        super(errorMessage);
    }
}