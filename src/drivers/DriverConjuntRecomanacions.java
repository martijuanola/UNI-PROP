package src.drivers;

//Utils
import java.util.Scanner;

//Classes
import src.recomanador.persistencia.ControladorPersistencia;
import src.recomanador.domini.ConjuntRecomanacions;
import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.ConjuntUsuaris;
import src.recomanador.domini.Item;
import src.recomanador.domini.Usuari;

//Excepcions
import java.io.IOException;
import src.recomanador.excepcions.RecommendationNotFoundException;
import src.recomanador.excepcions.ItemNotFoundException;
import src.recomanador.excepcions.UserNotFoundException;
import src.recomanador.excepcions.RatingNotValidException;
import src.recomanador.excepcions.UserIdNotValidException;
import src.recomanador.excepcions.ItemIdNotValidException;

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
		"3. boolean existeixRecomanacio(Item, Usuari)\n" +
		"4. boolean existeixValoracio(int, int)\n" +
		"4. boolean existeixValoracio(Item, Usuari)\n" +
		"5. Recomanacio getRecomanacio(int,int)\n" +
		"5. Recomanacio getRecomanacio(Item, Usuari)\n" +
		"6. ConjuntUsuaris usuarisRecomanats(Item)\n" +
		" - Setters - \n" +
		"7. boolean add(Recomanacio)\n" +
		"8. void afegirDades(ConjuntItems, ConjuntUsuaris, ArrayList<ArrayList<String>>)\n" +
		" - Altres - \n" +
		"9. int cercaBinaria(int,int)";
		
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
				default:
			}
			
		} while (x != -1);
		
		System.out.println("Test ended.");
    }
    
    static private void mostra_1() {
		System.out.println("Testing function ConjuntRecomanacions()");
		
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

		try {
            ci = new ConjuntItems(cp.carregarFitxerExtern(s2));
        }
        catch(Exception e) {
            //s'han de mirar les que pugen
        }

		//carrega 2 fitxers
		//inicialitzar items
		//inicalitzar usuaris
		
		//inicalitzar recomanacions

	}
    static private void mostra_3() {
		System.out.println("Testing function boolean existeixRecomanacio(int, int) & boolean existeixRecomanacio(Item, Usuari)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Item ID: ");
		m = scanner.nextInt();
		i = new Item(m);
		
		System.out.print("User ID: ");
		n = scanner.nextInt();
		u = new Usuari(n);
		
		b1 = c.existeixRecomanacio(m,n);
		b2 = c.existeixRecomanacio(i,u);

		if(b1 != b2) System.out.println("ERROR: either 'boolean existeixRecomanacio(int, int)' or 'boolean existeixRecomanacio(Item, Usuari)' is not working correctlly.");
		else {
			if(b1) System.out.println("The Recomanacio with ITEM id " + m + " and USER ID " + n + " IS in the set.");
			else System.out.println("The Recomanacio with ITEM id " + m + " and USER ID " + n + " IS NOT in the set.");
		}
	}
    static private void mostra_4() {
		System.out.println("Testing function boolean existeixValoracio(int, int) & boolean existeixValoracio(Item, Usuari)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}


	}
    static private void mostra_5() {
		System.out.println("Testing function Recomanacio getRecomanacio(int,int)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		
	}
    static private void mostra_6() {
		System.out.println("Testing function ConjuntUsuaris usuarisRecomanats(Item)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		
	}
	static private void mostra_7() {
		System.out.println("Testing function boolean add(Recomanacio)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		
	}
	static private void mostra_8() {
		System.out.println("Testing function ConjuntUsuaris void afegirDades(ConjuntItems, ConjuntUsuaris, ArrayList<ArrayList<String>>)");
		if(!class_initalised) {
			System.out.println("!! ConjuntRecomanacions not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		
	}
	static private void mostra_9() {
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
		System.out.println("Current state of the set(Users IDs)");
		for(int i = 0; i < c.size(); i++) {
			System.out.println("Position "+ i + ": ID Item = " + c.get(i).getItem().getId() + " & ID Usuari = " + c.get(i).getUsuari().getId());
		}
		System.out.println();
	}
}
