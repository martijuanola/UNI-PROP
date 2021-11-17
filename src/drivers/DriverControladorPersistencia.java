package src.drivers;

import src.recomanador.persistencia.ControladorPersistencia;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverControladorPersistencia {
    
    static private ControladorPersistencia c;
    static private inout io;
    
    public static void main(String[] args) {
        try{
			c = new ControladorPersistencia();
			io = new inout();
			
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
			"9. Exists data saved to use with the algoritm (internal attributes)? \n" +
			"10. Exists tests? \n" +
			"11. Load any .csv file from the folder\n" +
			"12. Load items' atrributes' weights\n" +
			"13. Load items' atrributes' type\n" +
			"14. Load test known\n" +
			"15. Load test unknown\n" +
			"16. Load algorithm's attributes\n" +
			"17. Create a new project save data\n" +
			"18. Save data (any into any file)\n" +
			"19. Save recomendations and valorations\n" +
			"20. Save items\n" +
			"21. Save items' attributes' weights\n" +
			"22. Save items' atrributes' type\n" +
			"23. Save algorithm's attributes\n" +
			"-1. exit\n";
			
			String reduced = "Options: \n" +
			"0. Show options\n" + "-1. exit\n";
			
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
						testExisteixenDadesAlgorisme();
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
						break;
					case 16:
						break;
					case 17:
						break;
					case 18:
						break;
					case 19:
						break;
					case 20:
						break;
					case 21:
						break;
					case 22:
						break;
					case 23:
						break;
					default:
				}
				
			} while (x != -1);
			
			io.writeln("Test ended");
			
		}catch(Exception e){
			System.out.println("An error has occurred");
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
				" and checking if both files exist: pesos.csv and tipus.csv");
		}
	}
	static private void testExisteixenDadesAlgorisme() throws Exception {
		io.writeln("Testing function existeixenDadesAlgorisme()\n");
		//demanar l'input
		io.writeln("Does the algorithm attributes data exist in the project folder?");
		io.writeln("(If you are not on one, you can change it by choosing option 1 at the main menu.)");
		//executar la funcionalitat
		if (c.existeixenDadesAlgorisme()) io.writeln("ANSWER: yes");
		else io.writeln("ANSWER: no");
		
		//mostrar output
		if (c.getNomProjecte() == null)
		{
			io.writeln("\nYou aren't in any project folder.");
		}
		else
		{
			io.writeln("\nYou can check its veracity by going to the folder " + c.getNomProjecte() + 
				" and checking if the file algorisme.csv exists.");
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
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
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
	
	
	
	static private void test() throws Exception {
		io.writeln("Testing function ()\n");
		//demanar l'input
		
		//executar la funcionalitat
		
		//mostrar output
	}
}
