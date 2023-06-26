package fr.diginamic.bll.parsingCsv;

import java.io.File;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class CleaningFile {

    public static ResourceBundle csvFile = ResourceBundle.getBundle("project");
    public static final String CSV_RELATIVE_PATH = csvFile.getString("project.csvfile");

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
    }

    public CleaningFile() {
    }

    public static HashMap<String, Pattern> getPatternCollection() {
        return patternCollection;
    }

    public static void setPatternCollection(HashMap<String, Pattern> patternCollection) {
        CleaningFile.patternCollection = patternCollection;
    }

    public static String getResourcePath(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            throw new IllegalArgumentException("Le nom du fichier ne peut pas �tre null");
        }

        final File f = new File("");
        final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
        return dossierPath;
    }

    public static File getResource(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            throw new IllegalArgumentException("Le nom du fichier ne peut pas �tre null");
        }

        final String completeFileName = getResourcePath(fileName);
        File file = new File(completeFileName);
        return file;
    }
}
