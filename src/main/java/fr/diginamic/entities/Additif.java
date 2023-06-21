package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Additif")
public class Additif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id_additif;
    private String nom_additif;

    @ManyToMany
    @JoinTable(name = "ADD_PROD",
            joinColumns = @JoinColumn(name = "ID_ADDITIF", referencedColumnName = "id_additif"),
            inverseJoinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produits")
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

    @Override
    public String toString() {
        return "Additif{" +
                "id=" + id_additif +
                ", nom_additif='" + nom_additif + '\'' +
                '}';
    }
}
