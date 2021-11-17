package src.recomanador.excepcions;


public class ItemNotCompatibleException extends Exception { 
    public ItemNotCompatibleException(String errorMessage) {
        super("Tried to compare to Items that do not share atributes " + errorMessage);
    }
}