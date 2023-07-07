package fr.diginamic.service;

import fr.diginamic.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.CriteriaBuilder;
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
                Set<Ingredient> listIngredients = creationInstanceIngredient(ingredients, line[4].split(","));
                listIngredients.forEach(em::persist);

                // REMPLISSAGE DU MAPPER DES ALLERGENES
                Set<Allergene> listAllergenes = creationInstanceAllergene(allergenes, line[28].split(","));
                listAllergenes.forEach(em::persist);

                // REMPLISSAGE DU MAPPER DES ADDITIFS
                Set<Additif> listAdditifs = creationInstanceAdditif(additifs, line[29].split(","));
                listAdditifs.forEach(em::persist);

                // REMPLISSAGE DU MAPPER DES PRODUITS AVEC LEUR JOINTURE
                Produit produit = creationInstanceProduit(produits, line[2]);

                // ASSOCIATIONS MANY TO MANY and MANY TO ONE
                produit.setCategorie(categorie);
                produit.setMarque(marque);
                produit.setNutriscore(nutriscore);
                produit.setIngredients(listIngredients);
                produit.setAllergenes(listAllergenes);
                produit.setAdditifs(listAdditifs);

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
    public static Set<Additif> creationInstanceAdditif(Set<String> hashSetAdditif, String[] colContent) {
        Set<Additif> setAdditifs = new HashSet<>();
        Additif additif = new Additif();
        for (String additi : colContent) {
            if (!hashSetAdditif.contains(additi)) {
                hashSetAdditif.add(additi);
            }
            Optional<String> optionalS = hashSetAdditif.stream().findAny().filter(s -> s.equalsIgnoreCase(additi));
            additif.setNom_additif(optionalS.get());
            setAdditifs.add(additif);
        }
        return setAdditifs;
    }

    /**
     * @param hashSetAllergene HashSet permettant de récupérer les chaines de caractère unique
     * @param colContent       tableau de chaines de caractère en paramètre de la méthode
     * @return on retourne une liste composée des instances d'objets
     */
    public static Set<Allergene> creationInstanceAllergene(Set<String> hashSetAllergene, String[] colContent) {
        Set<Allergene> setAllergenes = new HashSet<>();
        Allergene allergene = new Allergene();
        for (String allerg : colContent) {
            if (!hashSetAllergene.contains(allerg)) {
                hashSetAllergene.add(allerg);
            }
            Optional<String> optionalS = hashSetAllergene.stream().findAny().filter(s -> s.equalsIgnoreCase(allerg));
            allergene.setNom_allergene(optionalS.get());
            setAllergenes.add(allergene);
        }
        return setAllergenes;
    }

    /**
     * @param hashSetIngredient HashSet permettant de récupérer les chaines de caractère unique
     * @param lineSplit         tableau de chaines de caractère en paramètre de la méthode
     * @return on retourne une liste composée des instances d'objets
     */
    public static Set<Ingredient> creationInstanceIngredient(Set<String> hashSetIngredient, String[] lineSplit) {
        Set<Ingredient> setIngredients = new HashSet<>();
        Ingredient ingredient = new Ingredient();
        for (String ingdt : lineSplit) {
            if (!hashSetIngredient.contains(ingdt)) {
                hashSetIngredient.add(ingdt);
            }
            Optional<String> optionalS = hashSetIngredient.stream().findAny().filter(s -> s.equalsIgnoreCase(ingdt));
            ingredient.setNom_ingredient(optionalS.get());
            setIngredients.add(ingredient);
        }
        return setIngredients;
    }

    /**
     * @param hashSetNutriscore
     * @param colContent
     * @return
     */
    public static Nutriscore creationInstanceNutriscore(Set<String> hashSetNutriscore, String colContent) {
        if (!hashSetNutriscore.contains(colContent)) {
            hashSetNutriscore.add(colContent);
        }
        Optional<String> optionalS = hashSetNutriscore.stream().findAny().filter(s -> s.equalsIgnoreCase(colContent));
        Nutriscore nutriscore = new Nutriscore();
        nutriscore.setValeurScore(optionalS.get());
        return nutriscore;
    }

    /**
     * @param hashSetMarque
     * @param colContent
     * @return
     */
    public static Marque creationInstanceMarque(Set<String> hashSetMarque, String colContent) {
        if (!hashSetMarque.contains(colContent)) {
            hashSetMarque.add(colContent);
        }
        Optional<String> optionalS = hashSetMarque.stream().findAny().filter(s -> s.equalsIgnoreCase(colContent));
        Marque marque = new Marque();
        marque.setNom_marque(optionalS.get());
        return marque;
    }

    /**
     * @param hashSetProduit
     * @param colContent
     * @return
     */
    public static Produit creationInstanceProduit(Set<String> hashSetProduit, String colContent) {
        if (!hashSetProduit.contains(colContent)) {
            hashSetProduit.add(colContent);
        }
        Optional<String> optionalS = hashSetProduit.stream().findAny().filter(s -> s.equalsIgnoreCase(colContent));
        Produit produit = new Produit();
        produit.setNom_produit(optionalS.get());
        return produit;
    }

    /**
     * @param hashSetCategorie
     * @param colContent
     * @return
     */
    public static Categorie creationInstanceCategorie(Set<String> hashSetCategorie, String colContent) {
        if (!hashSetCategorie.contains(colContent)) {
            hashSetCategorie.add(colContent);
        }
        Optional<String> optionalS = hashSetCategorie.stream().findAny().filter(s -> s.equalsIgnoreCase(colContent));
        Categorie categorie = new Categorie();
        categorie.setNom_categorie(optionalS.get());
        return categorie;
    }
}
