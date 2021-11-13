package src.recomanador.domini;

/**
 * This class describes a recomendation of a <u>item</u> to a <u>user</u> with a possible <u>rating</u>.
 * @author     Martí J.
 */
public class Recomanacio {
    
    /*----- ATRIBUTS -----*/

    private Usuari usr;
    private Item item;

    /**
     * Values between [0,5] by steps of 0.5
     */
    private float valoracio;
    
    /**
     * Value used as the null value for the atribute <i>valoracio</i>
     */
    private static final float nul = 0;


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
    public Recomanacio(Usuari u, Item i, float v) {
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
    public void setVal(float v) {
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
     * Returs if the recomendation has a rating
     *
     * @return     Rating not null
     */
    public boolean recomanacioValorada() {
        return valoracio != nul;
    }

}
