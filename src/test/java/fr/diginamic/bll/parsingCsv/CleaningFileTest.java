package fr.diginamic.bll.parsingCsv;

import junit.framework.TestCase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CleaningFileTest extends TestCase {


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
        assertEquals("écrémé à", chaineNettoye.trim());
    }

    public void testNettoyageMg() {
        String chaine = "yaourt 45mg";
        Pattern pattern = CleaningFile.getPatternCollection().get("FILTRE_SUR_LES_GRAMMES");
        Matcher rechercheMg = pattern.matcher(chaine);
        String chaineNettoye = rechercheMg.replaceAll("");
        assertEquals("yaourt", chaineNettoye.trim());

    }

    public void testNettoyageMgPourcentage() {
        String chaine = "yaourt 45%mg";
        Pattern pattern = CleaningFile.getPatternCollection().get("FILTRE_SUR_LES_MILLIGRAMMES");
        Matcher matcher = pattern.matcher(chaine);
        String chaineNettoye = matcher.replaceAll("");
        assertEquals("yaourt", chaineNettoye.trim());
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

    public void testWhiteSpace() {
        String chaineBrut = "édulcorants, carrefour sélection, sirop framboise grenade, d, sucre, eau, jus de fruits à base de concentrés , acidifiant, 1318, 0, 78,";

    }

    public void testNettoyage() {
        CleaningFile.cleanFile(CleaningFile.CSV_RELATIVE_PATH);
    }

}