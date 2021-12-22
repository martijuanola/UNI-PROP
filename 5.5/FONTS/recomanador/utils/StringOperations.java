package src.recomanador.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import src.recomanador.domini.Item.tipus;
import src.recomanador.excepcions.ItemTypeNotValidException;

/**
 * Class that works with strings and types that are associated for strings
 * @author Jaume C.
 */
public class StringOperations {
    /**
     * Function that tells if a string is made up of numbers
     * @param s input string made up of any characters
     * @return returns false if the string has any character that does not belong to a positive integer, 
     * true if if has only numbers or if it's empty
     */
    public static boolean esNombre(String s) {
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (!esNombre(c)){
                return false;
            }
        }
        return true;
    }

    /**
     * Simple function that returns if the character is a number
     * @param s character to check
     * @return true if the character is between 0 and 9, both included, false otherwise
     */
    public static boolean esNombre(char s) {
        return s == '0' || s == '1' || s == '2' || s == '3' || s == '4' || s == '5' || s == '6' || s == '7' || s == '8' || s == '9';
    }

    /**
     * Checks if a string can be treated as a float
     * @param s string to check
     * @return true if the string can be parsed as a float which can have a point, an exponent and can be negative, 
     * false otherwise
     */
    public static boolean esFloat(String s) { //Els ints també s'accepten com a floats
        boolean eUtilitzat = false; //es pot utilitzar una e per marcar un exponent
        boolean pUtilitzat = false; //Només pot tenir un punt
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (!esNombre(c)){
                if (c == 'e' && !eUtilitzat) eUtilitzat = true;
                else if (c == '.' && !pUtilitzat) pUtilitzat = true;
                else if (c == '-' && (i == 0 || s.charAt(i-1) == 'e')) {}
                else return false;
            }
        }
        return s.length() > 0;
    }

    /**
     * Returns if a string can be treated as a date
     * @param s input string
     * @return true if the string if of the form xx-xx-xxxx or xx/xx/xxxx or xxxx-xx-xx or xxxx/xx/xx where x is a number, 
     * false otherwise
     */
    public static boolean esData(String s) {
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

    /**
     * Checks if a string can be treated as a bool
     * @param s input string
     * @return true if the string equals "true" or "false", false otherwise
     */
    public static boolean esBool(String s) {
        return s.equalsIgnoreCase("false") || s.equalsIgnoreCase("true");
    }

    /**
     * Compares two attributes of two types and returns which one is bigger
     * @param s1 first input string
     * @param s2 second input string
     * @param tipus type of the two strings, must be common for both
     * @return Three cases:
     *  -1 if s1 &lt; s2 
     *  0 if s1 = s2
     *  1 id s1 &gt; s2
     */
    public static int compararAtributs(String s1, String s2, tipus tipus) {
        boolean s1Bigger = false, s2Bigger = false;
        if (s1 == "" && s2 == "") {}
        else if (s1 == "") s2Bigger = true;
        else if (s2 == "") s1Bigger = true;
        else if (tipus == src.recomanador.domini.Item.tipus.I) { //Identificador
            int i1 = Integer.parseInt(s1);
            int i2 = Integer.parseInt(s2);
            s1Bigger = i1 > i2;
            s2Bigger = i1 < i2;
        }
        else if (tipus == src.recomanador.domini.Item.tipus.B) { //Boolea
            s1Bigger = Boolean.parseBoolean(s1);
            s2Bigger = Boolean.parseBoolean(s2);
        }
        else if (tipus == src.recomanador.domini.Item.tipus.F) { //Float
            float i1 = Float.parseFloat(s1);
            float i2 = Float.parseFloat(s2);
            s1Bigger = i1 > i2;
            s2Bigger = i1 < i2;
        }
        else if (tipus == src.recomanador.domini.Item.tipus.D) { //Data
            int dia1, mes1, any1;
            int dia2, mes2, any2;
            if (s1.charAt(4) == '-') {
                int p1 = 4;
                int p2 = 7;
                any1 = Integer.parseInt(s1.substring(0, p1));
                mes1 = Integer.parseInt(s1.substring(p1+1, p2));
                dia1 = Integer.parseInt(s1.substring(p2+1, s1.length()));
            }
            else {
                int p1 = 2;
                int p2 = 5;
                dia1 = Integer.parseInt(s1.substring(0, p1));
                mes1 = Integer.parseInt(s1.substring(p1+1, p2));
                any1 = Integer.parseInt(s1.substring(p2+1, s1.length()));
            }
            if (s2.charAt(4) == '-') {
                int p1 = 4;
                int p2 = 7;
                any2 = Integer.parseInt(s2.substring(0, p1));
                mes2 = Integer.parseInt(s2.substring(p1+1, p2));
                dia2 = Integer.parseInt(s2.substring(p2+1, s2.length()));
            }
            else {
                int p1 = 2;
                int p2 = 5;
                dia2 = Integer.parseInt(s2.substring(0, p1));
                mes2 = Integer.parseInt(s2.substring(p1+1, p2));
                any2 = Integer.parseInt(s2.substring(p2+1, s2.length()));
            }
            
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

    /**
     * Returns the time in days since 00-00-0000 that represents the string
     * @param s input string that can be treated as a date
     * @return int time that represents the string, where years are 365 days and months are 30 days
     */
    public static int dataToTime(String s) {
        int p1, p2, dia, mes, any;
        if (s.charAt(4) == '-') {
            p1 = 4;
            p2 = 7;
            any = Integer.parseInt(s.substring(0, p1));
            mes = Integer.parseInt(s.substring(p1+1, p2));
            dia = Integer.parseInt(s.substring(p2+1, s.length()));
        }
        else {
            p1 = 2;
            p2 = 5;
            dia = Integer.parseInt(s.substring(0, p1));
            mes = Integer.parseInt(s.substring(p1+1, p2));
            any = Integer.parseInt(s.substring(p2+1, s.length()));
        }

        return any*365 + mes*30 + dia;
    }
    
    /**
     * Divides a string into several substrings with a divider
     * @param s input string
     * @param divider character that represents the division of a string
     * @return ArrayList of substrings where the divider does not appear
     */
    public static ArrayList<String> divideString(String s, char divider) {
        ArrayList<String> str = new ArrayList<String>();
        int ini = 0; //Últim ';' trobat

        for (int k = 0; k < s.length(); ++k) {
            if (s.charAt(k) == divider) {
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

    /**
     * Returns a big string to represent the maximum string possible (not actually the maximum)
     * @return a string of 4096 'Z'
     */
    public static String infinitString() { //4097 caracters
        String s = "Z";
        for (int i = 0; i < 12; ++i) {
            s = s+s;
        }
        return s;
    }

    /**
     * Returns the minimum edit distance to transform form a string to another
     * @param s1 first input string
     * @param s2 second input string
     * @param n size of the first string (s1)
     * @param m size of the second string (s2)
     * @param dp array matrix of n+1 x m+1 dimesions filled with -1, table to perform dynamic programming
     * @return number of modifications (add, delete or modify a character from a tring) to achieve an equal string
     */
    public static int minDis(String s1, String s2, int n, int m, int[][]dp)
    {
        // If any String is empty,
        // return the remaining characters of other String
        if(n == 0)    return m;  
        if(m == 0)    return n;

        // To check if the recursive tree
        // for given n & m has already been executed
        if(dp[n][m] != -1)    return dp[n][m];

        // If characters are equal, execute 
        // recursive function for n-1, m-1
        if(s1.charAt(n - 1) == s2.charAt(m - 1)) {           
            if(dp[n - 1][m - 1] == -1) {               
                return dp[n][m] = minDis(s1, s2, n - 1, m - 1, dp);           
            }        
            else return dp[n][m] = dp[n - 1][m - 1];   
        }

        // If characters are not equal, we need to
        // find the minimum cost out of all 3 operations.      
        else {           
            int m1, m2, m3;        // temp variables   
            if(dp[n-1][m] != -1) {    
                m1 = dp[n - 1][m];      
            }           
            else {   
                m1 = minDis(s1, s2, n - 1, m, dp);      
            }            

            if(dp[n][m - 1] != -1) {                
                m2 = dp[n][m - 1];            
            }            
            else {    
                m2 = minDis(s1, s2, n, m - 1, dp);      
            }                                   

            if(dp[n - 1][m - 1] != -1) {    
                m3 = dp[n - 1][m - 1];      
            }   
            else {   
                m3 = minDis(s1, s2, n - 1, m - 1, dp);       
            }     
            return dp[n][m] = 1 + Math.min(m1, Math.min(m2, m3));        
        }
    }

    /**
     * Returns the type that represents a string 
     * @param s input string
     * @return tipus in funtion of the input string
     * @throws ItemTypeNotValidException if s does not contain a type
     */
    public static tipus stringToType(String s) throws ItemTypeNotValidException{
        if (s.equalsIgnoreCase("identificador") || s.equalsIgnoreCase("I")) return tipus.I;
        else if (s.equalsIgnoreCase("boolean") || s.equalsIgnoreCase("B")) return tipus.B;
        else if (s.equalsIgnoreCase("nom") || s.equalsIgnoreCase("N")) return tipus.N;
        else if (s.equalsIgnoreCase("float") || s.equalsIgnoreCase("F")) return tipus.F;
        else if (s.equalsIgnoreCase("string") || s.equalsIgnoreCase("S")) return tipus.S;
        else if (s.equalsIgnoreCase("data") || s.equalsIgnoreCase("D")) return tipus.D;
        throw new ItemTypeNotValidException(s+" no conté un tipus");
    }

    /**
     * Returns a string based of the input tipus
     * @param t input tipus to transform to string
     * @return a string based of the tipus (Identificador, Boolean, Float, String, Data) can be "No assignat" if the type is not recognissed
     */
    public static String tipusToString(tipus t) {
        String s = "";
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

    /**
     * Checks if a string can be treated as type t
     * @param s string that is going to be checked
     * @param t type that is going to be checked
     * @return true if s can be treated as type t, false if s cannot be treated as type t
    */
    public static boolean tipusCorrecte(String s, tipus t) {
        // Nom i String sempre estaran bé
        if (t == tipus.S || t == tipus.N || s.equals("") && !s.equals(" ")) return true;

        if (t == tipus.I && StringOperations.esNombre(s)) return true;
        else if (t == tipus.B && StringOperations.esBool(s)) return true;
        else if (t == tipus.F && StringOperations.esFloat(s)) return true;
        else if (t == tipus.D && StringOperations.esData(s)) return true;

        return false;
    }

    /**
     * Computes the similarity between to strings that belong to the same column. The similarity for each type is measured differently
     * @param a1 first string to be compared
     * @param a2 second string to be compared
     * @param t Type of atribute
     * @param min Min value possible
     * @param max Max value possible
     * @return float between 0 and 1 where 0 means completely different and 1 means exaclty the same
     * @throws ItemTypeNotValidException if a1 and a2 are not of the same type
     */
    public static float distanciaAtribut(String a1, String a2, tipus t, float min, float max) throws ItemTypeNotValidException {
        if (!StringOperations.tipusCorrecte(a1, t) || !StringOperations.tipusCorrecte(a2, t))
            throw new ItemTypeNotValidException(
                    "atribut " + a1 + " o atribut " + a2 + " no son del tipus " + StringOperations.tipusToString(t));

        if (a1.equals(a2))
            return (float) 1.0;
        if (a1.length() == 0 || a2.length() == 0)
            return (float) 0.0;

        float sim = (float) 0.0;
        if (t == tipus.I) {
            int i1 = Integer.parseInt(a1), i2 = Integer.parseInt(a2);
            sim = 1 - Math.abs((i1 - i2) / (max - min));
        } else if (t == tipus.B) {
            boolean b1 = Boolean.parseBoolean(a1), b2 = Boolean.parseBoolean(a2);
            if (b1 == b2)
                sim = (float) 1.0;
            else
                sim = (float) 0.0;
        } else if (t == tipus.D) {
            if (a1.length() != a2.length())
                return (float) 0.0;
            sim = 1 - Math.abs((StringOperations.dataToTime(a1) - StringOperations.dataToTime(a2)) / (max - min));
        } else if (t == tipus.F) {
            float i1 = Float.parseFloat(a1), i2 = Float.parseFloat(a2);
            sim = 1 - Math.abs((i1 - i2) / (max - min));
        } else if (t == tipus.S || t == tipus.N) {
            int n = a1.length(), m = a2.length();
            int[][] dp = new int[n + 1][m + 1];
            for (int i = 0; i < n + 1; i++)
                Arrays.fill(dp[i], -1);
            int a = StringOperations.minDis(a1, a2, n, m, dp);
            sim = 1 - (float) 1.0 * a / Math.max(n, m);
        }
        return sim;
    }

}
