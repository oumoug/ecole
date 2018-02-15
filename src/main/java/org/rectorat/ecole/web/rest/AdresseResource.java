package org.rectorat.ecole.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.rectorat.ecole.service.AdresseService;
import org.rectorat.ecole.web.rest.errors.BadRequestAlertException;
import org.rectorat.ecole.web.rest.util.HeaderUtil;
import org.rectorat.ecole.service.dto.AdresseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Adresse.
 */
@RestController
@RequestMapping("/api")
public class AdresseResource {

    private final Logger log = LoggerFactory.getLogger(AdresseResource.class);

    private static final String ENTITY_NAME = "adresse";

    private final AdresseService adresseService;

    public AdresseResource(AdresseService adresseService) {
        this.adresseService = adresseService;
    }

    /**
     * POST  /adresses : Create a new adresse.
     *
     * @param adresseDTO the adresseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new adresseDTO, or with status 400 (Bad Request) if the adresse has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/adresses")
    @Timed
    public ResponseEntity<AdresseDTO> createAdresse(@Valid @RequestBody AdresseDTO adresseDTO) throws URISyntaxException {
        log.debug("REST request to save Adresse : {}", adresseDTO);
        if (adresseDTO.getId() != null) {
            throw new BadRequestAlertException("A new adresse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AdresseDTO result = adresseService.save(adresseDTO);
        return ResponseEntity.created(new URI("/api/adresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /adresses : Updates an existing adresse.
     *
     * @param adresseDTO the adresseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated adresseDTO,
     * or with status 400 (Bad Request) if the adresseDTO is not valid,
     * or with status 500 (Internal Server Error) if the adresseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/adresses")
    @Timed
    public ResponseEntity<AdresseDTO> updateAdresse(@Valid @RequestBody AdresseDTO adresseDTO) throws URISyntaxException {
        log.debug("REST request to update Adresse : {}", adresseDTO);
        if (adresseDTO.getId() == null) {
            return createAdresse(adresseDTO);
        }
        AdresseDTO result = adresseService.save(adresseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, adresseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /adresses : get all the adresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of adresses in body
     */
    @GetMapping("/adresses")
    @Timed
    public List<AdresseDTO> getAllAdresses() {
        log.debug("REST request to get all Adresses");
        return adresseService.findAll();
        }

    /**
     * GET  /adresses/:id : get the "id" adresse.
     *
     * @param id the id of the adresseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the adresseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/adresses/{id}")
    @Timed
    public ResponseEntity<AdresseDTO> getAdresse(@PathVariable Long id) {
        log.debug("REST request to get Adresse : {}", id);
        AdresseDTO adresseDTO = adresseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(adresseDTO));
    }

    /**
     * DELETE  /adresses/:id : delete the "id" adresse.
     *
     * @param id the id of the adresseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/adresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteAdresse(@PathVariable Long id) {
        log.debug("REST request to delete Adresse : {}", id);
        adresseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
