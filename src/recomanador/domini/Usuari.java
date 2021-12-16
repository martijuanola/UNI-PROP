package src.recomanador.domini;

/**
 * This class represents a user. It only has the atribute id.
 * @author Martí J.
 */
public class Usuari implements Comparable<Usuari> {
    
    /*----- ATRIBUTS -----*/

    /** ID of the user **/
    private int id;

    /*----- CONSTRUCTORS -----*/

    /**
     * Constructs a new instance.
     *
     * @param      id    Identifier of Usuari
     */
    public Usuari(int id) {
        this.id = id;
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
