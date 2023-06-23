package fr.diginamic.bll.parsingCsv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;

public class CsvFile extends AbstractAdvanceICsvFile {

	private CSVReader reader;

	private CsvFile() {
		super();
	}

	public CsvFile(File file) {
		this(file, DEFAULT_SEPARATOR);
	}

	public CsvFile(File file, char separator) {

		this();

		if (file == null) {
			throw new IllegalArgumentException("Le fichier file ne peut pas �tre null");
		}

		this.file = file;

		if (!isValidSeparator(separator)) {
			throw new IllegalArgumentException("Le s�parateur sp�cifi� n'est pas pris en charge.");
		}

		this.separator = separator;
		// Init
		init();
	}

	private void init() {

		try {
			reader = new CSVReader(new FileReader(file), separator);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// ...
		}

		data = new ArrayList<String[]>();

		try {
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				final int size = nextLine.length;
				if(size == 0) {
					continue;
				}
				
				String debut = nextLine[0].trim();
				if(debut.length() == 0 && size == 1 ) {
					continue;
				}
				if(debut.startsWith("#")) {
					continue;
				}
				data.add(nextLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
			//...
		}

		titles = data.get(0);
		data.remove(0);

		mapData();
	}

	// GETTERS

}
