package src.recomanador;

import java.util.ArrayList;

public class StringOperations {
    static public ArrayList<String> divideString(String s, char divider) {
        ArrayList<String> str = new ArrayList<String>();
        int ini = 0; //Últim ';' trobat

        for (int k = 0; k < s.length(); ++k) {
            if (s.charAt(k) == ';') {
                String aux = s.substring(ini, k);
                ini = k + 1;
                str.add(aux); //S'afegeix la part del atribut
            }
        }
        //Paraula final o cas que no hi ha substring
        String aux = s.substring(ini, s.length());
        str.add(aux);
        return str;
    }
}
