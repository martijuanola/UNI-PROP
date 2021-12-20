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
import src.recomanador.excepcions.*;
/**
 * Driver tot test the class Usuari.
 * @author     Martí J.
 */
public class DriverUsuari {
	static private Scanner scanner;
    
    static private Usuari c;

    static private boolean class_initalised;
    static private ConjuntRecomanacions cr;
    static private ConjuntRecomanacions cv;
    static private Usuari u;
    static private Item i;
    static private int n;
    static private int m;
    static private int p;
    static private float f;
    
    public static void main(String[] args) throws ItemWeightNotCorrectException {
   		scanner = new Scanner(System.in);
		class_initalised = false;
		
		//inicalitzar statics item
		ArrayList<String> as = new ArrayList<String>();
		as.add("id");
		try{ Item.inicialitzarStaticsDefault(as); }
		catch(Exception e) {System.out.println("ERROR: " + e.getMessage());return;}

		String s = "Options: \n" +
		"-1. exit\n" +
		"0. show options\n" +
		" - Constructors - \n" +
		"1. Usuari(int)\n" +
		" - Getters - \n" +
		"2. int getId()\n" +
		" - Setters - \n" +
		"3. int compareTo(Usuari u2)\n";
		
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
		System.out.println("Testing function getId()");
		if(!class_initalised) {
			System.out.println("!! Usuari not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.println("Usuari has ID=" + c.getId());
	}
    
	static private void mostra_3() {
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
