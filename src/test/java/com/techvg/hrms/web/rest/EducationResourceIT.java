package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Education;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.enumeration.Degree;
import com.techvg.hrms.repository.EducationRepository;
import com.techvg.hrms.service.criteria.EducationCriteria;
import com.techvg.hrms.service.dto.EducationDTO;
import com.techvg.hrms.service.mapper.EducationMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EducationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EducationResourceIT {

    private static final String DEFAULT_SCHOOL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL_NAME = "BBBBBBBBBB";

    private static final Degree DEFAULT_DEGREE = Degree.PG;
    private static final Degree UPDATED_DEGREE = Degree.GRADUATION;

    private static final String DEFAULT_SECTOR = "AAAAAAAAAA";
    private static final String UPDATED_SECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_EDU_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EDU_TYPE = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_GRADE = 1D;
    private static final Double UPDATED_GRADE = 2D;
    private static final Double SMALLER_GRADE = 1D - 1D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/educations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private EducationMapper educationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEducationMockMvc;

    private Education education;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createEntity(EntityManager em) {
        Education education = new Education()
            .schoolName(DEFAULT_SCHOOL_NAME)
            .degree(DEFAULT_DEGREE)
            .sector(DEFAULT_SECTOR)
            .eduType(DEFAULT_EDU_TYPE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .grade(DEFAULT_GRADE)
            .description(DEFAULT_DESCRIPTION)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return education;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Education createUpdatedEntity(EntityManager em) {
        Education education = new Education()
            .schoolName(UPDATED_SCHOOL_NAME)
            .degree(UPDATED_DEGREE)
            .sector(UPDATED_SECTOR)
            .eduType(UPDATED_EDU_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return education;
    }

    @BeforeEach
    public void initTest() {
        education = createEntity(em);
    }

    @Test
    @Transactional
    void createEducation() throws Exception {
        int databaseSizeBeforeCreate = educationRepository.findAll().size();
        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);
        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isCreated());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate + 1);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testEducation.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testEducation.getSector()).isEqualTo(DEFAULT_SECTOR);
        assertThat(testEducation.getEduType()).isEqualTo(DEFAULT_EDU_TYPE);
        assertThat(testEducation.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEducation.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEducation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEducation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEducation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEducation.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testEducation.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testEducation.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testEducation.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createEducationWithExistingId() throws Exception {
        // Create the Education with an existing ID
        education.setId(1L);
        EducationDTO educationDTO = educationMapper.toDto(education);

        int databaseSizeBeforeCreate = educationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEducationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEducations() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME)))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].sector").value(hasItem(DEFAULT_SECTOR)))
            .andExpect(jsonPath("$.[*].eduType").value(hasItem(DEFAULT_EDU_TYPE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get the education
        restEducationMockMvc
            .perform(get(ENTITY_API_URL_ID, education.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(education.getId().intValue()))
            .andExpect(jsonPath("$.schoolName").value(DEFAULT_SCHOOL_NAME))
            .andExpect(jsonPath("$.degree").value(DEFAULT_DEGREE.toString()))
            .andExpect(jsonPath("$.sector").value(DEFAULT_SECTOR))
            .andExpect(jsonPath("$.eduType").value(DEFAULT_EDU_TYPE))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getEducationsByIdFiltering() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        Long id = education.getId();

        defaultEducationShouldBeFound("id.equals=" + id);
        defaultEducationShouldNotBeFound("id.notEquals=" + id);

        defaultEducationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEducationShouldNotBeFound("id.greaterThan=" + id);

        defaultEducationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEducationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEducationsBySchoolNameIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where schoolName equals to DEFAULT_SCHOOL_NAME
        defaultEducationShouldBeFound("schoolName.equals=" + DEFAULT_SCHOOL_NAME);

        // Get all the educationList where schoolName equals to UPDATED_SCHOOL_NAME
        defaultEducationShouldNotBeFound("schoolName.equals=" + UPDATED_SCHOOL_NAME);
    }

    @Test
    @Transactional
    void getAllEducationsBySchoolNameIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where schoolName in DEFAULT_SCHOOL_NAME or UPDATED_SCHOOL_NAME
        defaultEducationShouldBeFound("schoolName.in=" + DEFAULT_SCHOOL_NAME + "," + UPDATED_SCHOOL_NAME);

        // Get all the educationList where schoolName equals to UPDATED_SCHOOL_NAME
        defaultEducationShouldNotBeFound("schoolName.in=" + UPDATED_SCHOOL_NAME);
    }

    @Test
    @Transactional
    void getAllEducationsBySchoolNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where schoolName is not null
        defaultEducationShouldBeFound("schoolName.specified=true");

        // Get all the educationList where schoolName is null
        defaultEducationShouldNotBeFound("schoolName.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsBySchoolNameContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where schoolName contains DEFAULT_SCHOOL_NAME
        defaultEducationShouldBeFound("schoolName.contains=" + DEFAULT_SCHOOL_NAME);

        // Get all the educationList where schoolName contains UPDATED_SCHOOL_NAME
        defaultEducationShouldNotBeFound("schoolName.contains=" + UPDATED_SCHOOL_NAME);
    }

    @Test
    @Transactional
    void getAllEducationsBySchoolNameNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where schoolName does not contain DEFAULT_SCHOOL_NAME
        defaultEducationShouldNotBeFound("schoolName.doesNotContain=" + DEFAULT_SCHOOL_NAME);

        // Get all the educationList where schoolName does not contain UPDATED_SCHOOL_NAME
        defaultEducationShouldBeFound("schoolName.doesNotContain=" + UPDATED_SCHOOL_NAME);
    }

    @Test
    @Transactional
    void getAllEducationsByDegreeIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degree equals to DEFAULT_DEGREE
        defaultEducationShouldBeFound("degree.equals=" + DEFAULT_DEGREE);

        // Get all the educationList where degree equals to UPDATED_DEGREE
        defaultEducationShouldNotBeFound("degree.equals=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    void getAllEducationsByDegreeIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degree in DEFAULT_DEGREE or UPDATED_DEGREE
        defaultEducationShouldBeFound("degree.in=" + DEFAULT_DEGREE + "," + UPDATED_DEGREE);

        // Get all the educationList where degree equals to UPDATED_DEGREE
        defaultEducationShouldNotBeFound("degree.in=" + UPDATED_DEGREE);
    }

    @Test
    @Transactional
    void getAllEducationsByDegreeIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where degree is not null
        defaultEducationShouldBeFound("degree.specified=true");

        // Get all the educationList where degree is null
        defaultEducationShouldNotBeFound("degree.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsBySectorIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where sector equals to DEFAULT_SECTOR
        defaultEducationShouldBeFound("sector.equals=" + DEFAULT_SECTOR);

        // Get all the educationList where sector equals to UPDATED_SECTOR
        defaultEducationShouldNotBeFound("sector.equals=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    void getAllEducationsBySectorIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where sector in DEFAULT_SECTOR or UPDATED_SECTOR
        defaultEducationShouldBeFound("sector.in=" + DEFAULT_SECTOR + "," + UPDATED_SECTOR);

        // Get all the educationList where sector equals to UPDATED_SECTOR
        defaultEducationShouldNotBeFound("sector.in=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    void getAllEducationsBySectorIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where sector is not null
        defaultEducationShouldBeFound("sector.specified=true");

        // Get all the educationList where sector is null
        defaultEducationShouldNotBeFound("sector.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsBySectorContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where sector contains DEFAULT_SECTOR
        defaultEducationShouldBeFound("sector.contains=" + DEFAULT_SECTOR);

        // Get all the educationList where sector contains UPDATED_SECTOR
        defaultEducationShouldNotBeFound("sector.contains=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    void getAllEducationsBySectorNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where sector does not contain DEFAULT_SECTOR
        defaultEducationShouldNotBeFound("sector.doesNotContain=" + DEFAULT_SECTOR);

        // Get all the educationList where sector does not contain UPDATED_SECTOR
        defaultEducationShouldBeFound("sector.doesNotContain=" + UPDATED_SECTOR);
    }

    @Test
    @Transactional
    void getAllEducationsByEduTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where eduType equals to DEFAULT_EDU_TYPE
        defaultEducationShouldBeFound("eduType.equals=" + DEFAULT_EDU_TYPE);

        // Get all the educationList where eduType equals to UPDATED_EDU_TYPE
        defaultEducationShouldNotBeFound("eduType.equals=" + UPDATED_EDU_TYPE);
    }

    @Test
    @Transactional
    void getAllEducationsByEduTypeIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where eduType in DEFAULT_EDU_TYPE or UPDATED_EDU_TYPE
        defaultEducationShouldBeFound("eduType.in=" + DEFAULT_EDU_TYPE + "," + UPDATED_EDU_TYPE);

        // Get all the educationList where eduType equals to UPDATED_EDU_TYPE
        defaultEducationShouldNotBeFound("eduType.in=" + UPDATED_EDU_TYPE);
    }

    @Test
    @Transactional
    void getAllEducationsByEduTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where eduType is not null
        defaultEducationShouldBeFound("eduType.specified=true");

        // Get all the educationList where eduType is null
        defaultEducationShouldNotBeFound("eduType.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEduTypeContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where eduType contains DEFAULT_EDU_TYPE
        defaultEducationShouldBeFound("eduType.contains=" + DEFAULT_EDU_TYPE);

        // Get all the educationList where eduType contains UPDATED_EDU_TYPE
        defaultEducationShouldNotBeFound("eduType.contains=" + UPDATED_EDU_TYPE);
    }

    @Test
    @Transactional
    void getAllEducationsByEduTypeNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where eduType does not contain DEFAULT_EDU_TYPE
        defaultEducationShouldNotBeFound("eduType.doesNotContain=" + DEFAULT_EDU_TYPE);

        // Get all the educationList where eduType does not contain UPDATED_EDU_TYPE
        defaultEducationShouldBeFound("eduType.doesNotContain=" + UPDATED_EDU_TYPE);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate equals to DEFAULT_START_DATE
        defaultEducationShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the educationList where startDate equals to UPDATED_START_DATE
        defaultEducationShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultEducationShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the educationList where startDate equals to UPDATED_START_DATE
        defaultEducationShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where startDate is not null
        defaultEducationShouldBeFound("startDate.specified=true");

        // Get all the educationList where startDate is null
        defaultEducationShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate equals to DEFAULT_END_DATE
        defaultEducationShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the educationList where endDate equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultEducationShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the educationList where endDate equals to UPDATED_END_DATE
        defaultEducationShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllEducationsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where endDate is not null
        defaultEducationShouldBeFound("endDate.specified=true");

        // Get all the educationList where endDate is null
        defaultEducationShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade equals to DEFAULT_GRADE
        defaultEducationShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the educationList where grade equals to UPDATED_GRADE
        defaultEducationShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultEducationShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the educationList where grade equals to UPDATED_GRADE
        defaultEducationShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade is not null
        defaultEducationShouldBeFound("grade.specified=true");

        // Get all the educationList where grade is null
        defaultEducationShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade is greater than or equal to DEFAULT_GRADE
        defaultEducationShouldBeFound("grade.greaterThanOrEqual=" + DEFAULT_GRADE);

        // Get all the educationList where grade is greater than or equal to UPDATED_GRADE
        defaultEducationShouldNotBeFound("grade.greaterThanOrEqual=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade is less than or equal to DEFAULT_GRADE
        defaultEducationShouldBeFound("grade.lessThanOrEqual=" + DEFAULT_GRADE);

        // Get all the educationList where grade is less than or equal to SMALLER_GRADE
        defaultEducationShouldNotBeFound("grade.lessThanOrEqual=" + SMALLER_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsLessThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade is less than DEFAULT_GRADE
        defaultEducationShouldNotBeFound("grade.lessThan=" + DEFAULT_GRADE);

        // Get all the educationList where grade is less than UPDATED_GRADE
        defaultEducationShouldBeFound("grade.lessThan=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByGradeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where grade is greater than DEFAULT_GRADE
        defaultEducationShouldNotBeFound("grade.greaterThan=" + DEFAULT_GRADE);

        // Get all the educationList where grade is greater than SMALLER_GRADE
        defaultEducationShouldBeFound("grade.greaterThan=" + SMALLER_GRADE);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description equals to DEFAULT_DESCRIPTION
        defaultEducationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the educationList where description equals to UPDATED_DESCRIPTION
        defaultEducationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEducationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the educationList where description equals to UPDATED_DESCRIPTION
        defaultEducationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description is not null
        defaultEducationShouldBeFound("description.specified=true");

        // Get all the educationList where description is null
        defaultEducationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description contains DEFAULT_DESCRIPTION
        defaultEducationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the educationList where description contains UPDATED_DESCRIPTION
        defaultEducationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where description does not contain DEFAULT_DESCRIPTION
        defaultEducationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the educationList where description does not contain UPDATED_DESCRIPTION
        defaultEducationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEducationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the educationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEducationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEducationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the educationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEducationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModified is not null
        defaultEducationShouldBeFound("lastModified.specified=true");

        // Get all the educationList where lastModified is null
        defaultEducationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy is not null
        defaultEducationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the educationList where lastModifiedBy is null
        defaultEducationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEducationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the educationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEducationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdBy equals to DEFAULT_CREATED_BY
        defaultEducationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the educationList where createdBy equals to UPDATED_CREATED_BY
        defaultEducationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEducationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the educationList where createdBy equals to UPDATED_CREATED_BY
        defaultEducationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdBy is not null
        defaultEducationShouldBeFound("createdBy.specified=true");

        // Get all the educationList where createdBy is null
        defaultEducationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdBy contains DEFAULT_CREATED_BY
        defaultEducationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the educationList where createdBy contains UPDATED_CREATED_BY
        defaultEducationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEducationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the educationList where createdBy does not contain UPDATED_CREATED_BY
        defaultEducationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdOn equals to DEFAULT_CREATED_ON
        defaultEducationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the educationList where createdOn equals to UPDATED_CREATED_ON
        defaultEducationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultEducationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the educationList where createdOn equals to UPDATED_CREATED_ON
        defaultEducationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEducationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where createdOn is not null
        defaultEducationShouldBeFound("createdOn.specified=true");

        // Get all the educationList where createdOn is null
        defaultEducationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where isDeleted equals to DEFAULT_IS_DELETED
        defaultEducationShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the educationList where isDeleted equals to UPDATED_IS_DELETED
        defaultEducationShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllEducationsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultEducationShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the educationList where isDeleted equals to UPDATED_IS_DELETED
        defaultEducationShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllEducationsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where isDeleted is not null
        defaultEducationShouldBeFound("isDeleted.specified=true");

        // Get all the educationList where isDeleted is null
        defaultEducationShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultEducationShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the educationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultEducationShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultEducationShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the educationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultEducationShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField1 is not null
        defaultEducationShouldBeFound("freeField1.specified=true");

        // Get all the educationList where freeField1 is null
        defaultEducationShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultEducationShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the educationList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultEducationShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultEducationShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the educationList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultEducationShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultEducationShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the educationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultEducationShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultEducationShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the educationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultEducationShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField2 is not null
        defaultEducationShouldBeFound("freeField2.specified=true");

        // Get all the educationList where freeField2 is null
        defaultEducationShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultEducationShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the educationList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultEducationShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultEducationShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the educationList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultEducationShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultEducationShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the educationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultEducationShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultEducationShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the educationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultEducationShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField3 is not null
        defaultEducationShouldBeFound("freeField3.specified=true");

        // Get all the educationList where freeField3 is null
        defaultEducationShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultEducationShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the educationList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultEducationShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEducationsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        // Get all the educationList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultEducationShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the educationList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultEducationShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllEducationsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            educationRepository.saveAndFlush(education);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        education.setEmployee(employee);
        educationRepository.saveAndFlush(education);
        Long employeeId = employee.getId();

        // Get all the educationList where employee equals to employeeId
        defaultEducationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the educationList where employee equals to (employeeId + 1)
        defaultEducationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEducationShouldBeFound(String filter) throws Exception {
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(education.getId().intValue())))
            .andExpect(jsonPath("$.[*].schoolName").value(hasItem(DEFAULT_SCHOOL_NAME)))
            .andExpect(jsonPath("$.[*].degree").value(hasItem(DEFAULT_DEGREE.toString())))
            .andExpect(jsonPath("$.[*].sector").value(hasItem(DEFAULT_SECTOR)))
            .andExpect(jsonPath("$.[*].eduType").value(hasItem(DEFAULT_EDU_TYPE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEducationShouldNotBeFound(String filter) throws Exception {
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEducationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEducation() throws Exception {
        // Get the education
        restEducationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education
        Education updatedEducation = educationRepository.findById(education.getId()).get();
        // Disconnect from session so that the updates on updatedEducation are not directly saved in db
        em.detach(updatedEducation);
        updatedEducation
            .schoolName(UPDATED_SCHOOL_NAME)
            .degree(UPDATED_DEGREE)
            .sector(UPDATED_SECTOR)
            .eduType(UPDATED_EDU_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        EducationDTO educationDTO = educationMapper.toDto(updatedEducation);

        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testEducation.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEducation.getSector()).isEqualTo(UPDATED_SECTOR);
        assertThat(testEducation.getEduType()).isEqualTo(UPDATED_EDU_TYPE);
        assertThat(testEducation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEducation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEducation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEducation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEducation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testEducation.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testEducation.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(educationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEducationWithPatch() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education using partial update
        Education partialUpdatedEducation = new Education();
        partialUpdatedEducation.setId(education.getId());

        partialUpdatedEducation
            .eduType(UPDATED_EDU_TYPE)
            .startDate(UPDATED_START_DATE)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField3(UPDATED_FREE_FIELD_3);

        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducation))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getSchoolName()).isEqualTo(DEFAULT_SCHOOL_NAME);
        assertThat(testEducation.getDegree()).isEqualTo(DEFAULT_DEGREE);
        assertThat(testEducation.getSector()).isEqualTo(DEFAULT_SECTOR);
        assertThat(testEducation.getEduType()).isEqualTo(UPDATED_EDU_TYPE);
        assertThat(testEducation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testEducation.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEducation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEducation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEducation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEducation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testEducation.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testEducation.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateEducationWithPatch() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeUpdate = educationRepository.findAll().size();

        // Update the education using partial update
        Education partialUpdatedEducation = new Education();
        partialUpdatedEducation.setId(education.getId());

        partialUpdatedEducation
            .schoolName(UPDATED_SCHOOL_NAME)
            .degree(UPDATED_DEGREE)
            .sector(UPDATED_SECTOR)
            .eduType(UPDATED_EDU_TYPE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .grade(UPDATED_GRADE)
            .description(UPDATED_DESCRIPTION)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEducation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEducation))
            )
            .andExpect(status().isOk());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
        Education testEducation = educationList.get(educationList.size() - 1);
        assertThat(testEducation.getSchoolName()).isEqualTo(UPDATED_SCHOOL_NAME);
        assertThat(testEducation.getDegree()).isEqualTo(UPDATED_DEGREE);
        assertThat(testEducation.getSector()).isEqualTo(UPDATED_SECTOR);
        assertThat(testEducation.getEduType()).isEqualTo(UPDATED_EDU_TYPE);
        assertThat(testEducation.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testEducation.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testEducation.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEducation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEducation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEducation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEducation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEducation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEducation.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testEducation.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testEducation.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testEducation.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, educationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEducation() throws Exception {
        int databaseSizeBeforeUpdate = educationRepository.findAll().size();
        education.setId(count.incrementAndGet());

        // Create the Education
        EducationDTO educationDTO = educationMapper.toDto(education);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEducationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(educationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Education in the database
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEducation() throws Exception {
        // Initialize the database
        educationRepository.saveAndFlush(education);

        int databaseSizeBeforeDelete = educationRepository.findAll().size();

        // Delete the education
        restEducationMockMvc
            .perform(delete(ENTITY_API_URL_ID, education.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Education> educationList = educationRepository.findAll();
        assertThat(educationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
