package src.drivers;

//Utils
import java.util.Scanner;
import java.util.ArrayList;

//Classes
import src.recomanador.domini.Usuari;
import src.recomanador.domini.Item;
import src.recomanador.domini.Item.tipus;
import src.recomanador.domini.Recomanacio;
import src.recomanador.domini.ConjuntRecomanacions;

//Excepcions
import java.io.IOException;
import src.recomanador.excepcions.RatingNotValidException;
import src.recomanador.excepcions.RecommendationNotRatedException;
import src.recomanador.excepcions.RecommendationRatedException;
import src.recomanador.excepcions.ItemStaticValuesNotInitializedException;
import src.recomanador.excepcions.ItemWeightNotCorrectException;
/**
 * Driver tot test the class Usuari.
 * @author     Martí J.
 */
public class DriverUsuari {
	static private Scanner scanner;
    
    static private Usuari c;

    static private boolean class_initalised;
    static private Recomanacio r;
    static private ConjuntRecomanacions cr;
    static private ConjuntRecomanacions cv;
    static private Usuari u;
    static private Item i;
    static private String s1;
    static private String s2;
    static private boolean b;
    static private int n;
    static private int m;
    static private int p;
    static private float f;
    
    public static void main(String[] args) throws ItemWeightNotCorrectException {
   		scanner = new Scanner(System.in);
		class_initalised = false;
		
		//inicalitzar statics item
		ArrayList<Float> af = new ArrayList<Float>();
		ArrayList<tipus> at = new ArrayList<tipus>();
		ArrayList<String> as = new ArrayList<String>();
		
		af.add(100.0f);
		at.add(tipus.I);
		as.add("id");

		Item.setTipus(at);
		Item.assignarNomAtributs(as);
		Item.setPesos(af);
		Item.setId(0);

		String s = "Options: \n" +
		"-1. exit\n" +
		"0. show options\n" +
		" - Constructors - \n" +
		"1. Usuari(int)\n" +
		"2. Usuari(int, ConjuntRecomanacions, ConjuntRecomanacions)\n" +
		" - Getters - \n" +
		"3. int getId()\n" +
		"4. ConjuntRecomanacions getRecomanacions()\n" +
		"5. ConjuntRecomanacions getValoracions()\n" +
		" - Setters - \n" +
		"6. void setRecomanacions(ConjuntRecomanacions cr)\n" +
		"7. void setValoracions(ConjuntRecomanacions cv)\n" +
		"8. void moureRecomanacio(int)\n" +
		"9. int compareTo(Usuari u2)\n";
		
		System.out.println("Testing class Usuari");
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
		System.out.println("Testing function Usuari(int)");
		
		System.out.println("Data for new User:");
		System.out.print("User ID: ");
		n = scanner.nextInt();
		
		c = new Usuari(n);
		class_initalised = true;
		
		System.out.print("New Usuari has been initialised with the given ID. Data can be checked with getter operations.\n");
	}
    static private void mostra_2() {
		System.out.println("Testing function Usuari(int, ConjuntRecomanacions, ConjuntRecomanacions)");
		
		System.out.println("Data for new User:");
		System.out.print("User ID: ");
		n = scanner.nextInt();

		cr = new ConjuntRecomanacions();
		cv = new ConjuntRecomanacions();

		System.out.println("Data for new recommendations:");
		System.out.print("Number of recommendations(without rating): ");
		m = scanner.nextInt();

		for(int j = 0; j < m; j++) {
			System.out.print("ItemID of Recomanacio " + j + ": ");
			p = scanner.nextInt();
			try {
				i = new Item(p);
			}
			catch(ItemStaticValuesNotInitializedException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
			cr.add(new Recomanacio(c, i));
		}

		System.out.println("Data for new recommendations:");
		System.out.print("Number of recommendations with rating): ");
		m = scanner.nextInt();

		for(int j = 0; j < m; j++) {
			System.out.print("ItemID of rated Recomanacio " + j + ": ");
			p = scanner.nextInt();
			System.out.print("Valoració of rated Recomanacio " + j + ": ");
			f = scanner.nextFloat();
			try {
				i = new Item(p);
				cr.add(new Recomanacio(c, i, f));
			}
			catch(RatingNotValidException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
			catch(ItemStaticValuesNotInitializedException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
		}

		c = new Usuari(n,cr,cv);
		class_initalised = true;
		
		System.out.print("New Usuari has been initialised with the given ID and sets of recommendations. Data can be checked with getter operations.\n");
	}
    static private void mostra_3() {
		System.out.println("Testing function getId()");
		if(!class_initalised) {
			System.out.println("!! Usuari not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.println("Usuari has ID=" + c.getId());
	}
    static private void mostra_4() {
		System.out.println("Testing function ConjuntRecomanacions getRecomanacions()");
		if(!class_initalised) {
			System.out.println("!! Usuari not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.println("The user has the followning recommendations:");
		printCR(c.getRecomanacions());
	}
    static private void mostra_5() {
		System.out.println("Testing function ConjuntRecomanacions getValoracions()");
		if(!class_initalised) {
			System.out.println("!! Usuari not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.println("The user has the followning rated recommendations:");
		printCR(c.getValoracions());
	}
    static private void mostra_6() {
		System.out.println("Testing function setRecomanacions(ConjuntRecomanacions cr)");
		if(!class_initalised) {
			System.out.println("!! Usuari not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		cr = new ConjuntRecomanacions();
		
		System.out.println("Data for new recommendations:");
		System.out.print("Number of recommendations(without rating): ");
		m = scanner.nextInt();

		for(int j = 0; j < m; j++) {
			System.out.print("ItemID of Recomanacio " + j + ": ");
			p = scanner.nextInt();
			try {
				i = new Item(p);
			}
			catch(ItemStaticValuesNotInitializedException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
			cr.add(new Recomanacio(c, i));
		}

		c.setRecomanacions(cr);
		System.out.println("Recomanacions:");
		printCR(cv);
	}
	static private void mostra_7() {
		System.out.println("Testing function void setValoracions(ConjuntRecomanacions cv)");
		if(!class_initalised) {
			System.out.println("!! Usuari not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		cv = new ConjuntRecomanacions();

		System.out.println("Data for new recommendations:");
		System.out.print("Number of recommendations with rating): ");
		m = scanner.nextInt();

		for(int j = 0; j < m; j++) {
			System.out.print("ItemID of rated Recomanacio " + j + ": ");
			p = scanner.nextInt();
			System.out.print("Valoració of rated Recomanacio " + j + ": ");
			f = scanner.nextFloat();
			try {
				i = new Item(p);
				cr.add(new Recomanacio(c, i, f));
			}
			catch(RatingNotValidException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
			catch(ItemStaticValuesNotInitializedException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
		}
		c.setValoracions(cv);
		System.out.println("Valoracions:");
		printCR(cv);
	}
	static private void mostra_8() {
		System.out.println("Testing function Recomanacio void moureRecomanacio(int)");
		if(!class_initalised) {
			System.out.println("!! Usuari not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Index of the recommendation that needs to be moved from (Recomanacions to Valoracions): ");
		n = scanner.nextInt();

		try {
			c.moureRecomanacio(c.getRecomanacions().get(n),true);
		}
		catch(RecommendationNotRatedException | RecommendationRatedException e) {
			System.out.println("The opperation couldn't be performed(as expected) because this functionality will only work if the recommendation had a rating.");
		}

	}
	static private void mostra_9() {
		System.out.println("Testing function int compareTo(Usuari u2)");
		if(!class_initalised) {
			System.out.println("!! Usuari not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		System.out.println("Testing function Recomanacio void moureRecomanacio(int)");
		if(!class_initalised) {
			System.out.println("!! Usuari not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.println("Data for User to be compared:");
		System.out.print("User ID: ");
		n = scanner.nextInt();

		u = new Usuari(n);

		m = c.compareTo(u);

		if(m < 0) System.out.println("The new user has a greater index than the current.");
		else if(m > 0) System.out.println("The new user has a lower index than the current.");
		else System.out.println("Both user would have the same index in the sorting.");

	}

	static private void printCR(ConjuntRecomanacions cr) {
		for(int i = 0; i < cr.size(); i++) {
			System.out.print("Recomanacio " + i + ": ItemID=" + cr.get(i).getItem().getId());
			if(cr.get(i).getVal() != Recomanacio.nul) System.out.print(" Rating=" + cr.get(i).getVal());
			System.out.println();
		}
	}
}
