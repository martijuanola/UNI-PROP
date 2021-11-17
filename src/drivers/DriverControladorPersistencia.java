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
			"7. Exists preprocessed data? \n" +
			"8. Exists data saved to use with the algoritm (internal attributes)? \n" +
			"9. Exists tests? \n" +
			"10.Load any .csv file from the folder\n" +
			"11.Load items' atrributes' weights\n" +
			"12.Load items' atrributes' type\n" +
			"13.Load test known\n" +
			"14.Load test unknown\n" +
			"15.Load algorithm's attributes\n" +
			"-1. exit\n";
			
			String reduced = "Options: \n" +
			"0. Show options\n" + "-1. exit\n";
			
			io.writeln("Testing class ControladorPersistencia");
			int x;
			do {
				io.writeln("\n--------------------------------------------------\n");
				io.writeln("Enter the number of the function you want to test. \n");
				io.writeln(s);
				io.write("Option: ");
				
				x = io.readint();
				
				switch(x)
				{
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
					default:
				}
				
			} while (x != -1);
			
			io.writeln("Test ended");
			
		}catch(Exception e){
			System.out.println("An error has occurred");
		}
    }
    
    static private void testEscollirProjecte() throws Exception {
		io.writeln("Testing function testEscollirProjecte()");
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
		io.writeln("Testing function testLlistatCarpetes()");
		//demanar l'input
				
		//executar la funcionalitat
		ArrayList<String> a = c.llistatCarpetes();
		
		//mostrar output
		io.writeln("\nThese are the projects you can load:");
		for (int i = 0; i < a.size(); ++i) 
			io.writeln("\t" + a.get(i));	
	}
	static private void testGetNomProjecte() throws Exception {
		io.writeln("Testing function testGetNomProjecte()");
		//demanar l'input
				
		//executar la funcionalitat
		String p = c.getNomProjecte();
		
		//mostrar output
		if (p == null) io.writeln("There is not any project being used right now");
		else io.writeln("The project " + p + " is now being used");	
	}
    static private void testCarregarRecomanacionsCarpeta() throws Exception {
		io.writeln("Testing function testCarregarRecomanacionsCarpeta()");
		//demanar l'input
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<ArrayList<String>> sol = c.carregarRecomanacionsCarpeta();
		
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
		io.writeln("Testing function testCarregarItemsCarpeta()");
		//demanar l'input
		if (c.getNomProjecte() == null)
		{
			io.writeln("Choose a project before reading the files");
			io.writeln("You can do it by choosing the option 1 on the main menu");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<ArrayList<String>> sol = c.carregarItemsCarpeta();
		
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
		int cols = sol.get(0).size();
		io.writeln("Rows read: " + sol.size());
		//for (int i = 0; i < sol.get(0).size(); ++i) ++cols;
		
		
		for (int i = 0; i < sol.size(); ++i)
		{
			//for (int j = 0; j < sol.get(i).size(); ++j) io.write(sol.get(i).get(j) + " ");
			//io.writeln();
			int temp_cols = sol.get(i).size();
			//for (int j = 0; j < sol.get(i).size(); ++j) ++temp_cols;
			if (temp_cols != cols)
			{
				io.writeln("Error al llegir la fila " + i + ". LLegiex " + temp_cols);
				for (int j = 0; j < sol.get(i).size(); ++j) io.write(sol.get(i).get(j) + "|");
				io.writeln();
			}
			//io.writeln();
		}
		io.writeln("Cols: " + cols);
	}
    static private void testCarregarFitxerExtern() throws Exception {
		io.writeln("Testing function testCarregarFitxerExtern()");
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
			io.writeln("Error loading the file. it odesn't exist or is not valid.");
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
}
