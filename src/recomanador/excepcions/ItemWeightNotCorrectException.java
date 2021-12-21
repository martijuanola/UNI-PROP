package src.recomanador.excepcions;

public class ItemWeightNotCorrectException extends Exception {
    public ItemWeightNotCorrectException(String errorMessage) {
        super("Un pes ha de ser un float entre 0 i 100." + errorMessage);
    }
}
