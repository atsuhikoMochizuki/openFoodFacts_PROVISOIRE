package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAT_ID")
    private Integer id_categorie;

    private String nom_categorie;

    @OneToMany(mappedBy = "categorie")
    private Set<Produit> produits;

    {
        produits = new HashSet<>();
    }

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

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
        if (this.produits != null) {
            this.produits.remove(this);
        }
        this.produits = produits;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Categorie{");
        sb.append("id_categorie=").append(id_categorie);
        sb.append(", nom_categorie='").append(nom_categorie).append('\'');
        sb.append(", produits=").append(produits);
        sb.append('}');
        return sb.toString();
    }
}
