package org.rectorat.ecole.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.rectorat.ecole.service.SalleService;
import org.rectorat.ecole.web.rest.errors.BadRequestAlertException;
import org.rectorat.ecole.web.rest.util.HeaderUtil;
import org.rectorat.ecole.service.dto.SalleDTO;
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
 * REST controller for managing Salle.
 */
@RestController
@RequestMapping("/api")
public class SalleResource {

    private final Logger log = LoggerFactory.getLogger(SalleResource.class);

    private static final String ENTITY_NAME = "salle";

    private final SalleService salleService;

    public SalleResource(SalleService salleService) {
        this.salleService = salleService;
    }

    /**
     * POST  /salles : Create a new salle.
     *
     * @param salleDTO the salleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salleDTO, or with status 400 (Bad Request) if the salle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salles")
    @Timed
    public ResponseEntity<SalleDTO> createSalle(@Valid @RequestBody SalleDTO salleDTO) throws URISyntaxException {
        log.debug("REST request to save Salle : {}", salleDTO);
        if (salleDTO.getId() != null) {
            throw new BadRequestAlertException("A new salle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalleDTO result = salleService.save(salleDTO);
        return ResponseEntity.created(new URI("/api/salles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salles : Updates an existing salle.
     *
     * @param salleDTO the salleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salleDTO,
     * or with status 400 (Bad Request) if the salleDTO is not valid,
     * or with status 500 (Internal Server Error) if the salleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salles")
    @Timed
    public ResponseEntity<SalleDTO> updateSalle(@Valid @RequestBody SalleDTO salleDTO) throws URISyntaxException {
        log.debug("REST request to update Salle : {}", salleDTO);
        if (salleDTO.getId() == null) {
            return createSalle(salleDTO);
        }
        SalleDTO result = salleService.save(salleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salles : get all the salles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of salles in body
     */
    @GetMapping("/salles")
    @Timed
    public List<SalleDTO> getAllSalles() {
        log.debug("REST request to get all Salles");
        return salleService.findAll();
        }

    /**
     * GET  /salles/:id : get the "id" salle.
     *
     * @param id the id of the salleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/salles/{id}")
    @Timed
    public ResponseEntity<SalleDTO> getSalle(@PathVariable Long id) {
        log.debug("REST request to get Salle : {}", id);
        SalleDTO salleDTO = salleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(salleDTO));
    }

    /**
     * DELETE  /salles/:id : delete the "id" salle.
     *
     * @param id the id of the salleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salles/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalle(@PathVariable Long id) {
        log.debug("REST request to delete Salle : {}", id);
        salleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
