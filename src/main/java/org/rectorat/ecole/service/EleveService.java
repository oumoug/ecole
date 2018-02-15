package org.rectorat.ecole.service;

import org.rectorat.ecole.service.dto.EleveDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Eleve.
 */
public interface EleveService {

    /**
     * Save a eleve.
     *
     * @param eleveDTO the entity to save
     * @return the persisted entity
     */
    EleveDTO save(EleveDTO eleveDTO);

    /**
     * Get all the eleves.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EleveDTO> findAll(Pageable pageable);

    /**
     * Get the "id" eleve.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EleveDTO findOne(Long id);

    /**
     * Delete the "id" eleve.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
