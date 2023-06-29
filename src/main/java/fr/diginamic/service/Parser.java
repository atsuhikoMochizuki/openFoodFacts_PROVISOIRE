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

public class Parser {
    private static final Logger LOG = Logging.LOG;

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
                Categorie categorie = creationInstanceCategorie(iteration, categories, line[0]);
                em.persist(categorie);

                // AJOUT DE LA MARQUE
                Marque marque = creationInstanceMarque(iteration, marques, line[1]);
                em.persist(marque);

                // AJOUT DU NUTRISCORE
                Nutriscore nutriscore = creationInstanceNutriscore(iteration, nutriscores, line[3]);
                em.persist(nutriscore);

                // AJOUT DE LA LISTE INGREDIENT
                Set<Ingredient> listSetIngredients = creationInstanceIngredient(iteration, ingredients, line[4].split(","));
                listSetIngredients.forEach(em::persist);

                // AJOUT DES ALLERGENES
                Set<Allergene> listSetAllergenes = creationInstanceAllergene(iteration, allergenes, line[28].split(","));
                listSetAllergenes.forEach(em::persist);

                // AJOUT DES ADDITIFS
                Set<Additif> listSetAdditif = creationInstanceAdditif(iteration, additifs, line[29].split(","));
                listSetAdditif.forEach(em::persist);

                // AJOUT DES PRODUITS AVEC LEUR JOINTURE
                Produit produit = creationInstanceProduit(iteration, produits, line[2]);
                produit.setCategorie(categorie);
                categorie.getProduits().add(produit);
                produit.setMarque(marque);
                marque.getProduits().add(produit);
                produit.setNutriscore(nutriscore);
                nutriscore.getProduits().add(produit);

                produit.setIngredients(listSetIngredients);
                listSetIngredients.forEach(ingredient -> ingredient.getProduits().add(produit));

                produit.setAllergenes(listSetAllergenes);
                listSetAllergenes.forEach(allergene -> allergene.getProduits().add(produit));

                produit.setAdditifs(listSetAdditif);
                listSetAdditif.forEach(additif -> additif.getProduits().add(produit));

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
            if (!hashMapAdditifs.containsKey(iteration)) {
                hashMapAdditifs.put(iteration, additi);
                additif.setNom_additif(hashMapAdditifs.get(iteration));
            } else {
                additif.setNom_additif(additi);
            }

        }
        additifSet.add(additif);
        return additifSet;

    }

    /**
     * @param iteration
     * @param allergenes
     * @param colContent
     * @return
     */
    public static Set<Allergene> creationInstanceAllergene(int iteration, HashMap<Integer, String> allergenes, String[] colContent) {
        Set<Allergene> listAllergenes = new HashSet<>();
        Allergene allergene = new Allergene();
        for (String allerg : colContent) {
            if (!allergenes.containsValue(allerg)) {
                allergenes.put(iteration, allerg);
                allergene.setNom_allergene(allergenes.get(iteration));
                listAllergenes.add(allergene);
            } else {
                allergene.setNom_allergene(allerg);
                listAllergenes.add(allergene);
            }
        }
        return listAllergenes;
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
        if (!hashMapNutriscores.containsValue(colContent)) {
            hashMapNutriscores.put(iteration, colContent);
            nutriscore.setValeurScore(hashMapNutriscores.get(iteration).toCharArray()[0]);
        } else {
            nutriscore.setValeurScore(colContent.toCharArray()[0]);
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
        if (!hashMapMarques.containsValue(colContent)) {
            hashMapMarques.put(iteration, colContent);
            marque.setNom_marque(hashMapMarques.get(iteration));
        } else {
            marque.setNom_marque(colContent);
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
        if (!hashMapProduits.containsValue(colContent)) {
            hashMapProduits.put(iteration, colContent);
            produit.setNom_produit(hashMapProduits.get(iteration));
        } else {
            produit.setNom_produit(colContent);
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
        if (!hashMapCategorie.containsValue(colContent)) {
            hashMapCategorie.put(iteration, colContent);
            categorie.setNom_categorie(hashMapCategorie.get(iteration));
        } else {
            categorie.setNom_categorie(colContent);
        }
        return categorie;
    }
}