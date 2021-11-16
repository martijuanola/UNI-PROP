package src.recomanador.domini;

import src.recomanador.excepcions.*;

import java.util.Collections;  
import java.util.ArrayList;

//Per si s'ha d'utilitzar StubUsuari.java
import src.stubs.*;

/**
 * This class describes a set of users as a extension of a users ArrayList.
 * It keeps the elements ordered by the id of users, to achieve a better performance when asking
 * for a user with a certain ID.
 * @author Martí J.
 */
public class ConjuntUsuaris extends ArrayList<Usuari> {

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
     *  from a previous execution without filling ordered by ID
     *
     * @param      raw   The raw data from a file of ratings
     * @exception  UserIdNotValidException Gets thrown if the string for a user id is can not be converted
     * to int
     */
    public ConjuntUsuaris(ArrayList<ArrayList<String>> raw) throws UserIdNotValidException {
        int prev = -1;
        for(int i = 1; i < raw.size(); i++) {
            try {
                int newID = Integer.parseInt(raw.get(i).get(0));
                if(i == 1) {
                    this.add(0, new Usuari(newID));
                    prev = newID;
                }
                else {
                    if(newID != prev && ! existeixUsuari(newID)) this.add(new Usuari(newID));
                    prev = newID;
                }
            }
            catch(NumberFormatException e) {
                throw new UserIdNotValidException(raw.get(i).get(0));
            }
        }
        Collections.sort(this);
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
        int pos = cercaBinaria(0,this.size()-1,id);
        if(pos < this.size() && this.get(pos).getId() == id) return true;
        else return false;
    }

    /**
     * Gets the user with a certain id. Throws exception if not found
     *
     * @param      id                     The identifier
     *
     * @return     The user
     *
     * @throws     UserNotFoundException  The user with that id didn't exist
     */
    public Usuari getUsuari(int id) throws UserNotFoundException {
        int pos = cercaBinaria(0,this.size()-1,id);
        if(this.get(pos).getId() == id) return this.get(pos);
        else throw new UserNotFoundException(id);
    }

    //usuaris que han valorat 1 item

    //usuaris que han valorat 2 items


    /*----- MODIFICADORES -----*/

    /**
     * Adds the specified user <i>u</i> keeping the list ordered by id.
     *
     * @param      u     User to get added 
     *
     * @return     true (as specified by Collection.add(E))
     */
    @Override public boolean add(Usuari u) {
        int pos = cercaBinaria(0,this.size()-1,u.getId());
        try {
            this.add(pos,u);
        }
        catch(IndexOutOfBoundsException e){
            return false;
        }
        return true;
    }


    /*----- ALTRES -----*/

    /**
     * Returns de index of the element with id = <i>id</i>, or if it doesn't exist,
     *  the position where it should be added. It uses a binary search.
     *
     * @param      first  first element of the sequence of users
     * @param      last   Last element of the sequenece of users
     * @param      id     The identifier of the users we are looking for
     *
     * @return     The index where the user should be
     */
    private int cercaBinaria(int first, int last, int id) {  
        while(first <= last) {
            int mid = (first+last)/2;
            int mid_id = this.get(mid).getId();
            
            if(mid_id > id) last = mid - 1;
            else if(mid_id < id) first = mid + 1;
            else return mid;
        }
        
        if(first >= 0 && first < this.size() && this.get(first).getId() > id) return first;
        else if(last >= 0 && last < this.size()) return last;
        else {
            System.out.println("Error s'ha d'acabar de mirar el final de la cerca binaria");
            return -1;
        }
    }

}
