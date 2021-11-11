package src.recomanador.domini;

public class Usuari {
    
    /*----- ATRIBUTS -----*/

    private int id;

    private ConjuntRecomanacions cr;
    private ConjuntRecomanacions cv;

    /*----- CONSTRUCTORS -----*/

    // Pre: -
    // Post: L'usuari s'ha creat amb l'id id
    public Usuari(int id) {
        this.id = id;
    }

    // Pre: -
    // Post: L'usuari s'ha creat amb l'id, el conjunt de recomanacions i valoracions id, cr i cv
    public Usuari(int id, ConjuntRecomanacions cr, ConjuntRecomanacions cv) {
        this.id = id;
        this.cr = cr;
        this.cv = cv;
    }
    
    /*----- ID -----*/

    // Pre: -
    // Post: Retorna el id
    public int getId() {
        return id;
    }

    // Pre: -
    // Post: L'usuari té id id
    public void setId(int id) {
        this.id = id;
    }

    /*----- RECOMANACIONS -----*/

    // Pre: -
    // Post: Retorna les recomanacions del usuari
    public ConjuntRecomanacions getRecomanacions() {
        return cr;
    }

    // Pre: -
    // Post: El punter de les recomanacions ara apunta a cr
    public void setRecomanacions(ConjuntRecomanacions cr) {
        this.cr = cr;
    }

    // Pre: L'índex i és correcte
    // Post: Retorna la recomanació que pertany al índex i 
    public Recomanacio getRecomanacio(int i) {
        return cr.get(i);
    }

    // Pre: L'índex i és correcte
    // Post: Elimina la recomanació que pertany al índex i 
    public Recomanacio eliminarRecomanacio(int i) {
        return cr.remove(i);
    }


//falta canviar això
    // Pre: L'index recomanacio és correcte
    // Post: La recomanació s'elimina de recomanacions i s'afegeix com a valoració amb puntuació
    /*public Recomanacio valorar(int recomanacio, int puntuacio) {
        Recomanacio v = (Recomanacio) cr.get(recomanacio);
        cr.remove(recomanacio);
        v.setPuntuacio(puntuacio);
        cv.add(v);
        return v;
    }*/

    /*----- VALORACIONS -----*/

    // Pre: -
    // Post: Retorna el conjunt de valoracions del usuari
    public ConjuntRecomanacions getValoracions() {
        return cv;
    }

    // Pre: -
    // Post: El conjunt de valoracions ara apunta a cv
    public void setValoracions(ConjuntRecomanacions cv) {
        this.cv = cv;
    }

    // Pre: L'índex i és correcte
    // Post: Retorna la valoració que pertany al índex i 
    public Recomanacio getValoracio(int i) {
        return cv.get(i);
    }

    // Pre: L'índex i és correcte
    // Post: Elimina la valoració que pertany al índex i 
    public Recomanacio eliminarValoracio(int i) {
        return cv.remove(i);
    }

    // Pre: L'índex valoració és correcte
    // Post: La nova puntuació de la valoració és puntuacio
    public void valorarValoracio(int valoracio, int puntuacio) {
        cv.get(valoracio).setVal(puntuacio);
    }
}
