package org.rectorat.ecole.service;

import org.rectorat.ecole.service.dto.CoursDTO;
import java.util.List;

/**
 * Service Interface for managing Cours.
 */
public interface CoursService {

    /**
     * Save a cours.
     *
     * @param coursDTO the entity to save
     * @return the persisted entity
     */
    CoursDTO save(CoursDTO coursDTO);

    /**
     * Get all the cours.
     *
     * @return the list of entities
     */
    List<CoursDTO> findAll();

    /**
     * Get the "id" cours.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CoursDTO findOne(Long id);

    /**
     * Delete the "id" cours.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
