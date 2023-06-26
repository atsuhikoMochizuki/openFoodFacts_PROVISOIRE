package fr.diginamic.bll.parsingCsv;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestCsvFile extends TestCase {

    private static final String FILE_NAME = "src/test/resources/open-food-facts.csv";
    private static File file;

    @BeforeClass
    public static void beforeClass() {
        file = CsvFileHelper.getResource(FILE_NAME);
    }


    public void testGetResource() {
        final File file = CsvFileHelper.getResource(CleaningFile.CSV_RELATIVE_PATH);
        assertTrue(file.exists());
    }

    @Test
    public void testMappedData() {

        // Param
        final char separator = '|';

        // Result
        final int nombreLigne = 13432;
        final int nombreColonnes = 31;

        // Appel
        final I_CsvFile ICsvFile = new CsvFile(file, separator);
        final List<Map<String, String>> mappedData = ICsvFile.getMappedData();
        // List<String[]> data = csvFile.getData();

        // Test
        assertEquals(nombreLigne, mappedData.size());

        for (Map<String, String> oneMappedData : mappedData) {
            assertEquals(nombreColonnes, oneMappedData.size());
        }
    }

    @Test
    public void testTitles() {

        // Param
        final char separator = ';';

        // Result
        String[] wantedTitles = {"Id", "Pr�nom", "Couleur", "Age"};

        // Appel
        final I_CsvFile ICsvFile = new CsvFile(file, separator);
        final String[] titles = ICsvFile.getTitles();
        for (String title : titles) {
            System.out.println(title);
        }

        // Test
        for (int i = 0; i < wantedTitles.length; i++) {
            assertEquals(wantedTitles[i], titles[i]);
        }
    }
}
