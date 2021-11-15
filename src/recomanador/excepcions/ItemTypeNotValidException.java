package src.recomanador.excepcions;

public class ItemTypeNotValidException extends Exception{
    public ItemTypeNotValidException(String errorMessage) {
        super("This atribute does not accept this type " + errorMessage);
    }
}
