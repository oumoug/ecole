package org.rectorat.ecole.service;

import org.rectorat.ecole.service.dto.ClasseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Classe.
 */
public interface ClasseService {

    /**
     * Save a classe.
     *
     * @param classeDTO the entity to save
     * @return the persisted entity
     */
    ClasseDTO save(ClasseDTO classeDTO);

    /**
     * Get all the classes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClasseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" classe.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ClasseDTO findOne(Long id);

    /**
     * Delete the "id" classe.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
