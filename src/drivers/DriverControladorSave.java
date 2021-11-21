package src.drivers;

import src.recomanador.persistencia.ControladorSave;
import java.io.File;

import java.util.ArrayList;
import java.util.Scanner;

public class DriverControladorSave {
    
    static private ControladorSave c;
    static private inout io;
    static private File carpeta;
    static private ArrayList<ArrayList<String>> a;
    
    public static void main(String[] args) {
        try{
			c = new ControladorSave();
			io = new inout();
			a = new ArrayList<ArrayList<String>>();
			initializeDummyData();
			
			carpeta = new File(new File("data"), "testing-only-DriverSave-store");
			if (!carpeta.exists()) carpeta.mkdirs();
			
			
			String s = "Options: \n" +
			"0. exit\n" +
			"1. Store .csv file\n";
			io.writeln("\n==================================================\n");
			io.writeln("Testing class ControladorSave");
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
						testGuardarArxiu();
						break;
					default:
				}
				
			} while (x != 0);
			
			io.writeln("Test ended");
			io.writeln("\n==================================================\n");
			
		}catch(Exception e){
			System.out.println("An error has occurred");
		}
    }
    
    static private void testGuardarArxiu() throws Exception {
		io.writeln("Testing function guardarArxiu()");
		
		//demanar l'input
		io.writeln("Write the path of the .csv file where you want to store some information");
				
		String p = "nothing.csv";
		io.write("Name: ");
		
		Scanner scanner = new Scanner(System.in);
		File f = null;
		
		p = scanner.nextLine();
		f = new File(carpeta, p);
		if (!f.exists()) f.createNewFile();
				
		//executar la funcionalitat
		try
		{
			c.guardarArxiu(f, a);
		}catch(Exception e)
		{
			io.writeln("Error saving the file");
		}
		
		//mostrar output
		io.writeln("DONE!");
		io.writeln("You can check if it has worked at data/testing-only-DriverSave-store/"+p);
	}
	
	static private void initializeDummyData()
	{
		a = new ArrayList<ArrayList<String>>();
		
		a.add(new ArrayList<String>());
		a.get(0).add("id");
		a.get(0).add("nombre_receta");
		a.get(0).add("descripción");
		
		a.add(new ArrayList<String>());
		a.get(1).add(Integer.toString(10));
		a.get(1).add("\"Ensalada\"");
		a.get(1).add("\"Mezclar todo y aliñar con aceite, vinagre y sal." +
			" Servir fresquito.\"");
		
		a.add(new ArrayList<String>());
		a.get(2).add(Integer.toString(32));
		a.get(2).add("\"Spaguetti carbonara\"");
		a.get(2).add("\"Cocer los spaguetti Hacer el bacon a la plancha." + 
			" Añadir los spaguetti. Por ultimo añadir la nata y pimienta al gusto\"");
		
		a.add(new ArrayList<String>());
		a.get(3).add(Integer.toString(9678));
		a.get(3).add("\"Solomillo al oporto\"");
		a.get(3).add("\"Salpimentar las puntas de solomillo y freirlas en" + 
			" la mantequilla derretida en una sartén durante un par de" + 
			" minutos por cada lado. Sacarlos y reservarlos. Añadir a la" + 
			" misma sartén el oporto, cocer 3 minutos a fuego vivo e incorporar" +
			" la mostaza y la nata líquida con la Maizena disuelta y unas gotas" + 
			" de limón. Cocer. Introducir de nuevo los filetes en la salsa para" + 
			" que den unos hervores, colocarlos en una fuente y cubrirlos con la" + 
			" salsa. Servirlos calientes con arroz basmati.\"");
	}
}
