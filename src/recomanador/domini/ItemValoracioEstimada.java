package src.recomanador.domini;

/**
 * This class joins an item with an estimated rating, and reimplements the
 * the comparator function to be able to sort it.
 * 
 * @author Adrià F.
 */
public class ItemValoracioEstimada implements Comparable<ItemValoracioEstimada>{
    /** estimated rating */
    public float valoracioEstimada;

    /** Item */
    public Item item;

    /**
    *  Constructs a new instance of ItemValoracioEstimada with the given parameters as attributes
    *
    * @param      valoracioEstimada         a value between 0 and 5 representing the estimated rating
    * @param      item                      an instance of the class Item representing the rated item
    */
    public ItemValoracioEstimada(float valoracioEstimada, Item item) {
        this.valoracioEstimada = valoracioEstimada;
        this.item = item;
    }

    /**
    *  Reimplementation of the comparison function to be able to sort an array of ItemValoracioEstimada.
    *
    * @param      ive2                      a different instance of the class ItemValoracioEstimada
    * 
    * @return                               an integer representing which object is bigger
    */
    @Override public int compareTo(ItemValoracioEstimada ive2) {
        if (this.valoracioEstimada > ive2.valoracioEstimada) return -1;
        else if (this.valoracioEstimada < ive2.valoracioEstimada) return 1;
        else return 0;
    }
}

