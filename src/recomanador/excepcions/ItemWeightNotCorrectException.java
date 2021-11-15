package src.recomanador.excepcions;

public class ItemWeightNotCorrectException extends Exception {
    public ItemWeightNotCorrectException(String errorMessage) {
        super("The weight must be a float from 0 to 100" + errorMessage);
    }
}
