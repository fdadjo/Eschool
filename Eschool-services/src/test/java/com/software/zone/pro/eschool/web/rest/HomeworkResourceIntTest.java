package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.Homework;
import com.software.zone.pro.eschool.repository.HomeworkRepository;
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
 * Test class for the HomeworkResource REST controller.
 *
 * @see HomeworkResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class HomeworkResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_FILE_URL = "BBBBBBBBBB";

    @Autowired
    private HomeworkRepository homeworkRepository;

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

    private MockMvc restHomeworkMockMvc;

    private Homework homework;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HomeworkResource homeworkResource = new HomeworkResource(homeworkRepository);
        this.restHomeworkMockMvc = MockMvcBuilders.standaloneSetup(homeworkResource)
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
    public static Homework createEntity(EntityManager em) {
        Homework homework = new Homework()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .fileUrl(DEFAULT_FILE_URL);
        return homework;
    }

    @Before
    public void initTest() {
        homework = createEntity(em);
    }

    @Test
    @Transactional
    public void createHomework() throws Exception {
        int databaseSizeBeforeCreate = homeworkRepository.findAll().size();

        // Create the Homework
        restHomeworkMockMvc.perform(post("/api/homework")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homework)))
            .andExpect(status().isCreated());

        // Validate the Homework in the database
        List<Homework> homeworkList = homeworkRepository.findAll();
        assertThat(homeworkList).hasSize(databaseSizeBeforeCreate + 1);
        Homework testHomework = homeworkList.get(homeworkList.size() - 1);
        assertThat(testHomework.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testHomework.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testHomework.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
    }

    @Test
    @Transactional
    public void createHomeworkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = homeworkRepository.findAll().size();

        // Create the Homework with an existing ID
        homework.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHomeworkMockMvc.perform(post("/api/homework")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homework)))
            .andExpect(status().isBadRequest());

        // Validate the Homework in the database
        List<Homework> homeworkList = homeworkRepository.findAll();
        assertThat(homeworkList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllHomework() throws Exception {
        // Initialize the database
        homeworkRepository.saveAndFlush(homework);

        // Get all the homeworkList
        restHomeworkMockMvc.perform(get("/api/homework?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(homework.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL.toString())));
    }
    
    @Test
    @Transactional
    public void getHomework() throws Exception {
        // Initialize the database
        homeworkRepository.saveAndFlush(homework);

        // Get the homework
        restHomeworkMockMvc.perform(get("/api/homework/{id}", homework.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(homework.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.fileUrl").value(DEFAULT_FILE_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingHomework() throws Exception {
        // Get the homework
        restHomeworkMockMvc.perform(get("/api/homework/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHomework() throws Exception {
        // Initialize the database
        homeworkRepository.saveAndFlush(homework);

        int databaseSizeBeforeUpdate = homeworkRepository.findAll().size();

        // Update the homework
        Homework updatedHomework = homeworkRepository.findById(homework.getId()).get();
        // Disconnect from session so that the updates on updatedHomework are not directly saved in db
        em.detach(updatedHomework);
        updatedHomework
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .fileUrl(UPDATED_FILE_URL);

        restHomeworkMockMvc.perform(put("/api/homework")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHomework)))
            .andExpect(status().isOk());

        // Validate the Homework in the database
        List<Homework> homeworkList = homeworkRepository.findAll();
        assertThat(homeworkList).hasSize(databaseSizeBeforeUpdate);
        Homework testHomework = homeworkList.get(homeworkList.size() - 1);
        assertThat(testHomework.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testHomework.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testHomework.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingHomework() throws Exception {
        int databaseSizeBeforeUpdate = homeworkRepository.findAll().size();

        // Create the Homework

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHomeworkMockMvc.perform(put("/api/homework")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(homework)))
            .andExpect(status().isBadRequest());

        // Validate the Homework in the database
        List<Homework> homeworkList = homeworkRepository.findAll();
        assertThat(homeworkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHomework() throws Exception {
        // Initialize the database
        homeworkRepository.saveAndFlush(homework);

        int databaseSizeBeforeDelete = homeworkRepository.findAll().size();

        // Delete the homework
        restHomeworkMockMvc.perform(delete("/api/homework/{id}", homework.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Homework> homeworkList = homeworkRepository.findAll();
        assertThat(homeworkList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Homework.class);
        Homework homework1 = new Homework();
        homework1.setId(1L);
        Homework homework2 = new Homework();
        homework2.setId(homework1.getId());
        assertThat(homework1).isEqualTo(homework2);
        homework2.setId(2L);
        assertThat(homework1).isNotEqualTo(homework2);
        homework1.setId(null);
        assertThat(homework1).isNotEqualTo(homework2);
    }
}
