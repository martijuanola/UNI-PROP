package src.recomanador.domini;

import java.util.ArrayList;
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
    private static String nom = null;

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
     * Empty constructor. It only can be performed if the different Item static atributes are initialized.
     * 
     * @throws ItemStaticValuesNotInitializedException If the static values are not initialised
     */
    public ConjuntItems() throws ItemStaticValuesNotInitializedException {
        if (Item.getHeader() == null || Item.getPesos() == null || Item.getPosId() == -1
                || Item.getTipusArray() == null)
            throw new ItemStaticValuesNotInitializedException (
                    "Per crear un \"ConjuntItems\" buit primer has d'inicialitzar els atributs estàtics d'Item.");

        inicialitzarMinMax();
    }

    /**
     * Constructor with an array of items. This array is subdivided for atributes with ; <br>
     * For each item it is assigned an array of strings.<br>
     * Furthermore:<br>
     *  -Automatically detects the attributes types<br>
     *  -Calculates the maximums and minimums of the attributes<br>
     *  -Defaults all the weights to its maximum value (100)<br>
     *  -Fills the array with the names of each attribute<br>
     *  -Sets the position of id to the attribute with name id<br>
     *  -Sorts all items
     * 
     * @param items Array of an array of atributes. The first must be a header with the name of each attribute
     * @throws ItemTypeNotValidException                    Is thrown when the header does not contain "id" or when it is not valid
     * @throws ItemWeightNotCorrectException                Is thrown if it tries to assign a weight out of the range (0-100)
     * @throws ItemStaticValuesAlreadyInitializedException  Static values of items are already initialized
     * @throws ItemIdNotValidException                      There's no atribute valid tu use as ID
     * @throws ItemStaticValuesNotInitializedException      Thrown if the values are not correctlly initialized at the begining of the constructor
     * @throws ItemNewAtributesNotValidException            There's some new atribute not valid
     * @throws DataNotValidException                        Problem with the definition of atribute types
     */
    public ConjuntItems(ArrayList<ArrayList<String>> items) throws ItemTypeNotValidException,
            ItemWeightNotCorrectException, ItemStaticValuesAlreadyInitializedException,
            ItemIdNotValidException, ItemStaticValuesNotInitializedException,
            ItemNewAtributesNotValidException, DataNotValidException {
        //Inicialitzar tots els valors estàtics d'item i els max i min per defecte d'Atributs
        Item.inicialitzarStaticsDefault(items.get(0));
        inicialitzarMinMax();

        for (int i = 1; i < items.size(); ++i) {
            ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array on es posaran els atributs
            for (int j = 0; j < items.get(i).size(); ++j) { //Recorrem per separar en subvectors
                str.add(StringOperations.divideString(items.get(i).get(j), ';'));
            }
            Item it = new Item(str);
            add(it);//Afegeix ordenat
        }

        //S'assignen els tipus de cada columna
        detectarTipusAtributs();

        //calcular maxims i minims
        for (int i = 0; i < Item.getNumAtributs(); ++i) computeMinMaxAtribut(i);
    }

    /**
     * Constructor with an array of items, weights, attribute types, maximum attributes, minimum attributes, a string for the name, the position of the id attribute and the position of the name attribute.
     * @param items                             Array of an array of atributes. The first must be a header with the name of each attribute
     * @param pesos                             Array of weights for each attribute
     * @param tipusAtribut                      Array of types of each attributes
     * @param id                                Position of the attribute "id" in the array of name of attributes
     * @param nomA                              Position of the attribute of the name of each item in the array of name of attributes
     * @param nom                               Name that represents the set
     * @param maxAtributs                       Array of the maximum attributes of each column
     * @param minAtributs                       Array of the minimum attributes of each column
     * @throws ItemWeightNotCorrectException                Is thrown if it tries to assign a weight out of the range (0-100)
     * @throws ItemStaticValuesAlreadyInitializedException  Static values of items are already initialized
     * @throws ItemIdNotValidException                      There's no atribute valid tu use as ID
     * @throws ItemStaticValuesNotInitializedException      Thrown if the values are not correctlly initialized at the begining of the constructor
     * @throws ItemNewAtributesNotValidException            There's some new atribute not valid
     * @throws DataNotValidException                        Problem with the definition of atribute types
     */
    public ConjuntItems(ArrayList<ArrayList<String>> items, ArrayList<Float> pesos,
            ArrayList<tipus> tipusAtribut, int id, int nomA, String nom, ArrayList<Float> maxAtributs,
            ArrayList<Float> minAtributs)
            throws ItemWeightNotCorrectException, ItemStaticValuesAlreadyInitializedException, ItemIdNotValidException,
            ItemStaticValuesNotInitializedException, ItemNewAtributesNotValidException, DataNotValidException {

        // Inicialitzar atributs ConjuntItems
        ConjuntItems.nom = nom;

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
            super.add(it);
        }
    }

    /*----- STATICS -----*/
    
    /**
     * Gets the name of the set of items
     *
     * @return     The name
     */
    public static String getNomCjItems() {
        return nom;
    }

    /**
     * Sets the name of the set of items
     *
     * @param     s     The new name
     */
    public static void setNomCjItems(String s) {
        nom = s;
    }


    /*----- GETTERS -----*/

    /**
     * Return the item specified by its id
     * @param id ID of the item to get
     * @return Item with attribute "id" equals to id
     * @throws ItemNotFoundException Item with specified id does not exist
     */
    public Item getItem(int id) throws ItemNotFoundException {
        int pos = binarySearchItem(this, id, 0, size() - 1);
        if (pos < 0)
            throw new ItemNotFoundException("Item amb id: " + id + " no existeix");
        return get(pos);
    }

    /**
     * Get all items in the set. The result is used to store the contents of the set in a file by the persistance layer.
     * @return Return an array of an array of an array of strings which is the representation for an array of attributes of items
     */
    public ArrayList<ArrayList<ArrayList<String>>> getAllItems() {
        ArrayList<ArrayList<ArrayList<String>>> result = new ArrayList<ArrayList<ArrayList<String>>>();

        for (int i = 0; i < this.size(); ++i) {
            result.add(get(i).getAtributs());
        }
        return result;
    }

    /**
     * Returns array of maximum attributes
     * @return ArrayList&lt;Float&gt; of maximum attributes
     */
    public ArrayList<Float> getMaxAtributs() {
        return maxAtributs;
    }

    /**
     * Returns array of minimum attributes
     * @return ArrayList&lt;Float&gt; of minimum attributes
     */
    public ArrayList<Float> getMinAtributs() {
        return minAtributs;
    }

    /*----- SETTERS -----*/


    /**
     * Sets the minimum attributes to the parameter array
     * @param minAtributs2 Must be an array with the minimum values in the set and with size equals to the number of attributes
     */
    private void setMinAtributs(ArrayList<Float> minAtributs2) {
        this.minAtributs = minAtributs2;
    }

    /**
     * Sets the maximum attributes to the parameter array
     * @param maxAtributs2 Must be an array with the maximum values in the set and with size equals to the number of attributes
     */
    private void setMaxAtributs(ArrayList<Float> maxAtributs2) {
        this.maxAtributs = maxAtributs2;
    }

    /*----- CHECKERS -----*/

    /**
     * Checks if the column "columna" could be treated as a type t
     * @param columna int column
     * @param t type which will be checked
     * @return boolean, if true it can be treated as type t, if false the column cannot be treated as type t
     */
    private boolean tipusCorrecteColumna(int columna, tipus t) {
        if (t == tipus.S || t == tipus.N)
            return true;
        boolean empty = true;
        for (int i = 0; i < size(); ++i) {
            ArrayList<String> atribut = get(i).getAtributs().get(columna);
            if (atribut.size() > 1)
                return false;
            if (!atribut.get(0).equals("") && !atribut.get(0).equals(" ")) {
                empty = false;

                if (t == tipus.B && !StringOperations.esBool(atribut.get(0)))
                    return false;
                else if (t == tipus.F && !StringOperations.esFloat(atribut.get(0)))
                    return false;
                else if (t == tipus.I && !StringOperations.esNombre(atribut.get(0)))
                    return false;
                else if (t == tipus.D && !StringOperations.esData(atribut.get(0)))
                    return false;
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
        int res = binarySearchItem(this, id, 0, size() - 1);
        return res > -1;
    }

    /**
     * Checks if an item has bigger of lower attributes than the current max attributes and min attributes, respectively
     * @param it Item that is going to be checked
     * @throws ConjuntItemsAtributeNotInitializedException     If ther's one non-initialized atribute in the Item it 
     */
    private void checkMaxMin(Item it) throws ConjuntItemsAtributeNotInitializedException {
        if (maxAtributs == null || minAtributs == null) {
            throw new ConjuntItemsAtributeNotInitializedException();
        }
        for (int i = 0; i < maxAtributs.size(); ++i) {
            if (it.getAtributs().get(i).size() == 1) {
                Float nou = (float) 0;
                if (Item.getTipusArray().get(i) == tipus.I)
                    nou = (float) Integer.parseInt(it.getAtributs().get(i).get(0));
                else if (Item.getTipusArray().get(i) == tipus.F)
                    nou = Float.parseFloat(it.getAtributs().get(i).get(0));
                else if (Item.getTipusArray().get(i) == tipus.D)
                    nou = (float) StringOperations.dataToTime(it.getAtributs().get(i).get(0));

                if (nou > maxAtributs.get(i))
                    maxAtributs.set(i, (float) nou);
                if (nou < minAtributs.get(i))
                    minAtributs.set(i, (float) nou);
            }
        }
    }

    /*----- ADD/DELETE -----*/

    /**
     * Removes item specified by its id
     * @param id ID of the item that is going to be deleted
     * @throws ItemNotFoundException if it does not exist an item with such id
     * @throws ItemStaticValuesNotInitializedException      The static values need to be initialized.
     */
    public void eliminarItem(int id) throws ItemNotFoundException, ItemStaticValuesNotInitializedException {
        int pos = binarySearchItem(this, id, 0, size() - 1);
        if (pos < 0)
            throw new ItemNotFoundException("Item amb id " + id + " no existeix.");

        Item it = get(pos);
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            if (Item.getTipusArray().get(i) == tipus.S || Item.getTipusArray().get(i) == tipus.N
                    || Item.getTipusArray().get(i) == tipus.B)
                continue;

            float aux = 0;
            if (Item.getTipusArray().get(i) == tipus.D)
                aux = StringOperations.dataToTime(it.getAtributs().get(i).get(0));
            else
                aux = Float.parseFloat(it.getAtributs().get(i).get(0));

            if (aux == maxAtributs.get(i) || aux == minAtributs.get(i))
                computeMinMaxAtribut(i);
        }

        remove(pos);
    }

    /**
     * Adds an item to the sorted set and it checks and changes if necessary the maximum and minimums
     * @param i Item that is going to be added
     * @return true if the item has been added, false if there has been an error
     */
    @Override public boolean add(Item i) {
        int pos = Collections.binarySearch(this, i);
        if (pos < 0)
            pos = ~pos;
        super.add(pos, i);
        try {
            checkMaxMin(i);
        } catch (ConjuntItemsAtributeNotInitializedException e) {
            if (maxAtributs == null) System.out.println("MAX MAL");
            if (minAtributs == null) System.out.println("MIN MAL");
            System.out.println("ERROR: " + e.getMessage());
        }
        return (get(pos).getId() == i.getId());
    }

    /*----- COMPUTATIONS -----*/

    /**
     * Initializes de max and min values for every atribute with a default value.
     */
    private void inicialitzarMinMax() {
        maxAtributs = new ArrayList<Float>();
        minAtributs = new ArrayList<Float>();
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            maxAtributs.add(Float.MIN_VALUE);
            minAtributs.add(Float.MAX_VALUE);
        }
    }

    /**
     * Computes and sets the minimum and maximums attributes for the column col
     * @param col int column must be between 0 and the number of attributes
     * @throws ItemStaticValuesNotInitializedException  The static values of Item are not yet initialized
     */
    public void computeMinMaxAtribut(int col) throws ItemStaticValuesNotInitializedException {
        if (Item.getTipusArray() == null)
            throw new ItemStaticValuesNotInitializedException(
                    "Els valors estàtics d'Item han d'estar inicialitzats abans de calculars els màxims i mínims d'atributs.");
        tipus t = Item.getTipusArray().get(col);
        String sMin = "", sMax = "";

        if (t == tipus.I) {
            sMax = "" + Integer.MIN_VALUE;
            sMin = "" + Integer.MAX_VALUE;
        } else if (t == tipus.D) {
            sMax = "0000-00-00";
            sMin = "9999-12-31";
        } else if (t == tipus.F) {
            sMax = "" + Float.MIN_VALUE;
            sMin = "" + Float.MAX_VALUE;
        } else
            return;
        for (int i = 0; i < size(); ++i) {
            ArrayList<String> atr = get(i).getAtributs().get(col);
            for (int j = 0; j < atr.size(); ++j) {
                if (StringOperations.compararAtributs(atr.get(j), sMax, t) > 0) {
                    sMax = atr.get(j);
                } else if (StringOperations.compararAtributs(atr.get(j), sMin, t) < 0) { //atribut és menor que j
                    sMin = atr.get(j);
                }
            }
        }
        if (t == tipus.D) {
            if (sMax.length() == 10)
                maxAtributs.set(col, (float) StringOperations.dataToTime(sMax));
            if (sMin.length() == 10)
                minAtributs.set(col, (float) StringOperations.dataToTime(sMin));
        } else {
            maxAtributs.set(col, Float.parseFloat(sMax));
            minAtributs.set(col, Float.parseFloat(sMin));
        }
    }

    /**
     * Detects the type of each column and assigns it to the type array
     * @throws ItemTypeNotValidException if it doesn't exist an id column.
     * @throws ItemStaticValuesAlreadyInitializedException     The static values are already initialized.
     * @throws ItemIdNotValidException  There's not a valid column to use ad the ID of the items.
     */
    public void detectarTipusAtributs() throws ItemTypeNotValidException, ItemIdNotValidException,
                ItemStaticValuesAlreadyInitializedException {
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            if (Item.getPosId() == i) continue;
            if (tipusCorrecteColumna(i, tipus.B)) Item.setTipus(i, tipus.B);
            else if (tipusCorrecteColumna(i, tipus.F)) Item.setTipus(i, tipus.F);
            else if (tipusCorrecteColumna(i, tipus.D)) Item.setTipus(i, tipus.D);
            else Item.setTipus(i, tipus.S);
        }
    }

    /**
     * Computes and returns the similarity between two items comparing their attributes individually
     * @param i1 first item to be compared
     * @param i2 second item to be compared
     * @return float between 0 and 1 where 0 means completely different and 1 means exaclty the same
     * @throws ItemTypeNotValidException if the two items cannot be compared
     */
    public float distanciaItem(Item i1, Item i2) throws ItemTypeNotValidException {

        float res = (float) 0.0, pesTotal = (float) 0.0;
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            float dist = (float) 0.0;
            //Els casos on es comparen tags i només hi ha una string, es compara com si fossin conjunts, i es incorrecte
            if (Item.getTipusArray().get(i) == tipus.S
                    && (i1.getAtributs().get(i).size() > 1 || i2.getAtributs().get(i).size() > 1)) {
                float temp;
                temp = UnionIntersection.getIntersection(i1.getAtributs().get(i), i2.getAtributs().get(i)).size();
                dist = temp / UnionIntersection.getUnion(i1.getAtributs().get(i), i2.getAtributs().get(i)).size();
            } else {
                dist = StringOperations.distanciaAtribut(i1.getAtributs().get(i).get(0), i2.getAtributs().get(i).get(0), Item.getTipusArray().get(i),minAtributs.get(i) ,maxAtributs.get(i));
            }
            res += dist * (Item.getPesos().get(i) / ((float) 100.0));
            pesTotal += Item.getPesos().get(i) / ((float) 100.0);
        }
        res = ((float) 1.0 * res) / pesTotal;
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

            if (cmp == id)
                return mid;
            if (cmp > id)
                return binarySearchItem(list, id, lo, mid - 1);
            return binarySearchItem(list, id, mid + 1, hi);

        }
        return -1;
    }
}
