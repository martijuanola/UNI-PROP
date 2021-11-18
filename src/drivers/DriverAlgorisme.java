package src.drivers;

import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.ConjuntRecomanacions;
import src.recomanador.domini.ConjuntUsuaris;
import src.recomanador.domini.ControladorDomini;
import src.recomanador.domini.ControladorDominiAlgorisme;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.FolderNotValidException;
import src.recomanador.excepcions.UserNotFoundException;
import src.recomanador.excepcions.DataNotValidException;

import src.recomanador.persistencia.*;
import java.util.ArrayList;
import java.util.Scanner;

import src.recomanador.domini.ItemValoracioEstimada;

/**
 * This class is meant to test the class Algorisme.
 * To test some of its functions in an atomized manner, I temporarily
 * changed them from private to public.
 * 
 * @author Adrià F.
 */
public class DriverAlgorisme {
    
    public static void main(String[] args) throws UserNotFoundException{

        Scanner in= new Scanner(System.in);
        ControladorDomini domini = new ControladorDomini();
        ControladorPersistencia persistencia = new ControladorPersistencia();
        ControladorDominiAlgorisme algorisme = new ControladorDominiAlgorisme();

        System.out.println();
        System.out.println("DRIVER ALGORISME");
        System.out.println();

        System.out.println("Siusplau, escull un projecte:");
        System.out.println("movies-250, movies-750, movies-2250, movies-6750, movies.sample");
        System.out.println();
        System.out.print(">>>>> ");

        Boolean bool1 = true;
        String string1 = "";
        int int1 = 0;

        while (bool1) {
            string1 = in.next();

            try {
                domini.carregarCarpeta(string1);
                bool1 = false;
            }
            catch(FolderNotFoundException | FolderNotValidException e) {
                System.out.println("Carpeta no vàlida. Provi un altre cop.");
                System.out.print(">>>>> ");
            }
            catch(DataNotValidException e) {
                System.out.println("Dades no vàlides. Provi un altre cop.");
                System.out.print(">>>>> ");
            }
        }

        ConjuntItems items = domini.ci;
        ConjuntRecomanacions recomanacions = domini.cr;
        ConjuntUsuaris usuaris = domini.cu;

        System.out.println("Carregat projecte " + string1 + ".");
        System.out.println();

        System.out.println("Selecciona quants Items vols recomanats (i.e. 5):");
        System.out.print(">>>>> ");
        bool1 = true;
        while(bool1) {
            int1 = in.nextInt();
            try {
                algorisme.set_Q(int1);
                bool1 = false;
            }
            catch (DataNotValidException e) {
                System.out.println(e);
                System.out.print(">>>>> ");
            }
        }

        System.out.println();

        System.out.println("Selecciona el valor K (i.e. 10):");
        System.out.print(">>>>> ");
        bool1 = true;
        while(bool1) {
            int1 = in.nextInt();
            try {
                algorisme.set_k(int1);
                bool1 = false;
            }
            catch (DataNotValidException e) {
                System.out.println(e);
                System.out.print(">>>>> ");
            }
        }
        System.out.println();
        
        System.out.println("Selecciona l'algorisme recomanador:");
        System.out.println("0 - Collaborative Filtering (k-Means/Slope1)");
        System.out.println("1 - Content-Based Filtering (k-NearestNeighbours)");
        System.out.println("2 - Hybrid Approaches NO IMPLEMENTAT!");
        System.out.print(">>>>> ");
        bool1 = true;
        while(bool1) {
            int1 = in.nextInt();
            try {
                algorisme.seleccionar_algorisme(int1);
                bool1 = false;
            }
            catch (DataNotValidException e) {
                System.out.println(e);
                System.out.print(">>>>> ");
            }
        }
        System.out.println(); 

        System.out.println("Seleccioni un mode:");
        System.out.println("0 - Obtenir recomanacions per a un usuari");
        System.out.println("1 - Obtenir DCG usant known i unknown");
        System.out.print(">>>>> ");
        bool1 = true;
        int MODE_SELECCIONAT = 0;
        while(bool1) {
            MODE_SELECCIONAT = in.nextInt();
            if (MODE_SELECCIONAT > 1) {System.out.println("Valor no valid.");System.out.print(">>>>> ");}
            else bool1 = false;
        }
        System.out.println();

        ArrayList<ItemValoracioEstimada> items_recomanats = new ArrayList<ItemValoracioEstimada>();
        switch (MODE_SELECCIONAT) {
            case 0:

            int user_ID = 0;
            System.out.println("Escull una ID d'usuari:");
            System.out.print(">>>>> ");
            bool1 = true;
            while(bool1) {
                user_ID = in.nextInt();
                try {
                    usuaris.getUsuari(user_ID);
                    bool1 = false;
                }
                catch (UserNotFoundException e) {
                    System.out.println(e);
                    System.out.print(">>>>> ");
                }
            }
            System.out.println();

            items_recomanats = algorisme.run_algorithm(user_ID, items, usuaris, recomanacions);
            

            System.out.println("Et recomanem aquests items dels " + items.size() + " del nostre catàleg:");
            System.out.println("(ID item, puntuacio estimada)");
            for (int i = 0; i < items_recomanats.size(); ++i) {
                ItemValoracioEstimada it = items_recomanats.get(i);
                System.out.println("(" + it.item.getId() + "," + it.valoracioEstimada +")");
            }

            break;

            case 1:
                try {
                    ArrayList<ArrayList<String>> raw = persistencia.carregarFitxerExtern("data/movies-250/ratings.test.known.csv");
                    System.out.println("USUARIS1 = " + usuaris.size());
                    usuaris.afegirDades(raw);
                    recomanacions.afegirDades(items,usuaris,raw);
                }
                catch(Exception e) {
                    System.out.println("data not added!!!");
                    System.out.println("ERROR: " + e.getMessage());
                }
                
                System.out.println("INDEX = " + usuaris.cercaBinaria(159588));
                System.out.println("USUARIS2 = " + usuaris.size());
                items_recomanats = algorisme.run_algorithm(159588, items, usuaris, recomanacions);
            

                System.out.println("Et recomanem aquests items dels " + items.size() + " del nostre catàleg:");
                System.out.println("(ID item, puntuacio estimada)");
                for (int i = 0; i < items_recomanats.size(); ++i) {
                    ItemValoracioEstimada it = items_recomanats.get(i);
                    System.out.println("(" + it.item.getId() + "," + it.valoracioEstimada +")");
                }

            break;
        }

        //int user_ID = usuaris.get(0).getId();
        //algorisme.run_algorithm(user_ID, items, usuaris, recomanacions);

        in.close();
    }
}
