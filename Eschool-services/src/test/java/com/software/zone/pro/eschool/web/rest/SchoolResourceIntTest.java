package com.software.zone.pro.eschool.web.rest;

import com.software.zone.pro.eschool.EschoolApp;

import com.software.zone.pro.eschool.domain.School;
import com.software.zone.pro.eschool.repository.SchoolRepository;
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
 * Test class for the SchoolResource REST controller.
 *
 * @see SchoolResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EschoolApp.class)
public class SchoolResourceIntTest {

    private static final String DEFAULT_SCHOOL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DEVISE = "AAAAAAAAAA";
    private static final String UPDATED_DEVISE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_CC_COEF = 1D;
    private static final Double UPDATED_CC_COEF = 2D;

    private static final Double DEFAULT_C_COEF = 1D;
    private static final Double UPDATED_C_COEF = 2D;

    private static final String DEFAULT_LOGO_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRYNAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRYNAME = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_MOTTO = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_MOTTO = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_SECRETARY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_SECRETARY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_SUB_SECRETARY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_SUB_SECRETARY = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL_MOTTO = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_MOTTO = "BBBBBBBBBB";

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    /*@Autowired
    private Validator validator;*/

    private MockMvc restSchoolMockMvc;

    private School school;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SchoolResource schoolResource = new SchoolResource(schoolRepository);
        this.restSchoolMockMvc = MockMvcBuilders.standaloneSetup(schoolResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
            //.setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static School createEntity(EntityManager em) {
        School school = new School()
            .schoolName(DEFAULT_SCHOOL_NAME);
            /*.streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE)
            .telephone(DEFAULT_TELEPHONE)
            .devise(DEFAULT_DEVISE)
            .description(DEFAULT_DESCRIPTION)
            .ccCoef(DEFAULT_CC_COEF)
            .cCoef(DEFAULT_C_COEF)
            .logoLink(DEFAULT_LOGO_LINK)
            .countryname(DEFAULT_COUNTRYNAME)
            .countryMotto(DEFAULT_COUNTRY_MOTTO)
            .countrySecretary(DEFAULT_COUNTRY_SECRETARY)
            .countrySubSecretary(DEFAULT_COUNTRY_SUB_SECRETARY)
            .schoolMotto(DEFAULT_SCHOOL_MOTTO);*/
        return school;
    }

    @Before
    public void initTest() {
        school = createEntity(em);
    }

    @Test
    @Transactional
    public void createSchool() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isCreated());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testSchool.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testSchool.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testSchool.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSchool.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
        assertThat(testSchool.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testSchool.getDevise()).isEqualTo(DEFAULT_DEVISE);
        assertThat(testSchool.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSchool.getCcCoef()).isEqualTo(DEFAULT_CC_COEF);
        assertThat(testSchool.getcCoef()).isEqualTo(DEFAULT_C_COEF);
        assertThat(testSchool.getLogoLink()).isEqualTo(DEFAULT_LOGO_LINK);
        assertThat(testSchool.getCountryname()).isEqualTo(DEFAULT_COUNTRYNAME);
        assertThat(testSchool.getCountryMotto()).isEqualTo(DEFAULT_COUNTRY_MOTTO);
        assertThat(testSchool.getCountrySecretary()).isEqualTo(DEFAULT_COUNTRY_SECRETARY);
        assertThat(testSchool.getCountrySubSecretary()).isEqualTo(DEFAULT_COUNTRY_SUB_SECRETARY);
        assertThat(testSchool.getSchoolMotto()).isEqualTo(DEFAULT_SCHOOL_MOTTO);
    }

    @Test
    @Transactional
    public void createSchoolWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();

        // Create the School with an existing ID
        school.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSchoolMockMvc.perform(post("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSchools() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get all the schoolList
        restSchoolMockMvc.perform(get("/api/schools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(school.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].ccCoef").value(hasItem(DEFAULT_CC_COEF.doubleValue())))
            .andExpect(jsonPath("$.[*].cCoef").value(hasItem(DEFAULT_C_COEF.doubleValue())))
            .andExpect(jsonPath("$.[*].logoLink").value(hasItem(DEFAULT_LOGO_LINK.toString())))
            .andExpect(jsonPath("$.[*].countryname").value(hasItem(DEFAULT_COUNTRYNAME.toString())))
            .andExpect(jsonPath("$.[*].countryMotto").value(hasItem(DEFAULT_COUNTRY_MOTTO.toString())))
            .andExpect(jsonPath("$.[*].countrySecretary").value(hasItem(DEFAULT_COUNTRY_SECRETARY.toString())))
            .andExpect(jsonPath("$.[*].countrySubSecretary").value(hasItem(DEFAULT_COUNTRY_SUB_SECRETARY.toString())))
            .andExpect(jsonPath("$.[*].schoolMotto").value(hasItem(DEFAULT_SCHOOL_MOTTO.toString())));
    }
    
    @Test
    @Transactional
    public void getSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", school.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(school.getId().intValue()))
            .andExpect(jsonPath("$.schoolName").value(DEFAULT_SCHOOL_NAME.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.devise").value(DEFAULT_DEVISE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.ccCoef").value(DEFAULT_CC_COEF.doubleValue()))
            .andExpect(jsonPath("$.cCoef").value(DEFAULT_C_COEF.doubleValue()))
            .andExpect(jsonPath("$.logoLink").value(DEFAULT_LOGO_LINK.toString()))
            .andExpect(jsonPath("$.countryname").value(DEFAULT_COUNTRYNAME.toString()))
            .andExpect(jsonPath("$.countryMotto").value(DEFAULT_COUNTRY_MOTTO.toString()))
            .andExpect(jsonPath("$.countrySecretary").value(DEFAULT_COUNTRY_SECRETARY.toString()))
            .andExpect(jsonPath("$.countrySubSecretary").value(DEFAULT_COUNTRY_SUB_SECRETARY.toString()))
            .andExpect(jsonPath("$.schoolMotto").value(DEFAULT_SCHOOL_MOTTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSchool() throws Exception {
        // Get the school
        restSchoolMockMvc.perform(get("/api/schools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Update the school
        School updatedSchool = schoolRepository.findById(school.getId()).get();
        // Disconnect from session so that the updates on updatedSchool are not directly saved in db
        em.detach(updatedSchool);
        updatedSchool
            .schoolName(UPDATED_SCHOOL_NAME)
            /*.streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE)
            .telephone(UPDATED_TELEPHONE)
            .devise(UPDATED_DEVISE)
            .description(UPDATED_DESCRIPTION)
            .ccCoef(UPDATED_CC_COEF)
            .cCoef(UPDATED_C_COEF)
            .logoLink(UPDATED_LOGO_LINK)
            .countryname(UPDATED_COUNTRYNAME)
            .countryMotto(UPDATED_COUNTRY_MOTTO)
            .countrySecretary(UPDATED_COUNTRY_SECRETARY)
            .countrySubSecretary(UPDATED_COUNTRY_SUB_SECRETARY)
            .schoolMotto(UPDATED_SCHOOL_MOTTO)*/;

        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSchool)))
            .andExpect(status().isOk());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
        School testSchool = schoolList.get(schoolList.size() - 1);
        assertThat(testSchool.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testSchool.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testSchool.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testSchool.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSchool.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
        assertThat(testSchool.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testSchool.getDevise()).isEqualTo(UPDATED_DEVISE);
        assertThat(testSchool.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSchool.getCcCoef()).isEqualTo(UPDATED_CC_COEF);
        assertThat(testSchool.getcCoef()).isEqualTo(UPDATED_C_COEF);
        assertThat(testSchool.getLogoLink()).isEqualTo(UPDATED_LOGO_LINK);
        assertThat(testSchool.getCountryname()).isEqualTo(UPDATED_COUNTRYNAME);
        assertThat(testSchool.getCountryMotto()).isEqualTo(UPDATED_COUNTRY_MOTTO);
        assertThat(testSchool.getCountrySecretary()).isEqualTo(UPDATED_COUNTRY_SECRETARY);
        assertThat(testSchool.getCountrySubSecretary()).isEqualTo(UPDATED_COUNTRY_SUB_SECRETARY);
        assertThat(testSchool.getSchoolMotto()).isEqualTo(UPDATED_SCHOOL_MOTTO);
    }

    @Test
    @Transactional
    public void updateNonExistingSchool() throws Exception {
        int databaseSizeBeforeUpdate = schoolRepository.findAll().size();

        // Create the School

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSchoolMockMvc.perform(put("/api/schools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(school)))
            .andExpect(status().isBadRequest());

        // Validate the School in the database
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSchool() throws Exception {
        // Initialize the database
        schoolRepository.saveAndFlush(school);

        int databaseSizeBeforeDelete = schoolRepository.findAll().size();

        // Delete the school
        restSchoolMockMvc.perform(delete("/api/schools/{id}", school.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<School> schoolList = schoolRepository.findAll();
        assertThat(schoolList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(School.class);
        School school1 = new School();
        school1.setId(1L);
        School school2 = new School();
        school2.setId(school1.getId());
        assertThat(school1).isEqualTo(school2);
        school2.setId(2L);
        assertThat(school1).isNotEqualTo(school2);
        school1.setId(null);
        assertThat(school1).isNotEqualTo(school2);
    }
}
