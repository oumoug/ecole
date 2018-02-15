package org.rectorat.ecole.service.mapper;

import org.rectorat.ecole.domain.*;
import org.rectorat.ecole.service.dto.ProfesseurDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Professeur and its DTO ProfesseurDTO.
 */
@Mapper(componentModel = "spring", uses = {AdresseMapper.class, ClasseMapper.class})
public interface ProfesseurMapper extends EntityMapper<ProfesseurDTO, Professeur> {

    @Mapping(source = "adresseProfesseur.id", target = "adresseProfesseurId")
    @Mapping(source = "adresseProfesseur.ville", target = "adresseProfesseurVille")
    ProfesseurDTO toDto(Professeur professeur);

    @Mapping(source = "adresseProfesseurId", target = "adresseProfesseur")
    @Mapping(target = "matiereEnseignes", ignore = true)
    Professeur toEntity(ProfesseurDTO professeurDTO);

    default Professeur fromId(Long id) {
        if (id == null) {
            return null;
        }
        Professeur professeur = new Professeur();
        professeur.setId(id);
        return professeur;
    }
}
