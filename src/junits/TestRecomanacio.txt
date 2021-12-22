package src.junits;

import src.recomanador.domini.Recomanacio;

import src.recomanador.domini.Item;
import src.recomanador.domini.Usuari;
import src.recomanador.excepcions.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.After;

/**
 * Junit for the class Recomanacio
 * @author     Martí J.
 */
public class TestRecomanacio {

	/*------Funcions Before / After------*/
	
	@BeforeClass
	public static void beforeClass() {
		try{
			ArrayList<String> header = new ArrayList<String>();
			header.add("id");
			Item.inicialitzarStaticsDefault(header);
		}
		catch(Exception e) {
			System.out.println("ERROR: " + e.getMessage());
		}
	}
	
	/*------ Constructors ------*/
	
	//Recomanacio(Usuari,Item)
	@Test
	public void Constructor1Test() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			Recomanacio r = new Recomanacio(u, i);
			assertSame(u,r.getUsuari());
			assertSame(i,r.getItem());
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	//Recomanacio(Usuari,Item,float)
	@Test
	public void Constructor2Test() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			float f = 2.5f;
			Recomanacio r = new Recomanacio(u, i, f);
			assertSame(u,r.getUsuari());
			assertSame(i,r.getItem());
			assertEquals(f,r.getVal(),0);
		}
		catch(ItemStaticValuesNotInitializedException | DataNotValidException e) {} //No s'ha de llançar mai
	}
	
	@Test (expected = DataNotValidException.class)
	public void Constructor2WithInvalidRating1Test() throws Exception {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			float f = -1.0f;
			Recomanacio r = new Recomanacio(u, i, f);
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	@Test (expected = DataNotValidException.class)
	public void Constructor2WithInvalidRating2Test() throws Exception {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			float f = 6.0f;
			Recomanacio r = new Recomanacio(u, i, f);
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	@Test (expected = DataNotValidException.class)
	public void Constructor2WithInvalidRating3Test() throws Exception {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			float f = 2.6f;
			Recomanacio r = new Recomanacio(u, i, f);
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	
	/*------ Setters ------*/
	
	//setVal()
	@Test
	public void SetValTest() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			float f = 2.5f;
			Recomanacio r = new Recomanacio(u, i);
			r.setVal(f);
			assertEquals(f,r.getVal(),0);
		}
		catch(ItemStaticValuesNotInitializedException | RatingNotValidException e) {} //No s'ha de llançar mai
	}
	
	@Test (expected = RatingNotValidException.class)
	public void SetValWithInvalidRating1Test() throws Exception {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			float f = -1.0f;
			Recomanacio r = new Recomanacio(u, i);
			r.setVal(f);
			assertEquals(f,r.getVal(),0);
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	@Test (expected = RatingNotValidException.class)
	public void SetValWithInvalidRating2Test() throws Exception {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			float f = 6f;
			Recomanacio r = new Recomanacio(u, i);
			r.setVal(f);
			assertEquals(f,r.getVal(),0);
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	@Test (expected = RatingNotValidException.class)
	public void SetValWithInvalidRating3Test() throws Exception {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			float f = 2.7f;
			Recomanacio r = new Recomanacio(u, i);
			r.setVal(f);
			assertEquals(f,r.getVal(),0);
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	
	
	/*------ Getters ------*/
	
	//getUsuari()
	@Test
	public void GetUsuariTest() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			Recomanacio r = new Recomanacio(u, i);
			assertSame(u,r.getUsuari());
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}

	//getItem()
	@Test
	public void GetItemTest() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			Recomanacio r = new Recomanacio(u, i);
			assertSame(i,r.getItem());
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	//getVal()
	@Test
	public void GetValTest() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			float f = 2.5f;
			Recomanacio r = new Recomanacio(u, i, f);
			assertEquals(f,r.getVal(),0);
		}
		catch(ItemStaticValuesNotInitializedException | DataNotValidException e) {} //No s'ha de llançar mai
	}
	
	//recomanacioValorada()
	@Test
	public void RecomanacioValorada1Test() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			float f = 2.5f;
			Recomanacio r = new Recomanacio(u, i, f);
			assertTrue(r.recomanacioValorada());
		}
		catch(ItemStaticValuesNotInitializedException | DataNotValidException e) {} //No s'ha de llançar mai
	}
	
	@Test
	public void RecomanacioValorada2Test() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			Recomanacio r = new Recomanacio(u, i);
			assertFalse(r.recomanacioValorada());
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	//checkIds(int,int)
	@Test
	public void CheckIds1Test() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			Recomanacio r = new Recomanacio(u, i);
			assertTrue(r.checkIds(u.getId(),i.getId()));
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	@Test
	public void CheckIds2Test() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			Recomanacio r = new Recomanacio(u, i);
			assertFalse(r.checkIds(0,i.getId()));
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	@Test
	public void CheckIds3Test() {
		try{
			Usuari u = new Usuari(1);
			Item i = new Item(1);
			Recomanacio r = new Recomanacio(u, i);
			assertFalse(r.checkIds(u.getId(),0));
		}
		catch(ItemStaticValuesNotInitializedException e) {} //No s'ha de llançar mai
	}
	
	//compareTo(Recomanacio)
	@Test
	public void compareTo1() {
		try{
			Usuari u1 = new Usuari(2);
			Item i1 = new Item(2);
			float f1 = 2f;
			Recomanacio r1 = new Recomanacio(u1, i1, f1);
			
			Usuari u2 = new Usuari(1);
			Item i2 = new Item(2);
			float f2 = 2f;
			Recomanacio r2 = new Recomanacio(u2, i2, f2);
			
			assertEquals(1,r1.compareTo(r2));
		}
		catch(ItemStaticValuesNotInitializedException | DataNotValidException e) {} //No s'ha de llançar mai
	}
	
	@Test
	public void compareTo2() {
		try{
			Usuari u1 = new Usuari(2);
			Item i1 = new Item(2);
			float f1 = 2f;
			Recomanacio r1 = new Recomanacio(u1, i1, f1);
			
			Usuari u2 = new Usuari(3);
			Item i2 = new Item(2);
			float f2 = 2f;
			Recomanacio r2 = new Recomanacio(u2, i2, f2);
			
			assertEquals(-1,r1.compareTo(r2));
		}
		catch(ItemStaticValuesNotInitializedException | DataNotValidException e) {} //No s'ha de llançar mai
	}
	
	@Test
	public void compareTo3() {
		try{
			Usuari u1 = new Usuari(2);
			Item i1 = new Item(2);
			float f1 = 2f;
			Recomanacio r1 = new Recomanacio(u1, i1, f1);
			
			Usuari u2 = new Usuari(2);
			Item i2 = new Item(1);
			float f2 = 2f;
			Recomanacio r2 = new Recomanacio(u2, i2, f2);
			
			assertEquals(1,r1.compareTo(r2));
		}
		catch(ItemStaticValuesNotInitializedException | DataNotValidException e) {} //No s'ha de llançar mai
	}
	
	@Test
	public void compareTo4() {
		try{
			Usuari u1 = new Usuari(2);
			Item i1 = new Item(2);
			float f1 = 2f;
			Recomanacio r1 = new Recomanacio(u1, i1, f1);
			
			Usuari u2 = new Usuari(2);
			Item i2 = new Item(3);
			float f2 = 2f;
			Recomanacio r2 = new Recomanacio(u2, i2, f2);
			
			assertEquals(-1,r1.compareTo(r2));
		}
		catch(ItemStaticValuesNotInitializedException | DataNotValidException e) {} //No s'ha de llançar mai
	}
	
	@Test
	public void compareTo5() {
		try{
			Usuari u1 = new Usuari(2);
			Item i1 = new Item(2);
			float f1 = 2f;
			Recomanacio r1 = new Recomanacio(u1, i1, f1);
			
			Usuari u2 = new Usuari(2);
			Item i2 = new Item(2);
			float f2 = 1f;
			Recomanacio r2 = new Recomanacio(u2, i2, f2);
			
			assertEquals(1,r1.compareTo(r2));
		}
		catch(ItemStaticValuesNotInitializedException | DataNotValidException e) {} //No s'ha de llançar mai
	}
	
	@Test
	public void compareTo6() {
		try{
			Usuari u1 = new Usuari(2);
			Item i1 = new Item(2);
			float f1 = 2f;
			Recomanacio r1 = new Recomanacio(u1, i1, f1);
			
			Usuari u2 = new Usuari(2);
			Item i2 = new Item(2);
			float f2 = 3f;
			Recomanacio r2 = new Recomanacio(u2, i2, f2);
			
			assertEquals(-1,r1.compareTo(r2));
		}
		catch(ItemStaticValuesNotInitializedException | DataNotValidException e) {} //No s'ha de llançar mai
	}
	
	@Test
	public void compareTo7() {
		try{
			Usuari u1 = new Usuari(2);
			Item i1 = new Item(2);
			float f1 = 2f;
			Recomanacio r1 = new Recomanacio(u1, i1, f1);
			
			Usuari u2 = new Usuari(2);
			Item i2 = new Item(2);
			float f2 = 2f;
			Recomanacio r2 = new Recomanacio(u2, i2, f2);
			
			assertEquals(0,r1.compareTo(r2));
		}
		catch(ItemStaticValuesNotInitializedException | DataNotValidException e) {} //No s'ha de llançar mai
	}
}
