package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.LessonHistoryPreparation;
import com.software.zone.pro.eschool.repository.LessonHistoryPreparationRepository;
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
 * REST controller for managing LessonHistoryPreparation.
 */
@RestController
@RequestMapping("/api")
public class LessonHistoryPreparationResource {

    private final Logger log = LoggerFactory.getLogger(LessonHistoryPreparationResource.class);

    private static final String ENTITY_NAME = "lessonHistoryPreparation";

    private final LessonHistoryPreparationRepository lessonHistoryPreparationRepository;

    public LessonHistoryPreparationResource(LessonHistoryPreparationRepository lessonHistoryPreparationRepository) {
        this.lessonHistoryPreparationRepository = lessonHistoryPreparationRepository;
    }

    /**
     * POST  /lesson-history-preparations : Create a new lessonHistoryPreparation.
     *
     * @param lessonHistoryPreparation the lessonHistoryPreparation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lessonHistoryPreparation, or with status 400 (Bad Request) if the lessonHistoryPreparation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lesson-history-preparations")
    public ResponseEntity<LessonHistoryPreparation> createLessonHistoryPreparation(@RequestBody LessonHistoryPreparation lessonHistoryPreparation) throws URISyntaxException {
        log.debug("REST request to save LessonHistoryPreparation : {}", lessonHistoryPreparation);
        if (lessonHistoryPreparation.getId() != null) {
            throw new BadRequestAlertException("A new lessonHistoryPreparation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LessonHistoryPreparation result = lessonHistoryPreparationRepository.save(lessonHistoryPreparation);
        return ResponseEntity.created(new URI("/api/lesson-history-preparations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lesson-history-preparations : Updates an existing lessonHistoryPreparation.
     *
     * @param lessonHistoryPreparation the lessonHistoryPreparation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lessonHistoryPreparation,
     * or with status 400 (Bad Request) if the lessonHistoryPreparation is not valid,
     * or with status 500 (Internal Server Error) if the lessonHistoryPreparation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lesson-history-preparations")
    public ResponseEntity<LessonHistoryPreparation> updateLessonHistoryPreparation(@RequestBody LessonHistoryPreparation lessonHistoryPreparation) throws URISyntaxException {
        log.debug("REST request to update LessonHistoryPreparation : {}", lessonHistoryPreparation);
        if (lessonHistoryPreparation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LessonHistoryPreparation result = lessonHistoryPreparationRepository.save(lessonHistoryPreparation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lessonHistoryPreparation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lesson-history-preparations : get all the lessonHistoryPreparations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lessonHistoryPreparations in body
     */
    @GetMapping("/lesson-history-preparations")
    public List<LessonHistoryPreparation> getAllLessonHistoryPreparations() {
        log.debug("REST request to get all LessonHistoryPreparations");
        return lessonHistoryPreparationRepository.findAll();
    }

    /**
     * GET  /lesson-history-preparations/:id : get the "id" lessonHistoryPreparation.
     *
     * @param id the id of the lessonHistoryPreparation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lessonHistoryPreparation, or with status 404 (Not Found)
     */
    @GetMapping("/lesson-history-preparations/{id}")
    public ResponseEntity<LessonHistoryPreparation> getLessonHistoryPreparation(@PathVariable Long id) {
        log.debug("REST request to get LessonHistoryPreparation : {}", id);
        Optional<LessonHistoryPreparation> lessonHistoryPreparation = lessonHistoryPreparationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lessonHistoryPreparation);
    }

    /**
     * DELETE  /lesson-history-preparations/:id : delete the "id" lessonHistoryPreparation.
     *
     * @param id the id of the lessonHistoryPreparation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lesson-history-preparations/{id}")
    public ResponseEntity<Void> deleteLessonHistoryPreparation(@PathVariable Long id) {
        log.debug("REST request to delete LessonHistoryPreparation : {}", id);
        lessonHistoryPreparationRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
