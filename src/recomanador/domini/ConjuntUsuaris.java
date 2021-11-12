package src.recomanador.domini;

import java.util.ArrayList;

/**
 * This class describes the extension of ArrayList<Ususaris> with extra methods.
 */
public class ConjuntUsuaris extends ArrayList<Usuari>{
    
    //opció de guardar-ho per id ascendent per fer binarySearch()
    //dependrà de que s'ha de fer a l'algorisme, de moment ho faig sense ordre

    /*----- CONSTANTS -----*/
    
    /*----- ATRIBUTS -----*/


    /*----- CONSTRUCTORS -----*/


    public ConjuntUsuaris() {} //nose si cal

    public ConjuntUsuaris(ArrayList<ArrayList<String>> raw) {
        
        
    }

    /*----- CONSULTORES -----*/

    public Usuari getUsuari(int id) {}



    public boolean existeixUsuari() {} // nose si cal??

    /*----- MODIFICADORES -----*/

    private void addUsuari(Usuari u) {} // per si s'ha d'afegir de manera ordenada
}
