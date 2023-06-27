package fr.diginamic.bll.parsingCsv;

import fr.diginamic.Main;
import fr.diginamic.entities.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

        while (iter.hasNext()) {
            iteration++;
            String line = iter.next();
            System.out.println(line);
            String[] lineSplit = line.split("\\|", 30);
            // L'APPEL DE LA METHODE DE NETTOYAGE EST PROPRE A LA CLASSE CLEANINGFILE
            for (int i = 0; i < lineSplit.length; i++) {
                lineSplit[i] = nettoyage(patternCollection, lineSplit[i].toLowerCase());

                // On fait appel aux Business Objects dans les méthodes suivantes pour
                // créer les instanciations qui vont permettre de rentrer les données dans la BD
                switch (i) {
                    case 0 -> creationInstanceCategorie(iteration, categories, lineSplit[0]);
                    case 1 -> creationInstanceMarque(iteration, marques, lineSplit[1]);
                    case 2 -> creationInstanceProduit(iteration, produits, lineSplit[2]);
                    case 3 -> creationInstanceNutriscore(iteration, nutriscores, lineSplit[3]);
                    case 4 -> creationInstanceIngredient(iteration, ingredients, lineSplit[4].split(","));
                    case 28 -> creationInstanceAllergene(iteration, allergenes, lineSplit[28].split(","));
                    case 29 -> creationInstanceAdditif(iteration, additifs, lineSplit[29].split(","));
                }
            }
        }

    }

    /**
     * @param iteration
     * @param additifs
     * @param lineSplit
     * @return
     */
    public static List<Additif> creationInstanceAdditif(int iteration, HashMap<Integer, String> additifs, String[] lineSplit) {
        List<Additif> listOfAdditifs = new ArrayList<>();
        for (String addit : lineSplit) {
            if (!additifs.containsValue(addit)) {
                additifs.put(iteration, addit);
                Additif additif = new Additif();
                additif.setNom_additif(additifs.get(iteration));
                listOfAdditifs.add(additif);
            }
        }
        return listOfAdditifs;
    }

    /**
     * @param iteration
     * @param allergenes
     * @param lineSplit
     * @return
     */
    public static List<Allergene> creationInstanceAllergene(int iteration, HashMap<Integer, String> allergenes, String[] lineSplit) {
        List<Allergene> listOfAllergenes = new ArrayList<>();
        for (String allerg : lineSplit) {
            if (!allergenes.containsValue(allerg)) {
                allergenes.put(iteration, allerg);
                Allergene allergene = new Allergene();
                allergene.setNom_allergene(allergenes.get(iteration));
                listOfAllergenes.add(allergene);
            }
        }
        return listOfAllergenes;
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
        Nutriscore nutriscore = new Nutriscore();
        if (!nutriscores.containsValue(colContent)) {
            nutriscores.put(iteration, colContent);
            nutriscore.setValeurScore(nutriscores.get(iteration).toCharArray()[0]);
        }
        return nutriscore;
    }

    /**
     * @param iteration
     * @param marques
     * @param colContent
     * @return
     */
    public static Marque creationInstanceMarque(int iteration, HashMap<Integer, String> marques, String colContent) {
        Marque marque = new Marque();
        if (!marques.containsValue(colContent)) {
            marques.put(iteration, colContent);
            marque.setNom_marque(marques.get(iteration));
        }
        return marque;
    }

    /**
     * @param iteration
     * @param produits
     * @param colContent
     * @return
     */
    public static Produit creationInstanceProduit(int iteration, HashMap<Integer, String> produits, String colContent) {
        Produit produit = new Produit();
        if (!produits.containsValue(colContent)) {
            produits.put(iteration, colContent);
            produit.setNom_produit(produits.get(iteration));
        }
        return produit;
    }

    /**
     * @param iteration
     * @param categories
     * @param colContent
     * @return
     */
    public static Categorie creationInstanceCategorie(int iteration, HashMap<Integer, String> categories, String colContent) {
        Categorie categorie = new Categorie();
        if (!categories.containsValue(colContent)) {
            categories.put(iteration, colContent);
            categorie.setNom_categorie(categories.get(iteration));
        }
        return categorie;
    }

    /**
     * @param elemnt
     * @param s
     * @return
     */
    public static String nettoyage(HashMap<String, Pattern> elemnt, String s) {
        // PREMIER TRAITEMENT
        for (Pattern patt : elemnt.values()) {
            Matcher matcher = patt.matcher(s);
            s = matcher.replaceAll("").trim();
        }
        s = s.replaceAll(" - ", ", ").trim();
        return s;
    }

}
