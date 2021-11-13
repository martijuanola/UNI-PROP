package src.recomanador.domini;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import src.exceptions.NotDefinedException;
import src.exceptions.NotFoundException;

public class ConjuntItems extends ArrayList<Item> {

    public enum tipus
    { //En ordre: ID, Nom, bool, float, string 
        I, N, B, F, S
    }
    public static String nom; //Nom del conjunt d'items
    public static ArrayList<Float> pesos;
    public static ArrayList<tipus> tipusAtribut; //I i N haurien de ser únics en els atributs
    public static ArrayList<String> nomAtribut;

    static int id; //posició del atribut id al tipus atribut
    static private int nomA; //posició del atribut nom al al tipus atrbut. -1 si no en té

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
        ConjuntItems.assignarNomAtributs(nAtributs);
        inicialitzar(nAtributs.size());

        if (items.size() > 0) {
            for (int i = 1; i < items.size(); ++i) {
                ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array d'atributs
                for (int j = 0; j < items.get(i).size(); ++j) { //Recorrem per separar en subvectors
                    str.add(new ArrayList<String>());
                    String s = items.get(i).get(j); //String actual
                    int ini = 0; //Últim ';' trobat
                    for (int k = 0; k < s.length(); ++k) {
                        if (s.charAt(k) == ';') {
                            String aux = s.substring(ini, k);
                            ini = k + 1;
                            if (!s.equals("")) str.get(j).add(aux); //Les paraules buides no s'afegeixen
                        }
                    }
                    if (s.length() > 0 && s.charAt(s.length()-1) != ';' && ini != s.length()-1) { //Paraula final o cas que no hi ha substring
                        String aux = s.substring(ini, s.length()-1);
                        str.get(j).add(aux);
                    }
                }
                Item it = new Item(str);
                add(it);
            }
        }
    }

    static private void inicialitzar(int nAtributs) { //Inicialitza amb coses aleatories, no es pot utlitzar fins omplir bé
        pesos = new ArrayList<Float>(nAtributs);
        tipusAtribut = new ArrayList<tipus>(nAtributs);
        nomAtribut = new ArrayList<String>(nAtributs);
        int i = 0;
        while (i < nAtributs) {
            pesos.add((float)100.0);
            tipusAtribut.add(i, tipus.S);
            nomAtribut.add("holi"+i);
            ++i;
        }
    }

    public void printItems() {
        System.out.println("Nom conjunt: " + ConjuntItems.nom);
        for(int i = 0; i < ConjuntItems.getNumAtributs(); ++i) {
            System.out.print(ConjuntItems.getNomAtribut(i) + " " + ConjuntItems.getSTipus(i) + " " + ConjuntItems.getPes(i));
            if (i != ConjuntItems.getNumAtributs()-1) System.out.print(" | ");
        }
        System.out.println("");

        for (int i = 0; i < size(); ++i) {
            get(i).print();
        }
    }

    public Item getItem(String id) throws NotFoundException, NotDefinedException { //Cerca dicotòmica
        return cercaBinaria(id, 0, size());
    }

    public ArrayList<String> getAtributItem(String id, int i) throws NotFoundException, NotDefinedException { //Cerca dicotòmica + retornar atribut
        return getItem(id).getAtribut(i);
    }

    public ArrayList<String> getAtributItem(int index, int i) { //retornar atribut
        return get(index).getAtribut(i);
    }

    private Item cercaBinaria(String id, int left, int right) throws NotFoundException, NotDefinedException {
        int l = left, r = right, mid = 0;
        boolean end = false;
        while (!end) {
            if (r > l) {
                throw new NotFoundException("id no existeix: " + id);
            }

            mid = l + (r - l)/2;
            String s = getID(mid);

            if (s.compareTo(id) < 0) { //mid < id
                l = mid + 1;
            }
            if (s.compareTo(id) > 0) { //mid > id
                r = mid - 1;
            }
            else {
                end = true;
            }
        }
        return get(mid);
    }

    private String getID(int m) throws NotDefinedException {
        Item i = get(m);
        ArrayList<String> id;
        id = i.getID();
        if (id.size() != 1) {
            throw new NotDefinedException("atribut id no té 1 valor " + m);
        }
        return id.get(0);
    }

    static public void assignarPes(int a, float pes)  {
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
        }
        else if (c == tipus.N) {
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

        switch(t) {
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
            default:
                s = "No assignat";
                break;
        }

        return s;
    }

    static public tipus getTipus(int i) {
        return tipusAtribut.get(i);
    }

}