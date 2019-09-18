package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.StudentHomework;
import com.software.zone.pro.eschool.repository.StudentHomeworkRepository;
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
 * REST controller for managing StudentHomework.
 */
@RestController
@RequestMapping("/api")
public class StudentHomeworkResource {

    private final Logger log = LoggerFactory.getLogger(StudentHomeworkResource.class);

    private static final String ENTITY_NAME = "studentHomework";

    private final StudentHomeworkRepository studentHomeworkRepository;

    public StudentHomeworkResource(StudentHomeworkRepository studentHomeworkRepository) {
        this.studentHomeworkRepository = studentHomeworkRepository;
    }

    /**
     * POST  /student-homeworks : Create a new studentHomework.
     *
     * @param studentHomework the studentHomework to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentHomework, or with status 400 (Bad Request) if the studentHomework has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-homeworks")
    public ResponseEntity<StudentHomework> createStudentHomework(@RequestBody StudentHomework studentHomework) throws URISyntaxException {
        log.debug("REST request to save StudentHomework : {}", studentHomework);
        if (studentHomework.getId() != null) {
            throw new BadRequestAlertException("A new studentHomework cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentHomework result = studentHomeworkRepository.save(studentHomework);
        return ResponseEntity.created(new URI("/api/student-homeworks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-homeworks : Updates an existing studentHomework.
     *
     * @param studentHomework the studentHomework to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentHomework,
     * or with status 400 (Bad Request) if the studentHomework is not valid,
     * or with status 500 (Internal Server Error) if the studentHomework couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-homeworks")
    public ResponseEntity<StudentHomework> updateStudentHomework(@RequestBody StudentHomework studentHomework) throws URISyntaxException {
        log.debug("REST request to update StudentHomework : {}", studentHomework);
        if (studentHomework.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentHomework result = studentHomeworkRepository.save(studentHomework);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentHomework.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-homeworks : get all the studentHomeworks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of studentHomeworks in body
     */
    @GetMapping("/student-homeworks")
    public List<StudentHomework> getAllStudentHomeworks() {
        log.debug("REST request to get all StudentHomeworks");
        return studentHomeworkRepository.findAll();
    }

    /**
     * GET  /student-homeworks/:id : get the "id" studentHomework.
     *
     * @param id the id of the studentHomework to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentHomework, or with status 404 (Not Found)
     */
    @GetMapping("/student-homeworks/{id}")
    public ResponseEntity<StudentHomework> getStudentHomework(@PathVariable Long id) {
        log.debug("REST request to get StudentHomework : {}", id);
        Optional<StudentHomework> studentHomework = studentHomeworkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(studentHomework);
    }

    /**
     * DELETE  /student-homeworks/:id : delete the "id" studentHomework.
     *
     * @param id the id of the studentHomework to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-homeworks/{id}")
    public ResponseEntity<Void> deleteStudentHomework(@PathVariable Long id) {
        log.debug("REST request to delete StudentHomework : {}", id);
        studentHomeworkRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
