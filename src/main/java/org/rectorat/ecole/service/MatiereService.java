package org.rectorat.ecole.service;

import org.rectorat.ecole.service.dto.MatiereDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Matiere.
 */
public interface MatiereService {

    /**
     * Save a matiere.
     *
     * @param matiereDTO the entity to save
     * @return the persisted entity
     */
    MatiereDTO save(MatiereDTO matiereDTO);

    /**
     * Get all the matieres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MatiereDTO> findAll(Pageable pageable);

    /**
     * Get the "id" matiere.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MatiereDTO findOne(Long id);

    /**
     * Delete the "id" matiere.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
