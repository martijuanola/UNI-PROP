package src.recomanador.domini;

import java.util.ArrayList;

import src.recomanador.excepcions.UserNotFoundException;

/**
 * This class is used to call the algorithm and keep saved its parameters.
 * @author Adrià F.
 */
public class ControladorDominiAlgorisme {
    //0 - collaborative filtering
    //1 - content-based filtering
    //2 - hybrid approaches
    int ALGORISME_SELECCIONAT = 0;

    //how many items to give
    int Q = 5;

    //for kmeans
    int K = 5;

    public ControladorDominiAlgorisme() {}

    public void set_k(int k) {
        this.K = k;
    }

    public void set_Q(int Q) {
        this.Q = Q;
    }

    public void seleccionar_algorisme(int a) {
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
