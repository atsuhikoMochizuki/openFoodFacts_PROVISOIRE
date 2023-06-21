package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Categorie")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom_categorie")
    private String nom_categorie;

    @OneToMany(mappedBy = "categorie")
    private Set<Produit> produits;

    public Categorie() {
    }

    public Categorie(Integer id, String nom_categorie) {
        this.id = id;
        this.nom_categorie = nom_categorie;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
                "id=" + id +
                ", nom_categorie='" + nom_categorie + '\'' +
                '}';
    }
}
