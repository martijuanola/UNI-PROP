package src.recomanador.domini;

import java.util.ArrayList;
/**
 * Classe Item
 * @author Jaume
 */
public class Item implements Comparable<Item>{

    private ArrayList<ArrayList<String>> atributs; //Vector on cada atribut té un vector. Pot ser buit

    /**
     * Constructor buit
     */
    public Item() {
    }

    /**
     * Es crea l'item i s'assigna els atributs a l'item
     * @param atributs llista d'atributs d'items on cada atribut pot tenir diversos atributs
     */
    public Item(ArrayList<ArrayList<String>> atributs) {
        this.atributs = atributs;
    }

    /**
     * Imprimeix els atributs
     */
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

    /**
     * Retorna l'atribut de la posició i
     * @param i index del atribut: entro 0 i el nombre d'atributs
     * @return llista dels sub atributs
     */
    public ArrayList<String> getAtribut(int i) {
        return atributs.get(i);
    }

    /**
     * Retorna l'atribut de la posició id de ConjuntItems
     * @return llista del atribut id (Només hi hauria d'haver un atribut, però no està assegurat)
     */
    public ArrayList<String> getID() {
        return atributs.get(ConjuntItems.id);
    }

    @Override
    /**
     * Funció que fa override al compareTo per defecte, compara 2 items per els seus id's
     * @param otherItem L'altre item que compara
     * @return Retorna un nombre depenent de la comparació entre l'item al que es fa la crida de la funció i el paràmetre
     * 1 si implícit > otherItem, 
     * 0 si implícit > otherItem, 
     * -1 si implícit < otherItem
     */
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
