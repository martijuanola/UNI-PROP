package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class RecommendationRatedException extends Exception {

	public RecommendationRatedException() {
		super("A recommendation has been rated and ConjuntRecomanacions and ConjuntValoracions from the user that recieved the recommendation have not been updated.");
	}

}