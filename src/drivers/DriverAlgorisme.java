package src.drivers;

import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.ConjuntRecomanacions;
import src.recomanador.domini.ConjuntUsuaris;
import src.recomanador.domini.ControladorDomini;
import src.recomanador.domini.ControladorDominiAlgorisme;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.FolderNotValidException;
import src.recomanador.excepcions.DataNotValidException;

/**
 * This class is meant to test the class Algorisme.
 * To test some of its functions in an atomized manner, I temporarily
 * changed them from private to public.
 * 
 * @author Adrià F.
 */
public class DriverAlgorisme {
    
    public static void main(String[] args) {
        ControladorDomini domini = new ControladorDomini();
        try {
            domini.carregarCarpeta("src/data/movies-250");
        }
        catch(FolderNotFoundException | FolderNotValidException e) {
            System.out.println("cagaste we");
        }
        catch(DataNotValidException e) {
            System.out.println("cagaste we2");
        }

        ConjuntItems items = domini.ci;
        ConjuntRecomanacions recomanacions = domini.cr;
        ConjuntUsuaris usuaris = domini.cu;

        ControladorDominiAlgorisme algorisme = new ControladorDominiAlgorisme();
        try {
            algorisme.set_Q(5);
            algorisme.seleccionar_algorisme(0);
            algorisme.set_k(usuaris.size()/20);
        }
        catch(DataNotValidException e) {
            //de moment re però s'hauran de tractar(Martí)
        }
    }
}
