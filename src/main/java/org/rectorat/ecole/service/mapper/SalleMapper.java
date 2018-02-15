package org.rectorat.ecole.service.mapper;

import org.rectorat.ecole.domain.*;
import org.rectorat.ecole.service.dto.SalleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Salle and its DTO SalleDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SalleMapper extends EntityMapper<SalleDTO, Salle> {



    default Salle fromId(Long id) {
        if (id == null) {
            return null;
        }
        Salle salle = new Salle();
        salle.setId(id);
        return salle;
    }
}
