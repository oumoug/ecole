package org.rectorat.ecole.service.impl;

import org.rectorat.ecole.service.SalleService;
import org.rectorat.ecole.domain.Salle;
import org.rectorat.ecole.repository.SalleRepository;
import org.rectorat.ecole.service.dto.SalleDTO;
import org.rectorat.ecole.service.mapper.SalleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Salle.
 */
@Service
@Transactional
public class SalleServiceImpl implements SalleService {

    private final Logger log = LoggerFactory.getLogger(SalleServiceImpl.class);

    private final SalleRepository salleRepository;

    private final SalleMapper salleMapper;

    public SalleServiceImpl(SalleRepository salleRepository, SalleMapper salleMapper) {
        this.salleRepository = salleRepository;
        this.salleMapper = salleMapper;
    }

    /**
     * Save a salle.
     *
     * @param salleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SalleDTO save(SalleDTO salleDTO) {
        log.debug("Request to save Salle : {}", salleDTO);
        Salle salle = salleMapper.toEntity(salleDTO);
        salle = salleRepository.save(salle);
        return salleMapper.toDto(salle);
    }

    /**
     * Get all the salles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SalleDTO> findAll() {
        log.debug("Request to get all Salles");
        return salleRepository.findAll().stream()
            .map(salleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one salle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SalleDTO findOne(Long id) {
        log.debug("Request to get Salle : {}", id);
        Salle salle = salleRepository.findOne(id);
        return salleMapper.toDto(salle);
    }

    /**
     * Delete the salle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Salle : {}", id);
        salleRepository.delete(id);
    }
}
