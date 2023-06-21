package fr.diginamic.entities;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Nutriscore")
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

    @Override
    public String toString() {
        return "Nutriscore{" +
                "id=" + id +
                ", valeurScore=" + valeurScore +
                '}';
    }
}
