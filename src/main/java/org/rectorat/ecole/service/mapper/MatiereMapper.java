package org.rectorat.ecole.service.mapper;

import org.rectorat.ecole.domain.*;
import org.rectorat.ecole.service.dto.MatiereDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Matiere and its DTO MatiereDTO.
 */
@Mapper(componentModel = "spring", uses = {ProfesseurMapper.class})
public interface MatiereMapper extends EntityMapper<MatiereDTO, Matiere> {


    @Mapping(target = "matiereEleves", ignore = true)
    Matiere toEntity(MatiereDTO matiereDTO);

    default Matiere fromId(Long id) {
        if (id == null) {
            return null;
        }
        Matiere matiere = new Matiere();
        matiere.setId(id);
        return matiere;
    }
}
