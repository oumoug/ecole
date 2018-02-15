package org.rectorat.ecole.service.mapper;

import org.rectorat.ecole.domain.*;
import org.rectorat.ecole.service.dto.EleveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Eleve and its DTO EleveDTO.
 */
@Mapper(componentModel = "spring", uses = {AdresseMapper.class, ClasseMapper.class})
public interface EleveMapper extends EntityMapper<EleveDTO, Eleve> {

    @Mapping(source = "adresseEleve.id", target = "adresseEleveId")
    @Mapping(source = "adresseEleve.ville", target = "adresseEleveVille")
    @Mapping(source = "eleveClasse.id", target = "eleveClasseId")
    @Mapping(source = "eleveClasse.nomClass", target = "eleveClasseNomClass")
    EleveDTO toDto(Eleve eleve);

    @Mapping(source = "adresseEleveId", target = "adresseEleve")
    @Mapping(source = "eleveClasseId", target = "eleveClasse")
    Eleve toEntity(EleveDTO eleveDTO);

    default Eleve fromId(Long id) {
        if (id == null) {
            return null;
        }
        Eleve eleve = new Eleve();
        eleve.setId(id);
        return eleve;
    }
}
