package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.ClassroomHistory;
import com.software.zone.pro.eschool.repository.ClassroomHistoryRepository;
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
 * Test class for the ClassroomHistoryResource REST controller.
 *
 * @see ClassroomHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class ClassroomHistoryResourceIntTest {

    private static final Double DEFAULT_FEES = 1D;
    private static final Double UPDATED_FEES = 2D;

    @Autowired
    private ClassroomHistoryRepository classroomHistoryRepository;

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

    private MockMvc restClassroomHistoryMockMvc;

    private ClassroomHistory classroomHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassroomHistoryResource classroomHistoryResource = new ClassroomHistoryResource(classroomHistoryRepository);
        this.restClassroomHistoryMockMvc = MockMvcBuilders.standaloneSetup(classroomHistoryResource)
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
    public static ClassroomHistory createEntity(EntityManager em) {
        ClassroomHistory classroomHistory = new ClassroomHistory()
            .fees(DEFAULT_FEES);
        return classroomHistory;
    }

    @Before
    public void initTest() {
        classroomHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassroomHistory() throws Exception {
        int databaseSizeBeforeCreate = classroomHistoryRepository.findAll().size();

        // Create the ClassroomHistory
        restClassroomHistoryMockMvc.perform(post("/api/classroom-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classroomHistory)))
            .andExpect(status().isCreated());

        // Validate the ClassroomHistory in the database
        List<ClassroomHistory> classroomHistoryList = classroomHistoryRepository.findAll();
        assertThat(classroomHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ClassroomHistory testClassroomHistory = classroomHistoryList.get(classroomHistoryList.size() - 1);
        assertThat(testClassroomHistory.getFees()).isEqualTo(DEFAULT_FEES);
    }

    @Test
    @Transactional
    public void createClassroomHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classroomHistoryRepository.findAll().size();

        // Create the ClassroomHistory with an existing ID
        classroomHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassroomHistoryMockMvc.perform(post("/api/classroom-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classroomHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ClassroomHistory in the database
        List<ClassroomHistory> classroomHistoryList = classroomHistoryRepository.findAll();
        assertThat(classroomHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassroomHistories() throws Exception {
        // Initialize the database
        classroomHistoryRepository.saveAndFlush(classroomHistory);

        // Get all the classroomHistoryList
        restClassroomHistoryMockMvc.perform(get("/api/classroom-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classroomHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].fees").value(hasItem(DEFAULT_FEES.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getClassroomHistory() throws Exception {
        // Initialize the database
        classroomHistoryRepository.saveAndFlush(classroomHistory);

        // Get the classroomHistory
        restClassroomHistoryMockMvc.perform(get("/api/classroom-histories/{id}", classroomHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classroomHistory.getId().intValue()))
            .andExpect(jsonPath("$.fees").value(DEFAULT_FEES.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClassroomHistory() throws Exception {
        // Get the classroomHistory
        restClassroomHistoryMockMvc.perform(get("/api/classroom-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassroomHistory() throws Exception {
        // Initialize the database
        classroomHistoryRepository.saveAndFlush(classroomHistory);

        int databaseSizeBeforeUpdate = classroomHistoryRepository.findAll().size();

        // Update the classroomHistory
        ClassroomHistory updatedClassroomHistory = classroomHistoryRepository.findById(classroomHistory.getId()).get();
        // Disconnect from session so that the updates on updatedClassroomHistory are not directly saved in db
        em.detach(updatedClassroomHistory);
        updatedClassroomHistory
            .fees(UPDATED_FEES);

        restClassroomHistoryMockMvc.perform(put("/api/classroom-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassroomHistory)))
            .andExpect(status().isOk());

        // Validate the ClassroomHistory in the database
        List<ClassroomHistory> classroomHistoryList = classroomHistoryRepository.findAll();
        assertThat(classroomHistoryList).hasSize(databaseSizeBeforeUpdate);
        ClassroomHistory testClassroomHistory = classroomHistoryList.get(classroomHistoryList.size() - 1);
        assertThat(testClassroomHistory.getFees()).isEqualTo(UPDATED_FEES);
    }

    @Test
    @Transactional
    public void updateNonExistingClassroomHistory() throws Exception {
        int databaseSizeBeforeUpdate = classroomHistoryRepository.findAll().size();

        // Create the ClassroomHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassroomHistoryMockMvc.perform(put("/api/classroom-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classroomHistory)))
            .andExpect(status().isBadRequest());

        // Validate the ClassroomHistory in the database
        List<ClassroomHistory> classroomHistoryList = classroomHistoryRepository.findAll();
        assertThat(classroomHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassroomHistory() throws Exception {
        // Initialize the database
        classroomHistoryRepository.saveAndFlush(classroomHistory);

        int databaseSizeBeforeDelete = classroomHistoryRepository.findAll().size();

        // Delete the classroomHistory
        restClassroomHistoryMockMvc.perform(delete("/api/classroom-histories/{id}", classroomHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassroomHistory> classroomHistoryList = classroomHistoryRepository.findAll();
        assertThat(classroomHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassroomHistory.class);
        ClassroomHistory classroomHistory1 = new ClassroomHistory();
        classroomHistory1.setId(1L);
        ClassroomHistory classroomHistory2 = new ClassroomHistory();
        classroomHistory2.setId(classroomHistory1.getId());
        assertThat(classroomHistory1).isEqualTo(classroomHistory2);
        classroomHistory2.setId(2L);
        assertThat(classroomHistory1).isNotEqualTo(classroomHistory2);
        classroomHistory1.setId(null);
        assertThat(classroomHistory1).isNotEqualTo(classroomHistory2);
    }
}
