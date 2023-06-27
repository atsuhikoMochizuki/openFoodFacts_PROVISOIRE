package fr.diginamic.service;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.diginamic.service.Parsing.*;

public class ParsingTest extends TestCase {
    public HashMap<String, Pattern> filtres = Parsing.getFiltres();
    public String CSV_TEST_FILE_PATH = ResourceBundle.getBundle("project").getString("project.TEST_csvfile");


    public void test_FILTRE_ETOILES() {
        String chaineBrut = "*œuf d'esturgeon****";
        Pattern pattern = filtres.get(FILTRE_ETOILES.toLowerCase());
        Matcher rechercheEtoile = pattern.matcher(chaineBrut);
        String chaineNettoyee = rechercheEtoile.replaceAll(Parsing.EFFACER_CHAR);
        assertEquals("œuf d'esturgeon", chaineNettoyee);
    }

    //NE PASSE PAS!
    public void test_FILTRE_POURCENTAGES() {
        String chaineBrut = "%%écrémé à 45%%%%%%%%";
        Pattern pattern = filtres.get(FILTRE_POURCENTAGES.toLowerCase());
        Matcher recherchePourcentage = pattern.matcher(chaineBrut);
        String chaineNettoyee = recherchePourcentage.replaceAll(Parsing.EFFACER_CHAR);
        assertEquals("écrémé à", chaineNettoyee);
    }

    //    NE PASSE PAS!
    public void test_FILTRE_GRAMMES() {
        String chaine = "yaourt 45g";
        Pattern pattern = filtres.get(FILTRE_GRAMMES.toLowerCase());
        Matcher rechercheMg = pattern.matcher(chaine);
        String chaineNettoyee = rechercheMg.replaceAll(Parsing.EFFACER_CHAR);
        assertEquals("yaourt", chaineNettoyee);

    }

    // NE PASSE PAS!
    public void test_FILTRE_MILLIGRAMMES_MATIERES_GRASSES() {
        String chaine = "yaourt 45%mg";
        Pattern pattern = Parsing.getFiltres().get(FILTRE_MILLIGRAMMES_MATIERES_GRASSES.toLowerCase());
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoyee = matcher.replaceAll(EFFACER_CHAR);
        assertEquals("yaourt", chaineNettoyee);
    }

    public void test_FILTRE_CROCHETS() {
        String chaine = "[uhfeuihuifhehufifiuiuhdfuiefh]";
        Pattern pattern = Parsing.getFiltres().get(FILTRE_CROCHETS.toLowerCase());
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoyee = matcher.replaceAll(EFFACER_CHAR);
        assertEquals("", chaineNettoyee);
    }

    public void test_FILTRE_PARENTHESES() {
        String chaine = "(uhfeuihuifhehufifiuiuhdfuiefh)";
        Pattern pattern = Parsing.getFiltres().get(FILTRE_PARENTHESES.toLowerCase());
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoye = matcher.replaceAll(EFFACER_CHAR);
        assertEquals("", chaineNettoye);
    }

    public void test_FILTRE_DEUX_POINTS() {
        String chaine = "sucre - amidon - traces : lait en poudre";
        Pattern pattern = Parsing.getFiltres().get(FILTRE_DEUX_POINTS.toLowerCase());
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoyee = matcher.replaceAll(EFFACER_CHAR);
        assertEquals("sucre - amidon - traces", chaineNettoyee);
    }

    public void test_FILTRE_TIRET_DU_SIX() {
        String chaine = "sucre - amidon - traces";
        Pattern pattern = Parsing.getFiltres().get(FILTRE_TIRET_DU_SIX.toLowerCase());
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoyee = matcher.replaceAll(", ");
        assertEquals("sucre, amidon, traces", chaineNettoyee);
    }

    public void test_FILTRE_UNDERSCORE() {
        String chaine = "_oeuf_, _lait_, _creme, banane_";
        Pattern pattern = Parsing.getFiltres().get(FILTRE_UNDERSCORE.toLowerCase());
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoye = matcher.replaceAll("");
        assertEquals("oeuf, lait, creme, banane", chaineNettoye);
    }

    public void testWhiteSpace() {
        String chaineBrut = "édulcorants, carrefour sélection, sirop framboise grenade, d, sucre, eau, jus de fruits à base de concentrés , acidifiant, 1318, 0, 78,";
        //A TERMINER !
    }

    //Implémenter le test de la méthode cleanFile();
    //Implémenter le test de nettoyageEnregistrement();
    //Le fichier de test est dans le dossier des ressources de test
    // et contient dix enregistremnets
}