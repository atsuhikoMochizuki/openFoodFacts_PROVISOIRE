package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nom_marque;

    @OneToMany(mappedBy = "marque")
    private Set<Produit> produits;

    public Marque() {

    }

    public Marque(Integer id, String nom_marque, Set<Produit> produits) {
        this.id = id;
        this.nom_marque = nom_marque;
        this.produits = produits;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom_marque() {
        return nom_marque;
    }

    public void setNom_marque(String nom_marque) {
        this.nom_marque = nom_marque;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Marque{");
        sb.append("id=").append(id);
        sb.append(", nom_marque='").append(nom_marque).append('\'');
        sb.append(", produits=").append(produits);
        sb.append('}');
        return sb.toString();
    }
}
