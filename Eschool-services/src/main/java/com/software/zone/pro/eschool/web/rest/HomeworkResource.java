package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.Homework;
import com.software.zone.pro.eschool.repository.HomeworkRepository;
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
 * REST controller for managing Homework.
 */
@RestController
@RequestMapping("/api")
public class HomeworkResource {

    private final Logger log = LoggerFactory.getLogger(HomeworkResource.class);

    private static final String ENTITY_NAME = "homework";

    private final HomeworkRepository homeworkRepository;

    public HomeworkResource(HomeworkRepository homeworkRepository) {
        this.homeworkRepository = homeworkRepository;
    }

    /**
     * POST  /homework : Create a new homework.
     *
     * @param homework the homework to create
     * @return the ResponseEntity with status 201 (Created) and with body the new homework, or with status 400 (Bad Request) if the homework has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/homework")
    public ResponseEntity<Homework> createHomework(@RequestBody Homework homework) throws URISyntaxException {
        log.debug("REST request to save Homework : {}", homework);
        if (homework.getId() != null) {
            throw new BadRequestAlertException("A new homework cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Homework result = homeworkRepository.save(homework);
        return ResponseEntity.created(new URI("/api/homework/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /homework : Updates an existing homework.
     *
     * @param homework the homework to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated homework,
     * or with status 400 (Bad Request) if the homework is not valid,
     * or with status 500 (Internal Server Error) if the homework couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/homework")
    public ResponseEntity<Homework> updateHomework(@RequestBody Homework homework) throws URISyntaxException {
        log.debug("REST request to update Homework : {}", homework);
        if (homework.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Homework result = homeworkRepository.save(homework);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, homework.getId().toString()))
            .body(result);
    }

    /**
     * GET  /homework : get all the homework.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of homework in body
     */
    @GetMapping("/homework")
    public List<Homework> getAllHomework() {
        log.debug("REST request to get all Homework");
        return homeworkRepository.findAll();
    }

    /**
     * GET  /homework/:id : get the "id" homework.
     *
     * @param id the id of the homework to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the homework, or with status 404 (Not Found)
     */
    @GetMapping("/homework/{id}")
    public ResponseEntity<Homework> getHomework(@PathVariable Long id) {
        log.debug("REST request to get Homework : {}", id);
        Optional<Homework> homework = homeworkRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(homework);
    }

    /**
     * DELETE  /homework/:id : delete the "id" homework.
     *
     * @param id the id of the homework to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/homework/{id}")
    public ResponseEntity<Void> deleteHomework(@PathVariable Long id) {
        log.debug("REST request to delete Homework : {}", id);
        homeworkRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
