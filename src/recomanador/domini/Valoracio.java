package src.recomanador.domini;

public class Valoracio extends Recomanacio {

    /*----- ATRIBUTS -----*/

    private int puntuacio;
    
    
    /*----- CONSTRUCTORS -----*/

    public Valoracio(Recomanacio r) {

    }
    
    public int getPuntuacio() {
        return puntuacio;
    }

    public void setPuntuacio(int puntuacio) {
        this.puntuacio = puntuacio;
    }
}
