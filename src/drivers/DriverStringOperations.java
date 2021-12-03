package src.drivers;

//Utils
import java.util.Scanner;
import java.util.ArrayList;

import src.recomanador.Utils.StringOperations;
import src.recomanador.domini.Item.tipus;

public class DriverStringOperations {
	//StringOperations es 100% static i no té atributs
    
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
		"6. compare 2 attributes\n" +
		"7. convert a date to time\n" +
		"8. split a string\n" +
		"9. print a string VERY large (to represent an infinit string)\n" +
		"10. minimum distance among 2 strings\n";
		
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
					testCompararAtributs();
					break;
				case 7:
					testDataToTime();
					break;
				case 8:
					testDivideString();
					break;
				case 9:
					testInfinitString();
					break;
				case 10:
					testMinDis();
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
		if (StringOperations.esNombre(s)) System.out.println("YES! It's a number");
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
		if (StringOperations.esNombre(n)) System.out.println("YES! It's a number");
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
		if (StringOperations.esFloat(s)) System.out.println("YES! It's a float");
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
		if (StringOperations.esData(s)) System.out.println("YES! It's a date");
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
		if (StringOperations.esBool(s)) System.out.println("YES! It's a boolean");
		else System.out.println("NO! It's not a boolean");
	}
    static private void testCompararAtributs() {
		System.out.println("Testing function compararAtributs()");
		//demanar l'input
		System.out.println("Write 2 strings and its type. They will be" + 
			"compared as the type that you specify");
		System.out.println("NOTE: The type MUST correspond to the data given. " +
			"Otherwise it won't work properly. This requirement is set as a " +
			"precondition for the function.");
			
		Scanner scanner = new Scanner(System.in);
		System.out.print("String 1: ");
		String s1 = scanner.nextLine();
		System.out.print("String 2: ");
		String s2 = scanner.nextLine();
		
		System.out.println("Choose a number to set a type:");
		System.out.println("\t1. I -> ID");
		System.out.println("\t2. N -> Name");
		System.out.println("\t3. B -> Boolean");
		System.out.println("\t4. F -> Float");
		System.out.println("\t5. S -> String");
		System.out.println("\t6. D -> Date");
		int i = scanner.nextInt();
		tipus t = tipus.S;
		if (i == 1) t = tipus.I;
		else if (i == 2) t = tipus.N;
		else if (i == 3) t = tipus.B;
		else if (i == 4) t = tipus.F;
		else if (i == 5) t = tipus.S;
		else if (i == 6) t = tipus.D;
		
		//executar la funcionalitat
		int res = -2;
		
		try { res = StringOperations.compararAtributs(s1, s2, t); }
		catch (Exception e) 
		{
			System.out.println("ERROR!! Something went wrong");
			System.out.println("Make sure that the type is correct for the 2 of the strings");
			return;
		}
		
		//mostrar output
		if (res == -1) System.out.println(s1 + "<" + s2);
		else if (res == 0) System.out.println(s1 + "=" + s2);
		else if (res == 1) System.out.println(s1 + ">" + s2);
		else System.out.println("ERROR!!");
	}
	static private void testDataToTime() {
		System.out.println("Testing function dataToTime()");
		//demanar l'input
		System.out.println("Write a date and it will be converted into time (as days)");
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Date: ");
		String s1 = scanner.nextLine();
		
		if (!StringOperations.esData(s1))
		{
			System.out.println("That's not a valid date format.");
			System.out.println("Choose option 4 at main menu to test dates.");
			return;
		}
				
		//executar la funcionalitat
		int time = StringOperations.dataToTime(s1);
		
		//mostrar output
		System.out.println("Output: " + time);
	}
	static private void testDivideString() {
		System.out.println("Testing function divideString()");
		//demanar l'input
		System.out.println("Write a string and a character. The string will be " +
			"removed from all instances of that character, splitting the string");
		Scanner scanner = new Scanner(System.in);
		System.out.print("String: ");
		String s = scanner.nextLine();
		System.out.print("Divider char: ");
		char div = scanner.nextLine().charAt(0);
		
		//executar la funcionalitat
		ArrayList<String> res = StringOperations.divideString(s, div);
		
		//mostrar output
		for (int i = 0; i < res.size(); ++i) System.out.println("Substring " + i + ": " + res.get(i));
	}
	static private void testInfinitString() {
		System.out.println("Testing function infinitString()");
		//executar la funcionalitat

		System.out.println("Infinit string: " + StringOperations.infinitString());
	}
	static private void testMinDis() {
		System.out.println("Testing function minDist()");
		//demanar l'input
		System.out.println("Write 2 strings to know the minimum distance between them");
			
		Scanner scanner = new Scanner(System.in);
		System.out.print("String 1: ");
		String s1 = scanner.nextLine();
		System.out.print("String 2: ");
		String s2 = scanner.nextLine();
		
		int n = s1.length();
		int m = s2.length();
		int[][] dp = new int[n+1][m+1];
		for (int i = 0; i < n+1; ++i)
			for (int j = 0; j < m+1; ++j) dp[i][j] = -1;
		
		//executar la funcionalitat
		int dist = StringOperations.minDis(s1, s2, n, m, dp);
		
		//mostrar output
		System.out.println("Distance: " + dist);
	}
	static private void mostra_21() {
		System.out.println("Testing function tipusCorrecte(String s, tipus t)");
		if (!inicailitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		System.out.println("Enter a string to check:");
		String s = scanner.next();

		System.out.println("Enter the type to check with \""+ s + "\":");
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
		boolean res = ConjuntItems.tipusCorrecte(s, t);

		//mostrar output
		if (res) System.out.println("String " + s + " could be treated as a " + StringOperations.tipusToString(t));
		else System.out.println("String " + s + " is not of type " + StringOperations.tipusToString(t));
	}

	static private void mostra_29() {
		System.out.println("Testing function distanciaAtribut(String a1, String a2, int columna)");
		if (!inicialitzat) {
			System.out.println("The set is not initialized yet");
			return;
		}
		//demanar l'input
		System.out.println("Enter the first string: ");
		String s1 = scanner.next();
		System.out.println("Enter the second string: ");
		String s2 = scanner.next();
		System.out.println("Enter the column they belong to: ");
		for (int i = 0; i < Item.getNumAtributs(); ++i) {
			System.out.println(i + ": " + Item.getCapçalera().get(i));
		}
		int col = scanner.nextInt();

		//executar la funcionalitat
		float res;
		try {
			res = ci.distanciaAtribut(s1, s2, col);
		} catch (ItemTypeNotValidException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		//mostrar output
		System.out.println("Distance between " + s1 + " and " + s2 + ": " + res);
	}
}
