package src.recomanador.domini;
import src.recomanador.persistencia.*;

public class ControladorDomini {
    
    /*----- ATRIBUTS -----*/

    ControladorPersistencia cp;
    ConjuntUsuaris cu;
    
    
    /*----- CONSTRUCTORS -----*/

    public controladorDomini() {
        cp = new ControladorPersistencia();
        cu = new ConjuntUsuaris();
    }
}
