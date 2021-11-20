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

    public void set_k(int K) throws DataNotValidException {
        if(K <= 0) throw new DataNotValidException(K, "El valor de K ha de ser superior a 0.");
        this.K = K;
    }

    public void set_Q(int Q) throws DataNotValidException{
        if(Q <= 0) throw new DataNotValidException(Q, "El valor de Q ha de ser superior a 0.");
        this.Q = Q;
    }

    public void seleccionar_algorisme(int a) throws DataNotValidException {
        if(a < 0 || a >= 2) throw new DataNotValidException(a, "Els valors per seleccionar algorisme son entre 0 i 1");
        this.ALGORISME_SELECCIONAT = a;
    }
	
	public int get_k() {
		return K;
	}
	
	public int get_Q() {
		return Q;
	}
	
	public int get_algorisme() {
		return ALGORISME_SELECCIONAT;
	}
	
    public ArrayList<ItemValoracioEstimada> run_algorithm(int user_ID, ConjuntItems items, ConjuntUsuaris usuaris, ConjuntRecomanacions valoracions) throws UserNotFoundException{

        ArrayList<ItemValoracioEstimada> recomanacions_alg = new ArrayList<ItemValoracioEstimada>();

        switch(ALGORISME_SELECCIONAT) {
            //collaborative filtering
            case 0:
                System.out.println("Executant Collaborative Filtering");
                CollaborativeFiltering colFilt = new CollaborativeFiltering(items, usuaris, valoracions);
                recomanacions_alg = colFilt.collaborativeFiltering(Q, user_ID, K);
                break;
                
            //content based filtering
            case 1:
                System.out.println("Executant Content-Based Filtering");
                ContentBasedFiltering BasedFilt = new ContentBasedFiltering(items, usuaris, valoracions);
                recomanacions_alg = BasedFilt.contentBasedFiltering(Q, user_ID, K);
                break;

            //Hybrid approaches
            case 2:
                break;
        }

        /*
        for(int i = 0; i < recomanacions_alg.size(); ++i) {
            float estimacio = recomanacions_alg.get(i).valoracioEstimada;
            DCG += (Math.pow(2, estimacio) - 1)/(Math.log(i+1)/Math.log(2));
            nDCG += (32 - 1)/(Math.log(i+1)/Math.log(2));
        }
        */

        //nDCG = DCG/nDCG;

        return recomanacions_alg;
    }
}
