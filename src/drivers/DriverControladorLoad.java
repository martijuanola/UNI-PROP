package src.drivers;

import src.recomanador.persistencia.ControladorLoad;
import java.io.File;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverControladorLoad {
    
    static private ControladorLoad c;
    static private inout io;
    
    public static void main(String[] args) {
        try{
			c = ControladorLoad.getInstance();
			io = new inout();
			
			String s = "Options: \n" +
			"0. exit\n" +
			"1. Load .csv file\n";
			
			io.writeln("\n==================================================\n");
			io.writeln("Testing class ControladorLoad");
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
						testCarregarArxiu();
						break;
					default:
				}
				
			} while (x != 0);
			
			io.writeln("Test ended");
			io.writeln("\n==================================================\n");
			
		}catch(Exception e){
			System.out.println("An error has occurred");
			System.out.println(e.getMessage());
		}
    }
    
    static private void testCarregarArxiu() throws Exception {
		io.writeln("Testing function carregarArxiu()");
		
		//demanar l'input
		io.writeln("Write the path of the .csv file that you want to read");
		io.writeln("(If you don't know what to chose, you have an example at data/movies-2250/ratings.db.csv)");
		
		String p = "data/movies-2250/items.csv";
		io.write("Path: ");
		
		Scanner scanner = new Scanner(System.in);
		File f = null;
		
		p = scanner.nextLine();
		f = new File(p);
		if (!f.exists()) 
		{
			io.writeln("Wrong path");
			return;
		}
		
		//executar la funcionalitat
		ArrayList<ArrayList<String>> sol = new ArrayList<ArrayList<String>>();
		try
		{
			//Path relatiu
			sol = c.carregarArxiu(f);
		}catch(Exception e)
		{
			io.writeln("ERROR!!!");
			io.writeln("The file is not valid.");
			return;
		}
		
		//mostrar output
		int cols = sol.get(0).size();
		io.writeln("Rows read: " + sol.size());
		io.writeln("Columns: " + cols);
		
		for (int i = 0; i < sol.size(); ++i)
		{
			int temp_cols = sol.get(i).size();
			if (temp_cols != cols)
			{
				io.writeln("Error reading row" + i + ". Columns read: " + temp_cols);
				for (int j = 0; j < sol.get(i).size(); ++j) io.write(sol.get(i).get(j) + "|");
				io.writeln();
			}
		}
		
		
		io.writeln();
		io.writeln("Head of the file read:");
		int n = sol.size();
		if (11 < n) n = 11;
		
		for (int i = 0; i < n; ++i)
		{
			io.write("\t");
			for (int j = 0; j < sol.get(i).size(); ++j) 
			{
				io.write(sol.get(i).get(j));
				if (j != sol.get(i).size()-1) io.write(" | ");
			}
			io.writeln();
		}
		
		if (sol.size() > 11) io.writeln("...");
	}
}
