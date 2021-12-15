
package src.recomanador.domini;

import src.recomanador.excepcions.*;

import java.util.ArrayList;

/**
 * This class describes a set of recommendations as an extenssion of ArrayList of recommendations.
 * It always has the array sorted by item id and user id to improve efficency on certain calls.
 * 
 * @author Martí J.
 */
public class ConjuntRecomanacions extends ArrayList<Recomanacio>{

    /*----- CONSTRUCTORS -----*/

    /**
     * Constructs a new empty instance.
     */
    public ConjuntRecomanacions() {
    	super();
    }

    /**
     * Constructs a new instance with the recommendations and ratings from the raw data
     *  obtained from the ratings file. It also sets the correct elements in the sets of 
     *  recommendations and ratings of the users. It sorts the elements by item id.
     *
     * @param      ci    Set of items from the Domain Controler
     * @param      cu    Set of users from the Domain Controler
     * @param      raw   The raw data from the ratings file
     *
     * @throws     ItemNotFoundException        Thrown if a item was not found.
     * @throws     UserNotFoundException        Thrown if a user was not found.
     * @throws     DataNotValid                 If some value is not correct(ratings,...)
     */
    public ConjuntRecomanacions(ConjuntItems ci, ConjuntUsuaris cu, ArrayList<ArrayList<String>> raw) throws ItemNotFoundException, UserNotFoundException, DataNotValidException {
    	this.afegirDades(ci,cu,raw);
    }


    /*----- CONSULTORES -----*/

    /**
     * Returns if a recommendation with a rating of an item <i>itemid</i> to a user <i>userid</i> 
     * exisist in the set of recommendations.
     *
     * @param      itemid  The id of the item
     * @param      userid  The id of the user
     *
     * @return     True if the rated recomndation has been found
     */
    public boolean existeixValoracio(int itemid, int userid) {
    	int pos = cercaBinaria(itemid,userid);
        if(pos < 0 || pos >= this.size()) return false;
    	else if(this.get(pos).checkIds(itemid, userid) && this.get(pos).getVal() != Recomanacio.nul) return true;
    	else return false;
    }

    /**
     * Gets the recommendation with item id <i>itemid</i> and user id <i>userid</i>.
     *
     * @param      itemid                           The item id
     * @param      userid                           The user id
     *
     * @return     The recommendation
     *
     * @throws     RecommendationNotFoundException  Thrown if the recommendation doesen't exist.
     */
    public Recomanacio getRecomanacio(int itemid, int userid) throws RecommendationNotFoundException {
		int pos = cercaBinaria(itemid,userid);
    	if(this.get(pos).checkIds(itemid, userid)) return this.get(pos);
    	else throw new RecommendationNotFoundException(itemid, userid);
    }

    /**
     * Gets the unrated recommendations of a user
     *
     * @param      userid  The userid
     *
     * @return     A ConjuntRecomanacions with the unrated recommendations.
     */
    public ConjuntRecomanacions getRecomanacionsNoValorades(int userid) {
        ConjuntRecomanacions cr = new ConjuntRecomanacions();

        for(int i = 0; i < this.size(); i++) {
            if(this.get(i).getUsuari().getId() == userid && !this.get(i).recomanacioValorada()) cr.add(this.get(i));
        }

        return cr;
    }

    /**
     * Gets the rated recommendations of a user
     *
     * @param      userid  The userid
     *
     * @return     A ConjuntRecomanacions with the rated recommendations.
     */
    public ConjuntRecomanacions getValoracions(int userid) {
        ConjuntRecomanacions cv = new ConjuntRecomanacions();

        for(int i = 0; i < this.size(); i++) {
            if(this.get(i).getUsuari().getId() == userid && this.get(i).recomanacioValorada()) cv.add(this.get(i));
        }

        return cv;
    }

    /**
     * Gets all the recommendations(rated and not rated) in orther to export them and save them in a file.
     *
     * @return     All recommendations + the header!!
     */
    public ArrayList<ArrayList<String>> getAllRecomanacions() {
        ArrayList<ArrayList<String>> D = new ArrayList<ArrayList<String>>();
        ArrayList<String> header = new ArrayList<String>(); header.add("userId"); header.add("itemId"); header.add("rating");
        D.add(header);
        for(int i = 0; i < this.size();i++){
            ArrayList<String> aux = new ArrayList<String>();
            aux.add(String.valueOf(this.get(i).getUsuari().getId()));
            aux.add(String.valueOf(this.get(i).getItem().getId()));
            aux.add(String.valueOf(this.get(i).getVal()));
            D.add(aux);
        }
        return D;
    }

    /*----- MODIFICADORES -----*/

    /**
     * Adds the specified recomendation <i>r</i> keeping the list ordered by id.
     *
     * @param      r     User to get added 
     *
     * @return     true (as specified by Collection.add(E))
     */
    @Override public boolean add(Recomanacio r) {
        int pos = cercaBinaria(r.getItem().getId(), r.getUsuari().getId());
        try {
            this.add(pos,r);
        }
        catch(IndexOutOfBoundsException e){
            return false;
        }
        return true;
    }

    public void removeRecomanacionsItem(int id) {
        for(Recomanacio r: this) {
            if(r.getItem().getId() == id) this.remove(r);
        }
    }

    public void removeRecomanacionsUsuari(int id) {
        for(Recomanacio r: this) {
            if(r.getUsuari().getId() == id) this.remove(r);
        }
    }

    /**
     * This function adds data to the set, from a set of Items, Usuaris and the raw data of a ratings.csv file.
     *
     * @param      ci    Set of items from the Domain Controler
     * @param      cu    Set of users from the Domain Controler
     * @param      raw   The raw data from the ratings file
     *
     * @throws     ItemNotFoundException        Thrown if a item was not found.
     * @throws     UserNotFoundException        Thrown if a user was not found.
     * @throws     RatingNotValidException      Thrown if a rating was not found.
     * @throws     UserIdNotValidException      Thrown if a user id was not found.
     * @throws     ItemIdNotValidException      Thrown if a item id was not found.
     */
    public void afegirDades(ConjuntItems ci, ConjuntUsuaris cu, ArrayList<ArrayList<String>> raw) throws ItemNotFoundException, UserNotFoundException, DataNotValidException {
        raw.remove(0);//elimina la capçalera

        for(ArrayList<String> fila : raw) {
            float v;
            Item i;
            Usuari u;

            try {
                v = Float.parseFloat(fila.get(2));
            }
            catch(NumberFormatException e) {
                throw new DataNotValidException(fila.get(2),"Rating value not valid. Needs to be a float between 0.0 and 5.0 and the floating part can be .0 or .5.");
            }

            if(v < 0.0 || v > 5.0 || !( v % 1 == 0.0 || v % 1 == 0.5 )) throw new DataNotValidException(fila.get(2),"Rating value not valid. Needs to be a float between 0.0 and 5.0 and the floating part can be .0 or .5.");

            try {
                i = ci.getItem(Integer.parseInt(fila.get(1)));
            }
            catch(NumberFormatException e) {
                throw new DataNotValidException(fila.get(1),"Item ID not valid. Needs to be an integer.");
            }

            try {
                //no es fa existeix perquè ja hauria d'existir(previament inicialitzat) sino dona l'excepció que toca
                u = cu.getUsuari(Integer.parseInt(fila.get(0)));
            }
            catch(NumberFormatException e) {
                throw new DataNotValidException(fila.get(0),"User ID not valid. Needs to be an integer.");
            }

            Recomanacio r = new Recomanacio(u, i, v);
            this.add(r);
        }
    }

    /*----- ALTRES -----*/

    //hauria de ser private

   /**
     * Returns de index of the element with item id = <i>item_id</i> and user 
     * id = <i>user_id</i>, or if it doesn't exist, the position where it should be added. 
     * It uses a binary search.
     *
     * @param      first      The first element
     * @param      last       The last element
     * @param      item_id    The item identifier
     * @param      usuari_id  The user identifier
     *
     * @return     The index where the user should be
     */
    public int cercaBinaria(int item_id, int usuari_id) {  
        int first = 0;
        int last = this.size()-1;
        while(first <= last) {
            int mid = (first+last)/2;
            int mid_item_id = this.get(mid).getItem().getId();
            int mid_usuari_id = this.get(mid).getUsuari().getId();
        
            if(mid_usuari_id > usuari_id) last = mid - 1;
            else if(mid_usuari_id < usuari_id) first = mid + 1;
            else if(mid_item_id > item_id) last = mid - 1;
            else if(mid_item_id < item_id) first = mid + 1;
            else return mid;
        }
        return first;
    }

}
