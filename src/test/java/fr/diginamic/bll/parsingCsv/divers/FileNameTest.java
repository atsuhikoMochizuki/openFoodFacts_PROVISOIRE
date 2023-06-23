package fr.diginamic.bll.parsingCsv.divers;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.diginamic.bll.parsingCsv.*;

public class FileNameTest {
	private static final String FILE_NAME = "src/test/resources/chien-test-01.csv";
	private static File file;

	@BeforeClass
	public static void beforeClass() {
		file = CsvFileHelper.getResource(FILE_NAME);
	}

	@Test
	public void testFileName() {

		// Param
		// ...

		// Result
		final String resultName = "chien-test-01.csv";

		// Appel
		final String name = file.getName();
		System.out.println(name);
		// --> chien-test-01.csv

		// test
		assertEquals(resultName, name);
	}

	@Test
	public void testAbsolutePath() {

		// Param
		// ...

		// Result
		final String notResultName = "chien-test-01.csv";

		// Appel
		final String absolutePathame = file.getAbsolutePath();
		System.out.println(absolutePathame);
		// -->
		// D:\javadev\article\developpez.com\tuto-csv\src\test\resources\chien-test-01.csv

		// test
		assertFalse(notResultName.equals(absolutePathame));
	}
}
