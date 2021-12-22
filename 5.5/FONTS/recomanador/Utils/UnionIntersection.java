package src.recomanador.Utils;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Class for working with arraylists that act like sets, in particular, the union and intersection of those sets
 * @author Jaume C.
 */
public class UnionIntersection {
    
    /**
     * Returns the union of two ArrayLists
     * @param list1 first ArrayList of strings
     * @param list2 second ArrayList of strings
     * @return ArrayList with all the elements of both lists without repetitions
     */
    public static ArrayList<String> getUnion(ArrayList<String> list1, ArrayList<String> list2) {
        HashSet<String> hs = new HashSet<>();
 
        for (int i = 0; i < list1.size(); i++) {
            hs.add(list1.get(i));
        }
        for (int i = 0; i < list2.size(); i++) {
            hs.add(list2.get(i));
        }

        return new ArrayList<String>(hs);
    }
 
    /**
     * Returns the intersection of two ArrayLists
     * @param list1 first ArrayList of strings
     * @param list2 second ArrayList of strings
     * @return ArrayList with all the common elements in list1 and list2
     */
    public static ArrayList<String> getIntersection(ArrayList<String> list1, ArrayList<String> list2)
    {
        HashSet<String> hs = new HashSet<>();
 
        for (int i = 0; i < list1.size(); i++) {
            hs.add(list1.get(i));
        }

        ArrayList<String> result = new ArrayList<String>();

        for (int i = 0; i < list2.size(); i++) {
            if (hs.contains(list2.get(i))) {
                result.add(list2.get(i));
            }
        }

        return result;
    }

}
