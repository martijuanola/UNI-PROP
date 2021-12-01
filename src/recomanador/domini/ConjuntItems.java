package src.recomanador.domini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import src.recomanador.Utils.*;
import src.recomanador.domini.Item.tipus;

import src.recomanador.excepcions.*;
/**
 * This class represents a set of items in the form of an ArrayList extension. 
 * It keeps the items sorted according to the item's ID, 
 * to achieve better performance when modifying this list.
 * @author Jaume C.
 */
public class ConjuntItems extends ArrayList<Item> {

    /*----- ATRIBUTS -----*/

    /**
     * Nom conjunt d'items
     */
    private static String nom; 
    
    /**
     * Maximum attributes for each column. Columns with strings, names and booleans will have random data
     */
    private ArrayList<Float> maxAtributs;
    /**
     * Minimum attributes for each column. Columns with strings, names and booleans will have random data
     */
    private ArrayList<Float> minAtributs;

    /*----- CONSTRUCTORS -----*/

    /**
     * Empty constructor
     */
    public ConjuntItems() throws ItemStaticValuesNotInitializedException {
        if (Item.getCapçalera() == null || Item.getPesos() == null || Item.getPosId() == -1 || Item.getTipusArray() == null) throw new ItemStaticValuesNotInitializedException("To create an empty \"ConjuntItems\" you first have to initialize the Item static attributes.");
    }

    /**
     * Constructor with an array of items. This array is subdivided for atributes with ; 
     * For each item it is assigned an array of strings.
     * Furthermore:
     *  -Automatically detects the attributes types
     *  -Calculates the maximums and minimums of the attributes
     *  -Defaults all the weights to its maximum value (100)
     *  -Fills the array with the names of each attribute
     *  -Sets the position of id to the attribute with name id
     *  -Sorts all items
     * 
     * @param items Array of an array of atributes. The first must be a header with the name of each attribute
     * @throws ItemTypeNotValidException Is thrown when the header does not contain "id" or when it is not valid
     * @throws ItemWeightNotCorrectException Is thrown if it tries to assign a weight out of the range (0-100)
     * @throws ItemStaticValuesAlreadyInitializedException
     * @throws ItemIdNotValidException
     * @throws ArrayIndexOutOfBoundsException
     * @throws ItemNewAtributesNotValidException
     * @throws ItemStaticValuesNotInitializedException
     */
    public ConjuntItems(ArrayList<ArrayList<String>> items) throws ItemTypeNotValidException, ItemWeightNotCorrectException, ItemStaticValuesAlreadyInitializedException, ArrayIndexOutOfBoundsException, ItemIdNotValidException, ItemStaticValuesNotInitializedException, ItemNewAtributesNotValidException {
        //Nom atributs
        ArrayList<String> nAtributs = items.get(0); //Nom dels atributs (capçalera)

        Item.setNomAtributs(nAtributs);

        //Inicialitzar tots els valors estàtics d'item i els max i min Atributs
        if (!inicialitzar(nAtributs.size())) throw new ItemTypeNotValidException("Items has no column named \"id\"");

        for (int i = 1; i < items.size(); ++i) {
            ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array on es posaran els atributs
            for (int j = 0; j < items.get(i).size(); ++j) { //Recorrem per separar en subvectors
                str.add(StringOperations.divideString(items.get(i).get(j), ';'));
            }
            Item it = new Item(str);
            addIni(it);//Afegeix ordenat
        }
        //S'assignen els tipus de cada columna
        detectarTipusAtributs();

        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            computeMinMaxAtribut(i);
        }
    }

    /**
     * Constructor with an array of items, weights, attribute types, maximum attributes, minimum attributes, a string for the name, the position of the id attribute and the position of the name attribute.
     * @param items Array of an array of atributes. The first must be a header with the name of each attribute
     * @param pesos Array of weights for each attribute
     * @param tipusAtribut Array of types of each attributes
     * @param id Position of the attribute "id" in the array of name of attributes
     * @param nomA Position of the attribute of the name of each item in the array of name of attributes
     * @param nom Name that represents the set
     * @param maxAtributs Array of the maximum attributes of each column
     * @param minAtributs Array of the minimum attributes of each column
     * @throws ItemWeightNotCorrectException Is thrown if it tries to assign a weight out of the range (0-100)
     * @throws ItemStaticValuesAlreadyInitializedException
     * @throws ItemIdNotValidException
     * @throws ItemNewAtributesNotValidException
     * @throws ItemStaticValuesNotInitializedException
     */
    public ConjuntItems(ArrayList<ArrayList<String>> items, ArrayList<Float> pesos,
    ArrayList<tipus> tipusAtribut, int id, int nomA, String nom, ArrayList<Float> maxAtributs, 
    ArrayList<Float> minAtributs) throws ItemWeightNotCorrectException, ItemStaticValuesAlreadyInitializedException, ItemIdNotValidException, ItemStaticValuesNotInitializedException, ItemNewAtributesNotValidException {
        
        // Inicialitzar atributs ConjuntItems
        ConjuntItems.setNom(nom);

        this.setMaxAtributs(maxAtributs);
        this.setMinAtributs(minAtributs);

        // Inicialitzar atributs statics Item
        Item.inicialitzarStatics(pesos, items.get(0), tipusAtribut, id, nomA);

        for (int i = 1; i < items.size(); ++i) {
            ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array on es posaran els atributs
            for (int j = 0; j < items.get(i).size(); ++j) { //Recorrem per separar en subvectors
                str.add(StringOperations.divideString(items.get(i).get(j), ';'));
            }
            Item it = new Item(str);
            addIni(it);
        }
    }

    /*----- GETTERS -----*/

    /**
     * Return the item specified by its id
     * @param id ID of the item to get
     * @return Item with attribute "id" equals to id
     * @throws ItemNotFoundException Item with specified id does not exist
     */
    public Item getItem(int id) throws ItemNotFoundException { //Cerca dicotòmica
        int pos = binarySearchItem(this, id, 0, size()-1);
        if (pos < 0) throw new ItemNotFoundException("Item amb id: " + id + " no existeix");
        return get(pos);
    }

    /**
     * Returns the name of the set
     * @return returns the string that represents the name of the set
     */
    public String getNom() {
        return ConjuntItems.nom;
    }

    /**
     * Get all items in the set
     * @return Return an array of an array of an array of strings which is the representation for an array of attributes of items
     */
    public ArrayList<ArrayList<ArrayList<String>>> getAllItems() {
        ArrayList<ArrayList<ArrayList<String>>> result = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<String>> aux = new ArrayList<ArrayList<String>>();
        ArrayList<String> header = Item.getCapçalera();

        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            ArrayList<String> a2 = new ArrayList<String>();
            a2.add(header.get(i));
            aux.add(a2);
        }
        result.add(aux);

        for (int i = 0; i < this.size(); ++i) {
            result.add(get(i).getAtributs());
        }
        return result;
    }

    /**
     * Returns array of maximum attributes
     * @return ArrayList<Float> of maximum attributes
     */
    public ArrayList<Float> getMaxAtributs() {
        return maxAtributs;
    }

    /**
     * Returns array of minimum attributes
     * @return ArrayList<Float> of minimum attributes
     */
    public ArrayList<Float> getMinAtributs() {
        return minAtributs;
    }

    /*----- SETTERS -----*/

    /**
     * Sets the minimum attributes to the parameter array
     * @param minAtributs2 Must be an array with the minimum values in the set and with size equals to the number of attributes
     */
    public void setMinAtributs(ArrayList<Float> minAtributs2) {
        this.minAtributs = minAtributs2;
    }
   
    /**
     * Sets the maximum attributes to the parameter array
     * @param minAtributs2 Must be an array with the maximum values in the set and with size equals to the number of attributes
     */
    public void setMaxAtributs(ArrayList<Float> maxAtributs2) {
        this.maxAtributs = maxAtributs2;
    }
    
    //TODO: setTipusItem hauria de ser private
    /**
     * Sets the column "atribut" to type t
     * @param atribut must be a column of the item attributes
     * @param t a type suitable for the column
     * @throws ItemTypeNotValidException if the column does not admit the type, it will generate an exception
     * @throws ItemStaticValuesAlreadyInitializedException
     * @throws ItemIdNotValidException
     * @throws ArrayIndexOutOfBoundsException
     */
    public void setTipusItem(int atribut, tipus t) throws ItemTypeNotValidException, ArrayIndexOutOfBoundsException, ItemIdNotValidException, ItemStaticValuesAlreadyInitializedException {
        if (!tipusCorrecteColumna(atribut, t)) throw new ItemTypeNotValidException("Column " + atribut + " does not admit type " + StringOperations.tipusToString(t));
        Item.setTipus(atribut, t);
    }

    /**
     * Sets the name of the set to n
     * @param n new name of the set
     */
    public static void setNom(String n) {
        ConjuntItems.nom = n;
    }

    /*----- CHECKERS -----*/

    //TODO: tipusCorrecteColumna hauria de ser private
    /**
     * Checks if the column "columna" could be treated as a type t
     * @param columna int column
     * @param t type which will be checked
     * @return boolean, if true it can be treated as type t, if false the column cannot be treated as type t
     */
    public boolean tipusCorrecteColumna(int columna, tipus t) {
        if (t == tipus.S || t == tipus.N) return true;
        boolean empty = true;
        if (t == tipus.I) {
            for (int i = 0; i < size(); ++i) {
                ArrayList<String> id = get(i).getAtributs().get(columna);
                if (id.size() > 1) return false;
                if (!id.get(0).equals("") && !id.get(0).equals(" ")){
                    empty = false;
                    if (!StringOperations.esNombre(id.get(0))) return false;
                }
            }
        }
        else if (t == tipus.B) {
            for (int i = 0; i < size(); ++i) {
                ArrayList<String> id = get(i).getAtributs().get(columna);
                if (id.size() > 1) return false;
                if (!id.get(0).equals("") && !id.get(0).equals(" ")){
                    empty = false;
                    if (!StringOperations.esBool(id.get(0))) return false;
                }
            }
        }
        else if (t == tipus.F) {
            for (int i = 0; i < size(); ++i) {
                ArrayList<String> id = get(i).getAtributs().get(columna);
                if (id.size() > 1) return false;
                if (!id.get(0).equals("") && !id.get(0).equals(" ")){
                    empty = false;
                    if (!StringOperations.esFloat(id.get(0))){
                        return false;
                    } 
                }
            }
        }
        else if (t == tipus.D) {
            for (int i = 0; i < size(); ++i) {
                ArrayList<String> id = get(i).getAtributs().get(columna);
                if (id.size() > 1) return false;
                if (!id.get(0).equals("") && !id.get(0).equals(" ")){
                    empty = false;
                    if (!StringOperations.esData(id.get(0))) return false;
                }
            }
        }
        // Nom i String sempre estaran bé
        return !empty;
    }
    
    /**
     * Returns if an item with attribute id exists
     * @param id ID of the item that is searched
     * @return true if the item is in the set, false if the item does not exist
     */
    public boolean existeixItem(int id) {
        int res = binarySearchItem(this, id, 0, size()-1);
        return res > -1;
    }
    
    //TODO: tipusCorrecte hauria de ser private
    /**
     * Checks if a string can be treated as type t
     * @param s string that is going to be checked
     * @param t type that is going to be checked
     * @return true if s can be treated as type t, false if s cannot be treated as type t
     */
    public static boolean tipusCorrecte(String s, tipus t) {
        if (t == tipus.I) {
            if (StringOperations.esNombre(s) || s.equals("") && !s.equals(" ")) return true;
        }
        else if (t == tipus.B) {
            if (StringOperations.esBool(s) || s.equals("") && !s.equals(" ")) return true;
        }
        else if (t == tipus.F) {
            if (StringOperations.esFloat(s) || s.equals("") && !s.equals(" ")) return true;
        }
        else if (t == tipus.D) {
            if (StringOperations.esData(s) || s.equals("") && !s.equals(" ")) return true;
        }
        else if (t == tipus.S) return true;
        else if (t == tipus.N) return true;
        // Nom i String sempre estaran bé
        return false;
    }
    
    //TODO: checkMaxMin hauria de ser private
    /**
     * Checks if an item has bigger of lower attributes than the current max attributes and min attributes, respectively
     * @param it Item that is going to be checked
     */
    public void checkMaxMin(Item it) {
        if (maxAtributs == null || minAtributs == null) {
            for (int i = 0; i < it.getAtributs().size(); ++i) {
                if (it.getAtributs().get(i).size() == 1) {
                    Float nou = (float)0;
                    if (Item.getTipusArray().get(i) == tipus.I) {
                        nou = (float)Integer.parseInt(it.getAtributs().get(i).get(0));
                    }
                    else if (Item.getTipusArray().get(i) == tipus.F) {
                        nou = Float.parseFloat(it.getAtributs().get(i).get(0));
                    }
                    else if (Item.getTipusArray().get(i) == tipus.D) {
                        nou = (float) StringOperations.dataToTime(it.getAtributs().get(i).get(0));
                    }
                    maxAtributs.set(i, (float)nou);
                    minAtributs.set(i, (float)nou);
                }
            }
        }
        for (int i = 0; i < maxAtributs.size(); ++i) {
            if (it.getAtributs().get(i).size() == 1) {
                Float nou = (float)0;
                if (Item.getTipusArray().get(i) == tipus.I) {
                    nou = (float)Integer.parseInt(it.getAtributs().get(i).get(0));
                }
                else if (Item.getTipusArray().get(i) == tipus.F) {
                    nou = Float.parseFloat(it.getAtributs().get(i).get(0));
                }
                else if (Item.getTipusArray().get(i) == tipus.D) {
                    nou = (float) StringOperations.dataToTime(it.getAtributs().get(i).get(0));
                }
                if (nou > maxAtributs.get(i)) maxAtributs.set(i, (float)nou);
                if (nou < minAtributs.get(i)) minAtributs.set(i, (float)nou);
            }
        }
    }

    /*----- ADD/DELETE -----*/

    /**
     * Removes item specified by its id
     * @param id ID of the item that is going to be deleted
     * @throws ItemNotFoundException if it does not exist an item with such id
     */
    public void eliminarItem(int id) throws ItemNotFoundException { //Cerca dicotòmica
        int pos = binarySearchItem(this, id, 0, size()-1);
        if (pos < 0) throw new ItemNotFoundException("Item with id " + id + " does not exist");
        remove(pos);
        for (int i = 0; i < Item.getNumAtributs(); ++i) computeMinMaxAtribut(i);
    }
    
    /**
     * Adds an item to the sorted set and it checks and changes if necessary the maximum and minimums
     * @param i Item that is going to be added
     * @return true if the item has been added, false if there has been an error
     */
    @Override
    public boolean add(Item i) {
        int pos = Collections.binarySearch(this, i);
        if (pos < 0) pos = ~pos;
        super.add(pos, i);
        checkMaxMin(i);
        return (get(pos).getId() == i.getId());
    }

    //TODO: addIni hauria de ser private
    /**
     * Adds an item to the ordered set without checking the maximums and minimums
     * @param i Item that is going to be added
     * @return true if the item has been added, false if there has been an error
     */
    public boolean addIni(Item i) {
        int pos = Collections.binarySearch(this, i);
        if (pos < 0) pos = ~pos;
        super.add(pos, i);
        return (get(pos).getId() == i.getId());
    }

    /*----- COMPUTATIONS -----*/

    /**
     * Computes and sets the minimum and maximums attributes for the column col
     * @param col int column must be between 0 and the number of attributes
     */
    public void computeMinMaxAtribut(int col) {
        tipus t = Item.getTipusArray().get(col);
        String sMin = "", sMax = "";

        if (t == tipus.I) {
            sMax = ""+Integer.MIN_VALUE;
            sMin = ""+Integer.MAX_VALUE;
        }
        else if (t == tipus.D) {
            sMax = "0000-00-00";
            sMin = "9999-12-31";
        }
        else if (t == tipus.F) {
            sMax = ""+Float.MIN_VALUE; 
            sMin = ""+Float.MAX_VALUE;
        }
        else return;
        for (int i = 0; i < size(); ++i) {
            ArrayList<String> atr = get(i).getAtributs().get(col);
            for (int j = 0; j < atr.size(); ++j) {
                if (StringOperations.compararAtributs(atr.get(j), sMax, t) > 0) {
                    sMax = atr.get(j);
                }
                else if (StringOperations.compararAtributs(atr.get(j), sMin, t) < 0) { //atribut és menor que j
                    sMin = atr.get(j);
                }
            }
        }
        if (t == tipus.D) {
            if (sMax.length()==10) maxAtributs.set(col, (float)StringOperations.dataToTime(sMax));
            if (sMin.length()==10) minAtributs.set(col, (float)StringOperations.dataToTime(sMin));
        }
        else {
            maxAtributs.set(col, Float.parseFloat(sMax));
            minAtributs.set(col, Float.parseFloat(sMin));
        }
    }
    
    //TODO: inicialitzar hauria de ser private
    /**
     * Initializes the set and the basic attributes such as the weight array and the type array,
     * this allows acces to the arrays knowing that they won't be null
     * @param nAtributs number of attributes that the items in each set has
     * @return returns true if it exists an attribute named "id"
     * @throws ItemTypeNotValidException if it is tried to modify a column type that is not correct
     * @throws ItemWeightNotCorrectException if a weight outside the range (0 - 100) is tried to be assigned 
     * @throws ItemStaticValuesAlreadyInitializedException
     * @throws ItemIdNotValidException
     * @throws ArrayIndexOutOfBoundsException
     */
    public boolean inicialitzar(int nAtributs) throws ItemTypeNotValidException, ItemWeightNotCorrectException, ItemStaticValuesAlreadyInitializedException, ArrayIndexOutOfBoundsException, ItemIdNotValidException { //Inicialitza amb coses aleatories, no es pot utlitzar fins omplir bé

        // VALORS DE CONJUNTITEMS
        maxAtributs = new ArrayList<Float>();
        minAtributs = new ArrayList<Float>();
        for (int i = 0; i < nAtributs; ++i) {
            maxAtributs.add(Float.MIN_VALUE);
            minAtributs.add(Float.MAX_VALUE);
        }
        
        //VALORS ESTATICS DE ITEM
        ArrayList<Float> pesos = new ArrayList<Float>(nAtributs);
        ArrayList<tipus> tipusAtribut = new ArrayList<tipus>(nAtributs);

        for (int i = 0; i < nAtributs; i = i + 1) {
            pesos.add((float) 100.0);
            tipusAtribut.add(i, tipus.S);
        }

        Item.setPesos(pesos);
        Item.setTipusArray(tipusAtribut);

        boolean found = false;
        for (int i = 0; i < nAtributs && !found; ++i) {
            if (Item.getCapçalera().get(i).equalsIgnoreCase("id")) {
                Item.setTipus(i, tipus.I);
                found = true;
            }
        }
        return found;
    }
    
    /**
     * Detects the type of each column and assigns it to the type array
     * @throws ItemTypeNotValidException if it doesn't exist an id column
     * @throws ItemStaticValuesAlreadyInitializedException
     * @throws ItemIdNotValidException
     * @throws ArrayIndexOutOfBoundsException
     */
    public void detectarTipusAtributs() throws ItemTypeNotValidException, ArrayIndexOutOfBoundsException, ItemIdNotValidException, ItemStaticValuesAlreadyInitializedException { //Es pot assignar qualsevol tipus menys nom, aquest s'ha d'assignar manualment
        for (int i = 0; i < Item.getNumAtributs(); ++i) {            
            if (Item.getPosId() == i) continue;

            if (tipusCorrecteColumna(i, tipus.B)) setTipusItem(i, tipus.B);
            else if (tipusCorrecteColumna(i, tipus.F)) setTipusItem(i, tipus.F);
            else if (tipusCorrecteColumna(i, tipus.D)) setTipusItem(i, tipus.D);
            else setTipusItem(i, tipus.S);
        }
    }
    
    //TODO: distanciaAtribut hauria de ser private
    /**
     * Computes the similarity between to strings that belong to the same column. The similarity for each type is measured differently
     * @param a1 first string to be compared
     * @param a2 second string to be compared
     * @param columna column to which a1 and a2 belong
     * @return float between 0 and 1 where 0 means completely different and 1 means exaclty the same
     * @throws ItemTypeNotValidException if a1 and a2 are not of the same type
     */
    public float distanciaAtribut(String a1, String a2, int columna) throws ItemTypeNotValidException {
        tipus t = Item.getTipusArray().get(columna);
        if (!tipusCorrecte(a1, t) || !tipusCorrecte(a2, t)) throw new ItemTypeNotValidException("atribut " + a1 + " o atribut " + a2 + " no son del tipus " + StringOperations.tipusToString(t));

        if (a1.equals(a2)) return (float)1.0;
        if (a1.length() == 0 || a2.length() == 0) return (float)0.0;

        float sim = (float)0.0;
        if (t == tipus.I) {
            int i1 = Integer.parseInt(a1), i2 = Integer.parseInt(a2);
            sim = 1 - Math.abs((i1 - i2) / (maxAtributs.get(columna) - minAtributs.get(columna)));
        }
        else if (t == tipus.B) {
            boolean b1 = Boolean.parseBoolean(a1), b2 = Boolean.parseBoolean(a2);
            if (b1 == b2) sim = (float)1.0;
            else sim = (float)0.0;
        }
        else if (t == tipus.D) {
            if (a1.length() != a2.length()) return (float)0.0;
            float dataMax = maxAtributs.get(columna), dataMin = minAtributs.get(columna); //TODO: canviar que es guarden les dates
            sim = 1 - Math.abs((StringOperations.dataToTime(a1) - StringOperations.dataToTime(a2)) / (dataMax - dataMin));
        }
        else if (t == tipus.F) {
            float i1 = Float.parseFloat(a1), i2 = Float.parseFloat(a2);
            sim = 1 - Math.abs((i1 - i2) / (maxAtributs.get(columna) - minAtributs.get(columna)));
        }
        else if (t == tipus.S || t == tipus.N) {
            int n= a1.length(), m = a2.length();
            int[][] dp = new int[n + 1][m + 1];
            for(int i = 0; i < n + 1; i++) Arrays.fill(dp[i], -1);
            int a = StringOperations.minDis(a1, a2, n, m, dp);
            sim = 1-(float)1.0*a/Math.max(n, m);
        }
        return sim;
    }

    /**
     * Computes and returns the similarity between two items comparing their attributes individually
     * @param i1 first item to be compared
     * @param i2 second item to be compared
     * @return float between 0 and 1 where 0 means completely different and 1 means exaclty the same
     * @throws ItemTypeNotValidException if the two items cannot be compared
     */
    public float distanciaItem(Item i1, Item i2) throws ItemTypeNotValidException {

        float res = (float)0.0, pesTotal = (float)0.0;
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            float dist = (float)0.0;
            //Els casos on es comparen tags i només hi ha una string, es compara com si fossin conjunts, i es incorrecte
            if (Item.getTipusArray().get(i) == tipus.S && (i1.getAtributs().get(i).size() > 1 || i2.getAtributs().get(i).size() > 1)) {
                float temp;
                temp = UnionIntersection.getIntersection(i1.getAtributs().get(i), i2.getAtributs().get(i)).size();
                dist = temp / UnionIntersection.getUnion(i1.getAtributs().get(i), i2.getAtributs().get(i)).size();
            }
            else {
                dist = distanciaAtribut(i1.getAtributs().get(i).get(0), i2.getAtributs().get(i).get(0), i);
            }
            res += dist*(Item.getPesos().get(i)/((float)100.0));
            pesTotal += Item.getPesos().get(i)/((float)100.0);
        }
        res = ((float)1.0*res)/pesTotal;
        return res;
    }
    
    /**
     * Basic binary search adapted to work with Items
     * @param list ArrayList of Items 
     * @param id target to search for
     * @param lo lower bound
     * @param hi upper bound
     * @return returns -1 if the target is not in the list, otherwise, the position of the target in the list
     */
    public static int binarySearchItem(ArrayList<Item> list, int id, int lo, int hi) {
        if (hi >= lo) {
            int mid = lo + (hi - lo) / 2;
            int cmp = list.get(mid).getId();
            
            if (cmp == id) return mid;
            if (cmp > id) return binarySearchItem(list, id, lo, mid-1);
            return binarySearchItem(list, id, mid+1, hi);

        }
        return -1;
    }
}
