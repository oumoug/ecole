package org.rectorat.ecole.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.rectorat.ecole.service.EleveService;
import org.rectorat.ecole.web.rest.errors.BadRequestAlertException;
import org.rectorat.ecole.web.rest.util.HeaderUtil;
import org.rectorat.ecole.web.rest.util.PaginationUtil;
import org.rectorat.ecole.service.dto.EleveDTO;
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
 * REST controller for managing Eleve.
 */
@RestController
@RequestMapping("/api")
public class EleveResource {

    private final Logger log = LoggerFactory.getLogger(EleveResource.class);

    private static final String ENTITY_NAME = "eleve";

    private final EleveService eleveService;

    public EleveResource(EleveService eleveService) {
        this.eleveService = eleveService;
    }

    /**
     * POST  /eleves : Create a new eleve.
     *
     * @param eleveDTO the eleveDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new eleveDTO, or with status 400 (Bad Request) if the eleve has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/eleves")
    @Timed
    public ResponseEntity<EleveDTO> createEleve(@Valid @RequestBody EleveDTO eleveDTO) throws URISyntaxException {
        log.debug("REST request to save Eleve : {}", eleveDTO);
        if (eleveDTO.getId() != null) {
            throw new BadRequestAlertException("A new eleve cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EleveDTO result = eleveService.save(eleveDTO);
        return ResponseEntity.created(new URI("/api/eleves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eleves : Updates an existing eleve.
     *
     * @param eleveDTO the eleveDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated eleveDTO,
     * or with status 400 (Bad Request) if the eleveDTO is not valid,
     * or with status 500 (Internal Server Error) if the eleveDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/eleves")
    @Timed
    public ResponseEntity<EleveDTO> updateEleve(@Valid @RequestBody EleveDTO eleveDTO) throws URISyntaxException {
        log.debug("REST request to update Eleve : {}", eleveDTO);
        if (eleveDTO.getId() == null) {
            return createEleve(eleveDTO);
        }
        EleveDTO result = eleveService.save(eleveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, eleveDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eleves : get all the eleves.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of eleves in body
     */
    @GetMapping("/eleves")
    @Timed
    public ResponseEntity<List<EleveDTO>> getAllEleves(Pageable pageable) {
        log.debug("REST request to get a page of Eleves");
        Page<EleveDTO> page = eleveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eleves");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eleves/:id : get the "id" eleve.
     *
     * @param id the id of the eleveDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the eleveDTO, or with status 404 (Not Found)
     */
    @GetMapping("/eleves/{id}")
    @Timed
    public ResponseEntity<EleveDTO> getEleve(@PathVariable Long id) {
        log.debug("REST request to get Eleve : {}", id);
        EleveDTO eleveDTO = eleveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(eleveDTO));
    }

    /**
     * DELETE  /eleves/:id : delete the "id" eleve.
     *
     * @param id the id of the eleveDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/eleves/{id}")
    @Timed
    public ResponseEntity<Void> deleteEleve(@PathVariable Long id) {
        log.debug("REST request to delete Eleve : {}", id);
        eleveService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
