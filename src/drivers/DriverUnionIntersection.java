package src.drivers;

import java.util.Scanner;

import src.recomanador.domini.Utils.UnionIntersection;

public class DriverUnionIntersection {
	static private Scanner scanner;
    
    static private UnionIntersection c;

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
		"1. getUnion\n" +
		"2. getIntersection\n";
		
		System.out.println("Testing class UnionIntersection");
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
				default:
			}
			
		} while (x != -1);
		
		System.out.println("Test ended.");
    }
    
    static private void mostra_1() {
		System.out.println("Testing function getUnion");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
    static private void mostra_2() {
		System.out.println("Testing function getIntersection");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
}
