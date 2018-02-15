package org.rectorat.ecole.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Eleve.
 */
@Entity
@Table(name = "eleve")
public class Eleve implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "moyenne")
    private Float moyenne;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adresseEleve;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Classe eleveClasse;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Eleve nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Eleve prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Float getMoyenne() {
        return moyenne;
    }

    public Eleve moyenne(Float moyenne) {
        this.moyenne = moyenne;
        return this;
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }

    public Adresse getAdresseEleve() {
        return adresseEleve;
    }

    public Eleve adresseEleve(Adresse adresse) {
        this.adresseEleve = adresse;
        return this;
    }

    public void setAdresseEleve(Adresse adresse) {
        this.adresseEleve = adresse;
    }

    public Classe getEleveClasse() {
        return eleveClasse;
    }

    public Eleve eleveClasse(Classe classe) {
        this.eleveClasse = classe;
        return this;
    }

    public void setEleveClasse(Classe classe) {
        this.eleveClasse = classe;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Eleve eleve = (Eleve) o;
        if (eleve.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eleve.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Eleve{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", moyenne=" + getMoyenne() +
            "}";
    }
}
