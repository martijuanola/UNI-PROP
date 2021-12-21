package src.recomanador.excepcions;

public class ItemTypeNotValidException extends Exception{
    public ItemTypeNotValidException(String errorMessage) {
        super("Aquest atribut no accepta el tipus. " + errorMessage);
    }
}
