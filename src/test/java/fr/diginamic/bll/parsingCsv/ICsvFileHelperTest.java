package fr.diginamic.bll.parsingCsv;

import static junit.framework.Assert.assertTrue;

import java.io.File;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class ICsvFileHelperTest {

	private final static String FILE_NAME = "src/test/resources/chien-test-01.csv";

	@Test
	public void testGetResource() {
		// Param
		final String fileName = FILE_NAME;

		// Result
		// ...

		// Appel
		final File file = CsvFileHelper.getResource(fileName);

		// Test
		assertTrue(file.exists());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetResourceNullFileName() {
		// Param
		final String fileName = null;

		// Result
		// ...

		// Appel qui plante
		CsvFileHelper.getResource(fileName);

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetResourceEmptyFileName() {
		// Param
		final String fileName = "";

		// Result
		// ...

		// Appel qui plante
		CsvFileHelper.getResource(fileName);

	}

	@Test
	public void testReadFile() {
		// Param
		final String fileName = FILE_NAME;

		// Result
		final int nombreLigne = 11;

		// Appel
		final File file = CsvFileHelper.getResource(fileName);
		List<String> lines = CsvFileHelper.readFile(file);

		// Test
		Assert.assertEquals(nombreLigne, lines.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReadFileNullFile() {
		// Param
		final File file = null;

		// Result
		// ...

		// Appel qui plante
		List<String> lines = CsvFileHelper.readFile(file);
	}

	@Test
	public void testReadFullFile() {
		// Param
		final String fileName = FILE_NAME;

		// Result
		final String start = "Id,Pr�nom,Couleur,Age";
		final String end = "10,Dingo,Roux,1";

		// Appel
		final File file = CsvFileHelper.getResource(fileName);
		String content = CsvFileHelper.readFullFile(file);
		System.out.println(content);

		// Test
		assertTrue(content.startsWith(start));
		assertTrue(content.endsWith(end));
	}

}
