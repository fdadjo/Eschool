package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.TimeSlotHistory;
import com.software.zone.pro.eschool.repository.TimeSlotHistoryRepository;
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
 * REST controller for managing TimeSlotHistory.
 */
@RestController
@RequestMapping("/api")
public class TimeSlotHistoryResource {

    private final Logger log = LoggerFactory.getLogger(TimeSlotHistoryResource.class);

    private static final String ENTITY_NAME = "timeSlotHistory";

    private final TimeSlotHistoryRepository timeSlotHistoryRepository;

    public TimeSlotHistoryResource(TimeSlotHistoryRepository timeSlotHistoryRepository) {
        this.timeSlotHistoryRepository = timeSlotHistoryRepository;
    }

    /**
     * POST  /time-slot-histories : Create a new timeSlotHistory.
     *
     * @param timeSlotHistory the timeSlotHistory to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeSlotHistory, or with status 400 (Bad Request) if the timeSlotHistory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/time-slot-histories")
    public ResponseEntity<TimeSlotHistory> createTimeSlotHistory(@RequestBody TimeSlotHistory timeSlotHistory) throws URISyntaxException {
        log.debug("REST request to save TimeSlotHistory : {}", timeSlotHistory);
        if (timeSlotHistory.getId() != null) {
            throw new BadRequestAlertException("A new timeSlotHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeSlotHistory result = timeSlotHistoryRepository.save(timeSlotHistory);
        return ResponseEntity.created(new URI("/api/time-slot-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-slot-histories : Updates an existing timeSlotHistory.
     *
     * @param timeSlotHistory the timeSlotHistory to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeSlotHistory,
     * or with status 400 (Bad Request) if the timeSlotHistory is not valid,
     * or with status 500 (Internal Server Error) if the timeSlotHistory couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-slot-histories")
    public ResponseEntity<TimeSlotHistory> updateTimeSlotHistory(@RequestBody TimeSlotHistory timeSlotHistory) throws URISyntaxException {
        log.debug("REST request to update TimeSlotHistory : {}", timeSlotHistory);
        if (timeSlotHistory.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeSlotHistory result = timeSlotHistoryRepository.save(timeSlotHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeSlotHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-slot-histories : get all the timeSlotHistories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of timeSlotHistories in body
     */
    @GetMapping("/time-slot-histories")
    public List<TimeSlotHistory> getAllTimeSlotHistories() {
        log.debug("REST request to get all TimeSlotHistories");
        return timeSlotHistoryRepository.findAll();
    }

    /**
     * GET  /time-slot-histories/:id : get the "id" timeSlotHistory.
     *
     * @param id the id of the timeSlotHistory to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeSlotHistory, or with status 404 (Not Found)
     */
    @GetMapping("/time-slot-histories/{id}")
    public ResponseEntity<TimeSlotHistory> getTimeSlotHistory(@PathVariable Long id) {
        log.debug("REST request to get TimeSlotHistory : {}", id);
        Optional<TimeSlotHistory> timeSlotHistory = timeSlotHistoryRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(timeSlotHistory);
    }

    /**
     * DELETE  /time-slot-histories/:id : delete the "id" timeSlotHistory.
     *
     * @param id the id of the timeSlotHistory to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/time-slot-histories/{id}")
    public ResponseEntity<Void> deleteTimeSlotHistory(@PathVariable Long id) {
        log.debug("REST request to delete TimeSlotHistory : {}", id);
        timeSlotHistoryRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
