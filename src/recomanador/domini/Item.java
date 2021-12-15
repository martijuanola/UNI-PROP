package src.recomanador.domini;

import java.util.ArrayList;

import src.recomanador.excepcions.*;

/**
 * Classe represent un item d'un conjunt items
 * @author Jaume C.
 */
public class Item implements Comparable<Item>{

    /*----- ATRIBUTS STATICS -----*/
    
    /**
     * This enumaration represents the types that an attribute can have
     * ID
     * Nom
     * Boolean
     * Float
     * String
     * Date
     */
    public static enum tipus {
        I, 
        N, 
        B, 
        F, 
        S,
        D 
    }

    /**
     * Array with the weights of each attribute. The default value is null
     */
    private static ArrayList<Float> pesos;

    /**
     * Array with the weights of each attribute. I and N should be unique
     */
    private static ArrayList<tipus> tipusAtribut;

    /**
     * Array with the names of of each attribute.
     */
    private static ArrayList<String> nomAtribut;

    /**
     * Position of the attribute id at the array of types "tipusAtribut"
     */
    private static int id = -1;
    
    /**
     * Position of the attribute that represents tha name of an item at the array of types "tipusAtribut"
     * -1 if the index doesn't exist
     */
    private static int nomA = -1;


    /*----- ATRIBUTS -----*/

    /**
     * ArrayList where each attribute has its own vector. An attribute can be empty
     */
    private ArrayList<ArrayList<String>> atributs;


    /*----- CONSTRUCTORS -----*/

    /**
     * Constructor with only the attribute id. Initialized all static elements
     * @param id Item identifier
     */
    public Item(int idval) throws ItemStaticValuesNotInitializedException {
        if(pesos == null) throw new ItemStaticValuesNotInitializedException("pesos");
        if(tipusAtribut == null) throw new ItemStaticValuesNotInitializedException("tipusAtribut");
        if(nomAtribut == null) throw new ItemStaticValuesNotInitializedException("nomAtribut");
        if(id == -1) throw new ItemStaticValuesNotInitializedException("id");

        this.atributs = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < Item.getNumAtributs(); i++) {
            ArrayList<String> default_val = new ArrayList<String>();
            if(i != Item.id) default_val.add("");
            else default_val.add(""+idval);
            this.atributs.add(default_val);
        }
    }

    /**
     * An item is created and the attributes are assigned to it
     * @param atributs list of attributes where each attribute can have multiple subattributes
     */
    public Item(ArrayList<ArrayList<String>> atributsval) throws ItemStaticValuesNotInitializedException, ItemNewAtributesNotValidException {
        if(pesos == null) throw new ItemStaticValuesNotInitializedException("pesos");
        if(tipusAtribut == null) throw new ItemStaticValuesNotInitializedException("tipusAtribut");
        if(nomAtribut == null) throw new ItemStaticValuesNotInitializedException("nomAtribut");
        if(id == -1) throw new ItemStaticValuesNotInitializedException("id");

        if(atributsval.size() != Item.getNumAtributs()) throw new ItemNewAtributesNotValidException(Item.getNumAtributs());

        this.atributs = atributsval;
    }   

    /*----- GETTERS -----*/

    /**
     * Returns the id specified by the id position
     * @return id of the item
     */
    public int getId() {
        return Integer.parseInt(atributs.get(Item.id).get(0));
    }

    /**
     * Returns all attributes
     * @return attributes as an array of array of strings
     */
    public ArrayList<ArrayList<String>> getAtributs() {
        return atributs;
    }

    /*----- SETTERS -----*/


    /*----- STATICS GETTERS -----*/

    /**
     * Returns the positition where the attribute id points to
     * @return value of the "id" attribute
     */
    public static int getPosId() {
        return Item.id;
    }

    /**
     * Returns the positition where the attribute nomA points to
     * @return value of the "nomA" attribute
     */
    public static int getPosNomA() {
        return Item.nomA;
    }

    /**
     * Returns the number of attributes the item has
     * @return size of the attributes array
     */
    public static int getNumAtributs() {
        return Item.nomAtribut.size();
    }

    /**
     * Returns the array of weights
     * @return ArrayList<Float> with the weights of an item
     */
    public static ArrayList<Float> getPesos() {
        return Item.pesos;
    }

    /**
     * Returns the array of types
     * @return ArrayList<tipus> with the types of an item
     */
    public static ArrayList<tipus> getTipusArray() {
        return Item.tipusAtribut;
    }

    /**
     * Returns the array of names of the attributes
     * @return ArrayList<String> with the names of the attributes of an item
     */
    public static ArrayList<String> getCapçalera() {
        return Item.nomAtribut;
    }


    /*----- STATICS SETTERS -----*/

    //TODO: ha de ser protected
    /**
     * Sets a column that will act as id
     * @param c    new id that will be set
     * @throws     ItemIdNotValidException      Thrown when id can not be changed to the column c
     */
    public static void setId(int c) throws ItemIdNotValidException { //TODO: S'ha de ordenar el conjunt d'items quan es canvia l'id
        if (c < 0 || c >= Item.getNumAtributs()) throw new ArrayIndexOutOfBoundsException("The column is incorrect");    
        if (Item.id != -1 || !Item.nomAtribut.get(c).equalsIgnoreCase("id")) throw new ItemIdNotValidException(id);
        Item.id = c;
    }

    /**
     * Sets a column that will act as name of the items
     * @param c new name that will be set
     * @throws ItemStaticValuesAlreadyInitializedException If the column of the name is already defined
     */
    public static void setNomA(int c) throws ArrayIndexOutOfBoundsException, ItemStaticValuesAlreadyInitializedException {
        if (c < 0 || c >= Item.getNumAtributs()) throw new ArrayIndexOutOfBoundsException("The column is incorrect");
        if (nomA != -1) throw new ItemStaticValuesAlreadyInitializedException();
        Item.nomA = c;
    }

    /**
     * Sets the weight of column
     * @param a column which will be modified
     * @param pes new weight that will be assigned
     * @throws ItemWeightNotCorrectException if weights is outside the range (0 - 100)
     * @throws ArrayIndexOutOfBoundsException if the column is not between 0 and the number of attributes
     */
    public static void setPes(int a, float pes) throws ItemWeightNotCorrectException, ArrayIndexOutOfBoundsException {
        if (a < 0 || a >= Item.pesos.size()) throw new ArrayIndexOutOfBoundsException("index " + a + " out of bounds for array of size " + pesos.size());
        if (pes < 0.0) throw new ItemWeightNotCorrectException("Weight smaller than 0");
        else if (pes > 100.0) throw new ItemWeightNotCorrectException("Weight bigger than 100");
        
        Item.pesos.set(a, pes);
    }

    //ha de venir comprovat a assignaTipusItem de conjunt d'items
    /**
     * Sets the type of column
     * @param columna column which will be modified
     * @param t new type that will be assigned
     * @throws ItemTypeNotValidException if the column does not accept the new type
     * @throws ItemNomANotValidException If the column of the name is already defined
     * @throws ArrayIndexOutOfBoundsException
     */
    public static void setTipus(int columna, tipus t) throws ItemTypeNotValidException, ItemIdNotValidException, ArrayIndexOutOfBoundsException, ItemStaticValuesAlreadyInitializedException {
        if (Item.id == columna) throw new ItemTypeNotValidException("Cannot change the Item ID.");
        if (t == tipus.I) setId(columna);
        else if (t == tipus.N) setNomA(columna);

        Item.tipusAtribut.set(columna, t);
    }

    /**
     * Sets the weights array to the parameter
     * @param p array of weights
     * @throws ItemWeightNotCorrectException if any weights is outside the range (0 - 100)
     * @throws ArrayIndexOutOfBoundsException if the array is not the size of the attributes
     */
    public static void setPesos(ArrayList<Float> p) throws ItemWeightNotCorrectException, ArrayIndexOutOfBoundsException{
        if (p.size() != Item.getNumAtributs()) throw new ArrayIndexOutOfBoundsException("Weights array does not match items attributes size.");
        for (int i = 0; i < Item.getNumAtributs(); ++i) if (p.get(i) > 100 || p.get(i) < 0) throw new ItemWeightNotCorrectException("Weight smaller than 0 or bigger than 100");
        
        Item.pesos = p;
    }

    /**
     * Sets the types array to the parameter
     * @param a array of types of the same size as the attributes
     * 
     * @throws ItemStaticValuesAlreadyInitializedException
     */
    public static void setTipusArray(ArrayList<tipus> a) throws ItemStaticValuesAlreadyInitializedException {
        if (Item.tipusAtribut != null) throw new ItemStaticValuesAlreadyInitializedException();
        if (a.size() != Item.getNumAtributs()) throw new ArrayIndexOutOfBoundsException("Types array does not match items attributes size.");
        Item.tipusAtribut = a;
    }

    /**
     * Sets the names of attributes array to the parameter
     * @param n array of names of attributes of the same size as the attributes
     * @throws ItemStaticValuesAlreadyInitializedException
     */
    public static void setNomAtributs(ArrayList<String> n) throws ItemStaticValuesAlreadyInitializedException {
        if (Item.nomAtribut != null) throw new ItemStaticValuesAlreadyInitializedException();
        Item.nomAtribut = n;
    }
    
    /**
     * Resetao
     */
    public static void resetStatics() {
        Item.pesos = null;

        Item.tipusAtribut = null;

        Item.nomAtribut = null;

        Item.id = -1;

        Item.nomA = -1;
    }

    public static void inicialitzarStatics(ArrayList<Float> pesos, ArrayList<String> nomAtributs, ArrayList<tipus> tipusAtribut, int id, int nomA) throws ItemStaticValuesAlreadyInitializedException, ArrayIndexOutOfBoundsException, ItemWeightNotCorrectException, ItemIdNotValidException {
        Item.setNomAtributs(nomAtributs);

        Item.setPesos(pesos);

        Item.setTipusArray(tipusAtribut);

        Item.setId(id);

        Item.setNomA(nomA);
    }

    public static void inicialitzarStaticsDefault(ArrayList<String> atributs) throws ItemStaticValuesAlreadyInitializedException, ArrayIndexOutOfBoundsException, ItemWeightNotCorrectException, ItemTypeNotValidException, ItemIdNotValidException {
        int n = atributs.size();

        //VALORS ESTATICS DE ITEM
        Item.setNomAtributs(atributs);
        ArrayList<Float> pesos = new ArrayList<Float>(n);
        ArrayList<tipus> tipusAtribut = new ArrayList<tipus>(n);

        for (int i = 0; i < n; i = i + 1) {
            pesos.add((float) 100.0);
            tipusAtribut.add(i, tipus.S);
        }

        Item.setPesos(pesos);
        Item.setTipusArray(tipusAtribut);

        boolean found = false;
        for (int i = 0; i < n && !found; ++i) {
            if (Item.getCapçalera().get(i).equalsIgnoreCase("id")) {
                Item.setTipus(i, tipus.I);
                found = true;
            }
        }
        if (!found) throw new ItemTypeNotValidException("Items has no column named \"id\"");
    }

    /*----- ALTRES -----*/

    /**
     * Function that overrides the compareTo by defalut. Compares 2 items by their id's
     * @param otherItem The other item that compares
     * @return Returns an integer depending on the comparisson between the implicit item and the item given as a parameter
     * 1 if implicit > otherItem, 
     * 0 if implicit = otherItem, 
     * -1 if implicit < otherItem
     */
    @Override public int compareTo(Item otherItem) {
        int id1 = getId();
        int id2 = otherItem.getId();
        
        if (id1 == id2) return 0;
        else if (id1 < id2) return -1;
        else return 1;
    }
}
