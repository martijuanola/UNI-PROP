package src.recomanador.domini;

import src.recomanador.persistencia.*;
import src.recomanador.excepcions.*;
import src.recomanador.domini.Item.tipus;
import src.recomanador.Utils.StringOperations;

import java.util.ArrayList;
import java.util.Collections;

public class ControladorDomini {

    /*----- STATICS -----*/
    
    /* Controllers */
    private static ControladorDomini inst;
    private static ControladorDominiAlgorisme cda;
    private static ControladorPersistencia cp;

    /* Data of the execution */
    private static ConjuntItems ci;
    private static ConjuntUsuaris cu;
    private static ConjuntRecomanacions cr;
    
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
        cp = ControladorPersistencia.getInstance();
        cda = new ControladorDominiAlgorisme(); //S'haurà de canviar per getInst()

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

    public void loadSession(String directory) throws FolderNotFoundException, FolderNotValidException, DataNotValidException{
        cp.escollirProjecte(directory);
       
        try {
            ci = new ConjuntItems(cp.carregarItemsCarpeta());
        }
        catch(Exception e) {
            throw new FolderNotValidException(e.getMessage());
        }

        ArrayList<ArrayList<String>> valoracions = cp.carregarRecomanacionsCarpeta();            
        try {
            cu = new ConjuntUsuaris(valoracions);
            cr = new ConjuntRecomanacions(ci,cu,valoracions);
        }
        catch(DataNotValidException e) {
            throw new FolderNotValidException("There is invalid data in ratings file", true);
        }
        catch(ItemNotFoundException | UserNotFoundException e) {
            throw new FolderNotValidException("There are missing Users or Items. Some files are not valid.", true);
        }

        ArrayList<String> estat = cp.carregarEstat();

        cda.seleccionar_algorisme(Integer.parseInt(estat.get(0)));


        //          0           | 1 | 2 |       3       |   4    |   5
        //algorisme_seleccionat | Q | K | nom_cjt_items | pos_id | pos_nom
    

    }

    public void loadNewSession(String projName, String itemsFile, String usersFile) throws FolderNotValidException, FileNotValidException, FileNotFoundException{
        cp.crearProjecte(projName);

        //Llegir fitxers
        try {
            ci = new ConjuntItems(cp.carregarFitxerExtern(itemsFile));
        }
        catch(Exception e) {
            throw new FolderNotValidException(e.getMessage());
        }

        ArrayList<ArrayList<String>> valoracions = cp.carregarFitxerExtern(usersFile);            
        try {
            cu = new ConjuntUsuaris(valoracions);
            cr = new ConjuntRecomanacions(ci,cu,valoracions);
        }
        catch(DataNotValidException e) {
            throw new FolderNotValidException("There is invalid data in ratings file", true);
        }
        catch(ItemNotFoundException | UserNotFoundException e) {
            throw new FolderNotValidException("There are missing Users or Items. Some files are not valid.", true);
        }

        //Primer Save
        saveSession();
    }

//crear nova buida

    public void saveSession() throws FolderNotValidException {
        //CONVERSIONS de coses d'items
        //pesos + tipus + max + min
        ArrayList<String> pesoss = new ArrayList<String>(0);
        ArrayList<String> ts = new ArrayList<String>(0);
        ArrayList<String> maxs = new ArrayList<String>(0);
        ArrayList<String> mins = new ArrayList<String>(0);

        ArrayList<Float> pesos = Item.getPesos();
        ArrayList<tipus> t = Item.getTipusArray();
        ArrayList<Float> max = ci.getMaxAtributs();
        ArrayList<Float> min = ci.getMinAtributs();

        for(int i = 0; i < pesos.size(); i++) {
            pesoss.add(String.valueOf(pesos.get(i)));
            ts.add(StringOperations.tipusToString(t.get(i)));
            maxs.add(String.valueOf(max.get(i)));
            mins.add(String.valueOf(min.get(i)));
        }

        //Valors
        ArrayList<String> vals = new ArrayList<String>(0);
        vals.add(String.valueOf(cda.get_algorisme()));
        vals.add(String.valueOf(cda.get_Q()));
        vals.add(String.valueOf(cda.get_k()));
        vals.add(String.valueOf(ConjuntItems.getNomCjItems()));
        vals.add(String.valueOf(String.valueOf(Item.getPosId())));
        vals.add(String.valueOf(String.valueOf(Item.getPosNomA())));
        
        //totes les funcions de guardar(s'han de mirar excepcions)
        cp.guardarRecomanacions(cr.getAllRecomanacions());
        cp.guardarItems(Item.getCapçalera(), ci.getAllItems());
        cp.guardarPesosAtributs(pesoss);
        cp.guardarTipusAtributs(ts);
        cp.guardarMaxAtributsItems(maxs);
        cp.guardarMinAtributsItems(mins);
        cp.guardarEstat(vals);
    }

    /*----- TESTS -----*/

    //K I ALGORISME SON ELS QUE JA S'HAN DEFINIT
    ArrayList<Integer> runTest() throws FolderNotValidException, DataNotValidException {
        int auxQ = cda.get_Q(); //per no perdre el valor
        ArrayList<ItemValoracioEstimada> items_recomanats = new ArrayList<ItemValoracioEstimada>();

        try {
            cda.set_Q(ci.size()/2);
        }
        catch (DataNotValidException e) {
            throw new DataNotValidException(ci.size()/2,"Q needs to be greater than 0(There are no items)");
        }

        ConjuntUsuaris usuarisUnknown = new ConjuntUsuaris();
        ConjuntRecomanacions recomanacionsUnknown = new ConjuntRecomanacions();
        ConjuntUsuaris usuaris = new ConjuntUsuaris();
        ConjuntRecomanacions recomanacions = new ConjuntRecomanacions();
        
        //UNKNOWN
        ArrayList<ArrayList<String>> raw = cp.carregarTestUnknown();
        usuarisUnknown.afegirDades(raw);
        try {
            recomanacionsUnknown.afegirDades(ci,usuarisUnknown,raw);
        }
        catch(DataNotValidException e) {
            throw new FolderNotValidException("There is invalid data in ratings file", true);
        }
        catch(ItemNotFoundException | UserNotFoundException e) {
            throw new FolderNotValidException("There are missing Users or Items. Some files are not valid.", true);
        }

        //KNOWN
        raw = cp.carregarTestKnown();
        usuaris.afegirDades(cr.getAllRecomanacions());
        usuaris.afegirDades(raw);

        try {
            recomanacions.afegirDades(ci,cu,cr.getAllRecomanacions());
            recomanacions.afegirDades(ci,usuaris,raw);
        }
        catch(DataNotValidException e) {
            throw new FolderNotValidException("There is invalid data in ratings file", true);
        }
        catch(ItemNotFoundException | UserNotFoundException e) {
            throw new FolderNotValidException("There are missing Users or Items. Some files are not valid.", true);
        }

        int DCG = 0;
        int IDCG = 0;

        for(int idx_unknown = 0; idx_unknown < usuarisUnknown.size(); ++idx_unknown) {
            int id_unknown = usuarisUnknown.get(idx_unknown).getId();
            ConjuntRecomanacions val_unknown_aux = recomanacionsUnknown.getValoracions(usuarisUnknown.get(idx_unknown).getId());

            //Per calcular el NDGC, ens cal ordenar les valoracions de l'usuari a Unknown per valoraciÃ³.
            //Per fer-ho, podem reutilitzar la classe itemValoracioEstimada
            ArrayList<ItemValoracioEstimada> val_unknown = new ArrayList<ItemValoracioEstimada>(0);
            for (int val_idx = 0; val_idx < val_unknown_aux.size(); ++val_idx) {
                val_unknown.add(new ItemValoracioEstimada
                (val_unknown_aux.get(val_idx).getVal(), val_unknown_aux.get(val_idx).getItem()));
            }
            Collections.sort(val_unknown);

            try {
                items_recomanats = cda.run_algorithm(id_unknown, ci, usuaris, recomanacions);
            }
            catch(UserNotFoundException e) {
                System.out.print("ERROR!! Algo va malament als testos" + e.getMessage());
                return null;
            }

            int DCG_user = 0;

            for (int val_idx = 0; val_idx < val_unknown.size(); ++val_idx) {
                Item item_unknown = val_unknown.get(val_idx).item;

                //System.out.println(val_unknown.get(val_idx).valoracioEstimada);

                int i = 0;
                while (i < items_recomanats.size() && items_recomanats.get(i).item != item_unknown) ++i;
                
                ++i; //the first index is 1. zero gives infinity and we wouldn't want to claim our algorithm is infinitely good.

                if (i < items_recomanats.size()) {
                    DCG_user += (int) Math.round((Math.pow(2, val_unknown.get(val_idx).valoracioEstimada) - 1)/(Math.log(i+1)/Math.log(2)));
                    IDCG += Math.round((Math.pow(2, val_unknown.get(val_idx).valoracioEstimada) - 1)/(Math.log(val_idx+1+1)/Math.log(2)));
                }                        
            }

            System.out.println("L'usuari " +id_unknown+ " contribueix " +DCG_user+ " al DCG.");
            System.out.println("("+(idx_unknown+1)+"/"+usuarisUnknown.size()+")");
            System.out.println();
            DCG += DCG_user;
        }

        System.out.println("DCG TOTAL: " + DCG);
        System.out.println("IDCG TOTAL: " + IDCG);
        System.out.println("NORMALIZED DCG : " + DCG/IDCG);

        cda.set_Q(auxQ); //reset de la Q anterior

        ArrayList<Integer> result = new ArrayList<Integer>(0);
        result.add(DCG);
        result.add((100*DCG)/IDCG);
        return result;
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

//TESTS
    //Obtenir constant aquella amb unknow i known

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



