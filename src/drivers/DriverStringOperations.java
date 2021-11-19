package src.drivers;

//Utils
import java.util.Scanner;

//Classes
import src.recomanador.domini.Utils.StringOperations;
import src.recomanador.domini.ConjuntItems;
import src.recomanador.domini.Item.tipus;

//Excepcions
import java.io.IOException;

public class DriverStringOperations {
	//static private Scanner scanner;
    
    static private StringOperations c;

    /*static private boolean class_initalised;
    static private String s1;
    static private String s2;
    static private boolean b;
    static private int n;
    static private int m;
    static private float f;*/
    
    public static void main(String[] args) {
   		Scanner scanner = new Scanner(System.in);
		//class_initalised = false;
		
		String s = "Options: \n" +
		"-1. exit\n" +
		"0. show options\n" +
		"1. is natural number (string)\n" +
		"2. is natural number (char)\n" +
		"3. is float (string)\n" +
		"4. is date (string)\n" +
		"5. is boolean (string)\n" +
		"6. option 6\n";
		
		System.out.println("Testing class StringOperations");
		System.out.println(s);
		int x = -2;
		do {
			System.out.println("\n--------------------------------------------------\n");
			System.out.println("Enter the number of the function you want to test. \n");
			System.out.println("Options: \n-1. exit\n0. show options\n");
			System.out.print("Option: ");
			
			try{ x = scanner.nextInt(); }
			catch (Exception e) {
				System.out.println("Please, try again, this time with a number.");
				x = -2;
			}
			
			switch(x)
			{
				case 0:
					System.out.println(s);
					break;
				case 1:
					testEsNombreString();
					break;
				case 2:
					testEsNombreChar();
					break;
				case 3:
					testEsFloat();
					break;
				case 4:
					testEsData();
					break;
				case 5:
					testEsBool();
					break;
				case 6:
					//mostra_6();
					break;
				case 7:
					//mostra_5();
					break;
				case 8:
					//mostra_5();
					break;
				case 9:
					//mostra_5();
					break;
				case 10:
					//mostra_6();
					break;
				default:
			}
			
		} while (x != -1);
		
		System.out.println("Test ended.");
    }
    
    static private void testEsNombreString() {
		System.out.println("Testing function esNombre(String)");
		//demanar l'input
		System.out.println("Write something. If it's a natural number without " +
			"any points or exponent, it will return tell you that it's a number.");
		
		System.out.print("Your input: ");
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		
		//executar la funcionalitat
		if (c.esNombre(s)) System.out.println("YES! It's a number");
		else System.out.println("NO! It's not a number");
		
		//mostrar output
	}
    static private void testEsNombreChar() {
		System.out.println("Testing function esNombre(char)");
		//demanar l'input
		System.out.println("Write 1 character. It will return if it's a number " +
			"between 0 and 9");
		
		System.out.print("Your input: ");
		Scanner scanner = new Scanner(System.in);
		char n = scanner.nextLine().charAt(0);
		
		//executar la funcionalitat
		if (c.esNombre(n)) System.out.println("YES! It's a number");
		else System.out.println("NO! It's not a number");
		
		//mostrar output
	}
    static private void testEsFloat() {
		System.out.println("Testing function esFloat()");
		//demanar l'input
		System.out.println("Write something. It will return if it's a floating " +
			"point number (float). integers can also be interpreted as floats.");
		
		System.out.print("Your input: ");
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		
		//executar la funcionalitat
		if (c.esFloat(s)) System.out.println("YES! It's a float");
		else System.out.println("NO! It's not a float");
	}
    static private void testEsData() {
		System.out.println("Testing function esData()");
		//demanar l'input	
		System.out.println("Write something. It will return if it's a date.");
		System.out.println("It will be considered as a date if it's on one of the following formats:");
		System.out.println("\tDD-MM-YYYY");
		System.out.println("\tDD/MM/YYYY");
		System.out.println("\tYYYY-MM-DD");
		System.out.println("\tYYYY/MM/DD");
		
		System.out.print("Your input: ");
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		//executar la funcionalitat
		if (c.esData(s)) System.out.println("YES! It's a date");
		else System.out.println("NO! It's not a date");
		
	}
    static private void testEsBool() {
		System.out.println("Testing function esBool()");
		//demanar l'input	
		System.out.println("Write something. It will return if it's a bolean.");
		System.out.println("It will be considered as a boolean if it's on " + 
			"'true' or 'false' (ignoring uppercases).");
		
		System.out.print("Your input: ");
		Scanner scanner = new Scanner(System.in);
		String s = scanner.nextLine();
		//executar la funcionalitat
		if (c.esBool(s)) System.out.println("YES! It's a boolean");
		else System.out.println("NO! It's not a boolean");
	}
    static private void mostra_6() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
}
