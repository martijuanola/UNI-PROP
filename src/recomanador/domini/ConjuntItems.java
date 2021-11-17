package src.recomanador.domini;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import src.recomanador.domini.Utils.Search;
import src.recomanador.domini.Utils.StringOperations;
import src.recomanador.excepcions.ItemNotFoundException;
import src.recomanador.excepcions.ItemTypeNotValidException;
import src.recomanador.excepcions.ItemWeightNotCorrectException;

public class ConjuntItems extends ArrayList<Item> {

    public enum tipus {
        I, //ID
        N, //Nom
        B, //Boolean
        F, //Float
        S, //String
        D //Date
    }

    public static String nom; //Nom del conjunt d'items
    public static ArrayList<Float> pesos;
    public static ArrayList<tipus> tipusAtribut; //I i N haurien de ser únics en els atributs
    public static ArrayList<String> nomAtribut;

    static int id; //posició del atribut id al tipus atribut
    static int nomA; //posició del atribut nom al al tipus atrbut. -1 si no en té

    private ArrayList<Float> maxAtributs;
    private ArrayList<Float> minAtributs;

    public ConjuntItems(Collection<? extends Item> c) {
        super(c);
    }

    public ConjuntItems() {
    }

    public ConjuntItems(int size) {
        super(size);
    }

    public ConjuntItems(ArrayList<ArrayList<String>> items) throws ItemTypeNotValidException {
        ArrayList<String> nAtributs = items.get(0); //Nom dels atributs (capçalera)
        inicialitzar(nAtributs.size());
        ConjuntItems.assignarNomAtributs(nAtributs);

        for (int i = 1; i < items.size(); ++i) {
            ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array on es posaran els atributs
            for (int j = 0; j < items.get(i).size(); ++j) { //Recorrem per separar en subvectors
                str.add(StringOperations.divideString(items.get(i).get(j), ';'));
            }
            Item it = new Item(str);
            add(it);
        }
        detectarTipusAtributs();
    }


    static private void inicialitzar(int nAtributs) { //Inicialitza amb coses aleatories, no es pot utlitzar fins omplir bé
        id = -1;
        nomA = -1;
        pesos = new ArrayList<Float>(nAtributs);
        tipusAtribut = new ArrayList<tipus>(nAtributs);
        nomAtribut = new ArrayList<String>(nAtributs);
        int i = 0;
        while (i < nAtributs) {
            pesos.add((float) 100.0);
            tipusAtribut.add(i, tipus.S);
            nomAtribut.add("Not A Name" + i);
            ++i;
        }
    }

    public void printItems() {
        System.out.println("Nom conjunt: " + ConjuntItems.nom);
        for (int i = 0; i < ConjuntItems.getNumAtributs(); ++i) {
            System.out.print(
                    ConjuntItems.getNomAtribut(i) + " " + ConjuntItems.getSTipus(i) + " " + ConjuntItems.getPes(i));
            if (i != ConjuntItems.getNumAtributs() - 1)
                System.out.print(" | ");
        }
        System.out.println("");

        for (int i = 0; i < size(); ++i) {
            get(i).print();
        }
    }

    public Item getItem(int id) throws ItemNotFoundException { //Cerca dicotòmica
        int pos = Search.binarySearchItem(this, id, 0, size()-1);
        return get(pos);
    }

    public boolean existeixItem(int id) {
        return Search.binarySearchItem(this, id, 0, size()-1) > -1;
    }

    public ArrayList<String> getAtributItemId(int id, int i) throws ItemNotFoundException { //Cerca dicotòmica + retornar atribut
        return getItem(id).getAtribut(i);
    }

    public ArrayList<String> getAtributItem(int posItem, int atribut) { //retornar atribut
        return get(posItem).getAtribut(atribut);
    }

    static public void assignarPes(int a, float pes) throws ItemWeightNotCorrectException, ArrayIndexOutOfBoundsException {
        if (pes < 0.0) throw new ItemWeightNotCorrectException("Weight smaller than 0");
        else if (pes > 100.0) throw new ItemWeightNotCorrectException("Weight bigger than 100");
        if (a < 0 || a >= pesos.size()) throw new ArrayIndexOutOfBoundsException("index " + a + " out of bounds for array of size " + pesos.size());
        else ConjuntItems.pesos.set(a, pes);
    }

    public void assignarTipus(int atribut, tipus t) throws ItemTypeNotValidException {
        if (!tipusCorrecteColumna(atribut, t)) throw new ItemTypeNotValidException("Column " + atribut + " does not admit type " + tipusToString(t));
        ConjuntItems.tipusAtribut.set(atribut, t);
        canvisTipusAtribut(atribut, t);
    }

    private void canvisTipusAtribut(int atribut, tipus t) {
        if (t == tipus.I) {
            if (id != -1) { //Canviem l'id antic per evitar tenir 2 id
                ConjuntItems.tipusAtribut.set(id, tipus.S);
            }
            id = atribut;
            Collections.sort(this);
        } else if (t == tipus.N) {
            if (nomA != -1) { //Canviem el nom antic per evitar tenir 2 noms
                ConjuntItems.tipusAtribut.set(nomA, tipus.S);
            }
            nomA = atribut;
        }
    }

    private boolean tipusCorrecteColumna(int columna, tipus t) {
        boolean empty = true;
        if (t == tipus.I) {
            for (int i = 0; i < size(); ++i) {
                ArrayList<String> id = getAtributItem(i, columna);
                if (id.size() > 1) return false;
                if (!id.get(0).equals("") && !id.get(0).equals(" ")){
                    empty = false;
                    if (!StringOperations.esNombre(id.get(0))) return false;
                }
            }
        }
        else if (t == tipus.B) {
            for (int i = 0; i < size(); ++i) {
                ArrayList<String> id = getAtributItem(i, columna);
                if (id.size() > 1) return false;
                if (!id.get(0).equals("") && !id.get(0).equals(" ")){
                    empty = false;
                    if (!StringOperations.esBool(id.get(0))) return false;
                }
            }
        }
        else if (t == tipus.F) {
            for (int i = 0; i < size(); ++i) {
                ArrayList<String> id = getAtributItem(i, columna);
                if (id.size() > 1) return false;
                if (!id.get(0).equals("") && !id.get(0).equals(" ")){
                    empty = false;
                    if (!StringOperations.esFloat(id.get(0))){
                        System.out.println("columna "+getNomAtribut(columna)+" no es float:"+id.get(0));
                        return false;
                    } 
                }
            }
        }
        else if (t == tipus.D) {
            for (int i = 0; i < size(); ++i) {
                ArrayList<String> id = getAtributItem(i, columna);
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

    private boolean tipusCorrecte(String s, tipus t) {
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

    static public void assignarNomAtributs(ArrayList<String> n) {
        ConjuntItems.nomAtribut = n;
    }

    static public void assignarNom(String n) {
        ConjuntItems.nom = n;
    }

    static public String getNomAtribut(int i) {
        return nomAtribut.get(i);
    }

    static public ArrayList<String> getCapçalera() {
        return nomAtribut;
    }

    static public ArrayList<Float> getPesos() {
        return pesos;
    }

    static public ArrayList<tipus> getTipus() {
        return tipusAtribut;
    }

    static public Float getPes(int i) {
        return pesos.get(i);
    }

    static public int getNumAtributs() {
        return nomAtribut.size();
    }

    static public String getSTipus(int i) {
        tipus t = tipusAtribut.get(i);
        return tipusToString(t);

    }

    static public String tipusToString(tipus t) {
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

    static public tipus getTipus(int i) {
        return tipusAtribut.get(i);
    }

    public void printId() {
        for (int i = 0; i < size(); ++i) {
            Item it = get(i);
            String id = "Not An ID";
            id = "" + it.getId();
            System.out.println("ID" + (i + 1) + ": " + id);
        }
    }

    public String getMinMaxAtribut(int col, boolean Max) { //If max == true, agafes max, else agafes min
        tipus t = getTipus(col);
        String s = "";
        if (Max) {
            if (t == tipus.B) s = "True";
            if (t == tipus.S) s = "";
            if (t == tipus.N) s = "";
            if (t == tipus.I) s = ""+Integer.MIN_VALUE;
            if (t == tipus.D) s = "0000-00-00";
            if (t == tipus.F) s = ""+Float.MIN_VALUE; 
        }
        else {
            if (t == tipus.B) s = "True";
            if (t == tipus.S) s = StringOperations.infinitString();
            if (t == tipus.N) s = StringOperations.infinitString();
            if (t == tipus.I) s = ""+Integer.MAX_VALUE;
            if (t == tipus.D) s = "9999-12-31";
            if (t == tipus.F) s = ""+Float.MAX_VALUE;
        }

        for (int i = 0; i < size(); ++i) {
            if (col < get(i).getAtributs().size()) {
                ArrayList<String> atr = get(i).getAtribut(col);
                for (int j = 0; j < atr.size(); ++j) {
                    if (Max && StringOperations.compararAtributs(atr.get(j), s, t) > 0) {
                        s = atr.get(j);
                    }
                    else if (!Max && StringOperations.compararAtributs(atr.get(j), s, t) < 0) { //atribut és menor que j
                        s = atr.get(j);
                    }
                }
            }
        }
        if (!s.equals(StringOperations.infinitString()))return s;
        else return "BAD DETECTION";
    }

    public void detectarTipusAtributs() throws ItemTypeNotValidException { //Es pot assignar qualsevol tipus menys nom, aquest s'ha d'assignar manualment
        boolean idAssignat = false;
        for (int i = 0; i < nomAtribut.size(); ++i) {
            boolean found = false;
            String nom = nomAtribut.get(i);

            if (nom.equalsIgnoreCase("id")) { //Comprova si es id
                if (tipusCorrecteColumna(i, tipus.I)) {
                    tipusAtribut.set(i, tipus.I);
                    canvisTipusAtribut(i, tipus.I);
                    found = true;
                    idAssignat = true;
                }
                else throw new ItemTypeNotValidException("columna id no correspon a Identificador");
            }
            if (!found){ //Comprova si es bool
                if (tipusCorrecteColumna(i, tipus.B)) {
                    tipusAtribut.set(i, tipus.B);
                    found = true;
                }
            }
            if (!found) { //Comprova si es float/int
                if (tipusCorrecteColumna(i, tipus.F)) {
                    tipusAtribut.set(i, tipus.F);
                    found = true;
                }
            }
            if (!found) { //Comprovar si es una data
                if (tipusCorrecteColumna(i, tipus.D)) {
                    tipusAtribut.set(i, tipus.D);
                    found = true;
                }
            }
            if (!found) { //Es una string
                tipusAtribut.set(i, tipus.S);
                found = true;
            }
        }
        if (!idAssignat) throw new ItemTypeNotValidException("El conjunt de dades no te cap atribut id");
    }

    private float distanciaAtribut(String a1, String a2, int columna) throws ItemTypeNotValidException {
        tipus t = getTipus(columna);
        if (!tipusCorrecte(a1, t) || !tipusCorrecte(a2, t)) throw new ItemTypeNotValidException("atribut " + a1 + " o atribut " + a2 + " no son del tipus " + tipusToString(t));

        float sim = (float)0.0;
        if (t == tipus.I) {
            int i1 = Integer.parseInt(a1), i2 = Integer.parseInt(a2);
            sim = 1 - (Math.abs(i1 - i2) / (maxAtributs.get(columna) - minAtributs.get(columna)));
        }
        else if (t == tipus.B) {
            boolean b1 = Boolean.parseBoolean(a1), b2 = Boolean.parseBoolean(a2);
            if (b1 == b2) sim = (float)1.0;
            else sim = (float)0.0;
        }
        else if (t == tipus.D) {
            float dataMax = maxAtributs.get(columna), dataMin = minAtributs.get(columna); //TODO: canviar que es guarden les dates
            sim = 1 - (Math.abs(StringOperations.dataToTime(a1) - StringOperations.dataToTime(a2)) / (dataMax - dataMin));
        }
        else if (t == tipus.F) {
            float i1 = Float.parseFloat(a1), i2 = Float.parseFloat(a2);
            sim = 1 - (Math.abs(i1 - i2) / (maxAtributs.get(columna) - minAtributs.get(columna)));
        }
        else if (t == tipus.S || t == tipus.N) {
            //size(inteseccio)/size(unio)
        }
        return sim;
    }
}
    //K-NN
        //volem que ens doni 0-1
            //(string, string) = 1-edit_distance/max(size)
            //(int, int) = 1-(abs((a-b))/(max(atribut)-min(atribut)))
            //(bool, bool) = (bool == bool)
            //(Array, Array) = size(interseccio)/size(unio)
            //(Data, Data) =.....
            // (0..1p, 0..1*p)/(1*p+1*p) = 0..1*(vo-2.5)