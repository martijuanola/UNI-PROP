package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class RecommendationNotFoundException extends Exception {

	public RecommendationNotFoundException(int item_id, int user_id) {
		super("The recommendation with the user with id " + user_id + " and the with the item with id " 
			+ item_id + "was not found in de set of recomendations.");
	}

}