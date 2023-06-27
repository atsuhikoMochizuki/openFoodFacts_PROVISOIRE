package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Nutriscore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private char valeurScore;

    @OneToMany(mappedBy = "nutriscore")
    private Set<Produit> produits;

    public Nutriscore() {
    }

    public Nutriscore(Integer id, char valeurScore) {
        this.id = id;
        this.valeurScore = valeurScore;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public char getValeurScore() {
        return valeurScore;
    }

    public void setValeurScore(char valeurScore) {
        this.valeurScore = valeurScore;
    }

    public Set<Produit> getProduits() {
        return produits;
    }

    public void setProduits(Set<Produit> produits) {
        if (this.produits != null) {
            for (Produit produit : this.produits) {
                produit.setNutriscore(this);
            }
        }
        this.produits = produits;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Nutriscore{");
        sb.append("id=").append(id);
        sb.append(", valeurScore=").append(valeurScore);
        sb.append(", produits=").append(produits);
        sb.append('}');
        return sb.toString();
    }
}
