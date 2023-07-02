package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Cacheable
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id_produit")
    private Integer id_produit;
    private String nom_produit;

    @ManyToOne
    @JoinColumn(name = "CAT_ID")
    private Categorie categorie;

    @ManyToMany
    @JoinTable(name = "MARQUE_PROD",
            joinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "ID_MARQUE", referencedColumnName = "id_marque")
    )
    private Set<Marque> marques;

    @ManyToOne
    @JoinColumn(name = "NUTRISCORE_ID")
    private Nutriscore nutriscore;

    @ManyToMany
    @JoinTable(name = "ADD_PROD",
            joinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "ID_ADDITIF", referencedColumnName = "id_additif")
    )
    private Set<Additif> additifs;

    @ManyToMany
    @JoinTable(name = "ALLERGENE_PROD",
            joinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "ID_ALLERGENE", referencedColumnName = "id_allergene")
    )
    private Set<Allergene> allergenes;


    @ManyToMany
    @JoinTable(name = "INGREDIENT_PROD",
            joinColumns = @JoinColumn(name = "ID_PRODUIT", referencedColumnName = "id_produit"),
            inverseJoinColumns = @JoinColumn(name = "ID_INGREDIENT", referencedColumnName = "id_ingredient")
    )
    private Set<Ingredient> ingredients;

    {
        additifs = new HashSet<>();
        allergenes = new HashSet<>();
        ingredients = new HashSet<>();
    }


    public Produit() {
    }

    public Produit(String nom_produit) {
        this.nom_produit = nom_produit;
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

//    public void setAdditifs(Set<Additif> additifs) {
//        if (this.additifs != null) {
//            for (Additif additif : this.additifs) {
//                additif.getProduits().remove(this);
//            }
//        }
//        this.additifs = additifs;
//        if (this.additifs != null) {
//            for (Additif additif : this.additifs) {
//                additif.getProduits().add(this);
//            }
//        }
//    }


    public void setAdditifs(Set<Additif> additifs) {
        this.additifs = additifs;
    }

    public void setMarques(Set<Marque> marques) {
        this.marques = marques;
    }

    public Set<Allergene> getAllergenes() {
        return allergenes;
    }

    public void setNutriscore(Nutriscore nutriscore) {
        this.nutriscore = nutriscore;
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


//    public Nutriscore getNutriscore() {
//        return nutriscore;
//    }
//
//    public void setNutriscore(Nutriscore nutriscore) {
//        if (this.nutriscore != null) {
//            this.nutriscore.getProduits().remove(this);
//        }
//        this.nutriscore = nutriscore;
//        if (this.nutriscore != null) {
//            this.nutriscore.getProduits().add(this);
//        }
//    }

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

//    @Override
//    public String toString() {
//        final StringBuffer sb = new StringBuffer("Produit{");
//        sb.append("id_produit=").append(id_produit);
//        sb.append(", nom_produit='").append(nom_produit).append('\'');
//        sb.append(", categorie=").append(categorie);
//        sb.append(", additifs=").append(additifs);
//        sb.append(", allergenes=").append(allergenes);
//        sb.append(", marque=").append(marque);
//        sb.append(", nutriscore=").append(nutriscore);
//        sb.append(", ingredients=").append(ingredients);
//        sb.append('}');
//        return sb.toString();
//    }
}
