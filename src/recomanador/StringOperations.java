package src.recomanador;

import java.util.ArrayList;

import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.ConjuntItems.tipus;

public class StringOperations {
    static public boolean esNombre(String s) { //Només accepta nombres, sense punts ni exponents
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (!esNombre(c)){
                return false;
            }
        }
        return true;
    }

    static public boolean esNombre(char s) {
        return s == '0' || s == '1' || s == '2' || s == '3' || s == '4' || s == '5' || s == '6' || s == '7' || s == '8' || s == '9';
    }

    static public boolean esFloat(String s) { //Els ints també s'accepten com a floats
        boolean eUtilitzat = false; //es pot utilitzar una e per marcar un exponent
        boolean pUtilitzat = false; //Només pot tenir un punt
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (!esNombre(c)){
                if (c == 'e' && !eUtilitzat) eUtilitzat = true;
                else if (c == '.' && !pUtilitzat) pUtilitzat = true;
                else return false;
            }
        }
        return s.length() > 0;
    }

    static public boolean esData(String s) {
        int p1, p2; //Marquen les posicions dels separadors
        if (s.length() == 10) {//xxxx-xx-xx o xx-xx-xxxx
            if (StringOperations.esNombre(s.substring(0, 4))) { //Té l'any primer xxxx-xx-xx 
                p1 = 4;
                p2 = 7;
            }
            else if (StringOperations.esNombre(s.substring(0, 2))) { //Té un dia primer xx-xx-xxxx
                p1 = 2;
                p2 = 5;
            }
            else return false;

            if ((s.charAt(p1) == '-' && s.charAt(p2) == '-') || (s.charAt(p1) == '/' && s.charAt(p2) == '/')) {
                if (StringOperations.esNombre(s.substring(0, p1)) && StringOperations.esNombre(s.substring(p1+1, p2)) && StringOperations.esNombre(s.substring(p2+1, s.length()))) {
                    return true;
                }
            }
        }
        return false;
    }

    static public boolean esBool(String s) {
        return s.equalsIgnoreCase("false") || s.equalsIgnoreCase("true");
    }

    public static int compararAtributs(String s1, String s2, tipus tipus) { //-1 si s1 < s2, 0 si son iguals, 1 si s1 > s2
        boolean s1Bigger = false, s2Bigger = false;
        if (s1 == "" && s2 == "") {}
        else if (s1 == "") s2Bigger = true;
        else if (s2 == "") s1Bigger = true;
        else if (tipus == ConjuntItems.tipus.I) { //Identificador
            int i1 = Integer.parseInt(s1);
            int i2 = Integer.parseInt(s2);
            s1Bigger = i1 > i2;
            s2Bigger = i1 < i2;
        }
        else if (tipus == ConjuntItems.tipus.B) { //Boolea
            s1Bigger = Boolean.parseBoolean(s1);
            s2Bigger = Boolean.parseBoolean(s2);
        }
        else if (tipus == ConjuntItems.tipus.F) { //Float
            float i1 = Float.parseFloat(s1);
            float i2 = Float.parseFloat(s2);
            s1Bigger = i1 > i2;
            s2Bigger = i1 < i2;
        }
        else if (tipus == ConjuntItems.tipus.D) { //Data
            int s1p1, s1p2, dia1, mes1, any1;
            int s2p1, s2p2, dia2, mes2, any2;
            if (s1.charAt(4) == '-') {
                s1p1 = 4;
                s1p2 = 7;
            }
            else {
                s1p1 = 2;
                s1p2 = 5;
            }
            if (s1.charAt(4) == '-') {
                s2p1 = 4;
                s2p2 = 7;
            }
            else {
                s2p1 = 2;
                s2p2 = 5;
            }
            any1 = Integer.parseInt(s1.substring(0, s1p1));
            mes1 = Integer.parseInt(s1.substring(s1p1+1, s1p2));
            dia1 = Integer.parseInt(s1.substring(s1p2+1, s1.length()));
        
            any2 = Integer.parseInt(s2.substring(0, s2p1));
            mes2 = Integer.parseInt(s2.substring(s2p1+1, s2p2));
            dia2 = Integer.parseInt(s2.substring(s2p2+1, s2.length()));
            
            if (any1 == any2) {
                if (mes1 == mes2) {
                    s1Bigger = (dia1 > dia2);
                    s2Bigger = (dia1 < dia2);
                }
                else {
                    s1Bigger = (mes1 > mes2);
                    s2Bigger = (mes1 < mes2);
                }
            }
            else {
                s1Bigger = (any1 > any2);
                s2Bigger = (any1 < any2);
            }
        }
        else { //Nom i String (és el mateix)
            int x = s1.compareTo(s2);
            s1Bigger = x > 0;
            s2Bigger = x < 0;
        }
        if (s1Bigger) return 1;
        else if (s2Bigger) return -1;
        else return 0;
    }
    
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

    public static String infinitString() { //4097 caracters
        String s = "Z";
        for (int i = 0; i < 12; ++i) {
            s = s+s;
        }
        return s;
    }
}
