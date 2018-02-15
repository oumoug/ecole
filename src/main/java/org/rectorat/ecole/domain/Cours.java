package org.rectorat.ecole.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Cours.
 */
@Entity
@Table(name = "cours")
public class Cours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_date")
    private LocalDate date;

    @Column(name = "heure")
    private Instant heure;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Professeur coursProfesseur;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Classe coursClass;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Salle salleCours;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Cours date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getHeure() {
        return heure;
    }

    public Cours heure(Instant heure) {
        this.heure = heure;
        return this;
    }

    public void setHeure(Instant heure) {
        this.heure = heure;
    }

    public Professeur getCoursProfesseur() {
        return coursProfesseur;
    }

    public Cours coursProfesseur(Professeur professeur) {
        this.coursProfesseur = professeur;
        return this;
    }

    public void setCoursProfesseur(Professeur professeur) {
        this.coursProfesseur = professeur;
    }

    public Classe getCoursClass() {
        return coursClass;
    }

    public Cours coursClass(Classe classe) {
        this.coursClass = classe;
        return this;
    }

    public void setCoursClass(Classe classe) {
        this.coursClass = classe;
    }

    public Salle getSalleCours() {
        return salleCours;
    }

    public Cours salleCours(Salle salle) {
        this.salleCours = salle;
        return this;
    }

    public void setSalleCours(Salle salle) {
        this.salleCours = salle;
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
        Cours cours = (Cours) o;
        if (cours.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cours.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cours{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", heure='" + getHeure() + "'" +
            "}";
    }
}
