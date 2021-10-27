package src.recomanador.domini;

public class usuari {
    private int id;
    private conjuntRecomanacions cr;
    private conjuntValoracions cv;

    /*----- CONSTRUCTORS -----*/

    public usuari(int id) {
        this.id = id;
    }

    public usuari(int id, conjuntRecomanacions cr, conjuntValoracions cv) {
        this.id = id;
        this.cr = cr;
        this.cv = cv;
    }
    
    /*----- ID -----*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*----- RECOMANACIONS -----*/

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

}
