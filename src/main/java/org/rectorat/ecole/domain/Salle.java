package org.rectorat.ecole.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Salle.
 */
@Entity
@Table(name = "salle")
public class Salle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom_salle", nullable = false)
    private String nomSalle;

    @NotNull
    @Column(name = "numero_salle", nullable = false)
    private Integer numeroSalle;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public Salle nomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
        return this;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Integer getNumeroSalle() {
        return numeroSalle;
    }

    public Salle numeroSalle(Integer numeroSalle) {
        this.numeroSalle = numeroSalle;
        return this;
    }

    public void setNumeroSalle(Integer numeroSalle) {
        this.numeroSalle = numeroSalle;
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
        Salle salle = (Salle) o;
        if (salle.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salle.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Salle{" +
            "id=" + getId() +
            ", nomSalle='" + getNomSalle() + "'" +
            ", numeroSalle=" + getNumeroSalle() +
            "}";
    }
}
