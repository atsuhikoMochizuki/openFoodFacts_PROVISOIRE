package fr.diginamic.bll.parsingCsv;

import fr.diginamic.Main;
import fr.diginamic.entities.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CleaningFile {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static ResourceBundle csvFile = ResourceBundle.getBundle("project");
    public static final String CSV_RELATIVE_PATH = csvFile.getString("project.csvfile");

    public static String[] HEADER;

    // Filtres
    public static final String FILTRE_SUR_LES_POURCENTAGE = "([0-9]).*%";
    public static final String FILTRE_SUR_LES_GRAMMES = "([0-9]).*g";
    public static final String FILTRE_SUR_LES_MILLIGRAMMES = "([0-9]).*%mg";
    public static final String FILTRE_SUR_LES_CROCHETS = "\\[.+.\\]";
    public static final String FILTRE_SUR_LES_PARENTHESES = "\\(.*.\\)";
    public static final String FILTRE_SUR_LES_DEUX_POINTS = ".:.+";
    public static final String FILTRE_SUR_LES_ETOILES = "\\*";
    public static final String FILTRE_SUR_LE_TIRET = " - ";
    public static final String FILTRE_SUR_UNDERSCORE = "_";
    public static final String FILTRE_SUR_LE_POINT = "\\.";
    public static final String FILTRE_BARRE_VERTICAL = "\\|";

    private static HashMap<String, Pattern> patternCollection = new HashMap<>();

    static {
        patternCollection.put("FILTRE_SUR_LES_POURCENTAGE", Pattern.compile(FILTRE_SUR_LES_POURCENTAGE));
        patternCollection.put("FILTRE_SUR_LES_MILLIGRAMMES", Pattern.compile(FILTRE_SUR_LES_MILLIGRAMMES));
        patternCollection.put("FILTRE_SUR_LES_GRAMMES", Pattern.compile(FILTRE_SUR_LES_GRAMMES));
        patternCollection.put("FILTRE_SUR_LES_CROCHETS", Pattern.compile(FILTRE_SUR_LES_CROCHETS));
        patternCollection.put("FILTRE_SUR_LES_PARENTHESES", Pattern.compile(FILTRE_SUR_LES_PARENTHESES));
        patternCollection.put("FILTRE_SUR_LES_DEUX_POINTS", Pattern.compile(FILTRE_SUR_LES_DEUX_POINTS));
        patternCollection.put("FILTRE_SUR_UNDERSCORE", Pattern.compile(FILTRE_SUR_UNDERSCORE));
        patternCollection.put("FILTRE_SUR_LES_ETOILES", Pattern.compile(FILTRE_SUR_LES_ETOILES));
        patternCollection.put("FILTRE_SUR_LE_POINT", Pattern.compile(FILTRE_SUR_LE_POINT));
        patternCollection.put("FILTRE_SUR_LE_TIRET", Pattern.compile(FILTRE_SUR_LE_TIRET));
        patternCollection.put("FILTRE_BARRE_VERTICAL", Pattern.compile(FILTRE_BARRE_VERTICAL));
    }

    public CleaningFile() {
    }

    public static HashMap<String, Pattern> getPatternCollection() {
        return patternCollection;
    }

    public static void setPatternCollection(HashMap<String, Pattern> patternCollection) {
        CleaningFile.patternCollection = patternCollection;
    }

    /**
     *
     */
    public static void cleanFile(String pathcsv) {

        int iteration = 0;
        Path path = Paths.get(pathcsv);

        // Lecture ligne par ligne
        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

        // Initialisation de l'iterator
        Iterator<String> iter = lines.iterator();
        String entete = iter.next();
        HEADER = entete.split("\\|");

        HashMap<Integer, String> categories = new HashMap<>();
        HashMap<Integer, String> nutriscores = new HashMap<>();
        HashMap<Integer, String> marques = new HashMap<>();
        HashMap<Integer, String> produits = new HashMap<>();
        HashMap<Integer, String> ingredients = new HashMap<>();
        HashMap<Integer, String> allergenes = new HashMap<>();
        HashMap<Integer, String> additifs = new HashMap<>();

        Set<Produit> listProduits = new HashSet<>();

        while (iter.hasNext()) {
            iteration++;
            String line = iter.next();
            String[] lineSplit = line.split("\\|", 30);
            // L'APPEL DE LA METHODE DE NETTOYAGE EST PROPRE A LA CLASSE CLEANINGFILE
            for (int i = 0; i < lineSplit.length; i++) {
                lineSplit[i] = nettoyage(patternCollection, lineSplit[i].toLowerCase());
            }

            // LES METHODES D'INSTANCIATION DES BO DOIVENT ETRE RANGES DANS LE DAO
            creationInstanceCategorie(iteration, categories, lineSplit);

            creationInstanceProduit(iteration, produits, listProduits, lineSplit);

            creationInstanceMarque(iteration, marques, listProduits, lineSplit);

            creationInstanceNutriscore(iteration, nutriscores, lineSplit);

            creationInstanceIngredient(iteration, ingredients, lineSplit);

            creationInstanceAllergene(iteration, lineSplit);

            creationInstanceAdditif(iteration, lineSplit);

        }

    }

    private static void creationInstanceAdditif(int iteration, String[] lineSplit) {
        new Additif(iteration, lineSplit[29]);
    }

    private static void creationInstanceAllergene(int iteration, String[] lineSplit) {
        new Allergene(iteration, lineSplit[28]);
    }

    private static void creationInstanceIngredient(int iteration, HashMap<Integer, String> ingredients, String[] lineSplit) {
        String[] listeIngredients = lineSplit[4].split(",");
        for (String ingdt : listeIngredients) {
            if (!ingredients.containsValue(ingdt)) {
                ingredients.put(iteration, ingdt);
                new Ingredient(iteration, ingredients.get(iteration));
            }
        }
    }

    private static void creationInstanceNutriscore(int iteration, HashMap<Integer, String> nutriscores, String[] lineSplit) {
        if (!nutriscores.containsValue(lineSplit[3])) {
            nutriscores.put(iteration, lineSplit[3]);
            new Nutriscore(iteration, nutriscores.get(iteration).toCharArray()[0]);
        }
    }

    private static void creationInstanceMarque(int iteration, HashMap<Integer, String> marques, Set<Produit> listProduits, String[] lineSplit) {
        if (!marques.containsValue(lineSplit[1])) {
            marques.put(iteration, lineSplit[1]);
            new Marque(iteration, marques.get(iteration), listProduits);
        }
    }

    private static void creationInstanceProduit(int iteration, HashMap<Integer, String> produits, Set<Produit> listProduits, String[] lineSplit) {
        if (!produits.containsValue(lineSplit[2])) {
            produits.put(iteration, lineSplit[2]);
            Produit produit = new Produit(iteration, produits.get(iteration));
            listProduits.add(produit);
        }
    }

    private static void creationInstanceCategorie(int iteration, HashMap<Integer, String> categories, String[] lineSplit) {
        if (!categories.containsValue(lineSplit[0])) {
            categories.put(iteration, lineSplit[0]);
            new Categorie(iteration, categories.get(iteration));
        }
    }

    /**
     * @param elemnt
     * @param s
     * @return
     */
    private static String nettoyage(HashMap<String, Pattern> elemnt, String s) {
        // PREMIER TRAITEMENT
        for (Pattern patt : elemnt.values()) {
            Matcher matcher = patt.matcher(s);
            s = matcher.replaceAll("");
        }
        s = s.replaceAll(" - ", ", ").trim();
        return s;
    }
}
