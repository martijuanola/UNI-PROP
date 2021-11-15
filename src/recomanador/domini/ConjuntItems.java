package src.recomanador.domini;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import src.recomanador.Utils;
import src.recomanador.excepcions.ItemIdNotValidException;
import src.recomanador.excepcions.ItemNotDefinedException;
import src.recomanador.excepcions.ItemNotFoundException;

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

    public ConjuntItems(Collection<? extends Item> c) {
        super(c);
    }

    public ConjuntItems() {
    }

    public ConjuntItems(int size) {
        super(size);
    }

    public ConjuntItems(ArrayList<ArrayList<String>> items) {
        ArrayList<String> nAtributs = items.get(0); //Nom dels atributs (capçalera)
        inicialitzar(nAtributs.size());
        ConjuntItems.assignarNomAtributs(nAtributs);

        if (items.size() > 0) {
            for (int i = 1; i < items.size(); ++i) {
                ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array on es posaran els atributs
                for (int j = 0; j < items.get(i).size(); ++j) { //Recorrem per separar en subvectors
                    str.add(new ArrayList<String>()); //Array del atribut j del item i
                    String s = items.get(i).get(j); //Atribut abans de separar
                    int ini = 0; //Últim ';' trobat

                    String c = "";
                    for (int k = 0; k < s.length(); ++k) { //Eliminem totes les instàncies de ", perque no són últis
                        if (s.charAt(k) != '\"')
                            c = c + s.charAt(k);
                    }
                    s = c;

                    for (int k = 0; k < s.length(); ++k) {
                        if (s.charAt(k) == ';') {
                            String aux = s.substring(ini, k);
                            ini = k + 1;
                            str.get(j).add(aux); //S'afegeix la part del atribut
                        }
                    }
                    //Paraula final o cas que no hi ha substring
                    String aux = s.substring(ini, s.length());
                    str.get(j).add(aux);
                }
                Item it = new Item(str);
                add(it);
            }
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
            nomAtribut.add("holi" + i);
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

    public Item getItem(int id) throws ItemNotFoundException, ItemIdNotValidException { //Cerca dicotòmica
        return cercaBinaria(id, 0, size());
    }

    public ArrayList<String> getAtributItemId(int id, int i) throws ItemNotFoundException { //Cerca dicotòmica + retornar atribut
        return getItem(id).getAtribut(i);
    }

    public ArrayList<String> getAtributItem(int index, int i) { //retornar atribut
        return get(index).getAtribut(i);
    }

    private Item cercaBinaria(int id, int left, int right) throws ItemNotFoundException{
        int l = left, r = right, mid = 0;
        boolean end = false;
        while (!end) {
            if (r > l) {
                throw new ItemNotFoundException("id no existeix: " + id);
            }

            mid = l + (r - l) / 2;
            int s = getId(mid);

            if (s < id) { //mid < id
                l = mid + 1;
            }
            if (s > id) { //mid > id
                r = mid - 1;
            } else {
                end = true;
            }
        }
        return get(mid);
    }

    private int getId(int m) throws ItemIdNotValidException {
        Item i = get(m);
        return i.getId();
    }

    static public void assignarPes(int a, float pes) {
        ConjuntItems.pesos.set(a, pes);
    }

    public void assignarTipus(int a, tipus c) {
        ConjuntItems.tipusAtribut.set(a, c);
        if (c == tipus.I) {
            if (id != -1) { //Canviem l'id antic per evitar tenir 2 id
                ConjuntItems.tipusAtribut.set(id, tipus.S);
            }
            id = a;
            Collections.sort(this);
        } else if (c == tipus.N) {
            if (nomA != -1) { //Canviem el nom antic per evitar tenir 2 noms
                ConjuntItems.tipusAtribut.set(nomA, tipus.S);
            }
            nomA = a;
        }
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

    static public Float getPes(int i) {
        return pesos.get(i);
    }

    static public int getNumAtributs() {
        return pesos.size();
    }

    static public String getSTipus(int i) {
        String s = "";
        tipus t = tipusAtribut.get(i);

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

    public void printId() throws ItemIdNotValidException {
        for (int i = 0; i < size(); ++i) {
            Item it = get(i);
            String id = "NOT HERE PAL";
            id = "" + it.getId();
            System.out.println("ID" + (i + 1) + ": " + id);
        }
    }

    public void detectarTipusAtributs() { //Es pot assignar qualsevol tipus menys nom, aquest s'ha d'assignar manualment
        for (int i = 0; i < nomAtribut.size(); ++i) {
            double minimumPercent = 40.0; //Mínim percentatge d'aparacions per considerar que un atribut és d'un tipus
            int minimumItems = size()/5; //Items mínims que ha de comprovar per decidir si es correcte o no
            boolean isBool = false, isID = false, isFloat = false, isDate = false, isString = false, found = false;
            String nom = nomAtribut.get(i);

            System.out.println("atribut " + nom);
            if (nom.equalsIgnoreCase("id")) { //Comprova si es id
                isID = true;
                found = true;
            }
            if (!found){ //Comprova si es bool
                int it = 0, count = 0;
                while (!isBool && it < size()) {
                    ArrayList<String> c = get(it).getAtribut(i);
                    if (c.size() == 1 && Utils.esBool(c.get(0))) {
                        ++count;
                    }
                    if (it > minimumItems) {
                        if ((count*1.0/it*1.0)*100 > minimumPercent) {
                            isBool = true;
                            found = true;
                        }
                    }
                    ++it;
                }
            }
            if (!found) { //Comprova si es float/int
                int it = 0, count = 0;
                while (!isFloat && it < size()) {
                    ArrayList<String> c = get(it).getAtribut(i);
                    if (c.size() == 1) {
                        if (Utils.esFloat(c.get(0))) ++count;
                    }
                    if (it > minimumItems) {
                        if ((count*1.0/it*1.0)*100 > minimumPercent) {
                            isFloat = true;
                            found = true;
                        }
                    }
                    ++it;
                }
            }
            if (!found) { //Comprovar si es una data
                int it = 0, count = 0;
                while (!isDate && it < size()) {
                    ArrayList<String> c = get(it).getAtribut(i);
                    if (c.size() == 1) {
                        String date = c.get(0);
                        if (Utils.esData(date)) ++count;
                    }
                    if (it > minimumItems) {
                        if ((count*1.0/it*1.0)*100 > minimumPercent) {
                            isDate = true;
                            found = true;
                        }
                    }
                    ++it;
                }
            }
            if (!found) { //Es una string
                isString = true;
                found = true;
            }

            if (isID) assignarTipus(i, tipus.I);
            else if (isBool)assignarTipus(i, tipus.B);
            else if (isFloat) assignarTipus(i, tipus.F);
            else if (isDate) assignarTipus(i, tipus.D);
            else if (isString) assignarTipus(i, tipus.S);
        }
    }
}
