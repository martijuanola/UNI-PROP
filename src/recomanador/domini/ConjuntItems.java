package src.recomanador.domini;

import java.util.ArrayList;
import java.util.Collection;

public class ConjuntItems extends ArrayList<Item> {

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
        Item.assignarNomAtributs(nAtributs);
        Item.inicialitzar(nAtributs.size());

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

    public void printItems() {
        System.out.println("Nom conjunt: " + Item.nom);
        for(int i = 0; i < Item.getNumAtributs(); ++i) {
            System.out.print(Item.getNomAtribut(i) + " " + Item.getSTipus(i) + " " + Item.getPes(i));
            if (i != Item.getNumAtributs()-1) System.out.print(" | ");
        }
        System.out.println("");

        for (int i = 0; i < size(); ++i) {
            get(i).print();
        }
    }
}