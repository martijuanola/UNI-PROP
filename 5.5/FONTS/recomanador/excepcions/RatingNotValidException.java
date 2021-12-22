package src.recomanador.excepcions;

/**
 * Exception for signaling invalid rating values from the raw file errors.
 */
public class RatingNotValidException extends Exception {

	public RatingNotValidException (String s) {
		super("El valor " + s + " no és un puntuació vàlida. Ha de ser un float.");
	}

	public RatingNotValidException (float f) {
		super("El valor " + f + " no és un puntuació vàlida. Ha de ser un float entre 0.0 i 5.0 amb la part decimal igual a .0 o a .5.");
	}

}