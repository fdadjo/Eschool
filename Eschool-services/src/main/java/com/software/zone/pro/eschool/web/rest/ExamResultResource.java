package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.ExamResult;
import com.software.zone.pro.eschool.repository.ExamResultRepository;
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
 * REST controller for managing ExamResult.
 */
@RestController
@RequestMapping("/api")
public class ExamResultResource {

    private final Logger log = LoggerFactory.getLogger(ExamResultResource.class);

    private static final String ENTITY_NAME = "examResult";

    private final ExamResultRepository examResultRepository;

    public ExamResultResource(ExamResultRepository examResultRepository) {
        this.examResultRepository = examResultRepository;
    }

    /**
     * POST  /exam-results : Create a new examResult.
     *
     * @param examResult the examResult to create
     * @return the ResponseEntity with status 201 (Created) and with body the new examResult, or with status 400 (Bad Request) if the examResult has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/exam-results")
    public ResponseEntity<ExamResult> createExamResult(@RequestBody ExamResult examResult) throws URISyntaxException {
        log.debug("REST request to save ExamResult : {}", examResult);
        if (examResult.getId() != null) {
            throw new BadRequestAlertException("A new examResult cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExamResult result = examResultRepository.save(examResult);
        return ResponseEntity.created(new URI("/api/exam-results/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /exam-results : Updates an existing examResult.
     *
     * @param examResult the examResult to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated examResult,
     * or with status 400 (Bad Request) if the examResult is not valid,
     * or with status 500 (Internal Server Error) if the examResult couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/exam-results")
    public ResponseEntity<ExamResult> updateExamResult(@RequestBody ExamResult examResult) throws URISyntaxException {
        log.debug("REST request to update ExamResult : {}", examResult);
        if (examResult.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ExamResult result = examResultRepository.save(examResult);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, examResult.getId().toString()))
            .body(result);
    }

    /**
     * GET  /exam-results : get all the examResults.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of examResults in body
     */
    @GetMapping("/exam-results")
    public List<ExamResult> getAllExamResults() {
        log.debug("REST request to get all ExamResults");
        return examResultRepository.findAll();
    }

    /**
     * GET  /exam-results/:id : get the "id" examResult.
     *
     * @param id the id of the examResult to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the examResult, or with status 404 (Not Found)
     */
    @GetMapping("/exam-results/{id}")
    public ResponseEntity<ExamResult> getExamResult(@PathVariable Long id) {
        log.debug("REST request to get ExamResult : {}", id);
        Optional<ExamResult> examResult = examResultRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(examResult);
    }

    /**
     * DELETE  /exam-results/:id : delete the "id" examResult.
     *
     * @param id the id of the examResult to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/exam-results/{id}")
    public ResponseEntity<Void> deleteExamResult(@PathVariable Long id) {
        log.debug("REST request to delete ExamResult : {}", id);
        examResultRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
