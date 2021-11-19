package src.recomanador.domini.Utils;

import java.util.ArrayList;
import java.util.HashSet;
import src.recomanador.domini.Item;

public class UnionIntersection {
    
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
 
    // Prints intersection of arr1[0..m-1] and arr2[0..n-1]
    public static ArrayList<String> getIntersection(ArrayList<String> list1, ArrayList<String> list2)
    {
        HashSet<String> hs = new HashSet<>();
 
        for (int i = 0; i < list1.size(); i++)
            hs.add(list1.get(i));

        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < list2.size(); i++) {
            if (hs.contains(list2.get(i))) {
                result.add(list2.get(i));
            }
        }

        return result;
    }

}
