package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARQUE_ID")
    private Integer id_marque;
    @Column(name = "nom_marque")
    private String nom_marque;

    @OneToMany(mappedBy = "marque", cascade = CascadeType.ALL)
    private Set<Produit> produits;

    {
        produits = new HashSet<>();
    }

    public Marque() {

    }

    public Marque(Integer id_marque, String nom_marque, Set<Produit> produits) {
        this.id_marque = id_marque;
        this.nom_marque = nom_marque;
        this.produits = produits;
    }

    public Integer getId_marque() {
        return id_marque;
    }

    public void setId_marque(Integer id) {
        this.id_marque = id;
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
        if (this.produits != null) {
            this.produits.remove(this);
        }
        this.produits = produits;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Marque{");
        sb.append("id=").append(id_marque);
        sb.append(", nom_marque='").append(nom_marque).append('\'');
        sb.append(", produits=").append(produits);
        sb.append('}');
        return sb.toString();
    }
}
