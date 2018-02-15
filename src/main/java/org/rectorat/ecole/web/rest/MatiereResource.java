package org.rectorat.ecole.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.rectorat.ecole.service.MatiereService;
import org.rectorat.ecole.web.rest.errors.BadRequestAlertException;
import org.rectorat.ecole.web.rest.util.HeaderUtil;
import org.rectorat.ecole.web.rest.util.PaginationUtil;
import org.rectorat.ecole.service.dto.MatiereDTO;
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
 * REST controller for managing Matiere.
 */
@RestController
@RequestMapping("/api")
public class MatiereResource {

    private final Logger log = LoggerFactory.getLogger(MatiereResource.class);

    private static final String ENTITY_NAME = "matiere";

    private final MatiereService matiereService;

    public MatiereResource(MatiereService matiereService) {
        this.matiereService = matiereService;
    }

    /**
     * POST  /matieres : Create a new matiere.
     *
     * @param matiereDTO the matiereDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new matiereDTO, or with status 400 (Bad Request) if the matiere has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/matieres")
    @Timed
    public ResponseEntity<MatiereDTO> createMatiere(@Valid @RequestBody MatiereDTO matiereDTO) throws URISyntaxException {
        log.debug("REST request to save Matiere : {}", matiereDTO);
        if (matiereDTO.getId() != null) {
            throw new BadRequestAlertException("A new matiere cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MatiereDTO result = matiereService.save(matiereDTO);
        return ResponseEntity.created(new URI("/api/matieres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /matieres : Updates an existing matiere.
     *
     * @param matiereDTO the matiereDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated matiereDTO,
     * or with status 400 (Bad Request) if the matiereDTO is not valid,
     * or with status 500 (Internal Server Error) if the matiereDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/matieres")
    @Timed
    public ResponseEntity<MatiereDTO> updateMatiere(@Valid @RequestBody MatiereDTO matiereDTO) throws URISyntaxException {
        log.debug("REST request to update Matiere : {}", matiereDTO);
        if (matiereDTO.getId() == null) {
            return createMatiere(matiereDTO);
        }
        MatiereDTO result = matiereService.save(matiereDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, matiereDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /matieres : get all the matieres.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of matieres in body
     */
    @GetMapping("/matieres")
    @Timed
    public ResponseEntity<List<MatiereDTO>> getAllMatieres(Pageable pageable) {
        log.debug("REST request to get a page of Matieres");
        Page<MatiereDTO> page = matiereService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/matieres");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /matieres/:id : get the "id" matiere.
     *
     * @param id the id of the matiereDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the matiereDTO, or with status 404 (Not Found)
     */
    @GetMapping("/matieres/{id}")
    @Timed
    public ResponseEntity<MatiereDTO> getMatiere(@PathVariable Long id) {
        log.debug("REST request to get Matiere : {}", id);
        MatiereDTO matiereDTO = matiereService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(matiereDTO));
    }

    /**
     * DELETE  /matieres/:id : delete the "id" matiere.
     *
     * @param id the id of the matiereDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/matieres/{id}")
    @Timed
    public ResponseEntity<Void> deleteMatiere(@PathVariable Long id) {
        log.debug("REST request to delete Matiere : {}", id);
        matiereService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
