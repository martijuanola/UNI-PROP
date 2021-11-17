package src.drivers;

//Util
import java.util.Scanner;
import java.util.ArrayList;

//Classes
import src.recomanador.persistencia.*;
import src.recomanador.domini.ConjuntUsuaris;
import src.recomanador.domini.Usuari; //S'haurà de canviar per fer l'executable!!!!!!
//import src.stubs.Usuari;

//Excepcions
import java.io.IOException;
import src.recomanador.excepcions.UserNotFoundException;
import src.recomanador.excepcions.UserIdNotValidException;
import src.recomanador.excepcions.DataNotValidException;
import src.recomanador.excepcions.FileNotFoundException;
import src.recomanador.excepcions.FileNotValidException;

public class DriverConjuntUsuaris {
	static private Scanner scanner;
    
    static private ConjuntUsuaris c;

    static private boolean class_initalised;
    static private Usuari u;
    static private ControladorPersistencia cp;
    static private String s1;
    static private String s2;
    static private boolean b;
    static private int n;
    static private int m;
    static private float f;
    static private ArrayList<ArrayList<String>> raw;
    
    public static void main(String[] args) {
   		scanner = new Scanner(System.in);
   		cp = new ControladorPersistencia();
		class_initalised = false;
		
		String s = "Options: \n" +
		"-1. exit\n" +
		"0. show options\n" +
		"1. ConjuntUsuaris()\n" +
		"2. ConjuntUsuaris(ArrayList<ArrayList<String>>)\n" +
		"3. existeixUsuari(int)\n" +
		"4. getUsuari(int)\n" +
		"5. add(Usuari)\n" +
		"6. afegirDades(ArrayList<ArrayList<String>>)\n" +
		"7. cercaBinaria(int)";
		
		System.out.println("Testing class ConjuntUsuaris");
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
				default:
					System.out.println("Option not available");
			}
			
		} while (x != -1);
		
		System.out.println("Test ended.");
    }
    
    static private void mostra_1() {
		System.out.println("Testing function ConjuntUsuaris()");

		c = new ConjuntUsuaris();
		class_initalised = true;
		System.out.print("New ConjuntUsuaris has been initialised without any data.\n");
	}
    static private void mostra_2() {
		System.out.println("Testing function ConjuntUsuaris(ArrayList<ArrayList<String>>)");

		System.out.print("Enter (relative) path to a rating file: ");
		s1 = scanner.next();
		
		try {
			raw = cp.carregarFitxerExtern(s1);
		}
		catch(FileNotFoundException | FileNotValidException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		try{
			c = new ConjuntUsuaris(raw);
		}
		catch(UserIdNotValidException | DataNotValidException e) {
			System.out.println("ERROR: File not valid. Contains incorrect data types or wrong format.");
			return;
		}

		class_initalised = true;
		System.out.print("New ConjuntUsuaris has been initialised without any data.\n");

		printCU();
	}
    static private void mostra_3() {
		System.out.println("Testing function existeixUsuari(int)");
		if(!class_initalised) {
			System.out.println("!! ConjuntUsuaris not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("User ID: ");
		n = scanner.nextInt();

		if(c.existeixUsuari(n)) System.out.println("The user with id "+ n +" EXISTS in the set.");
		else System.out.println("The user with id "+ n +" does NOT EXISTS in the set.");

	}
    static private void mostra_4() {
		System.out.println("Testing function getUsuari(int)");
		if(!class_initalised) {
			System.out.println("!! ConjuntUsuaris not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("User ID: ");
		n = scanner.nextInt();

		try {
			u = c.getUsuari(n);
		}
		catch(UserNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
		System.out.println("The user with id "+ n +" EXISTS in the set, and was obtained.");
	}
    static private void mostra_5() {
		System.out.println("Testing function add(Usuari)");
		if(!class_initalised) {
			System.out.println("!! ConjuntUsuaris not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.println("Data for new User:");
		System.out.print("User ID: ");
		n = scanner.nextInt();
		u = new Usuari(n);

		c.add(u);

		System.out.print("New user correctlly added.");
		printCU();
	}
    static private void mostra_6() {
		System.out.println("Testing function afegirDades(ArrayList<ArrayList<String>>)");
		if(!class_initalised) {
			System.out.println("!! ConjuntUsuaris not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Enter (relative) path to a rating file: ");
		s1 = scanner.next();
		
		try {
			raw = cp.carregarFitxerExtern(s1);
		}
		catch(FileNotValidException | FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		try{
			c.afegirDades(raw);
		}
		catch(UserIdNotValidException | DataNotValidException e) {
			System.out.println("ERROR: File not valid. Contains incorrect data types or wrong format.");
			return;
		}

		System.out.print("New users have been added to the already existing ones.\n");

		printCU();
	}
    static private void mostra_7() {
		System.out.println("Testing function cercaBinaria(int)");
		if(!class_initalised) {
			System.out.println("!! ConjuntUsuaris not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("User ID: ");
		n = scanner.nextInt();

		m = c.cercaBinaria(n);
		System.out.println("The User with id " + n + "is or should be added in the position of the array " + m);
		printCU();
	}
	

	static private void printCU() {
		System.out.println("Current state of the set(Users IDs)");
		for(int i = 0; i < c.size(); i++) {
			System.out.println("Position "+ i + ": " + c.get(i).getId());
		}
		System.out.println();
	}
}
