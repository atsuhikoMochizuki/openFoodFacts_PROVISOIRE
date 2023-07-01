package fr.diginamic.service.parsing;

import fr.diginamic.mochizukiTools.Utils;
import fr.diginamic.service.Cleaner;
import fr.diginamic.service.Logging;
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


    public static void parseRows(ArrayList<String[]> rows) {
        int lineNbrInFile = 0;
        Iterator<String[]> iter = rows.iterator();

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


        Utils.msgTitle(String.format("Début de parsing des données du fichier %s", Cleaner.CSV_FILE_RELATIVE_PATH));

        while (iter.hasNext()) {
            lineNbrInFile++;
            String[] rowToParse = iter.next();
            Utils.msgTitle(String.format("Analyse de la ligne %d du fichier", lineNbrInFile));
            for (int colIndex = 0; colIndex < rowToParse.length; colIndex++) {
                switch (colIndex) {
                    case CATEGORIE:
                        Utils.msgInfo(String.format("Recherche de la catégorie <%s> dans la liste dédiée...", rowToParse[CATEGORIE]));
                        boolean categorie_exist = false;
                        for (String categorie : refs_categories) {
                            if (categorie.contentEquals(rowToParse[CATEGORIE])) {
                                categorie_exist = true;
                                break;
                            }
                        }
                        if (categorie_exist)
                            Utils.msgResult(String.format("La catégorie %s est déjà référencée dans la liste dédiée", rowToParse[CATEGORIE]));
                        else {
                            Utils.msgInfo("Categorie inexistante : début du référencement...");
                            refs_categories.add(index_refs_categories++, rowToParse[CATEGORIE]);
                            Utils.msgResult(String.format("Référencement de la catégorie <%s> OK", rowToParse[CATEGORIE]));
                        }
                        break;

                    case MARQUE:
                        Utils.msgInfo(String.format("Recherche de la marque <%s> dans la liste dédiée...", rowToParse[MARQUE]));
                        boolean marque_exist = false;
                        for (String marque : refs_marques) {
                            if (marque.contentEquals(rowToParse[MARQUE])) {
                                marque_exist = true;
                                break;
                            }
                        }
                        if (marque_exist)
                            Utils.msgResult(String.format("La marque %s est déjà référencée dans la liste dédiée", rowToParse[MARQUE]));
                        else {
                            Utils.msgInfo("Categorie inexistante : début du référencement...");
                            refs_marques.add(index_refs_marques++, rowToParse[MARQUE]);
                            Utils.msgResult(String.format("Référencement de la marque <%s> OK", rowToParse[MARQUE]));
                        }
                        break;

                    case PRODUIT:
                        Utils.msgInfo(String.format("Recherche du produit <%s> dans la liste dédiée...", rowToParse[PRODUIT]));
                        boolean produit_exist = false;
                        for (String produit : refs_produits) {
                            if (produit.contentEquals(rowToParse[PRODUIT])) {
                                produit_exist = true;
                                break;
                            }
                        }
                        if (produit_exist)
                            Utils.msgResult(String.format("Le produit %s est déjà référencée dans la liste dédiée", rowToParse[PRODUIT]));
                        else {
                            Utils.msgInfo("Produit inexistant : début du référencement...");
                            refs_produits.add(index_refs_produits++, rowToParse[PRODUIT]);
                            Utils.msgResult(String.format("Référencement du produit <%s> OK", rowToParse[PRODUIT]));
                        }
                        break;

                    case NUTRITIONGRADEFR:
                        Utils.msgInfo(String.format("Recherche du nutriscore <%s> dans la liste dédiée...", rowToParse[NUTRITIONGRADEFR]));
                        boolean nutriscore_Exist = false;
                        for (String nutriscore : refs_nutriscore) {
                            if (nutriscore.contentEquals(rowToParse[NUTRITIONGRADEFR])) {
                                nutriscore_Exist = true;
                                break;
                            }
                        }
                        if (nutriscore_Exist)
                            Utils.msgResult(String.format("Le nutriscore %s est déjà référencée dans la liste dédiée", rowToParse[NUTRITIONGRADEFR]));
                        else {
                            Utils.msgInfo("Nutriscore inexistant : début du référencement...");
                            refs_nutriscore.add(index_refs_nutriscrore++, rowToParse[NUTRITIONGRADEFR]);
                            Utils.msgResult(String.format("Référencement du nutriscore <%s> OK", rowToParse[NUTRITIONGRADEFR]));
                        }
                        break;

                    case INGREDIENTS:
                        String[] splittedIngredient = rowToParse[INGREDIENTS].split(",");
                        for (int j = 0; j < splittedIngredient.length; j++) {
                            Utils.msgInfo(String.format("Recherche de l'ingrédient <%s> dans la liste dédiée...", splittedIngredient[j]));
                            boolean ingredient_exist = false;
                            for (String ingredient : refs_ingredients) {
                                if (ingredient.contentEquals(splittedIngredient[j])) {
                                    ingredient_exist = true;
                                    break;
                                }
                            }
                            if (ingredient_exist)
                                Utils.msgResult(String.format("L'ingrédient %s est déjà référencée dans la liste dédiée", splittedIngredient[j]));
                            else {
                                Utils.msgInfo("Ingrédient inexistant : début du référencement...");
                                refs_ingredients.add(index_refs_ingredients++, splittedIngredient[j]);
                                Utils.msgResult(String.format("Référencement de l'ingrédient <%s> OK", splittedIngredient[j]));
                            }
                        }
                        break;

                    case ALLERGENES:
                        String[] splittedAllergenes = rowToParse[ALLERGENES].split(",");
                        for (int j = 0; j < splittedAllergenes.length; j++) {
                            Utils.msgInfo(String.format("Recherche de l'allergène <%s> dans la liste dédiée...", splittedAllergenes[j]));
                            boolean allergene_exist = false;
                            for (String ingredient : refs_allergenes) {
                                if (ingredient.contentEquals(splittedAllergenes[j])) {
                                    allergene_exist = true;
                                    break;
                                }
                            }
                            if (allergene_exist)
                                Utils.msgResult(String.format("L'allergène %s est déjà référencée dans la liste dédiée", splittedAllergenes[j]));
                            else {
                                Utils.msgInfo("Allergène inexistant : début du référencement...");
                                refs_allergenes.add(index_refs_allergenes++, splittedAllergenes[j]);
                                Utils.msgResult(String.format("Référencement de l'allergènes <%s> OK", splittedAllergenes[j]));
                            }
                        }
                        break;
                    case ADDITIFS:
                        String[] splittedAdditifs = rowToParse[ADDITIFS].split(",");
                        for (int j = 0; j < splittedAdditifs.length; j++) {
                            Utils.msgInfo(String.format("Recherche de l'additif <%s> dans la liste dédiée...", splittedAdditifs[j]));
                            boolean additif_exist = false;
                            for (String additif : refs_additifs) {
                                if (additif.contentEquals(splittedAdditifs[j])) {
                                    additif_exist = true;
                                    break;
                                }
                            }
                            if (additif_exist) {
                                Utils.msgResult(String.format("L'additif %s est déjà référencée dans la liste dédiée", splittedAdditifs[j]));
                                j--;
                            } else {
                                Utils.msgInfo("Additif inexistant : début du référencement...");
                                refs_allergenes.add(index_refs_additifs++, splittedAdditifs[j]);
                                Utils.msgResult(String.format("Référencement de l'additif <%s> OK", splittedAdditifs[j]));
                            }
                        }

                        break;


                }
            }
        }
        Utils.msgResult(String.format("\n ==> Parsing du fichier %s terminé avec succès", Cleaner.CSV_FILE_RELATIVE_PATH));
        Utils.msgResult("Affichage de la liste des catégories :");
        int i = 0;
        for (String categorie : refs_categories) {
            System.out.println(String.format("%d: %s", i, refs_categories.get(i)));
            i++;
        }

        Utils.msgResult("Affichage de la liste des marques :");
        i = 0;
        for (String marque : refs_marques) {
            System.out.println(String.format("%d: %s", i, refs_marques.get(i)));
            i++;
        }

        Utils.msgResult("Affichage de la liste des produits :");
        i = 0;
        for (String produit : refs_produits) {
            System.out.println(String.format("%d: %s", i, refs_produits.get(i)));
            i++;
        }

        Utils.msgResult("Affichage de la liste des nutriscores :");
        i = 0;
        for (String nutriscore : refs_nutriscore) {
            System.out.println(String.format("%d: %s", i, refs_nutriscore.get(i)));
            i++;
        }

        Utils.msgResult("Affichage de la liste des ingrédients :");
        i = 0;
        for (String ingredient : refs_ingredients) {
            System.out.println(String.format("%d: %s", i, refs_ingredients.get(i)));
            i++;
        }

        Utils.msgResult("Affichage de la liste des allergènes :");
        i = 0;
        for (String allergène : refs_allergenes) {
            System.out.println(String.format("%d: %s", i, refs_allergenes.get(i)));
            i++;
        }

        Utils.msgResult("Affichage de la liste des additifs :");
        i = 0;
        for (String additif : refs_additifs) {
            System.out.println(String.format("%d: %s", i, refs_additifs.get(i)));
            i++;
        }


        //            Categorie categorie = creationInstanceCategorie(colIndex, categories_members, row[CATEGORIE]);
        //            em.persist(categorie);
        //
        //            // AJOUT DE LA MARQUE
        //            Marque marque = creationInstanceMarque(colIndex, marque_membres, row[MARQUE]);
        //            em.persist(marque);
        //
        //            // AJOUT DU NUTRISCORE
        //            Nutriscore nutriscore = creationInstanceNutriscore(colIndex, nutriscores_members, row[NUTRITIONGRADEFR]);
        //            em.persist(nutriscore);
        //
        //
        //            // AJOUT DE LA LISTE INGREDIENT
        //            Set<Ingredient> listSetIngredients = creationInstanceIngredient(colIndex, ingredients_membres, row[INGREDIENTS].split(","));
        //            listSetIngredients.forEach(em::persist);
        //
        //            // AJOUT DES ALLERGENES
        //            Set<Allergene> allergeneSet = creationInstanceAllergene(colIndex, allergenes_members, row[ALLERGENES].split(","));
        //            allergeneSet.forEach(em::persist);
        //
        //            // AJOUT DES ADDITIFS
        //            Set<Additif> additifSet = creationInstanceAdditif(colIndex, additifs_members, row[ADDITIFS].split(","));
        //            additifSet.forEach(em::persist);
        //
        //            // AJOUT DES PRODUITS AVEC LEUR JOINTURE
        //            Produit produit = creationInstanceProduit(colIndex, produits_members, row[PRODUIT]);
        //
        //            produit.setCategorie(categorie);
        //
        //            categorie.getProduits().add(produit);
        //
        //            produit.setMarque(marque);
        //
        //            marque.getProduits().add(produit);
        //
        //            produit.setNutriscore(nutriscore);
        //
        //            nutriscore.getProduits().add(produit);
        //
        //            produit.setIngredients(listSetIngredients);
        //            listSetIngredients.forEach(ingredient -> ingredient.getProduits().add(produit));
        //
        //            produit.setAllergenes(allergeneSet);
        //            allergeneSet.forEach(allergene -> allergene.getProduits().add(produit));
        //
        //            produit.setAdditifs(additifSet);
        //            additifSet.forEach(additif -> additif.getProduits().add(produit));
        //
        //            // PERSISTANCE DES DONNEES DE LA TABLE PRODUIT
        //            em.persist(produit);
        //        } em.getTransaction().commit();
        //        // Fin de la persistence avec transaction
        //
        //    } catch(
        //    IllegalStateException e)
        //
        //    {
        //        LOG.error(e.getMessage());
        //        throw new RuntimeException(e);
        //    }

    }

//    /**
//     * @param iteration       on utilise l'iteration comme une clé pour préserver
//     *                        l'intégrité des données traîtées à chaque iteration
//     * @param hashMapAdditifs Le hashMap sert à conserver les pairs clés-valeurs
//     *                        qui vont servir à créer les instances d'objets
//     * @return La méthode retourne l'instance d'objet Additif
//     */
//    public static Set<Additif> creationInstanceAdditif(int iteration, HashMap<Integer, String> hashMapAdditifs, String[] colContent) {
//        Set<Additif> additifSet = new HashSet<>();
//        Additif additif = new Additif();
//        for (String additi : colContent) {
//            if (!hashMapAdditifs.containsValue(additif)) {
//                hashMapAdditifs.put(iteration, additi);
//                additif.setNom_additif(hashMapAdditifs.get(iteration));
//                additifSet.add(additif);
//            }
//        }
//        return additifSet;
//    }
//
//    /**
//     * @param iteration
//     * @param allergenes
//     * @param colContent
//     * @return
//     */
//    public static Set<Allergene> creationInstanceAllergene(int iteration, HashMap<Integer, String> allergenes, String[] colContent) {
//        Set<Allergene> allergeneSet = new HashSet<>();
//        Allergene allergene = new Allergene();
//        for (String allerg : colContent) {
//            if (!allergenes.containsValue(allerg)) {
//                allergenes.put(iteration, allerg);
//                allergene.setNom_allergene(allergenes.get(iteration));
//                allergeneSet.add(allergene);
//            }
//        }
//        return allergeneSet;
//    }
//
//    /**
//     * @param iteration
//     * @param ingredients
//     * @param lineSplit
//     * @return
//     */
//    public static Set<Ingredient> creationInstanceIngredient(int iteration, HashMap<Integer, String> ingredients, String[] lineSplit) {
//        Set<Ingredient> setOfIngredients = new HashSet<>();
//        Ingredient ingredient = new Ingredient();
//        for (String ingdt : lineSplit) {
//            if (!ingredients.containsValue(ingdt)) {
//                ingredients.put(iteration, ingdt);
//                ingredient.setNom_ingredient(ingredients.get(iteration));
//                setOfIngredients.add(ingredient);
//            } else {
//                ingredient.setNom_ingredient(ingdt);
//                setOfIngredients.add(ingredient);
//            }
//        }
//        return setOfIngredients;
//    }
//
//    /**
//     * @param iteration
//     * @param hashMapNutriscores
//     * @param colContent
//     * @return
//     */
//    public static Nutriscore creationInstanceNutriscore(int iteration, HashMap<Integer, String> hashMapNutriscores, String colContent) {
//        Nutriscore nutriscore = new Nutriscore();
//
//        if (!hashMapNutriscores.containsValue(colContent)) {
//            hashMapNutriscores.put(iteration, colContent);
//            // candidate = hashMapNutriscores.get(iteration);
//            nutriscore.setValeurScore(colContent.charAt(0));
//        }
//        return nutriscore;
//    }
//
//    /**
//     * @param iteration
//     * @param hashMapMarques
//     * @param colContent
//     * @return
//     */
//    public static Marque creationInstanceMarque(int iteration, HashMap<Integer, String> hashMapMarques, String colContent) {
//        Marque marque = new Marque();
////        String candidate;
//        if (!hashMapMarques.containsValue(colContent)) {
//            hashMapMarques.put(iteration, colContent);
////            candidate = hashMapMarques.get(iteration);
//            marque.setNom_marque(colContent);
//        }
//        return marque;
//    }
//
//    /**
//     * @param iteration
//     * @param hashMapProduits
//     * @param colContent
//     * @return
//     */
//    public static Produit creationInstanceProduit(int iteration, HashMap<Integer, String> hashMapProduits, String colContent) {
//        Produit produit = new Produit();
//        // String candidate;
//        if (!hashMapProduits.containsValue(colContent)) {
//            hashMapProduits.put(iteration, colContent);
//            // candidate = hashMapProduits.get(iteration);
////
////        } else {
////            candidate = colContent;
////            produit.setNom_produit(candidate);
//        }
//        produit.setNom_produit(colContent);
//        return produit;
//    }
//
//    /**
//     * @param iteration
//     * @param hashMapCategorie
//     * @param colContent
//     * @return
//     */
//    public static Categorie creationInstanceCategorie(int iteration, HashMap<Integer, String> hashMapCategorie, String colContent) {
//        //Instanciation de l'objet
//        Categorie categorie = new Categorie();
//        //String candidate;
//        //Si l'objet n'a pas déjà été traité, on l'insère dans le hashMap
//        if (!hashMapCategorie.containsValue(colContent)) {
//            hashMapCategorie.put(iteration, colContent);
//            //candidate = hashMapCategorie.get(iteration);
//            categorie.setNom_categorie(colContent);
//        }
//        return categorie;
//    }
//
//    public static void mapRow(int rowNbr, HashMap<Integer, String> hashMap, String rowContent) {
//        switch (rowNbr) {
//            case CATEGORIE:
//                if (!hashMap.containsValue(rowContent)) {
//                    hashMap.put(rowNbr, rowContent);
//                    new Categorie(rowContent);
//                    //candidate = hashMapCategorie.get(iteration);
//                    categorie.setNom_categorie(colContent);
//                }
//                Categorie categorie = new Categorie()
//                //String candidate;
//                //Si l'objet n'a pas déjà été traité, on l'insère dans le hashMap
//                if (!hashMapCategorie.containsValue(colContent)) {
//                    hashMapCategorie.put(iteration, colContent);
//                    //candidate = hashMapCategorie.get(iteration);
//                    categorie.setNom_categorie(colContent);
//                    break;
//                    case :
//                        break;
//                    case :
//                        break;
//                    case :
//                        break;
//
//                }
}
