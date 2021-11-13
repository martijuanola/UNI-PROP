package src.exceptions;


public class NotDefinedException extends Exception { 
    public NotDefinedException(String errorMessage) {
        super(errorMessage);
    }
}