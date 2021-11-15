package src.drivers;

import java.util.ArrayList;
import java.util.Collections;

import javax.sound.sampled.SourceDataLine;

import src.recomanador.domini.ItemValoracioEstimada;

/**
 * This class is meant to test the class ItemValoracioEstimada.
 * To simplify testing, i have changed the type of the attribute
 * item from Item to String.
 * 
 * @author Adrià F.
 */
public class DriverItemValoracioEstimada {
    public static void main(String[] args) {
        ArrayList<ItemValoracioEstimada> items = new ArrayList<ItemValoracioEstimada>();

        items.add(new ItemValoracioEstimada(2.5f, "itemA"));
        items.add(new ItemValoracioEstimada(5.5f, "itemB"));
        items.add(new ItemValoracioEstimada(0.5f, "itemC"));
        items.add(new ItemValoracioEstimada(7.5f, "itemD"));
        items.add(new ItemValoracioEstimada(1.5f, "itemE"));

        Collections.sort(items);

        for(int i = 0; i < items.size(); ++i) {
            System.out.println(items.get(i).item);
        }
    }
    
}
