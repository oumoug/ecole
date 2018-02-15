package org.rectorat.ecole.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Salle entity.
 */
public class SalleDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomSalle;

    @NotNull
    private Integer numeroSalle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Integer getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(Integer numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalleDTO salleDTO = (SalleDTO) o;
        if(salleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalleDTO{" +
            "id=" + getId() +
            ", nomSalle='" + getNomSalle() + "'" +
            ", numeroSalle=" + getNumeroSalle() +
            "}";
    }
}
