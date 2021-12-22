package src.recomanador.excepcions;

public class DataNotValidException extends Exception {

	public DataNotValidException(int d, String message) {
		super(message + " Data("+d+")");
	}

	public DataNotValidException(float d, String message) {
		super(message + " Data("+d+")");
	}

	public DataNotValidException(String d, String message) {
		super(message + " Data("+d+")");
	}
}