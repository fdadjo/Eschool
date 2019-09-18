package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.TimeSlotHistory;
import com.software.zone.pro.eschool.repository.TimeSlotHistoryRepository;
import com.software.zone.pro.eschool.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.software.zone.pro.eschool.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimeSlotHistoryResource REST controller.
 *
 * @see TimeSlotHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class TimeSlotHistoryResourceIntTest {

    @Autowired
    private TimeSlotHistoryRepository timeSlotHistoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTimeSlotHistoryMockMvc;

    private TimeSlotHistory timeSlotHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimeSlotHistoryResource timeSlotHistoryResource = new TimeSlotHistoryResource(timeSlotHistoryRepository);
        this.restTimeSlotHistoryMockMvc = MockMvcBuilders.standaloneSetup(timeSlotHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeSlotHistory createEntity(EntityManager em) {
        TimeSlotHistory timeSlotHistory = new TimeSlotHistory();
        return timeSlotHistory;
    }

    @Before
    public void initTest() {
        timeSlotHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeSlotHistory() throws Exception {
        int databaseSizeBeforeCreate = timeSlotHistoryRepository.findAll().size();

        // Create the TimeSlotHistory
        restTimeSlotHistoryMockMvc.perform(post("/api/time-slot-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSlotHistory)))
            .andExpect(status().isCreated());

        // Validate the TimeSlotHistory in the database
        List<TimeSlotHistory> timeSlotHistoryList = timeSlotHistoryRepository.findAll();
        assertThat(timeSlotHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        TimeSlotHistory testTimeSlotHistory = timeSlotHistoryList.get(timeSlotHistoryList.size() - 1);
    }

    @Test
    @Transactional
    public void createTimeSlotHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeSlotHistoryRepository.findAll().size();

        // Create the TimeSlotHistory with an existing ID
        timeSlotHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeSlotHistoryMockMvc.perform(post("/api/time-slot-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSlotHistory)))
            .andExpect(status().isBadRequest());

        // Validate the TimeSlotHistory in the database
        List<TimeSlotHistory> timeSlotHistoryList = timeSlotHistoryRepository.findAll();
        assertThat(timeSlotHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimeSlotHistories() throws Exception {
        // Initialize the database
        timeSlotHistoryRepository.saveAndFlush(timeSlotHistory);

        // Get all the timeSlotHistoryList
        restTimeSlotHistoryMockMvc.perform(get("/api/time-slot-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeSlotHistory.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTimeSlotHistory() throws Exception {
        // Initialize the database
        timeSlotHistoryRepository.saveAndFlush(timeSlotHistory);

        // Get the timeSlotHistory
        restTimeSlotHistoryMockMvc.perform(get("/api/time-slot-histories/{id}", timeSlotHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeSlotHistory.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeSlotHistory() throws Exception {
        // Get the timeSlotHistory
        restTimeSlotHistoryMockMvc.perform(get("/api/time-slot-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeSlotHistory() throws Exception {
        // Initialize the database
        timeSlotHistoryRepository.saveAndFlush(timeSlotHistory);

        int databaseSizeBeforeUpdate = timeSlotHistoryRepository.findAll().size();

        // Update the timeSlotHistory
        TimeSlotHistory updatedTimeSlotHistory = timeSlotHistoryRepository.findById(timeSlotHistory.getId()).get();
        // Disconnect from session so that the updates on updatedTimeSlotHistory are not directly saved in db
        em.detach(updatedTimeSlotHistory);

        restTimeSlotHistoryMockMvc.perform(put("/api/time-slot-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimeSlotHistory)))
            .andExpect(status().isOk());

        // Validate the TimeSlotHistory in the database
        List<TimeSlotHistory> timeSlotHistoryList = timeSlotHistoryRepository.findAll();
        assertThat(timeSlotHistoryList).hasSize(databaseSizeBeforeUpdate);
        TimeSlotHistory testTimeSlotHistory = timeSlotHistoryList.get(timeSlotHistoryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeSlotHistory() throws Exception {
        int databaseSizeBeforeUpdate = timeSlotHistoryRepository.findAll().size();

        // Create the TimeSlotHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTimeSlotHistoryMockMvc.perform(put("/api/time-slot-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeSlotHistory)))
            .andExpect(status().isBadRequest());

        // Validate the TimeSlotHistory in the database
        List<TimeSlotHistory> timeSlotHistoryList = timeSlotHistoryRepository.findAll();
        assertThat(timeSlotHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTimeSlotHistory() throws Exception {
        // Initialize the database
        timeSlotHistoryRepository.saveAndFlush(timeSlotHistory);

        int databaseSizeBeforeDelete = timeSlotHistoryRepository.findAll().size();

        // Delete the timeSlotHistory
        restTimeSlotHistoryMockMvc.perform(delete("/api/time-slot-histories/{id}", timeSlotHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeSlotHistory> timeSlotHistoryList = timeSlotHistoryRepository.findAll();
        assertThat(timeSlotHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeSlotHistory.class);
        TimeSlotHistory timeSlotHistory1 = new TimeSlotHistory();
        timeSlotHistory1.setId(1L);
        TimeSlotHistory timeSlotHistory2 = new TimeSlotHistory();
        timeSlotHistory2.setId(timeSlotHistory1.getId());
        assertThat(timeSlotHistory1).isEqualTo(timeSlotHistory2);
        timeSlotHistory2.setId(2L);
        assertThat(timeSlotHistory1).isNotEqualTo(timeSlotHistory2);
        timeSlotHistory1.setId(null);
        assertThat(timeSlotHistory1).isNotEqualTo(timeSlotHistory2);
    }
}
