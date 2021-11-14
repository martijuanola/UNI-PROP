package src.recomanador.excepcions;


public class ItemIDNotValidException extends Exception { 
    public ItemIDNotValidException(String errorMessage) {
        super(errorMessage);
    }
}