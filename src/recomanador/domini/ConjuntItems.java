package src.recomanador.domini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import src.recomanador.domini.Utils.*;
import src.recomanador.domini.Item.tipus;

import src.recomanador.excepcions.*;
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

    /*----- CONSTRUCTORS -----*/

    //check
    public ConjuntItems() {}

    //check
    public ConjuntItems(ArrayList<ArrayList<String>> items) throws ItemTypeNotValidException, ItemWeightNotCorrectException {
        //Nom atributs
        ArrayList<String> nAtributs = items.get(0); //Nom dels atributs (capçalera)
        Item.setNomAtributs(nAtributs);

        //Pesos default(100.0) + tipus default(String) + id
        if (!inicialitzar(nAtributs.size())) throw new ItemTypeNotValidException("Items has no column named \"id\"");

        for (int i = 1; i < items.size(); ++i) {
            ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array on es posaran els atributs
            for (int j = 0; j < items.get(i).size(); ++j) { //Recorrem per separar en subvectors
                str.add(StringOperations.divideString(items.get(i).get(j), ';'));
            }
            try {
                Item it = new Item(str);
                addIni(it);//Afegeix ordenat
            }
            catch(ItemStaticValuesNotInitializedException e) {
                System.out.println("No s'han inicialitzat bé les variables estàtiques d'Item!!!\n" + e.getMessage());
            }
            catch(ItemNewAtributesNotValidException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        detectarTipusAtributs();
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            computeMinMaxAtribut(i);
        }
    }

    public ConjuntItems(ArrayList<ArrayList<String>> items, ArrayList<Float> pesos,
    ArrayList<tipus> tipusAtribut, int id, int nomA, String nom, ArrayList<Float> maxAtributs, 
    ArrayList<Float> minAtributs) throws ItemTypeNotValidException, ItemWeightNotCorrectException {
        ConjuntItems.nom = nom;
        Item.setId(id);
        Item.setNomA(nomA);

        Item.setNomAtributs(items.get(0));
        Item.setPesos(pesos);
        Item.setTipus(tipusAtribut);

        ConjuntItems.setMaxAtributs(maxAtributs);
        ConjuntItems.setMinAtributs(minAtributs);

        for (int i = 1; i < items.size(); ++i) {
            ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array on es posaran els atributs
            for (int j = 0; j < items.get(i).size(); ++j) { //Recorrem per separar en subvectors
                str.add(StringOperations.divideString(items.get(i).get(j), ';'));
            }
            try {
                Item it = new Item(str);
                addIni(it);
            }
            catch(ItemStaticValuesNotInitializedException e) {
                System.out.println("No s'han inicialitzat bé les variables estàtiques d'Item!!!\n" + e.getMessage());
            }
            catch(ItemNewAtributesNotValidException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    /*----- GETTERS -----*/

    public Item getItem(int id) throws ItemNotFoundException { //Cerca dicotòmica
        int pos = binarySearchItem(this, id, 0, size()-1);
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

    public ArrayList<String> getAtributItemId(int id, int i) throws ItemNotFoundException { //Cerca dicotòmica + retornar atribut
        return getItem(id).getAtribut(i);
    }

    public ArrayList<String> getAtributItem(int posItem, int atribut) { //retornar atribut
        return get(posItem).getAtribut(atribut);
    }
    
    public static String getSTipus(int i) {
    tipus t = Item.getTipus(i);
    return StringOperations.tipusToString(t);

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

    /*----- SETTERS -----*/

    public static void setMinAtributs(ArrayList<Float> minAtributs2) {
        ConjuntItems.minAtributs = minAtributs2;
    }

    public static void setMaxAtributs(ArrayList<Float> maxAtributs2) {
        ConjuntItems.maxAtributs = maxAtributs2;
    }
    //TODO: setTipusItem hauria de ser private
    public void setTipusItem(int atribut, tipus t) throws ItemTypeNotValidException {
        if (!tipusCorrecteColumna(atribut, t)) throw new ItemTypeNotValidException("Column " + atribut + " does not admit type " + StringOperations.tipusToString(t));
        Item.assignarTipus(atribut, t);
    }

    public static void setNom(String n) {
        ConjuntItems.nom = n;
    }
    
    public static void setPesos(ArrayList<Float> p) throws ArrayIndexOutOfBoundsException, ItemWeightNotCorrectException {
        Item.setPesos(p);
    }

    public static void setPes(float p, int col) throws ArrayIndexOutOfBoundsException, ItemWeightNotCorrectException {
        Item.setPes(col, p);
    }

    /*----- CHECKERS -----*/

    //TODO: tipusCorrecteColumna hauria de ser private
    public boolean tipusCorrecteColumna(int columna, tipus t) {
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
    
    public boolean existeixItem(int id) {
        int res = binarySearchItem(this, id, 0, size()-1);
        return res > -1;
    }
    //TODO: tipusCorrecte hauria de ser private
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
    public void checkMaxMin(Item it) {
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

    /*----- ADD/DELETE -----*/

    public void eliminarItem(int id) throws ItemNotFoundException { //Cerca dicotòmica
        int pos = binarySearchItem(this, id, 0, size()-1);
        if (pos < 0) throw new ItemNotFoundException("Item with id " + id + " does not exist");
        remove(pos);
        for (int i = 0; i < Item.getNumAtributs(); ++i) computeMinMaxAtribut(i);
    }
    @Override
    public boolean add(Item i) {
        int pos = Collections.binarySearch(this, i);
        if (pos < 0) pos = ~pos;
        super.add(pos, i);
        checkMaxMin(i);
        return (get(pos).getId() == i.getId());
    }
    //TODO: addIni hauria de ser private
    public boolean addIni(Item i) {
        int pos = Collections.binarySearch(this, i);
        if (pos < 0) pos = ~pos;
        super.add(pos, i);
        return (get(pos).getId() == i.getId());
    }

    /*----- COMPUTATIONS -----*/

    public void computeMinMaxAtribut(int col) {
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
    public boolean inicialitzar(int nAtributs) throws ItemTypeNotValidException, ItemWeightNotCorrectException { //Inicialitza amb coses aleatories, no es pot utlitzar fins omplir bé
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
                    setTipusItem(i, tipus.B);
                    found = true;
                }
            }
            if (!found) { //Comprova si es float/int
                if (tipusCorrecteColumna(i, tipus.F)) {
                    setTipusItem(i, tipus.F);
                    found = true;
                }
            }
            if (!found) { //Comprovar si es una data
                if (tipusCorrecteColumna(i, tipus.D)) {
                    setTipusItem(i, tipus.D);
                    found = true;
                }
            }
            if (!found) { //Es una string
                setTipusItem(i, tipus.S);
                found = true;
            }
        }
        if (!idAssignat) throw new ItemTypeNotValidException("El conjunt de dades no te cap atribut id");
    }
    //TODO: distanciaAtribut hauria de ser private
    public static float distanciaAtribut(String a1, String a2, int columna) throws ItemTypeNotValidException {
        tipus t = Item.getTipus(columna);
        if (!tipusCorrecte(a1, t) || !tipusCorrecte(a2, t)) throw new ItemTypeNotValidException("atribut " + a1 + " o atribut " + a2 + " no son del tipus " + StringOperations.tipusToString(t));

        if (a1.equals(a2)) return (float)1.0;

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

    public static float distanciaItem(Item i1, Item i2) throws ItemTypeNotValidException {

        float res = (float)0.0, pesTotal = (float)0.0;
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            float dist = (float)0.0;
            //Els casos on es comparen tags i només hi ha una string, es compara com si fossin conjunts, i es incorrecte
            if (Item.getTipus(i) == tipus.S && (i1.getAtribut(i).size() > 1 || i2.getAtribut(i).size() > 1)) {
                float temp;
                temp = UnionIntersection.getIntersection(i1.getAtribut(i), i2.getAtribut(i)).size();
                dist = temp / UnionIntersection.getUnion(i1.getAtribut(i), i2.getAtribut(i)).size();
            }
            else {
                dist = distanciaAtribut(i1.getAtribut(i).get(0), i2.getAtribut(i).get(0), i);
            }
            res += dist*(Item.getPes(i)/((float)100.0));
            pesTotal += Item.getPes(i)/((float)100.0);
        }
        res = ((float)1.0*res)/pesTotal;
        return res;
    }
    
    public static <T extends Comparable<T>> int binarySearchItem(ArrayList<Item> list, int id, int lo, int hi) {
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
