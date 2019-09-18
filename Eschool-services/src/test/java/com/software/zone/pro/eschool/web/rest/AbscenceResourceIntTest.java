package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.Abscence;
import com.software.zone.pro.eschool.repository.AbscenceRepository;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static com.software.zone.pro.eschool.web.rest.TestUtil.sameInstant;
import static com.software.zone.pro.eschool.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AbscenceResource REST controller.
 *
 * @see AbscenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class AbscenceResourceIntTest {

    private static final ZonedDateTime DEFAULT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ABSCENCE = false;
    private static final Boolean UPDATED_ABSCENCE = true;

    private static final Boolean DEFAULT_JUSTIFY = false;
    private static final Boolean UPDATED_JUSTIFY = true;

    @Autowired
    private AbscenceRepository abscenceRepository;

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

    private MockMvc restAbscenceMockMvc;

    private Abscence abscence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AbscenceResource abscenceResource = new AbscenceResource(abscenceRepository);
        this.restAbscenceMockMvc = MockMvcBuilders.standaloneSetup(abscenceResource)
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
    public static Abscence createEntity(EntityManager em) {
        Abscence abscence = new Abscence()
            .date(DEFAULT_DATE)
            .commentaire(DEFAULT_COMMENTAIRE)
            .abscence(DEFAULT_ABSCENCE)
            .justify(DEFAULT_JUSTIFY);
        return abscence;
    }

    @Before
    public void initTest() {
        abscence = createEntity(em);
    }

    @Test
    @Transactional
    public void createAbscence() throws Exception {
        int databaseSizeBeforeCreate = abscenceRepository.findAll().size();

        // Create the Abscence
        restAbscenceMockMvc.perform(post("/api/abscences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abscence)))
            .andExpect(status().isCreated());

        // Validate the Abscence in the database
        List<Abscence> abscenceList = abscenceRepository.findAll();
        assertThat(abscenceList).hasSize(databaseSizeBeforeCreate + 1);
        Abscence testAbscence = abscenceList.get(abscenceList.size() - 1);
        assertThat(testAbscence.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testAbscence.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testAbscence.isAbscence()).isEqualTo(DEFAULT_ABSCENCE);
        assertThat(testAbscence.isJustify()).isEqualTo(DEFAULT_JUSTIFY);
    }

    @Test
    @Transactional
    public void createAbscenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = abscenceRepository.findAll().size();

        // Create the Abscence with an existing ID
        abscence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAbscenceMockMvc.perform(post("/api/abscences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abscence)))
            .andExpect(status().isBadRequest());

        // Validate the Abscence in the database
        List<Abscence> abscenceList = abscenceRepository.findAll();
        assertThat(abscenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAbscences() throws Exception {
        // Initialize the database
        abscenceRepository.saveAndFlush(abscence);

        // Get all the abscenceList
        restAbscenceMockMvc.perform(get("/api/abscences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(abscence.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(sameInstant(DEFAULT_DATE))))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE.toString())))
            .andExpect(jsonPath("$.[*].abscence").value(hasItem(DEFAULT_ABSCENCE.booleanValue())))
            .andExpect(jsonPath("$.[*].justify").value(hasItem(DEFAULT_JUSTIFY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAbscence() throws Exception {
        // Initialize the database
        abscenceRepository.saveAndFlush(abscence);

        // Get the abscence
        restAbscenceMockMvc.perform(get("/api/abscences/{id}", abscence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(abscence.getId().intValue()))
            .andExpect(jsonPath("$.date").value(sameInstant(DEFAULT_DATE)))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE.toString()))
            .andExpect(jsonPath("$.abscence").value(DEFAULT_ABSCENCE.booleanValue()))
            .andExpect(jsonPath("$.justify").value(DEFAULT_JUSTIFY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAbscence() throws Exception {
        // Get the abscence
        restAbscenceMockMvc.perform(get("/api/abscences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAbscence() throws Exception {
        // Initialize the database
        abscenceRepository.saveAndFlush(abscence);

        int databaseSizeBeforeUpdate = abscenceRepository.findAll().size();

        // Update the abscence
        Abscence updatedAbscence = abscenceRepository.findById(abscence.getId()).get();
        // Disconnect from session so that the updates on updatedAbscence are not directly saved in db
        em.detach(updatedAbscence);
        updatedAbscence
            .date(UPDATED_DATE)
            .commentaire(UPDATED_COMMENTAIRE)
            .abscence(UPDATED_ABSCENCE)
            .justify(UPDATED_JUSTIFY);

        restAbscenceMockMvc.perform(put("/api/abscences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAbscence)))
            .andExpect(status().isOk());

        // Validate the Abscence in the database
        List<Abscence> abscenceList = abscenceRepository.findAll();
        assertThat(abscenceList).hasSize(databaseSizeBeforeUpdate);
        Abscence testAbscence = abscenceList.get(abscenceList.size() - 1);
        assertThat(testAbscence.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testAbscence.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testAbscence.isAbscence()).isEqualTo(UPDATED_ABSCENCE);
        assertThat(testAbscence.isJustify()).isEqualTo(UPDATED_JUSTIFY);
    }

    @Test
    @Transactional
    public void updateNonExistingAbscence() throws Exception {
        int databaseSizeBeforeUpdate = abscenceRepository.findAll().size();

        // Create the Abscence

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAbscenceMockMvc.perform(put("/api/abscences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(abscence)))
            .andExpect(status().isBadRequest());

        // Validate the Abscence in the database
        List<Abscence> abscenceList = abscenceRepository.findAll();
        assertThat(abscenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAbscence() throws Exception {
        // Initialize the database
        abscenceRepository.saveAndFlush(abscence);

        int databaseSizeBeforeDelete = abscenceRepository.findAll().size();

        // Delete the abscence
        restAbscenceMockMvc.perform(delete("/api/abscences/{id}", abscence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Abscence> abscenceList = abscenceRepository.findAll();
        assertThat(abscenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Abscence.class);
        Abscence abscence1 = new Abscence();
        abscence1.setId(1L);
        Abscence abscence2 = new Abscence();
        abscence2.setId(abscence1.getId());
        assertThat(abscence1).isEqualTo(abscence2);
        abscence2.setId(2L);
        assertThat(abscence1).isNotEqualTo(abscence2);
        abscence1.setId(null);
        assertThat(abscence1).isNotEqualTo(abscence2);
    }
}
