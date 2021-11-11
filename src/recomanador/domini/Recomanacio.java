package src.recomanador.domini;

/**
 * Classe que representa la recomanació(valorada o no) d'un item a un ususari
 */
public class Recomanacio {
    
    /*----- ATRIBUTS -----*/

    private Usuari usr;
    private Item item;

    private float valoracio;
    
    private static final float nul = 0;


    /*----- CONSTRUCTORS -----*/

    /**
     * Crea una nova instància de recomanació sense valoració
     *
     * @param      u     Usuari que ha rebut la recomanació
     * @param      i     Item recomanat
     */
    public Recomanacio(Usuari u, Item i) {
        usr = u;
        item = i;
        valoracio = 0;
    }

    /**
     * Crea una nova instància de recomanació amb valoració
     *
     * @param      u     Usuari que ha rebut la recomanació
     * @param      i     Item recomanat
     * @param      v     Valoració de la recomanació(de l'item per l'usuari)
     */
    public Recomanacio(Usuari u, Item i, float v) {
        usr = u;
        item = i;
        valoracio = v;
    }


    /*----- MODIFICADORES -----*/

    /**
     * Assigna un valor per valoració
     *
     * @param      v     Nou valor per valoració
     */
    public void setVal(float v) {
        valoracio = v;
    }


    /*----- CONSULTORES -----*/

    /**
     * Retorna l'usuari de la recomanació
     *
     * @return     L'usuari
     */
    public Usuari getUsuari() {
        return usr;
    }

    /**
     * Retorna l'item
     *
     * @return     L'item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Retorna la valoració
     *
     * @return     The value.
     */
    public float getVal() {
        return valoracio;
    }

    /**
     * { function_description }
     *
     * @return     { description_of_the_return_value }
     */
    public boolean recomanacioValorada() {
        return valoracio != nul;
    }

}
