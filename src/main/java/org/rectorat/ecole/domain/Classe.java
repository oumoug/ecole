package org.rectorat.ecole.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "classe")
public class Classe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom_class")
    private String nomClass;

    @ManyToMany
    @JoinTable(name = "classe_matiere_etudier",
               joinColumns = @JoinColumn(name="classes_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="matiere_etudiers_id", referencedColumnName="id"))
    private Set<Matiere> matiereEtudiers = new HashSet<>();

    @ManyToMany(mappedBy = "classeEnseignes")
    @JsonIgnore
    private Set<Professeur> professeurClasses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomClass() {
        return nomClass;
    }

    public Classe nomClass(String nomClass) {
        this.nomClass = nomClass;
        return this;
    }

    public void setNomClass(String nomClass) {
        this.nomClass = nomClass;
    }

    public Set<Matiere> getMatiereEtudiers() {
        return matiereEtudiers;
    }

    public Classe matiereEtudiers(Set<Matiere> matieres) {
        this.matiereEtudiers = matieres;
        return this;
    }

    public Classe addMatiereEtudier(Matiere matiere) {
        this.matiereEtudiers.add(matiere);
        matiere.getMatiereEleves().add(this);
        return this;
    }

    public Classe removeMatiereEtudier(Matiere matiere) {
        this.matiereEtudiers.remove(matiere);
        matiere.getMatiereEleves().remove(this);
        return this;
    }

    public void setMatiereEtudiers(Set<Matiere> matieres) {
        this.matiereEtudiers = matieres;
    }

    public Set<Professeur> getProfesseurClasses() {
        return professeurClasses;
    }

    public Classe professeurClasses(Set<Professeur> professeurs) {
        this.professeurClasses = professeurs;
        return this;
    }

    public Classe addProfesseurClass(Professeur professeur) {
        this.professeurClasses.add(professeur);
        professeur.getClasseEnseignes().add(this);
        return this;
    }

    public Classe removeProfesseurClass(Professeur professeur) {
        this.professeurClasses.remove(professeur);
        professeur.getClasseEnseignes().remove(this);
        return this;
    }

    public void setProfesseurClasses(Set<Professeur> professeurs) {
        this.professeurClasses = professeurs;
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
        Classe classe = (Classe) o;
        if (classe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Classe{" +
            "id=" + getId() +
            ", nomClass='" + getNomClass() + "'" +
            "}";
    }
}
