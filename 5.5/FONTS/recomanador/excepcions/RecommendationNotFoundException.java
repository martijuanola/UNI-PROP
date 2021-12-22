package src.recomanador.excepcions;

//import java.lang.Exception; ??

public class RecommendationNotFoundException extends Exception {

	public RecommendationNotFoundException(int item_id, int user_id) {
		super("La recomanació amb id d'usuari " + user_id + " i id d'item " 
			+ item_id + " nos s'ha trobat al conjunt de recomanacions.");
	}

}