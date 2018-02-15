package org.rectorat.ecole.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.rectorat.ecole.service.ClasseService;
import org.rectorat.ecole.web.rest.errors.BadRequestAlertException;
import org.rectorat.ecole.web.rest.util.HeaderUtil;
import org.rectorat.ecole.web.rest.util.PaginationUtil;
import org.rectorat.ecole.service.dto.ClasseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Classe.
 */
@RestController
@RequestMapping("/api")
public class ClasseResource {

    private final Logger log = LoggerFactory.getLogger(ClasseResource.class);

    private static final String ENTITY_NAME = "classe";

    private final ClasseService classeService;

    public ClasseResource(ClasseService classeService) {
        this.classeService = classeService;
    }

    /**
     * POST  /classes : Create a new classe.
     *
     * @param classeDTO the classeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classeDTO, or with status 400 (Bad Request) if the classe has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/classes")
    @Timed
    public ResponseEntity<ClasseDTO> createClasse(@RequestBody ClasseDTO classeDTO) throws URISyntaxException {
        log.debug("REST request to save Classe : {}", classeDTO);
        if (classeDTO.getId() != null) {
            throw new BadRequestAlertException("A new classe cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClasseDTO result = classeService.save(classeDTO);
        return ResponseEntity.created(new URI("/api/classes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classes : Updates an existing classe.
     *
     * @param classeDTO the classeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classeDTO,
     * or with status 400 (Bad Request) if the classeDTO is not valid,
     * or with status 500 (Internal Server Error) if the classeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/classes")
    @Timed
    public ResponseEntity<ClasseDTO> updateClasse(@RequestBody ClasseDTO classeDTO) throws URISyntaxException {
        log.debug("REST request to update Classe : {}", classeDTO);
        if (classeDTO.getId() == null) {
            return createClasse(classeDTO);
        }
        ClasseDTO result = classeService.save(classeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classes : get all the classes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of classes in body
     */
    @GetMapping("/classes")
    @Timed
    public ResponseEntity<List<ClasseDTO>> getAllClasses(Pageable pageable) {
        log.debug("REST request to get a page of Classes");
        Page<ClasseDTO> page = classeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/classes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /classes/:id : get the "id" classe.
     *
     * @param id the id of the classeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/classes/{id}")
    @Timed
    public ResponseEntity<ClasseDTO> getClasse(@PathVariable Long id) {
        log.debug("REST request to get Classe : {}", id);
        ClasseDTO classeDTO = classeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classeDTO));
    }

    /**
     * DELETE  /classes/:id : delete the "id" classe.
     *
     * @param id the id of the classeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/classes/{id}")
    @Timed
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        log.debug("REST request to delete Classe : {}", id);
        classeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
