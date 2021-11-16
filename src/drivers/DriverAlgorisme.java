package src.drivers;

import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.ConjuntRecomanacions;
import src.recomanador.domini.ConjuntUsuaris;
import src.recomanador.domini.ControladorDominiAlgorisme;

/**
 * This class is meant to test the class Algorisme.
 * To test some of its functions in an atomized manner, I temporarily
 * changed them from private to public.
 * 
 * @author Adrià F.
 */
public class DriverAlgorisme {
    
    public static void main(String[] args) {
        ConjuntItems items;
        ConjuntRecomanacions recomanacions;
        ConjuntUsuaris usuaris = null;

        ControladorDominiAlgorisme algorisme = new ControladorDominiAlgorisme();
        algorisme.set_Q(5);
        algorisme.seleccionar_algorisme(0);
        algorisme.set_k(usuaris.size()/20);
    }
}
