package org.rectorat.ecole.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Eleve entity.
 */
public class EleveDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    private Float moyenne;

    private Long adresseEleveId;

    private String adresseEleveVille;

    private Long eleveClasseId;

    private String eleveClasseNomClass;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }

    public Long getAdresseEleveId() {
        return adresseEleveId;
    }

    public void setAdresseEleveId(Long adresseId) {
        this.adresseEleveId = adresseId;
    }

    public String getAdresseEleveVille() {
        return adresseEleveVille;
    }

    public void setAdresseEleveVille(String adresseVille) {
        this.adresseEleveVille = adresseVille;
    }

    public Long getEleveClasseId() {
        return eleveClasseId;
    }

    public void setEleveClasseId(Long classeId) {
        this.eleveClasseId = classeId;
    }

    public String getEleveClasseNomClass() {
        return eleveClasseNomClass;
    }

    public void setEleveClasseNomClass(String classeNomClass) {
        this.eleveClasseNomClass = classeNomClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EleveDTO eleveDTO = (EleveDTO) o;
        if(eleveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), eleveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EleveDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", moyenne=" + getMoyenne() +
            "}";
    }
}
