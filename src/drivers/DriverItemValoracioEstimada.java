package src.drivers;

import java.util.ArrayList;
import java.util.Collections;

import src.recomanador.domini.Item;
import src.recomanador.domini.ItemValoracioEstimada;
import src.recomanador.domini.Item.tipus;
import src.recomanador.excepcions.ItemStaticValuesNotInitializedException;
import src.recomanador.excepcions.ItemWeightNotCorrectException;
import src.recomanador.excepcions.ItemIdNotValidException;

/**
 * This class is meant to test the class ItemValoracioEstimada.
 * To simplify testing, i have changed the type of the attribute
 * item from Item to String.
 * 
 * @author Adrià F.
 */
public class DriverItemValoracioEstimada {
    public static void main(String[] args) throws ItemStaticValuesNotInitializedException {
        ArrayList<ItemValoracioEstimada> items = new ArrayList<ItemValoracioEstimada>();

        //inicalitzar statics item
		ArrayList<String> as = new ArrayList<String>();
        as.add("id");
        try{ Item.inicialitzarStaticsDefault(as); }
        catch(Exception e) {System.out.println("ERROR: " + e.getMessage());return;}

        items.add(new ItemValoracioEstimada(2.5f, new Item(1)));
        items.add(new ItemValoracioEstimada(5.5f, new Item(2)));
        items.add(new ItemValoracioEstimada(0.5f, new Item(3)));
        items.add(new ItemValoracioEstimada(7.5f, new Item(4)));
        items.add(new ItemValoracioEstimada(1.5f, new Item(5)));

        Collections.sort(items);

        System.out.println("ORDENACIÓ:");
        for(int i = 0; i < items.size(); ++i) {
            System.out.println("L'item " + items.get(i).item.getId() + " te valoració " + items.get(i).valoracioEstimada);
        }
    }
    
}
