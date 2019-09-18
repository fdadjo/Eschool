package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.SchoolAuthority;
import com.software.zone.pro.eschool.repository.SchoolAuthorityRepository;
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
 * REST controller for managing SchoolAuthority.
 */
@RestController
@RequestMapping("/api")
public class SchoolAuthorityResource {

    private final Logger log = LoggerFactory.getLogger(SchoolAuthorityResource.class);

    private static final String ENTITY_NAME = "schoolAuthority";

    private final SchoolAuthorityRepository schoolAuthorityRepository;

    public SchoolAuthorityResource(SchoolAuthorityRepository schoolAuthorityRepository) {
        this.schoolAuthorityRepository = schoolAuthorityRepository;
    }

    /**
     * POST  /school-authorities : Create a new schoolAuthority.
     *
     * @param schoolAuthority the schoolAuthority to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schoolAuthority, or with status 400 (Bad Request) if the schoolAuthority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/school-authorities")
    public ResponseEntity<SchoolAuthority> createSchoolAuthority(@RequestBody SchoolAuthority schoolAuthority) throws URISyntaxException {
        log.debug("REST request to save SchoolAuthority : {}", schoolAuthority);
        if (schoolAuthority.getId() != null) {
            throw new BadRequestAlertException("A new schoolAuthority cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchoolAuthority result = schoolAuthorityRepository.save(schoolAuthority);
        return ResponseEntity.created(new URI("/api/school-authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /school-authorities : Updates an existing schoolAuthority.
     *
     * @param schoolAuthority the schoolAuthority to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schoolAuthority,
     * or with status 400 (Bad Request) if the schoolAuthority is not valid,
     * or with status 500 (Internal Server Error) if the schoolAuthority couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/school-authorities")
    public ResponseEntity<SchoolAuthority> updateSchoolAuthority(@RequestBody SchoolAuthority schoolAuthority) throws URISyntaxException {
        log.debug("REST request to update SchoolAuthority : {}", schoolAuthority);
        if (schoolAuthority.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchoolAuthority result = schoolAuthorityRepository.save(schoolAuthority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schoolAuthority.getId().toString()))
            .body(result);
    }

    /**
     * GET  /school-authorities : get all the schoolAuthorities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schoolAuthorities in body
     */
    @GetMapping("/school-authorities")
    public List<SchoolAuthority> getAllSchoolAuthorities() {
        log.debug("REST request to get all SchoolAuthorities");
        return schoolAuthorityRepository.findAll();
    }

    /**
     * GET  /school-authorities/:id : get the "id" schoolAuthority.
     *
     * @param id the id of the schoolAuthority to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schoolAuthority, or with status 404 (Not Found)
     */
    @GetMapping("/school-authorities/{id}")
    public ResponseEntity<SchoolAuthority> getSchoolAuthority(@PathVariable Long id) {
        log.debug("REST request to get SchoolAuthority : {}", id);
        Optional<SchoolAuthority> schoolAuthority = schoolAuthorityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(schoolAuthority);
    }

    /**
     * DELETE  /school-authorities/:id : delete the "id" schoolAuthority.
     *
     * @param id the id of the schoolAuthority to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/school-authorities/{id}")
    public ResponseEntity<Void> deleteSchoolAuthority(@PathVariable Long id) {
        log.debug("REST request to delete SchoolAuthority : {}", id);
        schoolAuthorityRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
