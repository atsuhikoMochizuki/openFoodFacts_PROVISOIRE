package fr.diginamic.bll.parsingCsv;

import static junit.framework.Assert.assertEquals;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class CsvFileBTest {

	private static final String FILE_NAME = "src/main/resources/open-food-facts.csv";
	private static File file;

	@BeforeClass
	public static void beforeClass() {
		file = CsvFileHelper.getResource(FILE_NAME);
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
		List<String[]> data = ICsvFile.getData();
		for (String[] oneData : data) {
			for (String s : oneData) {
				System.out.println(s);
			}
			System.out.println("--------");
		}

		// Test
		assertEquals(nombreLigne, mappedData.size());

		for (Map<String, String> oneMappedData : mappedData) {
			assertEquals(nombreColonnes, oneMappedData.size());
		}
	}
}
