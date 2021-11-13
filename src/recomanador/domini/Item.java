package src.recomanador.domini;

import java.util.ArrayList;

public class Item implements Comparable<Item>{

    private ArrayList<ArrayList<String>> atributs; //Vector on cada atribut té un vector. Pot ser buit
    static int id; //posició del atribut id al tipus atribut
    static private int nomA; //posició del atribut nom al al tipus atrbut. -1 si no en té

    public Item() {
    }

    public Item(ArrayList<ArrayList<String>> atributs) {
        this.atributs = atributs;
        Item.id = -1;
        Item.nomA = -1;
    }

    static public void assignarPes(int a, float pes)  {
        Item.pesos.set(a, pes);
    }

    static public void assignarTipus(int a, tipus c) {
        Item.tipusAtribut.set(a, c);
        if (c == tipus.I) {
            if (id != -1) { //Canviem l'id antic per evitar tenir 2 id
                Item.tipusAtribut.set(id, tipus.S);
            }
            id = a;
        }
        else if (c == tipus.N) {
            if (nomA != -1) { //Canviem el nom antic per evitar tenir 2 noms
                Item.tipusAtribut.set(nomA, tipus.S);
            }
            nomA = a;
        }
    }
    
    static public void assignarNomAtributs(ArrayList<String> n) {
        Item.nomAtribut = n;
    }

    static public void assignarNom(String n) {
        Item.nom = n;
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

    public void print() {
        for (int i = 0; i < atributs.size(); ++i) {
            int g = atributs.get(i).size();
            if (g > 1) System.out.print("{");
            for (int j = 0; j < g; ++j) {
                System.out.print(atributs.get(i).get(j));
                if (j != g-1) System.out.print(", ");
            }
            if (g > 1) System.out.print("}");
            else if (i != atributs.size()-1) System.out.print(", ");
        }
        System.out.println("");
    }

    public ArrayList<String> getAtribut(int i) {
        return atributs.get(i);
    }

    public ArrayList<String> getID() {
        return atributs.get(id);
    }

    @Override
    public int compareTo(Item otherItem) {
        return getID().get(0).compareTo(otherItem.getID().get(0));
    }
}
