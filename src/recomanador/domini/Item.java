package src.recomanador.domini;

import java.util.ArrayList;

import src.recomanador.excepcions.*;

/**
 * Classe Item
 * @author Jaume
 */
public class Item implements Comparable<Item>{

    /*----- ATRIBUTS STATICS -----*/
    
    public static enum tipus {
        I, //ID
        N, //Nom
        B, //Boolean
        F, //Float
        S, //String
        D //Date
    }

    //default value = null
    private static ArrayList<Float> pesos;
    private static ArrayList<tipus> tipusAtribut; //I i N haurien de ser únics en els atributs
    private static ArrayList<String> nomAtribut;

    private static int id = -1; //posició del atribut id al tipus atribut
    
    /**
     * Index of the atribute that contains the name of the item
     * -1 if the index doesn't exist
     */
    private static int nomA = -1;


    /*----- ATRIBUTS -----*/

    private ArrayList<ArrayList<String>> atributs; //Vector on cada atribut té un vector. Pot ser buit


    /*----- CONSTRUCTORS -----*/

    /**
     * Constructor amb només l'atribut id. Inicialitza tots els valors estàtics.
     * @param id Identificador de l'Item
     */
    public Item(int idval) throws ItemStaticValuesNotInitializedException {
        if(pesos == null) throw new ItemStaticValuesNotInitializedException("pesos");
        if(tipusAtribut == null) throw new ItemStaticValuesNotInitializedException("tipusAtribut");
        if(nomAtribut == null) throw new ItemStaticValuesNotInitializedException("nomAtribut");
        if(id == -1) throw new ItemStaticValuesNotInitializedException("id");

        this.atributs = new ArrayList<ArrayList<String>>(0);
        for(int i = 0; i < Item.pesos.size(); i++) {
            ArrayList<String> default_val = new ArrayList<String>(0);
            if(i != Item.id) default_val.add("");
            else default_val.add(""+idval);
            this.atributs.add(default_val);
        }
    }

    /**
     * Es crea l'item i s'assigna els atributs a l'item
     * @param atributs llista d'atributs d'items on cada atribut pot tenir diversos atributs
     */
    public Item(ArrayList<ArrayList<String>> atributsval) throws ItemStaticValuesNotInitializedException, ItemNewAtributesNotValidException {
        if(pesos == null) throw new ItemStaticValuesNotInitializedException("pesos");
        if(tipusAtribut == null) throw new ItemStaticValuesNotInitializedException("tipusAtribut");
        if(nomAtribut == null) throw new ItemStaticValuesNotInitializedException("nomAtribut");
        if(id == -1) throw new ItemStaticValuesNotInitializedException("id");

        if(atributsval.size() != Item.pesos.size()) throw new ItemNewAtributesNotValidException(Item.pesos.size());

        this.atributs = atributsval;
    }   

    /*----- GETTERS -----*/

    /**
     * Retorna l'atribut de la posició id de ConjuntItems
     * @return atribut id en forma de int
     */
    public int getId() {
        return Integer.parseInt(atributs.get(Item.id).get(0));
    }


    //S'HA DE TREURE!!
    /**
     * Retorna l'atribut de la posició i
     * @param i index del atribut: entro 0 i el nombre d'atributs
     * @return llista dels sub atributs
     */
    public ArrayList<String> getAtribut(int i) {
        return atributs.get(i);
    }

    /**
     * Retorna tots els atributs
     * @return atributs en forma de llista de llistes d'atributs
     */
    public ArrayList<ArrayList<String>> getAtributs() {
        return atributs;
    }

    /*----- SETTERS -----*/


    /*----- STATICS GETTERS -----*/

    public static int getPosId() {
        return Item.id;
    }

    public static int getPosNomA() {
        return Item.nomA;
    }

    /**
     * Serveix més per comprovar que per funcionalitat
     * @return retorna el nombre d'atributs que té l'item
     */
    public static int getNumAtributs() {
        return Item.nomAtribut.size();
    }

    //S'HA DE TREURE!!
    public static Float getPes(int i) {
        return Item.pesos.get(i);
    }

    //S'HA DE TREURE!!
    public static tipus getTipus(int i) {
        return Item.tipusAtribut.get(i);
    }

    //S'HA DE TREURE!!
    public static String getNomAtribut(int i) {
        return Item.nomAtribut.get(i);
    }

    public static ArrayList<Float> getPesos() {
        return Item.pesos;
    }

    public static ArrayList<tipus> getTipusArray() {
        return Item.tipusAtribut;
    }

    public static ArrayList<String> getCapçalera() {
        return Item.nomAtribut;
    }


    /*----- STATICS SETTERS -----*/

    public static void setId(int id) {
        Item.id= id;
    }

    public static void setNomA(int a) {
        Item.nomA = a;
    }

    //S'HA DE TREURE!!
    public static void setPes(int a, float pes) throws ItemWeightNotCorrectException, ArrayIndexOutOfBoundsException {
        if (pes < 0.0) throw new ItemWeightNotCorrectException("Weight smaller than 0");
        else if (pes > 100.0) throw new ItemWeightNotCorrectException("Weight bigger than 100");
        if (a < 0 || a >= Item.pesos.size()) throw new ArrayIndexOutOfBoundsException("index " + a + " out of bounds for array of size " + pesos.size());
        else Item.pesos.set(a, pes);
    }

    //S'HA DE TREURE!!
    //ha de venir comprovat a assignaTipusItem de conjunt d'items
    public static void setTipus(int atribut, tipus t) throws ItemTypeNotValidException {
        Item.tipusAtribut.set(atribut, t);
        Item.canvisTipusAtribut(atribut, t);
    }

    public static void setPesos(ArrayList<Float> p) throws ItemWeightNotCorrectException, ArrayIndexOutOfBoundsException{
        if (p.size() != Item.getNumAtributs()) throw new ArrayIndexOutOfBoundsException("Weights array does not match items attributes.");
        for (int i = 0; i < Item.getNumAtributs(); ++i) if (p.get(i) > 100 || p.get(i) < 0) throw new ItemWeightNotCorrectException("Weight smaller than 0 or bigger than 100");
        Item.pesos = p;
    }

    public static void setTipusArray(ArrayList<tipus> a) {
        Item.tipusAtribut = a;
    }

    private static void canvisTipusAtribut(int atribut, tipus t) throws ItemTypeNotValidException {
        if (t == tipus.I) {
            if (id != -1) throw new ItemTypeNotValidException("L'item ja tenia un id assignat, no es poden tenir dos ids.");
            Item.id = atribut;
        }
        else if (t == tipus.N) {
            if (nomA != -1) { //Canviem el nom antic per evitar tenir 2 noms
                Item.tipusAtribut.set(nomA, tipus.S);
            }
           Item.nomA = atribut;
        }
    }

    public static void setNomAtributs(ArrayList<String> n) {
        Item.nomAtribut = n;
    }
    

    /*----- ALTRES -----*/

    /**
     * Funció que fa override al compareTo per defecte, compara 2 items per els seus id's
     * @param otherItem L'altre item que compara
     * @return Retorna un nombre depenent de la comparació entre l'item al que es fa la crida de la funció i el paràmetre
     * 1 si implícit > otherItem, 
     * 0 si implícit = otherItem, 
     * -1 si implícit < otherItem
     */
    @Override public int compareTo(Item otherItem) {
        int id1 = getId();
        int id2 = otherItem.getId();
        
        if (id1 == id2) return 0;
        else if (id1 < id2) return -1;
        else return 1;
    }

    public static String tipusToString(tipus t) {
        String s = "";
        switch (t) {
            case I:
                s = "Identificador";
                break;
            case N:
                s = "Nom";
                break;
            case B:
                s = "Boolean";
                break;
            case F:
                s = "Float";
                break;
            case S:
                s = "String";
                break;
            case D:
                s = "Data";
                break;
            default:
                s = "No assignat";
                break;
            }
        return s;
    }    
  
}
