package src.recomanador.domini;

import src.recomanador.persistencia.*;
import src.recomanador.excepcions.*;

import java.util.ArrayList;

public class ControladorDomini {

    /*----- STATICS -----*/
    
    /* Controllers */
    private static ControladorDomini inst;
    private static ControladorDominiAlgorisme cda;
    private static ControladorPersistencia cp;

    /* Data of the execution */
    private static ConjuntUsuaris cu;
    private static ConjuntRecomanacions cr;
    private static ConjuntItems ci;

    /* Atributes */
    /* Id of the user/actor of the application */
    private static int id;

    /* True if active user/actor has admin privileges */
    private static boolean admin;

    public static ControladorDomini getInstance() {
        if(ControladorDomini.inst == null) inst = new ControladorDomini();
        return inst;
    }

    /*----- CONSTANTS -----*/
    
    /* Value that represents null in the atribute id */
    public static final int NULL_ID = -1;


    /*----- CONSTRUCTORS -----*/

    private ControladorDomini() {
        cp = new ControladorPersistencia(); //S'haurà de canviar per getInst()
        cda = new ControladorDominiAlgorisme(); //S'haurà de canviar per getInst()

        cu = new ConjuntUsuaris();
        cr = new ConjuntRecomanacions();
        //conjunt items no es pot inicialitzar(com a molt amb valors tontos però no cal)

        id = NULL_ID;
        admin = false;
    }


    /*----- SESSIONS/PRIVILEGIS -----*/
    
    /**
     * User logs in a with an ID. If the id is not in the data already gathered, a new object Usuari is created.
     *
     * @param      id                       The identifier
     *
     * @throws     AlreadyLogedInException  Thrown if a current session is already open.
     */
    public void login(int id) throws AlreadyLogedInException {
        if(this.id != NULL_ID) throw new AlreadyLogedInException(this.id);
        if(!cu.existeixUsuari(id)) cu.add(new Usuari(id));

        this.id = id;
        admin = false;
    }

    /**
     * Logs in as an admin.
     *
     * @throws     AlreadyLogedInException  Thrown if a session is already opened.
     */
    public void loginAdmin() throws AlreadyLogedInException {
        if(this.id != NULL_ID) throw new AlreadyLogedInException(this.id);
       
        this.id = NULL_ID;
        admin = true;
    }

    /**
     * Logs out either if you loged in as a user or as an admin.
     */
    public void logout() {
        id = NULL_ID;
        admin = false;
    }

    /**
     * Gets the active user identifier.
     *
     * @return     The active user identifier.
     *
     * @throws     NotLogedInException  Thrown if no user session is opened.
     */
    public int getActiveUserId() throws NotLogedInException {
        if(this.id == NULL_ID) throw new NotLogedInException();

        return this.id;
    }

    /**
     * Determines if the current session has admin privilages.
     *
     * @return     True if admin, False otherwise.
     */
    public boolean isAdmin() {
        return this.admin;
    }


    /*----- DATA & FILES -----*/




    //COSES DADES(implica carregar les dades als conjunts)(n'hi ha molts, estan als casos d'ús)
    //CARREGAR CARPETA
    //CARREGAR FITXER
    //OBTENIR DADES
        //RATINGS
        //ITEMS
        //USERS
    //GUARDAR FITXERS
    

    /*----- DATA & FILES -----*/

    public void carregarCarpeta(String directory) throws FolderNotFoundException, FolderNotValidException, DataNotValidException {

        //Funció per enviar el nom de la carpeta al controlador de persistència i mirar que és valida
        cp.escollirProjecte(directory);
       
        try {
            ArrayList<ArrayList<String>> items = cp.carregarItemsCarpeta();
            try {
                ci = new ConjuntItems(items);
            } catch (ArrayIndexOutOfBoundsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ItemStaticValuesAlreadyInitializedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ItemStaticValuesNotInitializedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ItemNewAtributesNotValidException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

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

}


//COSES USUARI/ADMIN
    //LOGIN
    //LOGOUT
    //DEFINIR ADMIN
    //TREURE ADMIN
    //COMPROVAR SI ÉS ADMIN O SI TÉ PERMISOS O ALGO

//COSES DADES(implica carregar les dades als conjunts)(n'hi ha molts, estan als casos d'ús)
    //CARREGAR CARPETA
    //CARREGAR FITXER
    //OBTENIR DADES
        //RATINGS
        //ITEMS
        //USERS
        //KNOWN
        //UNKNOWN
    //GUARDAR FITXERS

//USUARIS
    //GET USER
    //ADD USER
    //REMOVE USER???

//ITEMS
    //GET ITEM
    //ADD ITEM
    //REMOVE ITEM???
    //GET INFO ITEM

    //GET PES
    //GET PESOS
    //EDITAR PESOS
    //

//RECOMANACIONS
    //GET RECOMANACIONS?
    //GET RECOMANACIONS DE USUARI ACTIU 
    //GET VALORACIONS
    //MODIFY VALORACIO
    //REMOVE RATING
    //GENERAR RECOMANACIONS
    //VALORAR RECOMANACIO


//CANVIAR CONSTANTS(gets i sets)
    //K
    //Q
    //ALGORISME
    //GET CAPÇALERA

//TESTS
    //Obtenir constant aquella amb unknow i known

