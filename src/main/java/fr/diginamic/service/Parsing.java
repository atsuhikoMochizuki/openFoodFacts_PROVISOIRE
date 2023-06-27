package fr.diginamic.service;

import fr.diginamic.mochizukiTools.Utils;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parsing {
    private static Logger LOG = null;
    public static String CSV_FILE_RELATIVE_PATH = null;
    private static HashMap<String, Pattern> filtres = new HashMap<>();
    public static final String SEP_CHAR = "\\|";
    public static final String EFFACER_CHAR = "";
    private static String[] HEADER;

    // Filtres
    public static final String FILTRE_POURCENTAGES = "([0-9]).*%";
    public static final String FILTRE_GRAMMES = "([0-9]).*g";
    public static final String FILTRE_MILLIGRAMMES_MATIERES_GRASSES = "([0-9]).*%mg";
    public static final String FILTRE_CROCHETS = "\\[.+.\\]";
    public static final String FILTRE_PARENTHESES = "\\(.*.\\)";
    public static final String FILTRE_DEUX_POINTS = ".:.+";
    public static final String FILTRE_ETOILES = "\\*";
    public static final String FILTRE_TIRET_DU_SIX = " - ";
    public static final String FILTRE_UNDERSCORE = "_";
    public static final String FILTRE_POINT = "\\.";
    public static final String FILTRE_BARRE_VERTICALE = "\\|";

    static {
        LOG = Logging.LOG;
        CSV_FILE_RELATIVE_PATH = ResourceBundle.getBundle("project").getString("project.csvfile");
        filtres.put(FILTRE_POURCENTAGES.toLowerCase(), Pattern.compile(FILTRE_POURCENTAGES));
        filtres.put(FILTRE_MILLIGRAMMES_MATIERES_GRASSES.toLowerCase(), Pattern.compile(FILTRE_MILLIGRAMMES_MATIERES_GRASSES));
        filtres.put(FILTRE_GRAMMES.toLowerCase(), Pattern.compile(FILTRE_GRAMMES));
        filtres.put(FILTRE_CROCHETS.toLowerCase(), Pattern.compile(FILTRE_CROCHETS));
        filtres.put(FILTRE_PARENTHESES.toLowerCase(), Pattern.compile(FILTRE_PARENTHESES));
        filtres.put(FILTRE_DEUX_POINTS.toLowerCase(), Pattern.compile(FILTRE_DEUX_POINTS));
        filtres.put(FILTRE_UNDERSCORE.toLowerCase(), Pattern.compile(FILTRE_UNDERSCORE));
        filtres.put(FILTRE_ETOILES.toLowerCase(), Pattern.compile(FILTRE_ETOILES));
        filtres.put(FILTRE_POINT.toLowerCase(), Pattern.compile(FILTRE_POINT));
        filtres.put(FILTRE_TIRET_DU_SIX.toLowerCase(), Pattern.compile(FILTRE_TIRET_DU_SIX));
        filtres.put(FILTRE_BARRE_VERTICALE.toLowerCase(), Pattern.compile(FILTRE_BARRE_VERTICALE));
    }

    public static HashMap<String, Pattern> getFiltres() {
        return filtres;
    }

    public static void cleanFile(String csvFile_path) {
        Utils.msgInfo(String.format("Nettoyage du fichier %s", csvFile_path));
        //Récupération des données brutes
        int id_enregistrement = 0;
        Path path = Paths.get(csvFile_path);
        List<String> lines;

        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

        /*Application des filtres de nettoyage et insertion en base de données*/
        Iterator<String> iter = lines.iterator();
        HEADER = iter.next().split(SEP_CHAR);
        while (iter.hasNext()) {
            id_enregistrement++;
            String enregistrement = iter.next();
            String[] enregistrementParse = enregistrement.split(SEP_CHAR, HEADER.length);
            for (int i = 0; i < enregistrementParse.length; i++) {
                enregistrementParse[i] = nettoyageEnregistrement(filtres, enregistrementParse[i].toLowerCase());
                // System.out.println(enregistrementParse[i]);
                /* suite à implémenter*/
//                new Categorie(id_enregistrement, enregistrementParse[0]);
//                new Allergene(id_enregistrement, enregistrementParse[28]);
//                new Additif(id_enregistrement, enregistrementParse[29]);
            }
        }
        Utils.msgResult(String.format("Nettoyage du fichier %s OK", csvFile_path));
    }

    private static String nettoyageEnregistrement(HashMap<String, Pattern> filtres, String chaineAnettoyer) {

        // Application des filtres de remplacement de valeur
        chaineAnettoyer = chaineAnettoyer.replaceAll(" - ", ", ").trim();

        // Application des filtres de suppression
        for (Pattern filtre : filtres.values()) {
            Matcher matcher = filtre.matcher(chaineAnettoyer);
            chaineAnettoyer = matcher.replaceAll(EFFACER_CHAR);
        }

        return chaineAnettoyer;
    }
}
