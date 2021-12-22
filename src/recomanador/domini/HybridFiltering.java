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

    /*----- STATICS -----*/

    /** Contains the only instance of the class **/
    private static HybridFiltering inst;

    /**
     * Returs the only instance of the class, and if it's not created, it creates it.
     *
     * @return     The instance.
     */
    public static HybridFiltering getInstance() {
        if(HybridFiltering.inst == null) inst = new HybridFiltering();
        return inst;
    }

    /*----- ATRIBUTS -----*/

    /**items from which to base the recommendation*/
    private ConjuntItems items;
    /**users from which to base the recommendation*/
    private ConjuntUsuaris usuaris;
    /**ratings from which to base the recommendation*/
    private ConjuntRecomanacions valoracions;

    /** lineal calls to Recomanacions of a user(by index) **/
    private ArrayList<ConjuntRecomanacions> crs;

    /**Stores a set of k centroids with their ratings. Used for k-NN*/
    private Centroid[] centroids;
	
    /**For each user ID, stores the centroid they belong to*/
    private HashMap<Integer, Integer> closest_centroid;

    /** Indicates if the centroids are already calculated */
    private Boolean centroidesCalculats = false;

    /** To add stochasticity to the centroids generation */
    private Random rand = new Random();

    /*----- CONSTRUCTORS -----*/

    /**
     *  Constructs a new empty instance
     */
    private HybridFiltering() {}

    /*----- OPERADORS -----*/

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
        centroidesCalculats = false;
    }

    /**
     *  Returns a set of item IDs, sorted by relevance, using Hybrid Filtering
     *
     * @param      Q            how many items to be recommended
     * @param      user_ID      ID of the user to be recommended
     * @param      K   number of clusters to be generated on k-means
     * 
     * @return     a sorted set the recommended item IDs with their estimated ratings
     * 
     * @throws     UserNotFoundException if the id specified is not valid
     */
    public ArrayList<ItemValoracioEstimada> hybridFiltering(int Q, int user_ID, int K) throws UserNotFoundException {

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
            for (int i = 0; i < Q &&  i < random_items.size(); ++i) Q_items.add(random_items.get(i));
            return Q_items;
        }
        
        //we find the cluster of users closest to our user
        ArrayList<Usuari> usuaris_cluster = usuaris_cluster(user_ID, K); //kmeans
        System.out.println("L'usuari " + user_ID + " pertany a un cluster que conté " + usuaris_cluster.size() + " dels " + usuaris.size() + " usuaris." );

        //we find the items the users in the cluster rated well
        
        ConjuntItems items_cluster = null;
        try {items_cluster = new ConjuntItems();}
        catch (ItemStaticValuesNotInitializedException e) {
            System.out.println(e);
        }

        for (int i = 0; i < usuaris_cluster.size(); ++i) {
            ConjuntRecomanacions vals_users = valoracions.getValoracions(usuaris_cluster.get(i).getId());
            for (int j = 0; j < vals_users.size(); ++j) {
                Recomanacio val = vals_users.get(j);
                if (val.getVal() >= 4.5) {
                    if (!items_cluster.existeixItem(val.getItem().getId())) items_cluster.add(val.getItem());
                }
            }
        }

        System.out.println("Els usuaris del cluster els hi agraden " + items_cluster.size() + " dels " + items.size() + " items." );

        //we execute content-based filtering on this set of items
        System.out.println("Executant k-NN" );
        System.out.println();
        ArrayList<ItemValoracioEstimada> items_estimats = new ArrayList<ItemValoracioEstimada>();

        //Usuari user = usuaris.getUsuari(user_ID);
        //ConjuntRecomanacions valUser = valoracions.getValoracions(user.getId());
        
        //necessari per a que els Q items nomes tinguin un item un cop
        ArrayList<Item> items_afegits = new ArrayList<Item>();

        for (int val_user_idx = 0; val_user_idx < valUser.size(); ++val_user_idx) {
            
            float val = valUser.get(val_user_idx).getVal();
            if (val < 3.5f) continue; //THRESHHOLD. ARBITRARI
            //una millor alternativa podria ser, per exemple, selection algorithm per trobar la valoracio en la posicio floor(0.75*size)

            Item item_val = valUser.get(val_user_idx).getItem();
            ArrayList<ItemValoracioEstimada> Kpropers = new ArrayList<ItemValoracioEstimada>();

            //iterem sobre tots els items no valorats i no recomanats previament!!
            for (int idxNV = 0; idxNV < items_cluster.size(); ++idxNV) {
                Item iNV = items_cluster.get(idxNV);
                if (valoracions.existeixRecomanacio(iNV.getId(), user.getId())) continue;
                
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

    /**
     *  Executes k-means and returns the set of IDs of the users in the same cluster than user_ID
     *
     * @param      user_ID      ID of the user whose cluster will be returned
     * @param      K            The value of K
     * 
     * @return     users of the cluster
     * 
     * @throws     UserNotFoundException if the id specified is not valid
     */
    private ArrayList<Usuari> usuaris_cluster(int user_ID, int K) throws UserNotFoundException {
        if(!centroidesCalculats) {
            System.out.println("Executant k-Means");
            centroidesCalculats = true;
            centroids = new Centroid[K];
            closest_centroid = new HashMap<Integer, Integer>(usuaris.size());

            //per fer les cirdes lineals
            crs = new ArrayList<ConjuntRecomanacions>();
            for (int idx_usuari = 0; idx_usuari < usuaris.size(); ++idx_usuari) {
                crs.add(valoracions.getValoracions(usuaris.get(idx_usuari).getId()));
            }

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
            //int count = 0;
            while (has_changed){
                //System.out.println(count + " ");
                //count++;
                has_changed = false;
                //System.out.println("A");
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
                //System.out.println("B");
                if(has_changed) {
                    for (int idx_usuari = 0; idx_usuari < usuaris.size(); ++idx_usuari) {
                        int centroid = closest_centroid.get(idx_usuari);
                        
                        ConjuntRecomanacions valoracionsUser = crs.get(idx_usuari);//l'array esta ordenat com usuaris

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
                    //System.out.println("C");
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
        }        

        ArrayList<Usuari> usuaris_cluster = new ArrayList<Usuari>();
        int user_ID_pos = -1;
        for(int i = 0; i < usuaris.size() && user_ID_pos == -1; i++) {
            if(usuaris.get(i).getId() == user_ID) user_ID_pos = i;
        }
        if(user_ID_pos == -1) throw new UserNotFoundException(user_ID);
        int centroid = closest_centroid.get(user_ID_pos);
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
     * @param      idx_usuari    identifier of a user
     *
     * @param      centroid    index of a centroid
     *
     * @return     the distance between the user and the centroid
     */
    private float distance(int idx_usuari, int centroid) {
        //System.out.println("calculating the distance between " + idx_usuari + " and centroid " + centroid);
        
        float distance = 0;

        ConjuntRecomanacions valoracionsUser = crs.get(idx_usuari);

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
