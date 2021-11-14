package src.recomanador.domini;
import java.util.ArrayList;

import src.recomanador.persistencia.*;

public class ControladorDomini {
	
	/*----- CONSTANT -----*/
	public static final int NULL_ID = -1;
	
	/*----- ATRIBUTS -----*/
	
    ControladorPersistencia cp;
    ConjuntUsuaris cu;
    ConjuntItems ci;
    int id;
    
    /*----- CONSTRUCTORS -----*/
    public ControladorDomini() {
        cp = new ControladorPersistencia();
        cu = new ConjuntUsuaris();
        ci = new ConjuntItems();
        id = NULL_ID;
    }
    
    /*----- ALTRES -----*/
    
    //Pre: identificador és vàlida (i.e. identificador és diferent a NULL_ID)
    //Post: s'iguala l'id de la classe a identificador
    public void establir_id_activa(int identificador)
    {
		id = identificador;
	}
	
	//Pre: cert
	//Post: s'iguala la id a NULL_ID, indicant que ningú està com a usuari
	public void logout()
    {
		id = NULL_ID;
	}

    public void provaItems(ArrayList<ArrayList<String>> items) {
        System.out.println("Num items: " + (items.size()-1) + " Num atributs: " + items.get(0).size());
        ci = new ConjuntItems(items);
        ConjuntItems.assignarNom("HEY NO SE QUE POSAR");
        for (int i = 0; i < ConjuntItems.getNumAtributs(); ++i) {
            if (i == 5) ci.assignarTipus(i, ConjuntItems.tipus.I);
            else if (i == 7) ci.assignarTipus(i, ConjuntItems.tipus.N);
            else ci.assignarTipus(i, ConjuntItems.tipus.S);
            ConjuntItems.assignarPes(i, ((float)100.0));
        }
        ci.printItems();
        ci.printID();
    }


    //PSEUDOCODI !!!
   /* public void carregaCarpeta(String file) {

        //passar carpeta

        ArrayList<ArrayList<String>> items = cp.carregaItemsOld();

        CJ(items);

         ArrayList<ArrayList<String>> valoracions = cp.carregaRecomanacionsOld();

        CU(valoracions); //inicialitzarà

        CV(valoracions); //només agafarà valoracions
    }*/




}
