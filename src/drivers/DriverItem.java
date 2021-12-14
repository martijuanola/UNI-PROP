package src.drivers;

//Utils
import java.util.Scanner;
import java.util.ArrayList;
import src.recomanador.domini.Item.tipus;
import src.recomanador.Utils.*;

//Classes
import src.recomanador.domini.Item;
import src.recomanador.persistencia.ControladorPersistencia;

//Excepcions
import src.recomanador.excepcions.*;

/**
 * Driver tot test the class Item.
 * @author     Martí J.
 */
public class DriverItem {
	static private Scanner scanner;
    
    static private Item c;

    static private boolean class_initalised;
    static private ControladorPersistencia cp;
    static private int n;
    static private int m;
    static private float f;
    
    public static void main(String[] args) {
   		scanner = new Scanner(System.in);
		class_initalised = false;
		cp = ControladorPersistencia.getInstance();
		
		String s = "Options: \n" +
		"-1. exit\n" +
		"0. show options\n" +
		" - Constructors - \n" +
		"1. Item(int id)\n" +
		"2. Item(ArrayList<ArrayList<String>> atributs)\n" +
		" - Getters - \n" +
		"3. int getId()\n" +
		"4. ArrayList<ArrayList<String>> getAtributs()\n" +
		" - Static Getters - \n" +
		"5. int getPosId()\n" +
		"6. int getPosNomA()\n" +
		"7. int getNumAtributs()\n" +
		"8. ArrayList<Float> getPesos()\n" +
		"9. ArrayList<tipus> getTipusArray()\n" +
		"10. ArrayList<String> getCapçalera()\n" +
		" - Static Setters - \n" +
		"11. void setId(int id)\n" +
		"12. void setNomA(int a)\n" +
		"13. void setPes(int a, float pes)\n" +
		"14. void setTipus(int atribut, tipus t)\n" +
		"15. void setPesos(ArrayList<Float> p)\n" +
		"16. void setTipusArray(ArrayList<tipus> a)\n" +
		"17. void setNomAtributs(ArrayList<String> n)\n" +
		"18. void resetStatics()\n" +
		"19. void inicialitzarStatics(ArrayList<Float> pesos, ArrayList<String> nomAtributs, ArrayList<tipus> tipusAtribut, int id, int nomA)\n" +
		"20. void inicialitzarStaticsDefault(ArrayList<String> atributs)\n" +
		" - Others - \n" +
		"21. int compareTo(Item otherItem)\n";
		
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
				default:
			}
			
		} while (x != -1);
		
		System.out.println("Test ended.");
    }
    
    static private void mostra_1() {
		System.out.println("Testing function Item(int id)");
		System.out.println("The statics values will be initialised with default values.");
		
		ArrayList<String> as = new ArrayList<String>();
		as.add("id");
		try{ Item.inicialitzarStaticsDefault(as); }
		catch(Exception e) {System.out.println("ERROR: " + e.getMessage());return;}


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
		System.out.println("The statics values will be initialised with values derivated from the input file.");
		
		System.out.print("file name .csv with the header and only 1 item: ");
		String s = scanner.next();
		ArrayList<ArrayList<String>> raw;
		try{ 
			raw = cp.carregarFitxerExtern(s);
		}
		catch(FileNotValidException | FileNotFoundException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		int size = raw.get(0).size();

		int idprov = -1;

		ArrayList<String> nAtributs = raw.get(0);
		ArrayList<Float> ap = new ArrayList<Float>(0);
		ArrayList<tipus> at = new ArrayList<tipus>(0);
		for(int i = 0; i < size; i++) {
			ap.add(100.0f);
			at.add(tipus.S);
			if(raw.get(0).get(i).equalsIgnoreCase("id")) idprov = i;
		}

		try{
			Item.setId(idprov);
			Item.setNomAtributs(nAtributs);
			Item.setPesos(ap);
			Item.setTipusArray(at);
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
		
		ArrayList<ArrayList<String>> str = new ArrayList<ArrayList<String>>(); //Array on es posaran els atributs
        for (int j = 0; j < raw.get(1).size(); ++j) { //Recorrem per separar en subvectors
            str.add(StringOperations.divideString(raw.get(1).get(j), ';'));
        }

		try {
			c = new Item(str);
			class_initalised = true;
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
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
		System.out.println("Testing function ArrayList<ArrayList<String>> getAtributs()");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		ArrayList<ArrayList<String>> aux = c.getAtributs();
		System.out.println("These are the atributs of the item:");
		System.out.println(aux);
	}
    static private void mostra_5() {
		System.out.println("Testing function int getPosId()");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		n = Item.getPosId();
		System.out.println("All the items store the ID in the position: " + n + ".");
	}
    static private void mostra_6() {
		System.out.println("Testing function getPosNomA()");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		n = Item.getPosNomA();
		if(n == -1) System.out.println("The items don't hava an atribute assigned as the name.");
		System.out.println("All the items use the atribute "+ n+ " as the name of the item.");
	}
	static private void mostra_7() {
		System.out.println("Testing function getNumAtributs()");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		n = Item.getNumAtributs();
		System.out.println("All the items have "+n+" atributes.");	
	}
	static private void mostra_8() {
		System.out.println("Testing function ArrayList<Float> getPesos()");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
		ArrayList<Float> aux = Item.getPesos();
		System.out.println("The weight of the atributes are:");
		System.out.println(aux);
	}
	static private void mostra_9() {
		System.out.println("Testing function ArrayList<tipus> getTipusArray()");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}
		
		ArrayList<tipus> aux = Item.getTipusArray();
		System.out.println("The types of the atributes are:");
		for(int i = 0; i < aux.size(); i++){
			System.out.print(StringOperations.tipusToString(aux.get(i)) + "\t");
		}
		System.out.println();
	}
	static private void mostra_10() {
		System.out.println("Testing function ArrayList<String> getCapçalera()");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		ArrayList<String> aux = Item.getCapçalera();
		System.out.println("The name of the atributes are: ");
		System.out.println(aux);
	}
	static private void mostra_11() {
		System.out.println("Testing function void setId(int id)");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Old value for Id(column index):");
		n = scanner.nextInt();
		
		try{
			Item.setId(n);
		}catch(ItemIdNotValidException e) {System.out.println("ERROR: " + e.getMessage());return;}
		System.out.println("The value id has been correctly initialised.");
	}
	static private void mostra_12() {
		System.out.println("Testing function void setNomA(int a)");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Old value for nomA(column index):");
		n = scanner.nextInt();
		
		try {
			Item.setNomA(n);
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		System.out.println("The value nomA has been correctly initialised.");
	}
	static private void mostra_13() {
		System.out.println("Testing function setPes(int a, float pes)");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Index of the atribute to change it's weight:");
		n = scanner.nextInt();

		System.out.print("New weight :");
		f = scanner.nextFloat();
		
		try {
			Item.setPes(n,f);
			System.out.println("The weight has been changed.");
		}
		catch(ItemWeightNotCorrectException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		ArrayList<Float> aux = Item.getPesos();
		System.out.println("The weight of the now atributes are:");
		System.out.println(aux);
	}
	static private void mostra_14() {
		System.out.println("Testing function void setTipus(int atribut, tipus t)");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.print("Index of the atribute to change it's type:");
		n = scanner.nextInt();

		System.out.println("Enter the new type. Options:");
		System.out.println("I - ID");
		System.out.println("N - NOM");
		System.out.println("B - BOOLEAN");
		System.out.println("F - FLOAT");
		System.out.println("S - STRING");
		System.out.println("D - DATE");
		String s = scanner.next();
		try {
			tipus t = StringOperations.stringToType(s);
			Item.setTipus(n,t);
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		mostra_9();
	}
	static private void mostra_15() {
		System.out.println("Testing function void setPesos(ArrayList<Float> p)");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		ArrayList<Float> w = new ArrayList<Float>(0);

		for(int i = 0; i < Item.getNumAtributs(); i++) {
			System.out.println("Enter weight of atribute number " + i + ":");
			f = scanner.nextFloat();
			w.add(f);
		}

		try{
			Item.setPesos(w);
		}
		catch(ItemWeightNotCorrectException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		ArrayList<Float> aux = Item.getPesos();
		System.out.println("The weight of the atributes are:");
		System.out.println(aux);
	}
	static private void mostra_16() {
		System.out.println("Testing function void setTipusArray(ArrayList<tipus> a)");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		ArrayList<tipus> at = new ArrayList<tipus>(0);

		for(int i = 0; i < Item.getNumAtributs(); i++) {
			System.out.println("Enter the new type for Item number "+i+". Options:");
			System.out.println("I - ID");
			System.out.println("N - NOM");
			System.out.println("B - BOOLEAN");
			System.out.println("F - FLOAT");
			System.out.println("S - STRING");
			System.out.println("D - DATE");
			String s = scanner.next();
			try {
				tipus t = StringOperations.stringToType(s);
				at.add(t);
			}
			catch(ItemTypeNotValidException e) {
				System.out.println("ERROR: " + e.getMessage());
				return;
			}
		}
		try {
			Item.setTipusArray(at);
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		ArrayList<tipus> aux = Item.getTipusArray();
		System.out.println("The types of the atributes are:");
		for(int i = 0; i < aux.size(); i++){
			System.out.print(StringOperations.tipusToString(aux.get(i)) + "\t");
		}
		System.out.println();
	}
	static private void mostra_17() {
		System.out.println("Testing function void setNomAtributs(ArrayList<String> n)");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		ArrayList<String> as = new ArrayList<String>(0);

		for(int i = 0; i < Item.getNumAtributs(); i++) {
			System.out.println("Enter new name for atribute "+i+":");
			String s = scanner.next();
			as.add(s);
		}

		try {
			Item.setNomAtributs(as);
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
		
		ArrayList<String> aux = Item.getCapçalera();
		System.out.println("The name of the atributes are: ");
		System.out.println(aux);
	}
	static private void mostra_18() {
		System.out.println("Testing function void resetStatics()");
		
		Item.resetStatics();
		System.out.println("Item's static atributes have been reset:");
		if(Item.getPosId() == -1) System.out.println("IDPos = -1");
		else System.out.println("ERROR in ID");
		if(Item.getPosNomA() == -1) System.out.println("nomA = -1");
		else System.out.println("ERROR in nomA");
		if(Item.getPesos() == null) System.out.println("pesos = null");
		else System.out.println("ERROR in pesos");
		if(Item.getTipusArray() == null) System.out.println("tipusAtribut = null");
		else System.out.println("ERROR in tipusAtribut");
		if(Item.getCapçalera() == null) System.out.println("nomAtributs = null");
		else System.out.println("ERROR in nomAtribut");
	}

	
	static private void mostra_19() {
		System.out.println("Testing function void inicialitzarStatics(ArrayList<Float> pesos, ArrayList<String> nomAtributs, ArrayList<tipus> tipusAtribut, int id, int nomA)");

		System.out.println("Enter (relative) path to a \"items.csv\" file: ");
		String path = scanner.next();

		try {
			cp.escollirProjecte(path);
			cp.carregarEstat();

			ArrayList<Float> pesos = new ArrayList<>();
			ArrayList<tipus> tipusAtribut = new ArrayList<>();
			int id = Integer.parseInt(cp.carregarEstat().get(4));
			int nomA = Integer.parseInt(cp.carregarEstat().get(5));
			ArrayList<String> nomAtributs = cp.carregarItemsCarpeta().get(0);

			ArrayList<String> pesosS = cp.carregarPesosAtributs();
			for (int i = 0; i < pesosS.size(); ++i) {
				pesos.add(Float.parseFloat(pesosS.get(i)));
			}
			ArrayList<String> tipusS = cp.carregarTipusAtributs();
			for (int i = 0; i < tipusS.size(); ++i) {
				tipusAtribut.add(StringOperations.stringToType(tipusS.get(i)));
			}
			
			Item.inicialitzarStatics(pesos,nomAtributs, tipusAtribut, id, nomA);
			
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

	}
	static private void mostra_20() {
		System.out.println("Testing function void inicialitzarStaticsDefault(ArrayList<String> atributs)");

		System.out.print("Number of atribures: ");
		n = scanner.nextInt();

		ArrayList<String> as = new ArrayList<String>(0);

		for(int i = 0; i < n; i++) {
			System.out.println("Enter new name for atribute "+i+":");
			String s = scanner.next();
			as.add(s);
		}

		try {
			Item.inicialitzarStaticsDefault(as);
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}

		System.out.println("Item's static atributes have been initizlized:");
		System.out.println("IDPos = " + Item.getPosId());
		System.out.println("nomA = " + Item.getPosNomA());
		System.out.println("pesos = " + Item.getPesos());
		System.out.println("tipusAtribut = " + Item.getTipusArray());
		System.out.println("nomAtributs = " + Item.getCapçalera());
	}

	static private void mostra_21() {
		System.out.println("Testing function int compareTo(Item otherItem)");
		if(!class_initalised) {
			System.out.println("!! Item not initalised. Use option 1 or 2 to construct a instance first. !!");
			return;
		}

		System.out.println("Data for new Item:");
		System.out.print("Item ID: ");
		n = scanner.nextInt();
		Item aux;
		try{
			aux = new Item(n);
		}
		catch(ItemStaticValuesNotInitializedException e) {
			System.out.println("ERROR: " + e.getMessage());
			return;
		}
		m = c.compareTo(aux);

		if(m < 0) System.out.println("The new item has a greater index than the current.");
		else if(m > 0) System.out.println("The new item has a lower index than the current.");
		else System.out.println("Both item would have the same index in the sorting.");
	}
}
