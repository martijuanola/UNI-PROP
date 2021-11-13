package src.recomanador.domini;

import java.util.ArrayList;

import javax.print.attribute.standard.MediaSize.NA;

public class Item {

    public enum tipus
    { //En ordre: ID, Nom, bool, float, string 
        I, N, B, F, S
    }


    private ArrayList<ArrayList<String>> atributs; //Vector on cada atribut té un vector. Pot ser buit
    public static String nom; //Nom del conjunt d'items
    public static ArrayList<Float> pesos;
    public static ArrayList<tipus> tipusAtribut; //I i N haurien de ser únics en els atributs
    public static ArrayList<String> nomAtribut;

    public Item() {
    }

    public Item(ArrayList<ArrayList<String>> atributs) {
        this.atributs = atributs;
    }

    static public void inicialitzar(int nAtributs) {
        pesos = new ArrayList<Float>(nAtributs);
        tipusAtribut = new ArrayList<tipus>(nAtributs);
        nomAtribut = new ArrayList<String>(nAtributs);
        int i = 0;
        while (i < nAtributs) {
            pesos.add((float)i*2*i);
            tipusAtribut.add(i, tipus.S);
            nomAtribut.add("holi"+i);
            ++i;
        }
    }

    static public void assignarPes(int a, float pes)  {
        Item.pesos.set(a, pes);
    }

    static public void assignarTipus(int a, tipus c) {
        Item.tipusAtribut.set(a, c);
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
}
