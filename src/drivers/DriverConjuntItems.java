package src.drivers;

import java.util.ArrayList;
//Utils
import java.util.Scanner;
//Classes
import src.recomanador.Utils.StringOperations;
import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.Item;
import src.recomanador.domini.Item.tipus;
import src.recomanador.excepcions.ConjuntItemsAtributeNotInitializedException;
import src.recomanador.excepcions.FileNotValidException;
import src.recomanador.excepcions.FolderNotFoundException;
import src.recomanador.excepcions.ItemIdNotValidException;
import src.recomanador.excepcions.ItemNewAtributesNotValidException;
import src.recomanador.excepcions.ItemNotFoundException;
import src.recomanador.excepcions.ItemStaticValuesAlreadyInitializedException;
import src.recomanador.excepcions.ItemStaticValuesNotInitializedException;
import src.recomanador.excepcions.ItemTypeNotValidException;
import src.recomanador.persistencia.ControladorPersistencia;

public class DriverConjuntItems {
	static private Scanner scanner;
    
    static private ConjuntItems ci;
	static private boolean inicialitzat;
	static private ControladorPersistencia cp;
	static private boolean nom;
	static private boolean tip;
	static private boolean pes;
	static private boolean min;
	static private boolean max;
    
    public static void main(String[] args) {
   		scanner = new Scanner(System.in);
		inicialitzat = false;
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
		"6. getMaxAtributs()\n" +
		"7. getMinAtributs()\n" +
		" - Setters - \n" +
		"8. setMaxAtributs(ArrayList<Float> maxAtributs)\n" +
		"9. setMinAtributs(ArrayList<Float> minAtributs)\n" +
		" - Checks - \n" +
		"10. tipusCorrecteColumna(int columna, tipus t)\n" +
		"11. existeixItem(int id)\n" +
		"12. checkMaxMin(Item it)\n" +
		" - Add / Remove - \n" +
		"13. eliminarItem(int id)\n" +
		"14. add(Item i)\n" +
		" - Operations - \n" +
		"15. computeMinMaxAtribut(int col)\n" +
		"16. inicialitzar(int nAtributs)\n" +
		"17. detectarTipusAtributs()\n" +
		"18. distanciaItem(Item i1, Item i2)\n" +
		"19. binarySearchItem(ArrayList<Item> list, int id, int lo, int hi)\n";

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
				case 13:
					mostra_13();
					break;
				case 14:
					mostra_14();
					break;
				case 15:
					mostra_15();
					break;
				case 16:
					mostra_16();
					break;
				case 17:
					mostra_17();
					break;
				case 18:
					mostra_18();
					break;
				case 19:
					mostra_19();
					break;
				default:
			}
			
		} while (x != -1);
		
		System.out.println("Test ended.");
    }
	static private void mostra_1() {
		System.out.println("Testing function ConjuntItems()");
		String res = "y";
		if (inicialitzat) {
			System.out.println("WARNING!: Class ConjuntItems has already been initialized, all the previous data will be errased.");
			System.out.println("Are you sure you want to continue? yes/no");
			res = scanner.next();
		}
		if (res.equalsIgnoreCase("y") || res.equalsIgnoreCase("yes")) {

			//demanar l'input

			//executar la funcionalitat
			try {
				ci = new ConjuntItems();
				ConjuntItems.nom = null;
				ci.setMaxAtributs(null);
				ci.setMinAtributs(null);
			} catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
			DriverConjuntItems.nom = false;
			DriverConjuntItems.tip = false;
			DriverConjuntItems.pes = false;
			DriverConjuntItems.min = false;
			DriverConjuntItems.max = false;
			System.out.println("New ConjuntItems has been initialised and it's empty");
			inicialitzat = true;
		}
	}

    static private void mostra_2() {
		System.out.println("Testing function ConjuntItems(ArrayList<ArrayList<String>> items)");
		String res = "y";
		if (inicialitzat) {
			System.out.println("WARNING!: Class ConjuntItems has already been initialized, all the previous data will be errased.");
			System.out.println("Are you sure you want to continue? yes/no");
			res = scanner.next();
		}
		if (res.equalsIgnoreCase("y") || res.equalsIgnoreCase("yes")) {

			//demanar l'input
			System.out.println("Enter (relative) path to a \"items.csv\" file: ");
			String path = scanner.next();

			//executar la funcionalitat
			try {
				ci = new ConjuntItems(cp.carregarFitxerExtern(path));
				ConjuntItems.nom = null;
				Item.setNomA(-1);
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
			DriverConjuntItems.nom = false;
			DriverConjuntItems.tip = true;
			DriverConjuntItems.pes = true;
			DriverConjuntItems.min = true;
			DriverConjuntItems.max = true;
			System.out.println("New ConjuntItems has been initialised with the data from " + path + "/items.csv");
			inicialitzat = true;
			//mostrar output
			printCurrentState();
		}
	}

	static private void mostra_3() {
		System.out.println("Testing function ConjuntItems(ArrayList<ArrayList<String>> items, ArrayList<Float> pesos, ArrayList<tipus>t ipusAtribut, int id, int nomA, String nom, ArrayList<Float> maxAtributs, ArrayList<Float> minAtributs))");
		String res = "y";
		if (inicialitzat) {
			System.out.println("WARNING!: Class ConjuntItems has already been initialized, all the previous data will be errased.");
			System.out.println("Are you sure you want to continue? yes/no");
			res = scanner.next();
		}
		if (res.equalsIgnoreCase("y") || res.equalsIgnoreCase("yes")) {

			//demanar l'input
			System.out.println("Choose one of this folders with preprocessed data:");
			System.out.println(cp.llistatCarpetes());
			String path = scanner.next();

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
					minI.add(Float.parseFloat(minIS.get(i)));
				}
				
				ci = new ConjuntItems(cp.carregarItemsCarpeta(), pesos, tipus, 
				cp.getPosicioID(), cp.getPosicioNom(), cp.getNomConjuntItems(), maxI, minI);
			}
			catch (Exception e) {
				System.out.println("ERROR: " + e.getMessage());
			}
			DriverConjuntItems.nom = true;
			DriverConjuntItems.tip = true;
			DriverConjuntItems.pes = true;
			DriverConjuntItems.min = true;
			DriverConjuntItems.max = true;
			System.out.println("New ConjuntItems has been initialised with the data from " + path);
			inicialitzat = true;
			//mostrar output
			printCurrentState();
		}
	}
    
	static private void mostra_4() {
		System.out.println("Testing function getItem(int id)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
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
		System.out.println("Item " + id + " attributes: ");
		System.out.println(i.getAtributs());
	}
    
	static private void mostra_5() {
		System.out.println("Testing function getAllItems()");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//executar la funcionalitat
		ArrayList<ArrayList<ArrayList<String>>> items = ci.getAllItems();

		//mostrar output
		System.out.println(items);
	}

	static private void mostra_6() {
		System.out.println("Testing function getMaxAtributs()");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		if (!max) {
			System.out.println("Maximum attribute values are not initialized yet");
			return;
		}
		//executar la funcionalitat
		ArrayList<Float> res = ci.getMaxAtributs();

		//mostrar output
		System.out.println(res);
	}

	static private void mostra_7() {
		System.out.println("Testing function getMinAtributs()");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		if (!min) {
			System.out.println("Minimum attribute values are not initialized yet");
			return;
		}
		//executar la funcionalitat
		ArrayList<Float> res = ci.getMinAtributs();

		//mostrar output
		System.out.println(res);
	}

	static private void mostra_8() {
		System.out.println("Testing function setMaxAtributs(ArrayList<Float> maxAtributs)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		int n = Item.getNumAtributs();
        System.out.println("Enter " + n + " floats in diferent lines:");
		ArrayList<Float> l1 = new ArrayList<Float>();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
            l1.add(scanner.nextFloat());
		}

		//executar la funcionalitat
		ci.setMaxAtributs(l1);

		//mostrar output
		max = true;
		System.out.println("Max attributes updated");
		printCurrentState();
	}

	static private void mostra_9() {
		System.out.println("Testing function setMinAtributs(ArrayList<Float> minAtributs)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		int n = Item.getNumAtributs();
        System.out.println("Enter " + n + " floats in diferent lines:");
		ArrayList<Float> l1 = new ArrayList<Float>();
		scanner.nextLine();
		for (int i = 0; i < n; ++i) {
            l1.add(scanner.nextFloat());
		}
		
		//executar la funcionalitat
		ci.setMinAtributs(l1);

		//mostrar output
		min = true;
		System.out.println("Min attributes updated");
		printCurrentState();
	}

	static private void mostra_10() {
		System.out.println("Testing function tipusCorrecteColumna(int columna, tipus t)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		System.out.println("Enter a column to check:");
		int col = scanner.nextInt();

		System.out.println("Enter the type to check in column " + col + ":");
		System.out.println("I / N / B / F / D / S");
		System.out.println("Identificador / Nom / Boolean / Float / Data / String");
		String tipus = scanner.next();
		tipus t;
		try {
			t = StringOperations.stringToType(tipus);
		} catch (ItemTypeNotValidException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		//executar la funcionalitat
		boolean res = ci.tipusCorrecteColumna(col, t);

		//mostrar output
		if (res) System.out.println("Column " + col + " \"" + Item.getCapçalera().get(col) + "\" could be treated as a " + StringOperations.tipusToString(t));
		else System.out.println("Column " + col + " \"" + Item.getCapçalera().get(col) + "\" is not of type " + StringOperations.tipusToString(t));
	}

	static private void mostra_11() {
		System.out.println("Testing function existeixItem(int id)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		System.out.println("Enter an item id to check:");
		int id = scanner.nextInt();

		//executar la funcionalitat
		boolean res = ci.existeixItem(id);

		//mostrar output
		if (res) System.out.println("Item with id " + id + " exists");
		else System.out.println("Item with id " + id + " does not exist");
	}

	static private void mostra_12() {
		System.out.println("Testing function checkMaxMin(Item it)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		System.out.println("To check if an item is max or minimum we will first have to create it");
		System.out.println("Enter " + Item.getNumAtributs() + " attributes for the item");
		System.out.println("If an attribute has several parts, please divide it with \";\"");

		ArrayList<ArrayList<String>> atr = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < Item.getNumAtributs(); ++i) {
			System.out.println("Enter a " + StringOperations.tipusToString(Item.getTipusArray().get(i)) + ":");
			String a = scanner.next();

			ArrayList<String> subatr = new ArrayList<String>();
			subatr = StringOperations.divideString(a, ';');
			atr.add(subatr);
		}

		//executar la funcionalitat
		Item it;
		try {
			it = new Item(atr);
			ci.checkMaxMin(it);
		} catch (ItemStaticValuesNotInitializedException | ItemNewAtributesNotValidException | ConjuntItemsAtributeNotInitializedException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}


		//mostrar output
		printCurrentState();
	}

	static private void mostra_13() {
		System.out.println("Testing function eliminarItem(int id)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		System.out.println("Enter an id to delete:");
		int id = scanner.nextInt();

		//executar la funcionalitat
		try {
			ci.eliminarItem(id);
		} catch (ItemNotFoundException | ItemStaticValuesNotInitializedException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		//mostrar output
		System.out.println("Item with id " + id + " has been erased");
	}

	static private void mostra_14() {
		System.out.println("Testing function add(Item i)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		System.out.println("To add an item  we will first have to create it");
		System.out.println("Enter " + Item.getNumAtributs() + " attributes for the item");
		System.out.println("If an attribute has several parts, please divide it with \";\"");

		ArrayList<ArrayList<String>> atr = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < Item.getNumAtributs(); ++i) {
			System.out.println("Enter a " + StringOperations.tipusToString(Item.getTipusArray().get(i)) + ":");
			String a = scanner.next();

			ArrayList<String> subatr = new ArrayList<String>();
			subatr = StringOperations.divideString(a, ';');
			atr.add(subatr);
		}

		//executar la funcionalitat
		Item it;
		try {
			it = new Item(atr);
		} catch (ItemStaticValuesNotInitializedException | ItemNewAtributesNotValidException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
		boolean res = ci.add(it);
		//mostrar output
		if (res) System.out.println("Item has been correctly added");
		else System.out.println("Something went wrong: item has not been added");
	}

	static private void mostra_15() {
		System.out.println("Testing function computeMinMaxAtribut(int col)");
		if (!inicialitzat || !min || !max) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		System.out.println("Enter a column to calculate minimums and maximums:");
		int col = scanner.nextInt();

		//executar la funcionalitat
		try {
			ci.computeMinMaxAtribut(col);
		} catch (ItemStaticValuesNotInitializedException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		//mostrar output
		System.out.println("Minimum and maximums correctly recalculated");
		printCurrentState();
	}

	static private void mostra_16() {
		System.out.println("Testing function inicialitzar(int nAtributs)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}

		//executar la funcionalitat
		ci.inicialitzarMinMax();

		//mostrar output
		System.out.println("The set has been initialized correctly");
	}

	static private void mostra_17() {
		System.out.println("Testing function detectarTipusAtributs()");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}

		//executar la funcionalitat
		try {
			ci.detectarTipusAtributs();
		} catch (ItemTypeNotValidException | ArrayIndexOutOfBoundsException | ItemIdNotValidException | ItemStaticValuesAlreadyInitializedException e) {
			System.out.println("ERROR: " + e.getMessage());
		}

		//mostrar output
		printCurrentState();
	}

	static private void mostra_18() {
		System.out.println("Testing function distanciaItem(Item i1, Item i2)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		System.out.println("Option 1: Select two already existing items");
		System.out.println("Option 2: Create new ones only for this test");
		System.out.println("Enter 1 or 2: ");
		int option = scanner.nextInt();
		Item i1, i2;
		if (option == 1) {
			System.out.println("Enter id of the first item:");
			int id1 = scanner.nextInt();
			System.out.println("Enter id of the second item:");
			int id2 = scanner.nextInt();

			try {
				i1 = ci.getItem(id1);
			} catch (ItemNotFoundException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
			try {
				i2 = ci.getItem(id2);
			} catch (ItemNotFoundException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
		}
		else if (option == 2) {
			System.out.println("Enter " + Item.getNumAtributs() + " attributes for the first item");
			System.out.println("If an attribute has several parts, please divide it with \";\"");
	
			ArrayList<ArrayList<String>> atr1 = new ArrayList<ArrayList<String>>();
			for (int i = 0; i < Item.getNumAtributs(); ++i) {
				System.out.println("Enter a " + StringOperations.tipusToString(Item.getTipusArray().get(i)) + ":");
				String a = scanner.next();
	
				ArrayList<String> subatr = new ArrayList<String>();
				subatr = StringOperations.divideString(a, ';');
				atr1.add(subatr);
			}
			try {
				i1 = new Item(atr1);
			} catch (ItemStaticValuesNotInitializedException | ItemNewAtributesNotValidException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}

			System.out.println("Enter " + Item.getNumAtributs() + " attributes for the second item");
			System.out.println("If an attribute has several parts, please divide it with \";\"");
	
			ArrayList<ArrayList<String>> atr2 = new ArrayList<ArrayList<String>>();
			for (int i = 0; i < Item.getNumAtributs(); ++i) {
				System.out.println("Enter a " + StringOperations.tipusToString(Item.getTipusArray().get(i)) + ":");
				String a = scanner.next();
	
				ArrayList<String> subatr = new ArrayList<String>();
				subatr = StringOperations.divideString(a, ';');
				atr2.add(subatr);
			}
			try {
				i2 = new Item(atr2);
			} catch (ItemStaticValuesNotInitializedException | ItemNewAtributesNotValidException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
		}
		else {
			System.out.println("ERROR: You can only choose 1 or 2");
			return;
		}

		//executar la funcionalitat
		float res;
		try {
			res = ci.distanciaItem(i1, i2);
		} catch (ItemTypeNotValidException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		//mostrar output
		System.out.println("Distance correctly calculated: " + res);
	}

	static private void mostra_19() {
		System.out.println("Testing function binarySearchItem(ArrayList<Item> list, int id, int lo, int hi)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		System.out.println("Option 1: Select current item set");
		System.out.println("Option 2: (NOT RECOMMENDED) Create new item array only for this test");
		System.out.println("Enter 1 or 2: ");
		int option = scanner.nextInt();		
		ArrayList<Item> list = new ArrayList<Item>();
		if (option == 1) {
			list = ci;
			System.out.println("Selected current list as list");
		}
		else if (option == 2) {
			System.out.println("Enter the number of items on the new list");
			int n = scanner.nextInt();
			System.out.println("Important: Enter the items with an increasing id");
			for (int j = 0; j < n; ++j) {
				System.out.println("Enter " + Item.getNumAtributs() + " attributes for the first item");
				System.out.println("If an attribute has several parts, please divide it with \";\"");
		
				ArrayList<ArrayList<String>> atr1 = new ArrayList<ArrayList<String>>();
				for (int i = 0; i < Item.getNumAtributs(); ++i) {
					System.out.println("Enter a " + StringOperations.tipusToString(Item.getTipusArray().get(i)) + ":");
					String a = scanner.next();
		
					ArrayList<String> subatr = new ArrayList<String>();
					subatr = StringOperations.divideString(a, ';');
					atr1.add(subatr);
				}
				try {
					list.add(new Item(atr1));
				} catch (ItemStaticValuesNotInitializedException | ItemNewAtributesNotValidException e) {
					System.out.println("ERROR: " + e.getMessage());
					return;
				}
			}
		}
		else {
			System.out.println("ERROR: You can only choose 1 or 2");
			return;
		}

		System.out.println("Enter an item id to search for:");
		int id = scanner.nextInt();
		System.out.println("Enter where to start serching in the list (Recommended 0):");
		int lo = scanner.nextInt();
		System.out.println("Enter where to finish serching in the list (Recommended " + (list.size()-1) + "):");
		int hi = scanner.nextInt();

		//executar la funcionalitat
		int pos = ConjuntItems.binarySearchItem(list, id, lo, hi);

		//mostrar output
		System.out.println("The result of the binary search: " + pos);
	}

    private static void printCurrentState() {
		if (ConjuntItems.nom != null) System.out.println(ConjuntItems.nom);
		else System.out.println("This set has no name");
		System.out.println(ci.size() + " items loaded with " + Item.getNumAtributs() + " attributes each one");
		System.out.println("name_colum type weight maxAttributes minAtributes: ");
        for (int i = 0; i < Item.getNumAtributs(); ++i) {
            
            if (nom) System.out.print(Item.getCapçalera().get(i));
			if (tip) System.out.print(" " + StringOperations.tipusToString(Item.getTipusArray().get(i)));
			if (pes) System.out.print(" " + Item.getPesos().get(i));
			if (max) System.out.print(" " + ci.getMaxAtributs().get(i));
			if (min) System.out.print(" " + ci.getMinAtributs().get(i));

            if (i != Item.getNumAtributs() - 1) System.out.print(" | ");
        }
	}
}
