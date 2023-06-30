package fr.diginamic.service;

import fr.diginamic.mochizukiTools.Utils;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;

public class ParserTest extends TestCase {

    public void test_insertionToDataBase() {
        Utils.msgInfo("Extraction des données depuis le fichier csv...");
        ArrayList<String[]> rows = Cleaner.cleanFile(Cleaner.CSV_FILE_RELATIVE_PATH);
        Utils.msgResult("Extraction des données OK");
        Utils.msgInfo("Migration des données dans la base...");
        Parser.insertToDataBase(rows);
        Utils.msgResult("Migration des données dans la base OK");
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