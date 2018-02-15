package org.rectorat.ecole.service;

import org.rectorat.ecole.service.dto.SalleDTO;
import java.util.List;

/**
 * Service Interface for managing Salle.
 */
public interface SalleService {

    /**
     * Save a salle.
     *
     * @param salleDTO the entity to save
     * @return the persisted entity
     */
    SalleDTO save(SalleDTO salleDTO);

    /**
     * Get all the salles.
     *
     * @return the list of entities
     */
    List<SalleDTO> findAll();

    /**
     * Get the "id" salle.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SalleDTO findOne(Long id);

    /**
     * Delete the "id" salle.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
