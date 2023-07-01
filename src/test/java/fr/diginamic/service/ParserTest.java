package fr.diginamic.service;

import fr.diginamic.mochizukiTools.Utils;
import fr.diginamic.service.parsing.Parser;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ParserTest extends TestCase {

    @Test
    public void test_parseFile() {
        Utils.msgInfo("Test de la fonction parseFile");
        List<List<String>> listeGenerale = Parser.parseFile(Cleaner.CSV_FILE_RELATIVE_PATH);
        Utils.msgResult("Affichage des éléments référencés en mémoire en vue du mapping");
        int indexListeGenerale = 0, indexListe = 0;
        for (List<String> liste : listeGenerale) {
            Utils.msgResult(String.format("Liste %d:", indexListeGenerale++));
            indexListe = 0;
            for (String elementListe : liste) {
                System.out.println(String.format("-%d: %s", indexListe++, elementListe));
            }
        }
        Utils.msgResult("Test de la fonction parseFile OK");
    }

    @Test
    public void creationInstanceAdditif() {
    }

    @Test
    public void creationInstanceAllergene() {
    }

    @Test
    public void creationInstanceIngredient() {
    }

    @Test
    public void creationInstanceNutriscore() {
    }

    @Test
    public void creationInstanceMarque() {
    }

    @Test
    public void creationInstanceProduit() {
    }

    @Test
    public void creationInstanceCategorie() {
    }
}