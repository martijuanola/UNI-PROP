package src.recomanador.domini;

import src.recomanador.persistencia.*;
import src.recomanador.excepcions.*;

import java.util.ArrayList;

public class ControladorDomini {
	
    
	/*----- CONSTANTS -----*/
	public static final int NULL_ID = -1;
	

	/*----- ATRIBUTS -----*/
	
    ControladorPersistencia cp;

    ControladorDominiAlgorisme cda;

    //Potser faltarà afegir-ne d'altres per utiltizar amb els testos
    public ConjuntUsuaris cu;
    public ConjuntRecomanacions cr;
    public ConjuntItems ci;

    /**
     * Id of the user/actor of the application
     */
    int id;

    /**
     * True if active user/actor has admin privileges
     */
    boolean admin;
    

    /*----- CONSTRUCTORS -----*/

    public ControladorDomini() {
        cp = new ControladorPersistencia();

        cda = new ControladorDominiAlgorisme();

        cu = new ConjuntUsuaris();
        cr = new ConjuntRecomanacions();
        ci = new ConjuntItems();

        id = NULL_ID;
        admin = false;
    }
    

     /**
     * Calls the persistence controler to get all the data from a previous stored session
     * and initilizes all the necessari arrays of data(ConjuntItems, ConjuntUsuaris, ConjuntRecomanacions and others)   
     *
     * @param      directory  the directory where the files have been stored
     */

    /*----- DATA & FILES -----*/

    public void carregarCarpeta(String directory) throws FolderNotFoundException, FolderNotValidException, DataNotValidException {

        //Funció per enviar el nom de la carpeta al controlador de persistència i mirar que és valida
        cp.escollirProjecte(directory);
       
        try {
            ArrayList<ArrayList<String>> items = cp.carregarItemsCarpeta();
            ci = new ConjuntItems(items);

            ArrayList<ArrayList<String>> valoracions = cp.carregarRecomanacionsCarpeta();            
            cu = new ConjuntUsuaris(valoracions);
            cr = new ConjuntRecomanacions(ci,cu,valoracions);
        }
        catch(ItemTypeNotValidException | ItemWeightNotCorrectException e) {
            //també un altre not valid file o algo així, es pot diferenciar de la resta passant un string a cada cas
            //o sigui mateixa excepcion però amb missatges d'error diferents
        }
        catch( ItemNotFoundException | UserNotFoundException | RatingNotValidException | UserIdNotValidException | ItemIdNotValidException e) {
            //throw new invalid file o algo d'aquest estil
        }
    }
    
    
    public void carregarRatings(String fitxer) throws FileNotValidException, FileNotFoundException {
        try {
            cu = new ConjuntUsuaris(cp.carregarFitxerExtern(fitxer));
            cr = new ConjuntRecomanacions(ci,cu,cp.carregarFitxerExtern(fitxer));
        }
        catch(Exception e) {
            //s'han de mirar les que pugen
        }
    }

    public void carregarItems(String fitxer) throws FileNotValidException, FileNotFoundException {
        try {
            ci = new ConjuntItems(cp.carregarFitxerExtern(fitxer));
        }
        catch(Exception e) {
            //s'han de mirar les que pugen
        }
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
        cda.seleccionar_algorisme(a);
    }

    public void setK(int kk) throws DataNotValidException {
        cda.set_k(kk);
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
        Item.setPes(a, w);
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

}
