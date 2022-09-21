package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.EmployeeSeperation;
import com.techvg.hrms.domain.enumeration.SeperationStatus;
import com.techvg.hrms.repository.EmployeeSeperationRepository;
import com.techvg.hrms.service.criteria.EmployeeSeperationCriteria;
import com.techvg.hrms.service.dto.EmployeeSeperationDTO;
import com.techvg.hrms.service.mapper.EmployeeSeperationMapper;
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
 * Integration tests for the {@link EmployeeSeperationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EmployeeSeperationResourceIT {

    private static final String DEFAULT_REASON_FOR_SEPERATION = "AAAAAAAAAA";
    private static final String UPDATED_REASON_FOR_SEPERATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_SEPERATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SEPERATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final SeperationStatus DEFAULT_SEPERATION_STATUS = SeperationStatus.PENDING;
    private static final SeperationStatus UPDATED_SEPERATION_STATUS = SeperationStatus.REVOKED;

    private static final String DEFAULT_OTHER_REASON = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_REASON = "BBBBBBBBBB";

    private static final Long DEFAULT_NAGOTIATED_PERIOD = 1L;
    private static final Long UPDATED_NAGOTIATED_PERIOD = 2L;
    private static final Long SMALLER_NAGOTIATED_PERIOD = 1L - 1L;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPDATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/employee-seperations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeSeperationRepository employeeSeperationRepository;

    @Autowired
    private EmployeeSeperationMapper employeeSeperationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeSeperationMockMvc;

    private EmployeeSeperation employeeSeperation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSeperation createEntity(EntityManager em) {
        EmployeeSeperation employeeSeperation = new EmployeeSeperation()
            .reasonForSeperation(DEFAULT_REASON_FOR_SEPERATION)
            .seperationDate(DEFAULT_SEPERATION_DATE)
            .comment(DEFAULT_COMMENT)
            .seperationStatus(DEFAULT_SEPERATION_STATUS)
            .otherReason(DEFAULT_OTHER_REASON)
            .nagotiatedPeriod(DEFAULT_NAGOTIATED_PERIOD)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedOn(DEFAULT_UPDATED_ON)
            .createdOn(DEFAULT_CREATED_ON)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freefield1(DEFAULT_FREEFIELD_1)
            .freefield2(DEFAULT_FREEFIELD_2)
            .freefield3(DEFAULT_FREEFIELD_3)
            .freefield4(DEFAULT_FREEFIELD_4)
            .freefield5(DEFAULT_FREEFIELD_5);
        return employeeSeperation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSeperation createUpdatedEntity(EntityManager em) {
        EmployeeSeperation employeeSeperation = new EmployeeSeperation()
            .reasonForSeperation(UPDATED_REASON_FOR_SEPERATION)
            .seperationDate(UPDATED_SEPERATION_DATE)
            .comment(UPDATED_COMMENT)
            .seperationStatus(UPDATED_SEPERATION_STATUS)
            .otherReason(UPDATED_OTHER_REASON)
            .nagotiatedPeriod(UPDATED_NAGOTIATED_PERIOD)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .createdOn(UPDATED_CREATED_ON)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);
        return employeeSeperation;
    }

    @BeforeEach
    public void initTest() {
        employeeSeperation = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeSeperation() throws Exception {
        int databaseSizeBeforeCreate = employeeSeperationRepository.findAll().size();
        // Create the EmployeeSeperation
        EmployeeSeperationDTO employeeSeperationDTO = employeeSeperationMapper.toDto(employeeSeperation);
        restEmployeeSeperationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSeperationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeSeperation testEmployeeSeperation = employeeSeperationList.get(employeeSeperationList.size() - 1);
        assertThat(testEmployeeSeperation.getReasonForSeperation()).isEqualTo(DEFAULT_REASON_FOR_SEPERATION);
        assertThat(testEmployeeSeperation.getSeperationDate()).isEqualTo(DEFAULT_SEPERATION_DATE);
        assertThat(testEmployeeSeperation.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testEmployeeSeperation.getSeperationStatus()).isEqualTo(DEFAULT_SEPERATION_STATUS);
        assertThat(testEmployeeSeperation.getOtherReason()).isEqualTo(DEFAULT_OTHER_REASON);
        assertThat(testEmployeeSeperation.getNagotiatedPeriod()).isEqualTo(DEFAULT_NAGOTIATED_PERIOD);
        assertThat(testEmployeeSeperation.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployeeSeperation.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testEmployeeSeperation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmployeeSeperation.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testEmployeeSeperation.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testEmployeeSeperation.getFreefield1()).isEqualTo(DEFAULT_FREEFIELD_1);
        assertThat(testEmployeeSeperation.getFreefield2()).isEqualTo(DEFAULT_FREEFIELD_2);
        assertThat(testEmployeeSeperation.getFreefield3()).isEqualTo(DEFAULT_FREEFIELD_3);
        assertThat(testEmployeeSeperation.getFreefield4()).isEqualTo(DEFAULT_FREEFIELD_4);
        assertThat(testEmployeeSeperation.getFreefield5()).isEqualTo(DEFAULT_FREEFIELD_5);
    }

    @Test
    @Transactional
    void createEmployeeSeperationWithExistingId() throws Exception {
        // Create the EmployeeSeperation with an existing ID
        employeeSeperation.setId(1L);
        EmployeeSeperationDTO employeeSeperationDTO = employeeSeperationMapper.toDto(employeeSeperation);

        int databaseSizeBeforeCreate = employeeSeperationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeSeperationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSeperationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperations() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList
        restEmployeeSeperationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSeperation.getId().intValue())))
            .andExpect(jsonPath("$.[*].reasonForSeperation").value(hasItem(DEFAULT_REASON_FOR_SEPERATION)))
            .andExpect(jsonPath("$.[*].seperationDate").value(hasItem(DEFAULT_SEPERATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].seperationStatus").value(hasItem(DEFAULT_SEPERATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].otherReason").value(hasItem(DEFAULT_OTHER_REASON)))
            .andExpect(jsonPath("$.[*].nagotiatedPeriod").value(hasItem(DEFAULT_NAGOTIATED_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].freefield3").value(hasItem(DEFAULT_FREEFIELD_3)))
            .andExpect(jsonPath("$.[*].freefield4").value(hasItem(DEFAULT_FREEFIELD_4)))
            .andExpect(jsonPath("$.[*].freefield5").value(hasItem(DEFAULT_FREEFIELD_5)));
    }

    @Test
    @Transactional
    void getEmployeeSeperation() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get the employeeSeperation
        restEmployeeSeperationMockMvc
            .perform(get(ENTITY_API_URL_ID, employeeSeperation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employeeSeperation.getId().intValue()))
            .andExpect(jsonPath("$.reasonForSeperation").value(DEFAULT_REASON_FOR_SEPERATION))
            .andExpect(jsonPath("$.seperationDate").value(DEFAULT_SEPERATION_DATE.toString()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT))
            .andExpect(jsonPath("$.seperationStatus").value(DEFAULT_SEPERATION_STATUS.toString()))
            .andExpect(jsonPath("$.otherReason").value(DEFAULT_OTHER_REASON))
            .andExpect(jsonPath("$.nagotiatedPeriod").value(DEFAULT_NAGOTIATED_PERIOD.intValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.updatedOn").value(DEFAULT_UPDATED_ON.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freefield1").value(DEFAULT_FREEFIELD_1))
            .andExpect(jsonPath("$.freefield2").value(DEFAULT_FREEFIELD_2))
            .andExpect(jsonPath("$.freefield3").value(DEFAULT_FREEFIELD_3))
            .andExpect(jsonPath("$.freefield4").value(DEFAULT_FREEFIELD_4))
            .andExpect(jsonPath("$.freefield5").value(DEFAULT_FREEFIELD_5));
    }

    @Test
    @Transactional
    void getEmployeeSeperationsByIdFiltering() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        Long id = employeeSeperation.getId();

        defaultEmployeeSeperationShouldBeFound("id.equals=" + id);
        defaultEmployeeSeperationShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeSeperationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeSeperationShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeSeperationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeSeperationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByReasonForSeperationIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where reasonForSeperation equals to DEFAULT_REASON_FOR_SEPERATION
        defaultEmployeeSeperationShouldBeFound("reasonForSeperation.equals=" + DEFAULT_REASON_FOR_SEPERATION);

        // Get all the employeeSeperationList where reasonForSeperation equals to UPDATED_REASON_FOR_SEPERATION
        defaultEmployeeSeperationShouldNotBeFound("reasonForSeperation.equals=" + UPDATED_REASON_FOR_SEPERATION);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByReasonForSeperationIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where reasonForSeperation in DEFAULT_REASON_FOR_SEPERATION or UPDATED_REASON_FOR_SEPERATION
        defaultEmployeeSeperationShouldBeFound(
            "reasonForSeperation.in=" + DEFAULT_REASON_FOR_SEPERATION + "," + UPDATED_REASON_FOR_SEPERATION
        );

        // Get all the employeeSeperationList where reasonForSeperation equals to UPDATED_REASON_FOR_SEPERATION
        defaultEmployeeSeperationShouldNotBeFound("reasonForSeperation.in=" + UPDATED_REASON_FOR_SEPERATION);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByReasonForSeperationIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where reasonForSeperation is not null
        defaultEmployeeSeperationShouldBeFound("reasonForSeperation.specified=true");

        // Get all the employeeSeperationList where reasonForSeperation is null
        defaultEmployeeSeperationShouldNotBeFound("reasonForSeperation.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByReasonForSeperationContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where reasonForSeperation contains DEFAULT_REASON_FOR_SEPERATION
        defaultEmployeeSeperationShouldBeFound("reasonForSeperation.contains=" + DEFAULT_REASON_FOR_SEPERATION);

        // Get all the employeeSeperationList where reasonForSeperation contains UPDATED_REASON_FOR_SEPERATION
        defaultEmployeeSeperationShouldNotBeFound("reasonForSeperation.contains=" + UPDATED_REASON_FOR_SEPERATION);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByReasonForSeperationNotContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where reasonForSeperation does not contain DEFAULT_REASON_FOR_SEPERATION
        defaultEmployeeSeperationShouldNotBeFound("reasonForSeperation.doesNotContain=" + DEFAULT_REASON_FOR_SEPERATION);

        // Get all the employeeSeperationList where reasonForSeperation does not contain UPDATED_REASON_FOR_SEPERATION
        defaultEmployeeSeperationShouldBeFound("reasonForSeperation.doesNotContain=" + UPDATED_REASON_FOR_SEPERATION);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsBySeperationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where seperationDate equals to DEFAULT_SEPERATION_DATE
        defaultEmployeeSeperationShouldBeFound("seperationDate.equals=" + DEFAULT_SEPERATION_DATE);

        // Get all the employeeSeperationList where seperationDate equals to UPDATED_SEPERATION_DATE
        defaultEmployeeSeperationShouldNotBeFound("seperationDate.equals=" + UPDATED_SEPERATION_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsBySeperationDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where seperationDate in DEFAULT_SEPERATION_DATE or UPDATED_SEPERATION_DATE
        defaultEmployeeSeperationShouldBeFound("seperationDate.in=" + DEFAULT_SEPERATION_DATE + "," + UPDATED_SEPERATION_DATE);

        // Get all the employeeSeperationList where seperationDate equals to UPDATED_SEPERATION_DATE
        defaultEmployeeSeperationShouldNotBeFound("seperationDate.in=" + UPDATED_SEPERATION_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsBySeperationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where seperationDate is not null
        defaultEmployeeSeperationShouldBeFound("seperationDate.specified=true");

        // Get all the employeeSeperationList where seperationDate is null
        defaultEmployeeSeperationShouldNotBeFound("seperationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCommentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where comment equals to DEFAULT_COMMENT
        defaultEmployeeSeperationShouldBeFound("comment.equals=" + DEFAULT_COMMENT);

        // Get all the employeeSeperationList where comment equals to UPDATED_COMMENT
        defaultEmployeeSeperationShouldNotBeFound("comment.equals=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCommentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where comment in DEFAULT_COMMENT or UPDATED_COMMENT
        defaultEmployeeSeperationShouldBeFound("comment.in=" + DEFAULT_COMMENT + "," + UPDATED_COMMENT);

        // Get all the employeeSeperationList where comment equals to UPDATED_COMMENT
        defaultEmployeeSeperationShouldNotBeFound("comment.in=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCommentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where comment is not null
        defaultEmployeeSeperationShouldBeFound("comment.specified=true");

        // Get all the employeeSeperationList where comment is null
        defaultEmployeeSeperationShouldNotBeFound("comment.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCommentContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where comment contains DEFAULT_COMMENT
        defaultEmployeeSeperationShouldBeFound("comment.contains=" + DEFAULT_COMMENT);

        // Get all the employeeSeperationList where comment contains UPDATED_COMMENT
        defaultEmployeeSeperationShouldNotBeFound("comment.contains=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCommentNotContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where comment does not contain DEFAULT_COMMENT
        defaultEmployeeSeperationShouldNotBeFound("comment.doesNotContain=" + DEFAULT_COMMENT);

        // Get all the employeeSeperationList where comment does not contain UPDATED_COMMENT
        defaultEmployeeSeperationShouldBeFound("comment.doesNotContain=" + UPDATED_COMMENT);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsBySeperationStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where seperationStatus equals to DEFAULT_SEPERATION_STATUS
        defaultEmployeeSeperationShouldBeFound("seperationStatus.equals=" + DEFAULT_SEPERATION_STATUS);

        // Get all the employeeSeperationList where seperationStatus equals to UPDATED_SEPERATION_STATUS
        defaultEmployeeSeperationShouldNotBeFound("seperationStatus.equals=" + UPDATED_SEPERATION_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsBySeperationStatusIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where seperationStatus in DEFAULT_SEPERATION_STATUS or UPDATED_SEPERATION_STATUS
        defaultEmployeeSeperationShouldBeFound("seperationStatus.in=" + DEFAULT_SEPERATION_STATUS + "," + UPDATED_SEPERATION_STATUS);

        // Get all the employeeSeperationList where seperationStatus equals to UPDATED_SEPERATION_STATUS
        defaultEmployeeSeperationShouldNotBeFound("seperationStatus.in=" + UPDATED_SEPERATION_STATUS);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsBySeperationStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where seperationStatus is not null
        defaultEmployeeSeperationShouldBeFound("seperationStatus.specified=true");

        // Get all the employeeSeperationList where seperationStatus is null
        defaultEmployeeSeperationShouldNotBeFound("seperationStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByOtherReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where otherReason equals to DEFAULT_OTHER_REASON
        defaultEmployeeSeperationShouldBeFound("otherReason.equals=" + DEFAULT_OTHER_REASON);

        // Get all the employeeSeperationList where otherReason equals to UPDATED_OTHER_REASON
        defaultEmployeeSeperationShouldNotBeFound("otherReason.equals=" + UPDATED_OTHER_REASON);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByOtherReasonIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where otherReason in DEFAULT_OTHER_REASON or UPDATED_OTHER_REASON
        defaultEmployeeSeperationShouldBeFound("otherReason.in=" + DEFAULT_OTHER_REASON + "," + UPDATED_OTHER_REASON);

        // Get all the employeeSeperationList where otherReason equals to UPDATED_OTHER_REASON
        defaultEmployeeSeperationShouldNotBeFound("otherReason.in=" + UPDATED_OTHER_REASON);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByOtherReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where otherReason is not null
        defaultEmployeeSeperationShouldBeFound("otherReason.specified=true");

        // Get all the employeeSeperationList where otherReason is null
        defaultEmployeeSeperationShouldNotBeFound("otherReason.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByOtherReasonContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where otherReason contains DEFAULT_OTHER_REASON
        defaultEmployeeSeperationShouldBeFound("otherReason.contains=" + DEFAULT_OTHER_REASON);

        // Get all the employeeSeperationList where otherReason contains UPDATED_OTHER_REASON
        defaultEmployeeSeperationShouldNotBeFound("otherReason.contains=" + UPDATED_OTHER_REASON);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByOtherReasonNotContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where otherReason does not contain DEFAULT_OTHER_REASON
        defaultEmployeeSeperationShouldNotBeFound("otherReason.doesNotContain=" + DEFAULT_OTHER_REASON);

        // Get all the employeeSeperationList where otherReason does not contain UPDATED_OTHER_REASON
        defaultEmployeeSeperationShouldBeFound("otherReason.doesNotContain=" + UPDATED_OTHER_REASON);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByNagotiatedPeriodIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where nagotiatedPeriod equals to DEFAULT_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldBeFound("nagotiatedPeriod.equals=" + DEFAULT_NAGOTIATED_PERIOD);

        // Get all the employeeSeperationList where nagotiatedPeriod equals to UPDATED_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldNotBeFound("nagotiatedPeriod.equals=" + UPDATED_NAGOTIATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByNagotiatedPeriodIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where nagotiatedPeriod in DEFAULT_NAGOTIATED_PERIOD or UPDATED_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldBeFound("nagotiatedPeriod.in=" + DEFAULT_NAGOTIATED_PERIOD + "," + UPDATED_NAGOTIATED_PERIOD);

        // Get all the employeeSeperationList where nagotiatedPeriod equals to UPDATED_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldNotBeFound("nagotiatedPeriod.in=" + UPDATED_NAGOTIATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByNagotiatedPeriodIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where nagotiatedPeriod is not null
        defaultEmployeeSeperationShouldBeFound("nagotiatedPeriod.specified=true");

        // Get all the employeeSeperationList where nagotiatedPeriod is null
        defaultEmployeeSeperationShouldNotBeFound("nagotiatedPeriod.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByNagotiatedPeriodIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where nagotiatedPeriod is greater than or equal to DEFAULT_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldBeFound("nagotiatedPeriod.greaterThanOrEqual=" + DEFAULT_NAGOTIATED_PERIOD);

        // Get all the employeeSeperationList where nagotiatedPeriod is greater than or equal to UPDATED_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldNotBeFound("nagotiatedPeriod.greaterThanOrEqual=" + UPDATED_NAGOTIATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByNagotiatedPeriodIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where nagotiatedPeriod is less than or equal to DEFAULT_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldBeFound("nagotiatedPeriod.lessThanOrEqual=" + DEFAULT_NAGOTIATED_PERIOD);

        // Get all the employeeSeperationList where nagotiatedPeriod is less than or equal to SMALLER_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldNotBeFound("nagotiatedPeriod.lessThanOrEqual=" + SMALLER_NAGOTIATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByNagotiatedPeriodIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where nagotiatedPeriod is less than DEFAULT_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldNotBeFound("nagotiatedPeriod.lessThan=" + DEFAULT_NAGOTIATED_PERIOD);

        // Get all the employeeSeperationList where nagotiatedPeriod is less than UPDATED_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldBeFound("nagotiatedPeriod.lessThan=" + UPDATED_NAGOTIATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByNagotiatedPeriodIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where nagotiatedPeriod is greater than DEFAULT_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldNotBeFound("nagotiatedPeriod.greaterThan=" + DEFAULT_NAGOTIATED_PERIOD);

        // Get all the employeeSeperationList where nagotiatedPeriod is greater than SMALLER_NAGOTIATED_PERIOD
        defaultEmployeeSeperationShouldBeFound("nagotiatedPeriod.greaterThan=" + SMALLER_NAGOTIATED_PERIOD);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where createdBy equals to DEFAULT_CREATED_BY
        defaultEmployeeSeperationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the employeeSeperationList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployeeSeperationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEmployeeSeperationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the employeeSeperationList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployeeSeperationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where createdBy is not null
        defaultEmployeeSeperationShouldBeFound("createdBy.specified=true");

        // Get all the employeeSeperationList where createdBy is null
        defaultEmployeeSeperationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where createdBy contains DEFAULT_CREATED_BY
        defaultEmployeeSeperationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the employeeSeperationList where createdBy contains UPDATED_CREATED_BY
        defaultEmployeeSeperationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEmployeeSeperationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the employeeSeperationList where createdBy does not contain UPDATED_CREATED_BY
        defaultEmployeeSeperationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByUpdatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where updatedOn equals to DEFAULT_UPDATED_ON
        defaultEmployeeSeperationShouldBeFound("updatedOn.equals=" + DEFAULT_UPDATED_ON);

        // Get all the employeeSeperationList where updatedOn equals to UPDATED_UPDATED_ON
        defaultEmployeeSeperationShouldNotBeFound("updatedOn.equals=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByUpdatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where updatedOn in DEFAULT_UPDATED_ON or UPDATED_UPDATED_ON
        defaultEmployeeSeperationShouldBeFound("updatedOn.in=" + DEFAULT_UPDATED_ON + "," + UPDATED_UPDATED_ON);

        // Get all the employeeSeperationList where updatedOn equals to UPDATED_UPDATED_ON
        defaultEmployeeSeperationShouldNotBeFound("updatedOn.in=" + UPDATED_UPDATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByUpdatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where updatedOn is not null
        defaultEmployeeSeperationShouldBeFound("updatedOn.specified=true");

        // Get all the employeeSeperationList where updatedOn is null
        defaultEmployeeSeperationShouldNotBeFound("updatedOn.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where createdOn equals to DEFAULT_CREATED_ON
        defaultEmployeeSeperationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the employeeSeperationList where createdOn equals to UPDATED_CREATED_ON
        defaultEmployeeSeperationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultEmployeeSeperationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the employeeSeperationList where createdOn equals to UPDATED_CREATED_ON
        defaultEmployeeSeperationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where createdOn is not null
        defaultEmployeeSeperationShouldBeFound("createdOn.specified=true");

        // Get all the employeeSeperationList where createdOn is null
        defaultEmployeeSeperationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultEmployeeSeperationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the employeeSeperationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmployeeSeperationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultEmployeeSeperationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the employeeSeperationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultEmployeeSeperationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where lastModified is not null
        defaultEmployeeSeperationShouldBeFound("lastModified.specified=true");

        // Get all the employeeSeperationList where lastModified is null
        defaultEmployeeSeperationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeSeperationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeSeperationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployeeSeperationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultEmployeeSeperationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the employeeSeperationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultEmployeeSeperationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where lastModifiedBy is not null
        defaultEmployeeSeperationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the employeeSeperationList where lastModifiedBy is null
        defaultEmployeeSeperationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeSeperationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeSeperationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultEmployeeSeperationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultEmployeeSeperationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the employeeSeperationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultEmployeeSeperationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield1IsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield1 equals to DEFAULT_FREEFIELD_1
        defaultEmployeeSeperationShouldBeFound("freefield1.equals=" + DEFAULT_FREEFIELD_1);

        // Get all the employeeSeperationList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultEmployeeSeperationShouldNotBeFound("freefield1.equals=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield1IsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield1 in DEFAULT_FREEFIELD_1 or UPDATED_FREEFIELD_1
        defaultEmployeeSeperationShouldBeFound("freefield1.in=" + DEFAULT_FREEFIELD_1 + "," + UPDATED_FREEFIELD_1);

        // Get all the employeeSeperationList where freefield1 equals to UPDATED_FREEFIELD_1
        defaultEmployeeSeperationShouldNotBeFound("freefield1.in=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield1IsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield1 is not null
        defaultEmployeeSeperationShouldBeFound("freefield1.specified=true");

        // Get all the employeeSeperationList where freefield1 is null
        defaultEmployeeSeperationShouldNotBeFound("freefield1.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield1ContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield1 contains DEFAULT_FREEFIELD_1
        defaultEmployeeSeperationShouldBeFound("freefield1.contains=" + DEFAULT_FREEFIELD_1);

        // Get all the employeeSeperationList where freefield1 contains UPDATED_FREEFIELD_1
        defaultEmployeeSeperationShouldNotBeFound("freefield1.contains=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield1NotContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield1 does not contain DEFAULT_FREEFIELD_1
        defaultEmployeeSeperationShouldNotBeFound("freefield1.doesNotContain=" + DEFAULT_FREEFIELD_1);

        // Get all the employeeSeperationList where freefield1 does not contain UPDATED_FREEFIELD_1
        defaultEmployeeSeperationShouldBeFound("freefield1.doesNotContain=" + UPDATED_FREEFIELD_1);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield2IsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield2 equals to DEFAULT_FREEFIELD_2
        defaultEmployeeSeperationShouldBeFound("freefield2.equals=" + DEFAULT_FREEFIELD_2);

        // Get all the employeeSeperationList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultEmployeeSeperationShouldNotBeFound("freefield2.equals=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield2IsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield2 in DEFAULT_FREEFIELD_2 or UPDATED_FREEFIELD_2
        defaultEmployeeSeperationShouldBeFound("freefield2.in=" + DEFAULT_FREEFIELD_2 + "," + UPDATED_FREEFIELD_2);

        // Get all the employeeSeperationList where freefield2 equals to UPDATED_FREEFIELD_2
        defaultEmployeeSeperationShouldNotBeFound("freefield2.in=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield2IsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield2 is not null
        defaultEmployeeSeperationShouldBeFound("freefield2.specified=true");

        // Get all the employeeSeperationList where freefield2 is null
        defaultEmployeeSeperationShouldNotBeFound("freefield2.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield2ContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield2 contains DEFAULT_FREEFIELD_2
        defaultEmployeeSeperationShouldBeFound("freefield2.contains=" + DEFAULT_FREEFIELD_2);

        // Get all the employeeSeperationList where freefield2 contains UPDATED_FREEFIELD_2
        defaultEmployeeSeperationShouldNotBeFound("freefield2.contains=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield2NotContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield2 does not contain DEFAULT_FREEFIELD_2
        defaultEmployeeSeperationShouldNotBeFound("freefield2.doesNotContain=" + DEFAULT_FREEFIELD_2);

        // Get all the employeeSeperationList where freefield2 does not contain UPDATED_FREEFIELD_2
        defaultEmployeeSeperationShouldBeFound("freefield2.doesNotContain=" + UPDATED_FREEFIELD_2);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield3IsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield3 equals to DEFAULT_FREEFIELD_3
        defaultEmployeeSeperationShouldBeFound("freefield3.equals=" + DEFAULT_FREEFIELD_3);

        // Get all the employeeSeperationList where freefield3 equals to UPDATED_FREEFIELD_3
        defaultEmployeeSeperationShouldNotBeFound("freefield3.equals=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield3IsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield3 in DEFAULT_FREEFIELD_3 or UPDATED_FREEFIELD_3
        defaultEmployeeSeperationShouldBeFound("freefield3.in=" + DEFAULT_FREEFIELD_3 + "," + UPDATED_FREEFIELD_3);

        // Get all the employeeSeperationList where freefield3 equals to UPDATED_FREEFIELD_3
        defaultEmployeeSeperationShouldNotBeFound("freefield3.in=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield3IsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield3 is not null
        defaultEmployeeSeperationShouldBeFound("freefield3.specified=true");

        // Get all the employeeSeperationList where freefield3 is null
        defaultEmployeeSeperationShouldNotBeFound("freefield3.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield3ContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield3 contains DEFAULT_FREEFIELD_3
        defaultEmployeeSeperationShouldBeFound("freefield3.contains=" + DEFAULT_FREEFIELD_3);

        // Get all the employeeSeperationList where freefield3 contains UPDATED_FREEFIELD_3
        defaultEmployeeSeperationShouldNotBeFound("freefield3.contains=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield3NotContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield3 does not contain DEFAULT_FREEFIELD_3
        defaultEmployeeSeperationShouldNotBeFound("freefield3.doesNotContain=" + DEFAULT_FREEFIELD_3);

        // Get all the employeeSeperationList where freefield3 does not contain UPDATED_FREEFIELD_3
        defaultEmployeeSeperationShouldBeFound("freefield3.doesNotContain=" + UPDATED_FREEFIELD_3);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield4IsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield4 equals to DEFAULT_FREEFIELD_4
        defaultEmployeeSeperationShouldBeFound("freefield4.equals=" + DEFAULT_FREEFIELD_4);

        // Get all the employeeSeperationList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultEmployeeSeperationShouldNotBeFound("freefield4.equals=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield4IsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield4 in DEFAULT_FREEFIELD_4 or UPDATED_FREEFIELD_4
        defaultEmployeeSeperationShouldBeFound("freefield4.in=" + DEFAULT_FREEFIELD_4 + "," + UPDATED_FREEFIELD_4);

        // Get all the employeeSeperationList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultEmployeeSeperationShouldNotBeFound("freefield4.in=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield4IsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield4 is not null
        defaultEmployeeSeperationShouldBeFound("freefield4.specified=true");

        // Get all the employeeSeperationList where freefield4 is null
        defaultEmployeeSeperationShouldNotBeFound("freefield4.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield4ContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield4 contains DEFAULT_FREEFIELD_4
        defaultEmployeeSeperationShouldBeFound("freefield4.contains=" + DEFAULT_FREEFIELD_4);

        // Get all the employeeSeperationList where freefield4 contains UPDATED_FREEFIELD_4
        defaultEmployeeSeperationShouldNotBeFound("freefield4.contains=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield4NotContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield4 does not contain DEFAULT_FREEFIELD_4
        defaultEmployeeSeperationShouldNotBeFound("freefield4.doesNotContain=" + DEFAULT_FREEFIELD_4);

        // Get all the employeeSeperationList where freefield4 does not contain UPDATED_FREEFIELD_4
        defaultEmployeeSeperationShouldBeFound("freefield4.doesNotContain=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield5IsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield5 equals to DEFAULT_FREEFIELD_5
        defaultEmployeeSeperationShouldBeFound("freefield5.equals=" + DEFAULT_FREEFIELD_5);

        // Get all the employeeSeperationList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultEmployeeSeperationShouldNotBeFound("freefield5.equals=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield5IsInShouldWork() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield5 in DEFAULT_FREEFIELD_5 or UPDATED_FREEFIELD_5
        defaultEmployeeSeperationShouldBeFound("freefield5.in=" + DEFAULT_FREEFIELD_5 + "," + UPDATED_FREEFIELD_5);

        // Get all the employeeSeperationList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultEmployeeSeperationShouldNotBeFound("freefield5.in=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield5IsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield5 is not null
        defaultEmployeeSeperationShouldBeFound("freefield5.specified=true");

        // Get all the employeeSeperationList where freefield5 is null
        defaultEmployeeSeperationShouldNotBeFound("freefield5.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield5ContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield5 contains DEFAULT_FREEFIELD_5
        defaultEmployeeSeperationShouldBeFound("freefield5.contains=" + DEFAULT_FREEFIELD_5);

        // Get all the employeeSeperationList where freefield5 contains UPDATED_FREEFIELD_5
        defaultEmployeeSeperationShouldNotBeFound("freefield5.contains=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByFreefield5NotContainsSomething() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        // Get all the employeeSeperationList where freefield5 does not contain DEFAULT_FREEFIELD_5
        defaultEmployeeSeperationShouldNotBeFound("freefield5.doesNotContain=" + DEFAULT_FREEFIELD_5);

        // Get all the employeeSeperationList where freefield5 does not contain UPDATED_FREEFIELD_5
        defaultEmployeeSeperationShouldBeFound("freefield5.doesNotContain=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllEmployeeSeperationsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            employeeSeperationRepository.saveAndFlush(employeeSeperation);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        employeeSeperation.setEmployee(employee);
        employeeSeperationRepository.saveAndFlush(employeeSeperation);
        Long employeeId = employee.getId();

        // Get all the employeeSeperationList where employee equals to employeeId
        defaultEmployeeSeperationShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the employeeSeperationList where employee equals to (employeeId + 1)
        defaultEmployeeSeperationShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeSeperationShouldBeFound(String filter) throws Exception {
        restEmployeeSeperationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSeperation.getId().intValue())))
            .andExpect(jsonPath("$.[*].reasonForSeperation").value(hasItem(DEFAULT_REASON_FOR_SEPERATION)))
            .andExpect(jsonPath("$.[*].seperationDate").value(hasItem(DEFAULT_SEPERATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT)))
            .andExpect(jsonPath("$.[*].seperationStatus").value(hasItem(DEFAULT_SEPERATION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].otherReason").value(hasItem(DEFAULT_OTHER_REASON)))
            .andExpect(jsonPath("$.[*].nagotiatedPeriod").value(hasItem(DEFAULT_NAGOTIATED_PERIOD.intValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].updatedOn").value(hasItem(DEFAULT_UPDATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freefield1").value(hasItem(DEFAULT_FREEFIELD_1)))
            .andExpect(jsonPath("$.[*].freefield2").value(hasItem(DEFAULT_FREEFIELD_2)))
            .andExpect(jsonPath("$.[*].freefield3").value(hasItem(DEFAULT_FREEFIELD_3)))
            .andExpect(jsonPath("$.[*].freefield4").value(hasItem(DEFAULT_FREEFIELD_4)))
            .andExpect(jsonPath("$.[*].freefield5").value(hasItem(DEFAULT_FREEFIELD_5)));

        // Check, that the count call also returns 1
        restEmployeeSeperationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeSeperationShouldNotBeFound(String filter) throws Exception {
        restEmployeeSeperationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeSeperationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeSeperation() throws Exception {
        // Get the employeeSeperation
        restEmployeeSeperationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployeeSeperation() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        int databaseSizeBeforeUpdate = employeeSeperationRepository.findAll().size();

        // Update the employeeSeperation
        EmployeeSeperation updatedEmployeeSeperation = employeeSeperationRepository.findById(employeeSeperation.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeSeperation are not directly saved in db
        em.detach(updatedEmployeeSeperation);
        updatedEmployeeSeperation
            .reasonForSeperation(UPDATED_REASON_FOR_SEPERATION)
            .seperationDate(UPDATED_SEPERATION_DATE)
            .comment(UPDATED_COMMENT)
            .seperationStatus(UPDATED_SEPERATION_STATUS)
            .otherReason(UPDATED_OTHER_REASON)
            .nagotiatedPeriod(UPDATED_NAGOTIATED_PERIOD)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .createdOn(UPDATED_CREATED_ON)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);
        EmployeeSeperationDTO employeeSeperationDTO = employeeSeperationMapper.toDto(updatedEmployeeSeperation);

        restEmployeeSeperationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeSeperationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSeperationDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSeperation testEmployeeSeperation = employeeSeperationList.get(employeeSeperationList.size() - 1);
        assertThat(testEmployeeSeperation.getReasonForSeperation()).isEqualTo(UPDATED_REASON_FOR_SEPERATION);
        assertThat(testEmployeeSeperation.getSeperationDate()).isEqualTo(UPDATED_SEPERATION_DATE);
        assertThat(testEmployeeSeperation.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testEmployeeSeperation.getSeperationStatus()).isEqualTo(UPDATED_SEPERATION_STATUS);
        assertThat(testEmployeeSeperation.getOtherReason()).isEqualTo(UPDATED_OTHER_REASON);
        assertThat(testEmployeeSeperation.getNagotiatedPeriod()).isEqualTo(UPDATED_NAGOTIATED_PERIOD);
        assertThat(testEmployeeSeperation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployeeSeperation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testEmployeeSeperation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmployeeSeperation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployeeSeperation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEmployeeSeperation.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testEmployeeSeperation.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testEmployeeSeperation.getFreefield3()).isEqualTo(UPDATED_FREEFIELD_3);
        assertThat(testEmployeeSeperation.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testEmployeeSeperation.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingEmployeeSeperation() throws Exception {
        int databaseSizeBeforeUpdate = employeeSeperationRepository.findAll().size();
        employeeSeperation.setId(count.incrementAndGet());

        // Create the EmployeeSeperation
        EmployeeSeperationDTO employeeSeperationDTO = employeeSeperationMapper.toDto(employeeSeperation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeSeperationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeSeperationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSeperationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployeeSeperation() throws Exception {
        int databaseSizeBeforeUpdate = employeeSeperationRepository.findAll().size();
        employeeSeperation.setId(count.incrementAndGet());

        // Create the EmployeeSeperation
        EmployeeSeperationDTO employeeSeperationDTO = employeeSeperationMapper.toDto(employeeSeperation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeSeperationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSeperationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployeeSeperation() throws Exception {
        int databaseSizeBeforeUpdate = employeeSeperationRepository.findAll().size();
        employeeSeperation.setId(count.incrementAndGet());

        // Create the EmployeeSeperation
        EmployeeSeperationDTO employeeSeperationDTO = employeeSeperationMapper.toDto(employeeSeperation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeSeperationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSeperationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeSeperationWithPatch() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        int databaseSizeBeforeUpdate = employeeSeperationRepository.findAll().size();

        // Update the employeeSeperation using partial update
        EmployeeSeperation partialUpdatedEmployeeSeperation = new EmployeeSeperation();
        partialUpdatedEmployeeSeperation.setId(employeeSeperation.getId());

        partialUpdatedEmployeeSeperation
            .seperationDate(UPDATED_SEPERATION_DATE)
            .comment(UPDATED_COMMENT)
            .seperationStatus(UPDATED_SEPERATION_STATUS)
            .otherReason(UPDATED_OTHER_REASON)
            .createdBy(UPDATED_CREATED_BY)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);

        restEmployeeSeperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeSeperation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeSeperation))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSeperation testEmployeeSeperation = employeeSeperationList.get(employeeSeperationList.size() - 1);
        assertThat(testEmployeeSeperation.getReasonForSeperation()).isEqualTo(DEFAULT_REASON_FOR_SEPERATION);
        assertThat(testEmployeeSeperation.getSeperationDate()).isEqualTo(UPDATED_SEPERATION_DATE);
        assertThat(testEmployeeSeperation.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testEmployeeSeperation.getSeperationStatus()).isEqualTo(UPDATED_SEPERATION_STATUS);
        assertThat(testEmployeeSeperation.getOtherReason()).isEqualTo(UPDATED_OTHER_REASON);
        assertThat(testEmployeeSeperation.getNagotiatedPeriod()).isEqualTo(DEFAULT_NAGOTIATED_PERIOD);
        assertThat(testEmployeeSeperation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployeeSeperation.getUpdatedOn()).isEqualTo(DEFAULT_UPDATED_ON);
        assertThat(testEmployeeSeperation.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testEmployeeSeperation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployeeSeperation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEmployeeSeperation.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testEmployeeSeperation.getFreefield2()).isEqualTo(DEFAULT_FREEFIELD_2);
        assertThat(testEmployeeSeperation.getFreefield3()).isEqualTo(DEFAULT_FREEFIELD_3);
        assertThat(testEmployeeSeperation.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testEmployeeSeperation.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeSeperationWithPatch() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        int databaseSizeBeforeUpdate = employeeSeperationRepository.findAll().size();

        // Update the employeeSeperation using partial update
        EmployeeSeperation partialUpdatedEmployeeSeperation = new EmployeeSeperation();
        partialUpdatedEmployeeSeperation.setId(employeeSeperation.getId());

        partialUpdatedEmployeeSeperation
            .reasonForSeperation(UPDATED_REASON_FOR_SEPERATION)
            .seperationDate(UPDATED_SEPERATION_DATE)
            .comment(UPDATED_COMMENT)
            .seperationStatus(UPDATED_SEPERATION_STATUS)
            .otherReason(UPDATED_OTHER_REASON)
            .nagotiatedPeriod(UPDATED_NAGOTIATED_PERIOD)
            .createdBy(UPDATED_CREATED_BY)
            .updatedOn(UPDATED_UPDATED_ON)
            .createdOn(UPDATED_CREATED_ON)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freefield1(UPDATED_FREEFIELD_1)
            .freefield2(UPDATED_FREEFIELD_2)
            .freefield3(UPDATED_FREEFIELD_3)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);

        restEmployeeSeperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployeeSeperation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeSeperation))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSeperation testEmployeeSeperation = employeeSeperationList.get(employeeSeperationList.size() - 1);
        assertThat(testEmployeeSeperation.getReasonForSeperation()).isEqualTo(UPDATED_REASON_FOR_SEPERATION);
        assertThat(testEmployeeSeperation.getSeperationDate()).isEqualTo(UPDATED_SEPERATION_DATE);
        assertThat(testEmployeeSeperation.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testEmployeeSeperation.getSeperationStatus()).isEqualTo(UPDATED_SEPERATION_STATUS);
        assertThat(testEmployeeSeperation.getOtherReason()).isEqualTo(UPDATED_OTHER_REASON);
        assertThat(testEmployeeSeperation.getNagotiatedPeriod()).isEqualTo(UPDATED_NAGOTIATED_PERIOD);
        assertThat(testEmployeeSeperation.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployeeSeperation.getUpdatedOn()).isEqualTo(UPDATED_UPDATED_ON);
        assertThat(testEmployeeSeperation.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testEmployeeSeperation.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testEmployeeSeperation.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testEmployeeSeperation.getFreefield1()).isEqualTo(UPDATED_FREEFIELD_1);
        assertThat(testEmployeeSeperation.getFreefield2()).isEqualTo(UPDATED_FREEFIELD_2);
        assertThat(testEmployeeSeperation.getFreefield3()).isEqualTo(UPDATED_FREEFIELD_3);
        assertThat(testEmployeeSeperation.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testEmployeeSeperation.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingEmployeeSeperation() throws Exception {
        int databaseSizeBeforeUpdate = employeeSeperationRepository.findAll().size();
        employeeSeperation.setId(count.incrementAndGet());

        // Create the EmployeeSeperation
        EmployeeSeperationDTO employeeSeperationDTO = employeeSeperationMapper.toDto(employeeSeperation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeSeperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeSeperationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeSeperationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployeeSeperation() throws Exception {
        int databaseSizeBeforeUpdate = employeeSeperationRepository.findAll().size();
        employeeSeperation.setId(count.incrementAndGet());

        // Create the EmployeeSeperation
        EmployeeSeperationDTO employeeSeperationDTO = employeeSeperationMapper.toDto(employeeSeperation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeSeperationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeSeperationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployeeSeperation() throws Exception {
        int databaseSizeBeforeUpdate = employeeSeperationRepository.findAll().size();
        employeeSeperation.setId(count.incrementAndGet());

        // Create the EmployeeSeperation
        EmployeeSeperationDTO employeeSeperationDTO = employeeSeperationMapper.toDto(employeeSeperation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeSeperationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeSeperationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EmployeeSeperation in the database
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployeeSeperation() throws Exception {
        // Initialize the database
        employeeSeperationRepository.saveAndFlush(employeeSeperation);

        int databaseSizeBeforeDelete = employeeSeperationRepository.findAll().size();

        // Delete the employeeSeperation
        restEmployeeSeperationMockMvc
            .perform(delete(ENTITY_API_URL_ID, employeeSeperation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeSeperation> employeeSeperationList = employeeSeperationRepository.findAll();
        assertThat(employeeSeperationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
