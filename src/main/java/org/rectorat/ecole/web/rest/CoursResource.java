package org.rectorat.ecole.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.rectorat.ecole.service.CoursService;
import org.rectorat.ecole.web.rest.errors.BadRequestAlertException;
import org.rectorat.ecole.web.rest.util.HeaderUtil;
import org.rectorat.ecole.service.dto.CoursDTO;
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
 * REST controller for managing Cours.
 */
@RestController
@RequestMapping("/api")
public class CoursResource {

    private final Logger log = LoggerFactory.getLogger(CoursResource.class);

    private static final String ENTITY_NAME = "cours";

    private final CoursService coursService;

    public CoursResource(CoursService coursService) {
        this.coursService = coursService;
    }

    /**
     * POST  /cours : Create a new cours.
     *
     * @param coursDTO the coursDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coursDTO, or with status 400 (Bad Request) if the cours has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cours")
    @Timed
    public ResponseEntity<CoursDTO> createCours(@Valid @RequestBody CoursDTO coursDTO) throws URISyntaxException {
        log.debug("REST request to save Cours : {}", coursDTO);
        if (coursDTO.getId() != null) {
            throw new BadRequestAlertException("A new cours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoursDTO result = coursService.save(coursDTO);
        return ResponseEntity.created(new URI("/api/cours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cours : Updates an existing cours.
     *
     * @param coursDTO the coursDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coursDTO,
     * or with status 400 (Bad Request) if the coursDTO is not valid,
     * or with status 500 (Internal Server Error) if the coursDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cours")
    @Timed
    public ResponseEntity<CoursDTO> updateCours(@Valid @RequestBody CoursDTO coursDTO) throws URISyntaxException {
        log.debug("REST request to update Cours : {}", coursDTO);
        if (coursDTO.getId() == null) {
            return createCours(coursDTO);
        }
        CoursDTO result = coursService.save(coursDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coursDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cours : get all the cours.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cours in body
     */
    @GetMapping("/cours")
    @Timed
    public List<CoursDTO> getAllCours() {
        log.debug("REST request to get all Cours");
        return coursService.findAll();
        }

    /**
     * GET  /cours/:id : get the "id" cours.
     *
     * @param id the id of the coursDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coursDTO, or with status 404 (Not Found)
     */
    @GetMapping("/cours/{id}")
    @Timed
    public ResponseEntity<CoursDTO> getCours(@PathVariable Long id) {
        log.debug("REST request to get Cours : {}", id);
        CoursDTO coursDTO = coursService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coursDTO));
    }

    /**
     * DELETE  /cours/:id : delete the "id" cours.
     *
     * @param id the id of the coursDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cours/{id}")
    @Timed
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        log.debug("REST request to delete Cours : {}", id);
        coursService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
