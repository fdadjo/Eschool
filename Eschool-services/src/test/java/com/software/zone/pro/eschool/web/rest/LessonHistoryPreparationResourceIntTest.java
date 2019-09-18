package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.LessonHistoryPreparation;
import com.software.zone.pro.eschool.repository.LessonHistoryPreparationRepository;
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
 * Test class for the LessonHistoryPreparationResource REST controller.
 *
 * @see LessonHistoryPreparationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class LessonHistoryPreparationResourceIntTest {

    @Autowired
    private LessonHistoryPreparationRepository lessonHistoryPreparationRepository;

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

    private MockMvc restLessonHistoryPreparationMockMvc;

    private LessonHistoryPreparation lessonHistoryPreparation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LessonHistoryPreparationResource lessonHistoryPreparationResource = new LessonHistoryPreparationResource(lessonHistoryPreparationRepository);
        this.restLessonHistoryPreparationMockMvc = MockMvcBuilders.standaloneSetup(lessonHistoryPreparationResource)
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
    public static LessonHistoryPreparation createEntity(EntityManager em) {
        LessonHistoryPreparation lessonHistoryPreparation = new LessonHistoryPreparation();
        return lessonHistoryPreparation;
    }

    @Before
    public void initTest() {
        lessonHistoryPreparation = createEntity(em);
    }

    @Test
    @Transactional
    public void createLessonHistoryPreparation() throws Exception {
        int databaseSizeBeforeCreate = lessonHistoryPreparationRepository.findAll().size();

        // Create the LessonHistoryPreparation
        restLessonHistoryPreparationMockMvc.perform(post("/api/lesson-history-preparations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonHistoryPreparation)))
            .andExpect(status().isCreated());

        // Validate the LessonHistoryPreparation in the database
        List<LessonHistoryPreparation> lessonHistoryPreparationList = lessonHistoryPreparationRepository.findAll();
        assertThat(lessonHistoryPreparationList).hasSize(databaseSizeBeforeCreate + 1);
        LessonHistoryPreparation testLessonHistoryPreparation = lessonHistoryPreparationList.get(lessonHistoryPreparationList.size() - 1);
    }

    @Test
    @Transactional
    public void createLessonHistoryPreparationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lessonHistoryPreparationRepository.findAll().size();

        // Create the LessonHistoryPreparation with an existing ID
        lessonHistoryPreparation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLessonHistoryPreparationMockMvc.perform(post("/api/lesson-history-preparations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonHistoryPreparation)))
            .andExpect(status().isBadRequest());

        // Validate the LessonHistoryPreparation in the database
        List<LessonHistoryPreparation> lessonHistoryPreparationList = lessonHistoryPreparationRepository.findAll();
        assertThat(lessonHistoryPreparationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLessonHistoryPreparations() throws Exception {
        // Initialize the database
        lessonHistoryPreparationRepository.saveAndFlush(lessonHistoryPreparation);

        // Get all the lessonHistoryPreparationList
        restLessonHistoryPreparationMockMvc.perform(get("/api/lesson-history-preparations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lessonHistoryPreparation.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getLessonHistoryPreparation() throws Exception {
        // Initialize the database
        lessonHistoryPreparationRepository.saveAndFlush(lessonHistoryPreparation);

        // Get the lessonHistoryPreparation
        restLessonHistoryPreparationMockMvc.perform(get("/api/lesson-history-preparations/{id}", lessonHistoryPreparation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lessonHistoryPreparation.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLessonHistoryPreparation() throws Exception {
        // Get the lessonHistoryPreparation
        restLessonHistoryPreparationMockMvc.perform(get("/api/lesson-history-preparations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLessonHistoryPreparation() throws Exception {
        // Initialize the database
        lessonHistoryPreparationRepository.saveAndFlush(lessonHistoryPreparation);

        int databaseSizeBeforeUpdate = lessonHistoryPreparationRepository.findAll().size();

        // Update the lessonHistoryPreparation
        LessonHistoryPreparation updatedLessonHistoryPreparation = lessonHistoryPreparationRepository.findById(lessonHistoryPreparation.getId()).get();
        // Disconnect from session so that the updates on updatedLessonHistoryPreparation are not directly saved in db
        em.detach(updatedLessonHistoryPreparation);

        restLessonHistoryPreparationMockMvc.perform(put("/api/lesson-history-preparations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLessonHistoryPreparation)))
            .andExpect(status().isOk());

        // Validate the LessonHistoryPreparation in the database
        List<LessonHistoryPreparation> lessonHistoryPreparationList = lessonHistoryPreparationRepository.findAll();
        assertThat(lessonHistoryPreparationList).hasSize(databaseSizeBeforeUpdate);
        LessonHistoryPreparation testLessonHistoryPreparation = lessonHistoryPreparationList.get(lessonHistoryPreparationList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingLessonHistoryPreparation() throws Exception {
        int databaseSizeBeforeUpdate = lessonHistoryPreparationRepository.findAll().size();

        // Create the LessonHistoryPreparation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLessonHistoryPreparationMockMvc.perform(put("/api/lesson-history-preparations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lessonHistoryPreparation)))
            .andExpect(status().isBadRequest());

        // Validate the LessonHistoryPreparation in the database
        List<LessonHistoryPreparation> lessonHistoryPreparationList = lessonHistoryPreparationRepository.findAll();
        assertThat(lessonHistoryPreparationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLessonHistoryPreparation() throws Exception {
        // Initialize the database
        lessonHistoryPreparationRepository.saveAndFlush(lessonHistoryPreparation);

        int databaseSizeBeforeDelete = lessonHistoryPreparationRepository.findAll().size();

        // Delete the lessonHistoryPreparation
        restLessonHistoryPreparationMockMvc.perform(delete("/api/lesson-history-preparations/{id}", lessonHistoryPreparation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LessonHistoryPreparation> lessonHistoryPreparationList = lessonHistoryPreparationRepository.findAll();
        assertThat(lessonHistoryPreparationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LessonHistoryPreparation.class);
        LessonHistoryPreparation lessonHistoryPreparation1 = new LessonHistoryPreparation();
        lessonHistoryPreparation1.setId(1L);
        LessonHistoryPreparation lessonHistoryPreparation2 = new LessonHistoryPreparation();
        lessonHistoryPreparation2.setId(lessonHistoryPreparation1.getId());
        assertThat(lessonHistoryPreparation1).isEqualTo(lessonHistoryPreparation2);
        lessonHistoryPreparation2.setId(2L);
        assertThat(lessonHistoryPreparation1).isNotEqualTo(lessonHistoryPreparation2);
        lessonHistoryPreparation1.setId(null);
        assertThat(lessonHistoryPreparation1).isNotEqualTo(lessonHistoryPreparation2);
    }
}
