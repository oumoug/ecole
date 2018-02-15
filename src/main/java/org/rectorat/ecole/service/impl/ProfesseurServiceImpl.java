package org.rectorat.ecole.service.impl;

import org.rectorat.ecole.service.ProfesseurService;
import org.rectorat.ecole.domain.Professeur;
import org.rectorat.ecole.repository.ProfesseurRepository;
import org.rectorat.ecole.service.dto.ProfesseurDTO;
import org.rectorat.ecole.service.mapper.ProfesseurMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Professeur.
 */
@Service
@Transactional
public class ProfesseurServiceImpl implements ProfesseurService {

    private final Logger log = LoggerFactory.getLogger(ProfesseurServiceImpl.class);

    private final ProfesseurRepository professeurRepository;

    private final ProfesseurMapper professeurMapper;

    public ProfesseurServiceImpl(ProfesseurRepository professeurRepository, ProfesseurMapper professeurMapper) {
        this.professeurRepository = professeurRepository;
        this.professeurMapper = professeurMapper;
    }

    /**
     * Save a professeur.
     *
     * @param professeurDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProfesseurDTO save(ProfesseurDTO professeurDTO) {
        log.debug("Request to save Professeur : {}", professeurDTO);
        Professeur professeur = professeurMapper.toEntity(professeurDTO);
        professeur = professeurRepository.save(professeur);
        return professeurMapper.toDto(professeur);
    }

    /**
     * Get all the professeurs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProfesseurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Professeurs");
        return professeurRepository.findAll(pageable)
            .map(professeurMapper::toDto);
    }

    /**
     * Get one professeur by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProfesseurDTO findOne(Long id) {
        log.debug("Request to get Professeur : {}", id);
        Professeur professeur = professeurRepository.findOneWithEagerRelationships(id);
        return professeurMapper.toDto(professeur);
    }

    /**
     * Delete the professeur by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Professeur : {}", id);
        professeurRepository.delete(id);
    }
}
