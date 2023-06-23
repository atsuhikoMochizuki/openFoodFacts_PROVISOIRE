package fr.diginamic.bll.parsingCsv;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractICsvFile implements I_CsvFile {

	public final static char DEFAULT_SEPARATOR = ',';
	
	protected File file;
	protected List<String> lines;
	protected List<String[]> data;
	protected String[] titles;
	protected List<Map<String, String>> mappedData;
	
	protected void mapData() {
		mappedData = new ArrayList<Map<String, String>>(data.size());

		final int titlesLength = titles.length;

		for (String[] oneData : data) {
			final Map<String, String> map = new HashMap<String, String>();
			for (int i = 0; i < titlesLength; i++) {
				final String key = titles[i];
				final String value = oneData[i];
				map.put(key, value);
			}

			mappedData.add(map);
		}
	}

	
	
	// GETTERS
	
	public File getFile() {
		return file;
	}

	public List<String> getLines() {
		return lines;
	}

	public List<String[]> getData() {
		return data;
	}

	public String[] getTitles() {
		return titles;
	}

	public List<Map<String, String>> getMappedData() {
		return mappedData;
	}
	
}
