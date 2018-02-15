package org.rectorat.ecole.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Matiere.
 */
@Entity
@Table(name = "matiere")
public class Matiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom_matiere", nullable = false)
    private String nomMatiere;

    @ManyToMany
    @JoinTable(name = "matiere_matiere_prof",
               joinColumns = @JoinColumn(name="matieres_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="matiere_profs_id", referencedColumnName="id"))
    private Set<Professeur> matiereProfs = new HashSet<>();

    @ManyToMany(mappedBy = "matiereEtudiers")
    @JsonIgnore
    private Set<Classe> matiereEleves = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public Matiere nomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
        return this;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public Set<Professeur> getMatiereProfs() {
        return matiereProfs;
    }

    public Matiere matiereProfs(Set<Professeur> professeurs) {
        this.matiereProfs = professeurs;
        return this;
    }

    public Matiere addMatiereProf(Professeur professeur) {
        this.matiereProfs.add(professeur);
        professeur.getMatiereEnseignes().add(this);
        return this;
    }

    public Matiere removeMatiereProf(Professeur professeur) {
        this.matiereProfs.remove(professeur);
        professeur.getMatiereEnseignes().remove(this);
        return this;
    }

    public void setMatiereProfs(Set<Professeur> professeurs) {
        this.matiereProfs = professeurs;
    }

    public Set<Classe> getMatiereEleves() {
        return matiereEleves;
    }

    public Matiere matiereEleves(Set<Classe> classes) {
        this.matiereEleves = classes;
        return this;
    }

    public Matiere addMatiereEleve(Classe classe) {
        this.matiereEleves.add(classe);
        classe.getMatiereEtudiers().add(this);
        return this;
    }

    public Matiere removeMatiereEleve(Classe classe) {
        this.matiereEleves.remove(classe);
        classe.getMatiereEtudiers().remove(this);
        return this;
    }

    public void setMatiereEleves(Set<Classe> classes) {
        this.matiereEleves = classes;
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
        Matiere matiere = (Matiere) o;
        if (matiere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matiere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Matiere{" +
            "id=" + getId() +
            ", nomMatiere='" + getNomMatiere() + "'" +
            "}";
    }
}
