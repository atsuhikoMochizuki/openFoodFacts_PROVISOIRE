package fr.diginamic.bll.parsingCsv;

import fr.diginamic.Main;
import fr.diginamic.entities.Categorie;
import fr.diginamic.entities.Produit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CleaningFileTest extends TestCase {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    String lineExample = "Saucissons secs pur porc|Bordeau Chesnel|L'affiné|e|Viande de porc, gras de porc, sel, dextrose, sucre, épices, ail, rhum, nitrite de sodium, nitrate de potassium, ferments|1590|28|0.3||32|4.6|||||||||||||||||0||E250 - Nitrite de sodium|";
    String[] lineSplit = lineExample.split("\\|");

    HashMap<Integer, String> categories = new HashMap<>();
    HashMap<Integer, String> nutriscores = new HashMap<>();
    HashMap<Integer, String> marques = new HashMap<>();
    HashMap<Integer, String> produits = new HashMap<>();
    HashMap<Integer, String> ingredients = new HashMap<>();
    HashMap<Integer, String> allergenes = new HashMap<>();
    HashMap<Integer, String> additifs = new HashMap<>();


    public void testNettoyagEtoile() {
        String chaineBrut = "*œuf d'esturgeon";
        Pattern pattern = CleaningFile.getPatternCollection().get("FILTRE_SUR_LES_ETOILES");
        Matcher rechercheEtoile = pattern.matcher(chaineBrut);
        String chaineNettoye = rechercheEtoile.replaceAll("");
        assertEquals("œuf d'esturgeon", chaineNettoye);
    }

    public void testNettoyagePourcentage() {
        String chaineBrut = "écrémé à 45%";
        Pattern pattern = CleaningFile.getPatternCollection().get("FILTRE_SUR_LES_POURCENTAGE");
        Matcher recherchePourcentage = pattern.matcher(chaineBrut);
        String chaineNettoye = recherchePourcentage.replaceAll("");
        assertEquals("écrémé à", chaineNettoye);
    }

    public void testNettoyageMg() {
        String chaine = "yaourt 45mg";
        Pattern pattern = CleaningFile.getPatternCollection().get("FILTRE_SUR_LES_GRAMMES");
        Matcher rechercheMg = pattern.matcher(chaine);
        String chaineNettoye = rechercheMg.replaceAll("");
        assertEquals("yaourt", chaineNettoye);

    }

    public void testNettoyageMgPourcentage() {
        String chaine = "yaourt 45%mg";
        Pattern pattern = CleaningFile.getPatternCollection().get("FILTRE_SUR_LES_MILLIGRAMMES");
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoye = matcher.replaceAll("");
        assertEquals("yaourt", chaineNettoye);
    }

    public void testNettoyageCrochet() {
        String chaine = "[uhfeuihuifhehufifiuiuhdfuiefh]";
        Pattern pattern = CleaningFile.getPatternCollection().get("FILTRE_SUR_LES_CROCHETS");
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoye = matcher.replaceAll("");
        assertEquals("", chaineNettoye);
    }

    public void testNettoyageParenthese() {
        String chaine = "(uhfeuihuifhehufifiuiuhdfuiefh)";
        Pattern pattern = CleaningFile.getPatternCollection().get("FILTRE_SUR_LES_PARENTHESES");
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoye = matcher.replaceAll("");
        assertEquals("", chaineNettoye);
    }

    public void testNettoyageDeuxPoints() {
        String chaine = "sucre - amidon - traces : lait en poudre";
        Pattern pattern = CleaningFile.getPatternCollection().get("FILTRE_SUR_LES_DEUX_POINTS");
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoye = matcher.replaceAll("");
        assertEquals("sucre - amidon - traces", chaineNettoye);
    }

    public void testNettoyageTirest() {
        String chaine = "sucre - amidon - traces";
        Pattern pattern = CleaningFile.getPatternCollection().get("FILTRE_SUR_LE_TIRET");
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoye = matcher.replaceAll(", ");
        assertEquals("sucre, amidon, traces", chaineNettoye);
    }

    public void testNettoyageUnderscore() {
        String chaine = "_oeuf_, _lait_, _creme, banane_";
        Pattern pattern = Pattern.compile("_");
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoye = matcher.replaceAll("");
        assertEquals("oeuf, lait, creme, banane", chaineNettoye);
    }

    public void testNettoyageComplet() {
        CleaningFile.cleanFile(CleaningFile.CSV_RELATIVE_PATH);
    }

    public void testConnectionToDataBase() {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("IBOOF-JPA");
             EntityManager em = emf.createEntityManager();
        ) {
            System.out.println(em);
        } catch (IllegalStateException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public void testCreationInstanceCategorie() {
        // Creation de l'echantillon de test
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("IBOOF-JPA");
             EntityManager em = emf.createEntityManager();
        ) {
            // Debut de la persistence avec transaction
            em.getTransaction().begin();

            lineSplit[0] = CleaningFile.nettoyage(CleaningFile.getPatternCollection(), lineSplit[0].toLowerCase());
            Categorie categorie = CleaningFile.creationInstanceCategorie(1, categories, lineSplit[0]);
            em.persist(categorie);

            em.getTransaction().commit();
            // Fin de la persistence avec transaction

        } catch (IllegalStateException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public void testCreationInstanceProduit() {
        // Creation de l'echantillon de test
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("IBOOF-JPA");
             EntityManager em = emf.createEntityManager();
        ) {
            // Debut de la persistence avec transaction
            em.getTransaction().begin();

            String nettoyage = CleaningFile.nettoyage(CleaningFile.getPatternCollection(), lineSplit[2].toLowerCase());
            Produit produit = CleaningFile.creationInstanceProduit(1, produits, nettoyage);
            em.persist(produit);

            em.getTransaction().commit();
            // Fin de la persistence avec transaction

        } catch (IllegalStateException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public void testCreationInstanceMarque() {
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("IBOOF-JPA");
             EntityManager em = emf.createEntityManager();
        ) {
            // Debut de la persistence avec transaction
            em.getTransaction().begin();


            em.getTransaction().commit();
            // Fin de la persistence avec transaction

        } catch (IllegalStateException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void testCreationInstanceNutriScore() {

    }

    public void testCreationInstanceIngredient() {

    }

    public void testCreationInstanceAllergene() {

    }

    public void testCreationInstanceAdditif() {

    }

}