package fr.diginamic.service;

import fr.diginamic.entities.*;
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
        HashMap<Integer, Categorie> categories = new HashMap<>();
        HashMap<Integer, Nutriscore> nutriscores = new HashMap<>();
        HashMap<Integer, Marque> marques = new HashMap<>();
        HashMap<Integer, Produit> produits = new HashMap<>();
        HashMap<Integer, Ingredient> ingredients = new HashMap<>();
        HashMap<Integer, Allergene> allergenes = new HashMap<>();
        HashMap<Integer, Additif> additifs = new HashMap<>();


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

                // REMPLISSAGE DU MAPPER DE LA CATEGORIE
                creationInstanceCategorie(iteration, categories, line[0]);

                // REMPLISSAGE DU MAPPER DE LA MARQUE
                creationInstanceMarque(iteration, marques, line[1]);

                // REMPLISSAGE DU MAPPER DU NUTRISCORE
                creationInstanceNutriscore(iteration, nutriscores, line[3]);

                // REMPLISSAGE DU MAPPER DE LA LISTE INGREDIENT
                creationInstanceIngredient(iteration, ingredients, line[4].split(","));

                // REMPLISSAGE DU MAPPER DES ALLERGENES
                creationInstanceAllergene(iteration, allergenes, line[28].split(","));

                // REMPLISSAGE DU MAPPER DES ADDITIFS
                creationInstanceAdditif(iteration, additifs, line[29].split(","));

                // REMPLISSAGE DU MAPPER DES PRODUITS AVEC LEUR JOINTURE
                creationInstanceProduit(iteration, produits, line[2]);

            }

            // PERSISTENCE DES DONNEES POUR CHAQUE ENTITES
            categories.forEach((integer, categorie) -> em.persist(categorie));
            marques.forEach((integer, marque) -> em.persist(marque));
            nutriscores.forEach((integer, nutriscore) -> em.persist(nutriscore));
            ingredients.forEach((integer, ingredient) -> em.persist(ingredient));
            allergenes.forEach((integer, allergene) -> em.persist(allergene));
            additifs.forEach((integer, additif) -> em.persist(additif));

            // ASSOCIATIONS MANY TO MANY and MANY TO ONE
            categories.values().forEach(categorie -> produits.values().forEach(produit -> produit.setCategorie(categorie)));
            marques.values().forEach(marque -> produits.values().forEach(produit -> produit.setMarque(marque)));
            nutriscores.values().forEach(nutriscore -> produits.values().forEach(produit -> produit.setNutriscore(nutriscore)));

            Set<Ingredient> ingredientSet = new HashSet<>(ingredients.values());
            produits.values().forEach(produit -> produit.setIngredients(ingredientSet));

            Set<Allergene> allergeneSet = new HashSet<>(allergenes.values());
            produits.values().forEach(produit -> produit.setAllergenes(allergeneSet));

            Set<Additif> additifSet = new HashSet<>(additifs.values());
            produits.values().forEach(produit -> produit.setAdditifs(additifSet));

            // PERSISTANCE DES DONNEES DE L'ENTITE PRODUIT
            produits.values().forEach(em::persist);

            // Fin de la persistence avec transaction
            em.getTransaction().commit();

        } catch (
                IllegalStateException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    /**
     * @param hashMapAdditifs
     * @param colContent
     * @return
     */
    public static void creationInstanceAdditif(int iteration, Map<Integer, Additif> hashMapAdditifs, String[] colContent) {
        for (String additi : colContent) {
            Additif additif = new Additif();
            if (hashMapAdditifs.containsKey(iteration)) {
                additif.setNom_additif(String.valueOf(hashMapAdditifs
                        .values()
                        .stream()
                        .filter(additif1 -> additif1
                                .getNom_additif()
                                .equalsIgnoreCase(additi))));
            } else {
                hashMapAdditifs.put(iteration, additif);
                additif.setNom_additif(String.valueOf(hashMapAdditifs.get(iteration).getNom_additif()));
            }
        }
    }

    /**
     * @param hashMapAllergenes
     * @param colContent
     * @return
     */
    public static void creationInstanceAllergene(int iteration, Map<Integer, Allergene> hashMapAllergenes, String[] colContent) {
        for (String allerg : colContent) {
            Allergene allergene = new Allergene();
            if (hashMapAllergenes.containsKey(iteration)) {
                allergene.setNom_allergene(String.valueOf(hashMapAllergenes
                        .values()
                        .stream()
                        .filter(s -> s
                                .getNom_allergene()
                                .equalsIgnoreCase(allerg))));
            } else {
                hashMapAllergenes.put(iteration, allergene);
                allergene.setNom_allergene(String.valueOf(hashMapAllergenes.get(iteration).getNom_allergene()));
            }
        }
    }

    /**
     * @param hashMapIngredients
     * @param lineSplit
     * @return
     */
    public static void creationInstanceIngredient(int iteration, Map<Integer, Ingredient> hashMapIngredients, String[] lineSplit) {
        for (String ingdt : lineSplit) {
            Ingredient ingredient = new Ingredient();
            if (hashMapIngredients.containsKey(iteration)) {
                ingredient.setNom_ingredient(String.valueOf(hashMapIngredients
                        .values()
                        .stream()
                        .filter(s -> s
                                .getNom_ingredient()
                                .equalsIgnoreCase(ingdt))));
            } else {
                hashMapIngredients.put(iteration, ingredient);
                ingredient.setNom_ingredient(String.valueOf(hashMapIngredients.get(iteration).getNom_ingredient()));
            }
        }
    }

    /**
     * @param hashMapNutriscore
     * @param colContent
     * @return
     */
    public static void creationInstanceNutriscore(int iteration, Map<Integer, Nutriscore> hashMapNutriscore, String colContent) {
        Nutriscore nutriscore = new Nutriscore();
        if (hashMapNutriscore.containsKey(iteration)) {
            nutriscore.setValeurScore(String.valueOf(hashMapNutriscore
                    .values()
                    .stream()
                    .filter(s -> s
                            .getValeurScore()
                            .equalsIgnoreCase(colContent))));
        } else {
            hashMapNutriscore.put(iteration, nutriscore);
            nutriscore.setValeurScore(String.valueOf(hashMapNutriscore.get(iteration).getValeurScore()));
        }
    }

    /**
     * @param hashMapMarques
     * @param colContent
     * @return
     */
    public static void creationInstanceMarque(int iteration, Map<Integer, Marque> hashMapMarques, String colContent) {
        Marque marque = new Marque();
        if (hashMapMarques.containsKey(iteration)) {
            marque.setNom_marque(String.valueOf(hashMapMarques
                    .values()
                    .stream()
                    .filter(s -> s
                            .getNom_marque()
                            .equalsIgnoreCase(colContent))));
        } else {
            hashMapMarques.put(iteration, marque);
            marque.setNom_marque(String.valueOf(hashMapMarques.get(iteration).getNom_marque()));
        }
    }

    /**
     * @param hashMapProduits
     * @param colContent
     * @return
     */
    public static void creationInstanceProduit(int iteration, Map<Integer, Produit> hashMapProduits, String colContent) {
        Produit produit = new Produit();
        if (hashMapProduits.containsKey(iteration)) {
            produit.setNom_produit(String.valueOf(hashMapProduits
                    .values()
                    .stream()
                    .filter(s -> s
                            .getNom_produit()
                            .equalsIgnoreCase(colContent))));
        } else {
            hashMapProduits.put(iteration, produit);
            produit.setNom_produit(String.valueOf(hashMapProduits.get(iteration).getNom_produit()));
        }
    }

    /**
     * @param hashMapCategorie
     * @param colContent
     * @return
     */
    public static void creationInstanceCategorie(int iteration, Map<Integer, Categorie> hashMapCategorie, String colContent) {
        Categorie categorie = new Categorie();
        if (hashMapCategorie.containsKey(iteration)) {
            categorie.setNom_categorie(String.valueOf(hashMapCategorie
                    .values()
                    .stream()
                    .filter(s -> s
                            .getNom_categorie()
                            .equalsIgnoreCase(colContent))));
        } else {
            hashMapCategorie.put(iteration, categorie);
            categorie.setNom_categorie(String.valueOf(hashMapCategorie.get(iteration).getNom_categorie()));
        }
    }
}
