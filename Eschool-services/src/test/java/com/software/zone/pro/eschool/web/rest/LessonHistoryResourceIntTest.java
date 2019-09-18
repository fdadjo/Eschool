package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.LessonHistory;
import com.software.zone.pro.eschool.repository.LessonHistoryRepository;
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
 * Test class for the LessonHistoryResource REST controller.
 *
 * @see LessonHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class LessonHistoryResourceIntTest {

    private static final String DEFAULT_COURSE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COURSE_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_COEF = 1D;
    private static final Double UPDATED_COEF = 2D;

    @Autowired
    private LessonHistoryRepository lessonHistoryRepository;

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

    private MockMvc restLessonHistoryMockMvc;

    private LessonHistory lessonHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LessonHistoryResource lessonHistoryResource = new LessonHistoryResource(lessonHistoryRepository);
        this.restLessonHistoryMockMvc = MockMvcBuilders.standaloneSetup(lessonHistoryResource)
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
    public static LessonHistory createEntity(EntityManager em) {
        LessonHistory lessonHistory = new LessonHistory()
            .courseName(DEFAULT_COURSE_NAME)
            .coef(DEFAULT_COEF);
        return lessonHistory;
    }

    @Before
    public void initTest() {
        lessonHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createLessonHistory() throws Exception {
        int databaseSizeBeforeCreate = lessonHistoryRepository.findAll().size();

        // Create the LessonHistory
        restLessonHistoryMockMvc.perform(post("/api/lesson-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonHistory)))
            .andExpect(status().isCreated());

        // Validate the LessonHistory in the database
        List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAll();
        assertThat(lessonHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        LessonHistory testLessonHistory = lessonHistoryList.get(lessonHistoryList.size() - 1);
        assertThat(testLessonHistory.getCourseName()).isEqualTo(DEFAULT_COURSE_NAME);
        assertThat(testLessonHistory.getCoef()).isEqualTo(DEFAULT_COEF);
    }

    @Test
    @Transactional
    public void createLessonHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lessonHistoryRepository.findAll().size();

        // Create the LessonHistory with an existing ID
        lessonHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLessonHistoryMockMvc.perform(post("/api/lesson-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonHistory)))
            .andExpect(status().isBadRequest());

        // Validate the LessonHistory in the database
        List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAll();
        assertThat(lessonHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLessonHistories() throws Exception {
        // Initialize the database
        lessonHistoryRepository.saveAndFlush(lessonHistory);

        // Get all the lessonHistoryList
        restLessonHistoryMockMvc.perform(get("/api/lesson-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lessonHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].courseName").value(hasItem(DEFAULT_COURSE_NAME.toString())))
            .andExpect(jsonPath("$.[*].coef").value(hasItem(DEFAULT_COEF.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getLessonHistory() throws Exception {
        // Initialize the database
        lessonHistoryRepository.saveAndFlush(lessonHistory);

        // Get the lessonHistory
        restLessonHistoryMockMvc.perform(get("/api/lesson-histories/{id}", lessonHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lessonHistory.getId().intValue()))
            .andExpect(jsonPath("$.courseName").value(DEFAULT_COURSE_NAME.toString()))
            .andExpect(jsonPath("$.coef").value(DEFAULT_COEF.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLessonHistory() throws Exception {
        // Get the lessonHistory
        restLessonHistoryMockMvc.perform(get("/api/lesson-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLessonHistory() throws Exception {
        // Initialize the database
        lessonHistoryRepository.saveAndFlush(lessonHistory);

        int databaseSizeBeforeUpdate = lessonHistoryRepository.findAll().size();

        // Update the lessonHistory
        LessonHistory updatedLessonHistory = lessonHistoryRepository.findById(lessonHistory.getId()).get();
        // Disconnect from session so that the updates on updatedLessonHistory are not directly saved in db
        em.detach(updatedLessonHistory);
        updatedLessonHistory
            .courseName(UPDATED_COURSE_NAME)
            .coef(UPDATED_COEF);

        restLessonHistoryMockMvc.perform(put("/api/lesson-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLessonHistory)))
            .andExpect(status().isOk());

        // Validate the LessonHistory in the database
        List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAll();
        assertThat(lessonHistoryList).hasSize(databaseSizeBeforeUpdate);
        LessonHistory testLessonHistory = lessonHistoryList.get(lessonHistoryList.size() - 1);
        assertThat(testLessonHistory.getCourseName()).isEqualTo(UPDATED_COURSE_NAME);
        assertThat(testLessonHistory.getCoef()).isEqualTo(UPDATED_COEF);
    }

    @Test
    @Transactional
    public void updateNonExistingLessonHistory() throws Exception {
        int databaseSizeBeforeUpdate = lessonHistoryRepository.findAll().size();

        // Create the LessonHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLessonHistoryMockMvc.perform(put("/api/lesson-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonHistory)))
            .andExpect(status().isBadRequest());

        // Validate the LessonHistory in the database
        List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAll();
        assertThat(lessonHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLessonHistory() throws Exception {
        // Initialize the database
        lessonHistoryRepository.saveAndFlush(lessonHistory);

        int databaseSizeBeforeDelete = lessonHistoryRepository.findAll().size();

        // Delete the lessonHistory
        restLessonHistoryMockMvc.perform(delete("/api/lesson-histories/{id}", lessonHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LessonHistory> lessonHistoryList = lessonHistoryRepository.findAll();
        assertThat(lessonHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LessonHistory.class);
        LessonHistory lessonHistory1 = new LessonHistory();
        lessonHistory1.setId(1L);
        LessonHistory lessonHistory2 = new LessonHistory();
        lessonHistory2.setId(lessonHistory1.getId());
        assertThat(lessonHistory1).isEqualTo(lessonHistory2);
        lessonHistory2.setId(2L);
        assertThat(lessonHistory1).isNotEqualTo(lessonHistory2);
        lessonHistory1.setId(null);
        assertThat(lessonHistory1).isNotEqualTo(lessonHistory2);
    }
}
