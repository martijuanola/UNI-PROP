package src.junits;

import src.recomanador.persistencia.ControladorPersistencia;
//import src.recomanador.persistencia.ControladorSave;
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

public class TestControladorPersistencia {
	
	/* NOTA: Per a provar que aquesta classe funciona correctament, s'ha
	 * s'ha de fer una lectura del que s'ha escrit en el fitxer, i
	 * s'usarà el ControladorLoad per a provar-ho. Per tant, abans d'aquest
	 * test s'ha d'haver comprovat la classe ControladorLoad.
	 * També s'ha d'haver provar la classe ControladorSave, ja que
	 * ControladorPersistencia depen tant de la classe ControladorSave 
	 * com de la classe ControladorLoad.
	 */
	/*------Atributs------*/
	private static ArrayList<ArrayList<String>> dummy_data;
	
	/*------Funcions Before / After------*/
	
	@BeforeClass
	public static void beforeClass() {
		//Genera dades per a poder guardar-les
		
		//dummy_data
		dummy_data = new ArrayList<ArrayList<String>>();
		dummy_data.add(new ArrayList<String>());
		dummy_data.get(0).add("id");
		dummy_data.get(0).add("nombre_receta");
		dummy_data.get(0).add("descripcion");
		
		dummy_data.add(new ArrayList<String>());
		dummy_data.get(1).add("10");
		dummy_data.get(1).add("\"Ensalada\"");
		dummy_data.get(1).add("\"Mezclar todo y aliñar con aceite, vinagre y sal. Servir fresquito.\"");
		
		dummy_data.add(new ArrayList<String>());
		dummy_data.get(2).add("32");
		dummy_data.get(2).add("\"Spaguetti carbonara\"");
		dummy_data.get(2).add("\"Cocer los spaguetti Hacer el bacon a la plancha. Añadir los spaguetti. Por ultimo añadir la nata y pimienta al gusto\"");
		
		dummy_data.add(new ArrayList<String>());
		dummy_data.get(3).add("9678");
		dummy_data.get(3).add("\"Solomillo al oporto\"");
		dummy_data.get(3).add("\"Salpimentar las puntas de solomillo y freirlas en la mantequilla derretida en una sartén durante un par de minutos por cada lado. Sacarlos y reservarlos. Añadir a la misma sartén el oporto, cocer 3 minutos a fuego vivo e incorporar la mostaza y la nata líquida con la Maizena disuelta y unas gotas de limón. Cocer. Introducir de nuevo los filetes en la salsa para que den unos hervores, colocarlos en una fuente y cubrirlos con la salsa. Servirlos calientes con arroz basmati.\"");
		
		//data_estat
	}
	
	/*------Funcions Testos------*/
	
	@Test
	public void escollirProjecte_and_getNomProjecte_Test() {
		/* Aquestes dues funcions es proven com un conjunt ja que per
		 * a provar la segona es necessita la primera, però per a provar
		 * la primera es necessita la segona
		 */
		ControladorPersistencia cp = ControladorPersistencia.getInstance();
		
		try {
			cp.escollirProjecte("JUNIT-TEST-ControladorPersistencia");
		} catch(Exception e) {
			System.out.print("ERROR: " + e.getMessage());
		}
		
		String s = "";
		
		try {
			s = cp.getNomProjecte();
		} catch(Exception e) {
			System.out.print("ERROR: " + e.getMessage());
		}
		
		assertEquals("Test de escollir projecte i saber-ne el nom", s, 
					 "JUNIT-TEST-ControladorPersistencia");
	}
	
}
