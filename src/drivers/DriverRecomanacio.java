package src.drivers;

import java.util.Scanner;
import java.util.ArrayList;

import src.recomanador.domini.Recomanacio;
import src.recomanador.domini.Item;
import src.recomanador.domini.Item.tipus;
import src.recomanador.domini.Usuari; //S'haurà de canviar per fer l'executable!!!!!!
//import src.stubs.Usuari;

import src.recomanador.excepcions.*;

/**
 * Driver tot test the class Recomanacio.
 * @author     Martí J.
 */
public class DriverRecomanacio {
    
	static private Scanner scanner;

    static private Recomanacio c;

    static private boolean class_initalised;
    static private Item i;
    static private Usuari u;
    static private int n;
    static private int m;
    static private float f;

    public static void main(String[] args) {
    	scanner = new Scanner(System.in);
    	class_initalised = false;  
    	int x;

    	//inicalitzar statics item
		ArrayList<String> as = new ArrayList<String>();
		as.add("id");
		try{ Item.inicialitzarStaticsDefault(as); }
		catch(Exception e) {System.out.println("ERROR: " + e.getMessage());return;}

		String s = "Options: \n\n" +
		"-1. exit\n" +
		"0. show options\n" +
		" - Constructors - \n" +
		"1. Recomanacio(Usuari, Item)\n" +
		"2. Recomanacio(Usuari, Item, int)\n" +
		" - Setters - \n" +
		"3. void setVal(float)\n" +
		" - Getters - \n" +
		"4. Usuari getUsuari()\n" +
		"5. Item getItem()\n" +
		"6. float getVal()\n" +
		"7. boolean recomanacioValorada()\n" +
		"8. boolean checkIds(int, int)\n" +
		"9. int compareTo(Recomanacio)\n";

		
		System.out.println("Testing class Recomanacio");
		System.out.println(s);
		
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
		
		System.out.println("Test ended");
    }
    
    static private void mostra_1() {
		System.out.println("Testing function Recomanacio(Usuari, Item)");

		//demanar dades usuari i item
		System.out.println("Data for new User:");
		System.out.print("User ID: ");
		n = scanner.nextInt();
		u = new Usuari(n);
		
		System.out.println("Data for new Item:");
		System.out.print("Item ID: ");
		n = scanner.nextInt();
		
		try {
			i = new Item(n);
		}
		catch(ItemStaticValuesNotInitializedException e) {
            System.out.println("ERROR: " + e.getMessage());
			return;
		}

		
		//construir Recomanació
		c = new Recomanacio(u,i);
		class_initalised = true;

		//output
		System.out.print("New Recomanacio has been initialised with the given Item and User. Data can be checked with getter operations.\n");
	}
    static private void mostra_2() {
		System.out.println("Testing function Recomanacio(Usuari, Item, int)");

		//demanar dades usuari i item
		System.out.println("Data for new User:");
		System.out.print("User ID: ");
		n = scanner.nextInt();
		u = new Usuari(n);
		
		System.out.println("Data for new Item:");
		System.out.print("Item ID: ");
		n = scanner.nextInt();
		
		try {
			i = new Item(n);
		}
		catch(ItemStaticValuesNotInitializedException e) {
            System.out.println("ERROR: " + e.getMessage());
			return;
		}

		System.out.print("Rating value: ");
		f = scanner.nextFloat();
		
		//construir Recomanació
		try {
			c = new Recomanacio(u,i,f);
		}
		catch(DataNotValidException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
		class_initalised = true;

		System.out.print("New Recomanacio has been initialised with the given Item, User and rating. Data can be checked with getter operations.\n");
	}
    static private void mostra_3() {
		System.out.println("Testing function void setVal(float)");
		if(!class_initalised) {
			System.out.println("!! Recomendation not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
		System.out.print("New rating: ");
		f = scanner.nextFloat();

		try {
			c.setVal(f);
		}
		catch(RatingNotValidException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
		System.out.println("Rating changed. Data can be checked with getter operations.");
	}
    static private void mostra_4() {
		System.out.println("Testing function Usuari getUsuari()");
		if(!class_initalised) {
			System.out.println("!! Recomendation not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		n = c.getUsuari().getId();
		System.out.println("The recommendation has a User with ID=" + n + ".");
	}
    static private void mostra_5() {
		System.out.println("Testing function Item getItem()");
		if(!class_initalised) {
			System.out.println("!! Recomendation not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		n = c.getItem().getId();
		System.out.println("The recommendation has an Item with ID=" + n + ".");
	}
    static private void mostra_6() {
		System.out.println("Testing function float getVal()");
		if(!class_initalised) {
			System.out.println("!! Recomendation not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		f = c.getVal();
		System.out.println("The recommendation has rating with value " + f + ".");
	}
	static private void mostra_7() {
		System.out.println("Testing function boolean recomanacioValorada()");
		if(!class_initalised) {
			System.out.println("!! Recomendation not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		if(c.recomanacioValorada()) System.out.println("The recommendation IS rated by the user.");
		else System.out.println("The recommendation IS NOT rated by the user.");
	}
	static private void mostra_8() {
		System.out.println("Testing function boolean checkIds(int, int)");
		if(!class_initalised) {
			System.out.println("!! Recomendation not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
		System.out.print("User ID: ");
		n = scanner.nextInt();

		System.out.print("Item ID: ");
		m = scanner.nextInt();

		if(c.checkIds(m,n)) System.out.println("The Item and User IDs of the recommendation coincide with the once entered.");
		else System.out.println("The Item and User IDs of the recommendation do NOT coincide with the once entered.");
	}
	static private void mostra_9() {
		System.out.println("Testing function int compareTo(Recomanacio)");
		System.out.println("Sorting in ascendent value of Item ID(primary) and User ID.");
		if(!class_initalised) {
			System.out.println("!! Recomendation not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		//demanar dades usuari i item
		System.out.print("Data for new Recommendation:\n");
		System.out.print("Data for new User:\n");
		System.out.print("User ID: ");
		n = scanner.nextInt();
		u = new Usuari(n);
		
		System.out.print("Data for new Item:\n");
		System.out.print("Item ID: ");
		n = scanner.nextInt();
		
		try {
			i = new Item(n);
		}
		catch(ItemStaticValuesNotInitializedException e) {
            System.out.println("ERROR: " + e.getMessage());
			return;
		}

		System.out.print("Rating: ");
		f = scanner.nextFloat();

		Recomanacio rr;
		try {
			rr = new Recomanacio(u,i,f);
		}
		catch(DataNotValidException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
		n = c.compareTo(rr);

		if(n < 0) System.out.println("The new recommendation has a greater index than the current.");
		else if(n > 0) System.out.println("The new recommendation has a lower index than the current.");
		else System.out.println("Both recommendations would have the same index in the sorting.");
	}
}
