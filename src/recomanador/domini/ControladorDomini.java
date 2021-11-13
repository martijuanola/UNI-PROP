package src.recomanador.domini;

import src.recomanador.persistencia.*;

import java.util.ArrayList;

public class ControladorDomini {
	
    
	/*----- CONSTANTS -----*/
	public static final int NULL_ID = -1;
	

	/*----- ATRIBUTS -----*/
	
    ControladorPersistencia cp;

    ConjuntUsuaris cu;
    ConjuntRecomanacions cr;
    ConjuntItems ci;

    int id;
    

    /*----- CONSTRUCTORS -----*/

    public ControladorDomini() {
        cp = new ControladorPersistencia();

        cu = new ConjuntUsuaris();
        cr = new ConjuntRecomanacions();
        ci = new ConjuntItems();

        id = NULL_ID;
    }
    
    /*----- ALTRES -----*/
    
    //Pre: identificador és vàlida (i.e. identificador és diferent a NULL_ID)
    //Post: s'iguala l'id de la classe a identificador
    public void establir_id_activa(int identificador) {
		id = identificador;
	}
	
	//Pre: cert
	//Post: s'iguala la id a NULL_ID, indicant que ningú està com a usuari
	public void logout() {
		id = NULL_ID;
	}


    /**
     * Calls the persistence controler to get all the data from a previous stored session
     * and initilizes all the necessari arrays of data(ConjuntItems, ConjuntUsuaris, ConjuntRecomanacions and others)   
     *
     * @param      directory  the directory where the files have been stored
     */
    public void carregarCarpeta(String directory) {

        //Funció per enviar el nom de la carpeta al controlador de persistència
        cp.escollirProjecte(directory);


    //Jaume - Items
        //Funció per obtenir els items
        //CJ(items);


    //Martí - Users + Recomanacions
        ArrayList<ArrayList<String>> valoracions = cp.carregarRecomanacionsCarpeta();

        //crea ususaris buits
        cu = new ConjuntUsuaris(valoracions);

        //CV(valoracions); //només agafarà valoracions i les valoracions dels usuaris
    }


}
