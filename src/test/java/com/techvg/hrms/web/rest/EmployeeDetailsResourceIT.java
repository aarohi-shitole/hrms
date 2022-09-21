package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.EmployeeDetails;
import com.techvg.hrms.domain.enumeration.BloodGroup;
import com.techvg.hrms.domain.enumeration.MaritalStatus;
import com.techvg.hrms.repository.EmployeeDetailsRepository;
import com.techvg.hrms.service.criteria.EmployeeDetailsCriteria;
import com.techvg.hrms.service.dto.EmployeeDetailsDTO;
import com.techvg.hrms.service.mapper.EmployeeDetailsMapper;
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
 * Integration tests for the {@link EmployeeDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeDetailsResourceIT {

    private static final Long DEFAULT_AGE = 1L;
    private static final Long UPDATED_AGE = 2L;
    private static final Long SMALLER_AGE = 1L - 1L;

    private static final String DEFAULT_FATHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FATHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MOTHER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MOTHER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final Double DEFAULT_YEARS_OF_EXPERIENCE = 1D;
    private static final Double UPDATED_YEARS_OF_EXPERIENCE = 2D;
    private static final Double SMALLER_YEARS_OF_EXPERIENCE = 1D - 1D;

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final BloodGroup DEFAULT_BLOOD_GROUP = BloodGroup.A_POSITIVE;
    private static final BloodGroup UPDATED_BLOOD_GROUP = BloodGroup.B_POSITIVE;

    private static final String DEFAULT_BIRTH_DATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_EXPERTISE = "AAAAAAAAAA";
    private static final String UPDATED_EXPERTISE = "BBBBBBBBBB";

    private static final String DEFAULT_JOB_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_JOB_DESCRIPTION = "BBBBBBBBBB";

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.MARRIED;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.SINGLE;

    private static final String DEFAULT_SECONDARY_CONTACT = "AAAAAAAAAA";
    private static final String UPDATED_SECONDARY_CONTACT = "BBBBBBBBBB";

    private static final String DEFAULT_HOBBIES = "AAAAAAAAAA";
    private static final String UPDATED_HOBBIES = "BBBBBBBBBB";

    private static final String DEFAULT_AREA_INTEREST = "AAAAAAAAAA";
    private static final String UPDATED_AREA_INTEREST = "BBBBBBBBBB";

    private static final Long DEFAULT_NO_OF_DEPENDENT = 1L;
    private static final Long UPDATED_NO_OF_DEPENDENT = 2L;
    private static final Long SMALLER_NO_OF_DEPENDENT = 1L - 1L;

    private static final String DEFAULT_LANGUAGE_KNOWN = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_KNOWN = "BBBBBBBBBB";

    private static final String DEFAULT_NATINALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATINALITY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_JOINING_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JOINING_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FREEFIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_4 = "BBBBBBBBBB";

    private static final String DEFAULT_FREEFIELD_5 = "AAAAAAAAAA";
    private static final String UPDATED_FREEFIELD_5 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/employee-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Autowired
    private EmployeeDetailsMapper employeeDetailsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeDetailsMockMvc;

    private EmployeeDetails employeeDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDetails createEntity(EntityManager em) {
        EmployeeDetails employeeDetails = new EmployeeDetails()
            .age(DEFAULT_AGE)
            .fatherName(DEFAULT_FATHER_NAME)
            .motherName(DEFAULT_MOTHER_NAME)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .yearsOfExperience(DEFAULT_YEARS_OF_EXPERIENCE)
            .notes(DEFAULT_NOTES)
            .bloodGroup(DEFAULT_BLOOD_GROUP)
            .birthDate(DEFAULT_BIRTH_DATE)
            .designation(DEFAULT_DESIGNATION)
            .expertise(DEFAULT_EXPERTISE)
            .jobDescription(DEFAULT_JOB_DESCRIPTION)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .secondaryContact(DEFAULT_SECONDARY_CONTACT)
            .hobbies(DEFAULT_HOBBIES)
            .areaInterest(DEFAULT_AREA_INTEREST)
            .noOfDependent(DEFAULT_NO_OF_DEPENDENT)
            .languageKnown(DEFAULT_LANGUAGE_KNOWN)
            .natinality(DEFAULT_NATINALITY)
            .description(DEFAULT_DESCRIPTION)
            .department(DEFAULT_DEPARTMENT)
            .joiningDate(DEFAULT_JOINING_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .freefield1(DEFAULT_FREEFIELD_1)
            .freefield2(DEFAULT_FREEFIELD_2)
            .freefield3(DEFAULT_FREEFIELD_3)
            .freefield4(DEFAULT_FREEFIELD_4)
            .freefield5(DEFAULT_FREEFIELD_5);
        return employeeDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeDetails createUpdatedEntity(EntityManager em) {
        EmployeeDetails employeeDetails = new EmployeeDetails()
            .age(UPDATED_AGE)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE)
            .notes(UPDATED_NOTES)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .birthDate(UPDATED_BIRTH_DATE)
            .designation(UPDATED_DESIGNATION)
            .expertise(UPDATED_EXPERTISE)
            .jobDescription(UPDATED_JOB_DESCRIPTION)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .secondaryContact(UPDATED_SECONDARY_CONTACT)
            .hobbies(UPDATED_HOBBIES)
            .areaInterest(UPDATED_AREA_INTEREST)
            .noOfDependent(UPDATED_NO_OF_DEPENDENT)
            .languageKnown(UPDATED_LANGUAGE_KNOWN)
            .natinality(UPDATED_NATINALITY)
            .description(UPDATED_DESCRIPTION)
            .department(UPDATED_DEPARTMENT)
            .joiningDate(UPDATED_JOINING_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);
        return employeeDetails;
    }

    @BeforeEach
    public void initTest() {
        employeeDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeDetails() throws Exception {
        int databaseSizeBeforeCreate = employeeDetailsRepository.findAll().size();
        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);
        restEmployeeDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testEmployeeDetails.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testEmployeeDetails.getMotherName()).isEqualTo(DEFAULT_MOTHER_NAME);
        assertThat(testEmployeeDetails.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployeeDetails.getYearsOfExperience()).isEqualTo(DEFAULT_YEARS_OF_EXPERIENCE);
        assertThat(testEmployeeDetails.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testEmployeeDetails.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testEmployeeDetails.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployeeDetails.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testEmployeeDetails.getExpertise()).isEqualTo(DEFAULT_EXPERTISE);
        assertThat(testEmployeeDetails.getJobDescription()).isEqualTo(DEFAULT_JOB_DESCRIPTION);
        assertThat(testEmployeeDetails.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testEmployeeDetails.getSecondaryContact()).isEqualTo(DEFAULT_SECONDARY_CONTACT);
        assertThat(testEmployeeDetails.getHobbies()).isEqualTo(DEFAULT_HOBBIES);
        assertThat(testEmployeeDetails.getAreaInterest()).isEqualTo(DEFAULT_AREA_INTEREST);
        assertThat(testEmployeeDetails.getNoOfDependent()).isEqualTo(DEFAULT_NO_OF_DEPENDENT);
        assertThat(testEmployeeDetails.getLanguageKnown()).isEqualTo(DEFAULT_LANGUAGE_KNOWN);
        assertThat(testEmployeeDetails.getNatinality()).isEqualTo(DEFAULT_NATINALITY);
        assertThat(testEmployeeDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEmployeeDetails.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testEmployeeDetails.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testEmployeeDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployeeDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmployeeDetails.getFreefield1()).isEqualTo(DEFAULT_FREEFIELD_1);
        assertThat(testEmployeeDetails.getFreefield2()).isEqualTo(DEFAULT_FREEFIELD_2);
        assertThat(testEmployeeDetails.getFreefield3()).isEqualTo(DEFAULT_FREEFIELD_3);
        assertThat(testEmployeeDetails.getFreefield4()).isEqualTo(DEFAULT_FREEFIELD_4);
        assertThat(testEmployeeDetails.getFreefield5()).isEqualTo(DEFAULT_FREEFIELD_5);
    }

    @Test
    @Transactional
    void createEmployeeDetailsWithExistingId() throws Exception {
        // Create the EmployeeDetails with an existing ID
        employeeDetails.setId(1L);
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        int databaseSizeBeforeCreate = employeeDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].yearsOfExperience").value(hasItem(DEFAULT_YEARS_OF_EXPERIENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].expertise").value(hasItem(DEFAULT_EXPERTISE)))
            .andExpect(jsonPath("$.[*].jobDescription").value(hasItem(DEFAULT_JOB_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].secondaryContact").value(hasItem(DEFAULT_SECONDARY_CONTACT)))
            .andExpect(jsonPath("$.[*].hobbies").value(hasItem(DEFAULT_HOBBIES)))
            .andExpect(jsonPath("$.[*].areaInterest").value(hasItem(DEFAULT_AREA_INTEREST)))
            .andExpect(jsonPath("$.[*].noOfDependent").value(hasItem(DEFAULT_NO_OF_DEPENDENT.intValue())))
            .andExpect(jsonPath("$.[*].languageKnown").value(hasItem(DEFAULT_LANGUAGE_KNOWN)))
            .andExpect(jsonPath("$.[*].natinality").value(hasItem(DEFAULT_NATINALITY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].freefield3").value(hasItem(DEFAULT_FREEFIELD_3)))
            .andExpect(jsonPath("$.[*].freefield4").value(hasItem(DEFAULT_FREEFIELD_4)))
            .andExpect(jsonPath("$.[*].freefield5").value(hasItem(DEFAULT_FREEFIELD_5)));
    }

    @Test
    @Transactional
    void getEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get the employeeDetails
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeDetails.getId().intValue()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE.intValue()))
            .andExpect(jsonPath("$.fatherName").value(DEFAULT_FATHER_NAME))
            .andExpect(jsonPath("$.motherName").value(DEFAULT_MOTHER_NAME))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.yearsOfExperience").value(DEFAULT_YEARS_OF_EXPERIENCE.doubleValue()))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.bloodGroup").value(DEFAULT_BLOOD_GROUP.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION))
            .andExpect(jsonPath("$.expertise").value(DEFAULT_EXPERTISE))
            .andExpect(jsonPath("$.jobDescription").value(DEFAULT_JOB_DESCRIPTION))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.secondaryContact").value(DEFAULT_SECONDARY_CONTACT))
            .andExpect(jsonPath("$.hobbies").value(DEFAULT_HOBBIES))
            .andExpect(jsonPath("$.areaInterest").value(DEFAULT_AREA_INTEREST))
            .andExpect(jsonPath("$.noOfDependent").value(DEFAULT_NO_OF_DEPENDENT.intValue()))
            .andExpect(jsonPath("$.languageKnown").value(DEFAULT_LANGUAGE_KNOWN))
            .andExpect(jsonPath("$.natinality").value(DEFAULT_NATINALITY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.joiningDate").value(DEFAULT_JOINING_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.freefield1").value(DEFAULT_FREEFIELD_1))
            .andExpect(jsonPath("$.freefield2").value(DEFAULT_FREEFIELD_2))
            .andExpect(jsonPath("$.freefield3").value(DEFAULT_FREEFIELD_3))
            .andExpect(jsonPath("$.freefield4").value(DEFAULT_FREEFIELD_4))
            .andExpect(jsonPath("$.freefield5").value(DEFAULT_FREEFIELD_5));
    }

    @Test
    @Transactional
    void getEmployeeDetailsByIdFiltering() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        Long id = employeeDetails.getId();

        defaultEmployeeDetailsShouldBeFound("id.equals=" + id);
        defaultEmployeeDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age equals to DEFAULT_AGE
        defaultEmployeeDetailsShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the employeeDetailsList where age equals to UPDATED_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age in DEFAULT_AGE or UPDATED_AGE
        defaultEmployeeDetailsShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the employeeDetailsList where age equals to UPDATED_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age is not null
        defaultEmployeeDetailsShouldBeFound("age.specified=true");

        // Get all the employeeDetailsList where age is null
        defaultEmployeeDetailsShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age is greater than or equal to DEFAULT_AGE
        defaultEmployeeDetailsShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the employeeDetailsList where age is greater than or equal to UPDATED_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age is less than or equal to DEFAULT_AGE
        defaultEmployeeDetailsShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the employeeDetailsList where age is less than or equal to SMALLER_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age is less than DEFAULT_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the employeeDetailsList where age is less than UPDATED_AGE
        defaultEmployeeDetailsShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where age is greater than DEFAULT_AGE
        defaultEmployeeDetailsShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the employeeDetailsList where age is greater than SMALLER_AGE
        defaultEmployeeDetailsShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFatherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where fatherName equals to DEFAULT_FATHER_NAME
        defaultEmployeeDetailsShouldBeFound("fatherName.equals=" + DEFAULT_FATHER_NAME);

        // Get all the employeeDetailsList where fatherName equals to UPDATED_FATHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("fatherName.equals=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFatherNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where fatherName in DEFAULT_FATHER_NAME or UPDATED_FATHER_NAME
        defaultEmployeeDetailsShouldBeFound("fatherName.in=" + DEFAULT_FATHER_NAME + "," + UPDATED_FATHER_NAME);

        // Get all the employeeDetailsList where fatherName equals to UPDATED_FATHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("fatherName.in=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFatherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where fatherName is not null
        defaultEmployeeDetailsShouldBeFound("fatherName.specified=true");

        // Get all the employeeDetailsList where fatherName is null
        defaultEmployeeDetailsShouldNotBeFound("fatherName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFatherNameContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where fatherName contains DEFAULT_FATHER_NAME
        defaultEmployeeDetailsShouldBeFound("fatherName.contains=" + DEFAULT_FATHER_NAME);

        // Get all the employeeDetailsList where fatherName contains UPDATED_FATHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("fatherName.contains=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFatherNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where fatherName does not contain DEFAULT_FATHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("fatherName.doesNotContain=" + DEFAULT_FATHER_NAME);

        // Get all the employeeDetailsList where fatherName does not contain UPDATED_FATHER_NAME
        defaultEmployeeDetailsShouldBeFound("fatherName.doesNotContain=" + UPDATED_FATHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMotherNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where motherName equals to DEFAULT_MOTHER_NAME
        defaultEmployeeDetailsShouldBeFound("motherName.equals=" + DEFAULT_MOTHER_NAME);

        // Get all the employeeDetailsList where motherName equals to UPDATED_MOTHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("motherName.equals=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMotherNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where motherName in DEFAULT_MOTHER_NAME or UPDATED_MOTHER_NAME
        defaultEmployeeDetailsShouldBeFound("motherName.in=" + DEFAULT_MOTHER_NAME + "," + UPDATED_MOTHER_NAME);

        // Get all the employeeDetailsList where motherName equals to UPDATED_MOTHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("motherName.in=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMotherNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where motherName is not null
        defaultEmployeeDetailsShouldBeFound("motherName.specified=true");

        // Get all the employeeDetailsList where motherName is null
        defaultEmployeeDetailsShouldNotBeFound("motherName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMotherNameContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where motherName contains DEFAULT_MOTHER_NAME
        defaultEmployeeDetailsShouldBeFound("motherName.contains=" + DEFAULT_MOTHER_NAME);

        // Get all the employeeDetailsList where motherName contains UPDATED_MOTHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("motherName.contains=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMotherNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where motherName does not contain DEFAULT_MOTHER_NAME
        defaultEmployeeDetailsShouldNotBeFound("motherName.doesNotContain=" + DEFAULT_MOTHER_NAME);

        // Get all the employeeDetailsList where motherName does not contain UPDATED_MOTHER_NAME
        defaultEmployeeDetailsShouldBeFound("motherName.doesNotContain=" + UPDATED_MOTHER_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultEmployeeDetailsShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeDetailsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeDetailsShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultEmployeeDetailsShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the employeeDetailsList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultEmployeeDetailsShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId is not null
        defaultEmployeeDetailsShouldBeFound("employeeId.specified=true");

        // Get all the employeeDetailsList where employeeId is null
        defaultEmployeeDetailsShouldNotBeFound("employeeId.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId contains DEFAULT_EMPLOYEE_ID
        defaultEmployeeDetailsShouldBeFound("employeeId.contains=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeDetailsList where employeeId contains UPDATED_EMPLOYEE_ID
        defaultEmployeeDetailsShouldNotBeFound("employeeId.contains=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByEmployeeIdNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where employeeId does not contain DEFAULT_EMPLOYEE_ID
        defaultEmployeeDetailsShouldNotBeFound("employeeId.doesNotContain=" + DEFAULT_EMPLOYEE_ID);

        // Get all the employeeDetailsList where employeeId does not contain UPDATED_EMPLOYEE_ID
        defaultEmployeeDetailsShouldBeFound("employeeId.doesNotContain=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByYearsOfExperienceIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where yearsOfExperience equals to DEFAULT_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldBeFound("yearsOfExperience.equals=" + DEFAULT_YEARS_OF_EXPERIENCE);

        // Get all the employeeDetailsList where yearsOfExperience equals to UPDATED_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldNotBeFound("yearsOfExperience.equals=" + UPDATED_YEARS_OF_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByYearsOfExperienceIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where yearsOfExperience in DEFAULT_YEARS_OF_EXPERIENCE or UPDATED_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldBeFound("yearsOfExperience.in=" + DEFAULT_YEARS_OF_EXPERIENCE + "," + UPDATED_YEARS_OF_EXPERIENCE);

        // Get all the employeeDetailsList where yearsOfExperience equals to UPDATED_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldNotBeFound("yearsOfExperience.in=" + UPDATED_YEARS_OF_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByYearsOfExperienceIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where yearsOfExperience is not null
        defaultEmployeeDetailsShouldBeFound("yearsOfExperience.specified=true");

        // Get all the employeeDetailsList where yearsOfExperience is null
        defaultEmployeeDetailsShouldNotBeFound("yearsOfExperience.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByYearsOfExperienceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where yearsOfExperience is greater than or equal to DEFAULT_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldBeFound("yearsOfExperience.greaterThanOrEqual=" + DEFAULT_YEARS_OF_EXPERIENCE);

        // Get all the employeeDetailsList where yearsOfExperience is greater than or equal to UPDATED_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldNotBeFound("yearsOfExperience.greaterThanOrEqual=" + UPDATED_YEARS_OF_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByYearsOfExperienceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where yearsOfExperience is less than or equal to DEFAULT_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldBeFound("yearsOfExperience.lessThanOrEqual=" + DEFAULT_YEARS_OF_EXPERIENCE);

        // Get all the employeeDetailsList where yearsOfExperience is less than or equal to SMALLER_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldNotBeFound("yearsOfExperience.lessThanOrEqual=" + SMALLER_YEARS_OF_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByYearsOfExperienceIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where yearsOfExperience is less than DEFAULT_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldNotBeFound("yearsOfExperience.lessThan=" + DEFAULT_YEARS_OF_EXPERIENCE);

        // Get all the employeeDetailsList where yearsOfExperience is less than UPDATED_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldBeFound("yearsOfExperience.lessThan=" + UPDATED_YEARS_OF_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByYearsOfExperienceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where yearsOfExperience is greater than DEFAULT_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldNotBeFound("yearsOfExperience.greaterThan=" + DEFAULT_YEARS_OF_EXPERIENCE);

        // Get all the employeeDetailsList where yearsOfExperience is greater than SMALLER_YEARS_OF_EXPERIENCE
        defaultEmployeeDetailsShouldBeFound("yearsOfExperience.greaterThan=" + SMALLER_YEARS_OF_EXPERIENCE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where notes equals to DEFAULT_NOTES
        defaultEmployeeDetailsShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the employeeDetailsList where notes equals to UPDATED_NOTES
        defaultEmployeeDetailsShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultEmployeeDetailsShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the employeeDetailsList where notes equals to UPDATED_NOTES
        defaultEmployeeDetailsShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where notes is not null
        defaultEmployeeDetailsShouldBeFound("notes.specified=true");

        // Get all the employeeDetailsList where notes is null
        defaultEmployeeDetailsShouldNotBeFound("notes.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNotesContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where notes contains DEFAULT_NOTES
        defaultEmployeeDetailsShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the employeeDetailsList where notes contains UPDATED_NOTES
        defaultEmployeeDetailsShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where notes does not contain DEFAULT_NOTES
        defaultEmployeeDetailsShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the employeeDetailsList where notes does not contain UPDATED_NOTES
        defaultEmployeeDetailsShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodGroup equals to DEFAULT_BLOOD_GROUP
        defaultEmployeeDetailsShouldBeFound("bloodGroup.equals=" + DEFAULT_BLOOD_GROUP);

        // Get all the employeeDetailsList where bloodGroup equals to UPDATED_BLOOD_GROUP
        defaultEmployeeDetailsShouldNotBeFound("bloodGroup.equals=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodGroupIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodGroup in DEFAULT_BLOOD_GROUP or UPDATED_BLOOD_GROUP
        defaultEmployeeDetailsShouldBeFound("bloodGroup.in=" + DEFAULT_BLOOD_GROUP + "," + UPDATED_BLOOD_GROUP);

        // Get all the employeeDetailsList where bloodGroup equals to UPDATED_BLOOD_GROUP
        defaultEmployeeDetailsShouldNotBeFound("bloodGroup.in=" + UPDATED_BLOOD_GROUP);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBloodGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where bloodGroup is not null
        defaultEmployeeDetailsShouldBeFound("bloodGroup.specified=true");

        // Get all the employeeDetailsList where bloodGroup is null
        defaultEmployeeDetailsShouldNotBeFound("bloodGroup.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultEmployeeDetailsShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeDetailsList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEmployeeDetailsShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultEmployeeDetailsShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the employeeDetailsList where birthDate equals to UPDATED_BIRTH_DATE
        defaultEmployeeDetailsShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where birthDate is not null
        defaultEmployeeDetailsShouldBeFound("birthDate.specified=true");

        // Get all the employeeDetailsList where birthDate is null
        defaultEmployeeDetailsShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBirthDateContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where birthDate contains DEFAULT_BIRTH_DATE
        defaultEmployeeDetailsShouldBeFound("birthDate.contains=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeDetailsList where birthDate contains UPDATED_BIRTH_DATE
        defaultEmployeeDetailsShouldNotBeFound("birthDate.contains=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByBirthDateNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where birthDate does not contain DEFAULT_BIRTH_DATE
        defaultEmployeeDetailsShouldNotBeFound("birthDate.doesNotContain=" + DEFAULT_BIRTH_DATE);

        // Get all the employeeDetailsList where birthDate does not contain UPDATED_BIRTH_DATE
        defaultEmployeeDetailsShouldBeFound("birthDate.doesNotContain=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where designation equals to DEFAULT_DESIGNATION
        defaultEmployeeDetailsShouldBeFound("designation.equals=" + DEFAULT_DESIGNATION);

        // Get all the employeeDetailsList where designation equals to UPDATED_DESIGNATION
        defaultEmployeeDetailsShouldNotBeFound("designation.equals=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where designation in DEFAULT_DESIGNATION or UPDATED_DESIGNATION
        defaultEmployeeDetailsShouldBeFound("designation.in=" + DEFAULT_DESIGNATION + "," + UPDATED_DESIGNATION);

        // Get all the employeeDetailsList where designation equals to UPDATED_DESIGNATION
        defaultEmployeeDetailsShouldNotBeFound("designation.in=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where designation is not null
        defaultEmployeeDetailsShouldBeFound("designation.specified=true");

        // Get all the employeeDetailsList where designation is null
        defaultEmployeeDetailsShouldNotBeFound("designation.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDesignationContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where designation contains DEFAULT_DESIGNATION
        defaultEmployeeDetailsShouldBeFound("designation.contains=" + DEFAULT_DESIGNATION);

        // Get all the employeeDetailsList where designation contains UPDATED_DESIGNATION
        defaultEmployeeDetailsShouldNotBeFound("designation.contains=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDesignationNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where designation does not contain DEFAULT_DESIGNATION
        defaultEmployeeDetailsShouldNotBeFound("designation.doesNotContain=" + DEFAULT_DESIGNATION);

        // Get all the employeeDetailsList where designation does not contain UPDATED_DESIGNATION
        defaultEmployeeDetailsShouldBeFound("designation.doesNotContain=" + UPDATED_DESIGNATION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByExpertiseIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where expertise equals to DEFAULT_EXPERTISE
        defaultEmployeeDetailsShouldBeFound("expertise.equals=" + DEFAULT_EXPERTISE);

        // Get all the employeeDetailsList where expertise equals to UPDATED_EXPERTISE
        defaultEmployeeDetailsShouldNotBeFound("expertise.equals=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByExpertiseIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where expertise in DEFAULT_EXPERTISE or UPDATED_EXPERTISE
        defaultEmployeeDetailsShouldBeFound("expertise.in=" + DEFAULT_EXPERTISE + "," + UPDATED_EXPERTISE);

        // Get all the employeeDetailsList where expertise equals to UPDATED_EXPERTISE
        defaultEmployeeDetailsShouldNotBeFound("expertise.in=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByExpertiseIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where expertise is not null
        defaultEmployeeDetailsShouldBeFound("expertise.specified=true");

        // Get all the employeeDetailsList where expertise is null
        defaultEmployeeDetailsShouldNotBeFound("expertise.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByExpertiseContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where expertise contains DEFAULT_EXPERTISE
        defaultEmployeeDetailsShouldBeFound("expertise.contains=" + DEFAULT_EXPERTISE);

        // Get all the employeeDetailsList where expertise contains UPDATED_EXPERTISE
        defaultEmployeeDetailsShouldNotBeFound("expertise.contains=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByExpertiseNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where expertise does not contain DEFAULT_EXPERTISE
        defaultEmployeeDetailsShouldNotBeFound("expertise.doesNotContain=" + DEFAULT_EXPERTISE);

        // Get all the employeeDetailsList where expertise does not contain UPDATED_EXPERTISE
        defaultEmployeeDetailsShouldBeFound("expertise.doesNotContain=" + UPDATED_EXPERTISE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByJobDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where jobDescription equals to DEFAULT_JOB_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("jobDescription.equals=" + DEFAULT_JOB_DESCRIPTION);

        // Get all the employeeDetailsList where jobDescription equals to UPDATED_JOB_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("jobDescription.equals=" + UPDATED_JOB_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByJobDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where jobDescription in DEFAULT_JOB_DESCRIPTION or UPDATED_JOB_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("jobDescription.in=" + DEFAULT_JOB_DESCRIPTION + "," + UPDATED_JOB_DESCRIPTION);

        // Get all the employeeDetailsList where jobDescription equals to UPDATED_JOB_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("jobDescription.in=" + UPDATED_JOB_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByJobDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where jobDescription is not null
        defaultEmployeeDetailsShouldBeFound("jobDescription.specified=true");

        // Get all the employeeDetailsList where jobDescription is null
        defaultEmployeeDetailsShouldNotBeFound("jobDescription.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByJobDescriptionContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where jobDescription contains DEFAULT_JOB_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("jobDescription.contains=" + DEFAULT_JOB_DESCRIPTION);

        // Get all the employeeDetailsList where jobDescription contains UPDATED_JOB_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("jobDescription.contains=" + UPDATED_JOB_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByJobDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where jobDescription does not contain DEFAULT_JOB_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("jobDescription.doesNotContain=" + DEFAULT_JOB_DESCRIPTION);

        // Get all the employeeDetailsList where jobDescription does not contain UPDATED_JOB_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("jobDescription.doesNotContain=" + UPDATED_JOB_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalStatus equals to DEFAULT_MARITAL_STATUS
        defaultEmployeeDetailsShouldBeFound("maritalStatus.equals=" + DEFAULT_MARITAL_STATUS);

        // Get all the employeeDetailsList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEmployeeDetailsShouldNotBeFound("maritalStatus.equals=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalStatus in DEFAULT_MARITAL_STATUS or UPDATED_MARITAL_STATUS
        defaultEmployeeDetailsShouldBeFound("maritalStatus.in=" + DEFAULT_MARITAL_STATUS + "," + UPDATED_MARITAL_STATUS);

        // Get all the employeeDetailsList where maritalStatus equals to UPDATED_MARITAL_STATUS
        defaultEmployeeDetailsShouldNotBeFound("maritalStatus.in=" + UPDATED_MARITAL_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByMaritalStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where maritalStatus is not null
        defaultEmployeeDetailsShouldBeFound("maritalStatus.specified=true");

        // Get all the employeeDetailsList where maritalStatus is null
        defaultEmployeeDetailsShouldNotBeFound("maritalStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsBySecondaryContactIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where secondaryContact equals to DEFAULT_SECONDARY_CONTACT
        defaultEmployeeDetailsShouldBeFound("secondaryContact.equals=" + DEFAULT_SECONDARY_CONTACT);

        // Get all the employeeDetailsList where secondaryContact equals to UPDATED_SECONDARY_CONTACT
        defaultEmployeeDetailsShouldNotBeFound("secondaryContact.equals=" + UPDATED_SECONDARY_CONTACT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsBySecondaryContactIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where secondaryContact in DEFAULT_SECONDARY_CONTACT or UPDATED_SECONDARY_CONTACT
        defaultEmployeeDetailsShouldBeFound("secondaryContact.in=" + DEFAULT_SECONDARY_CONTACT + "," + UPDATED_SECONDARY_CONTACT);

        // Get all the employeeDetailsList where secondaryContact equals to UPDATED_SECONDARY_CONTACT
        defaultEmployeeDetailsShouldNotBeFound("secondaryContact.in=" + UPDATED_SECONDARY_CONTACT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsBySecondaryContactIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where secondaryContact is not null
        defaultEmployeeDetailsShouldBeFound("secondaryContact.specified=true");

        // Get all the employeeDetailsList where secondaryContact is null
        defaultEmployeeDetailsShouldNotBeFound("secondaryContact.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsBySecondaryContactContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where secondaryContact contains DEFAULT_SECONDARY_CONTACT
        defaultEmployeeDetailsShouldBeFound("secondaryContact.contains=" + DEFAULT_SECONDARY_CONTACT);

        // Get all the employeeDetailsList where secondaryContact contains UPDATED_SECONDARY_CONTACT
        defaultEmployeeDetailsShouldNotBeFound("secondaryContact.contains=" + UPDATED_SECONDARY_CONTACT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsBySecondaryContactNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where secondaryContact does not contain DEFAULT_SECONDARY_CONTACT
        defaultEmployeeDetailsShouldNotBeFound("secondaryContact.doesNotContain=" + DEFAULT_SECONDARY_CONTACT);

        // Get all the employeeDetailsList where secondaryContact does not contain UPDATED_SECONDARY_CONTACT
        defaultEmployeeDetailsShouldBeFound("secondaryContact.doesNotContain=" + UPDATED_SECONDARY_CONTACT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByHobbiesIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where hobbies equals to DEFAULT_HOBBIES
        defaultEmployeeDetailsShouldBeFound("hobbies.equals=" + DEFAULT_HOBBIES);

        // Get all the employeeDetailsList where hobbies equals to UPDATED_HOBBIES
        defaultEmployeeDetailsShouldNotBeFound("hobbies.equals=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByHobbiesIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where hobbies in DEFAULT_HOBBIES or UPDATED_HOBBIES
        defaultEmployeeDetailsShouldBeFound("hobbies.in=" + DEFAULT_HOBBIES + "," + UPDATED_HOBBIES);

        // Get all the employeeDetailsList where hobbies equals to UPDATED_HOBBIES
        defaultEmployeeDetailsShouldNotBeFound("hobbies.in=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByHobbiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where hobbies is not null
        defaultEmployeeDetailsShouldBeFound("hobbies.specified=true");

        // Get all the employeeDetailsList where hobbies is null
        defaultEmployeeDetailsShouldNotBeFound("hobbies.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByHobbiesContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where hobbies contains DEFAULT_HOBBIES
        defaultEmployeeDetailsShouldBeFound("hobbies.contains=" + DEFAULT_HOBBIES);

        // Get all the employeeDetailsList where hobbies contains UPDATED_HOBBIES
        defaultEmployeeDetailsShouldNotBeFound("hobbies.contains=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByHobbiesNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where hobbies does not contain DEFAULT_HOBBIES
        defaultEmployeeDetailsShouldNotBeFound("hobbies.doesNotContain=" + DEFAULT_HOBBIES);

        // Get all the employeeDetailsList where hobbies does not contain UPDATED_HOBBIES
        defaultEmployeeDetailsShouldBeFound("hobbies.doesNotContain=" + UPDATED_HOBBIES);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAreaInterestIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where areaInterest equals to DEFAULT_AREA_INTEREST
        defaultEmployeeDetailsShouldBeFound("areaInterest.equals=" + DEFAULT_AREA_INTEREST);

        // Get all the employeeDetailsList where areaInterest equals to UPDATED_AREA_INTEREST
        defaultEmployeeDetailsShouldNotBeFound("areaInterest.equals=" + UPDATED_AREA_INTEREST);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAreaInterestIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where areaInterest in DEFAULT_AREA_INTEREST or UPDATED_AREA_INTEREST
        defaultEmployeeDetailsShouldBeFound("areaInterest.in=" + DEFAULT_AREA_INTEREST + "," + UPDATED_AREA_INTEREST);

        // Get all the employeeDetailsList where areaInterest equals to UPDATED_AREA_INTEREST
        defaultEmployeeDetailsShouldNotBeFound("areaInterest.in=" + UPDATED_AREA_INTEREST);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAreaInterestIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where areaInterest is not null
        defaultEmployeeDetailsShouldBeFound("areaInterest.specified=true");

        // Get all the employeeDetailsList where areaInterest is null
        defaultEmployeeDetailsShouldNotBeFound("areaInterest.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAreaInterestContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where areaInterest contains DEFAULT_AREA_INTEREST
        defaultEmployeeDetailsShouldBeFound("areaInterest.contains=" + DEFAULT_AREA_INTEREST);

        // Get all the employeeDetailsList where areaInterest contains UPDATED_AREA_INTEREST
        defaultEmployeeDetailsShouldNotBeFound("areaInterest.contains=" + UPDATED_AREA_INTEREST);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByAreaInterestNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where areaInterest does not contain DEFAULT_AREA_INTEREST
        defaultEmployeeDetailsShouldNotBeFound("areaInterest.doesNotContain=" + DEFAULT_AREA_INTEREST);

        // Get all the employeeDetailsList where areaInterest does not contain UPDATED_AREA_INTEREST
        defaultEmployeeDetailsShouldBeFound("areaInterest.doesNotContain=" + UPDATED_AREA_INTEREST);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfDependentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfDependent equals to DEFAULT_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldBeFound("noOfDependent.equals=" + DEFAULT_NO_OF_DEPENDENT);

        // Get all the employeeDetailsList where noOfDependent equals to UPDATED_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldNotBeFound("noOfDependent.equals=" + UPDATED_NO_OF_DEPENDENT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfDependentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfDependent in DEFAULT_NO_OF_DEPENDENT or UPDATED_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldBeFound("noOfDependent.in=" + DEFAULT_NO_OF_DEPENDENT + "," + UPDATED_NO_OF_DEPENDENT);

        // Get all the employeeDetailsList where noOfDependent equals to UPDATED_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldNotBeFound("noOfDependent.in=" + UPDATED_NO_OF_DEPENDENT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfDependentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfDependent is not null
        defaultEmployeeDetailsShouldBeFound("noOfDependent.specified=true");

        // Get all the employeeDetailsList where noOfDependent is null
        defaultEmployeeDetailsShouldNotBeFound("noOfDependent.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfDependentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfDependent is greater than or equal to DEFAULT_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldBeFound("noOfDependent.greaterThanOrEqual=" + DEFAULT_NO_OF_DEPENDENT);

        // Get all the employeeDetailsList where noOfDependent is greater than or equal to UPDATED_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldNotBeFound("noOfDependent.greaterThanOrEqual=" + UPDATED_NO_OF_DEPENDENT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfDependentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfDependent is less than or equal to DEFAULT_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldBeFound("noOfDependent.lessThanOrEqual=" + DEFAULT_NO_OF_DEPENDENT);

        // Get all the employeeDetailsList where noOfDependent is less than or equal to SMALLER_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldNotBeFound("noOfDependent.lessThanOrEqual=" + SMALLER_NO_OF_DEPENDENT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfDependentIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfDependent is less than DEFAULT_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldNotBeFound("noOfDependent.lessThan=" + DEFAULT_NO_OF_DEPENDENT);

        // Get all the employeeDetailsList where noOfDependent is less than UPDATED_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldBeFound("noOfDependent.lessThan=" + UPDATED_NO_OF_DEPENDENT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNoOfDependentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where noOfDependent is greater than DEFAULT_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldNotBeFound("noOfDependent.greaterThan=" + DEFAULT_NO_OF_DEPENDENT);

        // Get all the employeeDetailsList where noOfDependent is greater than SMALLER_NO_OF_DEPENDENT
        defaultEmployeeDetailsShouldBeFound("noOfDependent.greaterThan=" + SMALLER_NO_OF_DEPENDENT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLanguageKnownIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where languageKnown equals to DEFAULT_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldBeFound("languageKnown.equals=" + DEFAULT_LANGUAGE_KNOWN);

        // Get all the employeeDetailsList where languageKnown equals to UPDATED_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldNotBeFound("languageKnown.equals=" + UPDATED_LANGUAGE_KNOWN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLanguageKnownIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where languageKnown in DEFAULT_LANGUAGE_KNOWN or UPDATED_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldBeFound("languageKnown.in=" + DEFAULT_LANGUAGE_KNOWN + "," + UPDATED_LANGUAGE_KNOWN);

        // Get all the employeeDetailsList where languageKnown equals to UPDATED_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldNotBeFound("languageKnown.in=" + UPDATED_LANGUAGE_KNOWN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLanguageKnownIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where languageKnown is not null
        defaultEmployeeDetailsShouldBeFound("languageKnown.specified=true");

        // Get all the employeeDetailsList where languageKnown is null
        defaultEmployeeDetailsShouldNotBeFound("languageKnown.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLanguageKnownContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where languageKnown contains DEFAULT_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldBeFound("languageKnown.contains=" + DEFAULT_LANGUAGE_KNOWN);

        // Get all the employeeDetailsList where languageKnown contains UPDATED_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldNotBeFound("languageKnown.contains=" + UPDATED_LANGUAGE_KNOWN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByLanguageKnownNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where languageKnown does not contain DEFAULT_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldNotBeFound("languageKnown.doesNotContain=" + DEFAULT_LANGUAGE_KNOWN);

        // Get all the employeeDetailsList where languageKnown does not contain UPDATED_LANGUAGE_KNOWN
        defaultEmployeeDetailsShouldBeFound("languageKnown.doesNotContain=" + UPDATED_LANGUAGE_KNOWN);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNatinalityIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where natinality equals to DEFAULT_NATINALITY
        defaultEmployeeDetailsShouldBeFound("natinality.equals=" + DEFAULT_NATINALITY);

        // Get all the employeeDetailsList where natinality equals to UPDATED_NATINALITY
        defaultEmployeeDetailsShouldNotBeFound("natinality.equals=" + UPDATED_NATINALITY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNatinalityIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where natinality in DEFAULT_NATINALITY or UPDATED_NATINALITY
        defaultEmployeeDetailsShouldBeFound("natinality.in=" + DEFAULT_NATINALITY + "," + UPDATED_NATINALITY);

        // Get all the employeeDetailsList where natinality equals to UPDATED_NATINALITY
        defaultEmployeeDetailsShouldNotBeFound("natinality.in=" + UPDATED_NATINALITY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNatinalityIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where natinality is not null
        defaultEmployeeDetailsShouldBeFound("natinality.specified=true");

        // Get all the employeeDetailsList where natinality is null
        defaultEmployeeDetailsShouldNotBeFound("natinality.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNatinalityContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where natinality contains DEFAULT_NATINALITY
        defaultEmployeeDetailsShouldBeFound("natinality.contains=" + DEFAULT_NATINALITY);

        // Get all the employeeDetailsList where natinality contains UPDATED_NATINALITY
        defaultEmployeeDetailsShouldNotBeFound("natinality.contains=" + UPDATED_NATINALITY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByNatinalityNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where natinality does not contain DEFAULT_NATINALITY
        defaultEmployeeDetailsShouldNotBeFound("natinality.doesNotContain=" + DEFAULT_NATINALITY);

        // Get all the employeeDetailsList where natinality does not contain UPDATED_NATINALITY
        defaultEmployeeDetailsShouldBeFound("natinality.doesNotContain=" + UPDATED_NATINALITY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where description equals to DEFAULT_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the employeeDetailsList where description equals to UPDATED_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the employeeDetailsList where description equals to UPDATED_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where description is not null
        defaultEmployeeDetailsShouldBeFound("description.specified=true");

        // Get all the employeeDetailsList where description is null
        defaultEmployeeDetailsShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where description contains DEFAULT_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the employeeDetailsList where description contains UPDATED_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where description does not contain DEFAULT_DESCRIPTION
        defaultEmployeeDetailsShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the employeeDetailsList where description does not contain UPDATED_DESCRIPTION
        defaultEmployeeDetailsShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where department equals to DEFAULT_DEPARTMENT
        defaultEmployeeDetailsShouldBeFound("department.equals=" + DEFAULT_DEPARTMENT);

        // Get all the employeeDetailsList where department equals to UPDATED_DEPARTMENT
        defaultEmployeeDetailsShouldNotBeFound("department.equals=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDepartmentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where department in DEFAULT_DEPARTMENT or UPDATED_DEPARTMENT
        defaultEmployeeDetailsShouldBeFound("department.in=" + DEFAULT_DEPARTMENT + "," + UPDATED_DEPARTMENT);

        // Get all the employeeDetailsList where department equals to UPDATED_DEPARTMENT
        defaultEmployeeDetailsShouldNotBeFound("department.in=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDepartmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where department is not null
        defaultEmployeeDetailsShouldBeFound("department.specified=true");

        // Get all the employeeDetailsList where department is null
        defaultEmployeeDetailsShouldNotBeFound("department.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDepartmentContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where department contains DEFAULT_DEPARTMENT
        defaultEmployeeDetailsShouldBeFound("department.contains=" + DEFAULT_DEPARTMENT);

        // Get all the employeeDetailsList where department contains UPDATED_DEPARTMENT
        defaultEmployeeDetailsShouldNotBeFound("department.contains=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByDepartmentNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where department does not contain DEFAULT_DEPARTMENT
        defaultEmployeeDetailsShouldNotBeFound("department.doesNotContain=" + DEFAULT_DEPARTMENT);

        // Get all the employeeDetailsList where department does not contain UPDATED_DEPARTMENT
        defaultEmployeeDetailsShouldBeFound("department.doesNotContain=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByJoiningDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where joiningDate equals to DEFAULT_JOINING_DATE
        defaultEmployeeDetailsShouldBeFound("joiningDate.equals=" + DEFAULT_JOINING_DATE);

        // Get all the employeeDetailsList where joiningDate equals to UPDATED_JOINING_DATE
        defaultEmployeeDetailsShouldNotBeFound("joiningDate.equals=" + UPDATED_JOINING_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByJoiningDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where joiningDate in DEFAULT_JOINING_DATE or UPDATED_JOINING_DATE
        defaultEmployeeDetailsShouldBeFound("joiningDate.in=" + DEFAULT_JOINING_DATE + "," + UPDATED_JOINING_DATE);

        // Get all the employeeDetailsList where joiningDate equals to UPDATED_JOINING_DATE
        defaultEmployeeDetailsShouldNotBeFound("joiningDate.in=" + UPDATED_JOINING_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByJoiningDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where joiningDate is not null
        defaultEmployeeDetailsShouldBeFound("joiningDate.specified=true");

        // Get all the employeeDetailsList where joiningDate is null
        defaultEmployeeDetailsShouldNotBeFound("joiningDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdBy equals to DEFAULT_CREATED_BY
        defaultEmployeeDetailsShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the employeeDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployeeDetailsShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEmployeeDetailsShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the employeeDetailsList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployeeDetailsShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdBy is not null
        defaultEmployeeDetailsShouldBeFound("createdBy.specified=true");

        // Get all the employeeDetailsList where createdBy is null
        defaultEmployeeDetailsShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdBy contains DEFAULT_CREATED_BY
        defaultEmployeeDetailsShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the employeeDetailsList where createdBy contains UPDATED_CREATED_BY
        defaultEmployeeDetailsShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEmployeeDetailsShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the employeeDetailsList where createdBy does not contain UPDATED_CREATED_BY
        defaultEmployeeDetailsShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdOn equals to DEFAULT_CREATED_ON
        defaultEmployeeDetailsShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the employeeDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultEmployeeDetailsShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultEmployeeDetailsShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the employeeDetailsList where createdOn equals to UPDATED_CREATED_ON
        defaultEmployeeDetailsShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where createdOn is not null
        defaultEmployeeDetailsShouldBeFound("createdOn.specified=true");

        // Get all the employeeDetailsList where createdOn is null
        defaultEmployeeDetailsShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield1IsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield1 equals to DEFAULT_FREEFIELD_1
        defaultEmployeeDetailsShouldBeFound("freefield1.equals=" + DEFAULT_FREEFIELD_1);

        // Get all the employeeDetailsList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultEmployeeDetailsShouldNotBeFound("freefield1.equals=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield1IsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield1 in DEFAULT_FREEFIELD_1 or UPDATED_FREEFIELD_1
        defaultEmployeeDetailsShouldBeFound("freefield1.in=" + DEFAULT_FREEFIELD_1 + "," + UPDATED_FREEFIELD_1);

        // Get all the employeeDetailsList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultEmployeeDetailsShouldNotBeFound("freefield1.in=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield1IsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield1 is not null
        defaultEmployeeDetailsShouldBeFound("freefield1.specified=true");

        // Get all the employeeDetailsList where freefield1 is null
        defaultEmployeeDetailsShouldNotBeFound("freefield1.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield1ContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield1 contains DEFAULT_FREEFIELD_1
        defaultEmployeeDetailsShouldBeFound("freefield1.contains=" + DEFAULT_FREEFIELD_1);

        // Get all the employeeDetailsList where freefield1 contains UPDATED_FREEFIELD_1
        defaultEmployeeDetailsShouldNotBeFound("freefield1.contains=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield1NotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield1 does not contain DEFAULT_FREEFIELD_1
        defaultEmployeeDetailsShouldNotBeFound("freefield1.doesNotContain=" + DEFAULT_FREEFIELD_1);

        // Get all the employeeDetailsList where freefield1 does not contain UPDATED_FREEFIELD_1
        defaultEmployeeDetailsShouldBeFound("freefield1.doesNotContain=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield2IsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield2 equals to DEFAULT_FREEFIELD_2
        defaultEmployeeDetailsShouldBeFound("freefield2.equals=" + DEFAULT_FREEFIELD_2);

        // Get all the employeeDetailsList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultEmployeeDetailsShouldNotBeFound("freefield2.equals=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield2IsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield2 in DEFAULT_FREEFIELD_2 or UPDATED_FREEFIELD_2
        defaultEmployeeDetailsShouldBeFound("freefield2.in=" + DEFAULT_FREEFIELD_2 + "," + UPDATED_FREEFIELD_2);

        // Get all the employeeDetailsList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultEmployeeDetailsShouldNotBeFound("freefield2.in=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield2IsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield2 is not null
        defaultEmployeeDetailsShouldBeFound("freefield2.specified=true");

        // Get all the employeeDetailsList where freefield2 is null
        defaultEmployeeDetailsShouldNotBeFound("freefield2.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield2ContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield2 contains DEFAULT_FREEFIELD_2
        defaultEmployeeDetailsShouldBeFound("freefield2.contains=" + DEFAULT_FREEFIELD_2);

        // Get all the employeeDetailsList where freefield2 contains UPDATED_FREEFIELD_2
        defaultEmployeeDetailsShouldNotBeFound("freefield2.contains=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield2NotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield2 does not contain DEFAULT_FREEFIELD_2
        defaultEmployeeDetailsShouldNotBeFound("freefield2.doesNotContain=" + DEFAULT_FREEFIELD_2);

        // Get all the employeeDetailsList where freefield2 does not contain UPDATED_FREEFIELD_2
        defaultEmployeeDetailsShouldBeFound("freefield2.doesNotContain=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield3IsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield3 equals to DEFAULT_FREEFIELD_3
        defaultEmployeeDetailsShouldBeFound("freefield3.equals=" + DEFAULT_FREEFIELD_3);

        // Get all the employeeDetailsList where freefield3 equals to UPDATED_FREEFIELD_3
        defaultEmployeeDetailsShouldNotBeFound("freefield3.equals=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield3IsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield3 in DEFAULT_FREEFIELD_3 or UPDATED_FREEFIELD_3
        defaultEmployeeDetailsShouldBeFound("freefield3.in=" + DEFAULT_FREEFIELD_3 + "," + UPDATED_FREEFIELD_3);

        // Get all the employeeDetailsList where freefield3 equals to UPDATED_FREEFIELD_3
        defaultEmployeeDetailsShouldNotBeFound("freefield3.in=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield3IsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield3 is not null
        defaultEmployeeDetailsShouldBeFound("freefield3.specified=true");

        // Get all the employeeDetailsList where freefield3 is null
        defaultEmployeeDetailsShouldNotBeFound("freefield3.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield3ContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield3 contains DEFAULT_FREEFIELD_3
        defaultEmployeeDetailsShouldBeFound("freefield3.contains=" + DEFAULT_FREEFIELD_3);

        // Get all the employeeDetailsList where freefield3 contains UPDATED_FREEFIELD_3
        defaultEmployeeDetailsShouldNotBeFound("freefield3.contains=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield3NotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield3 does not contain DEFAULT_FREEFIELD_3
        defaultEmployeeDetailsShouldNotBeFound("freefield3.doesNotContain=" + DEFAULT_FREEFIELD_3);

        // Get all the employeeDetailsList where freefield3 does not contain UPDATED_FREEFIELD_3
        defaultEmployeeDetailsShouldBeFound("freefield3.doesNotContain=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield4IsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield4 equals to DEFAULT_FREEFIELD_4
        defaultEmployeeDetailsShouldBeFound("freefield4.equals=" + DEFAULT_FREEFIELD_4);

        // Get all the employeeDetailsList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultEmployeeDetailsShouldNotBeFound("freefield4.equals=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield4IsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield4 in DEFAULT_FREEFIELD_4 or UPDATED_FREEFIELD_4
        defaultEmployeeDetailsShouldBeFound("freefield4.in=" + DEFAULT_FREEFIELD_4 + "," + UPDATED_FREEFIELD_4);

        // Get all the employeeDetailsList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultEmployeeDetailsShouldNotBeFound("freefield4.in=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield4IsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield4 is not null
        defaultEmployeeDetailsShouldBeFound("freefield4.specified=true");

        // Get all the employeeDetailsList where freefield4 is null
        defaultEmployeeDetailsShouldNotBeFound("freefield4.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield4ContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield4 contains DEFAULT_FREEFIELD_4
        defaultEmployeeDetailsShouldBeFound("freefield4.contains=" + DEFAULT_FREEFIELD_4);

        // Get all the employeeDetailsList where freefield4 contains UPDATED_FREEFIELD_4
        defaultEmployeeDetailsShouldNotBeFound("freefield4.contains=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield4NotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield4 does not contain DEFAULT_FREEFIELD_4
        defaultEmployeeDetailsShouldNotBeFound("freefield4.doesNotContain=" + DEFAULT_FREEFIELD_4);

        // Get all the employeeDetailsList where freefield4 does not contain UPDATED_FREEFIELD_4
        defaultEmployeeDetailsShouldBeFound("freefield4.doesNotContain=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield5IsEqualToSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield5 equals to DEFAULT_FREEFIELD_5
        defaultEmployeeDetailsShouldBeFound("freefield5.equals=" + DEFAULT_FREEFIELD_5);

        // Get all the employeeDetailsList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultEmployeeDetailsShouldNotBeFound("freefield5.equals=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield5IsInShouldWork() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield5 in DEFAULT_FREEFIELD_5 or UPDATED_FREEFIELD_5
        defaultEmployeeDetailsShouldBeFound("freefield5.in=" + DEFAULT_FREEFIELD_5 + "," + UPDATED_FREEFIELD_5);

        // Get all the employeeDetailsList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultEmployeeDetailsShouldNotBeFound("freefield5.in=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield5IsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield5 is not null
        defaultEmployeeDetailsShouldBeFound("freefield5.specified=true");

        // Get all the employeeDetailsList where freefield5 is null
        defaultEmployeeDetailsShouldNotBeFound("freefield5.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield5ContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield5 contains DEFAULT_FREEFIELD_5
        defaultEmployeeDetailsShouldBeFound("freefield5.contains=" + DEFAULT_FREEFIELD_5);

        // Get all the employeeDetailsList where freefield5 contains UPDATED_FREEFIELD_5
        defaultEmployeeDetailsShouldNotBeFound("freefield5.contains=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployeeDetailsByFreefield5NotContainsSomething() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        // Get all the employeeDetailsList where freefield5 does not contain DEFAULT_FREEFIELD_5
        defaultEmployeeDetailsShouldNotBeFound("freefield5.doesNotContain=" + DEFAULT_FREEFIELD_5);

        // Get all the employeeDetailsList where freefield5 does not contain UPDATED_FREEFIELD_5
        defaultEmployeeDetailsShouldBeFound("freefield5.doesNotContain=" + UPDATED_FREEFIELD_5);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeDetailsShouldBeFound(String filter) throws Exception {
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE.intValue())))
            .andExpect(jsonPath("$.[*].fatherName").value(hasItem(DEFAULT_FATHER_NAME)))
            .andExpect(jsonPath("$.[*].motherName").value(hasItem(DEFAULT_MOTHER_NAME)))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].yearsOfExperience").value(hasItem(DEFAULT_YEARS_OF_EXPERIENCE.doubleValue())))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].bloodGroup").value(hasItem(DEFAULT_BLOOD_GROUP.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE)))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION)))
            .andExpect(jsonPath("$.[*].expertise").value(hasItem(DEFAULT_EXPERTISE)))
            .andExpect(jsonPath("$.[*].jobDescription").value(hasItem(DEFAULT_JOB_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].secondaryContact").value(hasItem(DEFAULT_SECONDARY_CONTACT)))
            .andExpect(jsonPath("$.[*].hobbies").value(hasItem(DEFAULT_HOBBIES)))
            .andExpect(jsonPath("$.[*].areaInterest").value(hasItem(DEFAULT_AREA_INTEREST)))
            .andExpect(jsonPath("$.[*].noOfDependent").value(hasItem(DEFAULT_NO_OF_DEPENDENT.intValue())))
            .andExpect(jsonPath("$.[*].languageKnown").value(hasItem(DEFAULT_LANGUAGE_KNOWN)))
            .andExpect(jsonPath("$.[*].natinality").value(hasItem(DEFAULT_NATINALITY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].joiningDate").value(hasItem(DEFAULT_JOINING_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].freefield3").value(hasItem(DEFAULT_FREEFIELD_3)))
            .andExpect(jsonPath("$.[*].freefield4").value(hasItem(DEFAULT_FREEFIELD_4)))
            .andExpect(jsonPath("$.[*].freefield5").value(hasItem(DEFAULT_FREEFIELD_5)));

        // Check, that the count call also returns 1
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeDetailsShouldNotBeFound(String filter) throws Exception {
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeDetails() throws Exception {
        // Get the employeeDetails
        restEmployeeDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Update the employeeDetails
        EmployeeDetails updatedEmployeeDetails = employeeDetailsRepository.findById(employeeDetails.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeDetails are not directly saved in db
        em.detach(updatedEmployeeDetails);
        updatedEmployeeDetails
            .age(UPDATED_AGE)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE)
            .notes(UPDATED_NOTES)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .birthDate(UPDATED_BIRTH_DATE)
            .designation(UPDATED_DESIGNATION)
            .expertise(UPDATED_EXPERTISE)
            .jobDescription(UPDATED_JOB_DESCRIPTION)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .secondaryContact(UPDATED_SECONDARY_CONTACT)
            .hobbies(UPDATED_HOBBIES)
            .areaInterest(UPDATED_AREA_INTEREST)
            .noOfDependent(UPDATED_NO_OF_DEPENDENT)
            .languageKnown(UPDATED_LANGUAGE_KNOWN)
            .natinality(UPDATED_NATINALITY)
            .description(UPDATED_DESCRIPTION)
            .department(UPDATED_DEPARTMENT)
            .joiningDate(UPDATED_JOINING_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(updatedEmployeeDetails);

        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEmployeeDetails.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testEmployeeDetails.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testEmployeeDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeDetails.getYearsOfExperience()).isEqualTo(UPDATED_YEARS_OF_EXPERIENCE);
        assertThat(testEmployeeDetails.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testEmployeeDetails.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testEmployeeDetails.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployeeDetails.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployeeDetails.getExpertise()).isEqualTo(UPDATED_EXPERTISE);
        assertThat(testEmployeeDetails.getJobDescription()).isEqualTo(UPDATED_JOB_DESCRIPTION);
        assertThat(testEmployeeDetails.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployeeDetails.getSecondaryContact()).isEqualTo(UPDATED_SECONDARY_CONTACT);
        assertThat(testEmployeeDetails.getHobbies()).isEqualTo(UPDATED_HOBBIES);
        assertThat(testEmployeeDetails.getAreaInterest()).isEqualTo(UPDATED_AREA_INTEREST);
        assertThat(testEmployeeDetails.getNoOfDependent()).isEqualTo(UPDATED_NO_OF_DEPENDENT);
        assertThat(testEmployeeDetails.getLanguageKnown()).isEqualTo(UPDATED_LANGUAGE_KNOWN);
        assertThat(testEmployeeDetails.getNatinality()).isEqualTo(UPDATED_NATINALITY);
        assertThat(testEmployeeDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmployeeDetails.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testEmployeeDetails.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testEmployeeDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployeeDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmployeeDetails.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testEmployeeDetails.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testEmployeeDetails.getFreefield3()).isEqualTo(UPDATED_FREEFIELD_3);
        assertThat(testEmployeeDetails.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testEmployeeDetails.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDetailsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeDetailsWithPatch() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Update the employeeDetails using partial update
        EmployeeDetails partialUpdatedEmployeeDetails = new EmployeeDetails();
        partialUpdatedEmployeeDetails.setId(employeeDetails.getId());

        partialUpdatedEmployeeDetails
            .motherName(UPDATED_MOTHER_NAME)
            .yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE)
            .designation(UPDATED_DESIGNATION)
            .jobDescription(UPDATED_JOB_DESCRIPTION)
            .secondaryContact(UPDATED_SECONDARY_CONTACT)
            .hobbies(UPDATED_HOBBIES)
            .areaInterest(UPDATED_AREA_INTEREST)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4);

        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testEmployeeDetails.getFatherName()).isEqualTo(DEFAULT_FATHER_NAME);
        assertThat(testEmployeeDetails.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testEmployeeDetails.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testEmployeeDetails.getYearsOfExperience()).isEqualTo(UPDATED_YEARS_OF_EXPERIENCE);
        assertThat(testEmployeeDetails.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testEmployeeDetails.getBloodGroup()).isEqualTo(DEFAULT_BLOOD_GROUP);
        assertThat(testEmployeeDetails.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testEmployeeDetails.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployeeDetails.getExpertise()).isEqualTo(DEFAULT_EXPERTISE);
        assertThat(testEmployeeDetails.getJobDescription()).isEqualTo(UPDATED_JOB_DESCRIPTION);
        assertThat(testEmployeeDetails.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testEmployeeDetails.getSecondaryContact()).isEqualTo(UPDATED_SECONDARY_CONTACT);
        assertThat(testEmployeeDetails.getHobbies()).isEqualTo(UPDATED_HOBBIES);
        assertThat(testEmployeeDetails.getAreaInterest()).isEqualTo(UPDATED_AREA_INTEREST);
        assertThat(testEmployeeDetails.getNoOfDependent()).isEqualTo(DEFAULT_NO_OF_DEPENDENT);
        assertThat(testEmployeeDetails.getLanguageKnown()).isEqualTo(DEFAULT_LANGUAGE_KNOWN);
        assertThat(testEmployeeDetails.getNatinality()).isEqualTo(DEFAULT_NATINALITY);
        assertThat(testEmployeeDetails.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEmployeeDetails.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testEmployeeDetails.getJoiningDate()).isEqualTo(DEFAULT_JOINING_DATE);
        assertThat(testEmployeeDetails.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployeeDetails.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmployeeDetails.getFreefield1()).isEqualTo(DEFAULT_FREEFIELD_1);
        assertThat(testEmployeeDetails.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testEmployeeDetails.getFreefield3()).isEqualTo(UPDATED_FREEFIELD_3);
        assertThat(testEmployeeDetails.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testEmployeeDetails.getFreefield5()).isEqualTo(DEFAULT_FREEFIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeDetailsWithPatch() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();

        // Update the employeeDetails using partial update
        EmployeeDetails partialUpdatedEmployeeDetails = new EmployeeDetails();
        partialUpdatedEmployeeDetails.setId(employeeDetails.getId());

        partialUpdatedEmployeeDetails
            .age(UPDATED_AGE)
            .fatherName(UPDATED_FATHER_NAME)
            .motherName(UPDATED_MOTHER_NAME)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE)
            .notes(UPDATED_NOTES)
            .bloodGroup(UPDATED_BLOOD_GROUP)
            .birthDate(UPDATED_BIRTH_DATE)
            .designation(UPDATED_DESIGNATION)
            .expertise(UPDATED_EXPERTISE)
            .jobDescription(UPDATED_JOB_DESCRIPTION)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .secondaryContact(UPDATED_SECONDARY_CONTACT)
            .hobbies(UPDATED_HOBBIES)
            .areaInterest(UPDATED_AREA_INTEREST)
            .noOfDependent(UPDATED_NO_OF_DEPENDENT)
            .languageKnown(UPDATED_LANGUAGE_KNOWN)
            .natinality(UPDATED_NATINALITY)
            .description(UPDATED_DESCRIPTION)
            .department(UPDATED_DEPARTMENT)
            .joiningDate(UPDATED_JOINING_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);

        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeDetails))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
        EmployeeDetails testEmployeeDetails = employeeDetailsList.get(employeeDetailsList.size() - 1);
        assertThat(testEmployeeDetails.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testEmployeeDetails.getFatherName()).isEqualTo(UPDATED_FATHER_NAME);
        assertThat(testEmployeeDetails.getMotherName()).isEqualTo(UPDATED_MOTHER_NAME);
        assertThat(testEmployeeDetails.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testEmployeeDetails.getYearsOfExperience()).isEqualTo(UPDATED_YEARS_OF_EXPERIENCE);
        assertThat(testEmployeeDetails.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testEmployeeDetails.getBloodGroup()).isEqualTo(UPDATED_BLOOD_GROUP);
        assertThat(testEmployeeDetails.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testEmployeeDetails.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testEmployeeDetails.getExpertise()).isEqualTo(UPDATED_EXPERTISE);
        assertThat(testEmployeeDetails.getJobDescription()).isEqualTo(UPDATED_JOB_DESCRIPTION);
        assertThat(testEmployeeDetails.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testEmployeeDetails.getSecondaryContact()).isEqualTo(UPDATED_SECONDARY_CONTACT);
        assertThat(testEmployeeDetails.getHobbies()).isEqualTo(UPDATED_HOBBIES);
        assertThat(testEmployeeDetails.getAreaInterest()).isEqualTo(UPDATED_AREA_INTEREST);
        assertThat(testEmployeeDetails.getNoOfDependent()).isEqualTo(UPDATED_NO_OF_DEPENDENT);
        assertThat(testEmployeeDetails.getLanguageKnown()).isEqualTo(UPDATED_LANGUAGE_KNOWN);
        assertThat(testEmployeeDetails.getNatinality()).isEqualTo(UPDATED_NATINALITY);
        assertThat(testEmployeeDetails.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmployeeDetails.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testEmployeeDetails.getJoiningDate()).isEqualTo(UPDATED_JOINING_DATE);
        assertThat(testEmployeeDetails.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployeeDetails.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmployeeDetails.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testEmployeeDetails.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testEmployeeDetails.getFreefield3()).isEqualTo(UPDATED_FREEFIELD_3);
        assertThat(testEmployeeDetails.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testEmployeeDetails.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeDetailsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeDetails() throws Exception {
        int databaseSizeBeforeUpdate = employeeDetailsRepository.findAll().size();
        employeeDetails.setId(count.incrementAndGet());

        // Create the EmployeeDetails
        EmployeeDetailsDTO employeeDetailsDTO = employeeDetailsMapper.toDto(employeeDetails);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDetailsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeDetails in the database
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeDetails() throws Exception {
        // Initialize the database
        employeeDetailsRepository.saveAndFlush(employeeDetails);

        int databaseSizeBeforeDelete = employeeDetailsRepository.findAll().size();

        // Delete the employeeDetails
        restEmployeeDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeDetails> employeeDetailsList = employeeDetailsRepository.findAll();
        assertThat(employeeDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
