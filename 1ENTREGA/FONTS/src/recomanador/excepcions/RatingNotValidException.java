package src.recomanador.excepcions;

/**
 * Exception for signaling invalid rating values from the raw file errors.
 */
public class RatingNotValidException extends Exception {

	public RatingNotValidException (String s) {
		super("The value " + s + " is not valid as rating. Needs to be a float.");
	}

	public RatingNotValidException (float f) {
		super("The value " + f + " is not valid as rating. Needs to be a float between 0.0 and 5.0 and the floating part can be .0 or .5.");
	}

}