package fr.diginamic.service;

import fr.diginamic.entities.*;
import fr.diginamic.mochizukiTools.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Parser {
    private static final Logger LOG = Logging.LOG;
    private static ArrayList<String[]> rows;

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
                Categorie categorie = creationInstanceCategorie(iteration, categories, line[0]);
                if (categorie != null) {
                    em.persist(categorie);
                }
                Marque marque = creationInstanceMarque(iteration, marques, line[1]);
                if (marque != null) {
                    em.persist(marque);
                }
                Nutriscore nutriscore = creationInstanceNutriscore(iteration, nutriscores, line[3]);
                if (nutriscore != null) {
                    em.persist(nutriscore);
                }

                List<Ingredient> listIngredients = creationInstanceIngredient(iteration, ingredients, line[4].split(","));
                listIngredients.forEach(em::persist);

                List<Allergene> listAllergenes = creationInstanceAllergene(iteration, allergenes, line[28].split(","));
                listAllergenes.forEach(em::persist);

                Additif additif = creationInstanceAdditif(iteration, additifs, line[29]);
                if (additif != null) {
                    em.persist(additif);
                }

                Produit produit = creationInstanceProduit(iteration, produits, line[2]);
                if (produit != null) {
                    produit.setCategorie(categorie);
                    produit.setMarque(marque);
                    produit.setNutriscore(nutriscore);
                    Set<Ingredient> ingredientSet = produit.getIngredients();
                    produit.setIngredients(ingredientSet);
                    Set<Allergene> allergeneSet = produit.getAllergenes();
                    produit.setAllergenes(allergeneSet);
                    Set<Additif> additifSet = produit.getAdditifs();
                    produit.setAdditifs(additifSet);

                    em.persist(produit);
                }


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
     * @param iteration
     * @param additifs
     * @return
     */
    public static Additif creationInstanceAdditif(int iteration, HashMap<Integer, String> additifs, String lineSplit) {
        if (!additifs.containsValue(lineSplit)) {
            additifs.put(iteration, lineSplit);
            Additif additif = new Additif();
            additif.setNom_additif(additifs.get(iteration));
            return additif;
        }
        return null;

    }

    /**
     * @param iteration
     * @param allergenes
     * @param lineSplit
     * @return
     */
    public static List<Allergene> creationInstanceAllergene(int iteration, HashMap<Integer, String> allergenes, String[] lineSplit) {
        List<Allergene> listAllergenes = new ArrayList<>();
        for (String allerg : lineSplit) {
            if (!allergenes.containsValue(allerg)) {
                allergenes.put(iteration, allerg);
                Allergene allergene = new Allergene();
                allergene.setNom_allergene(allergenes.get(iteration));
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
    public static List<Ingredient> creationInstanceIngredient(int iteration, HashMap<Integer, String> ingredients, String[] lineSplit) {
        List<Ingredient> listOfIngredients = new ArrayList<>();
        for (String ingdt : lineSplit) {
            if (!ingredients.containsValue(ingdt)) {
                ingredients.put(iteration, ingdt);
                Ingredient ingredient = new Ingredient();
                ingredient.setNom_ingredient(ingredients.get(iteration));
                listOfIngredients.add(ingredient);
            }
        }
        return listOfIngredients;
    }

    /**
     * @param iteration
     * @param nutriscores
     * @param colContent
     * @return
     */
    public static Nutriscore creationInstanceNutriscore(int iteration, HashMap<Integer, String> nutriscores, String colContent) {
        if (!nutriscores.containsValue(colContent)) {
            nutriscores.put(iteration, colContent);
            Nutriscore nutriscore = new Nutriscore();
            nutriscore.setValeurScore(nutriscores.get(iteration).toCharArray()[0]);
            return nutriscore;
        }
        return null;
    }

    /**
     * @param iteration
     * @param marques
     * @param colContent
     * @return
     */
    public static Marque creationInstanceMarque(int iteration, HashMap<Integer, String> marques, String colContent) {
        if (!marques.containsValue(colContent)) {
            marques.put(iteration, colContent);
            Marque marque = new Marque();
            marque.setNom_marque(marques.get(iteration));
            return marque;
        }
        return null;
    }

    /**
     * @param iteration
     * @param produits
     * @param colContent
     * @return
     */
    public static Produit creationInstanceProduit(int iteration, HashMap<Integer, String> produits, String colContent) {
        if (!produits.containsValue(colContent)) {
            produits.put(iteration, colContent);
            Produit produit = new Produit();
            produit.setNom_produit(produits.get(iteration));
            return produit;
        }
        return null;
    }

    /**
     * @param iteration
     * @param hashMapCategorie
     * @param colContent
     * @return
     */
    public static Categorie creationInstanceCategorie(int iteration, HashMap<Integer, String> hashMapCategorie, String colContent) {
        if (!hashMapCategorie.containsValue(colContent)) {
            hashMapCategorie.put(iteration, colContent);
            Categorie categorie = new Categorie();
            categorie.setNom_categorie(hashMapCategorie.get(iteration));
            return categorie;
        }
        return null;
    }
}
