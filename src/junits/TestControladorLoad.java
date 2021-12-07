package src.junit;

import src.recomanador.persistencia.ControladorLoad;
import java.io.IOException;
import java.util.ArrayList;
import java.io.File;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestControladorLoad {
	@Test
	public void CarregarArxiuTest() {
		ControladorLoad cl = new ControladorLoad();
		File arxiu = new File("data");
		arxiu = new File(arxiu, "movies.sample");
		arxiu = new File(arxiu, "ratings.db.csv");
		
		ArrayList<ArrayList<String>> output = cl.carregarArxiu(arxiu);
	}
	
}
