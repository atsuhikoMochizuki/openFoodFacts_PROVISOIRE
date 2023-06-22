package fr.diginamic;


import fr.diginamic.ihm.Ihm;
import fr.diginamic.mochizukiTools.Params;
import fr.diginamic.mochizukiTools.Utils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.ResourceBundle;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws UnsupportedLookAndFeelException, IOException {
        Utils.clearConsole();
        Params.welcomePrompt();
        Ihm.display();
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("IBOOF-JPA");
             EntityManager em = emf.createEntityManager();
        ) {
            Utils.msgInfo("Connection à la base de données OK");
        } catch (IllegalStateException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}