package fr.diginamic.service;

import fr.diginamic.entities.Additif;
import fr.diginamic.entities.Allergene;
import fr.diginamic.entities.Categorie;
import fr.diginamic.entities.Ingredient;
import fr.diginamic.entities.Marque;
import fr.diginamic.entities.Nutriscore;
import fr.diginamic.entities.Produit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;

import java.util.*;

public class Mapper {
    private static final Logger LOG = Logging.LOG;

    public static final int CATEGORIE = 0;
    public static final int MARQUE = 1;
    public static final int PRODUIT = 2;
    public static final int NUTRITIONGRADEFR = 3;
    public static final int INGREDIENTS = 4;
    public static final int ENERGIE100G = 5;
    public static final int GRAISSE100G = 6;
    public static final int SUCRES100G = 7;
    public static final int FIBRES100G = 8;
    public static final int PROTEINES100G = 9;
    public static final int SEL100G = 10;
    public static final int VITA100G = 11;
    public static final int VITD100G = 12;
    public static final int VITE100G = 13;
    public static final int VITK100G = 14;
    public static final int VITC100G = 15;
    public static final int VITB1100G = 16;
    public static final int VITB2100G = 17;
    public static final int VITPP100G = 18;
    public static final int VITB6100G = 19;
    public static final int VITB9100G = 20;
    public static final int VITB12100G = 21;
    public static final int CALCIUM100G = 22;
    public static final int MAGNESIUM100G = 23;
    public static final int IRON100G = 24;
    public static final int FER100G = 25;
    public static final int BETACAROTENE100G = 26;
    public static final int PRESENCEHUILEPALME = 27;
    public static final int ALLERGENES = 28;
    public static final int ADDITIFS = 29;

    static {
    }

    /**
     *
     */
    public static void insertToDataBase(ArrayList<String[]> rows) {

        int iteration = 0;

        // Initialisation de l'iterator
        Iterator<String[]> iter = rows.iterator();

        // HashMap de chaque colonne
        HashMap<Integer, String> categories = new HashMap<>();
        HashMap<Integer, String> nutriscores = new HashMap<>();
        HashMap<Integer, String> marques = new HashMap<>();
        HashMap<Integer, String> produits = new HashMap<>();
        HashMap<Integer, String> ingredients = new HashMap<>();
        HashMap<Integer, String> allergenes = new HashMap<>();
        HashMap<Integer, String> additifs = new HashMap<>();


        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("IBOOF-JPA");
             EntityManager em = emf.createEntityManager();
        ) {
//         Debut de la persistence avec transaction
            em.getTransaction().begin();

            while (iter.hasNext()) {
                iteration++;
                String[] line = iter.next();

                // On fait appel aux Business Objects dans les méthodes suivantes pour
                // créer les instanciations qui vont permettre de rentrer les données dans la BD

                // AJOUT DE LA CATEGORIE
                Categorie categorie = creationInstanceCategorie(iteration, categories, line[CATEGORIE]);
                em.persist(categorie);

                // AJOUT DE LA MARQUE
                Marque marque = creationInstanceMarque(iteration, marques, line[MARQUE]);
                em.persist(marque);

                // AJOUT DU NUTRISCORE
                Nutriscore nutriscore = creationInstanceNutriscore(iteration, nutriscores, line[NUTRITIONGRADEFR]);
                em.persist(nutriscore);


                // AJOUT DE LA LISTE INGREDIENT
                Set<Ingredient> listSetIngredients = creationInstanceIngredient(iteration, ingredients, line[INGREDIENTS].split(","));
                listSetIngredients.forEach(em::persist);

                // AJOUT DES ALLERGENES
                Set<Allergene> allergeneSet = creationInstanceAllergene(iteration, allergenes, line[ALLERGENES].split(","));
                allergeneSet.forEach(em::persist);

                // AJOUT DES ADDITIFS
                Set<Additif> additifSet = creationInstanceAdditif(iteration, additifs, line[ADDITIFS].split(","));
                additifSet.forEach(em::persist);

                // AJOUT DES PRODUITS AVEC LEUR JOINTURE
                Produit produit = creationInstanceProduit(iteration, produits, line[PRODUIT]);

                produit.setCategorie(categorie);

                categorie.getProduits().add(produit);

                produit.setMarque(marque);

                marque.getProduits().add(produit);

                produit.setNutriscore(nutriscore);

                nutriscore.getProduits().add(produit);

                produit.setIngredients(listSetIngredients);
                listSetIngredients.forEach(ingredient -> ingredient.getProduits().add(produit));

                produit.setAllergenes(allergeneSet);
                allergeneSet.forEach(allergene -> allergene.getProduits().add(produit));

                produit.setAdditifs(additifSet);
                additifSet.forEach(additif -> additif.getProduits().add(produit));

                // PERSISTANCE DES DONNEES DE LA TABLE PRODUIT
                em.persist(produit);
            }
            em.getTransaction().commit();
            // Fin de la persistence avec transaction

        } catch (
                IllegalStateException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    /**
     * @param iteration       on utilise l'iteration comme une clé pour préserver
     *                        l'intégrité des données traîtées à chaque iteration
     * @param hashMapAdditifs Le hashMap sert à conserver les pairs clés-valeurs
     *                        qui vont servir à créer les instances d'objets
     * @return La méthode retourne l'instance d'objet Additif
     */
    public static Set<Additif> creationInstanceAdditif(int iteration, HashMap<Integer, String> hashMapAdditifs, String[] colContent) {
        Set<Additif> additifSet = new HashSet<>();
        Additif additif = new Additif();
        for (String additi : colContent) {
            if (!hashMapAdditifs.containsValue(additif)) {
                hashMapAdditifs.put(iteration, additi);
                additif.setNom_additif(hashMapAdditifs.get(iteration));
                additifSet.add(additif);
            }
        }
        return additifSet;
    }

    /**
     * @param iteration
     * @param allergenes
     * @param colContent
     * @return
     */
    public static Set<Allergene> creationInstanceAllergene(int iteration, HashMap<Integer, String> allergenes, String[] colContent) {
        Set<Allergene> allergeneSet = new HashSet<>();
        Allergene allergene = new Allergene();
        for (String allerg : colContent) {
            if (!allergenes.containsValue(allerg)) {
                allergenes.put(iteration, allerg);
                allergene.setNom_allergene(allergenes.get(iteration));
                allergeneSet.add(allergene);
            }
        }
        return allergeneSet;
    }

    /**
     * @param iteration
     * @param ingredients
     * @param lineSplit
     * @return
     */
    public static Set<Ingredient> creationInstanceIngredient(int iteration, HashMap<Integer, String> ingredients, String[] lineSplit) {
        Set<Ingredient> setOfIngredients = new HashSet<>();
        Ingredient ingredient = new Ingredient();
        for (String ingdt : lineSplit) {
            if (!ingredients.containsValue(ingdt)) {
                ingredients.put(iteration, ingdt);
                ingredient.setNom_ingredient(ingredients.get(iteration));
                setOfIngredients.add(ingredient);
            } else {
                ingredient.setNom_ingredient(ingdt);
                setOfIngredients.add(ingredient);
            }
        }
        return setOfIngredients;
    }

    /**
     * @param iteration
     * @param hashMapNutriscores
     * @param colContent
     * @return
     */
    public static Nutriscore creationInstanceNutriscore(int iteration, HashMap<Integer, String> hashMapNutriscores, String colContent) {
        Nutriscore nutriscore = new Nutriscore();
        String candidate;
        if (!hashMapNutriscores.containsValue(colContent)) {
            hashMapNutriscores.put(iteration, colContent);
            candidate = hashMapNutriscores.get(iteration);
            nutriscore.setValeurScore(candidate.toCharArray()[0]);
        }
        return nutriscore;
    }

    /**
     * @param iteration
     * @param hashMapMarques
     * @param colContent
     * @return
     */
    public static Marque creationInstanceMarque(int iteration, HashMap<Integer, String> hashMapMarques, String colContent) {
        Marque marque = new Marque();
        String candidate;
        if (!hashMapMarques.containsValue(colContent)) {
            hashMapMarques.put(iteration, colContent);
            candidate = hashMapMarques.get(iteration);
            marque.setNom_marque(candidate);
        }
        return marque;
    }

    /**
     * @param iteration
     * @param hashMapProduits
     * @param colContent
     * @return
     */
    public static Produit creationInstanceProduit(int iteration, HashMap<Integer, String> hashMapProduits, String colContent) {
        Produit produit = new Produit();
        String candidate;
        if (!hashMapProduits.containsValue(colContent)) {
            hashMapProduits.put(iteration, colContent);
            candidate = hashMapProduits.get(iteration);
            produit.setNom_produit(candidate);
        } else {
            candidate = colContent;
            produit.setNom_produit(candidate);
        }
        return produit;
    }

    /**
     * @param iteration
     * @param hashMapCategorie
     * @param colContent
     * @return
     */
    public static Categorie creationInstanceCategorie(int iteration, HashMap<Integer, String> hashMapCategorie, String colContent) {
        Categorie categorie = new Categorie();
        String candidate;
        if (!hashMapCategorie.containsValue(colContent)) {
            hashMapCategorie.put(iteration, colContent);
            candidate = hashMapCategorie.get(iteration);
            categorie.setNom_categorie(candidate);
        }
        return categorie;
    }
}
