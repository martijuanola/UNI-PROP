package src.drivers;

import java.util.ArrayList;
//Utils
import java.util.Scanner;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.sound.sampled.Control;

//Classes
import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.Item;
import src.recomanador.domini.Item.tipus;
import src.recomanador.domini.Utils.StringOperations;
import src.recomanador.excepcions.FileNotFoundException;
import src.recomanador.excepcions.FileNotValidException;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.ItemTypeNotValidException;
import src.recomanador.persistencia.ControladorPersistencia;

public class DriverConjuntItems {
	static private Scanner scanner;
    
    static private ConjuntItems ci;
	static private boolean inicailitzat;
	static private ControladorPersistencia cp;
    
    public static void main(String[] args) {
   		scanner = new Scanner(System.in);
		inicailitzat = false;
		cp = new ControladorPersistencia();
		
		String s = "Options: \n" +
		"-1. exit\n" +
		"0. show options\n" +
		" - Constructors - \n" +
		"1. ConjuntItems(ArrayList<ArrayList<String>> items)\n" +
		"2. ConjuntItems(items, pesos, tipusAtribut, id, nomA, nom, maxAtributs, minAtributs)\n" +
		" - Getters - \n" +
		"3. option 4\n" +
		"4. option 5\n" +
		"5. option 5\n" +
		"6. option 5\n" +
		" - Setters - \n" +
		"7. option 5\n" +
		"8. option 5\n" +
		" - Altres - \n" +
		"9. option 5\n" +
		"10. option 5\n" +
		"11. option 6\n";
		
		System.out.println("Testing class <NOM_CLASSE>");
		System.out.println(s);
		int x;
		do {
			System.out.println("\n--------------------------------------------------\n");
			System.out.println("Enter the number of the function you want to test. \n");
			System.out.print("Option: ");
			x = scanner.nextInt();
			
			switch(x)
			{
				case 0:
					System.out.println(s);
					break;
				case 1:
					mostra_1();
					break;
				case 2:
					mostra_2();
					break;
				case 3:
					mostra_3();
					break;
				case 4:
					mostra_4();
					break;
				case 5:
					mostra_5();
					break;
				case 6:
					mostra_6();
					break;
				default:
			}
			
		} while (x != -1);
		
		System.out.println("Test ended.");
    }
    
    static private void mostra_1() {
		String res = "y";
		if (inicailitzat) {
			System.out.println("WARNING!: Class ConjuntItems has already been initialized, all the previous data will be errased.");
			System.out.println("Are you sure you want to continue? yes/no");
			res = scanner.next();
		}
		if (res.equalsIgnoreCase("y") || res.equalsIgnoreCase("yes")) {
			System.out.println("Testing function ConjuntItems(ArrayList<ArrayList<String>> items)");

			//demanar l'input
			System.out.println("Enter (relative) path to a \"items.csv\" file: ");
			String path = scanner.next();

			//executar la funcionalitat
			try {
				ci = new ConjuntItems(cp.carregarFitxerExtern(path));
			} catch (ItemTypeNotValidException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			} catch (FileNotValidException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
			System.out.println("New ConjuntItems has been initialised with the data from " + path + "/items.csv");
			inicailitzat = true;
			//mostrar output
			printCurrentState();
		}
	}

	static private void mostra_2() {
		String res = "y";
		if (inicailitzat) {
			System.out.println("WARNING!: Class ConjuntItems has already been initialized, all the previous data will be errased.");
			System.out.println("Are you sure you want to continue? yes/no");
			res = scanner.next();
		}
		if (res.equalsIgnoreCase("y") || res.equalsIgnoreCase("yes")) {
			System.out.println("Testing function ConjuntItems(ArrayList<ArrayList<String>> items)");

			//demanar l'input
			System.out.println("Choose one of this folders with preprocessed data:");
			System.out.println(cp.llistatCarpetes());
			String path = "testing-load-basic";//scanner.next();

			//executar la funcionalitat
			try {
				cp.escollirProjecte(path);
			}
			catch (FolderNotFoundException e1) {
				System.out.println("ERROR: " + e1.getMessage());
				return;
			}
			if (!cp.existeixenDadesPreprocesades()) {
				System.out.println("ERROR: No preprocessed data found");
				return;
			}

			try {
				ArrayList<String> pesosS = cp.carregarPesosAtributs();
				ArrayList<Float> pesos = new ArrayList<>();
				for (int i = 0; i < pesosS.size(); ++i) {
					pesos.add(Float.parseFloat(pesosS.get(i)));
				}
				System.out.println("HOLA!");
				ArrayList<String> tipusS = cp.carregarTipusAtributs();
				ArrayList<tipus> tipus = new ArrayList<>();
				for (int i = 0; i < tipusS.size(); ++i) {
					tipus.add(StringOperations.stringToType(tipusS.get(i)));
				}
				System.out.println("HOLA!");
				ArrayList<String> maxIS = cp.carregarMaxAtributsItems();
				ArrayList<Float> maxI = new ArrayList<>();
				for (int i = 0; i < maxIS.size(); ++i) {
					maxI.add(Float.parseFloat(maxIS.get(i)));
				}
				System.out.println("HOLA!");
				ArrayList<String> minIS = cp.carregarMinAtributsItems();
				ArrayList<Float> minI = new ArrayList<>();
				for (int i = 0; i < minIS.size(); ++i) {
					maxI.add(Float.parseFloat(minIS.get(i)));
				}
				System.out.println("HOLA!");
				ci = new ConjuntItems(cp.carregarItemsCarpeta(), pesos, tipus, 
				5, -1, "test", maxI, minI);
				System.out.println("funca");
			}
			catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}

			System.out.println("New ConjuntItems has been initialised with the data from " + path);
			inicailitzat = true;
			//mostrar output
			printCurrentState();
		}
	}
    static private void mostra_3() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_4() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_5() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_6() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}

    private static void printCurrentState() {
		System.out.println("HOLA!");
		System.out.println(ci.size() + " items loaded with " + Item.getNumAtributs() + " atributes each one");
		System.out.println("HOLA222!");
		System.out.println("name_colum type weight: ");
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            
            System.out.print(Item.getNomAtribut(i) + " " + ConjuntItems.getSTipus(i) + " " + Item.getPes(i));

            if (i != Item.getNumAtributs() - 1) System.out.print(" | ");
        }
	}
}
