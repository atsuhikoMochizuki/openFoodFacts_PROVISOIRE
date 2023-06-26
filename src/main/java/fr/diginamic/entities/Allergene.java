package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Allergene {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_allergene;
    private String nom_allergene;
    @ManyToMany
    @JoinTable(name = "ALLERGENE_PROD",
            joinColumns = @JoinColumn(name = "ID_ALLERGENE", referencedColumnName = "id_allergene"),
            inverseJoinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit")
    )
    private Set<Produit> produits;

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
