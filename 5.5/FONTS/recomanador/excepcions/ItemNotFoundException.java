package src.recomanador.excepcions;


public class ItemNotFoundException extends Exception { 
    public ItemNotFoundException(String errorMessage) {
        super("S'ha provat d'accedir a un item que no existeix." + errorMessage);
    }
}