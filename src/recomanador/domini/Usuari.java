package src.recomanador.domini;

import src.recomanador.excepcions.RatingNotValidException;
import src.recomanador.excepcions.RecommendationNotRatedException;

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
     * L'usuari s'ha creat amb l'id id
     * @param id id ha de ser únic entre tots els usuaris
     */
    public Usuari(int id) {
        this.id = id;
        cr = new ConjuntRecomanacions();
        cv = new ConjuntRecomanacions();
    }

    /**
     * L'usuari s'ha creat amb l'id, el conjunt de recomanacions i valoracions id, cr i cv
     * @param id id ha de ser únic entre tots els usuaris
     * @param cr conjunt de recomanacions que té actualemt l'usuari (si s'han modificat les dades, s'han de recalculat)
     * @param cv conjunt de valoracions que té l'usuari 
     */
    public Usuari(int id, ConjuntRecomanacions cr, ConjuntRecomanacions cv) {
        this.id = id;
        this.cr = cr;
        this.cv = cv;
    }
    
    /*----- GETTERS -----*/

    /**
     * Retorna el id de l'usuari
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna les recomanacions del usuari
     * @return ConjuntRecomanacions 
     */
    public ConjuntRecomanacions getRecomanacions() {
        return cr;
    }

    /**
     * Retorna el conjunt de valoracions del usuari
     * @return retorna el conjunt de valoracions de l'usuari
     */
    public ConjuntRecomanacions getValoracions() {
        return cv;
    }
 
    /*----- SETTERS -----*/

    /**
     * El punter de les recomanacions ara apunta a cr
     * @param cr ConjuntRecomanacions
     */
    public void setRecomanacions(ConjuntRecomanacions cr) {
        this.cr = cr;
    }

    /**
     * El conjunt de valoracions ara apunta a cv
     * @param cv ConjuntRecomanacions
     */
    public void setValoracions(ConjuntRecomanacions cv) {
        this.cv = cv;
    }

    //Aquestes 2 funcions no haurien d'existir!
    //Pensar com poder canviar valors i actualitzar el conjunt
    
    /**
     * Used to move a recommendation from cr to cv when a a recommendations is rated
     *
     * @param      rec   The index of the recommendations in cr
     */
    public void moureRecomanacio(int rec) throws RecommendationNotRatedException{
        Recomanacio r = cr.get(rec);
        if(r.getVal() == Recomanacio.nul) throw new RecommendationNotRatedException();
        cr.remove(rec);
        cv.add(r);
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
