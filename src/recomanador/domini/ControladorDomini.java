package src.recomanador.domini;

import src.recomanador.persistencia.*;
import src.recomanador.excepcions.*;
import src.recomanador.domini.Item.tipus;
import src.recomanador.Utils.StringOperations;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Controller of the 
 * @author Martí J.
 */
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

    /**
     * Returs the only instance of the class, and if it's not created, it creates it.
     *
     * @return     The instance.
     */
    public static ControladorDomini getInstance() {
        if(ControladorDomini.inst == null) inst = new ControladorDomini();
        return inst;
    }


    /*----- CONSTANTS -----*/
    
    /* Value that represents null in the atribute id */
    public static final int NULL_ID = -1;


    /*----- CONSTRUCTORS -----*/

    /**
     * Constructs a new instance. The algorithm subcontroller and the Persistence layer controller are also initialized.
     */
    private ControladorDomini() {
        cp = ControladorPersistencia.getInstance();
        cda = ControladorDominiAlgorisme.getInstance(); //S'haurà de canviar per getInst()

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
    public int getActiveUserId() throws PrivilegesException {
        if(this.id == NULL_ID) throw new PrivilegesException("Needs to be loged in as a USER.");

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

    /**
     * Loads a session/project. The Recomanacions, Usuaris and Items set are initialized, as well as all the other extra informations stored.
     *
     * @param      directory                The directory where the files are stored
     *
     * @throws     DataNotValidException    There's some data not correct in some file.
     * @throws     FolderNotFoundException  Thrown when de directory is not found in the folder system.
     * @throws     FolderNotValidException  Thrown when the folder contains faulty files or has some missing.
     */
    public void loadSession(String directory) throws FolderNotFoundException, FolderNotValidException, DataNotValidException {
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
            throw new FolderNotValidException("There is invalid data in ratings file." + e.getMessage(), true);
        }
        catch(ItemNotFoundException | UserNotFoundException e) {
            throw new FolderNotValidException("There are missing Users or Items. Some files are not valid.", true);
        }

        ArrayList<String> estat = cp.carregarEstat();

        cda.seleccionar_algorisme(Integer.parseInt(estat.get(0)));
        cda.set_Q(Integer.parseInt(estat.get(1)));
        cda.set_k(Integer.parseInt(estat.get(2)));

        ci.setNomCjItems(estat.get(3));

        try{
            Item.setId(Integer.parseInt(estat.get(4)));
            Item.setNomA(Integer.parseInt(estat.get(5)));
        }
        catch(ItemIdNotValidException e) {
            throw new DataNotValidException(estat.get(4),"El valor guardat de POS ITEM ID no és correcte!");
        }
        catch(ItemStaticValuesAlreadyInitializedException e) {
            System.out.print("ERROR INTERN!! S'havien d'haver resetejat els valors estàtics d'items abans de tornar-los a inicialitzar." + e.getMessage());
            return;
        }
    }

    /**
     * Creates a new session/project from a items file and a ratings file. All the other constants are either calculated from the input data
     * or set with default values.
     *
     * @param      projName                 The project name
     * @param      itemsFile                The items file
     * @param      usersFile                The users file
     *
     * @throws     FileNotFoundException    Thrown if either the items or users file was not found.
     * @throws     FileNotValidException    Thrown if the file has missing information or has not the right structure.
     * @throws     FolderNotValidException  Thrown if the current folder has missing files.
     */
    public void createSession(String projName, String itemsFile, String usersFile) throws FolderNotValidException, FileNotValidException, FileNotFoundException{
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
            throw new FolderNotValidException("There is invalid data in ratings file" + e.getMessage(), true);
        }
        catch(ItemNotFoundException | UserNotFoundException e) {
            throw new FolderNotValidException("There are missing Users or Items. Some files are not valid.", true);
        }

        //Crear Projecte
        cp.crearProjecte(projName);

        //Primer Save
        saveSession();
    }

    /**
     * Creates a new session without any initial data input.
     *
     * @param      projName                   The project name
     * @param      nomsAtriubts               The item atribute names
     * @param      tipusAtriubtsS             The item atribute types
     *
     * @throws     FolderNotValidException    Thrown if the current folder has missing files.
     * @throws     ItemTypeNotValidException  Thrown if there are problems with the atirubute type assignation.
     */
    public void createEmptySession(String projName, ArrayList<String> nomsAtriubts, ArrayList<String> tipusAtriubtsS) throws FolderNotValidException, ItemTypeNotValidException {
        cp.crearProjecte(projName);

        ArrayList<tipus> tipusAtributs = new ArrayList<tipus>(0);
        for(int i = 0; i < tipusAtriubtsS.size(); i++) tipusAtributs.add(StringOperations.stringToType(tipusAtriubtsS.get(i)));

        try {
            Item.setNomAtributs(nomsAtriubts);
            Item.setTipusArray(tipusAtributs);
        }
        catch(ItemStaticValuesAlreadyInitializedException e) {
            System.out.print("ERROR INTERN!! S'havien d'haver resetejat els valors estàtics d'items abans de tornar-los a inicialitzar." + e.getMessage());
            return;
        }

        //Primer Save
        saveSession();
    }

    /**
     * Saves all the information of the current session/project.
     *
     * @throws     FolderNotValidException  Thrown if there are problems with the project directory.
     */
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

    /**
     * Function designed to obtain the DCG and NDCG values for avaluating the performance of the algorithm.
     * The Unknown and Known test files are used as well as the basic items and rating files. The test is run
     * with the K and algorithm values that are assigned at the moment.
     *
     * @return     The DCG and NDCG(has a percentage) in this order in a ArrayList
     *
     * @throws     DataNotValidException    Thrown in a very specific case where the Q value can't be assignet due to a lack of user.
     * @throws     FolderNotValidException  Thrown if there are problems when getting the information from the Unknown and Known files.
     */
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
                System.out.print("ERROR INTERN!! Algo va malament als testos" + e.getMessage());
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


    /*----- ITEMS -----*/

    /**
     * Returns all the items in the current set of items(without the header).
     * 
     * @return All the atributes of all the items structured with ArrayLists.
     */
    public ArrayList<ArrayList<ArrayList<String>>> getAllItems() {
        ArrayList<ArrayList<ArrayList<String>>> aux = ci.getAllItems();
        aux.remove(0);
        return aux;
    }

    /**
     * Gets the item with a certain id.
     *
     * @param      id                     The identifier
     *
     * @return     The item with an standard form
     *
     * @throws     ItemNotFoundException  Thrown when de id is not an item id.
     */
    public ArrayList<ArrayList<String>> getItem(int id) throws ItemNotFoundException {
        return ci.getItem(id).getAtributs();
    }

    /**
     * Gets the value of the id position in the atributes of an item.
     *
     * @return     The value
     */
    public String getPosIdItem() {
        return String.valueOf(Item.getPosId());
    }

    /**
     * Gets the value of the name position in the atributes of an item.
     *
     * @return     The value
     */
    public String getPosNomItem() {
        return String.valueOf(Item.getPosId());
    }

    /**
     * Gets the header of the items.
     *
     * @return     The header as an array of Strings, one for each atribute.
     */
    public ArrayList<String> getHeader() {
        return Item.getCapçalera();
    }

    /**
     * Sets the value of the name position in the atributes of an item.
     *
     * @param      pos                                          The new value
     *
     * @throws     ItemStaticValuesAlreadyInitializedException  Thrown if the value was previouslly assigned.
     */
    public void setPosNomItem(String pos) throws ItemStaticValuesAlreadyInitializedException {
        Item.setNomA(Integer.parseInt(pos));
    }


    /**
     * Adds an item.
     *
     * @param      atributs                                 The atributes of the new item
     *
     * @throws     ItemNewAtributesNotValidException        Some atribute is not valid.
     * @throws     ItemStaticValuesNotInitializedException  The statics values of Item were not initialized.
     * @throws     PrivilegesException                      You need to be an admin to perform this functionallity.
     */
    public void addItem(ArrayList<ArrayList<String>> atributs) throws PrivilegesException, ItemStaticValuesNotInitializedException, ItemNewAtributesNotValidException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        ci.add(new Item(atributs));
    }

    /**
     * Removes an item and all its recommendations.
     *
     * @param      id                                       The identifier
     *
     * @throws     ItemNotFoundException                    The id is not an item id.
     * @throws     ItemStaticValuesNotInitializedException  The statics values of Item were not initialized.
     * @throws     PrivilegesException                      You need to be an admin to perform this functionallity.
     */
    public void removeItem(String id) throws PrivilegesException, ItemNotFoundException, ItemStaticValuesNotInitializedException{
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        cr.removeRecomanacionsItem(Integer.parseInt(id));
        ci.eliminarItem(Integer.parseInt(id));
    }

    /**
     * Modifies an item.
     *
     * @param      atributs                                 The new values of the item
     *
     * @throws     ItemNewAtributesNotValidException        Some atribute is not valid.
     * @throws     ItemNotFoundException                    The id is not an item id.
     * @throws     ItemStaticValuesNotInitializedException  The statics values of Item were not initialized.
     * @throws     PrivilegesException                      You need to be an admin to perform this functionallity.
     */
    public void editItem(ArrayList<ArrayList<String>> atributs) throws PrivilegesException, ItemNotFoundException, ItemStaticValuesNotInitializedException, ItemNewAtributesNotValidException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        ci.eliminarItem(Integer.parseInt(atributs.get(Item.getPosId()).get(0)));
        ci.add(new Item(atributs));
    }


    /**
     * Gets the weights of each atribute for the comutations of the recommendations.
     *
     * @return     The weights.
     *
     * @throws     PrivilegesException  You need to be an admin to perform this functionallity.
     */
    public ArrayList<String> getPesos() throws PrivilegesException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        ArrayList<Float> pesos = Item.getPesos();
        ArrayList<String> aux = new ArrayList<String>(0);
        for(int i = 0; i < pesos.size(); i++) aux.add(String.valueOf(pesos.get(i)));
        return aux;
    }

    /**
     * Sets the weights of each atribute for the comutations of the recommendations.
     *
     * @param      pesosS                         The new weights as strings.
     *
     * @throws     ItemWeightNotCorrectException  Some weight has not the right type.
     * @throws     PrivilegesException            You need to be an admin to perform this functionallity.
     */
    public void setPesos(ArrayList<String> pesosS) throws PrivilegesException, ItemWeightNotCorrectException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        ArrayList<Float> pesos = new ArrayList<Float>(0);
        for(int i = 0; i < pesosS.size(); i++) pesos.add(Float.parseFloat(pesosS.get(i)));
        Item.setPesos(pesos);
    }

    /**
     * Gets all the atribute types of the items.
     *
     * @return     The array of types as strings
     *
     * @throws     PrivilegesException  You need to be an admin to perform this functionallity.
     */
    public ArrayList<String> getTipus() {
        ArrayList<tipus> tipus = Item.getTipusArray();
        ArrayList<String> aux = new ArrayList<String>(0);
        for(int i = 0; i < tipus.size(); i++) aux.add(StringOperations.tipusToString(tipus.get(i)));
        return aux;
    }

    /**
     * Sets all the atribute types of the items.
     *
     * @param      tipusS                                       The new types of data for the atributes
     *
     * @throws     ItemTypeNotValidException                    Some type assignation is not possible.
     * @throws     PrivilegesException                          You need to be an admin to perform this functionallity.
     * @throws     ItemStaticValuesAlreadyInitializedException  
     */
    public void setTipus(ArrayList<String> tipusS) throws PrivilegesException, ItemStaticValuesAlreadyInitializedException, ItemTypeNotValidException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        ArrayList<tipus> tipus = new ArrayList<tipus>(0);
        for(int i = 0; i < tipusS.size(); i++) tipus.add(StringOperations.stringToType(tipusS.get(i)));
        Item.setTipusArray(tipus);
    }


    /*----- RECOMANACIONS -----*/
    
    /**
     * Gets all the not rated recommendations of a user.
     *
     * @param      idS                  The identifier as a String.
     *
     * @return     All the not rated recommendations of the user
     *
     * @throws     PrivilegesException  You need to be loged in as a user to perform this functionallity.
     */
    public ArrayList<ArrayList<String>> getAllRecomanacionsUsuari(String idS) throws PrivilegesException {
        int idR = Integer.parseInt(idS);
        if(!admin && idR != this.id) throw new PrivilegesException("A USER can only work with its Recommendations.");
        ArrayList<ArrayList<String>> aux = cr.getRecomanacionsNoValorades(idR).getAllRecomanacions();
        aux.remove(0);
        return aux;
    }

    /**
     * Gets all the rated recommendations of a user.
     *
     * @param      idS                  The identifier as a String.
     *
     * @return     All the rated recommendations of the user
     *
     * @throws     PrivilegesException  You need to be loged in as a user to perform this functionallity.
     */
    public ArrayList<ArrayList<String>> getAllValoracionsUsuari(String idS) throws PrivilegesException {
        int idR = Integer.parseInt(idS);
        if(!admin && idR != this.id) throw new PrivilegesException("A USER can only work with its Recommendations.");
        ArrayList<ArrayList<String>> aux = cr.getValoracions(idR).getAllRecomanacions();
        aux.remove(0);
        return aux;
    }

    /**
     * Sets a rating for a recommendation.
     *
     * @param      itemId                           The item identifier of the recommendation
     * @param      usuariId                         The usuari identifier of the recommendation
     * @param      rating                           The new rating
     *
     * @throws     PrivilegesException              If the user is loged in as a user, it can only modify his recommendations.
     * @throws     RatingNotValidException          The rating is not valid.
     * @throws     RecommendationNotFoundException  The item and user has no relation as a recommendation.
     */
    public void setValoracio(String itemId, String usuariId, String rating) throws PrivilegesException, RecommendationNotFoundException, RatingNotValidException {
        if(!admin && Integer.parseInt(usuariId) != this.id) throw new PrivilegesException("A USER can only work with its Recommendations.");
        cr.getRecomanacio(Integer.parseInt(itemId),Integer.parseInt(usuariId)).setVal(Float.parseFloat(rating));
    }

    /**
     * Removes a rating for a recommendation.
     *
     * @param      itemId                           The item identifier of the recommendation
     * @param      usuariId                         The usuari identifier of the recommendation
     *
     * @throws     PrivilegesException              If the user is loged in as a user, it can only modify his recommendations.
     * @throws     RatingNotValidException          The rating is not valid.
     * @throws     RecommendationNotFoundException  The item and user has no relation as a recommendation.
     */
    public void removeValoracio(String itemId, String usuariId) throws PrivilegesException, RecommendationNotFoundException, RatingNotValidException {
        if(!admin && Integer.parseInt(usuariId) != this.id) throw new PrivilegesException("A USER can only work with its Recommendations.");
        cr.getRecomanacio(Integer.parseInt(itemId),Integer.parseInt(usuariId)).setVal(Recomanacio.nul);
    }

    /**
     * Main functionality of this project. It generates recommendations for the current user from all the other information.
     * This calculations are performed in the algorithm controller.
     *
     * @throws     PrivilegesException  You need to be loged in as a user to perform this functionallity.
     */
    public void createRecomanacions() throws PrivilegesException {
        if(admin) throw new PrivilegesException("Needs to be loged in as a USER.");
        try{
            ArrayList<ItemValoracioEstimada> newRecomanacions = cda.run_algorithm(id, ci, cu, cr);
            for(int i = 0; i < newRecomanacions.size(); i++) cr.add(new Recomanacio(cu.getUsuari(id),newRecomanacions.get(i).item));
        }
        catch(UserNotFoundException e) {
            System.out.print("ERROR INTERN!! Problema amb l'usuari actiu. No es troba en algun conjunt." + e.getMessage());
            return;
        }
    }


    /*----- CANVI CONSTANTS ALGORISME -----*/

    /**
     * Gets the value K used in the algorithm to perform user clustering.
     *
     * @return     The value K
     *
     * @throws     PrivilegesException  You need to be an admin to perform this functionallity.
     */
    public String getK() throws PrivilegesException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        return String.valueOf(cda.get_k());
    }

    /**
     * Gets the value Q that specifies how many recomendations have to be return everytime getRecomenacions() is performed.
     *
     * @return     The value Q
     *
     * @throws     PrivilegesException  You need to be an admin to perform this functionallity.
     */
    public String getQ() throws PrivilegesException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        return String.valueOf(cda.get_Q());
    }

    /**
     * Gets the value that specifies which algorithm to run. These are the following options:
     * 0 - collaborative filtering(Kmeans + Slope1)
     * 1 - content-based filtering(KNN)
     * 2 - hybrid approaches
     *
     * @return     The value
     *
     * @throws     PrivilegesException  You need to be an admin to perform this functionallity.
     */
    public String getAlgorisme() throws PrivilegesException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        return String.valueOf(cda.get_algorisme());
    }

     /**
     * Sets the value K used in the algorithm to perform user clustering.
     *
     * @param     k                       The new value as a string
     *
     * @throws     PrivilegesException  You need to be an admin to perform this functionallity.
     */
    public void setK(String k) throws PrivilegesException, DataNotValidException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        cda.set_k(Integer.parseInt(k));
    }

    /**
     * Sets the value Q that specifies how many recomendations have to be return everytime getRecomenacions() is performed.
     *
     * @param      q                      The new value as a string
     *
     * @throws     DataNotValidException  The value is not correct.
     * @throws     PrivilegesException    You need to be an admin to perform this functionallity.
     */
    public void setQ(String q) throws PrivilegesException, DataNotValidException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        cda.set_Q(Integer.parseInt(q));
    }

    /**
     * Sets the value that specifies which algorithm to run. These are the following options:
     * 0 - collaborative filtering(Kmeans + Slope1)
     * 1 - content-based filtering(KNN)
     * 2 - hybrid approaches
     *
     * @param      a                      The new value as a string
     *
     * @throws     DataNotValidException  The value is not correct.
     * @throws     PrivilegesException    You need to be an admin to perform this functionallity.
     */
    public void setAlgorisme(String a) throws PrivilegesException, DataNotValidException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        cda.seleccionar_algorisme(Integer.parseInt(a));
    }

    /*----- USUARIS -----*/

    /**
     * Gets all the users in the current set of users.
     *
     * @return     All the users as an array of strings(has the only value of the class is the id)
     *
     * @throws     PrivilegesException  You need to be an admin to perform this functionallity.
     */
    public ArrayList<String> getAllUsuaris() throws PrivilegesException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        ArrayList<String> r = new ArrayList<String>(0);
        for(int i = 0; i < cu.size(); i++) r.add(String.valueOf(cu.get(i).getId()));
        return r;
    }

    /**
     * Adds a user.
     *
     * @param      id                       The identifier of the new user
     *
     * @throws     PrivilegesException      You need to be an admin to perform this functionallity.
     * @throws     UserIdNotValidException  The id is not a valid id(it may already be being used)
     */
    public void addUsuari(String id) throws PrivilegesException, UserIdNotValidException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        if(!cu.add(new Usuari(Integer.parseInt(id)))) throw new UserIdNotValidException(Integer.parseInt(id));
    }

    /**
     * Removes a user and all its recommendations.
     *
     * @param      id                     The identifier
     *
     * @throws     PrivilegesException    You need to be an admin to perform this functionallity.
     * @throws     UserNotFoundException  The id is not a valid user id.
     */
    public void removeUsuari(String id) throws PrivilegesException, UserNotFoundException {
        if(!admin) throw new PrivilegesException("Needs to be ADMIN.");
        cr.removeRecomanacionsUsuari(Integer.parseInt(id));
        cu.removeUsuari(Integer.parseInt(id));
    }


    /*----- ALTRES -----*/
    
    /**
     * Gets the different types of atribut using the enumeration Item.tipus
     *
     * @return     The array with the different types as strings.
     */
    public ArrayList<String> getTipusAsStrings() {
        ArrayList<String> r = new ArrayList<String>(0);
        for(tipus t: tipus.values()) r.add(StringOperations.tipusToString(t));
        return r;
    }

    /**
     * Gets the name of the current project.
     *
     * @return     The name
     *
     * @throws     FolderNotValidException  There's a problem with the current folder.
     */
    public String getNomProjecte() throws FolderNotValidException{
        return cp.getNomProjecte();
    }

    /**
     * Gets all the different projects found in the current directory.
     *
     * @return     An array of all the projects
     */
    public ArrayList<String> getAllProjectes() {
        return cp.llistatCarpetes();
    }

}
