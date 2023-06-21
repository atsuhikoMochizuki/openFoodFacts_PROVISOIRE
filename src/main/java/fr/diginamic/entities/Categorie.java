package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_categorie;

    private String nom_categorie;

    @OneToMany(mappedBy = "categorie")
    private Set<Produit> produits;

    public Categorie() {
    }

    public Categorie(Integer id_categorie, String nom_categorie) {
        this.id_categorie = id_categorie;
        this.nom_categorie = nom_categorie;
    }

    public Integer getId_categorie() {
        return id_categorie;
    }

    public void setId_categorie(Integer id) {
        this.id_categorie = id;
    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public void setNom_categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id_categorie +
                ", nom_categorie='" + nom_categorie + '\'' +
                '}';
    }
}
