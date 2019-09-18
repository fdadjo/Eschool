package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.StudentHomework;
import com.software.zone.pro.eschool.repository.StudentHomeworkRepository;
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
 * Test class for the StudentHomeworkResource REST controller.
 *
 * @see StudentHomeworkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class StudentHomeworkResourceIntTest {

    private static final Boolean DEFAULT_DONE = false;
    private static final Boolean UPDATED_DONE = true;

    @Autowired
    private StudentHomeworkRepository studentHomeworkRepository;

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

    private MockMvc restStudentHomeworkMockMvc;

    private StudentHomework studentHomework;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentHomeworkResource studentHomeworkResource = new StudentHomeworkResource(studentHomeworkRepository);
        this.restStudentHomeworkMockMvc = MockMvcBuilders.standaloneSetup(studentHomeworkResource)
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
    public static StudentHomework createEntity(EntityManager em) {
        StudentHomework studentHomework = new StudentHomework()
            .done(DEFAULT_DONE);
        return studentHomework;
    }

    @Before
    public void initTest() {
        studentHomework = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudentHomework() throws Exception {
        int databaseSizeBeforeCreate = studentHomeworkRepository.findAll().size();

        // Create the StudentHomework
        restStudentHomeworkMockMvc.perform(post("/api/student-homeworks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentHomework)))
            .andExpect(status().isCreated());

        // Validate the StudentHomework in the database
        List<StudentHomework> studentHomeworkList = studentHomeworkRepository.findAll();
        assertThat(studentHomeworkList).hasSize(databaseSizeBeforeCreate + 1);
        StudentHomework testStudentHomework = studentHomeworkList.get(studentHomeworkList.size() - 1);
        assertThat(testStudentHomework.isDone()).isEqualTo(DEFAULT_DONE);
    }

    @Test
    @Transactional
    public void createStudentHomeworkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentHomeworkRepository.findAll().size();

        // Create the StudentHomework with an existing ID
        studentHomework.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentHomeworkMockMvc.perform(post("/api/student-homeworks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentHomework)))
            .andExpect(status().isBadRequest());

        // Validate the StudentHomework in the database
        List<StudentHomework> studentHomeworkList = studentHomeworkRepository.findAll();
        assertThat(studentHomeworkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudentHomeworks() throws Exception {
        // Initialize the database
        studentHomeworkRepository.saveAndFlush(studentHomework);

        // Get all the studentHomeworkList
        restStudentHomeworkMockMvc.perform(get("/api/student-homeworks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(studentHomework.getId().intValue())))
            .andExpect(jsonPath("$.[*].done").value(hasItem(DEFAULT_DONE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStudentHomework() throws Exception {
        // Initialize the database
        studentHomeworkRepository.saveAndFlush(studentHomework);

        // Get the studentHomework
        restStudentHomeworkMockMvc.perform(get("/api/student-homeworks/{id}", studentHomework.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(studentHomework.getId().intValue()))
            .andExpect(jsonPath("$.done").value(DEFAULT_DONE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentHomework() throws Exception {
        // Get the studentHomework
        restStudentHomeworkMockMvc.perform(get("/api/student-homeworks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentHomework() throws Exception {
        // Initialize the database
        studentHomeworkRepository.saveAndFlush(studentHomework);

        int databaseSizeBeforeUpdate = studentHomeworkRepository.findAll().size();

        // Update the studentHomework
        StudentHomework updatedStudentHomework = studentHomeworkRepository.findById(studentHomework.getId()).get();
        // Disconnect from session so that the updates on updatedStudentHomework are not directly saved in db
        em.detach(updatedStudentHomework);
        updatedStudentHomework
            .done(UPDATED_DONE);

        restStudentHomeworkMockMvc.perform(put("/api/student-homeworks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudentHomework)))
            .andExpect(status().isOk());

        // Validate the StudentHomework in the database
        List<StudentHomework> studentHomeworkList = studentHomeworkRepository.findAll();
        assertThat(studentHomeworkList).hasSize(databaseSizeBeforeUpdate);
        StudentHomework testStudentHomework = studentHomeworkList.get(studentHomeworkList.size() - 1);
        assertThat(testStudentHomework.isDone()).isEqualTo(UPDATED_DONE);
    }

    @Test
    @Transactional
    public void updateNonExistingStudentHomework() throws Exception {
        int databaseSizeBeforeUpdate = studentHomeworkRepository.findAll().size();

        // Create the StudentHomework

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStudentHomeworkMockMvc.perform(put("/api/student-homeworks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(studentHomework)))
            .andExpect(status().isBadRequest());

        // Validate the StudentHomework in the database
        List<StudentHomework> studentHomeworkList = studentHomeworkRepository.findAll();
        assertThat(studentHomeworkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStudentHomework() throws Exception {
        // Initialize the database
        studentHomeworkRepository.saveAndFlush(studentHomework);

        int databaseSizeBeforeDelete = studentHomeworkRepository.findAll().size();

        // Delete the studentHomework
        restStudentHomeworkMockMvc.perform(delete("/api/student-homeworks/{id}", studentHomework.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentHomework> studentHomeworkList = studentHomeworkRepository.findAll();
        assertThat(studentHomeworkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StudentHomework.class);
        StudentHomework studentHomework1 = new StudentHomework();
        studentHomework1.setId(1L);
        StudentHomework studentHomework2 = new StudentHomework();
        studentHomework2.setId(studentHomework1.getId());
        assertThat(studentHomework1).isEqualTo(studentHomework2);
        studentHomework2.setId(2L);
        assertThat(studentHomework1).isNotEqualTo(studentHomework2);
        studentHomework1.setId(null);
        assertThat(studentHomework1).isNotEqualTo(studentHomework2);
    }
}
