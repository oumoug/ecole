package org.rectorat.ecole.service.impl;

import org.rectorat.ecole.service.CoursService;
import org.rectorat.ecole.domain.Cours;
import org.rectorat.ecole.repository.CoursRepository;
import org.rectorat.ecole.service.dto.CoursDTO;
import org.rectorat.ecole.service.mapper.CoursMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Cours.
 */
@Service
@Transactional
public class CoursServiceImpl implements CoursService {

    private final Logger log = LoggerFactory.getLogger(CoursServiceImpl.class);

    private final CoursRepository coursRepository;

    private final CoursMapper coursMapper;

    public CoursServiceImpl(CoursRepository coursRepository, CoursMapper coursMapper) {
        this.coursRepository = coursRepository;
        this.coursMapper = coursMapper;
    }

    /**
     * Save a cours.
     *
     * @param coursDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CoursDTO save(CoursDTO coursDTO) {
        log.debug("Request to save Cours : {}", coursDTO);
        Cours cours = coursMapper.toEntity(coursDTO);
        cours = coursRepository.save(cours);
        return coursMapper.toDto(cours);
    }

    /**
     * Get all the cours.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<CoursDTO> findAll() {
        log.debug("Request to get all Cours");
        return coursRepository.findAll().stream()
            .map(coursMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one cours by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CoursDTO findOne(Long id) {
        log.debug("Request to get Cours : {}", id);
        Cours cours = coursRepository.findOne(id);
        return coursMapper.toDto(cours);
    }

    /**
     * Delete the cours by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cours : {}", id);
        coursRepository.delete(id);
    }
}
