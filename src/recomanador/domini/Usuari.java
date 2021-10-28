package src.recomanador.domini;

public class Usuari {
    
    /*----- ATRIBUTS -----*/

    private int id;

    private ConjuntRecomanacions cr;
    private ConjuntValoracions cv;

    /*----- CONSTRUCTORS -----*/

    // Pre: -
    // Post: L'usuari s'ha creat amb l'id id
    public Usuari(int id) {
        this.id = id;
    }

    // Pre: -
    // Post: L'usuari s'ha creat amb l'id, el conjunt de recomanacions i valoracions id, cr i cv
    public Usuari(int id, ConjuntRecomanacions cr, ConjuntValoracions cv) {
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

    public void setRecomanacions(ConjuntRecomanacions cr) {
        this.cr = cr;
    }

    public Recomanacio getRecomanacio(int i) {
        return cr.get(i);
    }

    public Recomanacio eliminarRecomanacio(int i) {
        return cr.remove(i);
    }

    public Valoracio valorar(int recomanacio, int puntuacio) {
        Valoracio v = (valoracio) cr.get(recomanacio);
        cr.remove(recomanacio);
        v.setPuntuacio(puntuacio);
        cv.add(v);
        return v;
    }

    /*----- VALORACIONS -----*/

    public ConjuntValoracions getValoracions() {
        return cv;
    }

    public void setValoracions(ConjuntValoracions cv) {
        this.cv = cv;
    }

    public Valoracio getValoracio(int i) {
        return cv.get(i);
    }

    public Valoracio eliminarValoracio(int i) {
        return cv.remove(i);
    }

    public void ValorarValoracio(int valoracio, int puntuacio) {
        cv.get(valoracio).setPuntuacio(puntuacio);
    }
}
