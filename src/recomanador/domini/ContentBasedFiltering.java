package src.recomanador.domini;

import java.util.ArrayList;
import src.recomanador.excepcions.UserNotFoundException;
import java.util.Collections;

/**
 * This class implements the recomendation algorithm content based filtering.
 * We have thought of some variations of k-NN that we think might make the algorithm better,
 * like subtracting from the estimated rating of an item if it resembles one the user disliked.
 * 
 * @author Adrià F.
 */
public class ContentBasedFiltering {

    ConjuntItems items;
    ConjuntUsuaris usuaris;
    ConjuntRecomanacions valoracions;

    /**
     *  Constructs a new instance with the given items, users and recommendations
     *
     * @param      items    Set of items
     * @param      usuaris    Set of users
     * @param      valoracions   set of recommendations (which include ratings)
     */
    public ContentBasedFiltering(ConjuntItems items, ConjuntUsuaris usuaris, ConjuntRecomanacions valoracions) {
        this.items = items;
        this.usuaris = usuaris;
        this.valoracions = valoracions;
    }

    /**
     *  Returns a set of item IDs, sorted by relevance, using Collaborative Filtering for the given user
     *
     * @param      Q            how many items to be recommended
     * @param      user_ID      ID of the user to be recommended
     * @param      K   number of clusters to be generated on k-means
     * 
     * @return     a sorted set the recommended item IDs
     */
    public ArrayList<Item> contentBasedFiltering(int Q, int user_ID) throws UserNotFoundException {
        
        ArrayList<ItemValoracioEstimada> items_estimats = new ArrayList<ItemValoracioEstimada>(0);

        Usuari user = usuaris.getUsuari(user_ID);
        ConjuntRecomanacions valUser = user.getValoracions();

        for (int idxNV = 0; idxNV < items.size(); ++idxNV) {
            Item iNV = items.get(idxNV);
            if (valoracions.existeixValoracio(iNV, user)) continue;
            float puntuacio_estimada = 0f;
            for (int idxV = 0; idxV < valUser.size(); ++idxV) {
                Recomanacio val = valoracions.get(idxV);
                float similitud = 0.5f;//ConjuntItems.similitud(iNV, val.getItem());
                
                //Així, un ítem exactament igual que un que ha valorat com a 0 restaria 2.5 a la puntuació
                //i viceversa. Això està ponderat per la similitud i per la valoració.
                //Una valoració "meh" de 2.5 no afectaria a la puntuació estimada
                puntuacio_estimada += similitud*(val.getVal()-2.5f);
            }
            items_estimats.add(new ItemValoracioEstimada(puntuacio_estimada, iNV));
        }
        Collections.sort(items_estimats);
        
        ArrayList<Item> Q_items = new ArrayList<Item>(0);

        for(int i = 0; i < Q; ++i){
            Q_items.add(items_estimats.get(i).item);
        }

        return Q_items;
    }
    
}
