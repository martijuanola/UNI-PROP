package src.recomanador.domini;

public class Recomanacio {
    
    /*----- ATRIBUTS -----*/

    private Usuari usr;
    private Item item;

    private float valoracio;
    
    private static final float nul = 0;


    /*----- CONSTRUCTORS -----*/

    public Recomanacio(Usuari u, Item i) {
        usr = u;
        item = i;
        valoracio = 0;
    }

    public Recomanacio(Usuari u, Item i, float v) {
        usr = u;
        item = i;
        valoracio = v;
    }


    /*----- MODIFICADORES -----*/

    public void setVal(float v) {
        valoracio = v;
    }


    /*----- CONSULTORES -----*/

    
    public Usuari getUsuari() {
        return usr;
    }

    public Item getItem() {
        return item;
    }

    public float getVal() {
        return valoracio;
    }

    public boolean recomanacioValorada() {
        return valoracio != nul;
    }

}
