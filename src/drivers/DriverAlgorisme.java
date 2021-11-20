package src.drivers;

import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.ConjuntRecomanacions;
import src.recomanador.domini.ConjuntUsuaris;
import src.recomanador.domini.ControladorDomini;
import src.recomanador.domini.ControladorDominiAlgorisme;
import src.recomanador.domini.Item;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.FolderNotValidException;
import src.recomanador.excepcions.ItemWeightNotCorrectException;
import src.recomanador.excepcions.UserNotFoundException;
import src.recomanador.excepcions.DataNotValidException;

import src.recomanador.persistencia.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

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
        String PROJECTE = "";
        int int1 = 0;

        while (bool1) {
            PROJECTE = in.next();

            try {
                domini.carregarCarpeta(PROJECTE);
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

        System.out.println("Carregat projecte " + PROJECTE + ".");
        System.out.println();

        System.out.println("Selecciona el valor K (i.e. " + usuaris.size()/1000 + "):");
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
        
        if(int1 == 1) {
            int atribut_seleccionat = 0;

            ArrayList<String> atributs = Item.getCapçalera();
            ArrayList<Float> pesos = Item.getPesos();

            while(atribut_seleccionat != -1) {
                System.out.println("El conjunt d'Ítems té els següents atributs amb els següents pesos:");
                System.out.println();

                System.out.println("INDEX       PES              ATRIBUT");
                
                for (int i = 0; i < atributs.size(); ++i) {
                    System.out.print(i);
                    if (i < 10) System.out.print(" ");
                    System.out.println("          "+pesos.get(i)+"            "+atributs.get(i));
                }
                System.out.println();

                System.out.println("Si vol modificar el pes d'un atribut escrigui l'índex. Altrament, escrigui -1.");
                System.out.print(">>>>> ");
                atribut_seleccionat = in.nextInt();
                if (atribut_seleccionat != -1) {
                    System.out.println("Escrigui el nou pes:");
                    System.out.print(">>>>> ");
                    float nouPes = in.nextFloat();
                    try {Item.setPes(atribut_seleccionat, nouPes);}
                    catch (ItemWeightNotCorrectException | ArrayIndexOutOfBoundsException e) {System.out.println(e);}
                }
            }

            System.out.println();
        }

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

            int user_ID = 0;
            System.out.println("Escull una ID d'usuari:");
            System.out.println("(p.ex.: " + usuaris.get(0).getId() + ", " + usuaris.get(1).getId() + ", " + usuaris.get(2).getId() + ")");
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
                try {algorisme.set_Q(items.size());}
                catch (DataNotValidException e) {System.out.println(e); break;}

                ConjuntUsuaris usuarisUnknown = new ConjuntUsuaris();
                ConjuntRecomanacions recomanacionsUnknown = new ConjuntRecomanacions();
            
                try {
                    ArrayList<ArrayList<String>> raw = persistencia.carregarFitxerExtern("data/" +PROJECTE+ "/ratings.test.known.csv");
                    usuaris.afegirDades(raw);
                    recomanacions.afegirDades(items,usuaris,raw);

                    raw = persistencia.carregarFitxerExtern("data/" +PROJECTE+ "/ratings.test.unknown.csv");
                    usuarisUnknown.afegirDades(raw);
                    recomanacionsUnknown.afegirDades(items, usuarisUnknown, raw);
                }
                catch(Exception e) {
                    System.out.println("data not added!!!");
                    System.out.println("ERROR: " + e.getMessage());
                    break;
                }

                int DCG = 0;
                Double IDCG = 0d;

                for(int idx_unknown = 0; idx_unknown < usuarisUnknown.size(); ++idx_unknown) {
                    int id_unknown = usuarisUnknown.get(idx_unknown).getId();
                    ConjuntRecomanacions val_unknown_aux = usuarisUnknown.get(idx_unknown).getValoracions();

                    //Per calcular el NDGC, ens cal ordenar les valoracions de l'usuari a Unknown per valoració.
                    //Per fer-ho, podem reutilitzar la classe itemValoracioEstimada
                    ArrayList<ItemValoracioEstimada> val_unknown = new ArrayList<ItemValoracioEstimada>(0);
                    for (int val_idx = 0; val_idx < val_unknown_aux.size(); ++val_idx) {
                        val_unknown.add(new ItemValoracioEstimada
                        (val_unknown_aux.get(val_idx).getVal(), val_unknown_aux.get(val_idx).getItem()));
                    }
                    Collections.sort(val_unknown);

                    items_recomanats = algorisme.run_algorithm(id_unknown, items, usuaris, recomanacions);
                    int DCG_user = 0;

                    for (int val_idx = 0; val_idx < val_unknown.size(); ++val_idx) {
                        Item item_unknown = val_unknown.get(val_idx).item;

                        //System.out.println(val_unknown.get(val_idx).valoracioEstimada);

                        int i = 0;
                        while (i < items_recomanats.size() && items_recomanats.get(i).item != item_unknown) ++i;
                        
                        ++i; //the first index is 1. zero gives infinity and we wouldn't want to claim our algorithm is infinitely good.

                        if (i < items_recomanats.size()) {
                            DCG_user += (int) Math.round((Math.pow(2, val_unknown.get(val_idx).valoracioEstimada) - 1)/(Math.log(i+1)/Math.log(2)));
                            IDCG += Math.round((Math.pow(2, val_unknown.get(val_idx).valoracioEstimada) - 1)/(Math.log(val_idx+1+1)/Math.log(2)));
                        }                        
                    }

                    System.out.println("L'usuari " +id_unknown+ " contribueix " +DCG_user+ " al DCG.");
                    System.out.println("("+(idx_unknown+1)+"/"+usuarisUnknown.size()+")");
                    System.out.println();

                    DCG += DCG_user;
                }

                System.out.println("DCG TOTAL: " +DCG);
                System.out.println("IDCG TOTAL: " +IDCG);
                System.out.println("NORMALIZED DCG : " +DCG/IDCG);
                break;
        }

        //int user_ID = usuaris.get(0).getId();
        //algorisme.run_algorithm(user_ID, items, usuaris, recomanacions);

        in.close();
    }
}
