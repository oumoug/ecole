package org.rectorat.ecole.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.rectorat.ecole.service.ProfesseurService;
import org.rectorat.ecole.web.rest.errors.BadRequestAlertException;
import org.rectorat.ecole.web.rest.util.HeaderUtil;
import org.rectorat.ecole.web.rest.util.PaginationUtil;
import org.rectorat.ecole.service.dto.ProfesseurDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Professeur.
 */
@RestController
@RequestMapping("/api")
public class ProfesseurResource {

    private final Logger log = LoggerFactory.getLogger(ProfesseurResource.class);

    private static final String ENTITY_NAME = "professeur";

    private final ProfesseurService professeurService;

    public ProfesseurResource(ProfesseurService professeurService) {
        this.professeurService = professeurService;
    }

    /**
     * POST  /professeurs : Create a new professeur.
     *
     * @param professeurDTO the professeurDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new professeurDTO, or with status 400 (Bad Request) if the professeur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/professeurs")
    @Timed
    public ResponseEntity<ProfesseurDTO> createProfesseur(@Valid @RequestBody ProfesseurDTO professeurDTO) throws URISyntaxException {
        log.debug("REST request to save Professeur : {}", professeurDTO);
        if (professeurDTO.getId() != null) {
            throw new BadRequestAlertException("A new professeur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProfesseurDTO result = professeurService.save(professeurDTO);
        return ResponseEntity.created(new URI("/api/professeurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professeurs : Updates an existing professeur.
     *
     * @param professeurDTO the professeurDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated professeurDTO,
     * or with status 400 (Bad Request) if the professeurDTO is not valid,
     * or with status 500 (Internal Server Error) if the professeurDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/professeurs")
    @Timed
    public ResponseEntity<ProfesseurDTO> updateProfesseur(@Valid @RequestBody ProfesseurDTO professeurDTO) throws URISyntaxException {
        log.debug("REST request to update Professeur : {}", professeurDTO);
        if (professeurDTO.getId() == null) {
            return createProfesseur(professeurDTO);
        }
        ProfesseurDTO result = professeurService.save(professeurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, professeurDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professeurs : get all the professeurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of professeurs in body
     */
    @GetMapping("/professeurs")
    @Timed
    public ResponseEntity<List<ProfesseurDTO>> getAllProfesseurs(Pageable pageable) {
        log.debug("REST request to get a page of Professeurs");
        Page<ProfesseurDTO> page = professeurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/professeurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /professeurs/:id : get the "id" professeur.
     *
     * @param id the id of the professeurDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the professeurDTO, or with status 404 (Not Found)
     */
    @GetMapping("/professeurs/{id}")
    @Timed
    public ResponseEntity<ProfesseurDTO> getProfesseur(@PathVariable Long id) {
        log.debug("REST request to get Professeur : {}", id);
        ProfesseurDTO professeurDTO = professeurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(professeurDTO));
    }

    /**
     * DELETE  /professeurs/:id : delete the "id" professeur.
     *
     * @param id the id of the professeurDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/professeurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteProfesseur(@PathVariable Long id) {
        log.debug("REST request to delete Professeur : {}", id);
        professeurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
