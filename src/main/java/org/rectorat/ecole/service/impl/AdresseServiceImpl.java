package org.rectorat.ecole.service.impl;

import org.rectorat.ecole.service.AdresseService;
import org.rectorat.ecole.domain.Adresse;
import org.rectorat.ecole.repository.AdresseRepository;
import org.rectorat.ecole.service.dto.AdresseDTO;
import org.rectorat.ecole.service.mapper.AdresseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Adresse.
 */
@Service
@Transactional
public class AdresseServiceImpl implements AdresseService {

    private final Logger log = LoggerFactory.getLogger(AdresseServiceImpl.class);

    private final AdresseRepository adresseRepository;

    private final AdresseMapper adresseMapper;

    public AdresseServiceImpl(AdresseRepository adresseRepository, AdresseMapper adresseMapper) {
        this.adresseRepository = adresseRepository;
        this.adresseMapper = adresseMapper;
    }

    /**
     * Save a adresse.
     *
     * @param adresseDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AdresseDTO save(AdresseDTO adresseDTO) {
        log.debug("Request to save Adresse : {}", adresseDTO);
        Adresse adresse = adresseMapper.toEntity(adresseDTO);
        adresse = adresseRepository.save(adresse);
        return adresseMapper.toDto(adresse);
    }

    /**
     * Get all the adresses.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<AdresseDTO> findAll() {
        log.debug("Request to get all Adresses");
        return adresseRepository.findAll().stream()
            .map(adresseMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one adresse by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public AdresseDTO findOne(Long id) {
        log.debug("Request to get Adresse : {}", id);
        Adresse adresse = adresseRepository.findOne(id);
        return adresseMapper.toDto(adresse);
    }

    /**
     * Delete the adresse by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adresse : {}", id);
        adresseRepository.delete(id);
    }
}
