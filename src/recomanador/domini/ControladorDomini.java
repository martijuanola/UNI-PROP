package src.recomanador.domini;
import src.recomanador.persistencia.*;

public class ControladorDomini {
	
	/*----- CONSTANT -----*/
	public static final int NULL_ID = -1;
	
	/*----- ATRIBUTS -----*/
	
    ControladorPersistencia cp;
    ConjuntUsuaris cu;
    int id;
    
    /*----- CONSTRUCTORS -----*/
    public ControladorDomini() {
        cp = new ControladorPersistencia();
        cu = new ConjuntUsuaris();
        id = NULL_ID;
    }
    
    /*----- ALTRES -----*/
    
    //Pre: identificador és vàlida (i.e. identificador és diferent a NULL_ID)
    //Post: s'iguala l'id de la classe a identificador
    public void establir_id_activa(int identificador)
    {
		id = identificador;
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
