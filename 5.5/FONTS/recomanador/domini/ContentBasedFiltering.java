package src.recomanador.domini;

import java.util.ArrayList;

import src.recomanador.excepcions.ItemTypeNotValidException;
import src.recomanador.excepcions.UserNotFoundException;
import java.util.Collections;
import java.util.Random;

/**
 * This class implements the recomendation algorithm content based filtering.
 * 
 * @author Adrià F.
 */
public class ContentBasedFiltering {

    /*----- STATICS -----*/

    /** Contains the only instance of the class **/
    private static ContentBasedFiltering inst;

    /**
     * Returs the only instance of the class, and if it's not created, it creates it.
     *
     * @return     The instance.
     */
    public static ContentBasedFiltering getInstance() {
        if(ContentBasedFiltering.inst == null) inst = new ContentBasedFiltering();
        return inst;
    }

    /**items from which to base the recommendation*/
    private ConjuntItems items;
    /**users from which to base the recommendation*/
    private ConjuntUsuaris usuaris;
    /**ratings from which to base the recommendation*/
    private ConjuntRecomanacions valoracions;

    /** To add stochasticity to the centroids generation */
    private Random rand = new Random();

    /**
     *  Constructs a new empty instance
     */
    private ContentBasedFiltering() {}

    /**
     *  Sets the instance with the given items, users and recommendations
     *
     * @param      items    Set of items
     * @param      usuaris    Set of users
     * @param      valoracions   set of recommendations (which include ratings)
     */
    public void setData(ConjuntItems items, ConjuntUsuaris usuaris, ConjuntRecomanacions valoracions) {
        this.items = items;
        this.usuaris = usuaris;
        this.valoracions = valoracions;
    }

    /**
     *  Returns a set of item IDs, sorted by relevance, using Collaborative Filtering for the given user
     *
     * @param      Q            how many items to be recommended
     * @param      user_ID      ID of the user to be recommended
     * @param      k   number of clusters to be generated on k-means
     * 
     * @return     a sorted set the recommended item IDs
     * 
     * @throws     UserNotFoundException if the id specified is not valid
     */
    public ArrayList<ItemValoracioEstimada> contentBasedFiltering(int Q, int user_ID, int k) throws UserNotFoundException {
        
        ArrayList<ItemValoracioEstimada> items_estimats = new ArrayList<ItemValoracioEstimada>();

        Usuari user = null;

        try {user = usuaris.getUsuari(user_ID);}
        catch (UserNotFoundException e) {}
        
        ConjuntRecomanacions valUser = new ConjuntRecomanacions();
        if (user != null) valUser = valoracions.getValoracions(user.getId());

        if (user == null || valUser.size() == 0) {
            System.out.println("L'usuari no té cap valoració. Generant valoracions aleatories.");
            
            ArrayList<ItemValoracioEstimada> random_items = new ArrayList<ItemValoracioEstimada>();
            for (int i = 0; i < items.size(); ++i) {
                if (!valoracions.existeixRecomanacio(items.get(i).getId(), user_ID)) random_items.add(new ItemValoracioEstimada(rand.nextFloat()*5, items.get(i)));
            }
            Collections.sort(random_items);

            ArrayList<ItemValoracioEstimada> Q_items = new ArrayList<ItemValoracioEstimada>();
            for (int i = 0; i < Q && i < random_items.size(); ++i) Q_items.add(random_items.get(i));
            return Q_items;
        }
        
        //necessari per a que els Q items nomes tinguin un item un cop
        ArrayList<Item> items_afegits = new ArrayList<Item>();

        for (int val_user_idx = 0; val_user_idx < valUser.size(); ++val_user_idx) {
            
            float val = valUser.get(val_user_idx).getVal();

            if (val < 3.5f) continue; //THRESHHOLD. ARBITRARI
            //una millor alternativa podria ser, per exemple, selection algorithm per trobar la valoracio en la posicio floor(0.75*size)

            Item item_val = valUser.get(val_user_idx).getItem();
            ArrayList<ItemValoracioEstimada> Kpropers = new ArrayList<ItemValoracioEstimada>();

            //iterem sobre tots els items no valorats
            for (int idxNV = 0; idxNV < items.size(); ++idxNV) {
                Item iNV = items.get(idxNV);
                if (valoracions.existeixRecomanacio(iNV.getId(), user.getId())) continue;
                
                float similitud = 0;
                try {similitud = items.distanciaItem(iNV, item_val);}
                catch (ItemTypeNotValidException e) {System.out.println(e);}

                Kpropers.add(new ItemValoracioEstimada(similitud*val, iNV));
            }

            Collections.sort(Kpropers);
            for (int k_idx = 0; k_idx < k && k_idx < Kpropers.size(); ++k_idx) {
                items_estimats.add(Kpropers.get(k_idx));
                items_afegits.add(Kpropers.get(k_idx).item);
            }

        }        

        Collections.sort(items_estimats);
        ArrayList<ItemValoracioEstimada> Q_items = new ArrayList<ItemValoracioEstimada>();

        int i = 0;
        while (Q_items.size() < Q && i < items_estimats.size()) {
            if(items_afegits.contains(items_estimats.get(i).item)) {
                Q_items.add(items_estimats.get(i));
                items_afegits.remove(items_estimats.get(i).item);
            }
            ++i;
        }

        if(Q_items.size() == 0) {
            //System.out.println("L'usuari no té cap valoració. Generant valoracions aleatories.");
            
            ArrayList<ItemValoracioEstimada> random_items = new ArrayList<ItemValoracioEstimada>();
            for (int j = 0; j < items.size(); ++j) {
                if (!valoracions.existeixRecomanacio(items.get(j).getId(), user_ID)) random_items.add(new ItemValoracioEstimada(rand.nextFloat()*5, items.get(j)));
            }
            Collections.sort(random_items);

            Q_items = new ArrayList<ItemValoracioEstimada>();
            for (int j = 0; j < Q && j < random_items.size(); ++j) Q_items.add(random_items.get(j));
            return Q_items;
        }

        return Q_items;
    }    
}
