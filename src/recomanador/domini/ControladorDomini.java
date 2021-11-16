package src.recomanador.domini;

import src.recomanador.persistencia.*;
import src.recomanador.excepcions.*;

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

    public void prova2() {
        ConjuntItems.assignarNom("HEY NO SE QUE POSAR");
        System.out.println("Natributs: " + ConjuntItems.getNumAtributs());
        for (int i = 0; i < ci.size(); ++i) System.out.println("Item "+i+" tamany atributs:" + ci.get(i).getNumAtributs());
        for (int i = 0; i < ConjuntItems.getNumAtributs(); ++i) {
            System.out.println("Minim: " + ConjuntItems.getNomAtribut(i) + " " + ci.getMinMaxAtribut(i, false));
            System.out.println("Maxim: " + ConjuntItems.getNomAtribut(i) + " " + ci.getMinMaxAtribut(i, true));
        }
        //ci.printItems();
        //ci.printId();
    }


    /**
     * Calls the persistence controler to get all the data from a previous stored session
     * and initilizes all the necessari arrays of data(ConjuntItems, ConjuntUsuaris, ConjuntRecomanacions and others)   
     *
     * @param      directory  the directory where the files have been stored
     */
    public void carregarCarpeta(String directory) {

        //Funció per enviar el nom de la carpeta al controlador de persistència i mirar que és valida
        try {
            cp.escollirProjecte(directory);
        }
        catch (FolderNotFoundException ex) {
            System.out.println("not found");
            //passar-ho al driver per tornar a preguntar la carpeta
        }

        try {
            ArrayList<ArrayList<String>> items = cp.carregarItemsCarpeta();

            ci = new ConjuntItems(items);
            //TODO: Falta inicialitzar el nom del conjunt

        }
        catch (FolderNotValidException e1) {
            System.out.println("Folder not valid");
            //passar-ho al driver per tornar a preguntar la carpeta
        }
        catch (ItemTypeNotValidException e) {
            System.out.println("Item not valid");
            //l'atribut id no existeix o hi ha algun que no és int
        }

        try {
            ArrayList<ArrayList<String>> valoracions = cp.carregarRecomanacionsCarpeta();

            //crea ususaris buits
            cu = new ConjuntUsuaris(valoracions);

            //es creen les recomanacions/valoracions i s'afegeixen a usuaris 
            cr = new ConjuntRecomanacions(ci,cu,valoracions);
        }
        catch(FolderNotValidException e) {
            //processar error amb el driver
        }
        catch( ItemNotFoundException | UserNotFoundException | RatingNotValidException | UserIdNotValidException | ItemIdNotValidException e) {
            //throw new invalid file o algo d'aquest estil
        }
    }

}
