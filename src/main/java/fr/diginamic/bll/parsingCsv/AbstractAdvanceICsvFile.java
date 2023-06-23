package fr.diginamic.bll.parsingCsv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractAdvanceICsvFile extends AbstractICsvFile {

	public final static List<Character> AVAILABLE_SEPARATORS = Collections.unmodifiableList(new ArrayList<Character>(
			Arrays.asList(',', ';', '\t', '|')));

	// public final static Set<Character> AVAILABLE_SEPARATORS_AS_SET =
	// Collections.unmodifiableSet(new LinkedHashSet<Character>(
	// Arrays.asList(',', ';', '\t', '|')));

	protected char separator = DEFAULT_SEPARATOR;

	protected List<String> cleanedLines;

	protected void cleanLines() {
		cleanedLines = new ArrayList<String>();
		for (String line : lines) {
			// Suppression des espaces de fin de ligne
			line = line.trim();

			// On saute les lignes vides
			if (line.length() == 0) {
				continue;
			}

			// On saute les lignes de commentaire
			if (line.startsWith("#")) {
				continue;
			}

			cleanedLines.add(line);
		}

	}

	protected boolean isValidSeparator(char separator) {
		return AVAILABLE_SEPARATORS.contains(separator);
	}

	public int compterSeperateurs(String line, char separator) {
		int number = 0;

		int pos = line.indexOf(separator);
		while (pos != -1) {
			number++;
			line = line.substring(pos + 1);
			pos = line.indexOf(separator);
		}
		return number;
	}

	// GETTERS - SETTERS

	public char getSeparator() {
		return separator;
	}

	public void setSeparator(char separator) {
		this.separator = separator;
	}

}
