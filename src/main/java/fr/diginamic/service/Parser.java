package fr.diginamic.service;

import fr.diginamic.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;

import java.util.*;

public class Parser {
    private static final Logger LOG = Logging.LOG;

    private Parser() {
    }

    /**
     *
     */
    public static void insertToDataBase(List<String[]> rows) {

        // Initialisation de l'iterator
        Iterator<String[]> iter = rows.iterator();

        // HashMap de chaque colonne
        HashSet<String> categories = new HashSet<>();
        HashSet<String> nutriscores = new HashSet<>();
        HashSet<String> marques = new HashSet<>();
        HashSet<String> produits = new HashSet<>();
        HashSet<String> ingredients = new HashSet<>();
        HashSet<String> allergenes = new HashSet<>();
        HashSet<String> additifs = new HashSet<>();


        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("IBOOF-JPA");
             EntityManager em = emf.createEntityManager()
        ) {
//         Debut de la persistence avec transaction
            em.getTransaction().begin();

            while (iter.hasNext()) {
                String[] line = iter.next();

                // On fait appel aux Business Objects dans les méthodes suivantes pour
                // créer les instanciations qui vont permettre de rentrer les données dans la BD

                // REMPLISSAGE DU MAPPER DE LA CATEGORIE
                Categorie categorie = creationInstanceCategorie(categories, line[0]);
                em.persist(categorie);

                // REMPLISSAGE DU MAPPER DE LA MARQUE
                Marque marque = creationInstanceMarque(marques, line[1]);
                em.persist(marque);

                // REMPLISSAGE DU MAPPER DU NUTRISCORE
                Nutriscore nutriscore = creationInstanceNutriscore(nutriscores, line[3]);
                em.persist(nutriscore);

                // REMPLISSAGE DU MAPPER DE LA LISTE INGREDIENT
                List<Ingredient> listIngredients = creationInstanceIngredient(ingredients, line[4].split(","));
                listIngredients.forEach(em::persist);

                // REMPLISSAGE DU MAPPER DES ALLERGENES
                List<Allergene> listAllergenes = creationInstanceAllergene(allergenes, line[28].split(","));
                listAllergenes.forEach(em::persist);

                // REMPLISSAGE DU MAPPER DES ADDITIFS
                List<Additif> listAdditifs = creationInstanceAdditif(additifs, line[29].split(","));
                listAdditifs.forEach(em::persist);

                // REMPLISSAGE DU MAPPER DES PRODUITS AVEC LEUR JOINTURE
                Produit produit = creationInstanceProduit(produits, line[2]);

                // ASSOCIATIONS MANY TO MANY and MANY TO ONE
                produit.setCategorie(categorie);
                produit.setMarque(marque);
                produit.setNutriscore(nutriscore);

                Set<Ingredient> ingredientSet = new HashSet<>(listIngredients);
                produit.setIngredients(ingredientSet);

                Set<Allergene> allergeneSet = new HashSet<>(listAllergenes);
                produit.setAllergenes(allergeneSet);

                Set<Additif> additifSet = new HashSet<>(listAdditifs);
                produit.setAdditifs(additifSet);

                // PERSISTANCE DES DONNEES DE L'ENTITE PRODUIT
                em.persist(produit);
            }

            // Fin de la persistence avec transaction
            em.getTransaction().commit();

        } catch (IllegalStateException e) {
            LOG.error(e.getMessage());
        }

    }

    /**
     * @param hashSetAdditif HashSet permettant de récupérer les chaines de caractère unique
     * @param colContent     tableau de chaine de caractère en paramètre de la méthode
     * @return on retourne une liste composée des instances d'objets
     */
    public static List<Additif> creationInstanceAdditif(Set<String> hashSetAdditif, String[] colContent) {
        List<Additif> listAdditifs = new ArrayList<>();
        for (String additi : colContent) {
            Additif additif = new Additif();
            hashSetAdditif.add(additi);
            String s1 = String.valueOf(hashSetAdditif
                    .stream()
                    .filter(s -> s
                            .equalsIgnoreCase(additi)));
            additif.setNom_additif(s1);
            listAdditifs.add(additif);
        }
        return listAdditifs;
    }

    /**
     * @param hashSetAllergene HashSet permettant de récupérer les chaines de caractère unique
     * @param colContent       tableau de chaines de caractère en paramètre de la méthode
     * @return on retourne une liste composée des instances d'objets
     */
    public static List<Allergene> creationInstanceAllergene(Set<String> hashSetAllergene, String[] colContent) {
        List<Allergene> listAllergenes = new ArrayList<>();
        for (String allerg : colContent) {
            Allergene allergene = new Allergene();
            hashSetAllergene.add(allerg);
            String s1 = String.valueOf(hashSetAllergene
                    .stream()
                    .filter(s -> s
                            .equalsIgnoreCase(allerg)));
            allergene.setNom_allergene(s1);
            listAllergenes.add(allergene);
        }
        return listAllergenes;
    }

    /**
     * @param hashSetIngredient HashSet permettant de récupérer les chaines de caractère unique
     * @param lineSplit         tableau de chaines de caractère en paramètre de la méthode
     * @return on retourne une liste composée des instances d'objets
     */
    public static List<Ingredient> creationInstanceIngredient(Set<String> hashSetIngredient, String[] lineSplit) {
        List<Ingredient> listIngredients = new ArrayList<>();
        for (String ingdt : lineSplit) {
            Ingredient ingredient = new Ingredient();
            hashSetIngredient.add(ingdt);
            String s1 = String.valueOf(hashSetIngredient
                    .stream()
                    .filter(s -> s
                            .equalsIgnoreCase(ingdt)));
            ingredient.setNom_ingredient(s1);
            listIngredients.add(ingredient);
        }
        return listIngredients;
    }

    /**
     * @param hashSetNutriscore
     * @param colContent
     * @return
     */
    public static Nutriscore creationInstanceNutriscore(Set<String> hashSetNutriscore, String colContent) {
        Nutriscore nutriscore = new Nutriscore();
        hashSetNutriscore.add(colContent);
        String s1 = String.valueOf(hashSetNutriscore
                .stream()
                .filter(s -> s
                        .equalsIgnoreCase(colContent)));
        nutriscore.setValeurScore(s1);
        return nutriscore;
    }

    /**
     * @param hashSetMarque
     * @param colContent
     * @return
     */
    public static Marque creationInstanceMarque(Set<String> hashSetMarque, String colContent) {
        Marque marque = new Marque();
        hashSetMarque.add(colContent);
        String s1 = String.valueOf(hashSetMarque
                .stream()
                .filter(s -> s
                        .equalsIgnoreCase(colContent)));
        marque.setNom_marque(s1);

        return marque;
    }

    /**
     * @param hashSetProduit
     * @param colContent
     * @return
     */
    public static Produit creationInstanceProduit(Set<String> hashSetProduit, String colContent) {
        Produit produit = new Produit();
        hashSetProduit.add(colContent);
        String s1 = String.valueOf(hashSetProduit
                .stream()
                .filter(s -> s
                        .equalsIgnoreCase(colContent)));
        produit.setNom_produit(s1);
        return produit;
    }

    /**
     * @param hashSetCategorie
     * @param colContent
     * @return
     */
    public static Categorie creationInstanceCategorie(Set<String> hashSetCategorie, String colContent) {
        Categorie categorie = new Categorie();
        hashSetCategorie.add(colContent);
        String s1 = String.valueOf(hashSetCategorie
                .stream()
                .filter(s -> s
                        .equalsIgnoreCase(colContent)));
        categorie.setNom_categorie(s1);
        return categorie;
    }
}
