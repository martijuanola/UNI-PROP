package src.recomanador.domini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import src.recomanador.domini.Utils.Search;
import src.recomanador.domini.Utils.StringOperations;
import src.recomanador.domini.Utils.UnionIntersection;
import src.recomanador.domini.Item.tipus;

import src.recomanador.excepcions.ItemNotCompatibleException;
import src.recomanador.excepcions.ItemNotFoundException;
import src.recomanador.excepcions.ItemTypeNotValidException;

/**
 * This class represents a set of items in the form of an ArrayList extension. 
 * It keeps the items sorted according to the item's ID, 
 * to achieve better performance when modifying this list.
 * @author Jaume
 */
public class ConjuntItems extends ArrayList<Item> {

    /*----- ATRIBUTS -----*/
    /**
     * Nom conjunt d'items
     */
    public static String nom; 
    
    private static ArrayList<Float> maxAtributs;
    private static ArrayList<Float> minAtributs;

    //check
    public ConjuntItems() {}

    //check
    public ConjuntItems(ArrayList<ArrayList<String>> items) throws ItemTypeNotValidException {
        ArrayList<String> nAtributs = items.get(0); //Nom dels atributs (capçalera)
        
        Item.assignarNomAtributs(nAtributs);

        if (!inicialitzar(nAtributs.size())) throw new ItemTypeNotValidException("Items has no column named \"id\"");

        for (int i = 1; i < items.size(); ++i) {
            ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array on es posaran els atributs
            for (int j = 0; j < items.get(i).size(); ++j) { //Recorrem per separar en subvectors
                str.add(StringOperations.divideString(items.get(i).get(j), ';'));
            }
            Item it = new Item(str);
            addIni(it);//Afegeix ordenat
        }
        detectarTipusAtributs();
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            setMinMaxAtribut(i);
        }
    }

    //check
    public ConjuntItems(ArrayList<ArrayList<String>> items, ArrayList<Float> pesos,
    ArrayList<tipus> tipusAtribut, int id, int nomA, String nom, ArrayList<Float> maxAtributs, 
    ArrayList<Float> minAtributs) throws ItemTypeNotValidException {
        ConjuntItems.nom = nom;
        Item.setId(id);
        Item.setNomA(nomA);

        Item.setPesos(pesos);
        Item.setTipus(tipusAtribut);
        Item.assignarNomAtributs(items.get(0));
        
        ConjuntItems.assignarMaxAtributs(maxAtributs);
        ConjuntItems.assignarMinAtributs(minAtributs);

        for (int i = 1; i < items.size(); ++i) {
            ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array on es posaran els atributs
            for (int j = 0; j < items.get(i).size(); ++j) { //Recorrem per separar en subvectors
                str.add(StringOperations.divideString(items.get(i).get(j), ';'));
            }
            Item it = new Item(str);
            add(it);
        }
    }

    //check
    private static void assignarMinAtributs(ArrayList<Float> minAtributs2) {
        ConjuntItems.minAtributs = minAtributs2;
    }

    //check
    private static void assignarMaxAtributs(ArrayList<Float> maxAtributs2) {
        ConjuntItems.maxAtributs = maxAtributs2;
    }

    //check
    private boolean inicialitzar(int nAtributs) throws ItemTypeNotValidException { //Inicialitza amb coses aleatories, no es pot utlitzar fins omplir bé
        Item.setNomA(-1);
        Item.setId(-1);

        ArrayList<Float> pesos = new ArrayList<Float>(nAtributs);
        ArrayList<tipus> tipusAtribut = new ArrayList<tipus>(nAtributs);
        maxAtributs = new ArrayList<Float>();
        minAtributs = new ArrayList<Float>();
        for (int i = 0; i < nAtributs; ++i) {
            maxAtributs.add(Float.MIN_VALUE);
            minAtributs.add(Float.MAX_VALUE);
        }
        
        int i = 0;
        while (i < nAtributs) {
            pesos.add((float) 100.0);
            tipusAtribut.add(i, tipus.S);
            ++i;
        }

        Item.setPesos(pesos);
        Item.setTipus(tipusAtribut);

        int c = 0;
        boolean found = false;
        while (c < nAtributs && !found) {
            if (Item.getNomAtribut(c).equalsIgnoreCase("id")) {
                Item.assignarTipus(c, tipus.I);
                found = true;
            }
            ++c;
        }
        return found;
    }


    //check
    private void assignarTipusItem(int atribut, tipus t) throws ItemTypeNotValidException {
        if (!tipusCorrecteColumna(atribut, t)) throw new ItemTypeNotValidException("Column " + atribut + " does not admit type " + Item.tipusToString(t));
        Item.assignarTipus(atribut, t);
    }

    //check
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
        return !empty || t == tipus.S || t == tipus.N;
    }


    
    //check
    public void eliminarItem(int id) { //Cerca dicotòmica
        int pos = Search.binarySearchItem(this, id, 0, size()-1);
        remove(pos);
    }

    //check
    @Override
    public boolean add(Item i) {
        int pos = Collections.binarySearch(this, i);
        if (pos < 0) pos = ~pos;
        super.add(pos, i);
        esMaxMin(i);
        return (get(pos).getId() == i.getId());
    }

    private boolean addIni(Item i) {
        int pos = Collections.binarySearch(this, i);
        if (pos < 0) pos = ~pos;
        super.add(pos, i);
        return (get(pos).getId() == i.getId());
    }

    private void esMaxMin(Item it) {
        for (int i = 0; i < maxAtributs.size(); ++i) {
            if (it.getAtribut(i).size() == 1) {
                Float nou = (float)0;
                if (Item.getTipus(i) == tipus.I) {
                    nou = (float)Integer.parseInt(it.getAtribut(i).get(0));
                }
                else if (Item.getTipus(i) == tipus.F) {
                    nou = Float.parseFloat(it.getAtribut(i).get(0));
                }
                else if (Item.getTipus(i) == tipus.D) {
                    nou = (float) StringOperations.dataToTime(it.getAtribut(i).get(0));
                }
                if (nou > maxAtributs.get(i)) maxAtributs.set(i, (float)nou);
                if (nou < minAtributs.get(i)) minAtributs.set(i, (float)nou);
            }
        }
    }

    //check
    public Item getItem(int id) throws ItemNotFoundException { //Cerca dicotòmica
        int pos = Search.binarySearchItem(this, id, 0, size()-1);
        if (pos < 0) throw new ItemNotFoundException("Item amb id: " + id + " no existeix");
        return get(pos);
    }

    public ArrayList<ArrayList<ArrayList<String>>> getAllItems() {
        ArrayList<ArrayList<ArrayList<String>>> result = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<String>> aux = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            ArrayList<String> a2 = new ArrayList<String>();
            a2.add(Item.getNomAtribut(i));
            aux.add(a2);
        }
        result.add(aux);
        for (int i = 0; i < this.size(); ++i) {
            result.add(get(i).getAtributs());
        }
        return result;
    }

    //check
    public boolean existeixItem(int id) {
        int res = Search.binarySearchItem(this, id, 0, size()-1);
        return res > -1;
    }

    //check
    public ArrayList<String> getAtributItemId(int id, int i) throws ItemNotFoundException { //Cerca dicotòmica + retornar atribut
        return getItem(id).getAtribut(i);
    }

    //check
    public ArrayList<String> getAtributItem(int posItem, int atribut) { //retornar atribut
        return get(posItem).getAtribut(atribut);
    }
   
    //check
    private static boolean tipusCorrecte(String s, tipus t) {
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

    //check
    public static void assignarNom(String n) {
        ConjuntItems.nom = n;
    }
    
    //check
    public static String getSTipus(int i) {
        tipus t = Item.getTipus(i);
        return Item.tipusToString(t);

    }
    
    //check
    public void printId() {
        for (int i = 0; i < size(); ++i) {
            Item it = get(i);
            String id = "Not An ID";
            id = "" + it.getId();
            System.out.println("ID" + (i + 1) + ": " + id);
        }
    }

    //check
    public void setMinMaxAtribut(int col) {
        tipus t = Item.getTipus(col);
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
            if (col < get(i).getAtributs().size()) {
                ArrayList<String> atr = get(i).getAtribut(col);
                for (int j = 0; j < atr.size(); ++j) {
                    if (StringOperations.compararAtributs(atr.get(j), sMax, t) > 0) {
                        sMax = atr.get(j);
                    }
                    else if (StringOperations.compararAtributs(atr.get(j), sMin, t) < 0) { //atribut és menor que j
                        sMin = atr.get(j);
                    }
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

    //Check
    public void detectarTipusAtributs() throws ItemTypeNotValidException { //Es pot assignar qualsevol tipus menys nom, aquest s'ha d'assignar manualment
        boolean idAssignat = false;
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            boolean found = false;
            String nom = Item.getNomAtribut(i);

            if (nom.equalsIgnoreCase("id")) { //Comprova si es id
                if (tipusCorrecteColumna(i, tipus.I)) {
                    //assignarTipusItem(i, tipus.I); Ja es fa abans
                    found = true;
                    idAssignat = true;
                }
                else throw new ItemTypeNotValidException("columna id no correspon a Identificador");
            }
            if (!found){ //Comprova si es bool
                if (tipusCorrecteColumna(i, tipus.B)) {
                    assignarTipusItem(i, tipus.B);
                    found = true;
                }
            }
            if (!found) { //Comprova si es float/int
                if (tipusCorrecteColumna(i, tipus.F)) {
                    assignarTipusItem(i, tipus.F);
                    found = true;
                }
            }
            if (!found) { //Comprovar si es una data
                if (tipusCorrecteColumna(i, tipus.D)) {
                    assignarTipusItem(i, tipus.D);
                    found = true;
                }
            }
            if (!found) { //Es una string
                assignarTipusItem(i, tipus.S);
                found = true;
            }
        }
        if (!idAssignat) throw new ItemTypeNotValidException("El conjunt de dades no te cap atribut id");
    }

    //check
    private static float distanciaAtribut(String a1, String a2, int columna) throws ItemTypeNotValidException {
        tipus t = Item.getTipus(columna);
        if (!tipusCorrecte(a1, t) || !tipusCorrecte(a2, t)) throw new ItemTypeNotValidException("atribut " + a1 + " o atribut " + a2 + " no son del tipus " + Item.tipusToString(t));

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
            int n= a1.length(), m = a2.length();    
            int[][] dp = new int[n + 1][m + 1];
            for(int i = 0; i < n + 1; i++) Arrays.fill(dp[i], -1);                
            int a = StringOperations.minDis(a1, a2, n, m, dp);
            sim = a/Math.max(n, m);
        }
        return sim;
    }

    public static float distanciaItem(Item i1, Item i2) throws ItemTypeNotValidException, ItemNotCompatibleException {
        if (i1.getNumAtributs() != i2.getNumAtributs()) throw new ItemNotCompatibleException("Different sizes");

        float res = (float)0.0, pesTotal = (float)0.0;
        for (int i = 0; i < i1.getNumAtributs(); ++i) {
            float dist = (float)0.0;
            if (Item.getTipus(i) == tipus.S) {
                float temp;
                temp = UnionIntersection.getIntersection(i1.getAtribut(i), i2.getAtribut(i)).size();
                res = temp / UnionIntersection.getUnion(i1.getAtribut(i), i2.getAtribut(i)).size();
            }
            else {
                dist = distanciaAtribut(i1.getAtribut(0).get(0), i2.getAtribut(i).get(0), i);
            }
            res = dist*(Item.getPes(i)/100);
            pesTotal += Item.getPes(i)/100;
        }
        res = res/pesTotal;
        return res;
    }
    public void printItems() {
        System.out.println("Nom conjunt: " + ConjuntItems.nom);
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            
            System.out.print(Item.getNomAtribut(i) + " " + ConjuntItems.getSTipus(i) + " " + Item.getPes(i));

            if (i != Item.getNumAtributs() - 1) System.out.print(" | ");
        }
        System.out.println("");

        for (int i = 0; i < size(); ++i) {
            get(i).print();
        }
    }

    public Float getMaxAtribut(int i) {
        return maxAtributs.get(i);
    }

    public Float getMinAtribut(int i) {
        return minAtributs.get(i);
    }

    public ArrayList<Float> getMaxAtributs() {
        return maxAtributs;
    }

    public ArrayList<Float> getMinAtributs() {
        return minAtributs;
    }
}
