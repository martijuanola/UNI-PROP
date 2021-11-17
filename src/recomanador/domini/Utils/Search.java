package src.recomanador.domini.Utils;

import java.util.ArrayList;

import src.recomanador.domini.Item;

public class Search {

    public static <T extends Comparable<T>> int binarySearch(ArrayList<T> list, T value, int lo, int hi) {
	if (lo < hi) {
	    int mid = (lo / 2) + (hi / 2);
	    int cmp = list.get(mid).compareTo(value);
	    if (cmp < 0) return binarySearch(list, value, lo, mid - 1);
	    if (cmp > 0) return binarySearch(list, value, mid + 1, hi);
	    return mid;
	} // if
	return -1;
    } // binarySearch

    public static <T extends Comparable<T>> int binarySearchItem(ArrayList<Item> list, int id, int lo, int hi) {
        if (lo < hi) {
            int mid = (lo / 2) + (hi / 2);
            //Agafes l'id. Amb les precondicions de que l'id ha de ser correcte, no hauria de passar res
            int cmp = list.get(mid).getId();
            if (cmp < id) return binarySearchItem(list, id, lo, mid - 1);
            if (cmp > id) return binarySearchItem(list, id, mid + 1, hi);
            return mid;
        }
        return -1;
	}

    public static <T extends Comparable<T>> int linearSearch(T[] array, T value, int lo, int hi) {
	for (int i = lo; i <= hi; i++) {
	    if (array[i].compareTo(value) == 0) return i;
	} // for
	return -1;
    } // linearSearch

    public static <T> void swap(T[] array, int lhs, int rhs) {
	T tmp = array[lhs];
	array[lhs] = array[rhs];
	array[rhs] = tmp;
    } // swap

    public static <T extends Comparable<T>> int partition(T[] array, int piv, int lo, int hi) {
	T val = array[piv];
	while (lo < hi) {
	    while (array[lo].compareTo(val) < 0) lo++;
	    while (array[hi].compareTo(val) > 0) hi--;
	    swap(array, lo, hi);
	} // while
	return lo;
    } // partition

	private static int getClosest(int val1, int val2, int target) {
		if (target - val1 >= val2 - target) return val2;
		else return val1;
	}

    public static int findClosest(ArrayList<Item> list, int id) {
        int n = list.size();
 
        // Corner cases
        if (id <= list.get(0).getId())
            return 0;
        if (id >= list.get(n - 1).getId())
            return n - 1;
 
        // Doing binary search
        int i = 0, j = n, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;
 
            if (list.get(mid).getId() == id)
                return list.get(mid).getId();
 
            /* If target is less than array element,
               then search in left */
            if (id < list.get(mid).getId()) {
        
                // If target is greater than previous
                // to mid, return closest of two
                if (mid > 0 && id > list.get(mid - 1).getId())
                    return getClosest(list.get(mid - 1).getId(),
                                  list.get(mid).getId(), id);
                 
                /* Repeat for left half */
                j = mid;             
            }
 
            // If target is greater than mid
            else {
                if (mid < n-1 && id < list.get(mid + 1).getId())
                    return getClosest(list.get(mid).getId(),
					list.get(mid + 1).getId(), id);               
                i = mid + 1; // update i
            }
        }
 
        // Only single element left after search
        return mid;
    }

}