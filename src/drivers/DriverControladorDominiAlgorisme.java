package src.drivers;

import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.ConjuntRecomanacions;
import src.recomanador.domini.ConjuntUsuaris;
import src.recomanador.domini.ControladorDominiAlgorisme;
import src.recomanador.domini.Item;
import src.recomanador.excepcions.*;

import src.recomanador.persistencia.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.lang.Math;

import src.recomanador.domini.ItemValoracioEstimada;

/**
 * This class is meant to test the class Algorisme.
 * To test some of its functions in an atomized manner, I temporarily
 * changed them from private to public. As I have tested these work,
 * this driver only tests the public functions, and the private ones implicitly.
 * 
 * @author Adrià F.
 */
public class DriverControladorDominiAlgorisme {

    /*----- ATRIBUTS -----*/

    static ConjuntItems items;
    static ConjuntRecomanacions recomanacions;
    static ConjuntUsuaris usuaris;
    static ControladorPersistencia cp;

    /*----- MAIN -----*/
    public static void main(String[] args) throws UserNotFoundException{

        Scanner in= new Scanner(System.in);
        ControladorDominiAlgorisme algorisme = ControladorDominiAlgorisme.getInstance();
        cp = ControladorPersistencia.getInstance();

        System.out.println();
        System.out.println("DRIVER ALGORISME");
        System.out.println();

        System.out.println("Siusplau, escull un projecte:");

        System.out.print("Disponibles: ");
        ArrayList<String> carpetes = cp.llistatCarpetes();
        for (int i = 0; i < carpetes.size(); ++i) {
            System.out.print(carpetes.get(i) + " ");
        }

        System.out.println();
        System.out.print(">>>>> ");

        Boolean bool1 = true;
        String PROJECTE = "";
        int int1 = 0;

        while (bool1) {
            PROJECTE = in.next();

            try {
                carregarProjecte(PROJECTE);
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
            catch(Exception e) {
                System.out.println("Hi ha hagut un error. Provi un altre cop.");
                System.out.print(">>>>> ");
            }
        }

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

            ArrayList<String> atributs = Item.getHeader();
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
            user_ID = in.nextInt();
                /*try {
                    usuaris.getUsuari(user_ID);
                    bool1 = false;
                }
                catch (UserNotFoundException e) {
                    System.out.println(e);
                    System.out.print(">>>>> ");
                }
                */
            System.out.println();

            algorisme.setData(items, usuaris, recomanacions);
            items_recomanats = algorisme.run_algorithm(user_ID);
            

            System.out.println("Et recomanem aquests items dels " + items.size() + " del nostre catàleg:");
            System.out.println("(ID item, puntuacio estimada)");
            for (int i = 0; i < items_recomanats.size(); ++i) {
                ItemValoracioEstimada it = items_recomanats.get(i);
                System.out.println("(" + it.item.getId() + "," + it.valoracioEstimada +")");
            }

            break;

            case 1:
                try {algorisme.set_Q(items.size()/2);}
                catch (DataNotValidException e) {System.out.println(e); break;}

                ConjuntUsuaris usuarisUnknown = new ConjuntUsuaris();
                ConjuntRecomanacions recomanacionsUnknown = new ConjuntRecomanacions();
            
                try {
                    ArrayList<ArrayList<String>> raw = cp.carregarFitxerExtern("data/" +PROJECTE+ "/ratings.test.known.csv");
                    usuaris.afegirDades(raw);
                    recomanacions.afegirDades(items,usuaris,raw);

                    raw = cp.carregarFitxerExtern("data/" +PROJECTE+ "/ratings.test.unknown.csv");
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
                algorisme.setData(items, usuaris, recomanacions);

                for(int idx_unknown = 0; idx_unknown < usuarisUnknown.size(); ++idx_unknown) {
                    int id_unknown = usuarisUnknown.get(idx_unknown).getId();
                    ConjuntRecomanacions val_unknown_aux = recomanacionsUnknown.getValoracions(usuarisUnknown.get(idx_unknown).getId());

                    //Per calcular el NDGC, ens cal ordenar les valoracions de l'usuari a Unknown per valoració.
                    //Per fer-ho, podem reutilitzar la classe itemValoracioEstimada
                    ArrayList<ItemValoracioEstimada> val_unknown = new ArrayList<ItemValoracioEstimada>(0);
                    for (int val_idx = 0; val_idx < val_unknown_aux.size(); ++val_idx) {
                        val_unknown.add(new ItemValoracioEstimada
                        (val_unknown_aux.get(val_idx).getVal(), val_unknown_aux.get(val_idx).getItem()));
                    }
                    Collections.sort(val_unknown);

                    items_recomanats = algorisme.run_algorithm(id_unknown);
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

    private static void carregarProjecte(String directory) throws FolderNotFoundException, FolderNotValidException, DataNotValidException {

        //Funció per enviar el nom de la carpeta al controlador de persistència i mirar que és valida
        cp.escollirProjecte(directory);
       
        try {
            ArrayList<ArrayList<String>> items_raw = cp.carregarItemsCarpeta();
            items = null;
            items = new ConjuntItems(items_raw);

            ArrayList<ArrayList<String>> valoracions_raw = cp.carregarRecomanacionsCarpeta();            
            usuaris = new ConjuntUsuaris(valoracions_raw);
            recomanacions = new ConjuntRecomanacions(items,usuaris,valoracions_raw);
        }
        catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return;
        }
    }
}
