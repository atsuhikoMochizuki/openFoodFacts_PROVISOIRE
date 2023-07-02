package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Cacheable
public class Produit {
    // Attributs propres aux produits
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_produit;
    private String nom_produit;
    private double energiePour100g;

    //Attributs relations 1-n
    @ManyToOne
    @JoinColumn(name = "CAT_ID")
    private Categorie categorie;
    @ManyToOne
    @JoinColumn(name = "NUTRISCORE_ID")
    private Nutriscore nutriscore;

    //Attributs relations n-n
    @ManyToMany
    @JoinTable(name = "JOIN_TABLE_MARQUE_PRODUIT",
            joinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "ID_MARQUE", referencedColumnName = "id_marque")
    )
    private Set<Marque> marques;

    @ManyToMany
    @JoinTable(name = "JOIN_TABLE_ALLERGENE_PRODUIT",
            joinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "ID_ALLERGENE", referencedColumnName = "id_allergene")
    )
    private Set<Allergene> allergenes;

    @ManyToMany
    @JoinTable(name = "JOIN_TABLE_ADDITIF_PRODUIT",
            joinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "ID_ADDITIF", referencedColumnName = "id_additif")
    )
    private Set<Additif> additifs;

    @ManyToMany
    @JoinTable(name = "JOIN_TABLE_INGREDIENT_PRODUIT",
            joinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "ID_INGREDIENT", referencedColumnName = "id_ingredient")
    )
    private Set<Ingredient> ingredients;

    //Constructeurs
    public Produit() {
    }

    public Produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    //Getters

    public Integer getId_produit() {
        return id_produit;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public double getEnergiePour100g() {
        return energiePour100g;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public Nutriscore getNutriscore() {
        return nutriscore;
    }

    public Set<Marque> getMarques() {
        return marques;
    }

    public Set<Allergene> getAllergenes() {
        return allergenes;
    }

    public Set<Additif> getAdditifs() {
        return additifs;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    //Setters

    public void setId_produit(Integer id_produit) {
        this.id_produit = id_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public void setEnergiePour100g(double energiePour100g) {
        this.energiePour100g = energiePour100g;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public void setNutriscore(Nutriscore nutriscore) {
        this.nutriscore = nutriscore;
    }

    public void setMarques(Set<Marque> marques) {
        this.marques = marques;
    }

    public void setAllergenes(Set<Allergene> allergenes) {
        this.allergenes = allergenes;
    }

    public void setAdditifs(Set<Additif> additifs) {
        this.additifs = additifs;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    //ToString()
    @Override
    public String toString() {
        return "Produit{" +
                "id_produit=" + id_produit +
                ", nom_produit='" + nom_produit + '\'' +
                ", energiePour100g=" + energiePour100g +
                ", categorie=" + categorie +
                ", nutriscore=" + nutriscore +
                ", marques=" + marques +
                ", allergenes=" + allergenes +
                ", additifs=" + additifs +
                ", ingredients=" + ingredients +
                '}';
    }
}
