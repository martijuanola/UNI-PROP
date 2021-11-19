package src.drivers;

import java.util.Scanner;

import src.recomanador.domini.ConjuntItems;
import src.stubs.StubItem;

public class DriverConjuntItems {
	static private Scanner scanner;
    
    static private ConjuntItems c;

    static private boolean class_initalised;
    //static private Classe1 c1;
    //static private Classe2 c2;
    static private String s1;
    static private String s2;
    static private boolean b;
    static private int n;
    static private int m;
    static private float f;
    
    public static void main(String[] args) {
   		scanner = new Scanner(System.in);
		class_initalised = false;
		
		String s = "Options: \n" +
		"-1. exit\n" +
		"0. show options\n" +
		"1. option 1\n" +
		"2. option 2\n" +
		"3. option 3\n" +
		"4. option 4\n" +
		"5. option 5\n" +
		"6. option 6\n";
		
		System.out.println("Testing class ConjuntItems");
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
				default:
			}
			
		} while (x != -1);
		
		System.out.println("Test ended.");
    }
    
    static private void mostra_1() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_2() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_3() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_4() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_5() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_6() {
		System.out.println("Testing function <NAME_FUNCTION>");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
}