package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Additif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id_additif;
    @Column(length = 1024)
    private String nom_additif;

    @ManyToMany
    @JoinTable(name = "ADD_PROD",
            joinColumns = @JoinColumn(name = "ID_ADDITIF", referencedColumnName = "id_additif"),
            inverseJoinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit")
    )
    private Set<Produit> produits;

    public Additif() {
    }

    public Additif(Integer id_additif, String nom_additif) {
        this.id_additif = id_additif;
        this.nom_additif = nom_additif;
    }

    public Integer getId_additif() {
        return id_additif;
    }

    public void setId_additif(Integer id_additif) {
        this.id_additif = id_additif;
    }

    public String getNom_additif() {
        return nom_additif;
    }

    public void setNom_additif(String nom_additif) {
        this.nom_additif = nom_additif;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
        if (this.produits != null) {
            for (Produit produit : this.produits) {
                produit.getAdditifs().remove(this);
            }
        }
        this.produits = produits;
        if (this.produits != null) {
            for (Produit produit : this.produits) {
                produit.getAdditifs().add(this);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Additif{");
        sb.append("id_additif=").append(id_additif);
        sb.append(", nom_additif='").append(nom_additif).append('\'');
        sb.append(", produits=").append(produits);
        sb.append('}');
        return sb.toString();
    }
}
