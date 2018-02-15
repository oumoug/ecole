package org.rectorat.ecole.service.impl;

import org.rectorat.ecole.service.ClasseService;
import org.rectorat.ecole.domain.Classe;
import org.rectorat.ecole.repository.ClasseRepository;
import org.rectorat.ecole.service.dto.ClasseDTO;
import org.rectorat.ecole.service.mapper.ClasseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Classe.
 */
@Service
@Transactional
public class ClasseServiceImpl implements ClasseService {

    private final Logger log = LoggerFactory.getLogger(ClasseServiceImpl.class);

    private final ClasseRepository classeRepository;

    private final ClasseMapper classeMapper;

    public ClasseServiceImpl(ClasseRepository classeRepository, ClasseMapper classeMapper) {
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
    }

    /**
     * Save a classe.
     *
     * @param classeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClasseDTO save(ClasseDTO classeDTO) {
        log.debug("Request to save Classe : {}", classeDTO);
        Classe classe = classeMapper.toEntity(classeDTO);
        classe = classeRepository.save(classe);
        return classeMapper.toDto(classe);
    }

    /**
     * Get all the classes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClasseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Classes");
        return classeRepository.findAll(pageable)
            .map(classeMapper::toDto);
    }

    /**
     * Get one classe by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClasseDTO findOne(Long id) {
        log.debug("Request to get Classe : {}", id);
        Classe classe = classeRepository.findOneWithEagerRelationships(id);
        return classeMapper.toDto(classe);
    }

    /**
     * Delete the classe by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Classe : {}", id);
        classeRepository.delete(id);
    }
}
