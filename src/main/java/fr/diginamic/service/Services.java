package fr.diginamic.service;

import fr.diginamic.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

public class Services implements Iservices {

    private Produit produit;
    private Marque marque;
    private Categorie categorie;
    private Ingredient ingredient;
    private Allergene allergene;
    private Additif additif;

    @PersistenceContext
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("IBOOF-JPA");
    private final EntityManager em = emf.createEntityManager();


    @Transactional
    @Override
    public void service1() {
        List resultList = em.createNativeQuery(
                        "SELECT prod.nom_produit FROM Produit AS prod, " +
                                "Marque AS marq, " +
                                "NutriScore AS nutri " +
                                "WHERE marq.nom_marque = ? " +
                                "AND nutri.valeurScore = \"a\"" +
                                "LIMIT 10")
                .setParameter(1, marque.getNom_marque())
                .getResultList();
        resultList.forEach(System.out::println);

    }

    @Transactional
    @Override
    public void service2() {
        List resultList = em.createNativeQuery(
                        "SELECT prod.nom_produit FROM Produit AS prod, " +
                                "Categorie AS categ, " +
                                "NutriScore AS nutri " +
                                "WHERE categ.nom_categorie = ? " +
                                "AND nutri.valeurScore = \"a\"" +
                                "LIMIT 10")
                .setParameter(1, categorie.getNom_categorie())
                .getResultList();
        resultList.forEach(System.out::println);
    }

    @Transactional
    @Override
    public void service3() {
        List resultList = em.createNativeQuery(
                        "SELECT prod.nom_produit FROM Produit AS prod, " +
                                "Categorie AS categ, " +
                                "Marque AS marq, " +
                                "NutriScore AS nutri " +
                                "WHERE categ.nom_categorie = ? " +
                                "AND mar.nom_marque = ? " +
                                "AND nutri.valeurScore = \"a\"" +
                                "LIMIT 10")
                .setParameter(1, categorie.getNom_categorie())
                .setParameter(2, marque.getNom_marque())
                .getResultList();
        resultList.forEach(System.out::println);

    }

    @Transactional
    @Override
    public void service4() {
        List resultList = em.createNativeQuery("SELECT ingt, COUNT(prod) FROM Produit AS prod" +
                ", Ingredient AS ingt " +
                "WHERE ingdt" +
                "GROUP BY prod " +
                "ORDER BY DESC" +
                "LIMIT 10").getResultList();
        resultList.forEach(System.out::println);
    }

    @Transactional
    @Override
    public void service5() {
        List resultList = em.createNativeQuery("SELECT allerg, COUNT(prod) FROM Produit AS prod" +
                ", Allergene AS allerg" +
                "WHERE allerg" +
                "GROUP BY prod " +
                "ORDER BY DESC" +
                "LIMIT 10").getResultList();
        resultList.forEach(System.out::println);
    }

    @Transactional
    @Override
    public void service6() {
        List resultList = em.createNativeQuery("SELECT additi, COUNT(prod) FROM Produit AS prod" +
                ", Additif AS additi" +
                "WHERE additi" +
                "GROUP BY prod " +
                "ORDER BY DESC" +
                "LIMIT 10").getResultList();
        resultList.forEach(System.out::println);
    }

}
