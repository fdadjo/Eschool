package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.Abscence;
import com.software.zone.pro.eschool.repository.AbscenceRepository;
import com.software.zone.pro.eschool.web.rest.errors.BadRequestAlertException;
import com.software.zone.pro.eschool.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Abscence.
 */
@RestController
@RequestMapping("/api")
public class AbscenceResource {

    private final Logger log = LoggerFactory.getLogger(AbscenceResource.class);

    private static final String ENTITY_NAME = "abscence";

    private final AbscenceRepository abscenceRepository;

    public AbscenceResource(AbscenceRepository abscenceRepository) {
        this.abscenceRepository = abscenceRepository;
    }

    /**
     * POST  /abscences : Create a new abscence.
     *
     * @param abscence the abscence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new abscence, or with status 400 (Bad Request) if the abscence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/abscences")
    public ResponseEntity<Abscence> createAbscence(@RequestBody Abscence abscence) throws URISyntaxException {
        log.debug("REST request to save Abscence : {}", abscence);
        if (abscence.getId() != null) {
            throw new BadRequestAlertException("A new abscence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Abscence result = abscenceRepository.save(abscence);
        return ResponseEntity.created(new URI("/api/abscences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /abscences : Updates an existing abscence.
     *
     * @param abscence the abscence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated abscence,
     * or with status 400 (Bad Request) if the abscence is not valid,
     * or with status 500 (Internal Server Error) if the abscence couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/abscences")
    public ResponseEntity<Abscence> updateAbscence(@RequestBody Abscence abscence) throws URISyntaxException {
        log.debug("REST request to update Abscence : {}", abscence);
        if (abscence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Abscence result = abscenceRepository.save(abscence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, abscence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /abscences : get all the abscences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of abscences in body
     */
    @GetMapping("/abscences")
    public List<Abscence> getAllAbscences() {
        log.debug("REST request to get all Abscences");
        return abscenceRepository.findAll();
    }

    /**
     * GET  /abscences/:id : get the "id" abscence.
     *
     * @param id the id of the abscence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the abscence, or with status 404 (Not Found)
     */
    @GetMapping("/abscences/{id}")
    public ResponseEntity<Abscence> getAbscence(@PathVariable Long id) {
        log.debug("REST request to get Abscence : {}", id);
        Optional<Abscence> abscence = abscenceRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(abscence);
    }

    /**
     * DELETE  /abscences/:id : delete the "id" abscence.
     *
     * @param id the id of the abscence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/abscences/{id}")
    public ResponseEntity<Void> deleteAbscence(@PathVariable Long id) {
        log.debug("REST request to delete Abscence : {}", id);
        abscenceRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
