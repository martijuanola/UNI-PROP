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
            for (int i = 0; i < items.size(); ++i) random_items.add(new ItemValoracioEstimada(rand.nextFloat(5.0f), items.get(i)));
            Collections.sort(random_items);

            ArrayList<ItemValoracioEstimada> Q_items = new ArrayList<ItemValoracioEstimada>();
            for (int i = 0; i < Q; ++i) Q_items.add(random_items.get(i));
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

        return Q_items;
    }

    
    
}

/* IMPLEMENTACIO DIFERENT ANTIGA. POTSER UTIL PER HÍBRID?
public ArrayList<ItemValoracioEstimada> contentBasedFiltering(int Q, int user_ID, int K) throws UserNotFoundException {
       
        ArrayList<ItemValoracioEstimada> items_estimats = new ArrayList<ItemValoracioEstimada>();

        Usuari user = usuaris.getUsuari(user_ID);
        ConjuntRecomanacions valUser = user.getValoracions();

        for (int idxNV = 0; idxNV < items.size(); ++idxNV) {
            Item iNV = items.get(idxNV);
            if (valoracions.existeixValoracio(iNV, user)) continue;
            float puntuacio_estimada = 0f;
            for (int idxV = 0; idxV < valUser.size(); ++idxV) {
                Recomanacio val = valoracions.get(idxV);
                float similitud = (float)0.0;
                try {
                    similitud = ConjuntItems.distanciaItem(iNV, val.getItem());
                } catch (ItemTypeNotValidException e) {
                    e.printStackTrace();
                }
                
                //Així, un ítem exactament igual que un que ha valorat com a 0 restaria 2.5 a la puntuació
                //i viceversa. Això està ponderat per la similitud i per la valoració.
                //Una valoració "meh" de 2.5 no afectaria a la puntuació estimada
                puntuacio_estimada += similitud*(val.getVal()-2.5f);
            }
            items_estimats.add(new ItemValoracioEstimada(puntuacio_estimada, iNV));
        }
        Collections.sort(items_estimats);
        
        ArrayList<ItemValoracioEstimada> Q_items = new ArrayList<ItemValoracioEstimada>();

        for(int i = 0; i < Q; ++i){
            Q_items.add(items_estimats.get(i));
        }

        return Q_items;
    }
    
}
*/
