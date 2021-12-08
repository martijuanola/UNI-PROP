package src.junits;

import src.recomanador.persistencia.ControladorLoad;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestControladorLoad {
	@Test
	public void CarregarArxiuTest() {
		ControladorLoad cl = new ControladorLoad();
		File arxiu = new File("data");
		arxiu = new File(arxiu, "movies.sample");
		arxiu = new File(arxiu, "ratings.db.csv");
		
		try
		{
			ArrayList<ArrayList<String>> output = cl.carregarArxiu(arxiu);
		} catch(Exception e) {
			System.out.print("ERROR: " + e.getMessage());
		}
	}
	
}
