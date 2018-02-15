package org.rectorat.ecole.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Classe entity.
 */
public class ClasseDTO implements Serializable {

    private Long id;

    private String nomClass;

    private Set<MatiereDTO> matiereEtudiers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomClass() {
        return nomClass;
    }

    public void setNomClass(String nomClass) {
        this.nomClass = nomClass;
    }

    public Set<MatiereDTO> getMatiereEtudiers() {
        return matiereEtudiers;
    }

    public void setMatiereEtudiers(Set<MatiereDTO> matieres) {
        this.matiereEtudiers = matieres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClasseDTO classeDTO = (ClasseDTO) o;
        if(classeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClasseDTO{" +
            "id=" + getId() +
            ", nomClass='" + getNomClass() + "'" +
            "}";
    }
}
