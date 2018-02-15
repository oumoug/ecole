package org.rectorat.ecole.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Professeur.
 */
@Entity
@Table(name = "professeur")
public class Professeur implements Serializable {

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

    @OneToOne
    @JoinColumn(unique = true)
    private Adresse adresseProfesseur;

    @ManyToMany
    @JoinTable(name = "professeur_classe_enseigne",
               joinColumns = @JoinColumn(name="professeurs_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="classe_enseignes_id", referencedColumnName="id"))
    private Set<Classe> classeEnseignes = new HashSet<>();

    @ManyToMany(mappedBy = "matiereProfs")
    @JsonIgnore
    private Set<Matiere> matiereEnseignes = new HashSet<>();

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

    public Professeur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Professeur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Adresse getAdresseProfesseur() {
        return adresseProfesseur;
    }

    public Professeur adresseProfesseur(Adresse adresse) {
        this.adresseProfesseur = adresse;
        return this;
    }

    public void setAdresseProfesseur(Adresse adresse) {
        this.adresseProfesseur = adresse;
    }

    public Set<Classe> getClasseEnseignes() {
        return classeEnseignes;
    }

    public Professeur classeEnseignes(Set<Classe> classes) {
        this.classeEnseignes = classes;
        return this;
    }

    public Professeur addClasseEnseigne(Classe classe) {
        this.classeEnseignes.add(classe);
        classe.getProfesseurClasses().add(this);
        return this;
    }

    public Professeur removeClasseEnseigne(Classe classe) {
        this.classeEnseignes.remove(classe);
        classe.getProfesseurClasses().remove(this);
        return this;
    }

    public void setClasseEnseignes(Set<Classe> classes) {
        this.classeEnseignes = classes;
    }

    public Set<Matiere> getMatiereEnseignes() {
        return matiereEnseignes;
    }

    public Professeur matiereEnseignes(Set<Matiere> matieres) {
        this.matiereEnseignes = matieres;
        return this;
    }

    public Professeur addMatiereEnseigne(Matiere matiere) {
        this.matiereEnseignes.add(matiere);
        matiere.getMatiereProfs().add(this);
        return this;
    }

    public Professeur removeMatiereEnseigne(Matiere matiere) {
        this.matiereEnseignes.remove(matiere);
        matiere.getMatiereProfs().remove(this);
        return this;
    }

    public void setMatiereEnseignes(Set<Matiere> matieres) {
        this.matiereEnseignes = matieres;
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
        Professeur professeur = (Professeur) o;
        if (professeur.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professeur.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Professeur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            "}";
    }
}
