package src.recomanador.domini;

import src.recomanador.excepcions.*;

import java.util.Collections;  
import java.util.ArrayList;

/**
 * This class describes a set of recommendations as an extenssion of ArrayList of recommendations.
 * It always has the array sorted by item id and user id to improve efficency on certain calls.
 * @author Martí J.
 */
public class ConjuntRecomanacions extends ArrayList<Recomanacio>{
	
	/*----- CONSTANTS -----*/
    /*----- ATRIBUTS -----*/


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
     */
    public ConjuntRecomanacions(ConjuntItems ci, ConjuntUsuaris cu, ArrayList<ArrayList<String>> raw) {
    	
    	raw.remove(0);//elimina la capçalera

    	for(ArrayList<String> fila : raw) {
    		Item i = ci.getItem(Integer.parseInt(fila.get(1)));
    		Usuari u = cu.getUsuari(Integer.parseInt(fila.get(0)));
    		float v = Integer.parseInt(fila.get(2));

    		Recomanacio r = new Recomanacio(u, i, v);
    		this.add(r);

    		if(v != Recomanacio.nul) u.getValoracions().add(r);
    		else u.getRecomanacions().add(r);
    	}

    	Collections.sort(this);
    }


    /*----- CONSULTORES -----*/

    /**
     * Returns if a recomendation between of an item <i>itemid</i> to a user <i>userid</i> exisist in 
     * the set of recommendations.
     *
     * @param      itemid  The id of the item
     * @param      userid  The id of the user
     *
     * @return     True if the recomndation has been found
     */
    public boolean existeixRecomacio(int itemid, int userid) {
    	int pos = cercaBinaria(0,this.size()-1,itemid,userid);
    	if(this.get(pos).checkIds(itemid, userid)) return true;
    	else return false;
    }

    /**
     * Returns if a recomendation with a rating between of an item <i>itemid</i> to a user <i>userid</i> 
     * exisist in the set of recommendations.
     *
     * @param      itemid  The id of the item
     * @param      userid  The id of the user
     *
     * @return     True if the recomndation has been found
     */
    public boolean existeixValoracio(int itemid, int userid) {
    	int pos = cercaBinaria(0,this.size()-1,itemid,userid);
    	if(this.get(pos).checkIds(itemid, userid) && this.get(pos).getVal() != Recomanacio.nul) return true;
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
		int pos = cercaBinaria(0,this.size()-1,itemid,userid);
    	if(this.get(pos).checkIds(itemid, userid)) return this.get(pos);
    	else throw new RecommendationNotFoundException(itemid, userid);
    }

    /**
     * Used to get the users that had recomended the item <i>item</i>.
     *
     * @param      item  The item
     *
     * @return     Returns a set of users.
     */
    public ConjuntUsuaris usuarisRecomanats(Item item) {
    	ConjuntUsuaris cu = new ConjuntUsuaris();
    	
    	int pos = cercaBinaria(0,this.size()-1,item.getId(),0);

    	while(pos < this.size() && this.get(pos).getItem() == item) {
    		cu.add(this.get(pos).getUsuari());
    		pos += 1;
    	}

    	return cu;
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
        int pos = cercaBinaria(0, this.size(), r.getItem().getId(), r.getUsuari().getId());
        try {
            this.add(pos,r);
        }
        catch(IndexOutOfBoundsException e){
            return false;
        }
        return true;
    }


    /*----- ALTRES -----*/

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
    private int cercaBinaria(int first, int last, int item_id, int usuari_id) {  
        while(first <= last) {
            int mid = (first+last)/2;
            int mid_item_id = this.get(mid).getItem().getId();
            int mid_usuari_id = this.get(mid).getUsuari().getId();
            
            if(mid_item_id > item_id) last = mid - 1;
            else if(mid_item_id < item_id) first = mid + 1;
            else if(mid_usuari_id > usuari_id) last = mid - 1;
            else if(mid_usuari_id < usuari_id) first = mid + 1;
            else return mid;
        }
        if(this.get(first).getItem().getId() > item_id || (this.get(first).getItem().getId() == item_id && this.get(first).getUsuari().getId() > usuari_id)) return first;
        else return last;
    }
}