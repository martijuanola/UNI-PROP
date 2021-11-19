package src.drivers;

import java.util.Scanner;
import java.util.ArrayList;

import src.recomanador.domini.Utils.UnionIntersection;

public class DriverUnionIntersection {
	static private Scanner scanner;
        
    public static void main(String[] args) {
   		scanner = new Scanner(System.in);
		
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
		System.out.println("Enter the size of the first array:");
		int n1 = scanner.nextInt();

        System.out.println("Enter " + n1 + " strings in diferent lines:");
		ArrayList<String> l1 = new ArrayList<String>();
		scanner.nextLine();
		for (int i = 0; i < n1; ++i) {
            l1.add(scanner.nextLine());
		}
		
		System.out.println("Enter the size of the second array:");
		int n2 = scanner.nextInt();
        System.out.println("Enter " + n2 + " strings in diferent lines:");
		ArrayList<String> l2 = new ArrayList<String>();
		scanner.nextLine();
		for (int i = 0; i < n2; ++i) {
            l2.add(scanner.nextLine());
		}

		//executar la funcionalitat
		ArrayList<String> result = new ArrayList<String>();
		result = UnionIntersection.getUnion(l1, l2);

		//mostrar output
		System.out.println("Union: ");
        System.out.println(result);
	}
    static private void mostra_2() {
		System.out.println("Testing function getIntersection");

		//demanar l'input
		System.out.println("Enter the size of the first array:");
		int n1 = scanner.nextInt();

        System.out.println("Enter " + n1 + " strings in diferent lines:");
		ArrayList<String> l1 = new ArrayList<String>();
		scanner.nextLine();
		for (int i = 0; i < n1; ++i) {
            l1.add(scanner.nextLine());
		}
		
		System.out.println("Enter the size of the second array:");
		int n2 = scanner.nextInt();
        System.out.println("Enter " + n2 + " strings in diferent lines:");
		ArrayList<String> l2 = new ArrayList<String>();
		scanner.nextLine();
		for (int i = 0; i < n2; ++i) {
            l2.add(scanner.nextLine());
		}

		//executar la funcionalitat
		ArrayList<String> result = new ArrayList<String>();
		result = UnionIntersection.getIntersection(l1, l2);

		//mostrar output
		System.out.println("Intersection: ");
        System.out.println(result);
	}
}
