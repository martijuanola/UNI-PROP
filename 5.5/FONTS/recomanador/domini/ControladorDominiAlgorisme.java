package src.recomanador.domini;

import java.util.ArrayList;

import src.recomanador.excepcions.UserNotFoundException;
import src.recomanador.excepcions.DataNotValidException;

/**
 * This class is used to call the algorithm and keep saved its parameters.
 * @author Adrià F.
 */
public class ControladorDominiAlgorisme {

    /*----- STATICS -----*/

    /** Contains the only instance of the class **/
    private static ControladorDominiAlgorisme inst;

    /**
     * Returs the only instance of the class, and if it's not created, it creates it.
     *
     * @return     The instance.
     */
    public static ControladorDominiAlgorisme getInstance() {
        if(ControladorDominiAlgorisme.inst == null) inst = new ControladorDominiAlgorisme();
        return inst;
    }

    /**
     * Indicates which algorithm to use. Options are:
     * 0 - collaborative filtering(Kmeans + Slope1)
     * 1 - content-based filtering(KNN)
     * 2 - hybrid approaches
     */
    private int ALGORISME_SELECCIONAT;

    /**
     * Value used in the Kmeans algorithm. Determines how many centroids are used.
     */
    private int K;

    /**
     * How many items to be recommended
     */
    private int Q;

    /*----- ATRIBUTS -----*/
    
    /**instance of the Collaborative Filtering Algorithm*/
    CollaborativeFiltering colFilt;
    /**instance of the Hybrid Filtering Algorithm*/
    HybridFiltering HybFilt;
    /**instance of the Content Based Filtering Algorithm*/
    ContentBasedFiltering BasedFilt;

    

    /*----- CONSTRUCTORS -----*/

    /**
     *  Constructs a new empty instance
     */
    private ControladorDominiAlgorisme() {
        resetValues();
        colFilt = CollaborativeFiltering.getInstance();
        BasedFilt = ContentBasedFiltering.getInstance();
        HybFilt = HybridFiltering.getInstance();
    }

    /*----- SETTERS -----*/

    /**
     *  Sets the attribute K of the active instance
     *
     * @param       K   an integer value representing the k value
     * 
     * @throws     DataNotValidException        K has a invalid value
     */
    public void set_k(int K) throws DataNotValidException {
        if(K <= 0) throw new DataNotValidException(K, "El valor de K ha de ser superior a 0.");
        this.K = K;
    }

    /**
     *  Sets the attribute Q of the active instance
     *
     * @param       Q   an integer value representing how many items to be recommended
     * 
     * @throws     DataNotValidException        Q has a invalid value
     */
    public void set_Q(int Q) throws DataNotValidException {
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
     *                  
     * @throws     DataNotValidException        Algorithm has a invalid value
     */
    public void seleccionar_algorisme(int a) throws DataNotValidException {
        if(a < 0 || a > 2) throw new DataNotValidException(a, "Els valors per seleccionar algorisme son entre 0 i 2");
        this.ALGORISME_SELECCIONAT = a;
    }

    /**
     * Reset the 3 values of the controller
     */
    public void resetValues() {
        this.ALGORISME_SELECCIONAT = 0;
        this.K = 5;
        this.Q = 5;
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
     * @param       items         ConjuntItems from which the ratings will be generated
     * @param       usuaris       ConjuntUsuaris which will help generate the ratings on Collaborative Filtering
     * @param       valoracions   ConjuntRecomanacions which contain the ratings from which we will base our new ratings.
     * 
     */
    public void setData(ConjuntItems items, ConjuntUsuaris usuaris, ConjuntRecomanacions valoracions) {
        colFilt.setData(items, usuaris, valoracions);
        BasedFilt.setData(items, usuaris, valoracions);
        HybFilt.setData(items, usuaris, valoracions);
    }

    /**
     *  Runs the selected recommendation algorithm, without updating the values.
     *
     * @param       user_ID       user whose recommendations will be generated
     * 
     * @return     A sorted ArrayList of Q ItemValoracioEstimada, containing the items to be recommended and their estimated ratings.
     *              How these are generated depends on the attributes Q, K and ALGORISME_SELECCIONAT of the active ControladorDominiAlgorisme instance.
     *              
     * @throws     UserNotFoundException if the id specified is not valid
     */
    public ArrayList<ItemValoracioEstimada> run_algorithm(int user_ID) throws UserNotFoundException {

        ArrayList<ItemValoracioEstimada> recomanacions_alg = new ArrayList<ItemValoracioEstimada>();

        switch(ALGORISME_SELECCIONAT) {
            //collaborative filtering
            case 0:
                System.out.println("Executant Collaborative Filtering");
                recomanacions_alg = colFilt.collaborativeFiltering(Q, user_ID, K);
                break;
                
            //content based filtering
            case 1:
                System.out.println("Executant Content-Based Filtering");
                recomanacions_alg = BasedFilt.contentBasedFiltering(Q, user_ID, K);
                break;
                
            //Hybrid approaches
            case 2:
                System.out.println("Executant Hybrid Filtering");
                recomanacions_alg = HybFilt.hybridFiltering(Q, user_ID, K);
                break;
        }
        
        return recomanacions_alg;
    }
}
