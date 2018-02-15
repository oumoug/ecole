package org.rectorat.ecole.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Adresse entity.
 */
public class AdresseDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer codePostal;

    @NotNull
    private String ville;

    private Integer numeroRue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getNumeroRue() {
        return numeroRue;
    }

    public void setNumeroRue(Integer numeroRue) {
        this.numeroRue = numeroRue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdresseDTO adresseDTO = (AdresseDTO) o;
        if(adresseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adresseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdresseDTO{" +
            "id=" + getId() +
            ", codePostal=" + getCodePostal() +
            ", ville='" + getVille() + "'" +
            ", numeroRue=" + getNumeroRue() +
            "}";
    }
}
