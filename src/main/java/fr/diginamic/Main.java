package fr.diginamic;


import fr.diginamic.bll.parsingCsv.CsvFile;
import fr.diginamic.bll.parsingCsv.CsvFileHelper;
import fr.diginamic.bll.parsingCsv.I_CsvFile;
import fr.diginamic.ihm.Ihm;
import fr.diginamic.mochizukiTools.Params;
import fr.diginamic.mochizukiTools.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final String OPEN_FOOD_FACTS_CSV_FILE = "src/main/resources/open-food-facts.csv";
    private static File file;

    public static void main(String[] args) throws UnsupportedLookAndFeelException, IOException {
        Utils.clearConsole();
        Params.welcomePrompt();

        Utils.msgDebug("Parsing du fichier csv");
        file = CsvFileHelper.getResource(OPEN_FOOD_FACTS_CSV_FILE);
        final I_CsvFile ICsvFile = new CsvFile(file, '|');
        final List<Map<String, String>> mappedData = ICsvFile.getMappedData();
        for (Map<String, String> oneMappedData : mappedData) {
            System.out.println(oneMappedData.toString());
        }
        Utils.msgResult(String.format("Parsing du fichier CSV <%s> OK", OPEN_FOOD_FACTS_CSV_FILE));
        Utils.msgInfo("ATTENTION : méthode de nettoyage à implémenter");

        Utils.msgInfo("Début du mapping JPA de la base de données");
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("IBOOF-JPA");
             EntityManager em = emf.createEntityManager();
        ) {
            Utils.msgInfo("Mapping de la base de données OK");
            Utils.msgInfo("Connection à la base de données OK");
        } catch (IllegalStateException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }

        Utils.msgInfo("Lancement de la boucle évenementielle");
        Ihm.display();
    }
}