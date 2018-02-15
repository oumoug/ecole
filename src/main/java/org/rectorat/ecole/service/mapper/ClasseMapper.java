package org.rectorat.ecole.service.mapper;

import org.rectorat.ecole.domain.*;
import org.rectorat.ecole.service.dto.ClasseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Classe and its DTO ClasseDTO.
 */
@Mapper(componentModel = "spring", uses = {MatiereMapper.class})
public interface ClasseMapper extends EntityMapper<ClasseDTO, Classe> {


    @Mapping(target = "professeurClasses", ignore = true)
    Classe toEntity(ClasseDTO classeDTO);

    default Classe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Classe classe = new Classe();
        classe.setId(id);
        return classe;
    }
}
