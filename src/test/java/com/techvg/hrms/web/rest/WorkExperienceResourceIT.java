package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.WorkExperience;
import com.techvg.hrms.domain.enumeration.CompanyType;
import com.techvg.hrms.repository.WorkExperienceRepository;
import com.techvg.hrms.service.criteria.WorkExperienceCriteria;
import com.techvg.hrms.service.dto.WorkExperienceDTO;
import com.techvg.hrms.service.mapper.WorkExperienceMapper;
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
 * Integration tests for the {@link WorkExperienceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkExperienceResourceIT {

    private static final String DEFAULT_JOB_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_JOB_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_DESC = "AAAAAAAAAA";
    private static final String UPDATED_JOB_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final CompanyType DEFAULT_COMPANY_TYPE = CompanyType.GOVERNMENT_SECTOR;
    private static final CompanyType UPDATED_COMPANY_TYPE = CompanyType.PUBLIC_SECTOR;

    private static final String DEFAULT_ORG_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ORG_ADDRESS = "BBBBBBBBBB";

    private static final Double DEFAULT_YEAR_OF_EXP = 1D;
    private static final Double UPDATED_YEAR_OF_EXP = 2D;
    private static final Double SMALLER_YEAR_OF_EXP = 1D - 1D;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String DEFAULT_FREEFIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_5 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/work-experiences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    @Autowired
    private WorkExperienceMapper workExperienceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkExperienceMockMvc;

    private WorkExperience workExperience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkExperience createEntity(EntityManager em) {
        WorkExperience workExperience = new WorkExperience()
            .jobTitle(DEFAULT_JOB_TITLE)
            .jobDesc(DEFAULT_JOB_DESC)
            .companyName(DEFAULT_COMPANY_NAME)
            .companyType(DEFAULT_COMPANY_TYPE)
            .orgAddress(DEFAULT_ORG_ADDRESS)
            .yearOfExp(DEFAULT_YEAR_OF_EXP)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freefield4(DEFAULT_FREEFIELD_4)
            .freefield5(DEFAULT_FREEFIELD_5);
        return workExperience;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkExperience createUpdatedEntity(EntityManager em) {
        WorkExperience workExperience = new WorkExperience()
            .jobTitle(UPDATED_JOB_TITLE)
            .jobDesc(UPDATED_JOB_DESC)
            .companyName(UPDATED_COMPANY_NAME)
            .companyType(UPDATED_COMPANY_TYPE)
            .orgAddress(UPDATED_ORG_ADDRESS)
            .yearOfExp(UPDATED_YEAR_OF_EXP)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);
        return workExperience;
    }

    @BeforeEach
    public void initTest() {
        workExperience = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkExperience() throws Exception {
        int databaseSizeBeforeCreate = workExperienceRepository.findAll().size();
        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);
        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(DEFAULT_JOB_DESC);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testWorkExperience.getCompanyType()).isEqualTo(DEFAULT_COMPANY_TYPE);
        assertThat(testWorkExperience.getOrgAddress()).isEqualTo(DEFAULT_ORG_ADDRESS);
        assertThat(testWorkExperience.getYearOfExp()).isEqualTo(DEFAULT_YEAR_OF_EXP);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testWorkExperience.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorkExperience.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testWorkExperience.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testWorkExperience.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testWorkExperience.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testWorkExperience.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testWorkExperience.getFreefield4()).isEqualTo(DEFAULT_FREEFIELD_4);
        assertThat(testWorkExperience.getFreefield5()).isEqualTo(DEFAULT_FREEFIELD_5);
    }

    @Test
    @Transactional
    void createWorkExperienceWithExistingId() throws Exception {
        // Create the WorkExperience with an existing ID
        workExperience.setId(1L);
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        int databaseSizeBeforeCreate = workExperienceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkExperienceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkExperiences() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].jobDesc").value(hasItem(DEFAULT_JOB_DESC)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].orgAddress").value(hasItem(DEFAULT_ORG_ADDRESS)))
            .andExpect(jsonPath("$.[*].yearOfExp").value(hasItem(DEFAULT_YEAR_OF_EXP.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freefield4").value(hasItem(DEFAULT_FREEFIELD_4)))
            .andExpect(jsonPath("$.[*].freefield5").value(hasItem(DEFAULT_FREEFIELD_5)));
    }

    @Test
    @Transactional
    void getWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get the workExperience
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL_ID, workExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workExperience.getId().intValue()))
            .andExpect(jsonPath("$.jobTitle").value(DEFAULT_JOB_TITLE))
            .andExpect(jsonPath("$.jobDesc").value(DEFAULT_JOB_DESC))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.companyType").value(DEFAULT_COMPANY_TYPE.toString()))
            .andExpect(jsonPath("$.orgAddress").value(DEFAULT_ORG_ADDRESS))
            .andExpect(jsonPath("$.yearOfExp").value(DEFAULT_YEAR_OF_EXP.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freefield4").value(DEFAULT_FREEFIELD_4))
            .andExpect(jsonPath("$.freefield5").value(DEFAULT_FREEFIELD_5));
    }

    @Test
    @Transactional
    void getWorkExperiencesByIdFiltering() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        Long id = workExperience.getId();

        defaultWorkExperienceShouldBeFound("id.equals=" + id);
        defaultWorkExperienceShouldNotBeFound("id.notEquals=" + id);

        defaultWorkExperienceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkExperienceShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkExperienceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkExperienceShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle equals to DEFAULT_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.equals=" + DEFAULT_JOB_TITLE);

        // Get all the workExperienceList where jobTitle equals to UPDATED_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.equals=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle in DEFAULT_JOB_TITLE or UPDATED_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.in=" + DEFAULT_JOB_TITLE + "," + UPDATED_JOB_TITLE);

        // Get all the workExperienceList where jobTitle equals to UPDATED_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.in=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle is not null
        defaultWorkExperienceShouldBeFound("jobTitle.specified=true");

        // Get all the workExperienceList where jobTitle is null
        defaultWorkExperienceShouldNotBeFound("jobTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle contains DEFAULT_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.contains=" + DEFAULT_JOB_TITLE);

        // Get all the workExperienceList where jobTitle contains UPDATED_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.contains=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobTitleNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobTitle does not contain DEFAULT_JOB_TITLE
        defaultWorkExperienceShouldNotBeFound("jobTitle.doesNotContain=" + DEFAULT_JOB_TITLE);

        // Get all the workExperienceList where jobTitle does not contain UPDATED_JOB_TITLE
        defaultWorkExperienceShouldBeFound("jobTitle.doesNotContain=" + UPDATED_JOB_TITLE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc equals to DEFAULT_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.equals=" + DEFAULT_JOB_DESC);

        // Get all the workExperienceList where jobDesc equals to UPDATED_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.equals=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc in DEFAULT_JOB_DESC or UPDATED_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.in=" + DEFAULT_JOB_DESC + "," + UPDATED_JOB_DESC);

        // Get all the workExperienceList where jobDesc equals to UPDATED_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.in=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc is not null
        defaultWorkExperienceShouldBeFound("jobDesc.specified=true");

        // Get all the workExperienceList where jobDesc is null
        defaultWorkExperienceShouldNotBeFound("jobDesc.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc contains DEFAULT_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.contains=" + DEFAULT_JOB_DESC);

        // Get all the workExperienceList where jobDesc contains UPDATED_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.contains=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByJobDescNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where jobDesc does not contain DEFAULT_JOB_DESC
        defaultWorkExperienceShouldNotBeFound("jobDesc.doesNotContain=" + DEFAULT_JOB_DESC);

        // Get all the workExperienceList where jobDesc does not contain UPDATED_JOB_DESC
        defaultWorkExperienceShouldBeFound("jobDesc.doesNotContain=" + UPDATED_JOB_DESC);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName equals to DEFAULT_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.equals=" + DEFAULT_COMPANY_NAME);

        // Get all the workExperienceList where companyName equals to UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.equals=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName in DEFAULT_COMPANY_NAME or UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.in=" + DEFAULT_COMPANY_NAME + "," + UPDATED_COMPANY_NAME);

        // Get all the workExperienceList where companyName equals to UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.in=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName is not null
        defaultWorkExperienceShouldBeFound("companyName.specified=true");

        // Get all the workExperienceList where companyName is null
        defaultWorkExperienceShouldNotBeFound("companyName.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName contains DEFAULT_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.contains=" + DEFAULT_COMPANY_NAME);

        // Get all the workExperienceList where companyName contains UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.contains=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyNameNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyName does not contain DEFAULT_COMPANY_NAME
        defaultWorkExperienceShouldNotBeFound("companyName.doesNotContain=" + DEFAULT_COMPANY_NAME);

        // Get all the workExperienceList where companyName does not contain UPDATED_COMPANY_NAME
        defaultWorkExperienceShouldBeFound("companyName.doesNotContain=" + UPDATED_COMPANY_NAME);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyType equals to DEFAULT_COMPANY_TYPE
        defaultWorkExperienceShouldBeFound("companyType.equals=" + DEFAULT_COMPANY_TYPE);

        // Get all the workExperienceList where companyType equals to UPDATED_COMPANY_TYPE
        defaultWorkExperienceShouldNotBeFound("companyType.equals=" + UPDATED_COMPANY_TYPE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyTypeIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyType in DEFAULT_COMPANY_TYPE or UPDATED_COMPANY_TYPE
        defaultWorkExperienceShouldBeFound("companyType.in=" + DEFAULT_COMPANY_TYPE + "," + UPDATED_COMPANY_TYPE);

        // Get all the workExperienceList where companyType equals to UPDATED_COMPANY_TYPE
        defaultWorkExperienceShouldNotBeFound("companyType.in=" + UPDATED_COMPANY_TYPE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCompanyTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where companyType is not null
        defaultWorkExperienceShouldBeFound("companyType.specified=true");

        // Get all the workExperienceList where companyType is null
        defaultWorkExperienceShouldNotBeFound("companyType.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByOrgAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where orgAddress equals to DEFAULT_ORG_ADDRESS
        defaultWorkExperienceShouldBeFound("orgAddress.equals=" + DEFAULT_ORG_ADDRESS);

        // Get all the workExperienceList where orgAddress equals to UPDATED_ORG_ADDRESS
        defaultWorkExperienceShouldNotBeFound("orgAddress.equals=" + UPDATED_ORG_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByOrgAddressIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where orgAddress in DEFAULT_ORG_ADDRESS or UPDATED_ORG_ADDRESS
        defaultWorkExperienceShouldBeFound("orgAddress.in=" + DEFAULT_ORG_ADDRESS + "," + UPDATED_ORG_ADDRESS);

        // Get all the workExperienceList where orgAddress equals to UPDATED_ORG_ADDRESS
        defaultWorkExperienceShouldNotBeFound("orgAddress.in=" + UPDATED_ORG_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByOrgAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where orgAddress is not null
        defaultWorkExperienceShouldBeFound("orgAddress.specified=true");

        // Get all the workExperienceList where orgAddress is null
        defaultWorkExperienceShouldNotBeFound("orgAddress.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByOrgAddressContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where orgAddress contains DEFAULT_ORG_ADDRESS
        defaultWorkExperienceShouldBeFound("orgAddress.contains=" + DEFAULT_ORG_ADDRESS);

        // Get all the workExperienceList where orgAddress contains UPDATED_ORG_ADDRESS
        defaultWorkExperienceShouldNotBeFound("orgAddress.contains=" + UPDATED_ORG_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByOrgAddressNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where orgAddress does not contain DEFAULT_ORG_ADDRESS
        defaultWorkExperienceShouldNotBeFound("orgAddress.doesNotContain=" + DEFAULT_ORG_ADDRESS);

        // Get all the workExperienceList where orgAddress does not contain UPDATED_ORG_ADDRESS
        defaultWorkExperienceShouldBeFound("orgAddress.doesNotContain=" + UPDATED_ORG_ADDRESS);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp equals to DEFAULT_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.equals=" + DEFAULT_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp equals to UPDATED_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.equals=" + UPDATED_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp in DEFAULT_YEAR_OF_EXP or UPDATED_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.in=" + DEFAULT_YEAR_OF_EXP + "," + UPDATED_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp equals to UPDATED_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.in=" + UPDATED_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp is not null
        defaultWorkExperienceShouldBeFound("yearOfExp.specified=true");

        // Get all the workExperienceList where yearOfExp is null
        defaultWorkExperienceShouldNotBeFound("yearOfExp.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp is greater than or equal to DEFAULT_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.greaterThanOrEqual=" + DEFAULT_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp is greater than or equal to UPDATED_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.greaterThanOrEqual=" + UPDATED_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp is less than or equal to DEFAULT_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.lessThanOrEqual=" + DEFAULT_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp is less than or equal to SMALLER_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.lessThanOrEqual=" + SMALLER_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsLessThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp is less than DEFAULT_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.lessThan=" + DEFAULT_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp is less than UPDATED_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.lessThan=" + UPDATED_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByYearOfExpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where yearOfExp is greater than DEFAULT_YEAR_OF_EXP
        defaultWorkExperienceShouldNotBeFound("yearOfExp.greaterThan=" + DEFAULT_YEAR_OF_EXP);

        // Get all the workExperienceList where yearOfExp is greater than SMALLER_YEAR_OF_EXP
        defaultWorkExperienceShouldBeFound("yearOfExp.greaterThan=" + SMALLER_YEAR_OF_EXP);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where startDate equals to DEFAULT_START_DATE
        defaultWorkExperienceShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the workExperienceList where startDate equals to UPDATED_START_DATE
        defaultWorkExperienceShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultWorkExperienceShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the workExperienceList where startDate equals to UPDATED_START_DATE
        defaultWorkExperienceShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where startDate is not null
        defaultWorkExperienceShouldBeFound("startDate.specified=true");

        // Get all the workExperienceList where startDate is null
        defaultWorkExperienceShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where endDate equals to DEFAULT_END_DATE
        defaultWorkExperienceShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the workExperienceList where endDate equals to UPDATED_END_DATE
        defaultWorkExperienceShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultWorkExperienceShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the workExperienceList where endDate equals to UPDATED_END_DATE
        defaultWorkExperienceShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where endDate is not null
        defaultWorkExperienceShouldBeFound("endDate.specified=true");

        // Get all the workExperienceList where endDate is null
        defaultWorkExperienceShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultWorkExperienceShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the workExperienceList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkExperienceShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultWorkExperienceShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the workExperienceList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultWorkExperienceShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModified is not null
        defaultWorkExperienceShouldBeFound("lastModified.specified=true");

        // Get all the workExperienceList where lastModified is null
        defaultWorkExperienceShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy is not null
        defaultWorkExperienceShouldBeFound("lastModifiedBy.specified=true");

        // Get all the workExperienceList where lastModifiedBy is null
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultWorkExperienceShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the workExperienceList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultWorkExperienceShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdBy equals to DEFAULT_CREATED_BY
        defaultWorkExperienceShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the workExperienceList where createdBy equals to UPDATED_CREATED_BY
        defaultWorkExperienceShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultWorkExperienceShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the workExperienceList where createdBy equals to UPDATED_CREATED_BY
        defaultWorkExperienceShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdBy is not null
        defaultWorkExperienceShouldBeFound("createdBy.specified=true");

        // Get all the workExperienceList where createdBy is null
        defaultWorkExperienceShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdBy contains DEFAULT_CREATED_BY
        defaultWorkExperienceShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the workExperienceList where createdBy contains UPDATED_CREATED_BY
        defaultWorkExperienceShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdBy does not contain DEFAULT_CREATED_BY
        defaultWorkExperienceShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the workExperienceList where createdBy does not contain UPDATED_CREATED_BY
        defaultWorkExperienceShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdOn equals to DEFAULT_CREATED_ON
        defaultWorkExperienceShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the workExperienceList where createdOn equals to UPDATED_CREATED_ON
        defaultWorkExperienceShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultWorkExperienceShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the workExperienceList where createdOn equals to UPDATED_CREATED_ON
        defaultWorkExperienceShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where createdOn is not null
        defaultWorkExperienceShouldBeFound("createdOn.specified=true");

        // Get all the workExperienceList where createdOn is null
        defaultWorkExperienceShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where isDeleted equals to DEFAULT_IS_DELETED
        defaultWorkExperienceShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the workExperienceList where isDeleted equals to UPDATED_IS_DELETED
        defaultWorkExperienceShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultWorkExperienceShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the workExperienceList where isDeleted equals to UPDATED_IS_DELETED
        defaultWorkExperienceShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where isDeleted is not null
        defaultWorkExperienceShouldBeFound("isDeleted.specified=true");

        // Get all the workExperienceList where isDeleted is null
        defaultWorkExperienceShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultWorkExperienceShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the workExperienceList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultWorkExperienceShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultWorkExperienceShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the workExperienceList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultWorkExperienceShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField1 is not null
        defaultWorkExperienceShouldBeFound("freeField1.specified=true");

        // Get all the workExperienceList where freeField1 is null
        defaultWorkExperienceShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultWorkExperienceShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the workExperienceList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultWorkExperienceShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultWorkExperienceShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the workExperienceList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultWorkExperienceShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultWorkExperienceShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the workExperienceList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultWorkExperienceShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultWorkExperienceShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the workExperienceList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultWorkExperienceShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField2 is not null
        defaultWorkExperienceShouldBeFound("freeField2.specified=true");

        // Get all the workExperienceList where freeField2 is null
        defaultWorkExperienceShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultWorkExperienceShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the workExperienceList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultWorkExperienceShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultWorkExperienceShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the workExperienceList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultWorkExperienceShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultWorkExperienceShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the workExperienceList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultWorkExperienceShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultWorkExperienceShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the workExperienceList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultWorkExperienceShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField3 is not null
        defaultWorkExperienceShouldBeFound("freeField3.specified=true");

        // Get all the workExperienceList where freeField3 is null
        defaultWorkExperienceShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultWorkExperienceShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the workExperienceList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultWorkExperienceShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultWorkExperienceShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the workExperienceList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultWorkExperienceShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreefield4IsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freefield4 equals to DEFAULT_FREEFIELD_4
        defaultWorkExperienceShouldBeFound("freefield4.equals=" + DEFAULT_FREEFIELD_4);

        // Get all the workExperienceList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultWorkExperienceShouldNotBeFound("freefield4.equals=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreefield4IsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freefield4 in DEFAULT_FREEFIELD_4 or UPDATED_FREEFIELD_4
        defaultWorkExperienceShouldBeFound("freefield4.in=" + DEFAULT_FREEFIELD_4 + "," + UPDATED_FREEFIELD_4);

        // Get all the workExperienceList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultWorkExperienceShouldNotBeFound("freefield4.in=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreefield4IsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freefield4 is not null
        defaultWorkExperienceShouldBeFound("freefield4.specified=true");

        // Get all the workExperienceList where freefield4 is null
        defaultWorkExperienceShouldNotBeFound("freefield4.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreefield4ContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freefield4 contains DEFAULT_FREEFIELD_4
        defaultWorkExperienceShouldBeFound("freefield4.contains=" + DEFAULT_FREEFIELD_4);

        // Get all the workExperienceList where freefield4 contains UPDATED_FREEFIELD_4
        defaultWorkExperienceShouldNotBeFound("freefield4.contains=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreefield4NotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freefield4 does not contain DEFAULT_FREEFIELD_4
        defaultWorkExperienceShouldNotBeFound("freefield4.doesNotContain=" + DEFAULT_FREEFIELD_4);

        // Get all the workExperienceList where freefield4 does not contain UPDATED_FREEFIELD_4
        defaultWorkExperienceShouldBeFound("freefield4.doesNotContain=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreefield5IsEqualToSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freefield5 equals to DEFAULT_FREEFIELD_5
        defaultWorkExperienceShouldBeFound("freefield5.equals=" + DEFAULT_FREEFIELD_5);

        // Get all the workExperienceList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultWorkExperienceShouldNotBeFound("freefield5.equals=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreefield5IsInShouldWork() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freefield5 in DEFAULT_FREEFIELD_5 or UPDATED_FREEFIELD_5
        defaultWorkExperienceShouldBeFound("freefield5.in=" + DEFAULT_FREEFIELD_5 + "," + UPDATED_FREEFIELD_5);

        // Get all the workExperienceList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultWorkExperienceShouldNotBeFound("freefield5.in=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreefield5IsNullOrNotNull() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freefield5 is not null
        defaultWorkExperienceShouldBeFound("freefield5.specified=true");

        // Get all the workExperienceList where freefield5 is null
        defaultWorkExperienceShouldNotBeFound("freefield5.specified=false");
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreefield5ContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freefield5 contains DEFAULT_FREEFIELD_5
        defaultWorkExperienceShouldBeFound("freefield5.contains=" + DEFAULT_FREEFIELD_5);

        // Get all the workExperienceList where freefield5 contains UPDATED_FREEFIELD_5
        defaultWorkExperienceShouldNotBeFound("freefield5.contains=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByFreefield5NotContainsSomething() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        // Get all the workExperienceList where freefield5 does not contain DEFAULT_FREEFIELD_5
        defaultWorkExperienceShouldNotBeFound("freefield5.doesNotContain=" + DEFAULT_FREEFIELD_5);

        // Get all the workExperienceList where freefield5 does not contain UPDATED_FREEFIELD_5
        defaultWorkExperienceShouldBeFound("freefield5.doesNotContain=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllWorkExperiencesByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            workExperienceRepository.saveAndFlush(workExperience);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        workExperience.setEmployee(employee);
        workExperienceRepository.saveAndFlush(workExperience);
        Long employeeId = employee.getId();

        // Get all the workExperienceList where employee equals to employeeId
        defaultWorkExperienceShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the workExperienceList where employee equals to (employeeId + 1)
        defaultWorkExperienceShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkExperienceShouldBeFound(String filter) throws Exception {
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].jobTitle").value(hasItem(DEFAULT_JOB_TITLE)))
            .andExpect(jsonPath("$.[*].jobDesc").value(hasItem(DEFAULT_JOB_DESC)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyType").value(hasItem(DEFAULT_COMPANY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].orgAddress").value(hasItem(DEFAULT_ORG_ADDRESS)))
            .andExpect(jsonPath("$.[*].yearOfExp").value(hasItem(DEFAULT_YEAR_OF_EXP.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freefield4").value(hasItem(DEFAULT_FREEFIELD_4)))
            .andExpect(jsonPath("$.[*].freefield5").value(hasItem(DEFAULT_FREEFIELD_5)));

        // Check, that the count call also returns 1
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkExperienceShouldNotBeFound(String filter) throws Exception {
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkExperienceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWorkExperience() throws Exception {
        // Get the workExperience
        restWorkExperienceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience
        WorkExperience updatedWorkExperience = workExperienceRepository.findById(workExperience.getId()).get();
        // Disconnect from session so that the updates on updatedWorkExperience are not directly saved in db
        em.detach(updatedWorkExperience);
        updatedWorkExperience
            .jobTitle(UPDATED_JOB_TITLE)
            .jobDesc(UPDATED_JOB_DESC)
            .companyName(UPDATED_COMPANY_NAME)
            .companyType(UPDATED_COMPANY_TYPE)
            .orgAddress(UPDATED_ORG_ADDRESS)
            .yearOfExp(UPDATED_YEAR_OF_EXP)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(updatedWorkExperience);

        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(UPDATED_JOB_DESC);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testWorkExperience.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);
        assertThat(testWorkExperience.getOrgAddress()).isEqualTo(UPDATED_ORG_ADDRESS);
        assertThat(testWorkExperience.getYearOfExp()).isEqualTo(UPDATED_YEAR_OF_EXP);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkExperience.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkExperience.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWorkExperience.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testWorkExperience.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testWorkExperience.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testWorkExperience.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testWorkExperience.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testWorkExperience.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkExperienceWithPatch() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience using partial update
        WorkExperience partialUpdatedWorkExperience = new WorkExperience();
        partialUpdatedWorkExperience.setId(workExperience.getId());

        partialUpdatedWorkExperience
            .jobDesc(UPDATED_JOB_DESC)
            .companyName(UPDATED_COMPANY_NAME)
            .companyType(UPDATED_COMPANY_TYPE)
            .orgAddress(UPDATED_ORG_ADDRESS)
            .yearOfExp(UPDATED_YEAR_OF_EXP)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freefield5(UPDATED_FREEFIELD_5);

        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkExperience))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(DEFAULT_JOB_TITLE);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(UPDATED_JOB_DESC);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testWorkExperience.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);
        assertThat(testWorkExperience.getOrgAddress()).isEqualTo(UPDATED_ORG_ADDRESS);
        assertThat(testWorkExperience.getYearOfExp()).isEqualTo(UPDATED_YEAR_OF_EXP);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkExperience.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorkExperience.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWorkExperience.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testWorkExperience.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testWorkExperience.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testWorkExperience.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testWorkExperience.getFreefield4()).isEqualTo(DEFAULT_FREEFIELD_4);
        assertThat(testWorkExperience.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateWorkExperienceWithPatch() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();

        // Update the workExperience using partial update
        WorkExperience partialUpdatedWorkExperience = new WorkExperience();
        partialUpdatedWorkExperience.setId(workExperience.getId());

        partialUpdatedWorkExperience
            .jobTitle(UPDATED_JOB_TITLE)
            .jobDesc(UPDATED_JOB_DESC)
            .companyName(UPDATED_COMPANY_NAME)
            .companyType(UPDATED_COMPANY_TYPE)
            .orgAddress(UPDATED_ORG_ADDRESS)
            .yearOfExp(UPDATED_YEAR_OF_EXP)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);

        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkExperience.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkExperience))
            )
            .andExpect(status().isOk());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
        WorkExperience testWorkExperience = workExperienceList.get(workExperienceList.size() - 1);
        assertThat(testWorkExperience.getJobTitle()).isEqualTo(UPDATED_JOB_TITLE);
        assertThat(testWorkExperience.getJobDesc()).isEqualTo(UPDATED_JOB_DESC);
        assertThat(testWorkExperience.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testWorkExperience.getCompanyType()).isEqualTo(UPDATED_COMPANY_TYPE);
        assertThat(testWorkExperience.getOrgAddress()).isEqualTo(UPDATED_ORG_ADDRESS);
        assertThat(testWorkExperience.getYearOfExp()).isEqualTo(UPDATED_YEAR_OF_EXP);
        assertThat(testWorkExperience.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testWorkExperience.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testWorkExperience.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testWorkExperience.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testWorkExperience.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkExperience.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testWorkExperience.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testWorkExperience.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testWorkExperience.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testWorkExperience.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testWorkExperience.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testWorkExperience.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workExperienceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkExperience() throws Exception {
        int databaseSizeBeforeUpdate = workExperienceRepository.findAll().size();
        workExperience.setId(count.incrementAndGet());

        // Create the WorkExperience
        WorkExperienceDTO workExperienceDTO = workExperienceMapper.toDto(workExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workExperienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkExperience in the database
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkExperience() throws Exception {
        // Initialize the database
        workExperienceRepository.saveAndFlush(workExperience);

        int databaseSizeBeforeDelete = workExperienceRepository.findAll().size();

        // Delete the workExperience
        restWorkExperienceMockMvc
            .perform(delete(ENTITY_API_URL_ID, workExperience.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkExperience> workExperienceList = workExperienceRepository.findAll();
        assertThat(workExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
