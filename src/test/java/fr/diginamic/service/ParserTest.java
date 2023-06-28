package fr.diginamic.service;

import fr.diginamic.mochizukiTools.Utils;
import junit.framework.TestCase;

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

}