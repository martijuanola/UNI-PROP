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
     * This enumaration represents the types that an attribute can have (ID, Nom, Boolean, Float, String, Date)
     */
    public static enum tipus {
        /** ID */
        I,
        /** Nom */
        N,
        /** Boolean */
        B,
        /** Float */
        F,
        /** String */
        S,
        /** Date */
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
     * Constructor with only the attribute id. All the static values need to be initialitzed.
     *
     * @param      idval  The id
     * 
     * @throws     ItemStaticValuesNotInitializedException      Thrown if the static values are not initialized.
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
     * Constructs a new instance with the all the new atributes.
     *
     * @param      atributsval  The atributes
     * 
     * @throws     ItemStaticValuesNotInitializedException      Thrown if the static values are not initialized.
     * @throws     ItemNewAtributesNotValidException            There's some new atribute not valid
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
     * @return ArrayList&lt;Float&gt; with the weights of an item
     */
    public static ArrayList<Float> getPesos() {
        return Item.pesos;
    }

    /**
     * Returns the array of types
     * @return ArrayList&lt;tipus&gt; with the types of an item
     */
    public static ArrayList<tipus> getTipusArray() {
        return Item.tipusAtribut;
    }

    /**
     * Returns the array of names of the attributes(header)
     * @return ArrayList&lt;String&gt; with the names of the attributes of an item
     */
    public static ArrayList<String> getHeader() {
        return Item.nomAtribut;
    }


    /*----- STATICS SETTERS -----*/

    /**
     * Sets a column that will act as id
     * @param c    The atribute that will reperesent the id of the items
     * @throws     ItemIdNotValidException      posId is already defined or is an invalid column
     */
    protected static void setId(int c) throws ItemIdNotValidException {
        if (c < 0 || c >= Item.getNumAtributs()) throw new ArrayIndexOutOfBoundsException("La columna indicada no és correcte.");   
        if (Item.id != -1 || !Item.nomAtribut.get(c).equalsIgnoreCase("id")) throw new ItemIdNotValidException(id);
        Item.id = c;
    }

    /**
     * Sets a column that will act as name of the items
     * @param c new name that will be set
     * @throws ArrayIndexOutOfBoundsException If the column is out of range
     */
    public static void setNomA(int c) {
        if (c < 0 || c >= Item.getNumAtributs()) throw new ArrayIndexOutOfBoundsException("La columna indicada no és correcte.");
        if (nomA == -1) Item.nomA = c;
    }

    /**
     * Sets the weight of column
     * @param a column which will be modified
     * @param pes new weight that will be assigned
     * @throws ItemWeightNotCorrectException if weights is outside the range (0 - 100)
     */
    public static void setPes(int a, float pes) throws ItemWeightNotCorrectException {
        if (pes < 0.0) throw new ItemWeightNotCorrectException("Pes inferior a 0!");
        else if (pes > 100.0) throw new ItemWeightNotCorrectException("Pes superior a 100!");
        
        Item.pesos.set(a, pes);
    }

    //ha de venir comprovat a assignaTipusItem de conjunt d'items
    /**
     * Sets the type of column
     * @param columna column which will be modified
     * @param t new type that will be assigned
     * @throws ItemTypeNotValidException                    If the column does not accept the new type.
     * @throws ItemIdNotValidException                      podID already defined.
     * @throws ItemStaticValuesAlreadyInitializedException  The static values are already initialized.
     */
    public static void setTipus(int columna, tipus t) throws ItemTypeNotValidException, ItemIdNotValidException, ItemStaticValuesAlreadyInitializedException {
        if (Item.id == columna) throw new ItemTypeNotValidException("No es pot canviar l'ID d'un Item");
        if (t == tipus.I) setId(columna);
        else if (t == tipus.N) setNomA(columna);

        Item.tipusAtribut.set(columna, t);
    }

    /**
     * Sets the weights array to the parameter
     * @param p array of weights
     * @throws ItemWeightNotCorrectException if any weights is outside the range (0 - 100)
     */
    public static void setPesos(ArrayList<Float> p) throws ItemWeightNotCorrectException {
        if (p.size() != Item.getNumAtributs()) throw new ArrayIndexOutOfBoundsException("La llista de pesos no concorda amb el nombre d'atibuts.");
        for (int i = 0; i < Item.getNumAtributs(); ++i) if (p.get(i) > 100 || p.get(i) < 0) throw new ItemWeightNotCorrectException("Els pesos han de ser entre 0 i 100.");
        
        Item.pesos = p;
    }

    /**
     * Changes de types of all the atributes. Also checks that any
     * @param a array of types of the same size as the attributes
     * 
     * @throws     DataNotValidException            If the array given had problems with the ID position or NAM position.
     */
    public static void setTipusArray(ArrayList<tipus> a) throws DataNotValidException {
        if (a.size() != Item.getNumAtributs()) throw new ArrayIndexOutOfBoundsException("La llista de tipus d'atributs no concorda amb el nombre d'atibuts.");
        Item.tipusAtribut = a;
        for (int i = 0; i < a.size(); ++i) {
            if (a.get(i) == tipus.N && nomA == -1) {
                setNomA(i);
            }
        }
    }

    /**
     * Sets the names of the atributes
     * @param n array of names of attributes of the same size as the attributes
     * @throws ItemStaticValuesAlreadyInitializedException      If the names are already defined
     */
    public static void setNomAtributs(ArrayList<String> n) throws ItemStaticValuesAlreadyInitializedException {
        if (Item.nomAtribut != null) throw new ItemStaticValuesAlreadyInitializedException();
        Item.nomAtribut = n;
    }
    
    /**
     * Resets the static values to the default state.
     */
    public static void resetStatics() {
        Item.pesos = null;

        Item.tipusAtribut = null;

        Item.nomAtribut = null;

        Item.id = -1;

        Item.nomA = -1;
    }

    /**
     * Initializes the static values with the ones given.
     *
     * @param      pesos                                        Atribute weights
     * @param      nomAtributs                                  Atribute names
     * @param      tipusAtribut                                 Atribute types
     * @param      id                                           Id position
     * @param      nomA                                         name position
     *
     * @throws     DataNotValidException                        The types where not valid.
     * @throws     ItemIdNotValidException                      posId is already defined or is an invalid column.
     * @throws     ItemStaticValuesAlreadyInitializedException  Values were already initialized.
     * @throws     ItemWeightNotCorrectException                The atribute weights were not valid.
     */
    public static void inicialitzarStatics(ArrayList<Float> pesos, ArrayList<String> nomAtributs, ArrayList<tipus> tipusAtribut, int id, int nomA) throws ItemStaticValuesAlreadyInitializedException, ItemWeightNotCorrectException, ItemIdNotValidException, DataNotValidException {
        Item.setNomAtributs(nomAtributs);

        Item.setPesos(pesos);

        Item.setTipusArray(tipusAtribut);

        Item.setId(id);

        Item.setNomA(nomA);
    }


    /**
     * Initializes the static values automatically given the header of the items.
     *
     * @param      atributs                                     The atributes
     *
     * @throws     DataNotValidException                        The types where not valid.
     * @throws     ItemIdNotValidException                      posId is already defined or is an invalid column.
     * @throws     ItemStaticValuesAlreadyInitializedException  Values were already initialized.
     * @throws     ItemTypeNotValidException                    There was a problem assigning the types.e
     * @throws     ItemWeightNotCorrectException                The atribute weights were not valid.
     */
    public static void inicialitzarStaticsDefault(ArrayList<String> atributs) throws ItemStaticValuesAlreadyInitializedException, ArrayIndexOutOfBoundsException, ItemWeightNotCorrectException, ItemTypeNotValidException, ItemIdNotValidException, DataNotValidException {
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
            if (Item.getHeader().get(i).equalsIgnoreCase("id")) {
                Item.setTipus(i, tipus.I);
                found = true;
            }
        }
        if (!found) throw new ItemTypeNotValidException("No hi ha cap atribut d'Item amb el nom \"id\".");
    }

    /*----- ALTRES -----*/

    /**
     * Function that overrides the compareTo by defalut. Compares 2 items by their id's
     * @param otherItem The other item that compares
     * @return Returns an integer depending on the comparisson between the implicit item and the item given as a parameter <br>
     * 1 if implicit &gt; otherItem <br>
     * 0 if implicit = otherItem <br>
     * -1 if implicit &lt; otherItem <br>
     */
    @Override public int compareTo(Item otherItem) {
        int id1 = getId();
        int id2 = otherItem.getId();
        
        if (id1 == id2) return 0;
        else if (id1 < id2) return -1;
        else return 1;
    }
}
