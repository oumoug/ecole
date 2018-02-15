package org.rectorat.ecole.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Matiere entity.
 */
public class MatiereDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomMatiere;

    private Set<ProfesseurDTO> matiereProfs = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomMatiere() {
        return nomMatiere;
    }

    public void setNomMatiere(String nomMatiere) {
        this.nomMatiere = nomMatiere;
    }

    public Set<ProfesseurDTO> getMatiereProfs() {
        return matiereProfs;
    }

    public void setMatiereProfs(Set<ProfesseurDTO> professeurs) {
        this.matiereProfs = professeurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MatiereDTO matiereDTO = (MatiereDTO) o;
        if(matiereDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), matiereDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MatiereDTO{" +
            "id=" + getId() +
            ", nomMatiere='" + getNomMatiere() + "'" +
            "}";
    }
}
