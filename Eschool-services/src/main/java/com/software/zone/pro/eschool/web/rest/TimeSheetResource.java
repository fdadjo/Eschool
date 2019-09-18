package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.TimeSheet;
import com.software.zone.pro.eschool.repository.TimeSheetRepository;
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
 * REST controller for managing TimeSheet.
 */
@RestController
@RequestMapping("/api")
public class TimeSheetResource {

    private final Logger log = LoggerFactory.getLogger(TimeSheetResource.class);

    private static final String ENTITY_NAME = "timeSheet";

    private final TimeSheetRepository timeSheetRepository;

    public TimeSheetResource(TimeSheetRepository timeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
    }

    /**
     * POST  /time-sheets : Create a new timeSheet.
     *
     * @param timeSheet the timeSheet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeSheet, or with status 400 (Bad Request) if the timeSheet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/time-sheets")
    public ResponseEntity<TimeSheet> createTimeSheet(@RequestBody TimeSheet timeSheet) throws URISyntaxException {
        log.debug("REST request to save TimeSheet : {}", timeSheet);
        if (timeSheet.getId() != null) {
            throw new BadRequestAlertException("A new timeSheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeSheet result = timeSheetRepository.save(timeSheet);
        return ResponseEntity.created(new URI("/api/time-sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-sheets : Updates an existing timeSheet.
     *
     * @param timeSheet the timeSheet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeSheet,
     * or with status 400 (Bad Request) if the timeSheet is not valid,
     * or with status 500 (Internal Server Error) if the timeSheet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-sheets")
    public ResponseEntity<TimeSheet> updateTimeSheet(@RequestBody TimeSheet timeSheet) throws URISyntaxException {
        log.debug("REST request to update TimeSheet : {}", timeSheet);
        if (timeSheet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeSheet result = timeSheetRepository.save(timeSheet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeSheet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-sheets : get all the timeSheets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of timeSheets in body
     */
    @GetMapping("/time-sheets")
    public List<TimeSheet> getAllTimeSheets() {
        log.debug("REST request to get all TimeSheets");
        return timeSheetRepository.findAll();
    }

    /**
     * GET  /time-sheets/:id : get the "id" timeSheet.
     *
     * @param id the id of the timeSheet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeSheet, or with status 404 (Not Found)
     */
    @GetMapping("/time-sheets/{id}")
    public ResponseEntity<TimeSheet> getTimeSheet(@PathVariable Long id) {
        log.debug("REST request to get TimeSheet : {}", id);
        Optional<TimeSheet> timeSheet = timeSheetRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(timeSheet);
    }

    /**
     * DELETE  /time-sheets/:id : delete the "id" timeSheet.
     *
     * @param id the id of the timeSheet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/time-sheets/{id}")
    public ResponseEntity<Void> deleteTimeSheet(@PathVariable Long id) {
        log.debug("REST request to delete TimeSheet : {}", id);
        timeSheetRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
