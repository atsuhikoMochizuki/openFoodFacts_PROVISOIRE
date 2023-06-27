package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table
public class Nutriscore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_nutriscore;
    private char valeurScore;

    @OneToMany(mappedBy = "nutriscore")
    private Set<Produit> produits;

    public Nutriscore() {
    }

    public Nutriscore(Integer id_nutriscore, char valeurScore) {
        this.id_nutriscore = id_nutriscore;
        this.valeurScore = valeurScore;
    }

    public Integer getId_nutriscore() {
        return id_nutriscore;
    }

    public void setId_nutriscore(Integer id) {
        this.id_nutriscore = id;
    }

    public char getValeurScore() {
        return valeurScore;
    }

    public void setValeurScore(char valeurScore) {
        this.valeurScore = valeurScore;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Nutriscore{");
        sb.append("id=").append(id_nutriscore);
        sb.append(", valeurScore=").append(valeurScore);
        sb.append(", produits=").append(produits);
        sb.append('}');
        return sb.toString();
    }
}
