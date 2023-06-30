package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Cacheable
public class Allergene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_allergene")
    private Integer id_allergene;
    @Column(length = 555)
    private String nom_allergene;
    @ManyToMany(mappedBy = "allergenes")
    private Set<Produit> produits;

    {
        produits = new HashSet<>();
    }

    public Allergene() {
    }

    public Allergene(Integer id_allergene, String nom_allergene) {
        this.id_allergene = id_allergene;
        this.nom_allergene = nom_allergene;
    }

    public Integer getId_allergene() {
        return id_allergene;
    }

    public void setId_allergene(Integer id_allergene) {
        this.id_allergene = id_allergene;
    }

    public String getNom_allergene() {
        return nom_allergene;
    }

    public void setNom_allergene(String nom_allergene) {
        this.nom_allergene = nom_allergene;
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
        final StringBuilder sb = new StringBuilder("Allergene{");
        sb.append("id_allergene=").append(id_allergene);
        sb.append(", nom_allergene='").append(nom_allergene).append('\'');
        sb.append(", produits=").append(produits);
        sb.append('}');
        return sb.toString();
    }
}
