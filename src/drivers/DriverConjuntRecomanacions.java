package src.drivers;

//Utils
import java.util.Scanner;
import java.util.ArrayList;

//Classes
import src.recomanador.persistencia.ControladorPersistencia;
import src.recomanador.domini.ConjuntRecomanacions;
import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.ConjuntUsuaris;
import src.recomanador.domini.Item;
import src.recomanador.domini.Item.tipus;
import src.recomanador.domini.Usuari;
import src.recomanador.domini.Recomanacio;

//Excepcions
import src.recomanador.excepcions.*;

/**
 * Driver tot test the class ConjuntRecomanacions.
 * @author     Martí J.
 */
public class DriverConjuntRecomanacions {
	static private Scanner scanner;
    
    static private ConjuntRecomanacions c;

    static private boolean class_initalised;
    static private ControladorPersistencia cp;
    static private ConjuntItems ci;
    static private Item i;
    static private ConjuntUsuaris cu;
    static private Recomanacio r;
    static private Usuari u;
    static private String s1;
    static private String s2;
    static private boolean b1;
    static private boolean b2;
    static private int n;
    static private int m;
    static private int p;
    static private float f;
    
    public static void main(String[] args) {
   		scanner = new Scanner(System.in);
   		cp = new ControladorPersistencia();
		class_initalised = false;
		
		String s = "Options: \n" +
		"-1. exit\n" +
		"0. show options\n" +
		" - Constructors - \n" +
		"1. ConjuntRecomanacions()\n" +
		"2. ConjuntRecomanacions(ConjuntItems, ConjuntUsuaris, ArrayList<ArrayList<String>>)\n" +
		" - Getters - \n" +
		"3. boolean existeixRecomanacio(int, int)\n" +
		"4. boolean existeixValoracio(int, int)\n" +
		"5. Recomanacio getRecomanacio(int,int)\n" +
		"6. ConjuntRecomanacions getRecomanacions(int userid)\n" +
		"7. ConjuntRecomanacions getRecomanacionsNoValorades(int userid)\n" +
		"8. ConjuntRecomanacions getValoracions(int userid)\n" +
		" - Setters - \n" +
		"9. boolean add(Recomanacio)\n" +
		"10. void afegirDades(ConjuntItems, ConjuntUsuaris, ArrayList<ArrayList<String>>)\n" +
		" - Altres - \n" +
		"11. int cercaBinaria(int,int)";
		
		System.out.println("Testing class ConjuntRecomanacions");
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
				default:
			}
			
		} while (x != -1);
		
		System.out.println("Test ended.");
    }
    
    static private void mostra_1() {
		System.out.println("Testing function ConjuntRecomanacions()");
		
		//inicalitzar statics item
		if(class_initalised) Item.resetStatics();
		ArrayList<String> as = new ArrayList<String>();
		as.add("id");
		try{ Item.inicialitzarStaticsDefault(as); }
		catch(Exception e) {System.out.println("ERROR: " + e.getMessage());return;}

		c = new ConjuntRecomanacions();
		class_initalised = true;
		System.out.print("New ConjuntRecomanacions has been initialised without any data.\n");
	}
    static private void mostra_2() {
		System.out.println("Testing function ConjuntRecomanacions(ConjuntItems, ConjuntUsuaris, ArrayList<ArrayList<String>>)");
		
		System.out.print("Enter (relative) path to a rating file: ");
		s1 = scanner.next();

		System.out.print("Enter (relative) path to a items file: ");
		s2 = scanner.next();

		if(class_initalised) Item.resetStatics();
		try {
            ci = new ConjuntItems(cp.carregarFitxerExtern(s2));
        }
        catch(Exception e) {
            System.out.print("ERROR: " + e.getMessage());
            return;
        }
        ArrayList<ArrayList<String>> raw;
        try {
        	raw = cp.carregarFitxerExtern(s1);
            cu = new ConjuntUsuaris(raw);
        }
        catch(FileNotValidException | FileNotFoundException | DataNotValidException | UserIdNotValidException e) {
            System.out.print("ERROR: " + e.getMessage());
            return;
        }

		System.out.println("Cjt aux inicailitzats");

        try {
       		c = new ConjuntRecomanacions(ci,cu,raw);
       	}
       	catch(ItemNotFoundException | UserNotFoundException | RatingNotValidException | UserIdNotValidException | ItemIdNotValidException e) {
       		System.out.print("ERROR: " + e.getMessage());
            return;
       	}

       	class_initalised = true;
		System.out.print("New ConjuntRecomanacions has been initialised with the data from the 2 files\n");
		
		printCR();
	}
    static private void mostra_3() {
		System.out.println("Testing function boolean existeixRecomanacio(int, int) & boolean existeixRecomanacio(Item, Usuari)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Item ID: ");
		m = scanner.nextInt();
		
		try {
			i = new Item(m);
		}
		catch(ItemStaticValuesNotInitializedException e) {
            System.out.println("ERROR: " + e.getMessage());
			return;
		}
		
		System.out.print("User ID: ");
		n = scanner.nextInt();
		u = new Usuari(n);
		
		b1 = c.existeixRecomanacio(m,n);
		
		if(b1) System.out.println("The Recomanacio with ITEM id " + m + " and USER ID " + n + " IS in the set.");
		else System.out.println("The Recomanacio with ITEM id " + m + " and USER ID " + n + " IS NOT in the set.");
	}
    static private void mostra_4() {
		System.out.println("Testing function boolean existeixRecomanacio(int, int) & boolean existeixRecomanacio(Item, Usuari)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Item ID: ");
		m = scanner.nextInt();
		
		try {
			i = new Item(m);
		}
		catch(ItemStaticValuesNotInitializedException e) {
            System.out.println("ERROR: " + e.getMessage());
			return;
		}
		
		System.out.print("User ID: ");
		n = scanner.nextInt();
		u = new Usuari(n);
		
		b1 = c.existeixValoracio(m,n);

		if(b1) System.out.println("The rating of a the recommendation with ITEM id " + m + " and USER ID " + n + " IS in the set.");
		else System.out.println("The rating of a the recommendation with ITEM id " + m + " and USER ID " + n + " IS NOT in the set.");
	}
    static private void mostra_5() {
		System.out.println("Testing function Recomanacio getRecomanacio(int,int) & Recomanacio getRecomanacio(Item, Usuari)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Item ID: ");
		m = scanner.nextInt();
		
		try {
			i = new Item(m);
		}
		catch(ItemStaticValuesNotInitializedException e) {
            System.out.println("ERROR: " + e.getMessage());
			return;
		}
		
		System.out.print("User ID: ");
		n = scanner.nextInt();
		u = new Usuari(n);

		Recomanacio r1;
		try {
			r1 = c.getRecomanacio(m,n);
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
		
		System.out.println("The recommendation with ITEM id " + m + " and USER ID " + n + " has been obtained from the set. It's rating is " + r1.getVal() + ".");
	}

	static private void mostra_6() {

	}
	static private void mostra_7() {
		
	}
	static private void mostra_8() {
		
	}

//falten aquestes
	static private void mostra_9() {
		System.out.println("Testing function boolean add(Recomanacio)");
		System.out.println("If previous data was loaded, the fact that the Item or Usuari exisist is not checked.");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Item ID: ");
		m = scanner.nextInt();
		
		try {
			i = new Item(m);
		}
		catch(ItemStaticValuesNotInitializedException e) {
            System.out.println("ERROR: " + e.getMessage());
			return;
		}
		
		System.out.print("User ID: ");
		n = scanner.nextInt();
		u = new Usuari(n);

		System.out.print("Rating: ");
		f = scanner.nextFloat();

		try{
			r = new Recomanacio(u,i,f);
		}
		catch(RatingNotValidException e) {
			System.out.println("ERROR: "+ e.getMessage());
			return;
		}

		c.add(r);

		System.out.print("New Recomanacio has been added to the already existing ones.\n");
		printCR();
	}
	static private void mostra_10() {
		System.out.println("Testing function ConjuntUsuaris void afegirDades(ConjuntItems, ConjuntUsuaris, ArrayList<ArrayList<String>>)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Enter (relative) path to a rating file: ");
		s1 = scanner.next();

		System.out.print("Enter (relative) path to a items file: ");
		s2 = scanner.next();

		try {
            ci = new ConjuntItems(cp.carregarFitxerExtern(s2));
        }
        catch(Exception e) {
            System.out.print("ERROR: " + e.getMessage());
            return;
        }
        ArrayList<ArrayList<String>> raw;
        try {
        	raw = cp.carregarFitxerExtern(s1);
            cu = new ConjuntUsuaris(raw);
        }
        catch(FileNotValidException | FileNotFoundException | DataNotValidException | UserIdNotValidException e) {
            System.out.print("ERROR: " + e.getMessage());
            return;
        }

        try {
       		c.afegirDades(ci,cu,raw);
       	}
       	catch(ItemNotFoundException | UserNotFoundException | RatingNotValidException | UserIdNotValidException | ItemIdNotValidException e) {
       		System.out.print("ERROR: " + e.getMessage());
            return;
       	}

		System.out.print("New data has been added to the ConjuntRecomanacions fromt the 2 files.\n");
		printCR();
	}
	static private void mostra_11() {
		System.out.println("int cercaBinaria(int,int)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		System.out.print("Item ID: ");
		n = scanner.nextInt();

		System.out.print("User ID: ");
		m = scanner.nextInt();

		p = c.cercaBinaria(n, m);
		System.out.println("The Recomanacio with ITEM id " + n + " and USER ID " + m + " is or should be added in the position of the array " + p);
		printCR();
	}

	static private void printCR() {
		System.out.println("Current state of the set(only top 50):");
		for(int j = 0; j < c.size() && j < 50; j++) {
			System.out.println("Position "+ j + ": ID Item = " + c.get(j).getItem().getId() + " & ID Usuari = " + c.get(j).getUsuari().getId() + " & Rating = " + c.get(j).getVal());
		}

		System.out.println();
	}
}
