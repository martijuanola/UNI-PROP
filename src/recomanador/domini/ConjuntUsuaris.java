package src.recomanador.domini;

//Exceptions Used
import src.recomanador.excepcions.UserIdNotValidException;
import src.recomanador.excepcions.UserNotFoundException;
import src.recomanador.excepcions.DataNotValidException;

import java.util.ArrayList;

/**
 * This class describes a set of users as a extension of a users ArrayList.
 * It keeps the elements ordered by the id of users, to achieve a better performance when asking
 * for a user with a certain ID.
 * 
 * @author Martí J.
 */
public class ConjuntUsuaris extends ArrayList<Usuari> {

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
     * @throws     DataNotValidException    The input data is not valid
     */
    public ConjuntUsuaris(ArrayList<ArrayList<String>> raw) throws DataNotValidException {
        this.afegirDades(raw);
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
        if(this.size() == 0) return false;

        int pos = cercaBinaria(id);
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
        if(this.size() == 0) throw new UserNotFoundException(id);

        int pos = cercaBinaria(id);
        if(pos < this.size() && this.get(pos).getId() == id) return this.get(pos);
        else throw new UserNotFoundException(id);
    }


    /*----- MODIFICADORES -----*/

    /**
     * Adds the specified user <i>u</i> keeping the list ordered by id.
     * Doesn't accept duplicates.
     *
     * @param      u     User to get added 
     *
     * @return     true (as specified by Collection.add(E))
     */
    @Override public boolean add(Usuari u) {
        int pos = cercaBinaria(u.getId());
        if(this.size() != 0 && pos < this.size() && this.get(pos).getId() == u.getId()) return false;
        
        try {
            this.add(pos,u);
        }
        catch(IndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    /**
     * Removes an user with a certain id.
     *
     * @param      id                     The identifier
     *
     * @throws     UserNotFoundException  Thrown if the id doesn't belong to any user.
     */
    public void removeUsuari(int id) throws UserNotFoundException{
        int pos = cercaBinaria(id);
        if(this.get(pos).getId() != id) throw new UserNotFoundException(id);
        this.remove(pos);
    }

    /**
     * New users are added from the raw information from a ratings.csv file
     *
     * @param      raw                      The raw data
     *
     * @throws     DataNotValidException    The input data is not valid
     */
    public void afegirDades(ArrayList<ArrayList<String>> raw) throws DataNotValidException {
        int prev = 0;
        //es salta el header
        for(int i = 1; i < raw.size(); i++) {
            try {
                int newID = Integer.parseInt(raw.get(i).get(0));
                if((prev == 0 || newID != prev) && ! existeixUsuari(newID)) this.add(new Usuari(newID));
               
                prev = newID;
            }
            catch(NumberFormatException e) {
                throw new DataNotValidException(raw.get(i).get(0),"Usuari ID no vàlid. Ha de ser un int.");
            }
            catch(IndexOutOfBoundsException e) {
                throw new DataNotValidException(i,"Error amb l'input raw de ConjuntsUsuaris a l'iteració: ");
            }
        }
    }


    /*----- ALTRES -----*/

    /**
     * Returns de index of the element with id = <i>id</i>, or if it doesn't exist,
     *  the position where it should be added. It uses a binary search.
     *
     * @param      id     The identifier of the users we are looking for
     *
     * @return     The index where the user should be
     */
    private int cercaBinaria(int id) { 
        int first = 0;
        int last = this.size()-1;
        
        while(first <= last) {  
            int mid = (first+last)/2;
            int mid_id = this.get(mid).getId();
            
            if(mid_id > id) last = mid - 1;
            else if(mid_id < id) first = mid + 1;
            else return mid;
        }
        return first;
    }

}
