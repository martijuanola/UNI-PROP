package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class RecommendationNotRatedException extends Exception {

	public RecommendationNotRatedException() {
		super("The recommendation doesn't have a rating.");
	}

}