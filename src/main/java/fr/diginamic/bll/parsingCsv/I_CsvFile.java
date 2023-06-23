package fr.diginamic.bll.parsingCsv;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface I_CsvFile {

	/**
	 * 
	 * @return
	 */
	File getFile();

	/**
	 * 
	 * @return
	 */
	List<String[]> getData();
	
	/**
	 * 
	 * @return
	 */
	List<Map<String,String>> getMappedData();
	
	/**
	 * 
	 * @return
	 */
	String[] getTitles();
}
