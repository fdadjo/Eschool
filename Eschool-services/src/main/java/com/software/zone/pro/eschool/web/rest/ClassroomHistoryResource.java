package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.ClassroomHistory;
import com.software.zone.pro.eschool.repository.ClassroomHistoryRepository;
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
 * REST controller for managing ClassroomHistory.
 */
@RestController
@RequestMapping("/api")
public class ClassroomHistoryResource {

    private final Logger log = LoggerFactory.getLogger(ClassroomHistoryResource.class);

    private static final String ENTITY_NAME = "classroomHistory";

    private final ClassroomHistoryRepository classroomHistoryRepository;

    public ClassroomHistoryResource(ClassroomHistoryRepository classroomHistoryRepository) {
        this.classroomHistoryRepository = classroomHistoryRepository;
    }

    /**
     * POST  /classroom-histories : Create a new classroomHistory.
     *
     * @param classroomHistory the classroomHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classroomHistory, or with status 400 (Bad Request) if the classroomHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/classroom-histories")
    public ResponseEntity<ClassroomHistory> createClassroomHistory(@RequestBody ClassroomHistory classroomHistory) throws URISyntaxException {
        log.debug("REST request to save ClassroomHistory : {}", classroomHistory);
        if (classroomHistory.getId() != null) {
            throw new BadRequestAlertException("A new classroomHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassroomHistory result = classroomHistoryRepository.save(classroomHistory);
        return ResponseEntity.created(new URI("/api/classroom-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /classroom-histories : Updates an existing classroomHistory.
     *
     * @param classroomHistory the classroomHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classroomHistory,
     * or with status 400 (Bad Request) if the classroomHistory is not valid,
     * or with status 500 (Internal Server Error) if the classroomHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/classroom-histories")
    public ResponseEntity<ClassroomHistory> updateClassroomHistory(@RequestBody ClassroomHistory classroomHistory) throws URISyntaxException {
        log.debug("REST request to update ClassroomHistory : {}", classroomHistory);
        if (classroomHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassroomHistory result = classroomHistoryRepository.save(classroomHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classroomHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /classroom-histories : get all the classroomHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of classroomHistories in body
     */
    @GetMapping("/classroom-histories")
    public List<ClassroomHistory> getAllClassroomHistories() {
        log.debug("REST request to get all ClassroomHistories");
        return classroomHistoryRepository.findAll();
    }

    /**
     * GET  /classroom-histories/:id : get the "id" classroomHistory.
     *
     * @param id the id of the classroomHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classroomHistory, or with status 404 (Not Found)
     */
    @GetMapping("/classroom-histories/{id}")
    public ResponseEntity<ClassroomHistory> getClassroomHistory(@PathVariable Long id) {
        log.debug("REST request to get ClassroomHistory : {}", id);
        Optional<ClassroomHistory> classroomHistory = classroomHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(classroomHistory);
    }

    /**
     * DELETE  /classroom-histories/:id : delete the "id" classroomHistory.
     *
     * @param id the id of the classroomHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/classroom-histories/{id}")
    public ResponseEntity<Void> deleteClassroomHistory(@PathVariable Long id) {
        log.debug("REST request to delete ClassroomHistory : {}", id);
        classroomHistoryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
