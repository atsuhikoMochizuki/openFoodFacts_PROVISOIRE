package fr.diginamic.service;

import fr.diginamic.entities.*;
import fr.diginamic.mochizukiTools.Utils;
import jakarta.persistence.EntityManager;
import me.tongfei.progressbar.ProgressBar;
import org.slf4j.Logger;

import java.util.*;

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
    public static final int NBRE_LIGNES_IN_FILE = 13434;

    public static ArrayList parseFile(String csvFile_path) {
        Utils.msgInfo("Extraction, nettoyage et référencement en mémoire des élements du fichier" + " csv de OpenFoodFacts en vue de l'insertion en base de données");
        ArrayList<String[]> rowsOfFile = Cleaner.extractAndCleanDatas(csvFile_path);
        ArrayList listeGenerale = parseRows(rowsOfFile);
        mapping(listeGenerale);

        return listeGenerale;
    }

    public static ArrayList parseRows(ArrayList<String[]> rows) {
        //Création d'une liste qui référence toutes les listes correspondant aux valeurs de colonnes à
        //mapper dans la base de données.
        ArrayList listeGenerale = new ArrayList();

        //Liste de référencements et leur index associés
        Map<String, Categorie> refs_categories = new HashMap<>();
        Map<String, Marque> refs_marques = new HashMap<>();
        Map<String, Produit> refs_produits = new HashMap<>();
        Map<String, Nutriscore> refs_nutriscore = new HashMap<>();
        Map<String, Ingredient> refs_ingredients = new HashMap<>();
        Map<String, Allergene> refs_allergenes = new HashMap<>();
        Map<String, Additif> refs_additifs = new HashMap<>();

        try (ProgressBar progressBar = new ProgressBar("Parsing du fichier", NBRE_LIGNES_IN_FILE)) {
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
                            if (!refs_categories.containsKey(rowToParse[CATEGORIE])) {
                                Categorie categorieToAdd = new Categorie(rowToParse[CATEGORIE]);
                                refs_categories.put(rowToParse[CATEGORIE], categorieToAdd);
                            }
                            break;

                        case MARQUE:
                            String[] splittedMarque = rowToParse[MARQUE].trim().split(",");
                            for (int j = 0; j < splittedMarque.length; j++) {
                                if (splittedMarque[j] == null) {
                                } else if (splittedMarque[j].contentEquals("")) {
                                } else if (splittedMarque[j].contentEquals(" ")) {
                                } else {
                                    if (!refs_marques.containsKey(splittedMarque[j])) {
                                        Marque marqueToAdd = new Marque(splittedMarque[j]);
                                        refs_marques.put(splittedMarque[j], marqueToAdd);
                                    }
                                }
                            }
                            break;

                        case PRODUIT:
                            if (!refs_produits.containsKey(rowToParse[PRODUIT])) {
                                Produit produitToAdd = new Produit(rowToParse[PRODUIT]);
                                refs_produits.put(rowToParse[PRODUIT], produitToAdd);
                            }
                            break;

                        case NUTRITIONGRADEFR:
                            if (!refs_nutriscore.containsKey(rowToParse[NUTRITIONGRADEFR])) {
                                Nutriscore nutriscoreToAdd = new Nutriscore(rowToParse[NUTRITIONGRADEFR]);
                                refs_nutriscore.put(rowToParse[NUTRITIONGRADEFR], nutriscoreToAdd);
                            }
                            break;

                        case INGREDIENTS:
                            String[] splittedIngredient = rowToParse[INGREDIENTS].split(",");
                            for (int j = 0; j < splittedIngredient.length; j++) {
                                if (splittedIngredient[j] == null) {
                                } else if (splittedIngredient[j].contentEquals("")) {
                                } else if (splittedIngredient[j].contentEquals(" ")) {
                                } else {
                                    if (!refs_ingredients.containsKey(splittedIngredient[j])) {
                                        Ingredient ingredientToAdd = new Ingredient(splittedIngredient[j]);
                                        refs_ingredients.put(splittedIngredient[j], ingredientToAdd);
                                    }
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
                                    if (!refs_allergenes.containsKey(splittedAllergenes[j])) {
                                        Allergene allergeneToAdd = new Allergene(splittedAllergenes[j]);
                                        refs_allergenes.put(splittedAllergenes[j], allergeneToAdd);
                                    }
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
                                    if (!refs_allergenes.containsKey(splittedAdditifs[j])) {
                                        Allergene allergeneToAdd = new Allergene(splittedAdditifs[j]);
                                        refs_allergenes.put(splittedAdditifs[j], allergeneToAdd);
                                    }
                                }
                            }
                            break;


                    }
                }
                progressBar.step();
            }

        }
        try (ProgressBar progressBar2 = new ProgressBar("Mise à jour de la liste générale", 7)) {
            listeGenerale.add(refs_categories);
            progressBar2.step();

            listeGenerale.add(refs_marques);
            progressBar2.step();

            listeGenerale.add(refs_produits);
            progressBar2.step();

            listeGenerale.add(refs_nutriscore);
            progressBar2.step();

            listeGenerale.add(refs_ingredients);
            progressBar2.step();

            listeGenerale.add(refs_allergenes);
            progressBar2.step();

            listeGenerale.add(refs_additifs);
            progressBar2.step();
        }
        return listeGenerale;
    }

    public static ArrayList instantiationProduit(ArrayList<String[]> rowsToParse, ArrayList listeGenerale, EntityManager em) {
        Utils.msgTitle("Instantiation des produits...");

        //Liste de référencements et leur index associés
        Map<String, Categorie> refs_categories = (Map<String, Categorie>) listeGenerale.get(0);
        Map<String, Marque> refs_marques = (Map<String, Marque>) listeGenerale.get(1);
        Map<String, Produit> refs_produits = (Map<String, Produit>) listeGenerale.get(2);
        Map<String, Nutriscore> refs_nutriscore = (Map<String, Nutriscore>) listeGenerale.get(3);
        Map<String, Ingredient> refs_ingredients = (Map<String, Ingredient>) listeGenerale.get(4);
        Map<String, Allergene> refs_allergenes = (Map<String, Allergene>) listeGenerale.get(5);
        Map<String, Additif> refs_additifs = (Map<String, Additif>) listeGenerale.get(6);

//        Set entrySet = refs_produits.entrySet();
//        Iterator it_prod = entrySet.iterator();
//
//        while (it_prod.hasNext()) {
//            Map.Entry refProduit = (Map.Entry)it_prod.next();
//
        Produit produitToPersist = new Produit();


        Iterator<String[]> iter = rowsToParse.iterator();
        while (iter.hasNext()) {

            String[] rowToParse = iter.next();
            for (int colIndex = 0; colIndex < rowToParse.length; colIndex++) {
                switch (colIndex) {
                    case CATEGORIE:
                        Categorie catToAssociate = refs_categories.get(rowToParse[CATEGORIE]);
                        if (catToAssociate == null) {
                            throw new RuntimeException();
                        } else {
                            em.persist(catToAssociate);
                            produitToPersist.setCategorie(catToAssociate);
                        }
                        break;

                    case MARQUE:
                        //Nota : un produit peut contenir plusieurs marques, ce qui remet en cause
                        //Le mapping. Nous gardons donc pour le premier sprint d'éventuelles marques concaténées
//                        String[] splittedMarque = rowToParse[MARQUE].trim().split(",");
//                        for (int j = 0; j < splittedMarque.length; j++) {
//                            if (splittedMarque[j] == null) {
//                            } else if (splittedMarque[j].contentEquals("")) {
//                            } else if (splittedMarque[j].contentEquals(" ")) {
//                            } else {
//                                if (!refs_marques.containsKey(splittedMarque[j])) {
//                                    Marque marqueToAdd = new Marque(splittedMarque[j]);
//                                    refs_marques.put(splittedMarque[j], marqueToAdd);
//                                }
//                            }
//                        }
                        Marque marqueToAssociate = refs_marques.get(rowToParse[MARQUE]);
                        if (marqueToAssociate == null) {
                            throw new RuntimeException();
                        } else {
                            em.persist(marqueToAssociate);
                            produitToPersist.setMarque(marqueToAssociate);
                        }
                        break;

                    case NUTRITIONGRADEFR:
                        Nutriscore nutriscoreToAssociate = refs_nutriscore.get(rowToParse[NUTRITIONGRADEFR]);
                        if (nutriscoreToAssociate == null) {
                            throw new RuntimeException();
                        } else {
                            em.persist(nutriscoreToAssociate);
                            produitToPersist.setNutriscore(nutriscoreToAssociate);
                        }
                        break;

                    case INGREDIENTS:
                        String[] splittedIngredient = rowToParse[INGREDIENTS].split(",");
                        Set<Ingredient> ingredientsToAssociate = new HashSet<>();
                        for (int j = 0; j < splittedIngredient.length; j++) {
                            if (splittedIngredient[j] == null) {
                            } else if (splittedIngredient[j].contentEquals("")) {
                            } else if (splittedIngredient[j].contentEquals(" ")) {
                            } else {
                                Ingredient ingredientToAssociate = refs_ingredients.get(splittedIngredient[j]);
                                if (ingredientToAssociate == null) {
                                    throw new RuntimeException();
                                } else {
                                    ingredientsToAssociate.add(ingredientToAssociate);
                                }
                                break;
                            }
                        }
                        em.persist(ingredientsToAssociate);
                        produitToPersist.setIngredients(ingredientsToAssociate);
                        break;

                }
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
                if (!refs_allergenes.containsKey(splittedAllergenes[j])) {
                    Allergene allergeneToAdd = new Allergene(splittedAllergenes[j]);
                    refs_allergenes.put(splittedAllergenes[j], allergeneToAdd);
                }
            }
        }
        break;

        case ADDITIFS:
        String[] splittedAdditif = rowToParse[ADDITIFS].split(",");
        for (int j = 0; j < splittedAdditif.length; j++) {
            if (splittedAdditif[j] == null) {
            } else if (splittedAdditif[j].contentEquals("")) {
            } else if (splittedAdditif[j].contentEquals(" ")) {
            } else {
                if (!refs_additifs.containsKey(splittedAdditif[j])) {
                    Additif additifToAdd = new Additif(splittedAdditif[j]);
                    refs_additifs.put(splittedAdditif[j], additifToAdd);
                }
            }
        }
        break;
    }
}
        progressBar.step();
                }
                }
                try(
                ProgressBar progressBar2=new ProgressBar("Mise à jour de la liste générale",7))

                {
                listeGenerale.add(refs_categories);
                progressBar2.step();

                listeGenerale.add(refs_marques);
                progressBar2.step();

                listeGenerale.add(refs_produits);
                progressBar2.step();

                listeGenerale.add(refs_nutriscore);
                progressBar2.step();

                listeGenerale.add(refs_ingredients);
                progressBar2.step();

                listeGenerale.add(refs_allergenes);
                progressBar2.step();

                listeGenerale.add(refs_additifs);
                progressBar2.step();
                }
                return listeGenerale;
                }

public static void mapping(ArrayList listeGenerale,){
        Utils.msgTitle("Mapping des données en base relationnelle");
        Map<String, Produit> produits=(Map<String, Produit>)listeGenerale.get(2);
        Iterator<Map<String, Produit>>iterator=listeGenerale.iterator();

        for(Map.Entry<String, Produit> entry:produits.entrySet()){
        String key=entry.getKey();
        Produit value=entry.getValue();
        System.out.println("Clé: "+key+", Valeur: "+value.toString());
        entry.get
        }


        }
        }
