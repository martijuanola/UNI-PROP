package src.recomanador.domini;

import src.recomanador.persistencia.*;
import src.recomanador.excepcions.*;

import java.util.ArrayList;

public class ControladorDomini {
	
    
	/*----- CONSTANTS -----*/
	public static final int NULL_ID = -1;
	

	/*----- ATRIBUTS -----*/
	
    ControladorPersistencia cp;

    //Potser faltarà afegir-ne d'altres per utiltizar amb els testos
    public ConjuntUsuaris cu;
    public ConjuntRecomanacions cr;
    public ConjuntItems ci;

    /**
     * Id of the user/actor of the application
     */
    int id;

    /**
     * Indicates which algorithm to use. Options are:
     * 0 - Kmeans + Slope1
     * 1 - KNN
     */
    int algorithm;

    /**
     * Value used in the Kmeans algorithm. Determines how many centroids are used.
     */
    int k; // s'ha de fer servir bé si no te valor

    /**
     * True if active user/actor has admin privileges
     */
    boolean admin;
    

    /*----- CONSTRUCTORS -----*/

    public ControladorDomini() {
        cp = new ControladorPersistencia();

        cu = new ConjuntUsuaris();
        cr = new ConjuntRecomanacions();
        ci = new ConjuntItems();

        id = NULL_ID;
        algorithm = 0;
        k = 0;
        admin = false;
    }
    

     /**
     * Calls the persistence controler to get all the data from a previous stored session
     * and initilizes all the necessari arrays of data(ConjuntItems, ConjuntUsuaris, ConjuntRecomanacions and others)   
     *
     * @param      directory  the directory where the files have been stored
     */

    /*----- DATA & FILES -----*/

    public void carregarCarpeta(String directory) throws FolderNotFoundException, FolderNotValidException {

        //Funció per enviar el nom de la carpeta al controlador de persistència i mirar que és valida
        cp.escollirProjecte(directory);
       
        try {
            ArrayList<ArrayList<String>> items = cp.carregarItemsCarpeta();
            ci = new ConjuntItems(items);

            ArrayList<ArrayList<String>> valoracions = cp.carregarRecomanacionsCarpeta();            
            cu = new ConjuntUsuaris(valoracions);
            cr = new ConjuntRecomanacions(ci,cu,valoracions);
        }
        catch(ItemTypeNotValidException e) {
            //també un altre not valid file o algo així, es pot diferenciar de la resta passant un string a cada cas
            //o sigui mateixa excepcion però amb missatges d'error diferents
        }
        catch( ItemNotFoundException | UserNotFoundException | RatingNotValidException | UserIdNotValidException | ItemIdNotValidException e) {
            //throw new invalid file o algo d'aquest estil
        }
    }
    
    
    public void carregarRatings(String fitxer) /*throws FileNotFoundException, FileNotValidException*/ {
        //cp.funcio(fitxer);
    }

    public void carregarItems(String fitxer) /*throws FileNotFoundException, FileNotValidException*/ {
        //cp.funcio(fitxer);
    }



    //Funcions per obtenir i guardar info del sistema:
        //Get pesos
        //Get algorithm(int)
        //Get k(int)
        //??
    
    //Guardar

    //Funcions per obtenir informació(per display):
        //Get recomanacions usuari actiu
        //Get capçalera items(només per Admin)
        //Get info item
        //*Get pesos
        //*Get algorithm(int)
        //*Get k(int)

    /*----- ALTRES -----*/
    
    /**
     * Logs out: id of active user is set to null and privileges to regular.
     */
    public void logout() {
        id = NULL_ID;
        admin = false;
    }


    /*----- Funcions d'ADMIN -----*/

    /**
     * Sets the algorithm that the application will use.
     *
     * @param      a                      The value to determine the algorithm
     *
     * @throws     DataNotValidException  Thrown when the input is not a possible value
     * @throws     PrivilegesException    Thrown if the active user is not loged in as an admin
     */
    public void setAlgorithm(int a) throws PrivilegesException, DataNotValidException {
        if(!admin) throw new PrivilegesException();
        if(a >= 0 && a <= 5) throw new DataNotValidException(a, "Els valors per seleccionar algorisme son entre 0 i 5");

        algorithm = a;
    }

    public void setK(int kk) throws DataNotValidException {
        if(k <= 0) throw new DataNotValidException(k, "El valor de K ha de ser superior a 0.");
        k = kk;
    }

    /**
     * Sets the weight of an atribute on the set of items.
     *
     * @param      a                              Index of the atribute
     * @param      w                              New weight
     *
     * @throws     ItemWeightNotCorrectException  Thrown when the weight value is not valid
     */
    public void setPes(int a, float w) throws ItemWeightNotCorrectException {
        ci.assignarPes(a, w);
    }




    /*----- Funcions d'USER -----*/
    
    //Comprovar tema excepcions d'ID
    /**
     * Enters as a regular active user.
     *
     * @param      identificador  Id of the active user
     */
    public void establirIdActiva(int identificador) {
		id = identificador;
        admin = false;
	}

    //get new recomendations

 


    /*----- PROVES I COSES QUE ES TREURAN -----*/

    public void prova2() {
        ConjuntItems.assignarNom("HEY NO SE QUE POSAR");
        System.out.println("Natributs: " + ConjuntItems.getNumAtributs());
        for (int i = 0; i < ConjuntItems.getNumAtributs(); ++i) {
            System.out.println("---->" + ConjuntItems.getNomAtribut(i) + " " + ConjuntItems.getSTipus(i));
            System.out.println("Minim: " + " " + ci.getMinMaxAtribut(i, false));
            System.out.println("Maxim: " + " " + ci.getMinMaxAtribut(i, true));
        }
        //ci.printItems();
        //ci.printId();
    }
}
