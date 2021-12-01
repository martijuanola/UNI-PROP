package src.recomanador.domini;
import java.util.HashMap;

/**
 * This class stores three HashMaps in order to be able to store each centroud ratings and to calculate the mean of all values in the centroid.
 * In c++ terms, this would be a Struct.
 * 
 * @author Adrià F.
 */
public class Centroid {
    /**Contains, for each item, its rating in the Centroid (initially it will be randomized)*/
    public HashMap<Item,Float> valoracio;
    /**Contains, for each item, the sum of the ratings of the item of all the users in the centroid */
    public HashMap<Item,Float> sum;
    /**Contains, for each item, the quantity of users in the centroid who have rated the item */
    public HashMap<Item,Integer> quant;
}
