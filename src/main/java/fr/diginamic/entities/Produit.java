package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_produit;
    private String nom_produit;

    private double energiePour100g;
    private double graisse100g;
    private double sucres100g;
    private double fibres100g;
    private double proteines100g;
    private double sel100g;
    private double vitA100g;
    private double vitD100g;
    private double vitE100g;
    private double vitK100g;
    private double vitC100g;
    private double vitB1100g;
    private double vitB2100g;
    private double vitPP100g;
    private double vitB6100g;
    private double vitB9100g;
    private double vitB12100g;
    private double calcium100g;
    private double magnesium100g;
    private double iron100g;
    private double fer100g;
    private double betaCarotene100g;
    private boolean presenceHuilePalme;

    @ManyToOne
    @JoinColumn(name = "CAT_ID")
    private Categorie categorie;

    @ManyToMany(mappedBy = "produits")
    private Set<Additif> additifs;

    @ManyToMany(mappedBy = "produits")
    private Set<Allergene> allergenes;

    @ManyToOne
    @JoinColumn(name = "MARQUE_ID")
    private Marque marque;

    @ManyToOne
    @JoinColumn(name = "NUTRISCORE_ID")
    private Nutriscore nutriscore;

    @ManyToMany(mappedBy = "produits")
    private Set<Ingredient> ingredients;


    public Produit() {
    }

    public Integer getId_produit() {
        return id_produit;
    }

    public void setId_produit(Integer id) {
        this.id_produit = id;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public double getEnergiePour100g() {
        return energiePour100g;
    }

    public void setEnergiePour100g(double energiePour100g) {
        this.energiePour100g = energiePour100g;
    }

    public double getGraisse100g() {
        return graisse100g;
    }

    public void setGraisse100g(double graisse100g) {
        this.graisse100g = graisse100g;
    }

    public double getSucres100g() {
        return sucres100g;
    }

    public void setSucres100g(double sucres100g) {
        this.sucres100g = sucres100g;
    }

    public double getFibres100g() {
        return fibres100g;
    }

    public void setFibres100g(double fibres100g) {
        this.fibres100g = fibres100g;
    }

    public double getProteines100g() {
        return proteines100g;
    }

    public void setProteines100g(double proteines100g) {
        this.proteines100g = proteines100g;
    }

    public double getSel100g() {
        return sel100g;
    }

    public void setSel100g(double sel100g) {
        this.sel100g = sel100g;
    }

    public double getVitA100g() {
        return vitA100g;
    }

    public void setVitA100g(double vitA100g) {
        this.vitA100g = vitA100g;
    }

    public double getVitD100g() {
        return vitD100g;
    }

    public void setVitD100g(double vitD100g) {
        this.vitD100g = vitD100g;
    }

    public double getVitE100g() {
        return vitE100g;
    }

    public void setVitE100g(double vitE100g) {
        this.vitE100g = vitE100g;
    }

    public double getVitK100g() {
        return vitK100g;
    }

    public void setVitK100g(double vitK100g) {
        this.vitK100g = vitK100g;
    }

    public double getVitC100g() {
        return vitC100g;
    }

    public void setVitC100g(double vitC100g) {
        this.vitC100g = vitC100g;
    }

    public double getVitB1100g() {
        return vitB1100g;
    }

    public void setVitB1100g(double vitB1100g) {
        this.vitB1100g = vitB1100g;
    }

    public double getVitB2100g() {
        return vitB2100g;
    }

    public void setVitB2100g(double vitB2100g) {
        this.vitB2100g = vitB2100g;
    }

    public double getVitPP100g() {
        return vitPP100g;
    }

    public void setVitPP100g(double vitPP100g) {
        this.vitPP100g = vitPP100g;
    }

    public double getVitB6100g() {
        return vitB6100g;
    }

    public void setVitB6100g(double vitB6100g) {
        this.vitB6100g = vitB6100g;
    }

    public double getVitB9100g() {
        return vitB9100g;
    }

    public void setVitB9100g(double vitB9100g) {
        this.vitB9100g = vitB9100g;
    }

    public double getVitB12100g() {
        return vitB12100g;
    }

    public void setVitB12100g(double vitB12100g) {
        this.vitB12100g = vitB12100g;
    }

    public double getCalcium100g() {
        return calcium100g;
    }

    public void setCalcium100g(double calcium100g) {
        this.calcium100g = calcium100g;
    }

    public double getMagnesium100g() {
        return magnesium100g;
    }

    public void setMagnesium100g(double magnesium100g) {
        this.magnesium100g = magnesium100g;
    }

    public double getIron100g() {
        return iron100g;
    }

    public void setIron100g(double iron100g) {
        this.iron100g = iron100g;
    }

    public double getFer100g() {
        return fer100g;
    }

    public void setFer100g(double fer100g) {
        this.fer100g = fer100g;
    }

    public double getBetaCarotene100g() {
        return betaCarotene100g;
    }

    public void setBetaCarotene100g(double betaCarotene100g) {
        this.betaCarotene100g = betaCarotene100g;
    }

    public boolean isPresenceHuilePalme() {
        return presenceHuilePalme;
    }

    public void setPresenceHuilePalme(boolean presenceHuilePalme) {
        this.presenceHuilePalme = presenceHuilePalme;
    }


    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        if (this.categorie != null) {
            this.categorie.getProduits().remove(this);
        }
        this.categorie = categorie;
        if (this.categorie != null) {
            this.categorie.getProduits().add(this);
        }
    }

    public Set<Additif> getAdditifs() {
        return additifs;
    }

    public void setAdditifs(Set<Additif> additifs) {
        if (this.additifs != null) {
            for (Additif additif : this.additifs) {
                additif.getProduits().remove(this);
            }
        }
        this.additifs = additifs;
        if (this.additifs != null) {
            for (Additif additif : this.additifs) {
                additif.getProduits().add(this);
            }
        }
    }

    public Set<Allergene> getAllergenes() {
        return allergenes;
    }

    public void setAllergenes(Set<Allergene> allergenes) {
        if (this.allergenes != null) {
            for (Allergene allergene : this.allergenes) {
                allergene.getProduits().remove(this);
            }
        }
        this.allergenes = allergenes;
        if (this.allergenes != null) {
            for (Allergene allergene : this.allergenes) {
                allergene.getProduits().add(this);
            }
        }
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        if (this.marque != null) {
            this.marque.getProduits().remove(this);
        }
        this.marque = marque;
        if (this.marque != null) {
            this.marque.getProduits().add(this);
        }
    }

    public Nutriscore getNutriscore() {
        return nutriscore;
    }

    public void setNutriscore(Nutriscore nutriscore) {
        if (this.nutriscore != null) {
            this.nutriscore.getProduits().remove(this);
        }
        this.nutriscore = nutriscore;
        if (this.nutriscore != null) {
            this.nutriscore.getProduits().add(this);
        }
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        if (this.ingredients != null) {
            for (Ingredient ingredient : this.ingredients) {
                ingredient.getProduits().remove(this);
            }
        }
        this.ingredients = ingredients;
        if (this.ingredients != null) {
            for (Ingredient ingredient : this.ingredients) {
                ingredient.getProduits().add(this);
            }
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Produit{");
        sb.append("id_produit=").append(id_produit);
        sb.append(", nom_produit='").append(nom_produit).append('\'');
        sb.append(", energiePour100g=").append(energiePour100g);
        sb.append(", graisse100g=").append(graisse100g);
        sb.append(", sucres100g=").append(sucres100g);
        sb.append(", fibres100g=").append(fibres100g);
        sb.append(", proteines100g=").append(proteines100g);
        sb.append(", sel100g=").append(sel100g);
        sb.append(", vitA100g=").append(vitA100g);
        sb.append(", vitD100g=").append(vitD100g);
        sb.append(", vitE100g=").append(vitE100g);
        sb.append(", vitK100g=").append(vitK100g);
        sb.append(", vitC100g=").append(vitC100g);
        sb.append(", vitB1100g=").append(vitB1100g);
        sb.append(", vitB2100g=").append(vitB2100g);
        sb.append(", vitPP100g=").append(vitPP100g);
        sb.append(", vitB6100g=").append(vitB6100g);
        sb.append(", vitB9100g=").append(vitB9100g);
        sb.append(", vitB12100g=").append(vitB12100g);
        sb.append(", calcium100g=").append(calcium100g);
        sb.append(", magnesium100g=").append(magnesium100g);
        sb.append(", iron100g=").append(iron100g);
        sb.append(", fer100g=").append(fer100g);
        sb.append(", betaCarotene100g=").append(betaCarotene100g);
        sb.append(", presenceHuilePalme=").append(presenceHuilePalme);
        sb.append(", categorie=").append(categorie);
        sb.append(", additifs=").append(additifs);
        sb.append(", allergenes=").append(allergenes);
        sb.append(", marque=").append(marque);
        sb.append(", nutriscore=").append(nutriscore);
        sb.append(", ingredients=").append(ingredients);
        sb.append('}');
        return sb.toString();
    }
}
