package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.SchoolAuthority;
import com.software.zone.pro.eschool.repository.SchoolAuthorityRepository;
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
 * Test class for the SchoolAuthorityResource REST controller.
 *
 * @see SchoolAuthorityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class SchoolAuthorityResourceIntTest {

    @Autowired
    private SchoolAuthorityRepository schoolAuthorityRepository;

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

    private MockMvc restSchoolAuthorityMockMvc;

    private SchoolAuthority schoolAuthority;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchoolAuthorityResource schoolAuthorityResource = new SchoolAuthorityResource(schoolAuthorityRepository);
        this.restSchoolAuthorityMockMvc = MockMvcBuilders.standaloneSetup(schoolAuthorityResource)
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
    public static SchoolAuthority createEntity(EntityManager em) {
        SchoolAuthority schoolAuthority = new SchoolAuthority();
        return schoolAuthority;
    }

    @Before
    public void initTest() {
        schoolAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchoolAuthority() throws Exception {
        int databaseSizeBeforeCreate = schoolAuthorityRepository.findAll().size();

        // Create the SchoolAuthority
        restSchoolAuthorityMockMvc.perform(post("/api/school-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolAuthority)))
            .andExpect(status().isCreated());

        // Validate the SchoolAuthority in the database
        List<SchoolAuthority> schoolAuthorityList = schoolAuthorityRepository.findAll();
        assertThat(schoolAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        SchoolAuthority testSchoolAuthority = schoolAuthorityList.get(schoolAuthorityList.size() - 1);
    }

    @Test
    @Transactional
    public void createSchoolAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolAuthorityRepository.findAll().size();

        // Create the SchoolAuthority with an existing ID
        schoolAuthority.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolAuthorityMockMvc.perform(post("/api/school-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolAuthority)))
            .andExpect(status().isBadRequest());

        // Validate the SchoolAuthority in the database
        List<SchoolAuthority> schoolAuthorityList = schoolAuthorityRepository.findAll();
        assertThat(schoolAuthorityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchoolAuthorities() throws Exception {
        // Initialize the database
        schoolAuthorityRepository.saveAndFlush(schoolAuthority);

        // Get all the schoolAuthorityList
        restSchoolAuthorityMockMvc.perform(get("/api/school-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(schoolAuthority.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getSchoolAuthority() throws Exception {
        // Initialize the database
        schoolAuthorityRepository.saveAndFlush(schoolAuthority);

        // Get the schoolAuthority
        restSchoolAuthorityMockMvc.perform(get("/api/school-authorities/{id}", schoolAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(schoolAuthority.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSchoolAuthority() throws Exception {
        // Get the schoolAuthority
        restSchoolAuthorityMockMvc.perform(get("/api/school-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchoolAuthority() throws Exception {
        // Initialize the database
        schoolAuthorityRepository.saveAndFlush(schoolAuthority);

        int databaseSizeBeforeUpdate = schoolAuthorityRepository.findAll().size();

        // Update the schoolAuthority
        SchoolAuthority updatedSchoolAuthority = schoolAuthorityRepository.findById(schoolAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedSchoolAuthority are not directly saved in db
        em.detach(updatedSchoolAuthority);

        restSchoolAuthorityMockMvc.perform(put("/api/school-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchoolAuthority)))
            .andExpect(status().isOk());

        // Validate the SchoolAuthority in the database
        List<SchoolAuthority> schoolAuthorityList = schoolAuthorityRepository.findAll();
        assertThat(schoolAuthorityList).hasSize(databaseSizeBeforeUpdate);
        SchoolAuthority testSchoolAuthority = schoolAuthorityList.get(schoolAuthorityList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingSchoolAuthority() throws Exception {
        int databaseSizeBeforeUpdate = schoolAuthorityRepository.findAll().size();

        // Create the SchoolAuthority

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolAuthorityMockMvc.perform(put("/api/school-authorities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(schoolAuthority)))
            .andExpect(status().isBadRequest());

        // Validate the SchoolAuthority in the database
        List<SchoolAuthority> schoolAuthorityList = schoolAuthorityRepository.findAll();
        assertThat(schoolAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchoolAuthority() throws Exception {
        // Initialize the database
        schoolAuthorityRepository.saveAndFlush(schoolAuthority);

        int databaseSizeBeforeDelete = schoolAuthorityRepository.findAll().size();

        // Delete the schoolAuthority
        restSchoolAuthorityMockMvc.perform(delete("/api/school-authorities/{id}", schoolAuthority.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SchoolAuthority> schoolAuthorityList = schoolAuthorityRepository.findAll();
        assertThat(schoolAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SchoolAuthority.class);
        SchoolAuthority schoolAuthority1 = new SchoolAuthority();
        schoolAuthority1.setId(1L);
        SchoolAuthority schoolAuthority2 = new SchoolAuthority();
        schoolAuthority2.setId(schoolAuthority1.getId());
        assertThat(schoolAuthority1).isEqualTo(schoolAuthority2);
        schoolAuthority2.setId(2L);
        assertThat(schoolAuthority1).isNotEqualTo(schoolAuthority2);
        schoolAuthority1.setId(null);
        assertThat(schoolAuthority1).isNotEqualTo(schoolAuthority2);
    }
}
