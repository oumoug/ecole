package org.rectorat.ecole.service.impl;

import org.rectorat.ecole.service.EleveService;
import org.rectorat.ecole.domain.Eleve;
import org.rectorat.ecole.repository.EleveRepository;
import org.rectorat.ecole.service.dto.EleveDTO;
import org.rectorat.ecole.service.mapper.EleveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Eleve.
 */
@Service
@Transactional
public class EleveServiceImpl implements EleveService {

    private final Logger log = LoggerFactory.getLogger(EleveServiceImpl.class);

    private final EleveRepository eleveRepository;

    private final EleveMapper eleveMapper;

    public EleveServiceImpl(EleveRepository eleveRepository, EleveMapper eleveMapper) {
        this.eleveRepository = eleveRepository;
        this.eleveMapper = eleveMapper;
    }

    /**
     * Save a eleve.
     *
     * @param eleveDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EleveDTO save(EleveDTO eleveDTO) {
        log.debug("Request to save Eleve : {}", eleveDTO);
        Eleve eleve = eleveMapper.toEntity(eleveDTO);
        eleve = eleveRepository.save(eleve);
        return eleveMapper.toDto(eleve);
    }

    /**
     * Get all the eleves.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EleveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Eleves");
        return eleveRepository.findAll(pageable)
            .map(eleveMapper::toDto);
    }

    /**
     * Get one eleve by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EleveDTO findOne(Long id) {
        log.debug("Request to get Eleve : {}", id);
        Eleve eleve = eleveRepository.findOne(id);
        return eleveMapper.toDto(eleve);
    }

    /**
     * Delete the eleve by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Eleve : {}", id);
        eleveRepository.delete(id);
    }
}
