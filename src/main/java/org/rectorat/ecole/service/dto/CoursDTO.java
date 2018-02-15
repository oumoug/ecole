package org.rectorat.ecole.service.dto;


import java.time.Instant;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Cours entity.
 */
public class CoursDTO implements Serializable {

    private Long id;

    private LocalDate date;

    private Instant heure;

    private Long coursProfesseurId;

    private String coursProfesseurNom;

    private Long coursClassId;

    private String coursClassNomClass;

    private Long salleCoursId;

    private String salleCoursNomSalle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getHeure() {
        return heure;
    }

    public void setHeure(Instant heure) {
        this.heure = heure;
    }

    public Long getCoursProfesseurId() {
        return coursProfesseurId;
    }

    public void setCoursProfesseurId(Long professeurId) {
        this.coursProfesseurId = professeurId;
    }

    public String getCoursProfesseurNom() {
        return coursProfesseurNom;
    }

    public void setCoursProfesseurNom(String professeurNom) {
        this.coursProfesseurNom = professeurNom;
    }

    public Long getCoursClassId() {
        return coursClassId;
    }

    public void setCoursClassId(Long classeId) {
        this.coursClassId = classeId;
    }

    public String getCoursClassNomClass() {
        return coursClassNomClass;
    }

    public void setCoursClassNomClass(String classeNomClass) {
        this.coursClassNomClass = classeNomClass;
    }

    public Long getSalleCoursId() {
        return salleCoursId;
    }

    public void setSalleCoursId(Long salleId) {
        this.salleCoursId = salleId;
    }

    public String getSalleCoursNomSalle() {
        return salleCoursNomSalle;
    }

    public void setSalleCoursNomSalle(String salleNomSalle) {
        this.salleCoursNomSalle = salleNomSalle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoursDTO coursDTO = (CoursDTO) o;
        if(coursDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coursDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoursDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", heure='" + getHeure() + "'" +
            "}";
    }
}
