package fr.diginamic.bll.parsingCsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileHelper {

	public static List<String> readFile(File file) {

		if (file == null) {
			throw new IllegalArgumentException("Le fichier ne peut pas �tre null");
		}

		if (!file.exists()) {
			throw new IllegalArgumentException("Le fichier " + file.getName() + " n'existe pas.");
		}

		final List<String> result = new ArrayList<String>();

		FileReader fr = null;
		BufferedReader br = null;

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			for (String line = br.readLine(); line != null; line = br.readLine()) {
				result.add(line);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

		return result;
	}

	public static String readFileAsString(File file) {
		final List<String> lines = readFile(file);
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append("\n");
		}

		return sb.toString();
	}

	public static String readFullFile(File file) {
		StringBuilder sb = new StringBuilder();

		if (file == null) {
			throw new IllegalArgumentException("Le fichier ne peut pas �tre null");
		}

		if (!file.exists()) {
			throw new IllegalArgumentException("Le fichier " + file.getName() + " n'existe pas.");
		}

		FileReader reader = null;
		try {
			reader = new FileReader(file);

			char[] buffer = new char[2048];
			int nb = 0;
			while ((nb = reader.read(buffer)) > 0) {
				sb.append(buffer, 0, nb);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	public static String getResourcePath(String fileName) {
		if (fileName == null || fileName.length() == 0) {
			throw new IllegalArgumentException("Le nom du fichier ne peut pas �tre null");
		}

		final File f = new File("");
		final String dossierPath = f.getAbsolutePath() + File.separator + fileName;
		return dossierPath;
	}

	public static File getResource(String fileName) {
		if (fileName == null || fileName.length() == 0) {
			throw new IllegalArgumentException("Le nom du fichier ne peut pas �tre null");
		}

		final String completeFileName = getResourcePath(fileName);
		File file = new File(completeFileName);
		return file;
	}

	public static String cleanKey(String key) {
		String cleanKey = key.toLowerCase();
		return cleanKey;
	}
	
	public static void readFullFile2(File file) {
		FileReader reader = null;
		try {
			reader = new FileReader(file);

			for(char c = (char) reader.read(); c != '\0'; c = (char) reader.read() ) {
				
			}
			

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
