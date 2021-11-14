package src.recomanador.domini;

import java.util.ArrayList;

public class Item implements Comparable<Item>{

    private ArrayList<ArrayList<String>> atributs; //Vector on cada atribut té un vector. Pot ser buit


    public Item() {
    }

    public Item(ArrayList<ArrayList<String>> atributs) {
        this.atributs = atributs;
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
            if (i != atributs.size()-1) System.out.print(", ");
        }
        System.out.println("");
    }

    public ArrayList<String> getAtribut(int i) {
        return atributs.get(i);
    }

    public ArrayList<String> getID() {
        return atributs.get(ConjuntItems.id);
    }

    @Override
    public int compareTo(Item otherItem) {
        ArrayList<String> id1 = getID();
        ArrayList<String> id2 = otherItem.getID();
        int n1 = 0, n2 = 0;
        try {
            if (id1.size() != 0) n1 = Integer.parseInt(id1.get(0));
            if (id2.size() != 0) n2 = Integer.parseInt(id2.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (n1 == n2) return 0;
        else if (n1 < n2) return -1;
        else return 1;
    }
}
