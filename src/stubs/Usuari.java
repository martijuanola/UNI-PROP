package src.stubs;

import src.recomanador.domini.*;

/**
 * This class acts as a Stub for the class Usuari, implementing trivial or empty methods. 
 * to test other classes
 * @author Martí J.
 */
public class Usuari implements Comparable<Usuari> {
    
    /*----- ATRIBUTS -----*/

    private int id;
    private ConjuntRecomanacions cr;
    private ConjuntRecomanacions cv;

    /*----- CONSTRUCTORS -----*/

    public Usuari(int id) {
        this.id = id;
        cr = new ConjuntRecomanacions();
        cv = new ConjuntRecomanacions();
    }

    public Usuari(int id, ConjuntRecomanacions cr, ConjuntRecomanacions cv) {
        this.id = id;
        this.cr = cr;
        this.cv = cv;
    }
    
    /*----- GETTERS -----*/

    public int getId() {
        return id;
    }

    public ConjuntRecomanacions getRecomanacions() {
        return cr;
    }

    public Recomanacio getRecomanacio(int i) {
        return cr.get(i);
    }

    public ConjuntRecomanacions getValoracions() {
        return cv;
    }

    public Recomanacio getValoracio(int i) {
        return cv.get(i);
    }

    /*----- SETTERS -----*/

    public void setRecomanacions(ConjuntRecomanacions cr) {
        this.cr = cr;
    }

    public void setValoracions(ConjuntRecomanacions cv) {
        this.cv = cv;
    }

    public Recomanacio eliminarRecomanacio(int i) {
        return cr.remove(i);
    }

    public Recomanacio eliminarValoracio(int i) {
        return cv.remove(i);
    }

    public Recomanacio valorarRecomanacio(int rec, float pun) {
        return new Recomanacio(new Usuari(1), new Item(1));
    }

    public void valorarValoracio(int val, float pun) {}

    @Override public int compareTo(Usuari u2) {
        return Integer.compare(this.id, u2.id);
    }
}
