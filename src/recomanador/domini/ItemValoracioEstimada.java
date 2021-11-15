package src.recomanador.domini;

public class ItemValoracioEstimada implements Comparable<ItemValoracioEstimada>{
    public float valoracioEstimada;
    public Item item;

    public ItemValoracioEstimada(float valoracioEstimada, Item item){
        this.valoracioEstimada = valoracioEstimada;
        this.item = item;
    }

    @Override
  public int compareTo(ItemValoracioEstimada ive2) {
    if (this.valoracioEstimada > ive2.valoracioEstimada) return -1;
    else if (this.valoracioEstimada < ive2.valoracioEstimada) return 1;
    else return 0;
  }
}

