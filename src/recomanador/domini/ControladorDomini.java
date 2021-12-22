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

    /** Contains the only instance of the class **/
    private static ControladorDomini inst;
    /** Instance of the Algorithm Subcontroler **/
    private ControladorDominiAlgorisme cda;
    /** Instance of the Persistence Controler **/
    private ControladorPersistencia cp;

    /* Data of the execution */
    /** Set of Items of the execution **/
    private ConjuntItems ci;
    /** Set of Users of the execution **/
    private ConjuntUsuaris cu;
    /** Set of Recommendations of the execution **/
    private ConjuntRecomanacions cr;

    /** Indicates if the data for the algorithm needs to be reset **/
    private boolean dadesModificades;
    
    /* Atributes */
    /** Id of the user/actor of the application **/
    private int id;
    /** True if active user/actor has admin privileges **/
    private boolean admin;

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
    
    /** Value that represents null in the atribute id **/
    public final int NULL_ID = -1;


    /*----- CONSTRUCTORS -----*/

    /**
     * Constructs a new instance. The algorithm subcontroller and the Persistence layer controller are also initialized.
     */
    private ControladorDomini() {
        cp = ControladorPersistencia.getInstance();
        cda = ControladorDominiAlgorisme.getInstance();

        id = NULL_ID;
        admin = false;
        dadesModificades = true;
        resetData();
    }


    /*----- SESSIONS/PRIVILEGIS -----*/
    
    //no pots passar de usuari a admin

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

        dadesModificades = true;

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
        resetData();
    }

    /**
     * Gets the active user identifier.
     *
     * @return     The active user identifier.
     * 
     * @throws     PrivilegesException When the user is not loged in as a user.
     */
    public String getActiveUserId() throws PrivilegesException {
        if(this.id == NULL_ID) throw new PrivilegesException("Has d'haver entrat com un usuari.");

        return String.valueOf(this.id);
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
     * Fist, all the previous data is erased.
     *
     * @param      directory                The directory where the files are stored
     *
     * @throws     DataNotValidException    There's some data not correct in some file.
     * @throws     FolderNotFoundException  Thrown when de directory is not found in the folder system.
     * @throws     FolderNotValidException  Thrown when the folder contains faulty files or has some missing.
     */
    public void loadSession(String directory) throws FolderNotFoundException, FolderNotValidException, DataNotValidException {
        resetData();
        cp.escollirProjecte(directory);
       
        ArrayList<String> estat = cp.carregarEstat();

        //dades algorisme
        cda.seleccionar_algorisme(Integer.parseInt(estat.get(0)));
        cda.set_Q(Integer.parseInt(estat.get(1)));
        cda.set_k(Integer.parseInt(estat.get(2)));

        ArrayList<String> pS = cp.carregarPesosAtributs();
        ArrayList<Float> pF = new ArrayList<Float>();
        ArrayList<String> tS = cp.carregarTipusAtributs();
        ArrayList<tipus> tT = new ArrayList<tipus>();
        ArrayList<String> maxS = cp.carregarMaxAtributsItems();
        ArrayList<Float> maxF = new ArrayList<Float>();
        ArrayList<String> minS = cp.carregarMinAtributsItems();
        ArrayList<Float> minF = new ArrayList<Float>();

        try {
            for(int i = 0; i < pS.size(); i++) {
                pF.add(Float.parseFloat(pS.get(i)));
                tT.add(StringOperations.stringToType(tS.get(i)));
                maxF.add(Float.parseFloat(maxS.get(i)));
                minF.add(Float.parseFloat(minS.get(i)));
            }
        }
        catch(ItemTypeNotValidException e) { throw new FolderNotValidException("No es poden llegir bé els tipus d'atributs guardats.",true); }

        try {
            ci = new ConjuntItems(cp.carregarItemsCarpeta(), pF, tT,
                Integer.parseInt(estat.get(4)), Integer.parseInt(estat.get(5)), estat.get(3), maxF, minF);
        }
        catch(Exception e) {
            throw new FolderNotValidException(e.getMessage(),true);
        }

        ArrayList<ArrayList<String>> valoracions = cp.carregarRecomanacionsCarpeta();            
        try {
            cu = new ConjuntUsuaris(valoracions);
            cr = new ConjuntRecomanacions(ci,cu,valoracions);
        }
        catch(DataNotValidException e) {
            throw new FolderNotValidException("Hi ha dades no vàlides al fitxer de ratings." + e.getMessage(), true);
        }
        catch(ItemNotFoundException | UserNotFoundException e) {
            throw new FolderNotValidException("Hi ha algun Item o Usuari no trobat. Alguns fitxers no son vàlids.", true);
        }

        dadesModificades = true;
    }

    /**
     * Creates a new session/project from a items file and a ratings file. All the other constants are either calculated from the input data
     * or set with default values.
     * Fist, all the previous data is erased.
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
        resetData();

        //Llegir fitxers
        try {
            ci = new ConjuntItems(cp.carregarFitxerExtern(itemsFile));
        }
        catch(Exception e) {
            throw new FolderNotValidException(e.getMessage(),true);
        }

        ArrayList<ArrayList<String>> valoracions = cp.carregarFitxerExtern(usersFile);            
        try {
            cu = new ConjuntUsuaris(valoracions);
            cr = new ConjuntRecomanacions(ci,cu,valoracions);
        }
        catch(DataNotValidException e) {
            throw new FolderNotValidException("Hi ha dades no vàlides al fitxer de ratings." + e.getMessage(), true);
        }
        catch(ItemNotFoundException | UserNotFoundException e) {
            throw new FolderNotValidException("Hi ha algun Item o Usuari no trobat. Alguns fitxers no son vàlids.", true);
        }

        //Crear Projecte
        cp.crearProjecte(projName);

        //Primer Save
        saveSession();

        dadesModificades = true;
    }

    /**
     * Creates a new session without any initial data input.
     * Fist, all the previous data is erased.
     *
     * @param      projName                   The project name
     * @param      nomsAtriubts               The item atribute names
     * @param      tipusAtriubtsS             The item atribute types
     *
     * @throws     DataNotValidException        Problems with the initialization of the static values of Item
     * @throws     FolderNotValidException      Thrown if the current folder has missing files.
     * @throws     ItemTypeNotValidException    Thrown if there are problems with the atirubute type assignation.
     * @throws     PrivilegesException          You need to be an admin to perform this functionallity.
     */
    public void createEmptySession(String projName, ArrayList<String> nomsAtriubts, ArrayList<String> tipusAtriubtsS) throws PrivilegesException, FolderNotValidException, ItemTypeNotValidException, DataNotValidException {
        if(!admin) throw new PrivilegesException("Has de ser administrador.");       
        resetData();

        cp.crearProjecte(projName);

        ArrayList<tipus> tipusAtributs = new ArrayList<tipus>();
        for(int i = 0; i < tipusAtriubtsS.size(); i++) tipusAtributs.add(StringOperations.stringToType(tipusAtriubtsS.get(i)));
		
        ArrayList<Float> p = new ArrayList<Float>();
		for (int i = 0; i < nomsAtriubts.size(); ++i) p.add((float)100.0);

        try {
            Item.setNomAtributs(nomsAtriubts);
            Item.setTipusArray(tipusAtributs);
            Item.setId(0); //Sempre està en aquesta posició quan es crea
            Item.setNomA(1); //Sempre està en aquesta posició quan es crea
            Item.setPesos(p);
            ci = new ConjuntItems();
        }
        catch(ItemStaticValuesAlreadyInitializedException | ItemStaticValuesNotInitializedException | NumberFormatException | ItemIdNotValidException | ItemWeightNotCorrectException e) {
            System.out.print("ERROR INTERN!! S'havien d'haver resetejat els valors estàtics d'items abans de tornar-los a inicialitzar." + e.getMessage());
            return;
        }

        ConjuntItems.setNomCjItems(projName);
        cr = new ConjuntRecomanacions();
        cu = new ConjuntUsuaris();

        //Primer Save
        saveSession();

        dadesModificades = true;
    }

    /**
     * Saves all the information of the current session/project.
     *
     * @throws     FolderNotValidException  Thrown if there are problems with the project directory.
     */
    public void saveSession() throws FolderNotValidException {
        //CONVERSIONS de coses d'items
        //pesos + tipus + max + min
        ArrayList<String> pesoss = new ArrayList<String>();
        ArrayList<String> ts = new ArrayList<String>();
        ArrayList<String> maxs = new ArrayList<String>();
        ArrayList<String> mins = new ArrayList<String>();

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
        ArrayList<String> vals = new ArrayList<String>();
        vals.add(String.valueOf(cda.get_algorisme()));
        vals.add(String.valueOf(cda.get_Q()));
        vals.add(String.valueOf(cda.get_k()));
        vals.add(String.valueOf(ConjuntItems.getNomCjItems()));
        vals.add(String.valueOf(String.valueOf(Item.getPosId())));
        vals.add(String.valueOf(String.valueOf(Item.getPosNomA())));
        
        //totes les funcions de guardar(s'han de mirar excepcions)
        cp.guardarRecomanacions(cr.getAllRecomanacions());
        cp.guardarItems(Item.getHeader(), ci.getAllItems());
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
    public ArrayList<String> runTest() throws FolderNotValidException, DataNotValidException {
        int auxQ = cda.get_Q(); //per no perdre el valor
        ArrayList<ItemValoracioEstimada> items_recomanats = new ArrayList<ItemValoracioEstimada>();

        try {
            cda.set_Q(ci.size()/2);
        }
        catch (DataNotValidException e) {
            throw new DataNotValidException(ci.size()/2,"Q ha de ser superior a 0(No hi ha Items)");
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
            throw new FolderNotValidException("Hi ha dades no vàlides al fitxer de ratings.", true);
        }
        catch(ItemNotFoundException | UserNotFoundException e) {
            throw new FolderNotValidException("Hi ha algun Item o Usuari no trobat. Alguns fitxers no son vàlids.", true);
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
            throw new FolderNotValidException("Hi ha dades no vàlides al fitxer de ratings.", true);
        }
        catch(ItemNotFoundException | UserNotFoundException e) {
            throw new FolderNotValidException("Hi ha algun Item o Usuari no trobat. Alguns fitxers no son vàlids.", true);
        }

        int DCG = 0;
        int IDCG = 0;

        //passar dades a algorisme
        cda.setData(ci, usuaris, recomanacions);

        for(int idx_unknown = 0; idx_unknown < usuarisUnknown.size(); ++idx_unknown) {
            int id_unknown = usuarisUnknown.get(idx_unknown).getId();
            ConjuntRecomanacions val_unknown_aux = recomanacionsUnknown.getValoracions(usuarisUnknown.get(idx_unknown).getId());

            //Per calcular el NDGC, ens cal ordenar les valoracions de l'usuari a Unknown per valoraciÃ³.
            //Per fer-ho, podem reutilitzar la classe itemValoracioEstimada
            ArrayList<ItemValoracioEstimada> val_unknown = new ArrayList<ItemValoracioEstimada>();
            for (int val_idx = 0; val_idx < val_unknown_aux.size(); ++val_idx) {
                val_unknown.add(new ItemValoracioEstimada
                (val_unknown_aux.get(val_idx).getVal(), val_unknown_aux.get(val_idx).getItem()));
            }
            Collections.sort(val_unknown);

            try {
                items_recomanats = cda.run_algorithm(id_unknown);
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

        dadesModificades = true;

        System.out.println("DCG TOTAL: " + DCG);
        System.out.println("IDCG TOTAL: " + IDCG);
        System.out.println("NORMALIZED DCG : " + DCG/IDCG);

        cda.set_Q(auxQ); //reset de la Q anterior

        ArrayList<String> result = new ArrayList<String>();
        result.add(String.valueOf(DCG));
        result.add(String.valueOf((100*DCG)/IDCG));
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
        return String.valueOf(Item.getPosNomA());
    }

    /**
     * Gets the header of the items.
     *
     * @return     The header as an array of Strings, one for each atribute.
     */
    public ArrayList<String> getHeader() {
        return Item.getHeader();
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
     * @throws     ItemIdNotValidException                  There is already an item with the same id
     */
    public void addItem(ArrayList<ArrayList<String>> atributs) throws PrivilegesException, ItemStaticValuesNotInitializedException, ItemNewAtributesNotValidException, ItemIdNotValidException {
        if(!admin) throw new PrivilegesException("Ha de ser administrador.");
        if (ci.existeixItem(Integer.parseInt(atributs.get(Item.getPosId()).get(0)))) {
            throw new ItemIdNotValidException(atributs.get(Item.getPosId()).get(0) + " (id repetit) ");
        }
        ci.add(new Item(atributs));
        dadesModificades = true;
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
        if(!admin) throw new PrivilegesException("Ha de ser administrador.");
        cr.removeRecomanacionsItem(Integer.parseInt(id));
        ci.eliminarItem(Integer.parseInt(id));
        dadesModificades = true;
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
        if(!admin) throw new PrivilegesException("Ha de ser administrador.");
        ci.eliminarItem(Integer.parseInt(atributs.get(Item.getPosId()).get(0)));
        ci.add(new Item(atributs));
        dadesModificades = true;
    }


    /**
     * Gets the weights of each atribute for the comutations of the recommendations.
     *
     * @return     The weights.
     *
     * @throws     PrivilegesException  You need to be an admin to perform this functionallity.
     */
    public ArrayList<String> getPesos() throws PrivilegesException {
        if(!admin) throw new PrivilegesException("Ha de ser administrador.");
        ArrayList<Float> pesos = Item.getPesos();
        ArrayList<String> aux = new ArrayList<String>();
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
        if(!admin) throw new PrivilegesException("Ha de ser administrador.");
        ArrayList<Float> pesos = new ArrayList<Float>();
        for(int i = 0; i < pesosS.size(); i++) pesos.add(Float.parseFloat(pesosS.get(i)));
        Item.setPesos(pesos);
    }

    /**
     * Gets all the atribute types of the items.
     *
     * @return     The array of types as strings
     */
    public ArrayList<String> getTipus() {
        ArrayList<tipus> tipus = Item.getTipusArray();
        ArrayList<String> aux = new ArrayList<String>();
        for(int i = 0; i < tipus.size(); i++) aux.add(StringOperations.tipusToString(tipus.get(i)));
        return aux;
    }

    /**
     * Sets all the atribute types of the items.
     *
     * @param      tipusS                                       The new types of data for the atributes
     *
     * @throws     DataNotValidException                        Problems with the initialization of the static values of Item
     * @throws     ItemTypeNotValidException                    Some type assignation is not possible.
     */
    public void setTipus(ArrayList<String> tipusS) throws ItemTypeNotValidException, DataNotValidException {
        ArrayList<tipus> tipus = new ArrayList<tipus>();
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
        if(!admin && idR != this.id) throw new PrivilegesException("Un usuari només pot treballar amb les seves recomanacions.");
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
        if(!admin && idR != this.id) throw new PrivilegesException("Un usuari només pot treballar amb les seves recomanacions.");
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
        if(!admin && Integer.parseInt(usuariId) != this.id) throw new PrivilegesException("Un usuari només pot treballar amb les seves recomanacions.");
        cr.getRecomanacio(Integer.parseInt(itemId),Integer.parseInt(usuariId)).setVal(Float.parseFloat(rating));
        dadesModificades = true;
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
        if(!admin && Integer.parseInt(usuariId) != this.id) throw new PrivilegesException("Un usuari només pot treballar amb les seves recomanacions.");
        cr.getRecomanacio(Integer.parseInt(itemId),Integer.parseInt(usuariId)).setVal(Recomanacio.nul);
        dadesModificades = true;
    }

    /**
     * Removes the recommendation specified by the ids.
     *
     * @param      itemId                           The item identifier
     * @param      usrId                            The user identifier
     *
     * @throws     RecommendationNotFoundException  If the recommendation is not found.
     */
    public void removeRecmonacio(String itemId, String usrId) throws RecommendationNotFoundException {
        cr.removeRecomanacio(Integer.parseInt(itemId), Integer.parseInt(usrId));
        dadesModificades = true;
    }

    /**
     * Main functionality of this project. It generates recommendations for the current user from all the other information.
     * This calculations are performed in the algorithm controller.
     *
     * @throws     PrivilegesException  You need to be loged in as a user to perform this functionallity.
     */
    public void createRecomanacions() throws PrivilegesException {
        if(admin) throw new PrivilegesException("Has d'haver entrat com un usuari.");

        if(dadesModificades) cda.setData(ci, cu, cr);

        try{
            ArrayList<ItemValoracioEstimada> newRecomanacions = cda.run_algorithm(id);
            for(int i = 0; i < newRecomanacions.size(); i++) cr.add(new Recomanacio(cu.getUsuari(id),newRecomanacions.get(i).item));
        }
        catch(UserNotFoundException e) {
            System.out.print("ERROR INTERN!! Problema amb l'usuari actiu. No es troba en algun conjunt." + e.getMessage());
            return;
        }
        dadesModificades = true;
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
        if(!admin) throw new PrivilegesException("Has de ser administrador.");
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
        if(!admin) throw new PrivilegesException("Has de ser administrador.");
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
        if(!admin) throw new PrivilegesException("Has de ser administrador.");
        return String.valueOf(cda.get_algorisme());
    }

    /**
     * Sets the value K used in the algorithm to perform user clustering.
     *
     * @param     k                       The new value as a string
     *
     * @throws     PrivilegesException      You need to be an admin to perform this functionallity.
     * @throws     DataNotValidException    The value entered is not valid.
     */
    public void setK(String k) throws PrivilegesException, DataNotValidException {
        if(!admin) throw new PrivilegesException("Has de ser administrador.");
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
        if(!admin) throw new PrivilegesException("Has de ser administrador.");
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
        if(!admin) throw new PrivilegesException("Has de ser administrador.");
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
        if(!admin) throw new PrivilegesException("Has de ser administrador.");
        ArrayList<String> r = new ArrayList<String>();
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
        if(!admin) throw new PrivilegesException("Has de ser administrador.");
        if(!cu.add(new Usuari(Integer.parseInt(id)))) throw new UserIdNotValidException(Integer.parseInt(id));
        dadesModificades = true;
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
        if(!admin) throw new PrivilegesException("Has de ser administrador.");
        cr.removeRecomanacionsUsuari(Integer.parseInt(id));
        cu.removeUsuari(Integer.parseInt(id));
        dadesModificades = true;
    }


    /*----- ALTRES -----*/
    
    /**
     * Gets the different types of atribut using the enumeration Item.tipus
     *
     * @return     The array with the different types as strings.
     */
    public ArrayList<String> getTipusAsStrings() {
        ArrayList<String> r = new ArrayList<String>();
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

    /**
     * Resets all the data stored in a temporary state.
     */
    private void resetData() {
        cp.sortirDelProjecte();
        Item.resetStatics();
        cda.resetValues();
        ci = null;
        cr = null;
        cu = null;
        dadesModificades = true;
    }

}
