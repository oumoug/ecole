package org.rectorat.ecole.service.impl;

import org.rectorat.ecole.service.MatiereService;
import org.rectorat.ecole.domain.Matiere;
import org.rectorat.ecole.repository.MatiereRepository;
import org.rectorat.ecole.service.dto.MatiereDTO;
import org.rectorat.ecole.service.mapper.MatiereMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Matiere.
 */
@Service
@Transactional
public class MatiereServiceImpl implements MatiereService {

    private final Logger log = LoggerFactory.getLogger(MatiereServiceImpl.class);

    private final MatiereRepository matiereRepository;

    private final MatiereMapper matiereMapper;

    public MatiereServiceImpl(MatiereRepository matiereRepository, MatiereMapper matiereMapper) {
        this.matiereRepository = matiereRepository;
        this.matiereMapper = matiereMapper;
    }

    /**
     * Save a matiere.
     *
     * @param matiereDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MatiereDTO save(MatiereDTO matiereDTO) {
        log.debug("Request to save Matiere : {}", matiereDTO);
        Matiere matiere = matiereMapper.toEntity(matiereDTO);
        matiere = matiereRepository.save(matiere);
        return matiereMapper.toDto(matiere);
    }

    /**
     * Get all the matieres.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MatiereDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Matieres");
        return matiereRepository.findAll(pageable)
            .map(matiereMapper::toDto);
    }

    /**
     * Get one matiere by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MatiereDTO findOne(Long id) {
        log.debug("Request to get Matiere : {}", id);
        Matiere matiere = matiereRepository.findOneWithEagerRelationships(id);
        return matiereMapper.toDto(matiere);
    }

    /**
     * Delete the matiere by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Matiere : {}", id);
        matiereRepository.delete(id);
    }
}
