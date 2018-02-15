package org.rectorat.ecole.service;

import org.rectorat.ecole.service.dto.ProfesseurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Professeur.
 */
public interface ProfesseurService {

    /**
     * Save a professeur.
     *
     * @param professeurDTO the entity to save
     * @return the persisted entity
     */
    ProfesseurDTO save(ProfesseurDTO professeurDTO);

    /**
     * Get all the professeurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProfesseurDTO> findAll(Pageable pageable);

    /**
     * Get the "id" professeur.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProfesseurDTO findOne(Long id);

    /**
     * Delete the "id" professeur.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
