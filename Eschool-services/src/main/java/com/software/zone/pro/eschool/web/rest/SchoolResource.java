package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.School;
import com.software.zone.pro.eschool.domain.User;
import com.software.zone.pro.eschool.repository.SchoolRepository;
import com.software.zone.pro.eschool.security.AuthoritiesConstants;
import com.software.zone.pro.eschool.service.SchoolService;
import com.software.zone.pro.eschool.service.UserService;
import com.software.zone.pro.eschool.web.rest.errors.BadRequestAlertException;
import com.software.zone.pro.eschool.web.rest.util.HeaderUtil;
import com.software.zone.pro.eschool.web.rest.util.PageableSize;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing School.
 */
@RestController
@RequestMapping("/api")
public class SchoolResource {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserService userService;

    private final Logger log = LoggerFactory.getLogger(SchoolResource.class);

    private static final String ENTITY_NAME = "school";

    public SchoolResource(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    /**
     * POST  /schools : Create a new school.
     *
     * @param school the school to create
     * @return the ResponseEntity with status 201 (Created) and with body the new school, or with status 400 (Bad Request) if the school has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/schools")
    public ResponseEntity<School> createSchool(@RequestBody School school) throws URISyntaxException {
        log.debug("REST request to save School : {}", school);
        if (school.getId() != null) {
            throw new BadRequestAlertException("A new school cannot already have an ID", ENTITY_NAME, "idexists");
        }
        School result = schoolRepository.save(school);
        return ResponseEntity.created(new URI("/api/schools/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /schools : Updates an existing school.
     *
     * @param school the school to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated school,
     * or with status 400 (Bad Request) if the school is not valid,
     * or with status 500 (Internal Server Error) if the school couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/schools")
    public ResponseEntity<School> updateSchool(@RequestBody School school) throws URISyntaxException {
        log.debug("REST request to update School : {}", school);
        if (school.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        School result = schoolRepository.save(school);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, school.getId().toString()))
            .body(result);
    }

    /**
     * GET  /schools : get all the schools.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of schools in body
     */
    @GetMapping("/schools")
    public List<School> getAllSchools() {
        log.debug("REST request to get all Schools");
        return schoolRepository.findAll();
    }

    /**
     * GET  /schools/:id : get the "id" school.
     *
     * @param id the id of the school to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the school, or with status 404 (Not Found)
     */
    @GetMapping("/schools/{id}")
    public ResponseEntity<School> getSchool(@PathVariable Long id) {
        log.debug("REST request to get School : {}", id);
        Optional<School> school = schoolRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(school);
    }

    /**
     * DELETE  /schools/:id : delete the "id" school.
     *
     * @param id the id of the school to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/schools/{id}")
    public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
        log.debug("REST request to delete School : {}", id);
        schoolRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/schools/user/{idUser}")
    public List<School> getSchoolsByUserId(@PathVariable Long idUser,
                                           @PageableDefault(value = PageableSize.SIZE, sort = { "id" }, direction = Sort.Direction.DESC)Pageable pageable) {
        log.debug("REST request to get all Schools from user id : {}", idUser);
        Page<School> schoolsPage = null;
        List<School> schoolsList = null;

        User user = userService.getUserWithAuthorities(idUser);

        if (user != null) {
            if (user.getAuthorities().stream().map(auth -> auth.getAuthority()).collect(Collectors.toSet()).toString().contains(AuthoritiesConstants.ADMIN)) {
                schoolsPage = schoolService.findAll(pageable);
            } else {
                schoolsList = schoolService.getSchoolsByUserId(idUser);
            }
        }

        return schoolsList;
    }

}
