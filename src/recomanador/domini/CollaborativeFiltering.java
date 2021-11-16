package src.recomanador.domini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Collections;

import src.recomanador.excepcions.RecommendationNotFoundException;
import src.recomanador.excepcions.UserNotFoundException;

class Centroid {
    HashMap<Item,Float> valoracio;
    HashMap<Item,Float> sum;
    HashMap<Item,Integer> quant;
} 

/**
 * This class implements the recomendation algorithms.
 * @author Adrià F.
 */
public class CollaborativeFiltering {
    /*----- CONSTANTS -----*/

    /*----- ATRIBUTS -----*/

    ConjuntItems items;
    ConjuntUsuaris usuaris;
    ConjuntRecomanacions valoracions;

    Centroid[] centroids;

    //idx user, centroid they belong
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
    public CollaborativeFiltering(ConjuntItems items, ConjuntUsuaris usuaris, ConjuntRecomanacions valoracions) {
        this.items = items;
        this.usuaris = usuaris;
        this.valoracions = valoracions;
    }

    /*----- OPERADORS -----*/

    /**
     *  Returns a set of item IDs, sorted by relevance, using Collaborative Filtering for the given user
     *
     * @param      Q            how many items to be recommended
     * @param      user_ID      ID of the user to be recommended
     * @param      K   number of clusters to be generated on k-means
     * 
     * @return     a sorted set the recommended item IDs
     */
    public ArrayList<Item> collaborativeFiltering(int Q, int user_ID, int K) throws UserNotFoundException {
        
        ArrayList<Usuari> usuaris_cluster = usuaris_cluster(user_ID, K); //kmeans
        ArrayList<ItemValoracioEstimada> items_sorted = slope1(user_ID, usuaris_cluster);
        ArrayList<Item> Q_items = new ArrayList<Item>(0);

        for(int i = 0; i < Q; ++i){
            Q_items.add(items_sorted.get(i).item);
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

        //we fill each centroid with random values
        for (int centroid = 0; centroid < K; ++centroid) {
            for(int item = 0; item < items.size(); ++item) {
                float valoracio = rand.nextFloat()*5;
                centroids[centroid].valoracio.put(items.get(item), valoracio);
                centroids[centroid].sum.put(items.get(item), 0f);
                centroids[centroid].quant.put(items.get(item), 0);
            }
        }
        
        boolean has_changed = true;
        
        //calculate the k-means
        while (has_changed){
            has_changed = false;

            //we find the closest centroid of each user
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

                if (closest_centroid.get(idx_usuari) != min_centroid) {
                    has_changed = true;
                    closest_centroid.put(idx_usuari, min_centroid);
                }
            }

            //we recalculate the centroids
            if(has_changed) {
                //firstly, we set the sum and the quant, which we'll use to calculate the averages
                for (int idx_usuari = 0; idx_usuari < usuaris.size(); ++idx_usuari) {
                    int centroid = closest_centroid.get(idx_usuari);
                    ConjuntRecomanacions valoracionsUser = usuaris.get(idx_usuari).getValoracions();

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

                //now we calculate the new averages
                for (int centroid = 0; centroid < K; ++centroid) {
                    for (int idx_item = 0; idx_item < items.size(); ++idx_item) {
                        Item item = items.get(idx_item);

                        float valoracio;
                        if(centroids[centroid].quant.get(item) == 0) valoracio = rand.nextFloat()*5;
                        else valoracio = centroids[centroid].sum.get(item) / centroids[centroid].quant.get(item);
                        
                        centroids[centroid].valoracio.replace(item, valoracio);
                        centroids[centroid].sum.replace(item, 0f);
                        centroids[centroid].quant.replace(item, 0);
                    }
                }
            }


        }        

        //now we return the set of users in the same centroid than user_ID
        ArrayList<Usuari> usuaris_cluster = new ArrayList<Usuari>();
        int centroid = closest_centroid.get(user_ID);
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

        return (float) Math.sqrt(distance);
    }

    /**
     * Returns a set of item IDs, sorted by relevance, using Collaborative Filtering for the given user
     *
     * @param      u    identifier of a user
     *
     * @param      c    index of a centroid
     *
     * @return     the distance between the user and the centroid
     */
    private ArrayList<ItemValoracioEstimada> slope1(int user_ID, ArrayList<Usuari> usuaris_cluster) throws UserNotFoundException{

        Usuari user = usuaris.getUsuari(user_ID);
        ConjuntRecomanacions valoracionsUser = usuaris.get(user_ID).getValoracions();
        ArrayList<ItemValoracioEstimada> items_estimats = new ArrayList<ItemValoracioEstimada>(0);
        
        for (int j_idx = 0; j_idx < items.size(); ++j_idx) {
            Item j_item = items.get(j_idx);
            if (valoracions.existeixValoracio(j_item, user)) continue;
            
            int card_rj = 0;
            float sum1 = 0;

            for (int val_user_idx = 0; val_user_idx < valoracionsUser.size(); ++val_user_idx) {
                Recomanacio valoracio = valoracionsUser.get(val_user_idx);
                //if (!valoracio.recomanacioValorada()) continue;
                Item i_item = valoracio.getItem();

                if(i_item == j_item) continue;

                int card_sij = 0;
                float sum2 = 0;

                for(int user_clust_index = 0; user_clust_index < usuaris_cluster.size(); ++user_clust_index){
                    if (valoracions.existeixValoracio(j_item, usuaris_cluster.get(user_clust_index)) 
                    && valoracions.existeixValoracio(i_item, usuaris_cluster.get(user_clust_index))) {
                        ++card_sij;
                        try {
                        Recomanacio rec1 = valoracions.getRecomanacio(j_item, usuaris_cluster.get(user_clust_index));
                        Recomanacio rec2 = valoracions.getRecomanacio(i_item, usuaris_cluster.get(user_clust_index));
                        sum2 += rec1.getVal() - rec2.getVal();
                        }
                    catch(RecommendationNotFoundException e) {/*mai hauria de passar donat que hem comprovat existeixValoracio*/}
                    }

                    if (card_sij != 0) {
                        ++card_rj;
                        sum1 += sum2/card_sij + valoracio.getVal();
                    }
                }
            }

            if(card_rj != 0){
                float puntuacio_estimada = sum1/card_rj;
                items_estimats.add(new ItemValoracioEstimada(puntuacio_estimada, j_item));
            }
        }
        Collections.sort(items_estimats);
        return items_estimats;
    }
}
    //K-NN
        //volem que ens doni 0-1
            //(string, string) = 1-edit_distance/max(size)
            //(int, int) = 1-(abs((a-b))/(max(atribut)-min(atribut)))
            //(bool, bool) = (bool == bool)
            //(Array, Array) = size(interseccio)/size(unio)
            //(Data, Data) =.....
            // (0..1p, 0..1*p)/(1*p+1*p) = 0..1*(vo-2.5)