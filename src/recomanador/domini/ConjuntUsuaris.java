package src.recomanador.domini;

import java.util.ArrayList;

/**
 * This class describes the extension of ArrayList<Ususaris> with extra methods.
 * @author Martí J.
 */
public class ConjuntUsuaris extends ArrayList<Usuari> {
    
    //opció de guardar-ho per id ascendent per fer binarySearch()
    //dependrà de que s'ha de fer a l'algorisme, de moment ho faig sense ordre

    /*----- CONSTANTS -----*/
    
    /*----- ATRIBUTS -----*/


    /*----- CONSTRUCTORS -----*/

    /**
     * Constructs a new instance without anything new
     */
    public ConjuntUsuaris() {
        super();
    }

    /**
     * Constructs a new instance with the users empty user(without ratings and recomendations)
     *  from a previous execution without filling
     *
     * @param      raw   The raw data from a file of ratings
     */
    public ConjuntUsuaris(ArrayList<ArrayList<String>> raw) {
        int prev = -1;
        for(int i = 0; i < raw.size(); i++) {
            int newID = Integer.parseInt(raw.get(i).get(0));
            if(newID != prev && ! existeixUsuari(newID)) this.add(new Usuari(newID));
            prev = newID;
        }
    }


    /*----- CONSULTORES -----*/

    /**
     * Returns if a user with a certain ID exisist in the set of users.
     *
     * @param      id    The identifier
     *
     * @return     A boolean indicating if the user can be found or not
     */
    public boolean existeixUsuari(int id) {
        for(int i = 0; i < this.size(); i++) {
            if (this.get(i).getId() == id) return true;
        }
        return false;
    }

    /**
     * Gets the user with a certain id
     *
     * @param      id                     The identifier
     *
     * @return     The user
     *
     * @throws     UserNotFoundException  The user with that id didn't exist
     */
    public Usuari getUsuari(int id) throws UserNotFoundException {
        for(int i = 0; i < this.size(); i++) {
            if (this.get(i).getId() == id) return this.get(i);
        }
        throw new UserNotFoundException(id);
    }


    /*----- MODIFICADORES -----*/

    //private void addUsuari(Usuari u) {} // per si s'ha d'afegir de manera ordenada
}
