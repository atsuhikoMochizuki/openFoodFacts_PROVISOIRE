package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_ingredient;
    private String nom_ingredient;

    @ManyToMany
    @JoinTable(name = "INGREDIENT_PROD",
            joinColumns = @JoinColumn(name = "ID_INGREDIENT", referencedColumnName = "id_ingredient"),
            inverseJoinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit")
    )
    private Set<Produit> produits;


    public Ingredient() {
    }

    public Ingredient(Integer id_ingredient, String nom_ingredient) {
        this.id_ingredient = id_ingredient;
        this.nom_ingredient = nom_ingredient;
    }

    public Integer getId_ingredient() {
        return id_ingredient;
    }

    public void setId_ingredient(Integer id) {
        this.id_ingredient = id;
    }

    public String getNom_ingredient() {
        return nom_ingredient;
    }

    public void setNom_ingredient(String nom_ingredient) {
        this.nom_ingredient = nom_ingredient;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id_ingredient +
                ", nom_ingredient='" + nom_ingredient + '\'' +
                '}';
    }
}
