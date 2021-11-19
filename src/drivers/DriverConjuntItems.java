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
import src.recomanador.excepcions.ItemNotFoundException;
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
		"1. ConjuntItems()\n" +
		"2. ConjuntItems(ArrayList<ArrayList<String>> items)\n" +
		"3. ConjuntItems(items, pesos, tipusAtribut, id, nomA, nom, maxAtributs, minAtributs)\n" +
		" - Getters - \n" +
		"4. getItem(int id)\n" +
		"5. getAllItems()\n" +
		"6. getAtributItemId(int id, int atribut)\n" +
		"7. getAtributItem(int index, int atribut)\n" +
		"8. getSTipus(int column)\n" +
		"9. getAtributItem(int index, int atribut)\n" +
		"10. getAtributItem(int index, int atribut)\n" +
		" - Setters - \n" +
		"8. option 5\n" +
		"9. option 5\n" +
		" - Altres - \n" +
		"10. option 5\n" +
		"11. option 5\n" +
		"12. option 6\n";
		
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
				case 7:
					mostra_7();
					break;
				case 8:
					mostra_8();
					break;
				case 9:
					mostra_9();
					break;
				case 10:
					mostra_10();
					break;
				case 11:
					mostra_11();
					break;
				case 12:
					mostra_12();
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
			System.out.println("Testing function ConjuntItems()");

			//demanar l'input

			//executar la funcionalitat
			try {
				ConjuntItems.nom = null;
				ConjuntItems.setMaxAtributs(null);
				ConjuntItems.setMinAtributs(null);
				Item.setPesos(null);
				Item.setTipus(null);
				Item.setNomAtributs(null);
				Item.setId(-1);
				Item.setNomA(-1);
				ci = new ConjuntItems();
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
			System.out.println("New ConjuntItems has been initialised and it's empty");
			inicailitzat = false;
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
			System.out.println("Enter (relative) path to a \"items.csv\" file: ");
			String path = scanner.next();

			//executar la funcionalitat
			try {
				ConjuntItems.nom = null;
				ConjuntItems.setMaxAtributs(null);
				ConjuntItems.setMinAtributs(null);
				Item.setPesos(null);
				Item.setTipus(null);
				Item.setNomAtributs(null);
				Item.setId(-1);
				Item.setNomA(-1);
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

	static private void mostra_3() {
		String res = "y";
		if (inicailitzat) {
			System.out.println("WARNING!: Class ConjuntItems has already been initialized, all the previous data will be errased.");
			System.out.println("Are you sure you want to continue? yes/no");
			res = scanner.next();
		}
		if (res.equalsIgnoreCase("y") || res.equalsIgnoreCase("yes")) {
			System.out.println("Testing function ConjuntItems(ArrayList<ArrayList<String>> items, ArrayList<Float> pesos, ArrayList<tipus>t ipusAtribut, int id, int nomA, String nom, ArrayList<Float> maxAtributs, ArrayList<Float> minAtributs))");

			//demanar l'input
			System.out.println("Choose one of this folders with preprocessed data:");
			System.out.println(cp.llistatCarpetes());
			String path = "test-conjuntItems";//scanner.next();

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
				ArrayList<String> tipusS = cp.carregarTipusAtributs();
				ArrayList<tipus> tipus = new ArrayList<>();
				for (int i = 0; i < tipusS.size(); ++i) {
					tipus.add(StringOperations.stringToType(tipusS.get(i)));
				}
				ArrayList<String> maxIS = cp.carregarMaxAtributsItems();
				ArrayList<Float> maxI = new ArrayList<>();
				for (int i = 0; i < maxIS.size(); ++i) {
					maxI.add(Float.parseFloat(maxIS.get(i)));
				}
				ArrayList<String> minIS = cp.carregarMinAtributsItems();
				ArrayList<Float> minI = new ArrayList<>();
				for (int i = 0; i < minIS.size(); ++i) {
					maxI.add(Float.parseFloat(minIS.get(i)));
				}
				ci = new ConjuntItems(cp.carregarItemsCarpeta(), pesos, tipus, 
				5, -1, "test", maxI, minI);
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
    
	static private void mostra_4() {
		System.out.println("Testing function getItem(int id)");

		//demanar l'input
		System.out.println("Enter an item id:");
		int id = scanner.nextInt();

		//executar la funcionalitat
		Item i;
		try {
			i = ci.getItem(id);
		} catch (ItemNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		//mostrar output
		System.out.println("Item " + id + " atributes: ");
		i.print();
	}
    
	static private void mostra_5() {
		System.out.println("Testing function getAllItems()");

		//executar la funcionalitat
		ArrayList<ArrayList<ArrayList<String>>> items = ci.getAllItems();

		//mostrar output
		System.out.println(items);
	}
    
	static private void mostra_6() {
		System.out.println("Testing function getAtributItemId(int id, int atribut)");

		//demanar l'input
		System.out.println("Enter an item id:");
		int id = scanner.nextInt();

		System.out.println("Enter a column:");
		int col = scanner.nextInt();

		//executar la funcionalitat
		ArrayList<String> res;
		try {
			res = ci.getAtributItemId(id, col);
		} catch (ItemNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
		
		//mostrar output
		System.out.println(res);
	}
    
	static private void mostra_7() {
		System.out.println("Testing function getAtributItem(int index, int atribut)");
		//demanar l'input
		System.out.println("Enter an item position in the array:");
		int id = scanner.nextInt();

		System.out.println("Enter a column:");
		int col = scanner.nextInt();

		//executar la funcionalitat
		ArrayList<String> res = ci.getAtributItem(id, col);
		
		//mostrar output
		System.out.println(res);
	}
	
	static private void mostra_8() {
		System.out.println("Testing function getSTipus(int column)");
		//demanar l'input
		System.out.println("Enter a column:");
		int col = scanner.nextInt();

		//executar la funcionalitat
		String res = ConjuntItems.getSTipus(col);

		//mostrar output
		System.out.println(res);
	}

	static private void mostra_9() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}

	static private void mostra_10() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}

	static private void mostra_11() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}

	static private void mostra_12() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}

    private static void printCurrentState() {
		if (ConjuntItems.nom != null) System.out.println(ConjuntItems.nom);
		else System.out.println("This set has no name");
		System.out.println(ci.size() + " items loaded with " + Item.getNumAtributs() + " atributes each one");
		System.out.println("name_colum type weight: ");
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            
            System.out.print(Item.getNomAtribut(i) + " " + ConjuntItems.getSTipus(i) + " " + Item.getPes(i));

            if (i != Item.getNumAtributs() - 1) System.out.print(" | ");
        }
	}
}
