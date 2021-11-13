package src.recomanador.domini;


public class NotDefinedException extends Exception { 
    public NotDefinedException(String errorMessage) {
        super(errorMessage);
    }
}