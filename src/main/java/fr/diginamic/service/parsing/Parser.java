package fr.diginamic.service.parsing;

import fr.diginamic.mochizukiTools.Utils;
import fr.diginamic.service.Cleaner;
import fr.diginamic.service.Logging;
import me.tongfei.progressbar.ProgressBar;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parser {
    private static final Logger LOG = Logging.LOG;

    public static final int CATEGORIE = 0;
    public static final int MARQUE = 1;
    public static final int PRODUIT = 2;
    public static final int NUTRITIONGRADEFR = 3;
    public static final int INGREDIENTS = 4;
    public static final int ENERGIE100G = 5;
    public static final int GRAISSE100G = 6;
    public static final int SUCRES100G = 7;
    public static final int FIBRES100G = 8;
    public static final int PROTEINES100G = 9;
    public static final int SEL100G = 10;
    public static final int VITA100G = 11;
    public static final int VITD100G = 12;
    public static final int VITE100G = 13;
    public static final int VITK100G = 14;
    public static final int VITC100G = 15;
    public static final int VITB1100G = 16;
    public static final int VITB2100G = 17;
    public static final int VITPP100G = 18;
    public static final int VITB6100G = 19;
    public static final int VITB9100G = 20;
    public static final int VITB12100G = 21;
    public static final int CALCIUM100G = 22;
    public static final int MAGNESIUM100G = 23;
    public static final int IRON100G = 24;
    public static final int FER100G = 25;
    public static final int BETACAROTENE100G = 26;
    public static final int PRESENCEHUILEPALME = 27;
    public static final int ALLERGENES = 28;
    public static final int ADDITIFS = 29;

    public static final int NBRE_LIGNES_IN_FILE = 13342;

    public static List<List<String>> parseFile(String csvFile_path) {
        Utils.msgInfo("Extraction, nettoyage et référencement en mémoire des élements du fichier" +
                " csv de OpenFoodFacts en vue de l'insertion en base de données");
        ArrayList<String[]> rowsOfFile = Cleaner.extractAndCleanDatas(csvFile_path);
        return parseRows(rowsOfFile);
    }

    public static List<List<String>> parseRows(ArrayList<String[]> rows) {
        //Création d'une liste qui référence toutes les listes correspondant aux valeurs de colonnes à
        //mapper dans la base de données.
        List<List<String>> generalList = new ArrayList<>();
        //Liste de référencements et leur index associés
        List<String> refs_categories = new ArrayList<>();
        int index_refs_categories = 0;

        List<String> refs_marques = new ArrayList<>();
        int index_refs_marques = 0;

        List<String> refs_produits = new ArrayList<>();
        int index_refs_produits = 0;

        List<String> refs_nutriscore = new ArrayList<>();
        int index_refs_nutriscrore = 0;

        List<String> refs_ingredients = new ArrayList<>();
        int index_refs_ingredients = 0;

        List<String> refs_allergenes = new ArrayList<>();
        int index_refs_allergenes = 0;

        List<String> refs_additifs = new ArrayList<>();
        int index_refs_additifs = 0;

        try (ProgressBar progressBar = new ProgressBar(String.format("Parsing du fichier %s", Cleaner.CSV_FILE_RELATIVE_PATH), NBRE_LIGNES_IN_FILE)) {
            int lineNbrInFile = 0;
            Iterator<String[]> iter = rows.iterator();

            Utils.msgTitle(String.format("Début de parsing des données du fichier %s", Cleaner.CSV_FILE_RELATIVE_PATH));
            while (iter.hasNext()) {
                lineNbrInFile++;
                String[] rowToParse = iter.next();

                for (int colIndex = 0; colIndex < rowToParse.length; colIndex++) {
                    switch (colIndex) {

                        case CATEGORIE:
                            boolean categorie_exist = false;
                            for (String categorie : refs_categories) {
                                if (categorie.contentEquals(rowToParse[CATEGORIE])) {
                                    categorie_exist = true;
                                    break;
                                }
                            }
                            if (!categorie_exist)
                                refs_categories.add(index_refs_categories++, rowToParse[CATEGORIE]);
                            break;

                        case MARQUE:
                            boolean marque_exist = false;
                            for (String marque : refs_marques) {
                                if (marque.contentEquals(rowToParse[MARQUE])) {
                                    marque_exist = true;
                                    break;
                                }
                            }
                            if (!marque_exist)
                                refs_marques.add(index_refs_marques++, rowToParse[MARQUE]);
                            break;

                        case PRODUIT:
                            boolean produit_exist = false;
                            for (String produit : refs_produits) {
                                if (produit.contentEquals(rowToParse[PRODUIT])) {
                                    produit_exist = true;
                                    break;
                                }
                            }
                            if (!produit_exist)
                                refs_produits.add(index_refs_produits++, rowToParse[PRODUIT]);
                            break;

                        case NUTRITIONGRADEFR:
                            boolean nutriscore_Exist = false;
                            for (String nutriscore : refs_nutriscore) {
                                if (nutriscore.contentEquals(rowToParse[NUTRITIONGRADEFR])) {
                                    nutriscore_Exist = true;
                                    break;
                                }
                            }
                            if (!nutriscore_Exist)
                                refs_nutriscore.add(index_refs_nutriscrore++, rowToParse[NUTRITIONGRADEFR]);
                            break;

                        case INGREDIENTS:
                            String[] splittedIngredient = rowToParse[INGREDIENTS].split(",");
                            for (int j = 0; j < splittedIngredient.length; j++) {
                                if (splittedIngredient[j] == null) {
                                } else if (splittedIngredient[j].contentEquals("")) {
                                } else if (splittedIngredient[j].contentEquals(" ")) {
                                } else {
                                    boolean ingredient_exist = false;
                                    for (String ingredient : refs_ingredients) {
                                        if (ingredient.contentEquals(splittedIngredient[j])) {
                                            ingredient_exist = true;
                                            break;
                                        }
                                    }
                                    if (!ingredient_exist)
                                        refs_ingredients.add(index_refs_ingredients++, splittedIngredient[j]);
                                }
                            }
                            break;

                        case ALLERGENES:
                            String[] splittedAllergenes = rowToParse[ALLERGENES].split(",");
                            for (int j = 0; j < splittedAllergenes.length; j++) {
                                if (splittedAllergenes[j] == null) {
                                } else if (splittedAllergenes[j].contentEquals("")) {
                                } else if (splittedAllergenes[j].contentEquals(" ")) {
                                } else {
                                    boolean allergene_exist = false;
                                    for (String ingredient : refs_allergenes) {
                                        if (ingredient.contentEquals(splittedAllergenes[j])) {
                                            allergene_exist = true;
                                            break;
                                        }
                                    }
                                    if (!allergene_exist)
                                        refs_allergenes.add(index_refs_allergenes++, splittedAllergenes[j]);
                                }
                            }
                            break;

                        case ADDITIFS:
                            String[] splittedAdditifs = rowToParse[ADDITIFS].split(",");
                            for (int j = 0; j < splittedAdditifs.length; j++) {
                                if (splittedAdditifs[j] == null) {
                                } else if (splittedAdditifs[j].contentEquals("")) {
                                } else if (splittedAdditifs[j].contentEquals(" ")) {
                                } else {
                                    boolean additif_exist = false;
                                    for (String additif : refs_additifs) {
                                        if (additif.contentEquals(splittedAdditifs[j])) {
                                            additif_exist = true;
                                            break;
                                        }
                                    }
                                    if (!additif_exist)
                                        refs_allergenes.add(index_refs_additifs++, splittedAdditifs[j]);
                                }
                                break;
                            }
                    }
                }
                progressBar.step();
            }
            try (ProgressBar progressBar2 = new ProgressBar("Mise à jour de la liste générale", 7)) {
                generalList.add(refs_categories);
                progressBar2.step();

                generalList.add(refs_marques);
                progressBar2.step();

                generalList.add(refs_produits);
                progressBar2.step();

                generalList.add(refs_nutriscore);
                progressBar2.step();

                generalList.add(refs_ingredients);
                progressBar2.step();

                generalList.add(refs_allergenes);
                progressBar2.step();

                generalList.add(refs_additifs);
                progressBar2.step();
            }
        }
        return generalList;
    }
}
