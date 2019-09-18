package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.Classroom;
import com.software.zone.pro.eschool.repository.ClassroomRepository;
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
 * Test class for the ClassroomResource REST controller.
 *
 * @see ClassroomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class ClassroomResourceIntTest {

    private static final String DEFAULT_CLASSROOM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CLASSROOM_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Double DEFAULT_TUTION_FEES = 1D;
    private static final Double UPDATED_TUTION_FEES = 2D;

    private static final Double DEFAULT_CC_COEF = 1D;
    private static final Double UPDATED_CC_COEF = 2D;

    private static final Double DEFAULT_C_COEF = 1D;
    private static final Double UPDATED_C_COEF = 2D;

    @Autowired
    private ClassroomRepository classroomRepository;

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

    private MockMvc restClassroomMockMvc;

    private Classroom classroom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassroomResource classroomResource = new ClassroomResource(classroomRepository);
        this.restClassroomMockMvc = MockMvcBuilders.standaloneSetup(classroomResource)
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
    public static Classroom createEntity(EntityManager em) {
        Classroom classroom = new Classroom()
            .classroomName(DEFAULT_CLASSROOM_NAME)
            .year(DEFAULT_YEAR)
            .tutionFees(DEFAULT_TUTION_FEES)
            .ccCoef(DEFAULT_CC_COEF)
            .cCoef(DEFAULT_C_COEF);
        return classroom;
    }

    @Before
    public void initTest() {
        classroom = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassroom() throws Exception {
        int databaseSizeBeforeCreate = classroomRepository.findAll().size();

        // Create the Classroom
        restClassroomMockMvc.perform(post("/api/classrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classroom)))
            .andExpect(status().isCreated());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeCreate + 1);
        Classroom testClassroom = classroomList.get(classroomList.size() - 1);
        assertThat(testClassroom.getClassroomName()).isEqualTo(DEFAULT_CLASSROOM_NAME);
        assertThat(testClassroom.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testClassroom.getTutionFees()).isEqualTo(DEFAULT_TUTION_FEES);
        assertThat(testClassroom.getCcCoef()).isEqualTo(DEFAULT_CC_COEF);
        assertThat(testClassroom.getcCoef()).isEqualTo(DEFAULT_C_COEF);
    }

    @Test
    @Transactional
    public void createClassroomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classroomRepository.findAll().size();

        // Create the Classroom with an existing ID
        classroom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassroomMockMvc.perform(post("/api/classrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classroom)))
            .andExpect(status().isBadRequest());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassrooms() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        // Get all the classroomList
        restClassroomMockMvc.perform(get("/api/classrooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classroom.getId().intValue())))
            .andExpect(jsonPath("$.[*].classroomName").value(hasItem(DEFAULT_CLASSROOM_NAME.toString())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].tutionFees").value(hasItem(DEFAULT_TUTION_FEES.doubleValue())))
            .andExpect(jsonPath("$.[*].ccCoef").value(hasItem(DEFAULT_CC_COEF.doubleValue())))
            .andExpect(jsonPath("$.[*].cCoef").value(hasItem(DEFAULT_C_COEF.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getClassroom() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        // Get the classroom
        restClassroomMockMvc.perform(get("/api/classrooms/{id}", classroom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classroom.getId().intValue()))
            .andExpect(jsonPath("$.classroomName").value(DEFAULT_CLASSROOM_NAME.toString()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.tutionFees").value(DEFAULT_TUTION_FEES.doubleValue()))
            .andExpect(jsonPath("$.ccCoef").value(DEFAULT_CC_COEF.doubleValue()))
            .andExpect(jsonPath("$.cCoef").value(DEFAULT_C_COEF.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingClassroom() throws Exception {
        // Get the classroom
        restClassroomMockMvc.perform(get("/api/classrooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassroom() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();

        // Update the classroom
        Classroom updatedClassroom = classroomRepository.findById(classroom.getId()).get();
        // Disconnect from session so that the updates on updatedClassroom are not directly saved in db
        em.detach(updatedClassroom);
        updatedClassroom
            .classroomName(UPDATED_CLASSROOM_NAME)
            .year(UPDATED_YEAR)
            .tutionFees(UPDATED_TUTION_FEES)
            .ccCoef(UPDATED_CC_COEF)
            .cCoef(UPDATED_C_COEF);

        restClassroomMockMvc.perform(put("/api/classrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassroom)))
            .andExpect(status().isOk());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
        Classroom testClassroom = classroomList.get(classroomList.size() - 1);
        assertThat(testClassroom.getClassroomName()).isEqualTo(UPDATED_CLASSROOM_NAME);
        assertThat(testClassroom.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testClassroom.getTutionFees()).isEqualTo(UPDATED_TUTION_FEES);
        assertThat(testClassroom.getCcCoef()).isEqualTo(UPDATED_CC_COEF);
        assertThat(testClassroom.getcCoef()).isEqualTo(UPDATED_C_COEF);
    }

    @Test
    @Transactional
    public void updateNonExistingClassroom() throws Exception {
        int databaseSizeBeforeUpdate = classroomRepository.findAll().size();

        // Create the Classroom

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassroomMockMvc.perform(put("/api/classrooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classroom)))
            .andExpect(status().isBadRequest());

        // Validate the Classroom in the database
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClassroom() throws Exception {
        // Initialize the database
        classroomRepository.saveAndFlush(classroom);

        int databaseSizeBeforeDelete = classroomRepository.findAll().size();

        // Delete the classroom
        restClassroomMockMvc.perform(delete("/api/classrooms/{id}", classroom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Classroom> classroomList = classroomRepository.findAll();
        assertThat(classroomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classroom.class);
        Classroom classroom1 = new Classroom();
        classroom1.setId(1L);
        Classroom classroom2 = new Classroom();
        classroom2.setId(classroom1.getId());
        assertThat(classroom1).isEqualTo(classroom2);
        classroom2.setId(2L);
        assertThat(classroom1).isNotEqualTo(classroom2);
        classroom1.setId(null);
        assertThat(classroom1).isNotEqualTo(classroom2);
    }
}
