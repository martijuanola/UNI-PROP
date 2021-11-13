package src.recomanador.domini;

public class Usuari implements Comparable<Usuari> {
    
    /*----- ATRIBUTS -----*/

    private int id;

    private ConjuntRecomanacions cr; //Conjunt recomanacions
    private ConjuntRecomanacions cv; //Conjunt recomanacions valorades

    /*----- CONSTRUCTORS -----*/

    /**
     * L'usuari s'ha creat amb l'id id
     * @param id id ha de ser únic entre tots els usuaris
     */
    public Usuari(int id) {
        this.id = id;
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
     * Retorna la recomanació que pertany al índex i
     * @param i índex de la recomanació, ha de estar en el rang de recomanacions de l'usuari
     * @return retorna la recomanació indicada per l'índex i
     */
    public Recomanacio getRecomanacio(int i) {
        return cr.get(i);
    }

    /**
     * Retorna el conjunt de valoracions del usuari
     * @return retorna el conjunt de valoracions de l'usuari
     */
    public ConjuntRecomanacions getValoracions() {
        return cv;
    }
 
    /**
     * Retorna la valoració que pertany al índex i
     * @param i índex de la valoració, ha de estar en el rang de valoracions de l'usuari
     * @return retorna la valoració del índex i
     */
    public Recomanacio getValoracio(int i) {
        return cv.get(i);
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

    /**
     * Elimina la recomanació que pertany al índex i
     * @param i índex de la recomanació, ha de estar en el rang de recomanacions de l'usuari
     * @return retorna la recomanació eliminada
     */
    public Recomanacio eliminarRecomanacio(int i) {
        return cr.remove(i);
    }

    /**
     * Elimina la valoració que pertany al índex i 
     * @param i índex de la valoració, ha de estar en el rang de valoracions de l'usuari
     * @return retorna la valoració eliminada
     */
    public Recomanacio eliminarValoracio(int i) {
        return cv.remove(i);
    }

    /**
     * La recomanació s'elimina de recomanacions i s'afegeix com a valoració amb puntuació
     * @param rec índex de la valoració, ha de estar en el rang de valoracions de l'usuari
     * @param pun valor de la puntuació, ha de ser entre 0 i 5
     * @return retorna la nova valoració
     */
    public Recomanacio valorarRecomanacio(int rec, float pun) {
        Recomanacio r = cr.get(rec);
        cr.remove(rec);
        r.setVal(pun);
        cv.add(r);
        return r;
    }

    /**
     * La nova puntuació de la valoració és pun
     * @param val índex de la valoració, ha de estar en el rang de valoracions de l'usuari
     * @param pun valor de la puntuació, ha de ser entre 0 i 5
     */
    public void valorarValoracio(int val, float pun) {
        cv.get(val).setVal(pun);
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
