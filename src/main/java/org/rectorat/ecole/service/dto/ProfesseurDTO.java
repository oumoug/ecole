package org.rectorat.ecole.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Professeur entity.
 */
public class ProfesseurDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    private Long adresseProfesseurId;

    private String adresseProfesseurVille;

    private Set<ClasseDTO> classeEnseignes = new HashSet<>();

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

    public Long getAdresseProfesseurId() {
        return adresseProfesseurId;
    }

    public void setAdresseProfesseurId(Long adresseId) {
        this.adresseProfesseurId = adresseId;
    }

    public String getAdresseProfesseurVille() {
        return adresseProfesseurVille;
    }

    public void setAdresseProfesseurVille(String adresseVille) {
        this.adresseProfesseurVille = adresseVille;
    }

    public Set<ClasseDTO> getClasseEnseignes() {
        return classeEnseignes;
    }

    public void setClasseEnseignes(Set<ClasseDTO> classes) {
        this.classeEnseignes = classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProfesseurDTO professeurDTO = (ProfesseurDTO) o;
        if(professeurDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), professeurDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProfesseurDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            "}";
    }
}
