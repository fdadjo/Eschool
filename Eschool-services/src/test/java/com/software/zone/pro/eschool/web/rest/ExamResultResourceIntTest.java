package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.ExamResult;
import com.software.zone.pro.eschool.repository.ExamResultRepository;
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
 * Test class for the ExamResultResource REST controller.
 *
 * @see ExamResultResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class ExamResultResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ExamResultRepository examResultRepository;

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

    private MockMvc restExamResultMockMvc;

    private ExamResult examResult;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExamResultResource examResultResource = new ExamResultResource(examResultRepository);
        this.restExamResultMockMvc = MockMvcBuilders.standaloneSetup(examResultResource)
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
    public static ExamResult createEntity(EntityManager em) {
        ExamResult examResult = new ExamResult()
            .name(DEFAULT_NAME);
        return examResult;
    }

    @Before
    public void initTest() {
        examResult = createEntity(em);
    }

    @Test
    @Transactional
    public void createExamResult() throws Exception {
        int databaseSizeBeforeCreate = examResultRepository.findAll().size();

        // Create the ExamResult
        restExamResultMockMvc.perform(post("/api/exam-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examResult)))
            .andExpect(status().isCreated());

        // Validate the ExamResult in the database
        List<ExamResult> examResultList = examResultRepository.findAll();
        assertThat(examResultList).hasSize(databaseSizeBeforeCreate + 1);
        ExamResult testExamResult = examResultList.get(examResultList.size() - 1);
        assertThat(testExamResult.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createExamResultWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = examResultRepository.findAll().size();

        // Create the ExamResult with an existing ID
        examResult.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExamResultMockMvc.perform(post("/api/exam-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examResult)))
            .andExpect(status().isBadRequest());

        // Validate the ExamResult in the database
        List<ExamResult> examResultList = examResultRepository.findAll();
        assertThat(examResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllExamResults() throws Exception {
        // Initialize the database
        examResultRepository.saveAndFlush(examResult);

        // Get all the examResultList
        restExamResultMockMvc.perform(get("/api/exam-results?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(examResult.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getExamResult() throws Exception {
        // Initialize the database
        examResultRepository.saveAndFlush(examResult);

        // Get the examResult
        restExamResultMockMvc.perform(get("/api/exam-results/{id}", examResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(examResult.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingExamResult() throws Exception {
        // Get the examResult
        restExamResultMockMvc.perform(get("/api/exam-results/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExamResult() throws Exception {
        // Initialize the database
        examResultRepository.saveAndFlush(examResult);

        int databaseSizeBeforeUpdate = examResultRepository.findAll().size();

        // Update the examResult
        ExamResult updatedExamResult = examResultRepository.findById(examResult.getId()).get();
        // Disconnect from session so that the updates on updatedExamResult are not directly saved in db
        em.detach(updatedExamResult);
        updatedExamResult
            .name(UPDATED_NAME);

        restExamResultMockMvc.perform(put("/api/exam-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExamResult)))
            .andExpect(status().isOk());

        // Validate the ExamResult in the database
        List<ExamResult> examResultList = examResultRepository.findAll();
        assertThat(examResultList).hasSize(databaseSizeBeforeUpdate);
        ExamResult testExamResult = examResultList.get(examResultList.size() - 1);
        assertThat(testExamResult.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingExamResult() throws Exception {
        int databaseSizeBeforeUpdate = examResultRepository.findAll().size();

        // Create the ExamResult

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExamResultMockMvc.perform(put("/api/exam-results")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(examResult)))
            .andExpect(status().isBadRequest());

        // Validate the ExamResult in the database
        List<ExamResult> examResultList = examResultRepository.findAll();
        assertThat(examResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExamResult() throws Exception {
        // Initialize the database
        examResultRepository.saveAndFlush(examResult);

        int databaseSizeBeforeDelete = examResultRepository.findAll().size();

        // Delete the examResult
        restExamResultMockMvc.perform(delete("/api/exam-results/{id}", examResult.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ExamResult> examResultList = examResultRepository.findAll();
        assertThat(examResultList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExamResult.class);
        ExamResult examResult1 = new ExamResult();
        examResult1.setId(1L);
        ExamResult examResult2 = new ExamResult();
        examResult2.setId(examResult1.getId());
        assertThat(examResult1).isEqualTo(examResult2);
        examResult2.setId(2L);
        assertThat(examResult1).isNotEqualTo(examResult2);
        examResult1.setId(null);
        assertThat(examResult1).isNotEqualTo(examResult2);
    }
}
