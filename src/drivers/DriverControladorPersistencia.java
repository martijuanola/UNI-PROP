package src.drivers;

import src.recomanador.persistencia.ControladorPersistencia;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverControladorPersistencia {
    
    static private ControladorPersistencia c;
    static private inout io;
    static private ArrayList<ArrayList<String>> testData;
    static private ArrayList<ArrayList<ArrayList<String>>> testItemsData;
    static private ArrayList<String> testSimpleHeader;
    static private ArrayList<String> testArray6Values;
    
    public static void main(String[] args) {
        try{
			try
			{
				c = new ControladorPersistencia();
			} catch (Exception e) {
				throw new Exception("Failed to create an instance of ControladorPersistencia.");
			}
			
			io = new inout();
			initializeDummyData();
			initializeDummyItems();
			initializeDummyHeader();
			initializeDummyArray6();
			
			String s = "Options: \n" +
			"0. Show options\n" +
			"1. Chose which project to load\n" +
			"2. List all the available projects for loading\n" +
			"3. Show the project that is being used\n" +
			"4. Load the recomendations and valorations from the project chosen\n" +
			"5. Load the items from the project chosen\n" +
			"6. Load a .csv file (from anywhere)\n" +
			"7. Exit the project folder \n" +
			"8. Exists preprocessed data? \n" +
			"9. Test set and get some preprocessed data\n" +
			"10. Exists tests? \n" +
			"11. Load any .csv file from the folder\n" +
			"12. Load item atrributes' weights\n" +
			"13. Load item atrributes' type\n" +
			"14. Load test known\n" +
			"15. Load test unknown\n" +
			"16. Load previous execution's state\n" +
			"17. Create a new project folder\n" +
			"18. Save data (any .csv into any file inside the folder (it can be created))\n" +
			"19. Save recomendations and valorations\n" +
			"20. Save items\n" +
			"21. Save items' attributes' weights\n" +
			"22. Save items' atrributes' type\n" +
			"23. Load items' minimum atributes\n" +
			"24. Load items' maximum atributes\n" +
			"25. Save items' minimum atributes\n" +
			"26. Save items' maximum atributes\n" +
			"27. Save state\n" +
			"-1. exit\n";
			
			String reduced = "Options: \n" +
			"0. Show options\n" + "-1. exit\n";
			
			io.writeln("\n==================================================\n");
			io.writeln("Testing class ControladorPersistencia");
			int x;
			do {
				io.writeln("\n--------------------------------------------------\n");
				io.writeln("Enter the number of the function you want to test. \n");
				io.writeln(reduced);
				io.write("Option: ");
				
				x = io.readint();
				
				switch(x)
				{
					case 0:
						io.writeln(s);
						break;
					case 1:
						testEscollirProjecte();
						break;
					case 2:
						testLlistatCarpetes();
						break;
					case 3:
						testGetNomProjecte();
						break;
					case 4:
						testCarregarRecomanacionsCarpeta();
						break;
					case 5:
						testCarregarItemsCarpeta();
						break;
					case 6:
						testCarregarFitxerExtern();
						break;
					case 7:
						testSortirDelProjecte();
						break;
					case 8:
						testExisteixenDadesPreprocesades();
						break;
					case 9:
						testDadesEstat();
						break;
					case 10:
						testExisteixenTestos();
						break;
					case 11:
						testCarregarArxiuCarpeta();
						break;
					case 12:
						testCarregarPesosAtributs();
						break;
					case 13:
						testCarregarTipusAtributs();
						break;
					case 14:
						testCarregarTestKnown();
						break;
					case 15:
						testCarregarTestUnknown();
						break;
					case 16:
						testCarregarEstat();
						break;
					case 17:
						testCrearProjecte();
						break;
					case 18:
						testGuardarDades();
						break;
					case 19:
						testGuardarRecomanacions();
						break;
					case 20:
						testGuardarItems();
						break;
					case 21:
						testGuardarPesosAtributs();
						break;
					case 22:
						testGuardarTipusAtributs();
						break;
					case 23:
						testCarregarMinAtributsItems();
						break;
					case 24:
						testCarregarMaxAtributsItems();
						break;
					case 25:
						testGuardarMinAtributsItems();
						break;
					case 26:
						testGuardarEstat();
						break;
					default:
				}
				
			} while (x != -1);
			io.writeln("Test ended");
			io.writeln("\n==================================================\n");
			
		}catch(Exception e2){
			System.out.println("An error has occurred");
			System.out.println(e2.getMessage());
		}
    }
    
    static private void testEscollirProjecte() throws Exception {
		io.writeln("Testing function escollirProjecte()");
		
		//demanar l'input
		io.writeln("\nChoose a project to load from the list. " +
		"Enter the number of the project you want to load");
		
		ArrayList<String> a = c.llistatCarpetes();
		io.writeln("These are the projects you can load:");
		for (int i = 1; i <= a.size(); ++i) 
			io.writeln("\t" + i + ". " + a.get(i-1));	
		
		int x = io.readint();
		if (x > a.size() || x <= 0)
		{
			io.writeln("Number not valid. Enter a number that belongs to the interval.");
			return;
		}
		
		//executar la funcionalitat
		try {
			c.escollirProjecte(a.get(x-1));
		} catch (Exception e) {
			io.writeln("Project loading failed by an internar error");
			return;
		}
		//mostrar output
		if (c.getNomProjecte() == null)
		{
			io.writeln("Project loading failed");
			return;
		}
		
		io.writeln("The project " + c.getNomProjecte() + " is now being used");
		
	}
    static private void testLlistatCarpetes() throws Exception {
		io.writeln("Testing function llistatCarpetes()");
		//demanar l'input
				
		//executar la funcionalitat
		ArrayList<String> a = c.llistatCarpetes();
		
		//mostrar output
		io.writeln("\nThese are the projects you can load:");
		for (int i = 0; i < a.size(); ++i) 
			io.writeln("\t" + a.get(i));	
	}
	static private void testGetNomProjecte() throws Exception {
		io.writeln("Testing function getNomProjecte()");
		//demanar l'input
				
		//executar la funcionalitat
		String p = c.getNomProjecte();
		
		//mostrar output
		if (p == null) io.writeln("There is not any project being used right now");
		else io.writeln("The project " + p + " is now being used");	
	}
    static private void testCarregarRecomanacionsCarpeta() throws Exception {
		io.writeln("Testing function carregarRecomanacionsCarpeta()");
		//demanar l'input
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<ArrayList<String>> sol = null;
		try
		{		
			sol = c.carregarRecomanacionsCarpeta();
		} catch(Exception e)
		{
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		//mostrar output
		io.writeln("File ratings.db.csv from the poject " + c.getNomProjecte() + ":");
		int n = sol.size();
		if (11 < n) n = 11;
		
		for (int i = 0; i < n; ++i)
		{
			io.write("\t");
			for (int j = 0; j < sol.get(i).size(); ++j) io.write(sol.get(i).get(j) + " ");
			io.writeln();
		}
		
		io.writeln("...");
	}
    static private void testCarregarItemsCarpeta() throws Exception {
		io.writeln("Testing function carregarItemsCarpeta()");
		//demanar l'input
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<ArrayList<String>> sol = null;
		try
		{		
			sol = c.carregarItemsCarpeta();
		} catch(Exception e)
		{
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		
		//mostrar output
		io.writeln("File items.csv from the poject " + c.getNomProjecte() + ":");
		int n = sol.size();
		if (11 < n) n = 11;
		
		for (int i = 0; i < n; ++i)
		{
			io.write("\t");
			for (int j = 0; j < sol.get(i).size(); ++j) io.write(sol.get(i).get(j) + " ");
			io.writeln();
		}
		
		io.writeln("...");
		
		//mostrar output
		io.writeln("More information about the file read:");
		int cols = sol.get(0).size();
		io.writeln("Rows read: " + sol.size());
		
		
		for (int i = 0; i < sol.size(); ++i)
		{
			int temp_cols = sol.get(i).size();
			if (temp_cols != cols)
			{
				io.writeln("Error al llegir la fila " + i + ". LLegiex " + temp_cols);
				for (int j = 0; j < sol.get(i).size(); ++j) io.write(sol.get(i).get(j) + "|");
				io.writeln();
			}
		}
		
		io.writeln("Columns read per row: " + cols);
	}
    static private void testCarregarFitxerExtern() throws Exception {
		io.writeln("Testing function carregarFitxerExtern()");
		
		//demanar l'input
		io.writeln("Write the path of the .csv file that you want to read");
		io.writeln("(If you don't know what to chose, you have an example at data/Movies-2250/ratings.db.csv)");
		io.write("Path: ");
		
		Scanner scanner = new Scanner(System.in);		
		String p = scanner.nextLine();
				
		//executar la funcionalitat
		ArrayList<ArrayList<String>> sol = new ArrayList<ArrayList<String>>();
		try
		{
			sol = c.carregarFitxerExtern(p);
		}catch(Exception e)
		{
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			scanner.close();
			return;
		}
		
		//mostrar output
		io.writeln("Head of the file read:");
		int n = sol.size();
		if (11 < n) n = 11;
		
		for (int i = 0; i < n; ++i)
		{
			io.write("\t");
			for (int j = 0; j < sol.get(i).size(); ++j) io.write(sol.get(i).get(j) + " ");
			io.writeln();
		}
		
		io.writeln("...");
		scanner.close();
	}
	static private void testSortirDelProjecte() throws Exception {
		io.writeln("Testing function sortirDelProjecte()");
		//demanar l'input
		
		//executar la funcionalitat
		c.sortirDelProjecte();
		
		//mostrar output
		io.writeln("\nYou are now not assigned to any project");
		io.writeln("You can check it out by choosing the option 3 at the main menu");
	}
	static private void testExisteixenDadesPreprocesades() throws Exception {
		io.writeln("Testing function existeixenDadesPreprocesades()\n");
		//demanar l'input
		io.writeln("Does the preprocessed data exist in the project folder?");
		io.writeln("(If you are not on one, you can change it by choosing option 1 at the main menu.)");
		//executar la funcionalitat
		if (c.existeixenDadesPreprocesades()) io.writeln("ANSWER: yes");
		else io.writeln("ANSWER: no");
		
		//mostrar output
		if (c.getNomProjecte() == null)
		{
			io.writeln("\nYou aren't in any project folder.");
		}
		else
		{
			io.writeln("\nYou can check its veracity by going to the folder " + c.getNomProjecte() + 
				" and checking if all files exist: pesos.csv, tipus.csv, estat.csv, minAtributs.items.csv, maxAtributs.items.csv");
		}
	}
	static private void testDadesEstat() throws Exception { //Inacabat!!!!---------------
		io.writeln("Testing functions related to set and get the pre-processed values\n");
		
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		else if (!isWritable())
		{
			io.writeln("Remember that you don't have permisions to write on this folder.");
			io.writeln("To avoid the loss of data from other projects, " + 
				"you can only write on those projects that start with \"dummy\".");
			io.writeln("If you want to write, you can either select one with the option 1 " + 
				"from the main menu or create a new one by choosing option 17.");
		}
		
		io.writeln("When a setter value is called, the set will change the internal "+
			"structure that stores it adn will also call guardarEstat(), wich will " +
			"save it in memory\n");
		io.writeln("Choose an option from below to do something:\n");
		
		String s = "Options: \n" +
			"0. Get Algorithm used (an integer is required)\n" +
			"1. Get Q\n" +
			"2. Get K\n" +
			"3. Get item's set name\n" +
			"4. Get id's position in the array\n" +
			"5. Get name's position in the array\n" +
			"6. Set Algorithm used (an integer is required)\n" +
			"7. Set Q\n" +
			"8. Set K\n" +
			"9. Set item's set name\n" +
			"10. Set id's position in the array\n" +
			"11. Set name's position in the array\n";
		
		io.writeln(s);
		io.writeln("Option: ");
			
		int x = 42;
		
		try { x = io.readint(); }
		catch (Exception e){System.out.println("An integer was expected");}
		
		int n = 0;
		String r = "";
		
		switch(x)
		{
			case 0:
				try {
					n = c.getAlgorismeSeleccionat();
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("The selected algorithm is: " + n);
				break;
				
			case 1:
				try {
					n = c.getQAlgorisme();
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("The Q used is: " + n);
				break;
				
			case 2:
				try {
					n = c.getKAlgorisme();
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("The K used is: " + n);
				break;
								
			case 3:
				try {
					r = c.getNomConjuntItems();
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("The name used is: " + n);
				break;
				
			case 4:
				try {
					n = c.getPosicioID();
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("The id's column position is: " + n);
				break;
				
			case 5:
				try {
					n = c.getPosicioNom();
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("The name's column position is: " + n);
				break;
				
			case 6:
				if (!isWritable()) 
				{
					System.out.println("ERROR. You cannot write here");
					return;
				}
				
				try {
					c.setAlgorismeSeleccionat("32");
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("DONE!");
				io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
				break;
				
			case 7:
				if (!isWritable()) 
				{
					System.out.println("ERROR. You cannot write here");
					return;
				}
				
				try {
					c.setQAlgorisme("8");
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("DONE!");
				io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
				break;
				
			case 8:
				if (!isWritable()) 
				{
					System.out.println("ERROR. You cannot write here");
					return;
				}
				
				try {
					c.setKAlgorisme("3");
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("DONE!");
				io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
				break;
				
			case 9:
				if (!isWritable()) 
				{
					System.out.println("ERROR. You cannot write here");
					return;
				}
				
				try {
					c.setNomConjuntItems("Kvothe");
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("DONE!");
				io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
				break;
				
			case 10:
				if (!isWritable()) 
				{
					System.out.println("ERROR. You cannot write here");
					return;
				}
				
				try {
					c.setPosicioID("5");
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("DONE!");
				io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
				break;
				
			case 11:
				if (!isWritable()) 
				{
					System.out.println("ERROR. You cannot write here");
					return;
				}
				
				try {
					c.setPosicioNom("23");
				} catch (Exception e) {
					System.out.println("ERROR!!");
					System.out.println(e.getMessage());
					return;
				}
				
				io.writeln("DONE!");
				io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
				break;
				
			default:
		}
	}
	static private void testExisteixenTestos() throws Exception {
		io.writeln("Testing function existeixenTestos()\n");
		//demanar l'input
		io.writeln("Does the tests data exist in the project folder?");
		io.writeln("(If you are not on one, you can change it by choosing option 1 at the main menu.)");
		//executar la funcionalitat
		if (c.existeixenTestos()) io.writeln("ANSWER: yes");
		else io.writeln("ANSWER: no");
		
		//mostrar output
		if (c.getNomProjecte() == null)
		{
			io.writeln("\nYou aren't in any project folder.");
		}
		else
		{
			io.writeln("\nYou can check its veracity by going to the folder " + c.getNomProjecte() + 
				" and checking if both files exist: ratings.test.known.csv and ratings.test.unknown.csv");
		}
	}
	static private void testCarregarArxiuCarpeta() throws Exception {
		io.writeln("Testing function carregarArxiuCarpeta()\n");
		//demanar l'input	
		io.writeln("Choose a file to load. Write the name below.");
		io.writeln("Remember that the file will be read as if it were a " +
			".csv with the same bumber of columns on every row and allowing " +
			"the file to contain some empty lines (with just a 'next line symbol': \\" + "n)");
		io.write("File: ");
		
		Scanner scanner = new Scanner(System.in);		
		String s = scanner.nextLine();
		
		//executar la funcionalitat
		ArrayList<ArrayList<String>> sol = null;
		try
		{		
			sol = c.TESTcarregarArxiuCarpeta(s);
		} catch(Exception e)
		{
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			if (c.getNomProjecte() == null)
			{
				io.writeln("Choose a project before reading the files");
				io.writeln("You can do it by choosing the option 1 on the main menu");
			}
			scanner.close();
			return;
		}
		
		//mostrar output
		io.writeln("File loaded:");
		int n = sol.size();
		if (11 < n) n = 11;
		
		for (int i = 0; i < n; ++i)
		{
			io.write("\t");
			for (int j = 0; j < sol.get(i).size(); ++j) io.write(sol.get(i).get(j) + " ");
			io.writeln();
		}
		
		io.writeln("...");
		scanner.close();
	}
	static private void testCarregarPesosAtributs() throws Exception {
		io.writeln("Testing function carregarPesosAtributs()\n");
		//demanar l'input
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<String> sol = null;
		try
		{		
			sol = c.carregarPesosAtributs();
		} catch(Exception e)
		{
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		
		//mostrar output
		io.writeln("File pesos.csv from the poject " + c.getNomProjecte() + ":");
		int n = sol.size();
		
		for (int i = 0; i < n; ++i) io.writeln(sol.get(i));
				
		//mostrar output
		io.writeln("More information about the file read:");
		io.writeln("Rows read: 1");
		io.writeln("Columns read: " + sol.size());
	}
	static private void testCarregarTipusAtributs() throws Exception {
		io.writeln("Testing function carregarTipusAtributs()\n");
		//demanar l'input
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<String> sol = null;
		try
		{		
			sol = c.carregarTipusAtributs();
		} catch(Exception e)
		{
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		
		//mostrar output
		io.writeln("File tipus.csv from the poject " + c.getNomProjecte() + ":");
		int n = sol.size();
		
		for (int i = 0; i < n; ++i) io.writeln(sol.get(i));
				
		//mostrar output
		io.writeln("More information about the file read:");
		io.writeln("Rows read: 1");
		io.writeln("Columns read: " + sol.size());
	}
	static private void testCarregarTestKnown() throws Exception {
		io.writeln("Testing function carregarTestKnown()\n");
		//demanar l'input
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<ArrayList<String>> sol = null;
		try
		{		
			sol = c.carregarTestKnown();
		} catch(Exception e)
		{
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		
		//mostrar output
		io.writeln("File ratings.test.known.csv from the poject " + c.getNomProjecte() + ":");
		int n = sol.size();
		if (11 < n) n = 11;
		
		for (int i = 0; i < n; ++i)
		{
			io.write("\t");
			for (int j = 0; j < sol.get(i).size(); ++j) io.write(sol.get(i).get(j) + " ");
			io.writeln();
		}
		
		io.writeln("...");
		
		io.writeln("More information about the file read:");
		io.writeln("Rows read: " + sol.size());
		io.writeln("Columns read: " + sol.get(0).size());
	}
	static private void testCarregarTestUnknown() throws Exception {
		io.writeln("Testing function carregarTestUnknown()\n");
		//demanar l'input
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<ArrayList<String>> sol = null;
		try
		{		
			sol = c.carregarTestUnknown();
		} catch(Exception e)
		{
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		
		//mostrar output
		io.writeln("File ratings.test.unknown.csv from the poject " + c.getNomProjecte() + ":");
		int n = sol.size();
		if (11 < n) n = 11;
		
		for (int i = 0; i < n; ++i)
		{
			io.write("\t");
			for (int j = 0; j < sol.get(i).size(); ++j) io.write(sol.get(i).get(j) + " ");
			io.writeln();
		}
		
		io.writeln("...");
		
		io.writeln("More information about the file read:");
		io.writeln("Rows read: " + sol.size());
		io.writeln("Columns read: " + sol.get(0).size());
	}
	static private void testCarregarEstat() throws Exception {
		io.writeln("Testing function carregarEstat()\n");
		//demanar l'input
		io.writeln("This function is also executed when a project is chosen and there's the necessary files.");
		io.writeln("You will only see a difference if you modify the data on the file and load it again");
		
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<String> sol = null;
		try
		{
			c.carregarEstat();		
		} catch(Exception e) {
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		io.writeln("DONE!");
	}
	static private void testCrearProjecte() throws Exception {
		io.writeln("Testing function crearProjecte()\n");
		//demanar l'input
		io.writeln("Type the name of the project that you want to create.");
		io.writeln("Keep in mind that the project will be named as dummy-<your_choice>.");
		io.writeln("This is done to ensure that only projects that start with dummy are modified, " +
			"to avoid data loss from the other tests.");
		io.writeln("NOTE: Remember, you can only use letters, numbers, '-', '_', '.'\n");
		io.write("Name: ");
		
		Scanner scanner = new Scanner(System.in);		
		String s = scanner.nextLine();
		
		//executar la funcionalitat
		try
		{
			c.crearProjecte("dummy-" + s);
		}catch(Exception e) {
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			scanner.close();
			return;
		}
		
		//mostrar output
		io.writeln("DONE!");
		io.writeln("You can check it that it has been created using option 2 from the main menu.");
		io.writeln("You can check it that you are in the folder using option 3 from the main menu.");
		scanner.close();
	}
	static private void testGuardarDades() throws Exception {
		io.writeln("Testing function guardarDades()\n");
		//demanar l'input
		if (!isWritable())
		{
			io.writeln("You don't have permisions to write on this folder.");
			io.writeln("To avoid the loss of data from other projects, " + 
				"you can only write on those projects that start with \"dummy\".");
			io.writeln("You can either select one with the option 1 from the main menu or " + 
				"create a new one by choosing option 17.");
			return;
		}
		
		io.writeln("Some data will be stored to the file that you specify.");
		io.write("File: ");
		
		Scanner scanner = new Scanner(System.in);		
		String s = scanner.nextLine();
		
		//executar la funcionalitat
		try
		{
			c.TESTguardarDades(testData, s);
		}catch(Exception e) {
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			scanner.close();
			return;
		}
		
		//mostrar output
		io.writeln("DONE!");
		io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
		scanner.close();
	}
	static private void testGuardarRecomanacions() throws Exception {
		io.writeln("Testing function guardarRecomanacions()\n");
		//demanar l'input
		if (!isWritable())
		{
			io.writeln("You don't have permisions to write on this folder.");
			io.writeln("To avoid the loss of data from other projects, " + 
				"you can only write on those projects that start with \"dummy\".");
			io.writeln("You can either select one with the option 1 from the main menu or " + 
				"create a new one by choosing option 17.");
			return;
		}
		
		io.writeln("Some data will be stored in ratings.db.csv");
		
		//executar la funcionalitat
		try
		{
			c.guardarRecomanacions(testData);
		}catch(Exception e) {
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		//mostrar output
		io.writeln("DONE!");
		io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
	}
	static private void testGuardarItems() throws Exception {
		io.writeln("Testing function guardarRecomanacions()\n");
		//demanar l'input
		if (!isWritable())
		{
			io.writeln("You don't have permisions to write on this folder.");
			io.writeln("To avoid the loss of data from other projects, " + 
				"you can only write on those projects that start with \"dummy\".");
			io.writeln("You can either select one with the option 1 from the main menu or " + 
				"create a new one by choosing option 17.");
			return;
		}
		
		io.writeln("Some data will be stored in items.csv");
		
		//executar la funcionalitat
		try
		{
			c.guardarItems(testSimpleHeader, testItemsData);
		}catch(Exception e) {
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		//mostrar output
		io.writeln("DONE!");
		io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
	}
	static private void testGuardarPesosAtributs() throws Exception {
		io.writeln("Testing function guardarPesosAtributs()\n");
		//demanar l'input
		if (!isWritable())
		{
			io.writeln("You don't have permisions to write on this folder.");
			io.writeln("To avoid the loss of data from other projects, " + 
				"you can only write on those projects that start with \"dummy\".");
			io.writeln("You can either select one with the option 1 from the main menu or " + 
				"create a new one by choosing option 17.");
			return;
		}
		
		io.writeln("Some data will be stored in pesos.csv");
		
		//executar la funcionalitat
		try
		{
			ArrayList<Float> af = new ArrayList<Float>();
			for (int i = 0; i < 25; ++i) af.add(new Float(Integer.toString(i))); 
			c.guardarPesosAtributs(af);
		}catch(Exception e) {
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		//mostrar output
		io.writeln("DONE!");
		io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
	}
	static private void testGuardarTipusAtributs() throws Exception {
		io.writeln("Testing function guardarTipusAtributs()\n");
		//demanar l'input
		if (!isWritable())
		{
			io.writeln("You don't have permisions to write on this folder.");
			io.writeln("To avoid the loss of data from other projects, " + 
				"you can only write on those projects that start with \"dummy\".");
			io.writeln("You can either select one with the option 1 from the main menu or " + 
				"create a new one by choosing option 17.");
			return;
		}
		
		io.writeln("Some data will be stored in tipus.csv");
		
		//executar la funcionalitat
		try
		{
			ArrayList<String> af = new ArrayList<String>();
			for (int i = 0; i < 25; ++i) af.add("Type_" + Integer.toString(i)); 
			c.guardarTipusAtributs(af);
		}catch(Exception e) {
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		//mostrar output
		io.writeln("DONE!");
		io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
	}
	static private void testCarregarMinAtributsItems() throws Exception {
		io.writeln("Testing function carregarMinAtributsItems()\n");
		//demanar l'input
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<String> sol = null;
		try
		{		
			sol = c.carregarMinAtributsItems();
		} catch(Exception e)
		{
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		
		//mostrar output
		io.writeln("File minAtributs.items.csv from the poject " + c.getNomProjecte() + ":");
		int n = sol.size();
		
		for (int i = 0; i < n; ++i) io.write(sol.get(i) + "\t");
				
		//mostrar output
		io.writeln();
		io.writeln("More information about the file read:");
		io.writeln("Rows read: 1");
		io.writeln("Columns read: " + sol.size());
	}
	static private void testCarregarMaxAtributsItems() throws Exception {
		io.writeln("Testing function carregarMaxAtributsItems()\n");
		//demanar l'input
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<String> sol = null;
		try
		{		
			sol = c.carregarMaxAtributsItems();
		} catch(Exception e)
		{
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		
		//mostrar output
		io.writeln("File maxAtributs.items.csv from the poject " + c.getNomProjecte() + ":");
		int n = sol.size();
		
		for (int i = 0; i < n; ++i) io.write(sol.get(i) + "\t");
				
		//mostrar output
		io.writeln();
		io.writeln("More information about the file read:");
		io.writeln("Rows read: 1");
		io.writeln("Columns read: " + sol.size());
	}
	static private void testGuardarMinAtributsItems() throws Exception {
		io.writeln("Testing function guardarMinAtributsItems()\n");
		//demanar l'input
		if (!isWritable())
		{
			io.writeln("You don't have permisions to write on this folder.");
			io.writeln("To avoid the loss of data from other projects, " + 
				"you can only write on those projects that start with \"dummy\".");
			io.writeln("You can either select one with the option 1 from the main menu or " + 
				"create a new one by choosing option 17.");
			return;
		}
		
		io.writeln("Some data will be stored in minAtributs.items.csv");
		
		//executar la funcionalitat
		try
		{
			ArrayList<String> af = new ArrayList<String>();
			for (int i = 0; i < 25; ++i) af.add("min_" + Integer.toString(i)); 
			c.guardarMinAtributsItems(af);
		}catch(Exception e) {
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		//mostrar output
		io.writeln("DONE!");
		io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
	}
	static private void testGuardarMaxAtributsItems() throws Exception {
		io.writeln("Testing function guardarMaxAtributsItems()\n");
		//demanar l'input
		if (!isWritable())
		{
			io.writeln("You don't have permisions to write on this folder.");
			io.writeln("To avoid the loss of data from other projects, " + 
				"you can only write on those projects that start with \"dummy\".");
			io.writeln("You can either select one with the option 1 from the main menu or " + 
				"create a new one by choosing option 17.");
			return;
		}
		
		io.writeln("Some data will be stored in maxAtributs.items.csv");
		
		//executar la funcionalitat
		try
		{
			ArrayList<String> af = new ArrayList<String>();
			for (int i = 0; i < 25; ++i) af.add("max_" + Integer.toString(i));
			c.guardarMaxAtributsItems(af);
		}catch(Exception e) {
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		//mostrar output
		io.writeln("DONE!");
		io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
	}
	static private void testGuardarEstat() throws Exception {
		io.writeln("Testing function guardarEstat()\n");
		//demanar l'input
		if (!isWritable())
		{
			io.writeln("You don't have permisions to write on this folder.");
			io.writeln("To avoid the loss of data from other projects, " + 
				"you can only write on those projects that start with \"dummy\".");
			io.writeln("You can either select one with the option 1 from the main menu or " + 
				"create a new one by choosing option 17.");
			return;
		}
		
		io.writeln("Some data will be stored in estat.csv");
		
		//executar la funcionalitat
		try
		{
			c.guardarEstat();
		}catch(Exception e) {
			System.out.println("ERROR!!!");
			System.out.println(e.getMessage());
			return;
		}
		
		//mostrar output
		io.writeln("DONE!");
		io.writeln("You can check it by going to /data/<your_folder> and searching for your file.");
	}
	
	
	static private boolean isWritable() {
		String name;
		try { name = c.getNomProjecte(); }
		catch(Exception e) {return false;}
		
		if (name == null) return false;
		else if (name.length() < 5) return false;
		
		String d = "dummy";
		
		for (int i = 0; i < 5; ++i)
			if (name.charAt(i) != d.charAt(i)) return false;
			
		return true;
	}
	static private void initializeDummyData() {
		testData = new ArrayList<ArrayList<String>>();
		
		testData.add(new ArrayList<String>());
		testData.get(0).add("id");
		testData.get(0).add("nombre_receta");
		testData.get(0).add("descripción");
		
		testData.add(new ArrayList<String>());
		testData.get(1).add(Integer.toString(10));
		testData.get(1).add("\"Ensalada\"");
		testData.get(1).add("\"Mezclar todo y aliñar con aceite, vinagre y sal." +
			" Servir fresquito.\"");
		
		testData.add(new ArrayList<String>());
		testData.get(2).add(Integer.toString(32));
		testData.get(2).add("\"Spaguetti carbonara\"");
		testData.get(2).add("\"Cocer los spaguetti Hacer el bacon a la plancha." + 
			" Añadir los spaguetti. Por ultimo añadir la nata y pimienta al gusto\"");
		
		testData.add(new ArrayList<String>());
		testData.get(3).add(Integer.toString(9678));
		testData.get(3).add("\"Solomillo al oporto\"");
		testData.get(3).add("\"Salpimentar las puntas de solomillo y freirlas en" + 
			" la mantequilla derretida en una sartén durante un par de" + 
			" minutos por cada lado. Sacarlos y reservarlos. Añadir a la" + 
			" misma sartén el oporto, cocer 3 minutos a fuego vivo e incorporar" +
			" la mostaza y la nata líquida con la Maizena disuelta y unas gotas" + 
			" de limón. Cocer. Introducir de nuevo los filetes en la salsa para" + 
			" que den unos hervores, colocarlos en una fuente y cubrirlos con la" + 
			" salsa. Servirlos calientes con arroz basmati.\"");
	}
	static private void initializeDummyItems() {
		testItemsData = new ArrayList<ArrayList<ArrayList<String>>>();
		
		testItemsData.add(new ArrayList<ArrayList<String>>());
		testItemsData.get(0).add(new ArrayList<String>());
		testItemsData.get(0).get(0).add("3");
		testItemsData.get(0).add(new ArrayList<String>());
		testItemsData.get(0).get(1).add("entrecot");
		testItemsData.get(0).add(new ArrayList<String>());
		testItemsData.get(0).get(2).add("patatas");
		testItemsData.get(0).get(2).add("ensalada");
		testItemsData.get(0).get(2).add("arroz");
		
		testItemsData.add(new ArrayList<ArrayList<String>>());
		testItemsData.get(1).add(new ArrayList<String>());
		testItemsData.get(1).get(0).add("14");
		testItemsData.get(1).add(new ArrayList<String>());
		testItemsData.get(1).get(1).add("lomo_a_la_plancha");
		testItemsData.get(1).add(new ArrayList<String>());
		testItemsData.get(1).get(2).add("zanahoria");
		testItemsData.get(1).get(2).add("croquetas");
		
		testItemsData.add(new ArrayList<ArrayList<String>>());
		testItemsData.get(2).add(new ArrayList<String>());
		testItemsData.get(2).get(0).add("15");
		testItemsData.get(2).add(new ArrayList<String>());
		testItemsData.get(2).get(1).add("strogonof");
		testItemsData.get(2).add(new ArrayList<String>());
		testItemsData.get(2).get(2).add("patatas");
		
		testItemsData.add(new ArrayList<ArrayList<String>>());
		testItemsData.get(3).add(new ArrayList<String>());
		testItemsData.get(3).get(0).add("92");
		testItemsData.get(3).add(new ArrayList<String>());
		testItemsData.get(3).get(1).add("arroz_con_feijao");
		testItemsData.get(3).add(new ArrayList<String>());
		testItemsData.get(3).get(2).add("\"salsa picante, con ajo y perejil\"");
		testItemsData.get(3).get(2).add("farofa");
		
		testItemsData.add(new ArrayList<ArrayList<String>>());
		testItemsData.get(4).add(new ArrayList<String>());
		testItemsData.get(4).get(0).add("65");
		testItemsData.get(4).add(new ArrayList<String>());
		testItemsData.get(4).get(1).add("sopa");
		testItemsData.get(4).add(new ArrayList<String>());
		testItemsData.get(4).get(2).add("");
	}
	static private void initializeDummyHeader() {
		testSimpleHeader = new ArrayList<String>();
		
		testSimpleHeader.add("id");
		testSimpleHeader.add("plato_principal");
		testSimpleHeader.add("acompañamientos_y_aderezo");
	}
	static private void initializeDummyArray6() {
		testArray6Values = new ArrayList<String>();
		
		testArray6Values.add("0");
		testArray6Values.add("3");
		testArray6Values.add("8");
		testArray6Values.add("dummy");
		testArray6Values.add("42");
		testArray6Values.add("97");
	}
}
