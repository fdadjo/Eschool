package com.software.zone.pro.eschool.web.rest;
import com.software.zone.pro.eschool.domain.Job;
import com.software.zone.pro.eschool.repository.JobRepository;
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
 * REST controller for managing Job.
 */
@RestController
@RequestMapping("/api")
public class JobResource {

    private final Logger log = LoggerFactory.getLogger(JobResource.class);

    private static final String ENTITY_NAME = "job";

    private final JobRepository jobRepository;

    public JobResource(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    /**
     * POST  /jobs : Create a new job.
     *
     * @param job the job to create
     * @return the ResponseEntity with status 201 (Created) and with body the new job, or with status 400 (Bad Request) if the job has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/jobs")
    public ResponseEntity<Job> createJob(@RequestBody Job job) throws URISyntaxException {
        log.debug("REST request to save Job : {}", job);
        if (job.getId() != null) {
            throw new BadRequestAlertException("A new job cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Job result = jobRepository.save(job);
        return ResponseEntity.created(new URI("/api/jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jobs : Updates an existing job.
     *
     * @param job the job to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated job,
     * or with status 400 (Bad Request) if the job is not valid,
     * or with status 500 (Internal Server Error) if the job couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/jobs")
    public ResponseEntity<Job> updateJob(@RequestBody Job job) throws URISyntaxException {
        log.debug("REST request to update Job : {}", job);
        if (job.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Job result = jobRepository.save(job);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, job.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jobs : get all the jobs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of jobs in body
     */
    @GetMapping("/jobs")
    public List<Job> getAllJobs() {
        log.debug("REST request to get all Jobs");
        return jobRepository.findAll();
    }

    /**
     * GET  /jobs/:id : get the "id" job.
     *
     * @param id the id of the job to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the job, or with status 404 (Not Found)
     */
    @GetMapping("/jobs/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Long id) {
        log.debug("REST request to get Job : {}", id);
        Optional<Job> job = jobRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(job);
    }

    /**
     * DELETE  /jobs/:id : delete the "id" job.
     *
     * @param id the id of the job to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        log.debug("REST request to delete Job : {}", id);
        jobRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
