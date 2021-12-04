package src.recomanador.domini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Collections;

import src.recomanador.excepcions.ItemStaticValuesNotInitializedException;
import src.recomanador.excepcions.ItemTypeNotValidException;
import src.recomanador.excepcions.UserNotFoundException;
import java.util.Collections;

/**
 * This class implements the recomendation algorithm using a combination of Content-Based filtering and Collaborative Filtering.
 * For details about its implementation, please check the "Algorithms and Data-Structures" sections of the documentation 
 * 
 * @author Adrià F.
 */
public class HybridFiltering {
    /*----- CONSTANTS -----*/

    /*----- ATRIBUTS -----*/

    /**items from which to base the recommendation*/
    ConjuntItems items;
    /**users from which to base the recommendation*/
    ConjuntUsuaris usuaris;
    /**ratings from which to base the recommendation*/
    ConjuntRecomanacions valoracions;

    /**Stores a set of k centroids with their ratings. Used for k-NN*/
    Centroid[] centroids;
	
    /**For each user ID, stores the centroid they belong to*/
    HashMap<Integer, Integer> closest_centroid;

    Random rand = new Random();

    /*----- CONSTRUCTORS -----*/

    /**
     *  Constructs a new instance with the given items, users and recommendations
     *
     * @param      items    Set of items
     * @param      usuaris    Set of users
     * @param      valoracions   set of recommendations (which include ratings)
     */
    public HybridFiltering(ConjuntItems items, ConjuntUsuaris usuaris, ConjuntRecomanacions valoracions) {
        this.items = items;
        this.usuaris = usuaris;
        this.valoracions = valoracions;
    }

    /*----- OPERADORS -----*/

    /**
     *  Returns a set of item IDs, sorted by relevance, using Hybrid Filtering
     *
     * @param      Q            how many items to be recommended
     * @param      user_ID      ID of the user to be recommended
     * @param      K   number of clusters to be generated on k-means
     * 
     * @return     a sorted set the recommended item IDs with their estimated ratings
     */
    public ArrayList<ItemValoracioEstimada> hybridFiltering(int Q, int user_ID, int K) throws UserNotFoundException {
        
        //we find the cluster of users closest to our user
        System.out.println("Executant k-means");
        ArrayList<Usuari> usuaris_cluster = usuaris_cluster(user_ID, K); //kmeans
        System.out.println("L'usuari " + user_ID + " pertany a un cluster que conté " + usuaris_cluster.size() + " dels " + usuaris.size() + " usuaris." );

        //we find the items the users in the cluster rated well
        
        ConjuntItems items_cluster = null;
        try {items_cluster = new ConjuntItems();}
        catch (ItemStaticValuesNotInitializedException e) {
            System.out.println(e);
        }

        for (int i = 0; i < usuaris_cluster.size(); ++i) {
            ConjuntRecomanacions vals_users = usuaris_cluster.get(i).getValoracions();
            for (int j = 0; j < vals_users.size(); ++j) {
                Recomanacio val = vals_users.get(j);
                if (val.getVal() >= 3.5) {
                    if (!items_cluster.existeixItem(val.getItem().getId())) items_cluster.add(val.getItem());
                }
            }
        }

        System.out.println("Els usuaris del cluster els hi agraden " + items_cluster.size() + " dels " + items.size() + " items." );

        //we execute content-based filtering on this set of items
        System.out.println("Executant k-NN" );
        System.out.println();
        ArrayList<ItemValoracioEstimada> items_estimats = new ArrayList<ItemValoracioEstimada>(0);

        Usuari user = usuaris.getUsuari(user_ID);
        ConjuntRecomanacions valUser = user.getValoracions();
        
        //necessari per a que els Q items nomes tinguin un item un cop
        ArrayList<Item> items_afegits = new ArrayList<Item>();

        for (int val_user_idx = 0; val_user_idx < valUser.size(); ++val_user_idx) {
            
            float val = valUser.get(val_user_idx).getVal();
            if (val < 3.5f) continue; //THRESHHOLD. ARBITRARI
            //una millor alternativa podria ser, per exemple, selection algorithm per trobar la valoracio en la posicio floor(0.75*size)

            Item item_val = valUser.get(val_user_idx).getItem();
            ArrayList<ItemValoracioEstimada> Kpropers = new ArrayList<ItemValoracioEstimada>(0);

            //iterem sobre tots els items no valorats
            for (int idxNV = 0; idxNV < items.size(); ++idxNV) {
                Item iNV = items_cluster.get(idxNV);
                if (valoracions.existeixValoracio(iNV, user)) continue;
                
                float similitud = 0;
                try {similitud = items_cluster.distanciaItem(iNV, item_val);}
                catch (ItemTypeNotValidException e) {System.out.println(e);}

                Kpropers.add(new ItemValoracioEstimada(similitud*val, iNV));
            }

            Collections.sort(Kpropers);
            for (int k_idx = 0; k_idx < K && k_idx < Kpropers.size(); ++k_idx) {
                items_estimats.add(Kpropers.get(k_idx));
                items_afegits.add(Kpropers.get(k_idx).item);
            }

        }        

        Collections.sort(items_estimats);
        ArrayList<ItemValoracioEstimada> Q_items = new ArrayList<ItemValoracioEstimada>(0);

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

    /**
     *  Executes k-means and returns the set of IDs of the users in the same cluster than user_ID
     *
     * @param      user_ID      ID of the user whose cluster will be returned
     * 
     * @return     users of the cluster
     */
    private ArrayList<Usuari> usuaris_cluster(int user_ID, int K) {

        centroids = new Centroid[K];
        closest_centroid = new HashMap<Integer, Integer>(usuaris.size());

        for (int centroid = 0; centroid < K; ++centroid) {
			centroids[centroid] = new Centroid();
			centroids[centroid].valoracio = new HashMap<Item,Float>();
			centroids[centroid].sum = new HashMap<Item,Float>();
			centroids[centroid].quant = new HashMap<Item,Integer>();
		
            for(int item = 0; item < items.size(); ++item) {
                float valoracio = rand.nextFloat()*5;
                centroids[centroid].valoracio.put(items.get(item), valoracio);
                centroids[centroid].sum.put(items.get(item), 0f);
                centroids[centroid].quant.put(items.get(item), 0);
            }

        }
        
        boolean has_changed = true;
        
        while (has_changed){
            has_changed = false;

            for(int idx_usuari = 0; idx_usuari < usuaris.size(); ++idx_usuari) {                

                int min_centroid = 0;
                float min_distance = distance(idx_usuari, 0);
               
                float dist_aux;
                for(int centroid = 1; centroid < K; ++centroid) {
                    
                    dist_aux = distance(idx_usuari,centroid);
                    
                    if (dist_aux < min_distance) {
                        min_distance = dist_aux;
                        min_centroid = centroid;
                    }
                }

                if (!closest_centroid.containsKey(idx_usuari) || closest_centroid.get(idx_usuari) != min_centroid) {
                    has_changed = true;
                    closest_centroid.put(idx_usuari, min_centroid);
                }
            }

            if(has_changed) {
                for (int idx_usuari = 0; idx_usuari < usuaris.size(); ++idx_usuari) {
                    int centroid = closest_centroid.get(idx_usuari);
                    
                    ConjuntRecomanacions valoracionsUser = usuaris.get(idx_usuari).getValoracions();
                    //~ System.out.println("loaded " + valoracionsUser.size() + " ratings");

                    for (int idx_rec = 0; idx_rec < valoracionsUser.size(); ++idx_rec) {
                        Recomanacio rec = valoracionsUser.get(idx_rec);
                        Item item = rec.getItem();
                        if (rec.recomanacioValorada()) {
                            float new_sum = centroids[centroid].sum.get(item) + rec.getVal();
                            int new_quant = centroids[centroid].quant.get(item) + 1;

                            centroids[centroid].sum.replace(item, new_sum);
                            centroids[centroid].quant.replace(item, new_quant);
                        }
                    }

                }

                for (int centroid = 0; centroid < K; ++centroid) {
                    for (int idx_item = 0; idx_item < items.size(); ++idx_item) {

                        //~ System.out.println("Loading item " + idx_item);
                        Item item = items.get(idx_item);

                        float valoracio;
                        if(centroids[centroid].quant.get(item) == 0) valoracio = rand.nextFloat()*5;
                        else valoracio = centroids[centroid].sum.get(item) / centroids[centroid].quant.get(item);

                        //~ System.out.println("New centroid rating is " + valoracio);
                        
                        centroids[centroid].valoracio.replace(item, valoracio);
                        centroids[centroid].sum.replace(item, 0f);
                        centroids[centroid].quant.replace(item, 0);
                    }
                }
            }


        }        

        ArrayList<Usuari> usuaris_cluster = new ArrayList<Usuari>();
        int centroid = closest_centroid.get(usuaris.cercaBinaria(user_ID));
        for(int idx_usuari = 0; idx_usuari < usuaris.size(); ++idx_usuari) {
            if (closest_centroid.get(idx_usuari) == centroid) {
                usuaris_cluster.add(usuaris.get(idx_usuari));
            }
            
        }
        
        return usuaris_cluster;
    }

    /**
     * Returns the distance between a user and a centroid.
     *
     * @param      u    identifier of a user
     *
     * @param      c    index of a centroid
     *
     * @return     the distance between the user and the centroid
     */
    private float distance(int idx_usuari, int centroid) {
        //System.out.println("calculating the distance between " + idx_usuari + " and centroid " + centroid);
        
        float distance = 0;

        ConjuntRecomanacions valoracionsUser = usuaris.get(idx_usuari).getValoracions();

        for (int idx_rec = 0; idx_rec < valoracionsUser.size(); ++idx_rec) {
            Recomanacio rec = valoracionsUser.get(idx_rec);
            Item item = rec.getItem();
            if (rec.recomanacioValorada()) {
                float distance_add = rec.getVal()-centroids[centroid].valoracio.get(item); 
                distance += distance_add*distance_add;
            }
        }
		
		//~ System.out.println("distance = " + Math.sqrt(distance));
		
        return (float) Math.sqrt(distance);
    }

}