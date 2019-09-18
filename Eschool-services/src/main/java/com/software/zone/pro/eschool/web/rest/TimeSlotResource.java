package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.TimeSlot;
import com.software.zone.pro.eschool.repository.TimeSlotRepository;
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
 * REST controller for managing TimeSlot.
 */
@RestController
@RequestMapping("/api")
public class TimeSlotResource {

    private final Logger log = LoggerFactory.getLogger(TimeSlotResource.class);

    private static final String ENTITY_NAME = "timeSlot";

    private final TimeSlotRepository timeSlotRepository;

    public TimeSlotResource(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    /**
     * POST  /time-slots : Create a new timeSlot.
     *
     * @param timeSlot the timeSlot to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeSlot, or with status 400 (Bad Request) if the timeSlot has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/time-slots")
    public ResponseEntity<TimeSlot> createTimeSlot(@RequestBody TimeSlot timeSlot) throws URISyntaxException {
        log.debug("REST request to save TimeSlot : {}", timeSlot);
        if (timeSlot.getId() != null) {
            throw new BadRequestAlertException("A new timeSlot cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimeSlot result = timeSlotRepository.save(timeSlot);
        return ResponseEntity.created(new URI("/api/time-slots/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-slots : Updates an existing timeSlot.
     *
     * @param timeSlot the timeSlot to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeSlot,
     * or with status 400 (Bad Request) if the timeSlot is not valid,
     * or with status 500 (Internal Server Error) if the timeSlot couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-slots")
    public ResponseEntity<TimeSlot> updateTimeSlot(@RequestBody TimeSlot timeSlot) throws URISyntaxException {
        log.debug("REST request to update TimeSlot : {}", timeSlot);
        if (timeSlot.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TimeSlot result = timeSlotRepository.save(timeSlot);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeSlot.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-slots : get all the timeSlots.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of timeSlots in body
     */
    @GetMapping("/time-slots")
    public List<TimeSlot> getAllTimeSlots() {
        log.debug("REST request to get all TimeSlots");
        return timeSlotRepository.findAll();
    }

    /**
     * GET  /time-slots/:id : get the "id" timeSlot.
     *
     * @param id the id of the timeSlot to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeSlot, or with status 404 (Not Found)
     */
    @GetMapping("/time-slots/{id}")
    public ResponseEntity<TimeSlot> getTimeSlot(@PathVariable Long id) {
        log.debug("REST request to get TimeSlot : {}", id);
        Optional<TimeSlot> timeSlot = timeSlotRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(timeSlot);
    }

    /**
     * DELETE  /time-slots/:id : delete the "id" timeSlot.
     *
     * @param id the id of the timeSlot to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/time-slots/{id}")
    public ResponseEntity<Void> deleteTimeSlot(@PathVariable Long id) {
        log.debug("REST request to delete TimeSlot : {}", id);
        timeSlotRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
