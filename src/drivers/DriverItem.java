package src.drivers;

//Utils
import java.util.Scanner;
import java.util.ArrayList;
import src.recomanador.domini.Item.tipus;

//Classes
import src.recomanador.domini.Item;
import src.recomanador.persistencia.ControladorPersistencia;

//Excepcions
import java.io.IOException;
import src.recomanador.excepcions.ItemTypeNotValidException;
import src.recomanador.excepcions.ItemWeightNotCorrectException;
import src.recomanador.excepcions.ItemStaticValuesNotInitializedException;
import src.recomanador.excepcions.ItemNewAtributesNotValidException;

/**
 * Driver tot test the class Item.
 * @author     
 */
public class DriverItem {
	static private Scanner scanner;
    
    static private Item c;

    static private boolean class_initalised;
    static private ControladorPersistencia cp;
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
		"1. Item(int id)\n" +
		"2. Item(ArrayList<ArrayList<String>> atributs)\n" +
		"3. int getId()\n" +
			"4. ArrayList<String> getAtribut(int i)\n" +
		"4. ArrayList<ArrayList<String>> getAtributs()\n" +
		"5. int getPosId()\n" +
		"6. int getPosNomA()\n" +
		"7. int getNumAtributs()\n" +
			"9. Float getPes(int i)\n" +
			"10. tipus getTipus(int i)\n" +
			"11. String getNomAtribut(int i)\n" +
		"8. ArrayList<Float> getPesos()\n" +
		"9. ArrayList<tipus> getTipusArray(\n" +
		"10. ArrayList<String> getCapçalera()\n" +
		"11. void setId(int id)\n" +
		"12. void setNomA(int a)\n" +
			"17. void setPes(int a, float pes)\n" +
			"18. void assignarTipus(int atribut, tipus t)\n" +
		"13. void setPesos(ArrayList<Float> p)\n" +
		"14. void setTipus(ArrayList<tipus> a)\n" +
		"15. void canvisTipusAtribut(int atribut, tipus t)\n" +
		"16. void assignarNomAtributs(ArrayList<String> n)\n" +
		"17. int compareTo(Item otherItem)\n" +
		"18. String tipusToString(tipus t)\n";
		
		System.out.println("Testing class Item");
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
				case 10:
					mostra_10();
					break;
				case 11:
					mostra_11();
					break;
				case 12:
					mostra_12();
					break;
				case 13:
					mostra_13();
					break;
				case 14:
					mostra_14();
					break;
				case 15:
					mostra_15();
					break;
				case 16:
					mostra_16();
					break;
				case 17:
					mostra_17();
					break;
				case 18:
					mostra_18();
					break;
				case 19:
					mostra_19();
					break;
				case 20:
					mostra_20();
					break;
				case 21:
					mostra_21();
					break;
				case 22:
					mostra_22();
					break;
				case 23:
					mostra_23();
					break;
				case 24:
					mostra_24();
					break;
				default:
			}
			
		} while (x != -1);
		
		System.out.println("Test ended.");
    }
    
    static private void mostra_1() {
		System.out.println("Testing function Item(int id)");
			
		System.out.println("Data for new Item:");
		System.out.print("Item ID: ");
		n = scanner.nextInt();
		
		try {
			c = new Item(n);
			class_initalised = true;
		}
		catch(ItemStaticValuesNotInitializedException e) {
			System.out.println("ERROR: " + e.getMessage());
			System.out.println("Use the appropiate functionallities to set these values first.");
			return;
		}
		
		System.out.println("New Item has been initialised with the given ID. Data can be checked with getter operations.");
	}
    static private void mostra_2() {
		System.out.println("Testing function Item(ArrayList<ArrayList<String>> atributs)");
		


	}
    static private void mostra_3() {
		System.out.println("Testing function int getId()");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		n = c.getId();
		System.out.println("The ID of the item is " + n);
	}
    static private void mostra_4() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}


	}
    static private void mostra_5() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}


	}
    static private void mostra_6() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

	}
	static private void mostra_7() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_8() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_9() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_10() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_11() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_12() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_13() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_14() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_15() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_16() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_17() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_18() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_19() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_20() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_21() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_22() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_23() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}
	static private void mostra_24() {
		System.out.println("Testing function <NAME_FUNCTION>");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
	}

	//S'ha de treure
    /**
     * Imprimeix els atributs
     */
   /* public void print() {
        for (int i = 0; i < atributs.size(); ++i) {
            int g = atributs.get(i).size();
            if (g > 1) System.out.print("{");
            for (int j = 0; j < g; ++j) {
                System.out.print(atributs.get(i).get(j));
                if (j != g-1) System.out.print(", ");
            }
            if (g > 1) System.out.print("}");
            if (i != atributs.size()-1) System.out.print(", ");
        }
        System.out.println("");
    }*/
}
