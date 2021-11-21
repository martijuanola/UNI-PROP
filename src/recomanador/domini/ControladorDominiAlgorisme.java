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

    /**
     * How many items to be recommended
     */
    int Q = 5;

    /*----- CONSTRUCTORS -----*/

    /**
     *  Constructs a new empty instance
     *
     * @return      a new instance of ControladorDominiAlgorisme, which will be empty
     */
    public ControladorDominiAlgorisme() {}

    /*----- SETTERS -----*/

    /**
     *  Sets the attribute K of the active instance
     *
     * @param       k   an integer value representing the k value
     */
    public void set_k(int K) throws DataNotValidException {
        if(K <= 0) throw new DataNotValidException(K, "El valor de K ha de ser superior a 0.");
        this.K = K;
    }

    /**
     *  Sets the attribute Q of the active instance
     *
     * @param       Q   an integer value representing how many items to be recommended
     */
    public void set_Q(int Q) throws DataNotValidException{
        if(Q <= 0) throw new DataNotValidException(Q, "El valor de Q ha de ser superior a 0.");
        this.Q = Q;
    }

    /**
     *  Sets the attributre ALGORISME_SELECCIONAT of the active instance
     *
     * @param       a   an integer representing which recommendation algorithm should be ran.
     *                  0 represents collaborative filtering usking k-means+Slope-1,
     *                  1 represents content based filtering using k-Nearest-Neighbours and
     *                  2 represents hybrid approaches
     */
    public void seleccionar_algorisme(int a) throws DataNotValidException {
        if(a < 0 || a >= 2) throw new DataNotValidException(a, "Els valors per seleccionar algorisme son entre 0 i 1");
        this.ALGORISME_SELECCIONAT = a;
    }
	
    /*----- GETTERS -----*/

    /**
     *  Gets the attribute K of the active instance
     *
     * @return       the integer value of the K attribute of the active instance
     */
	public int get_k() {
		return K;
	}
	
    /**
     *  Gets the attribute Q of the active instance
     *
     * @return       the integer value of the Q attribute of the active instance
     */
	public int get_Q() {
		return Q;
	}
	
    /**
     *  Gets the attribute ALGORISME_SELECCIONAT of the active instance
     *
     * @return       the integer value of the ALGORISME_SELECCIONAT attribute of the active instance
     */
	public int get_algorisme() {
		return ALGORISME_SELECCIONAT;
	}
	
    /*----- OPERATORS -----*/

    /**
     *  Runs the selected recommendation algorithm.
     *
     * @param       user_ID       user whose recommendations will be generated
     * @param       items         ConjuntItems from which the ratings will be generated
     * @param       usuaris       ConjuntUsuaris which will help generate the ratings on Collaborative Filtering
     * @param       valoracions   ConjuntRecomanacions which contain the ratings from which we will base our new ratings.
     * 
     * @returns     A sorted ArrayList of Q ItemValoracioEstimada, containing the items to be recommended and their estimated ratings.
     *              How these are generated depends on the attributes Q, K and ALGORISME_SELECCIONAT of the active ControladorDominiAlgorisme instance.
     * 
     */
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
