package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.LessonHistory;
import com.software.zone.pro.eschool.repository.LessonHistoryRepository;
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
 * REST controller for managing LessonHistory.
 */
@RestController
@RequestMapping("/api")
public class LessonHistoryResource {

    private final Logger log = LoggerFactory.getLogger(LessonHistoryResource.class);

    private static final String ENTITY_NAME = "lessonHistory";

    private final LessonHistoryRepository lessonHistoryRepository;

    public LessonHistoryResource(LessonHistoryRepository lessonHistoryRepository) {
        this.lessonHistoryRepository = lessonHistoryRepository;
    }

    /**
     * POST  /lesson-histories : Create a new lessonHistory.
     *
     * @param lessonHistory the lessonHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lessonHistory, or with status 400 (Bad Request) if the lessonHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/lesson-histories")
    public ResponseEntity<LessonHistory> createLessonHistory(@RequestBody LessonHistory lessonHistory) throws URISyntaxException {
        log.debug("REST request to save LessonHistory : {}", lessonHistory);
        if (lessonHistory.getId() != null) {
            throw new BadRequestAlertException("A new lessonHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LessonHistory result = lessonHistoryRepository.save(lessonHistory);
        return ResponseEntity.created(new URI("/api/lesson-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lesson-histories : Updates an existing lessonHistory.
     *
     * @param lessonHistory the lessonHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lessonHistory,
     * or with status 400 (Bad Request) if the lessonHistory is not valid,
     * or with status 500 (Internal Server Error) if the lessonHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/lesson-histories")
    public ResponseEntity<LessonHistory> updateLessonHistory(@RequestBody LessonHistory lessonHistory) throws URISyntaxException {
        log.debug("REST request to update LessonHistory : {}", lessonHistory);
        if (lessonHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LessonHistory result = lessonHistoryRepository.save(lessonHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lessonHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lesson-histories : get all the lessonHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of lessonHistories in body
     */
    @GetMapping("/lesson-histories")
    public List<LessonHistory> getAllLessonHistories() {
        log.debug("REST request to get all LessonHistories");
        return lessonHistoryRepository.findAll();
    }

    /**
     * GET  /lesson-histories/:id : get the "id" lessonHistory.
     *
     * @param id the id of the lessonHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lessonHistory, or with status 404 (Not Found)
     */
    @GetMapping("/lesson-histories/{id}")
    public ResponseEntity<LessonHistory> getLessonHistory(@PathVariable Long id) {
        log.debug("REST request to get LessonHistory : {}", id);
        Optional<LessonHistory> lessonHistory = lessonHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(lessonHistory);
    }

    /**
     * DELETE  /lesson-histories/:id : delete the "id" lessonHistory.
     *
     * @param id the id of the lessonHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/lesson-histories/{id}")
    public ResponseEntity<Void> deleteLessonHistory(@PathVariable Long id) {
        log.debug("REST request to delete LessonHistory : {}", id);
        lessonHistoryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
