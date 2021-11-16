package src.recomanador.domini;

import java.util.ArrayList;

import src.recomanador.excepcions.UserNotFoundException;
import src.recomanador.excepcions.DataNotValidException;

/**
 * This class is used to call the algorithm and keep saved its parameters.
 * @author Adrià F.
 */
public class ControladorDominiAlgorisme {

    /*----- ATRIBUTS -----*/

    /**
     * Indicates which algorithm to use. Options are:
     * 0 - collaborative filtering(Kmeans + Slope1)
     * 1 - content-based filtering(KNN)
     * 2 - hybrid approaches
     */
    int ALGORISME_SELECCIONAT = 0;

    /**
     * Value used in the Kmeans algorithm. Determines how many centroids are used.
     */
    int K = 5;

    //s'ha d'acabar de mirar
    //how many items to give
    int Q = 5;

    

    public ControladorDominiAlgorisme() {}

    public void setK(int k) throws DataNotValidException {
        if(k <= 0) throw new DataNotValidException(k, "El valor de K ha de ser superior a 0.");
        this.K = k;
    }

    public void set_Q(int Q) {
        this.Q = Q;
    }

    public void setAlgorisme(int a) throws DataNotValidException {
        if(a >= 0 && a <= 2) throw new DataNotValidException(a, "Els valors per seleccionar algorisme son entre 0 i 2");
        this.ALGORISME_SELECCIONAT = a;
    }

    public ArrayList<Item> run_algorithm(int user_ID, ConjuntItems items, ConjuntUsuaris usuaris, ConjuntRecomanacions valoracions) throws UserNotFoundException{
        switch(ALGORISME_SELECCIONAT) {
            //collaborative filtering
            case 0:
                CollaborativeFiltering colFilt = new CollaborativeFiltering(items, usuaris, valoracions);
                return colFilt.collaborativeFiltering(Q, user_ID, K);
            //content based filtering
            case 1:
                ContentBasedFiltering BasedFilt = new ContentBasedFiltering(items, usuaris, valoracions);
                return BasedFilt.contentBasedFiltering(Q, user_ID);

            //Hybrid approaches
            case 2:


            default:
                return null;
        }
    }
}
