package src.recomanador.domini;

import src.recomanador.excepcions.RatingNotValidException;
import src.recomanador.excepcions.RecommendationRatedException;
import src.recomanador.excepcions.RecommendationNotRatedException;


/**
 * This class describes a recommendation of a <u>item</u> to a <u>user</u> with a possible <u>rating</u>.
 * @author     Martí J.
 */
public class Recomanacio implements Comparable<Recomanacio> {
    
    /*----- ATRIBUTS -----*/
    /**User that recieved the recommendation*/
    private Usuari usr;

    /**Recommended item*/
    private Item item;

    /**Values between [0,5] by steps of 0.5*/
    private float valoracio;
    
    /**
     * Value used as the null value for the atribute <i>valoracio</i>
     */
    public static final float nul = 0;


    /*----- CONSTRUCTORS -----*/

    /**
     * Constructs a new instance.
     *
     * @param      u     usuari
     * @param      i     item
     */
    public Recomanacio(Usuari u, Item i) {
        usr = u;
        item = i;
        valoracio = 0;
    }

    /**
     * Constructs a new instance.
     *
     * @param      u     user
     * @param      i     item
     * @param      v     rating
     */
    public Recomanacio(Usuari u, Item i, float v) throws RatingNotValidException {
        if(v < 0.0 || v > 5.0 || !( v % 1 == 0.0 || v % 1 == 0.5 )) throw new RatingNotValidException(v);
        usr = u;
        item = i;
        valoracio = v;
    }


    /*----- MODIFICADORES -----*/

    /**
     * Sets the value of rating
     *
     * @param      v     The new value
     */
    public void setVal(float v) throws RatingNotValidException, RecommendationRatedException {
        if(v < 0.0 || v > 5.0 || !( v % 1 == 0.0 || v % 1 == 0.5 )) throw new RatingNotValidException(v);

        try {
            if(valoracio == 0.0 && v > 0.0) this.usr.moureRecomanacio(this,true);
            else if(valoracio > 0.0 && v == 0.0) this.usr.moureRecomanacio(this,false);
        }
        catch(RecommendationRatedException | RecommendationNotRatedException e) {
            System.out.println("No hauria de petar mai");
        }
        valoracio = v;
    }


    /*----- CONSULTORES -----*/

    /**
     * Gets the user.
     *
     * @return     The user.
     */
    public Usuari getUsuari() {
        return usr;
    }

    /**
     * Gets the item.
     *
     * @return     The item.
     */
    public Item getItem() {
        return item;
    }

    /**
     * Gets the value of valoració.
     *
     * @return     The value of valoració.
     */
    public float getVal() {
        return valoracio;
    }

    /**
     * Returs if the recommendation has a rating
     *
     * @return     Rating not null
     */
    public boolean recomanacioValorada() {
        return valoracio != nul;
    }

    /**
     * Checks if the item has id <i>id_item</i> and that user has id
     * <i>id_usuari</i>.
     *
     * @param      id_item     The identifier of item
     * @param      id_usuari  The identifier of usuari
     *
     * @return     true if the ids are the same as the instance's
     */
    public boolean checkIds(int id_item, int id_usuari) {
        if(id_item != this.item.getId()) return false;
        else if(id_usuari != this.usr.getId()) return false;
        else return true;
    }

    /**
     * Checks if the item and user of the recomendation are <i>i</i> and <i>u</i>.
     *
     * @param      i     Item
     * @param      u     User
     *
     * @return     True if both objects are the same
     */
    public boolean checkKeys(Item i, Usuari u) {
        return usr == u && item == i;
        //return usr.getId() == u.getId() && item.getId() == i.getId();
    }

    /**
     * Compares the instance with another recommendation <i>r2</i>
     *
     * @param      r2    The other recommendation
     *
     * @return     Order by item id first and then accoding to user id
     */
    @Override public int compareTo(Recomanacio r2) {
        int a = Integer.compare(this.item.getId(), r2.getItem().getId());
        int b = Integer.compare(this.usr.getId(), r2.getUsuari().getId());
        int c = Float.compare(this.getVal(), r2.getVal());
        if(a != 0) return a;
        else if(b != 0) return b;
        else return c;
    }
}
