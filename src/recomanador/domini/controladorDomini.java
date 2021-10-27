package src.recomanador.domini;
import src.recomanador.persistencia.*;

public class controladorDomini {
    controladorPersistencia cp;
    conjuntUsuaris cu;
    public controladorDomini() {
        cp = new controladorPersistencia();
        cu = new conjuntUsuaris();
    }
}
