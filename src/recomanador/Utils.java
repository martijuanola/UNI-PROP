package src.recomanador;

public class Utils {
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
            if (Utils.esNombre(s.substring(0, 4))) { //Té l'any primer xxxx-xx-xx 
                p1 = 4;
                p2 = 7;
            }
            else if (Utils.esNombre(s.substring(0, 2))) { //Té un dia primer xx-xx-xxxx
                p1 = 2;
                p2 = 5;
            }
            else return false;

            if ((s.charAt(p1) == '-' && s.charAt(p2) == '-') || (s.charAt(p1) == '/' && s.charAt(p2) == '/')) {
                if (Utils.esNombre(s.substring(0, p1)) && Utils.esNombre(s.substring(p1+1, p2)) && Utils.esNombre(s.substring(p2+1, s.length()))) {
                    return true;
                }
            }
        }
        return false;
    }

    static public boolean esBool(String s) {
        return s.equalsIgnoreCase("false") || s.equalsIgnoreCase("true");
    }
}
