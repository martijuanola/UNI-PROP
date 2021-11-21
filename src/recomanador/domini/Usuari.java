package src.recomanador.domini;

import src.recomanador.excepcions.RatingNotValidException;
import src.recomanador.excepcions.RecommendationNotRatedException;
import src.recomanador.excepcions.RecommendationRatedException;

/**
 * @author Martí J. i Jaume
 */
public class Usuari implements Comparable<Usuari> {
    
    /*----- ATRIBUTS -----*/

    private int id;

    /**
     * Set of all the recomendations recieved by the user but with no rating
     */
    private ConjuntRecomanacions cr;

    /**
     * Set of the rated recomendations of the users
     */
    private ConjuntRecomanacions cv;

    /*----- CONSTRUCTORS -----*/

    /**
     * Constructs a new instance.
     *
     * @param      id    Identifier of Usuari
     */
    public Usuari(int id) {
        this.id = id;
        cr = new ConjuntRecomanacions();
        cv = new ConjuntRecomanacions();
    }

    
    /*----- GETTERS -----*/

    /**
     * Gets the identifier.
     *
     * @return     The identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the unrated recommendations of the user.
     *
     * @return     The set of recommendations
     */
    public ConjuntRecomanacions getRecomanacions() {
        return cr;
    }

    /**
     * Gets the rated recommendations of the user.
     *
     * @return     The set of recommendations.
     */
    public ConjuntRecomanacions getValoracions() {
        return cv;
    }
 
    /*----- SETTERS -----*/

    /**
     * Sets the ConjuntRecomanacions of unrated recommendations of the user.
     *
     * @param      cr    The new set.
     */
    public void setRecomanacions(ConjuntRecomanacions cr) {
        this.cr = cr;
    }

    /**
     * Sets the ConjuntRecomanacions of rated recommendations of the user.
     *
     * @param      cv    The new set.
     */
    public void setValoracions(ConjuntRecomanacions cv) {
        this.cv = cv;
    }
    
    /**
     * Moves a function between the 2 sets of recommendations to keep them updated when changes are performed in the recommendations.
     *
     * @param      r                                The recommendation that has to be moved.
     * @param      b                                True if the change is from CR to CV, false otherwise.
     *
     * @throws     RecommendationNotRatedException  The change is not possible because r doesn't has a rating
     * @throws     RecommendationRatedException     The change is not possible because r has a rating
     */
    public void moureRecomanacio(Recomanacio r, boolean b) throws RecommendationNotRatedException, RecommendationRatedException {
        if(b) {//es valora
            if(r.getVal() == 0.0) throw new RecommendationNotRatedException();
            cr.remove(r);
            cv.add(r);
        }
        else {//s'elimina la valoració
            if(r.getVal() != 0.0) throw new RecommendationRatedException();
            cv.remove(r);
            cr.add(r);
        }
    }

    /**
     * Compares the instance to a user u2.
     *
     * @param      u2    The other user
     *
     * @return     this.id compareTo u2.id
     */
    @Override public int compareTo(Usuari u2) {
        return Integer.compare(this.id, u2.id);
    }
}
