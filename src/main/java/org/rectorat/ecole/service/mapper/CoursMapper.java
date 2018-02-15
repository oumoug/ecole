package org.rectorat.ecole.service.mapper;

import org.rectorat.ecole.domain.*;
import org.rectorat.ecole.service.dto.CoursDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cours and its DTO CoursDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfesseurMapper.class, ClasseMapper.class, SalleMapper.class})
public interface CoursMapper extends EntityMapper<CoursDTO, Cours> {

    @Mapping(source = "coursProfesseur.id", target = "coursProfesseurId")
    @Mapping(source = "coursProfesseur.nom", target = "coursProfesseurNom")
    @Mapping(source = "coursClass.id", target = "coursClassId")
    @Mapping(source = "coursClass.nomClass", target = "coursClassNomClass")
    @Mapping(source = "salleCours.id", target = "salleCoursId")
    @Mapping(source = "salleCours.nomSalle", target = "salleCoursNomSalle")
    CoursDTO toDto(Cours cours);

    @Mapping(source = "coursProfesseurId", target = "coursProfesseur")
    @Mapping(source = "coursClassId", target = "coursClass")
    @Mapping(source = "salleCoursId", target = "salleCours")
    Cours toEntity(CoursDTO coursDTO);

    default Cours fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cours cours = new Cours();
        cours.setId(id);
        return cours;
    }
}
