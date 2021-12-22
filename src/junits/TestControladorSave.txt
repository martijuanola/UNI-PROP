package src.junits;

import src.recomanador.persistencia.ControladorSave;
import src.recomanador.persistencia.ControladorLoad;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;

/**
 * Junit for the class ControladorSave
 * @author     Pol
 */
public class TestControladorSave {
	/* NOTA: Per a provar que aquesta classe funciona correctament, s'ha
	 * s'ha de fer una lectura del que s'ha escrit en el fitxer, i
	 * s'usarà el ControladorLoad per a provar-ho. Per tant, abans d'aquest
	 * test s'ha d'haver comprovat la classe ControladorLoad.
	 */
	
	private ArrayList<ArrayList<String>> generate_data()
	{
		ArrayList<ArrayList<String>> real = new ArrayList<ArrayList<String>>();
		real.add(new ArrayList<String>());
		real.get(0).add("id");
		real.get(0).add("nombre_receta");
		real.get(0).add("descripcion");
		
		real.add(new ArrayList<String>());
		real.get(1).add("10");
		real.get(1).add("\"Ensalada\"");
		real.get(1).add("\"Mezclar todo y aliñar con aceite, vinagre y sal. Servir fresquito.\"");
		
		real.add(new ArrayList<String>());
		real.get(2).add("32");
		real.get(2).add("\"Spaguetti carbonara\"");
		real.get(2).add("\"Cocer los spaguetti Hacer el bacon a la plancha. Añadir los spaguetti. Por ultimo añadir la nata y pimienta al gusto\"");
		
		real.add(new ArrayList<String>());
		real.get(3).add("9678");
		real.get(3).add("\"Solomillo al oporto\"");
		real.get(3).add("\"Salpimentar las puntas de solomillo y freirlas en la mantequilla derretida en una sartén durante un par de minutos por cada lado. Sacarlos y reservarlos. Añadir a la misma sartén el oporto, cocer 3 minutos a fuego vivo e incorporar la mostaza y la nata líquida con la Maizena disuelta y unas gotas de limón. Cocer. Introducir de nuevo los filetes en la salsa para que den unos hervores, colocarlos en una fuente y cubrirlos con la salsa. Servirlos calientes con arroz basmati.\"");
		
		return real;
	}
	
	@Test
	public void GuardarArxiuTest() {
		ControladorSave cs = ControladorSave.getInstance();
		File arxiu = new File("data");
		arxiu = new File(arxiu, "JUNIT-TEST-ControladorSave");
		arxiu = new File(arxiu, "items.csv");
		
		try
		{
			cs.guardarArxiu(arxiu, generate_data());
		} catch(Exception e) {
			System.out.print("ERROR: " + e.getMessage());
		}
		
		//Inici load per a provar la correctesa
		ControladorLoad cl = ControladorLoad.getInstance();
		ArrayList<ArrayList<String>> data_read = null;
		try {data_read = cl.carregarArxiu(arxiu); }
		catch(Exception e) { System.out.print("ERROR: " + e.getMessage()); }
				
		assertEquals("Test de guardar dades", data_read, generate_data());
	}
	
}
