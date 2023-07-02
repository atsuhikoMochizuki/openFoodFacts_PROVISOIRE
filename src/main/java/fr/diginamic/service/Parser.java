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
                                    if (!refs_additifs.containsKey(splittedAdditifs[j])) {
                                        Additif additifToAdd = new Additif(splittedAdditifs[j]);
                                        refs_additifs.put(splittedAdditifs[j], additifToAdd);
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

    public static void instantiationProduits(ArrayList<String[]> rowsToParse, ArrayList listeGenerale, EntityManager em) {
        String nomProduit = new String("");
        Utils.msgTitle("Instantiation des produits...");

        em.getTransaction().begin();
        //Liste de référencements et leur index associés
        Map<String, Categorie> refs_categories = (Map<String, Categorie>) listeGenerale.get(0);
        Map<String, Marque> refs_marques = (Map<String, Marque>) listeGenerale.get(1);
        Map<String, Produit> refs_produits = (Map<String, Produit>) listeGenerale.get(2);
        Map<String, Nutriscore> refs_nutriscore = (Map<String, Nutriscore>) listeGenerale.get(3);
        Map<String, Ingredient> refs_ingredients = (Map<String, Ingredient>) listeGenerale.get(4);
        Map<String, Allergene> refs_allergenes = (Map<String, Allergene>) listeGenerale.get(5);
        Map<String, Additif> refs_additifs = (Map<String, Additif>) listeGenerale.get(6);

        Iterator<String[]> iter = rowsToParse.iterator();
        while (iter.hasNext()) {
            Produit produitToPersist = new Produit();
            String[] rowToParse = iter.next();
            Utils.msgInfo(String.format("Persistence du produit %s", rowToParse[PRODUIT]));
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
                        Utils.msgDebug("cateories attribuées");
                        break;

                    case MARQUE:
                        Set<Marque> marquesToAssociate = new HashSet<>();
                        String[] splittedMarque = rowToParse[MARQUE].split(",");
                        for (int j = 0; j < splittedMarque.length; j++) {
                            if (splittedMarque[j] == null) {
                            } else if (splittedMarque[j].contentEquals("")) {
                            } else if (splittedMarque[j].contentEquals(" ")) {
                            } else {
                                try {
                                    Marque marqueToAssociate = refs_marques.get(splittedMarque[j]);
                                    em.persist(marqueToAssociate);
                                    marquesToAssociate.add(marqueToAssociate);
                                } catch (Exception e) {
                                    Utils.msgError(e.getMessage());

                                }
                            }
                        }
                        produitToPersist.setMarques(marquesToAssociate);
                        Utils.msgDebug("marque attribuées");
                        break;

                    case PRODUIT:
                        Produit produitToAssociate = refs_produits.get(rowToParse[PRODUIT]);
                        if (produitToAssociate == null) {
                            throw new RuntimeException();
                        } else {
//                            em.persist(catToAssociate);
//                            produitToPersist.setCategorie(catToAssociate);
                            nomProduit = produitToAssociate.getNom_produit();
                        }
                        Utils.msgDebug("nom Récupéré");
                        break;

                    case NUTRITIONGRADEFR:
                        Nutriscore nutriscoreToAssociate = refs_nutriscore.get(rowToParse[NUTRITIONGRADEFR]);
                        if (nutriscoreToAssociate == null) {
                            throw new RuntimeException();
                        } else {
                            em.persist(nutriscoreToAssociate);
                            produitToPersist.setNutriscore(nutriscoreToAssociate);
                        }
                        Utils.msgDebug("nutriscore attribuées");
                        break;

                    case INGREDIENTS:
                        Set<Ingredient> ingredientsToAssociate = new HashSet<>();
                        String[] splittedIngredients = rowToParse[INGREDIENTS].split(",");
                        for (int j = 0; j < splittedIngredients.length; j++) {
                            if (splittedIngredients[j] == null) {
                            } else if (splittedIngredients[j].contentEquals("")) {
                            } else if (splittedIngredients[j].contentEquals(" ")) {
                            } else {
                                try {
                                    Ingredient ingredientToAssociate = refs_ingredients.get(splittedIngredients[j]);
                                    em.persist(ingredientToAssociate);
                                    ingredientsToAssociate.add(ingredientToAssociate);
                                } catch (Exception e) {
                                    Utils.msgError(e.getMessage());
                                }
                            }
                        }
                        produitToPersist.setIngredients(ingredientsToAssociate);
                        Utils.msgDebug("ingredients attribuées");
                        break;

                    case ENERGIE100G:
                        if (rowToParse[ENERGIE100G] == null) {
                        } else if (rowToParse[ENERGIE100G].contentEquals("")) {
                        } else if (rowToParse[ENERGIE100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setEnergiePour100g(Double.parseDouble(rowToParse[ENERGIE100G]));
                        }
                        break;

                    case GRAISSE100G:
                        if (rowToParse[GRAISSE100G] == null) {
                        } else if (rowToParse[GRAISSE100G].contentEquals("")) {
                        } else if (rowToParse[GRAISSE100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setGraisse100g(Double.parseDouble(rowToParse[GRAISSE100G]));
                        }
                        break;

                    case SUCRES100G:
                        if (rowToParse[SUCRES100G] == null) {
                        } else if (rowToParse[SUCRES100G].contentEquals("")) {
                        } else if (rowToParse[SUCRES100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setSucres100g(Double.parseDouble(rowToParse[SUCRES100G]));
                        }
                        break;

                    case FIBRES100G:
                        if (rowToParse[FIBRES100G] == null) {
                        } else if (rowToParse[FIBRES100G].contentEquals("")) {
                        } else if (rowToParse[FIBRES100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setFibres100g(Double.parseDouble(rowToParse[FIBRES100G]));
                        }
                        break;

                    case PROTEINES100G:
                        if (rowToParse[PROTEINES100G] == null) {
                        } else if (rowToParse[PROTEINES100G].contentEquals("")) {
                        } else if (rowToParse[PROTEINES100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setProteines100g(Double.parseDouble(rowToParse[PROTEINES100G]));
                        }
                        break;

                    case SEL100G:
                        if (rowToParse[SEL100G] == null) {
                        } else if (rowToParse[SEL100G].contentEquals("")) {
                        } else if (rowToParse[SEL100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setSel100g(Double.parseDouble(rowToParse[SEL100G]));
                        }
                        break;

                    case VITA100G:
                        if (rowToParse[VITA100G] == null) {
                        } else if (rowToParse[VITA100G].contentEquals("")) {
                        } else if (rowToParse[VITA100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVita100g(Double.parseDouble(rowToParse[VITA100G]));
                        }
                        break;

                    case VITD100G:
                        if (rowToParse[VITD100G] == null) {
                        } else if (rowToParse[VITD100G].contentEquals("")) {
                        } else if (rowToParse[VITD100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVitd100g(Double.parseDouble(rowToParse[VITD100G]));
                        }
                        break;

                    case VITE100G:
                        if (rowToParse[VITE100G] == null) {
                        } else if (rowToParse[VITE100G].contentEquals("")) {
                        } else if (rowToParse[VITE100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVite100g(Double.parseDouble(rowToParse[VITE100G]));
                        }
                        break;

                    case VITK100G:
                        if (rowToParse[ENERGIE100G] == null) {
                        } else if (rowToParse[ENERGIE100G].contentEquals("")) {
                        } else if (rowToParse[ENERGIE100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVitk100g(Double.parseDouble(rowToParse[ENERGIE100G]));
                        }
                        break;

                    case VITC100G:
                        if (rowToParse[VITC100G] == null) {
                        } else if (rowToParse[VITC100G].contentEquals("")) {
                        } else if (rowToParse[VITC100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVitc100g(Double.parseDouble(rowToParse[VITC100G]));
                        }
                        break;

                    case VITB1100G:
                        if (rowToParse[VITB1100G] == null) {
                        } else if (rowToParse[VITB1100G].contentEquals("")) {
                        } else if (rowToParse[VITB1100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVitb1100g(Double.parseDouble(rowToParse[VITB1100G]));
                        }
                        break;

                    case VITB2100G:
                        if (rowToParse[VITB2100G] == null) {
                        } else if (rowToParse[VITB2100G].contentEquals("")) {
                        } else if (rowToParse[VITB2100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVitb2100g(Double.parseDouble(rowToParse[VITB2100G]));
                        }
                        break;

                    case VITPP100G:
                        if (rowToParse[VITPP100G] == null) {
                        } else if (rowToParse[VITPP100G].contentEquals("")) {
                        } else if (rowToParse[VITPP100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVitpp100g(Double.parseDouble(rowToParse[VITPP100G]));
                        }
                        break;

                    case VITB6100G:
                        if (rowToParse[VITB6100G] == null) {
                        } else if (rowToParse[VITB6100G].contentEquals("")) {
                        } else if (rowToParse[VITB6100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVitb6100g(Double.parseDouble(rowToParse[VITB6100G]));
                        }
                        break;

                    case VITB9100G:
                        if (rowToParse[VITB9100G] == null) {
                        } else if (rowToParse[VITB9100G].contentEquals("")) {
                        } else if (rowToParse[VITB9100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVitb9100g(Double.parseDouble(rowToParse[VITB9100G]));
                        }
                        break;

                    case VITB12100G:
                        if (rowToParse[VITB12100G] == null) {
                        } else if (rowToParse[VITB12100G].contentEquals("")) {
                        } else if (rowToParse[VITB12100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setVitb12100g(Double.parseDouble(rowToParse[VITB12100G]));
                        }
                        break;

                    case CALCIUM100G:
                        if (rowToParse[CALCIUM100G] == null) {
                        } else if (rowToParse[CALCIUM100G].contentEquals("")) {
                        } else if (rowToParse[CALCIUM100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setCalcium100g(Double.parseDouble(rowToParse[CALCIUM100G]));
                        }
                        break;

                    case MAGNESIUM100G:
                        if (rowToParse[MAGNESIUM100G] == null) {
                        } else if (rowToParse[MAGNESIUM100G].contentEquals("")) {
                        } else if (rowToParse[MAGNESIUM100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setMagnesium100g(Double.parseDouble(rowToParse[MAGNESIUM100G]));
                        }
                        break;

                    case IRON100G:
                        if (rowToParse[IRON100G] == null) {
                        } else if (rowToParse[IRON100G].contentEquals("")) {
                        } else if (rowToParse[IRON100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setIron100g(Double.parseDouble(rowToParse[IRON100G]));
                        }
                        break;

                    case FER100G:
                        if (rowToParse[FER100G] == null) {
                        } else if (rowToParse[FER100G].contentEquals("")) {
                        } else if (rowToParse[FER100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setFer100g(Double.parseDouble(rowToParse[FER100G]));
                        }
                        break;

                    case BETACAROTENE100G:
                        if (rowToParse[BETACAROTENE100G] == null) {
                        } else if (rowToParse[BETACAROTENE100G].contentEquals("")) {
                        } else if (rowToParse[BETACAROTENE100G].contentEquals(" ")) {
                        } else {
                            produitToPersist.setBetacarotene100g(Double.parseDouble(rowToParse[BETACAROTENE100G]));
                        }
                        break;

                    case PRESENCEHUILEPALME:
                        if (rowToParse[PRESENCEHUILEPALME] == null) {
                        } else if (rowToParse[PRESENCEHUILEPALME].contentEquals("")) {
                        } else if (rowToParse[PRESENCEHUILEPALME].contentEquals(" ")) {
                        } else {
                            produitToPersist.setPresencehuilepalme(Boolean.parseBoolean(rowToParse[PRESENCEHUILEPALME]));
                        }
                        break;

                    case ALLERGENES:
                        Set<Allergene> allergenesToAssociate = new HashSet<>();
                        String[] splittedAllergenes = rowToParse[ALLERGENES].split(",");
                        for (int j = 0; j < splittedAllergenes.length; j++) {
                            if (splittedAllergenes[j] == null) {
                                Utils.msgWarning("La colonnes ne contient aucune donnée");
                            } else if (splittedAllergenes[j].contentEquals("")) {
                                Utils.msgWarning("La colonnes ne contient aucune donnée");
                            } else if (splittedAllergenes[j].contentEquals(" ")) {
                                Utils.msgWarning("La colonnes ne contient aucune donnée");
                            } else {
                                try {
                                    Allergene allergeneToAssociate = refs_allergenes.get(splittedAllergenes[j]);
                                    em.persist(allergeneToAssociate);
                                    allergenesToAssociate.add(allergeneToAssociate);
                                } catch (Exception e) {
                                    Utils.msgError(e.getMessage());

                                }
                            }
                        }
                        produitToPersist.setAllergenes(allergenesToAssociate);
                        Utils.msgDebug("allergenes attribuées");
                        break;

                    case ADDITIFS:
                        Set<Additif> additifsToAssociate = new HashSet<>();
                        String[] splittedAdditifs = rowToParse[ADDITIFS].split(",");
                        for (int j = 0; j < splittedAdditifs.length; j++) {
                            if (splittedAdditifs[j] == null) {
                            } else if (splittedAdditifs[j].contentEquals("")) {
                            } else if (splittedAdditifs[j].contentEquals(" ")) {
                            } else {
                                try {
                                    Additif additifToAssociate = refs_additifs.get(splittedAdditifs[j]);
                                    em.persist(additifToAssociate);
                                    additifsToAssociate.add(additifToAssociate);
                                } catch (Exception e) {
                                    Utils.msgError(e.getMessage());

                                }
                            }
                        }
                        produitToPersist.setAdditifs(additifsToAssociate);
                        Utils.msgDebug("additifs attribuées");
                        break;
                }
            }
            //Pour finir, on persiste le produit
            produitToPersist.setNom_produit(nomProduit);
            em.persist(produitToPersist);
            Utils.msgDebug("produit persisté");
        }
        em.getTransaction().commit();
    }
}


//public static void mapping(ArrayList listeGenerale,){
//        Utils.msgTitle("Mapping des données en base relationnelle");
//        Map<String, Produit> produits=(Map<String, Produit>)listeGenerale.get(2);
//        Iterator<Map<String, Produit>>iterator=listeGenerale.iterator();
//
//        for(Map.Entry<String, Produit> entry:produits.entrySet()){
//        String key=entry.getKey();
//        Produit value=entry.getValue();
//        System.out.println("Clé: "+key+", Valeur: "+value.toString());
//        entry.get
//        }
//
//
//        }
//        }
