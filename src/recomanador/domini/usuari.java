package src.recomanador.domini;

public class usuari {
    private int id;
    private conjuntRecomanacions cr;
    private conjuntValoracions cv;

    /*----- CONSTRUCTORS -----*/

    // Pre: -
    // Post: L'usuari s'ha creat amb l'id id
    public usuari(int id) {
        this.id = id;
    }

    // Pre: -
    // Post: L'usuari s'ha creat amb l'id, el conjunt de recomanacions i valoracions id, cr i cv
    public usuari(int id, conjuntRecomanacions cr, conjuntValoracions cv) {
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
    public conjuntRecomanacions getRecomanacions() {
        return cr;
    }

    public void setRecomanacions(conjuntRecomanacions cr) {
        this.cr = cr;
    }

    public recomanacio getRecomanacio(int i) {
        return cr.get(i);
    }

    public recomanacio eliminarRecomanacio(int i) {
        return cr.remove(i);
    }

    public valoracio valorar(int recomanacio, int puntuacio) {
        valoracio v = new valoracio(cr.get(recomanacio));
        cr.remove(recomanacio);
        cv.add(cv.size()-1, v);
        return v;
    }

    /*----- VALORACIONS -----*/

    public conjuntValoracions getValoracions() {
        return cv;
    }

    public void setValoracions(conjuntValoracions cv) {
        this.cv = cv;
    }

    public valoracio getValoracio(int i) {
        return cv.get(i);
    }

    public valoracio eliminarValoracio(int i) {
        return cv.remove(i);
    }

    public void valorarValoracio(int valoracio, int puntuacio) {
        cv.get(valoracio).setPuntuacio(puntuacio);
    }
}
