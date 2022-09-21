package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.ExitFormalties;
import com.techvg.hrms.domain.enumeration.Options;
import com.techvg.hrms.domain.enumeration.Options;
import com.techvg.hrms.domain.enumeration.Options;
import com.techvg.hrms.domain.enumeration.Options;
import com.techvg.hrms.domain.enumeration.Options;
import com.techvg.hrms.domain.enumeration.Options;
import com.techvg.hrms.domain.enumeration.Options;
import com.techvg.hrms.domain.enumeration.Options;
import com.techvg.hrms.domain.enumeration.Options;
import com.techvg.hrms.domain.enumeration.Status;
import com.techvg.hrms.repository.ExitFormaltiesRepository;
import com.techvg.hrms.service.criteria.ExitFormaltiesCriteria;
import com.techvg.hrms.service.dto.ExitFormaltiesDTO;
import com.techvg.hrms.service.mapper.ExitFormaltiesMapper;
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
 * Integration tests for the {@link ExitFormaltiesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ExitFormaltiesResourceIT {

    private static final Options DEFAULT_SECURITY = Options.YES;
    private static final Options UPDATED_SECURITY = Options.NO;

    private static final String DEFAULT_FEEDBACK = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXIT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXIT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Options DEFAULT_EXIT_INTERVIEW = Options.YES;
    private static final Options UPDATED_EXIT_INTERVIEW = Options.NO;

    private static final Options DEFAULT_LIB_NO_DUE = Options.YES;
    private static final Options UPDATED_LIB_NO_DUE = Options.NO;

    private static final Options DEFAULT_NOTICE_PERIOD_SERVED = Options.YES;
    private static final Options UPDATED_NOTICE_PERIOD_SERVED = Options.NO;

    private static final Options DEFAULT_CLEARENCE = Options.YES;
    private static final Options UPDATED_CLEARENCE = Options.NO;

    private static final Options DEFAULT_ORG_ASSETS = Options.YES;
    private static final Options UPDATED_ORG_ASSETS = Options.NO;

    private static final Options DEFAULT_ORG_VEHICAL = Options.YES;
    private static final Options UPDATED_ORG_VEHICAL = Options.NO;

    private static final Options DEFAULT_RESIGN_LETTER = Options.YES;
    private static final Options UPDATED_RESIGN_LETTER = Options.NO;

    private static final String DEFAULT_SHARES = "AAAAAAAAAA";
    private static final String UPDATED_SHARES = "BBBBBBBBBB";

    private static final String DEFAULT_STAFF_WELFARE = "AAAAAAAAAA";
    private static final String UPDATED_STAFF_WELFARE = "BBBBBBBBBB";

    private static final Options DEFAULT_WORK_FOR_ORG = Options.YES;
    private static final Options UPDATED_WORK_FOR_ORG = Options.NO;

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

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

    private static final String ENTITY_API_URL = "/api/exit-formalties";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ExitFormaltiesRepository exitFormaltiesRepository;

    @Autowired
    private ExitFormaltiesMapper exitFormaltiesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restExitFormaltiesMockMvc;

    private ExitFormalties exitFormalties;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExitFormalties createEntity(EntityManager em) {
        ExitFormalties exitFormalties = new ExitFormalties()
            .security(DEFAULT_SECURITY)
            .feedback(DEFAULT_FEEDBACK)
            .exitDate(DEFAULT_EXIT_DATE)
            .exitInterview(DEFAULT_EXIT_INTERVIEW)
            .libNoDue(DEFAULT_LIB_NO_DUE)
            .noticePeriodServed(DEFAULT_NOTICE_PERIOD_SERVED)
            .clearence(DEFAULT_CLEARENCE)
            .orgAssets(DEFAULT_ORG_ASSETS)
            .orgVehical(DEFAULT_ORG_VEHICAL)
            .resignLetter(DEFAULT_RESIGN_LETTER)
            .shares(DEFAULT_SHARES)
            .staffWelfare(DEFAULT_STAFF_WELFARE)
            .workForOrg(DEFAULT_WORK_FOR_ORG)
            .status(DEFAULT_STATUS)
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
        return exitFormalties;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExitFormalties createUpdatedEntity(EntityManager em) {
        ExitFormalties exitFormalties = new ExitFormalties()
            .security(UPDATED_SECURITY)
            .feedback(UPDATED_FEEDBACK)
            .exitDate(UPDATED_EXIT_DATE)
            .exitInterview(UPDATED_EXIT_INTERVIEW)
            .libNoDue(UPDATED_LIB_NO_DUE)
            .noticePeriodServed(UPDATED_NOTICE_PERIOD_SERVED)
            .clearence(UPDATED_CLEARENCE)
            .orgAssets(UPDATED_ORG_ASSETS)
            .orgVehical(UPDATED_ORG_VEHICAL)
            .resignLetter(UPDATED_RESIGN_LETTER)
            .shares(UPDATED_SHARES)
            .staffWelfare(UPDATED_STAFF_WELFARE)
            .workForOrg(UPDATED_WORK_FOR_ORG)
            .status(UPDATED_STATUS)
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
        return exitFormalties;
    }

    @BeforeEach
    public void initTest() {
        exitFormalties = createEntity(em);
    }

    @Test
    @Transactional
    void createExitFormalties() throws Exception {
        int databaseSizeBeforeCreate = exitFormaltiesRepository.findAll().size();
        // Create the ExitFormalties
        ExitFormaltiesDTO exitFormaltiesDTO = exitFormaltiesMapper.toDto(exitFormalties);
        restExitFormaltiesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exitFormaltiesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeCreate + 1);
        ExitFormalties testExitFormalties = exitFormaltiesList.get(exitFormaltiesList.size() - 1);
        assertThat(testExitFormalties.getSecurity()).isEqualTo(DEFAULT_SECURITY);
        assertThat(testExitFormalties.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
        assertThat(testExitFormalties.getExitDate()).isEqualTo(DEFAULT_EXIT_DATE);
        assertThat(testExitFormalties.getExitInterview()).isEqualTo(DEFAULT_EXIT_INTERVIEW);
        assertThat(testExitFormalties.getLibNoDue()).isEqualTo(DEFAULT_LIB_NO_DUE);
        assertThat(testExitFormalties.getNoticePeriodServed()).isEqualTo(DEFAULT_NOTICE_PERIOD_SERVED);
        assertThat(testExitFormalties.getClearence()).isEqualTo(DEFAULT_CLEARENCE);
        assertThat(testExitFormalties.getOrgAssets()).isEqualTo(DEFAULT_ORG_ASSETS);
        assertThat(testExitFormalties.getOrgVehical()).isEqualTo(DEFAULT_ORG_VEHICAL);
        assertThat(testExitFormalties.getResignLetter()).isEqualTo(DEFAULT_RESIGN_LETTER);
        assertThat(testExitFormalties.getShares()).isEqualTo(DEFAULT_SHARES);
        assertThat(testExitFormalties.getStaffWelfare()).isEqualTo(DEFAULT_STAFF_WELFARE);
        assertThat(testExitFormalties.getWorkForOrg()).isEqualTo(DEFAULT_WORK_FOR_ORG);
        assertThat(testExitFormalties.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testExitFormalties.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testExitFormalties.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testExitFormalties.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExitFormalties.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testExitFormalties.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testExitFormalties.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testExitFormalties.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testExitFormalties.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testExitFormalties.getFreefield4()).isEqualTo(DEFAULT_FREEFIELD_4);
        assertThat(testExitFormalties.getFreefield5()).isEqualTo(DEFAULT_FREEFIELD_5);
    }

    @Test
    @Transactional
    void createExitFormaltiesWithExistingId() throws Exception {
        // Create the ExitFormalties with an existing ID
        exitFormalties.setId(1L);
        ExitFormaltiesDTO exitFormaltiesDTO = exitFormaltiesMapper.toDto(exitFormalties);

        int databaseSizeBeforeCreate = exitFormaltiesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restExitFormaltiesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exitFormaltiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllExitFormalties() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList
        restExitFormaltiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exitFormalties.getId().intValue())))
            .andExpect(jsonPath("$.[*].security").value(hasItem(DEFAULT_SECURITY.toString())))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK)))
            .andExpect(jsonPath("$.[*].exitDate").value(hasItem(DEFAULT_EXIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].exitInterview").value(hasItem(DEFAULT_EXIT_INTERVIEW.toString())))
            .andExpect(jsonPath("$.[*].libNoDue").value(hasItem(DEFAULT_LIB_NO_DUE.toString())))
            .andExpect(jsonPath("$.[*].noticePeriodServed").value(hasItem(DEFAULT_NOTICE_PERIOD_SERVED.toString())))
            .andExpect(jsonPath("$.[*].clearence").value(hasItem(DEFAULT_CLEARENCE.toString())))
            .andExpect(jsonPath("$.[*].orgAssets").value(hasItem(DEFAULT_ORG_ASSETS.toString())))
            .andExpect(jsonPath("$.[*].orgVehical").value(hasItem(DEFAULT_ORG_VEHICAL.toString())))
            .andExpect(jsonPath("$.[*].resignLetter").value(hasItem(DEFAULT_RESIGN_LETTER.toString())))
            .andExpect(jsonPath("$.[*].shares").value(hasItem(DEFAULT_SHARES)))
            .andExpect(jsonPath("$.[*].staffWelfare").value(hasItem(DEFAULT_STAFF_WELFARE)))
            .andExpect(jsonPath("$.[*].workForOrg").value(hasItem(DEFAULT_WORK_FOR_ORG.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
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
    void getExitFormalties() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get the exitFormalties
        restExitFormaltiesMockMvc
            .perform(get(ENTITY_API_URL_ID, exitFormalties.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(exitFormalties.getId().intValue()))
            .andExpect(jsonPath("$.security").value(DEFAULT_SECURITY.toString()))
            .andExpect(jsonPath("$.feedback").value(DEFAULT_FEEDBACK))
            .andExpect(jsonPath("$.exitDate").value(DEFAULT_EXIT_DATE.toString()))
            .andExpect(jsonPath("$.exitInterview").value(DEFAULT_EXIT_INTERVIEW.toString()))
            .andExpect(jsonPath("$.libNoDue").value(DEFAULT_LIB_NO_DUE.toString()))
            .andExpect(jsonPath("$.noticePeriodServed").value(DEFAULT_NOTICE_PERIOD_SERVED.toString()))
            .andExpect(jsonPath("$.clearence").value(DEFAULT_CLEARENCE.toString()))
            .andExpect(jsonPath("$.orgAssets").value(DEFAULT_ORG_ASSETS.toString()))
            .andExpect(jsonPath("$.orgVehical").value(DEFAULT_ORG_VEHICAL.toString()))
            .andExpect(jsonPath("$.resignLetter").value(DEFAULT_RESIGN_LETTER.toString()))
            .andExpect(jsonPath("$.shares").value(DEFAULT_SHARES))
            .andExpect(jsonPath("$.staffWelfare").value(DEFAULT_STAFF_WELFARE))
            .andExpect(jsonPath("$.workForOrg").value(DEFAULT_WORK_FOR_ORG.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
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
    void getExitFormaltiesByIdFiltering() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        Long id = exitFormalties.getId();

        defaultExitFormaltiesShouldBeFound("id.equals=" + id);
        defaultExitFormaltiesShouldNotBeFound("id.notEquals=" + id);

        defaultExitFormaltiesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultExitFormaltiesShouldNotBeFound("id.greaterThan=" + id);

        defaultExitFormaltiesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultExitFormaltiesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesBySecurityIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where security equals to DEFAULT_SECURITY
        defaultExitFormaltiesShouldBeFound("security.equals=" + DEFAULT_SECURITY);

        // Get all the exitFormaltiesList where security equals to UPDATED_SECURITY
        defaultExitFormaltiesShouldNotBeFound("security.equals=" + UPDATED_SECURITY);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesBySecurityIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where security in DEFAULT_SECURITY or UPDATED_SECURITY
        defaultExitFormaltiesShouldBeFound("security.in=" + DEFAULT_SECURITY + "," + UPDATED_SECURITY);

        // Get all the exitFormaltiesList where security equals to UPDATED_SECURITY
        defaultExitFormaltiesShouldNotBeFound("security.in=" + UPDATED_SECURITY);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesBySecurityIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where security is not null
        defaultExitFormaltiesShouldBeFound("security.specified=true");

        // Get all the exitFormaltiesList where security is null
        defaultExitFormaltiesShouldNotBeFound("security.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFeedbackIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where feedback equals to DEFAULT_FEEDBACK
        defaultExitFormaltiesShouldBeFound("feedback.equals=" + DEFAULT_FEEDBACK);

        // Get all the exitFormaltiesList where feedback equals to UPDATED_FEEDBACK
        defaultExitFormaltiesShouldNotBeFound("feedback.equals=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFeedbackIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where feedback in DEFAULT_FEEDBACK or UPDATED_FEEDBACK
        defaultExitFormaltiesShouldBeFound("feedback.in=" + DEFAULT_FEEDBACK + "," + UPDATED_FEEDBACK);

        // Get all the exitFormaltiesList where feedback equals to UPDATED_FEEDBACK
        defaultExitFormaltiesShouldNotBeFound("feedback.in=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFeedbackIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where feedback is not null
        defaultExitFormaltiesShouldBeFound("feedback.specified=true");

        // Get all the exitFormaltiesList where feedback is null
        defaultExitFormaltiesShouldNotBeFound("feedback.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFeedbackContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where feedback contains DEFAULT_FEEDBACK
        defaultExitFormaltiesShouldBeFound("feedback.contains=" + DEFAULT_FEEDBACK);

        // Get all the exitFormaltiesList where feedback contains UPDATED_FEEDBACK
        defaultExitFormaltiesShouldNotBeFound("feedback.contains=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFeedbackNotContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where feedback does not contain DEFAULT_FEEDBACK
        defaultExitFormaltiesShouldNotBeFound("feedback.doesNotContain=" + DEFAULT_FEEDBACK);

        // Get all the exitFormaltiesList where feedback does not contain UPDATED_FEEDBACK
        defaultExitFormaltiesShouldBeFound("feedback.doesNotContain=" + UPDATED_FEEDBACK);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByExitDateIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where exitDate equals to DEFAULT_EXIT_DATE
        defaultExitFormaltiesShouldBeFound("exitDate.equals=" + DEFAULT_EXIT_DATE);

        // Get all the exitFormaltiesList where exitDate equals to UPDATED_EXIT_DATE
        defaultExitFormaltiesShouldNotBeFound("exitDate.equals=" + UPDATED_EXIT_DATE);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByExitDateIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where exitDate in DEFAULT_EXIT_DATE or UPDATED_EXIT_DATE
        defaultExitFormaltiesShouldBeFound("exitDate.in=" + DEFAULT_EXIT_DATE + "," + UPDATED_EXIT_DATE);

        // Get all the exitFormaltiesList where exitDate equals to UPDATED_EXIT_DATE
        defaultExitFormaltiesShouldNotBeFound("exitDate.in=" + UPDATED_EXIT_DATE);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByExitDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where exitDate is not null
        defaultExitFormaltiesShouldBeFound("exitDate.specified=true");

        // Get all the exitFormaltiesList where exitDate is null
        defaultExitFormaltiesShouldNotBeFound("exitDate.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByExitInterviewIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where exitInterview equals to DEFAULT_EXIT_INTERVIEW
        defaultExitFormaltiesShouldBeFound("exitInterview.equals=" + DEFAULT_EXIT_INTERVIEW);

        // Get all the exitFormaltiesList where exitInterview equals to UPDATED_EXIT_INTERVIEW
        defaultExitFormaltiesShouldNotBeFound("exitInterview.equals=" + UPDATED_EXIT_INTERVIEW);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByExitInterviewIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where exitInterview in DEFAULT_EXIT_INTERVIEW or UPDATED_EXIT_INTERVIEW
        defaultExitFormaltiesShouldBeFound("exitInterview.in=" + DEFAULT_EXIT_INTERVIEW + "," + UPDATED_EXIT_INTERVIEW);

        // Get all the exitFormaltiesList where exitInterview equals to UPDATED_EXIT_INTERVIEW
        defaultExitFormaltiesShouldNotBeFound("exitInterview.in=" + UPDATED_EXIT_INTERVIEW);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByExitInterviewIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where exitInterview is not null
        defaultExitFormaltiesShouldBeFound("exitInterview.specified=true");

        // Get all the exitFormaltiesList where exitInterview is null
        defaultExitFormaltiesShouldNotBeFound("exitInterview.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLibNoDueIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where libNoDue equals to DEFAULT_LIB_NO_DUE
        defaultExitFormaltiesShouldBeFound("libNoDue.equals=" + DEFAULT_LIB_NO_DUE);

        // Get all the exitFormaltiesList where libNoDue equals to UPDATED_LIB_NO_DUE
        defaultExitFormaltiesShouldNotBeFound("libNoDue.equals=" + UPDATED_LIB_NO_DUE);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLibNoDueIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where libNoDue in DEFAULT_LIB_NO_DUE or UPDATED_LIB_NO_DUE
        defaultExitFormaltiesShouldBeFound("libNoDue.in=" + DEFAULT_LIB_NO_DUE + "," + UPDATED_LIB_NO_DUE);

        // Get all the exitFormaltiesList where libNoDue equals to UPDATED_LIB_NO_DUE
        defaultExitFormaltiesShouldNotBeFound("libNoDue.in=" + UPDATED_LIB_NO_DUE);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLibNoDueIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where libNoDue is not null
        defaultExitFormaltiesShouldBeFound("libNoDue.specified=true");

        // Get all the exitFormaltiesList where libNoDue is null
        defaultExitFormaltiesShouldNotBeFound("libNoDue.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByNoticePeriodServedIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where noticePeriodServed equals to DEFAULT_NOTICE_PERIOD_SERVED
        defaultExitFormaltiesShouldBeFound("noticePeriodServed.equals=" + DEFAULT_NOTICE_PERIOD_SERVED);

        // Get all the exitFormaltiesList where noticePeriodServed equals to UPDATED_NOTICE_PERIOD_SERVED
        defaultExitFormaltiesShouldNotBeFound("noticePeriodServed.equals=" + UPDATED_NOTICE_PERIOD_SERVED);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByNoticePeriodServedIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where noticePeriodServed in DEFAULT_NOTICE_PERIOD_SERVED or UPDATED_NOTICE_PERIOD_SERVED
        defaultExitFormaltiesShouldBeFound("noticePeriodServed.in=" + DEFAULT_NOTICE_PERIOD_SERVED + "," + UPDATED_NOTICE_PERIOD_SERVED);

        // Get all the exitFormaltiesList where noticePeriodServed equals to UPDATED_NOTICE_PERIOD_SERVED
        defaultExitFormaltiesShouldNotBeFound("noticePeriodServed.in=" + UPDATED_NOTICE_PERIOD_SERVED);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByNoticePeriodServedIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where noticePeriodServed is not null
        defaultExitFormaltiesShouldBeFound("noticePeriodServed.specified=true");

        // Get all the exitFormaltiesList where noticePeriodServed is null
        defaultExitFormaltiesShouldNotBeFound("noticePeriodServed.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByClearenceIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where clearence equals to DEFAULT_CLEARENCE
        defaultExitFormaltiesShouldBeFound("clearence.equals=" + DEFAULT_CLEARENCE);

        // Get all the exitFormaltiesList where clearence equals to UPDATED_CLEARENCE
        defaultExitFormaltiesShouldNotBeFound("clearence.equals=" + UPDATED_CLEARENCE);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByClearenceIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where clearence in DEFAULT_CLEARENCE or UPDATED_CLEARENCE
        defaultExitFormaltiesShouldBeFound("clearence.in=" + DEFAULT_CLEARENCE + "," + UPDATED_CLEARENCE);

        // Get all the exitFormaltiesList where clearence equals to UPDATED_CLEARENCE
        defaultExitFormaltiesShouldNotBeFound("clearence.in=" + UPDATED_CLEARENCE);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByClearenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where clearence is not null
        defaultExitFormaltiesShouldBeFound("clearence.specified=true");

        // Get all the exitFormaltiesList where clearence is null
        defaultExitFormaltiesShouldNotBeFound("clearence.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByOrgAssetsIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where orgAssets equals to DEFAULT_ORG_ASSETS
        defaultExitFormaltiesShouldBeFound("orgAssets.equals=" + DEFAULT_ORG_ASSETS);

        // Get all the exitFormaltiesList where orgAssets equals to UPDATED_ORG_ASSETS
        defaultExitFormaltiesShouldNotBeFound("orgAssets.equals=" + UPDATED_ORG_ASSETS);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByOrgAssetsIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where orgAssets in DEFAULT_ORG_ASSETS or UPDATED_ORG_ASSETS
        defaultExitFormaltiesShouldBeFound("orgAssets.in=" + DEFAULT_ORG_ASSETS + "," + UPDATED_ORG_ASSETS);

        // Get all the exitFormaltiesList where orgAssets equals to UPDATED_ORG_ASSETS
        defaultExitFormaltiesShouldNotBeFound("orgAssets.in=" + UPDATED_ORG_ASSETS);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByOrgAssetsIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where orgAssets is not null
        defaultExitFormaltiesShouldBeFound("orgAssets.specified=true");

        // Get all the exitFormaltiesList where orgAssets is null
        defaultExitFormaltiesShouldNotBeFound("orgAssets.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByOrgVehicalIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where orgVehical equals to DEFAULT_ORG_VEHICAL
        defaultExitFormaltiesShouldBeFound("orgVehical.equals=" + DEFAULT_ORG_VEHICAL);

        // Get all the exitFormaltiesList where orgVehical equals to UPDATED_ORG_VEHICAL
        defaultExitFormaltiesShouldNotBeFound("orgVehical.equals=" + UPDATED_ORG_VEHICAL);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByOrgVehicalIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where orgVehical in DEFAULT_ORG_VEHICAL or UPDATED_ORG_VEHICAL
        defaultExitFormaltiesShouldBeFound("orgVehical.in=" + DEFAULT_ORG_VEHICAL + "," + UPDATED_ORG_VEHICAL);

        // Get all the exitFormaltiesList where orgVehical equals to UPDATED_ORG_VEHICAL
        defaultExitFormaltiesShouldNotBeFound("orgVehical.in=" + UPDATED_ORG_VEHICAL);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByOrgVehicalIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where orgVehical is not null
        defaultExitFormaltiesShouldBeFound("orgVehical.specified=true");

        // Get all the exitFormaltiesList where orgVehical is null
        defaultExitFormaltiesShouldNotBeFound("orgVehical.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByResignLetterIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where resignLetter equals to DEFAULT_RESIGN_LETTER
        defaultExitFormaltiesShouldBeFound("resignLetter.equals=" + DEFAULT_RESIGN_LETTER);

        // Get all the exitFormaltiesList where resignLetter equals to UPDATED_RESIGN_LETTER
        defaultExitFormaltiesShouldNotBeFound("resignLetter.equals=" + UPDATED_RESIGN_LETTER);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByResignLetterIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where resignLetter in DEFAULT_RESIGN_LETTER or UPDATED_RESIGN_LETTER
        defaultExitFormaltiesShouldBeFound("resignLetter.in=" + DEFAULT_RESIGN_LETTER + "," + UPDATED_RESIGN_LETTER);

        // Get all the exitFormaltiesList where resignLetter equals to UPDATED_RESIGN_LETTER
        defaultExitFormaltiesShouldNotBeFound("resignLetter.in=" + UPDATED_RESIGN_LETTER);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByResignLetterIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where resignLetter is not null
        defaultExitFormaltiesShouldBeFound("resignLetter.specified=true");

        // Get all the exitFormaltiesList where resignLetter is null
        defaultExitFormaltiesShouldNotBeFound("resignLetter.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesBySharesIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where shares equals to DEFAULT_SHARES
        defaultExitFormaltiesShouldBeFound("shares.equals=" + DEFAULT_SHARES);

        // Get all the exitFormaltiesList where shares equals to UPDATED_SHARES
        defaultExitFormaltiesShouldNotBeFound("shares.equals=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesBySharesIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where shares in DEFAULT_SHARES or UPDATED_SHARES
        defaultExitFormaltiesShouldBeFound("shares.in=" + DEFAULT_SHARES + "," + UPDATED_SHARES);

        // Get all the exitFormaltiesList where shares equals to UPDATED_SHARES
        defaultExitFormaltiesShouldNotBeFound("shares.in=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesBySharesIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where shares is not null
        defaultExitFormaltiesShouldBeFound("shares.specified=true");

        // Get all the exitFormaltiesList where shares is null
        defaultExitFormaltiesShouldNotBeFound("shares.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesBySharesContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where shares contains DEFAULT_SHARES
        defaultExitFormaltiesShouldBeFound("shares.contains=" + DEFAULT_SHARES);

        // Get all the exitFormaltiesList where shares contains UPDATED_SHARES
        defaultExitFormaltiesShouldNotBeFound("shares.contains=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesBySharesNotContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where shares does not contain DEFAULT_SHARES
        defaultExitFormaltiesShouldNotBeFound("shares.doesNotContain=" + DEFAULT_SHARES);

        // Get all the exitFormaltiesList where shares does not contain UPDATED_SHARES
        defaultExitFormaltiesShouldBeFound("shares.doesNotContain=" + UPDATED_SHARES);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByStaffWelfareIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where staffWelfare equals to DEFAULT_STAFF_WELFARE
        defaultExitFormaltiesShouldBeFound("staffWelfare.equals=" + DEFAULT_STAFF_WELFARE);

        // Get all the exitFormaltiesList where staffWelfare equals to UPDATED_STAFF_WELFARE
        defaultExitFormaltiesShouldNotBeFound("staffWelfare.equals=" + UPDATED_STAFF_WELFARE);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByStaffWelfareIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where staffWelfare in DEFAULT_STAFF_WELFARE or UPDATED_STAFF_WELFARE
        defaultExitFormaltiesShouldBeFound("staffWelfare.in=" + DEFAULT_STAFF_WELFARE + "," + UPDATED_STAFF_WELFARE);

        // Get all the exitFormaltiesList where staffWelfare equals to UPDATED_STAFF_WELFARE
        defaultExitFormaltiesShouldNotBeFound("staffWelfare.in=" + UPDATED_STAFF_WELFARE);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByStaffWelfareIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where staffWelfare is not null
        defaultExitFormaltiesShouldBeFound("staffWelfare.specified=true");

        // Get all the exitFormaltiesList where staffWelfare is null
        defaultExitFormaltiesShouldNotBeFound("staffWelfare.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByStaffWelfareContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where staffWelfare contains DEFAULT_STAFF_WELFARE
        defaultExitFormaltiesShouldBeFound("staffWelfare.contains=" + DEFAULT_STAFF_WELFARE);

        // Get all the exitFormaltiesList where staffWelfare contains UPDATED_STAFF_WELFARE
        defaultExitFormaltiesShouldNotBeFound("staffWelfare.contains=" + UPDATED_STAFF_WELFARE);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByStaffWelfareNotContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where staffWelfare does not contain DEFAULT_STAFF_WELFARE
        defaultExitFormaltiesShouldNotBeFound("staffWelfare.doesNotContain=" + DEFAULT_STAFF_WELFARE);

        // Get all the exitFormaltiesList where staffWelfare does not contain UPDATED_STAFF_WELFARE
        defaultExitFormaltiesShouldBeFound("staffWelfare.doesNotContain=" + UPDATED_STAFF_WELFARE);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByWorkForOrgIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where workForOrg equals to DEFAULT_WORK_FOR_ORG
        defaultExitFormaltiesShouldBeFound("workForOrg.equals=" + DEFAULT_WORK_FOR_ORG);

        // Get all the exitFormaltiesList where workForOrg equals to UPDATED_WORK_FOR_ORG
        defaultExitFormaltiesShouldNotBeFound("workForOrg.equals=" + UPDATED_WORK_FOR_ORG);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByWorkForOrgIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where workForOrg in DEFAULT_WORK_FOR_ORG or UPDATED_WORK_FOR_ORG
        defaultExitFormaltiesShouldBeFound("workForOrg.in=" + DEFAULT_WORK_FOR_ORG + "," + UPDATED_WORK_FOR_ORG);

        // Get all the exitFormaltiesList where workForOrg equals to UPDATED_WORK_FOR_ORG
        defaultExitFormaltiesShouldNotBeFound("workForOrg.in=" + UPDATED_WORK_FOR_ORG);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByWorkForOrgIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where workForOrg is not null
        defaultExitFormaltiesShouldBeFound("workForOrg.specified=true");

        // Get all the exitFormaltiesList where workForOrg is null
        defaultExitFormaltiesShouldNotBeFound("workForOrg.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where status equals to DEFAULT_STATUS
        defaultExitFormaltiesShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the exitFormaltiesList where status equals to UPDATED_STATUS
        defaultExitFormaltiesShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultExitFormaltiesShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the exitFormaltiesList where status equals to UPDATED_STATUS
        defaultExitFormaltiesShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where status is not null
        defaultExitFormaltiesShouldBeFound("status.specified=true");

        // Get all the exitFormaltiesList where status is null
        defaultExitFormaltiesShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultExitFormaltiesShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the exitFormaltiesList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultExitFormaltiesShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultExitFormaltiesShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the exitFormaltiesList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultExitFormaltiesShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where lastModified is not null
        defaultExitFormaltiesShouldBeFound("lastModified.specified=true");

        // Get all the exitFormaltiesList where lastModified is null
        defaultExitFormaltiesShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultExitFormaltiesShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the exitFormaltiesList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultExitFormaltiesShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultExitFormaltiesShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the exitFormaltiesList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultExitFormaltiesShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where lastModifiedBy is not null
        defaultExitFormaltiesShouldBeFound("lastModifiedBy.specified=true");

        // Get all the exitFormaltiesList where lastModifiedBy is null
        defaultExitFormaltiesShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultExitFormaltiesShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the exitFormaltiesList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultExitFormaltiesShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultExitFormaltiesShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the exitFormaltiesList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultExitFormaltiesShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where createdBy equals to DEFAULT_CREATED_BY
        defaultExitFormaltiesShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the exitFormaltiesList where createdBy equals to UPDATED_CREATED_BY
        defaultExitFormaltiesShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultExitFormaltiesShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the exitFormaltiesList where createdBy equals to UPDATED_CREATED_BY
        defaultExitFormaltiesShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where createdBy is not null
        defaultExitFormaltiesShouldBeFound("createdBy.specified=true");

        // Get all the exitFormaltiesList where createdBy is null
        defaultExitFormaltiesShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where createdBy contains DEFAULT_CREATED_BY
        defaultExitFormaltiesShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the exitFormaltiesList where createdBy contains UPDATED_CREATED_BY
        defaultExitFormaltiesShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where createdBy does not contain DEFAULT_CREATED_BY
        defaultExitFormaltiesShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the exitFormaltiesList where createdBy does not contain UPDATED_CREATED_BY
        defaultExitFormaltiesShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where createdOn equals to DEFAULT_CREATED_ON
        defaultExitFormaltiesShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the exitFormaltiesList where createdOn equals to UPDATED_CREATED_ON
        defaultExitFormaltiesShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultExitFormaltiesShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the exitFormaltiesList where createdOn equals to UPDATED_CREATED_ON
        defaultExitFormaltiesShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where createdOn is not null
        defaultExitFormaltiesShouldBeFound("createdOn.specified=true");

        // Get all the exitFormaltiesList where createdOn is null
        defaultExitFormaltiesShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where isDeleted equals to DEFAULT_IS_DELETED
        defaultExitFormaltiesShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the exitFormaltiesList where isDeleted equals to UPDATED_IS_DELETED
        defaultExitFormaltiesShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultExitFormaltiesShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the exitFormaltiesList where isDeleted equals to UPDATED_IS_DELETED
        defaultExitFormaltiesShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where isDeleted is not null
        defaultExitFormaltiesShouldBeFound("isDeleted.specified=true");

        // Get all the exitFormaltiesList where isDeleted is null
        defaultExitFormaltiesShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultExitFormaltiesShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the exitFormaltiesList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultExitFormaltiesShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultExitFormaltiesShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the exitFormaltiesList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultExitFormaltiesShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField1 is not null
        defaultExitFormaltiesShouldBeFound("freeField1.specified=true");

        // Get all the exitFormaltiesList where freeField1 is null
        defaultExitFormaltiesShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultExitFormaltiesShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the exitFormaltiesList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultExitFormaltiesShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultExitFormaltiesShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the exitFormaltiesList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultExitFormaltiesShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultExitFormaltiesShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the exitFormaltiesList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultExitFormaltiesShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultExitFormaltiesShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the exitFormaltiesList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultExitFormaltiesShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField2 is not null
        defaultExitFormaltiesShouldBeFound("freeField2.specified=true");

        // Get all the exitFormaltiesList where freeField2 is null
        defaultExitFormaltiesShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultExitFormaltiesShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the exitFormaltiesList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultExitFormaltiesShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultExitFormaltiesShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the exitFormaltiesList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultExitFormaltiesShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultExitFormaltiesShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the exitFormaltiesList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultExitFormaltiesShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultExitFormaltiesShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the exitFormaltiesList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultExitFormaltiesShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField3 is not null
        defaultExitFormaltiesShouldBeFound("freeField3.specified=true");

        // Get all the exitFormaltiesList where freeField3 is null
        defaultExitFormaltiesShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultExitFormaltiesShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the exitFormaltiesList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultExitFormaltiesShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultExitFormaltiesShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the exitFormaltiesList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultExitFormaltiesShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreefield4IsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freefield4 equals to DEFAULT_FREEFIELD_4
        defaultExitFormaltiesShouldBeFound("freefield4.equals=" + DEFAULT_FREEFIELD_4);

        // Get all the exitFormaltiesList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultExitFormaltiesShouldNotBeFound("freefield4.equals=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreefield4IsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freefield4 in DEFAULT_FREEFIELD_4 or UPDATED_FREEFIELD_4
        defaultExitFormaltiesShouldBeFound("freefield4.in=" + DEFAULT_FREEFIELD_4 + "," + UPDATED_FREEFIELD_4);

        // Get all the exitFormaltiesList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultExitFormaltiesShouldNotBeFound("freefield4.in=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreefield4IsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freefield4 is not null
        defaultExitFormaltiesShouldBeFound("freefield4.specified=true");

        // Get all the exitFormaltiesList where freefield4 is null
        defaultExitFormaltiesShouldNotBeFound("freefield4.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreefield4ContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freefield4 contains DEFAULT_FREEFIELD_4
        defaultExitFormaltiesShouldBeFound("freefield4.contains=" + DEFAULT_FREEFIELD_4);

        // Get all the exitFormaltiesList where freefield4 contains UPDATED_FREEFIELD_4
        defaultExitFormaltiesShouldNotBeFound("freefield4.contains=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreefield4NotContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freefield4 does not contain DEFAULT_FREEFIELD_4
        defaultExitFormaltiesShouldNotBeFound("freefield4.doesNotContain=" + DEFAULT_FREEFIELD_4);

        // Get all the exitFormaltiesList where freefield4 does not contain UPDATED_FREEFIELD_4
        defaultExitFormaltiesShouldBeFound("freefield4.doesNotContain=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreefield5IsEqualToSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freefield5 equals to DEFAULT_FREEFIELD_5
        defaultExitFormaltiesShouldBeFound("freefield5.equals=" + DEFAULT_FREEFIELD_5);

        // Get all the exitFormaltiesList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultExitFormaltiesShouldNotBeFound("freefield5.equals=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreefield5IsInShouldWork() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freefield5 in DEFAULT_FREEFIELD_5 or UPDATED_FREEFIELD_5
        defaultExitFormaltiesShouldBeFound("freefield5.in=" + DEFAULT_FREEFIELD_5 + "," + UPDATED_FREEFIELD_5);

        // Get all the exitFormaltiesList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultExitFormaltiesShouldNotBeFound("freefield5.in=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreefield5IsNullOrNotNull() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freefield5 is not null
        defaultExitFormaltiesShouldBeFound("freefield5.specified=true");

        // Get all the exitFormaltiesList where freefield5 is null
        defaultExitFormaltiesShouldNotBeFound("freefield5.specified=false");
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreefield5ContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freefield5 contains DEFAULT_FREEFIELD_5
        defaultExitFormaltiesShouldBeFound("freefield5.contains=" + DEFAULT_FREEFIELD_5);

        // Get all the exitFormaltiesList where freefield5 contains UPDATED_FREEFIELD_5
        defaultExitFormaltiesShouldNotBeFound("freefield5.contains=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByFreefield5NotContainsSomething() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        // Get all the exitFormaltiesList where freefield5 does not contain DEFAULT_FREEFIELD_5
        defaultExitFormaltiesShouldNotBeFound("freefield5.doesNotContain=" + DEFAULT_FREEFIELD_5);

        // Get all the exitFormaltiesList where freefield5 does not contain UPDATED_FREEFIELD_5
        defaultExitFormaltiesShouldBeFound("freefield5.doesNotContain=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllExitFormaltiesByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            exitFormaltiesRepository.saveAndFlush(exitFormalties);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        exitFormalties.setEmployee(employee);
        exitFormaltiesRepository.saveAndFlush(exitFormalties);
        Long employeeId = employee.getId();

        // Get all the exitFormaltiesList where employee equals to employeeId
        defaultExitFormaltiesShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the exitFormaltiesList where employee equals to (employeeId + 1)
        defaultExitFormaltiesShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExitFormaltiesShouldBeFound(String filter) throws Exception {
        restExitFormaltiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exitFormalties.getId().intValue())))
            .andExpect(jsonPath("$.[*].security").value(hasItem(DEFAULT_SECURITY.toString())))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK)))
            .andExpect(jsonPath("$.[*].exitDate").value(hasItem(DEFAULT_EXIT_DATE.toString())))
            .andExpect(jsonPath("$.[*].exitInterview").value(hasItem(DEFAULT_EXIT_INTERVIEW.toString())))
            .andExpect(jsonPath("$.[*].libNoDue").value(hasItem(DEFAULT_LIB_NO_DUE.toString())))
            .andExpect(jsonPath("$.[*].noticePeriodServed").value(hasItem(DEFAULT_NOTICE_PERIOD_SERVED.toString())))
            .andExpect(jsonPath("$.[*].clearence").value(hasItem(DEFAULT_CLEARENCE.toString())))
            .andExpect(jsonPath("$.[*].orgAssets").value(hasItem(DEFAULT_ORG_ASSETS.toString())))
            .andExpect(jsonPath("$.[*].orgVehical").value(hasItem(DEFAULT_ORG_VEHICAL.toString())))
            .andExpect(jsonPath("$.[*].resignLetter").value(hasItem(DEFAULT_RESIGN_LETTER.toString())))
            .andExpect(jsonPath("$.[*].shares").value(hasItem(DEFAULT_SHARES)))
            .andExpect(jsonPath("$.[*].staffWelfare").value(hasItem(DEFAULT_STAFF_WELFARE)))
            .andExpect(jsonPath("$.[*].workForOrg").value(hasItem(DEFAULT_WORK_FOR_ORG.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
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
        restExitFormaltiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExitFormaltiesShouldNotBeFound(String filter) throws Exception {
        restExitFormaltiesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExitFormaltiesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingExitFormalties() throws Exception {
        // Get the exitFormalties
        restExitFormaltiesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingExitFormalties() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        int databaseSizeBeforeUpdate = exitFormaltiesRepository.findAll().size();

        // Update the exitFormalties
        ExitFormalties updatedExitFormalties = exitFormaltiesRepository.findById(exitFormalties.getId()).get();
        // Disconnect from session so that the updates on updatedExitFormalties are not directly saved in db
        em.detach(updatedExitFormalties);
        updatedExitFormalties
            .security(UPDATED_SECURITY)
            .feedback(UPDATED_FEEDBACK)
            .exitDate(UPDATED_EXIT_DATE)
            .exitInterview(UPDATED_EXIT_INTERVIEW)
            .libNoDue(UPDATED_LIB_NO_DUE)
            .noticePeriodServed(UPDATED_NOTICE_PERIOD_SERVED)
            .clearence(UPDATED_CLEARENCE)
            .orgAssets(UPDATED_ORG_ASSETS)
            .orgVehical(UPDATED_ORG_VEHICAL)
            .resignLetter(UPDATED_RESIGN_LETTER)
            .shares(UPDATED_SHARES)
            .staffWelfare(UPDATED_STAFF_WELFARE)
            .workForOrg(UPDATED_WORK_FOR_ORG)
            .status(UPDATED_STATUS)
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
        ExitFormaltiesDTO exitFormaltiesDTO = exitFormaltiesMapper.toDto(updatedExitFormalties);

        restExitFormaltiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exitFormaltiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exitFormaltiesDTO))
            )
            .andExpect(status().isOk());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeUpdate);
        ExitFormalties testExitFormalties = exitFormaltiesList.get(exitFormaltiesList.size() - 1);
        assertThat(testExitFormalties.getSecurity()).isEqualTo(UPDATED_SECURITY);
        assertThat(testExitFormalties.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
        assertThat(testExitFormalties.getExitDate()).isEqualTo(UPDATED_EXIT_DATE);
        assertThat(testExitFormalties.getExitInterview()).isEqualTo(UPDATED_EXIT_INTERVIEW);
        assertThat(testExitFormalties.getLibNoDue()).isEqualTo(UPDATED_LIB_NO_DUE);
        assertThat(testExitFormalties.getNoticePeriodServed()).isEqualTo(UPDATED_NOTICE_PERIOD_SERVED);
        assertThat(testExitFormalties.getClearence()).isEqualTo(UPDATED_CLEARENCE);
        assertThat(testExitFormalties.getOrgAssets()).isEqualTo(UPDATED_ORG_ASSETS);
        assertThat(testExitFormalties.getOrgVehical()).isEqualTo(UPDATED_ORG_VEHICAL);
        assertThat(testExitFormalties.getResignLetter()).isEqualTo(UPDATED_RESIGN_LETTER);
        assertThat(testExitFormalties.getShares()).isEqualTo(UPDATED_SHARES);
        assertThat(testExitFormalties.getStaffWelfare()).isEqualTo(UPDATED_STAFF_WELFARE);
        assertThat(testExitFormalties.getWorkForOrg()).isEqualTo(UPDATED_WORK_FOR_ORG);
        assertThat(testExitFormalties.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testExitFormalties.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testExitFormalties.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testExitFormalties.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExitFormalties.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExitFormalties.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testExitFormalties.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testExitFormalties.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testExitFormalties.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testExitFormalties.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testExitFormalties.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingExitFormalties() throws Exception {
        int databaseSizeBeforeUpdate = exitFormaltiesRepository.findAll().size();
        exitFormalties.setId(count.incrementAndGet());

        // Create the ExitFormalties
        ExitFormaltiesDTO exitFormaltiesDTO = exitFormaltiesMapper.toDto(exitFormalties);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExitFormaltiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, exitFormaltiesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exitFormaltiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchExitFormalties() throws Exception {
        int databaseSizeBeforeUpdate = exitFormaltiesRepository.findAll().size();
        exitFormalties.setId(count.incrementAndGet());

        // Create the ExitFormalties
        ExitFormaltiesDTO exitFormaltiesDTO = exitFormaltiesMapper.toDto(exitFormalties);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExitFormaltiesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(exitFormaltiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamExitFormalties() throws Exception {
        int databaseSizeBeforeUpdate = exitFormaltiesRepository.findAll().size();
        exitFormalties.setId(count.incrementAndGet());

        // Create the ExitFormalties
        ExitFormaltiesDTO exitFormaltiesDTO = exitFormaltiesMapper.toDto(exitFormalties);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExitFormaltiesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(exitFormaltiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateExitFormaltiesWithPatch() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        int databaseSizeBeforeUpdate = exitFormaltiesRepository.findAll().size();

        // Update the exitFormalties using partial update
        ExitFormalties partialUpdatedExitFormalties = new ExitFormalties();
        partialUpdatedExitFormalties.setId(exitFormalties.getId());

        partialUpdatedExitFormalties
            .security(UPDATED_SECURITY)
            .exitDate(UPDATED_EXIT_DATE)
            .libNoDue(UPDATED_LIB_NO_DUE)
            .noticePeriodServed(UPDATED_NOTICE_PERIOD_SERVED)
            .shares(UPDATED_SHARES)
            .staffWelfare(UPDATED_STAFF_WELFARE)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freefield4(UPDATED_FREEFIELD_4)
            .freefield5(UPDATED_FREEFIELD_5);

        restExitFormaltiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExitFormalties.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExitFormalties))
            )
            .andExpect(status().isOk());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeUpdate);
        ExitFormalties testExitFormalties = exitFormaltiesList.get(exitFormaltiesList.size() - 1);
        assertThat(testExitFormalties.getSecurity()).isEqualTo(UPDATED_SECURITY);
        assertThat(testExitFormalties.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
        assertThat(testExitFormalties.getExitDate()).isEqualTo(UPDATED_EXIT_DATE);
        assertThat(testExitFormalties.getExitInterview()).isEqualTo(DEFAULT_EXIT_INTERVIEW);
        assertThat(testExitFormalties.getLibNoDue()).isEqualTo(UPDATED_LIB_NO_DUE);
        assertThat(testExitFormalties.getNoticePeriodServed()).isEqualTo(UPDATED_NOTICE_PERIOD_SERVED);
        assertThat(testExitFormalties.getClearence()).isEqualTo(DEFAULT_CLEARENCE);
        assertThat(testExitFormalties.getOrgAssets()).isEqualTo(DEFAULT_ORG_ASSETS);
        assertThat(testExitFormalties.getOrgVehical()).isEqualTo(DEFAULT_ORG_VEHICAL);
        assertThat(testExitFormalties.getResignLetter()).isEqualTo(DEFAULT_RESIGN_LETTER);
        assertThat(testExitFormalties.getShares()).isEqualTo(UPDATED_SHARES);
        assertThat(testExitFormalties.getStaffWelfare()).isEqualTo(UPDATED_STAFF_WELFARE);
        assertThat(testExitFormalties.getWorkForOrg()).isEqualTo(DEFAULT_WORK_FOR_ORG);
        assertThat(testExitFormalties.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testExitFormalties.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testExitFormalties.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testExitFormalties.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testExitFormalties.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExitFormalties.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testExitFormalties.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testExitFormalties.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testExitFormalties.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testExitFormalties.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testExitFormalties.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateExitFormaltiesWithPatch() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        int databaseSizeBeforeUpdate = exitFormaltiesRepository.findAll().size();

        // Update the exitFormalties using partial update
        ExitFormalties partialUpdatedExitFormalties = new ExitFormalties();
        partialUpdatedExitFormalties.setId(exitFormalties.getId());

        partialUpdatedExitFormalties
            .security(UPDATED_SECURITY)
            .feedback(UPDATED_FEEDBACK)
            .exitDate(UPDATED_EXIT_DATE)
            .exitInterview(UPDATED_EXIT_INTERVIEW)
            .libNoDue(UPDATED_LIB_NO_DUE)
            .noticePeriodServed(UPDATED_NOTICE_PERIOD_SERVED)
            .clearence(UPDATED_CLEARENCE)
            .orgAssets(UPDATED_ORG_ASSETS)
            .orgVehical(UPDATED_ORG_VEHICAL)
            .resignLetter(UPDATED_RESIGN_LETTER)
            .shares(UPDATED_SHARES)
            .staffWelfare(UPDATED_STAFF_WELFARE)
            .workForOrg(UPDATED_WORK_FOR_ORG)
            .status(UPDATED_STATUS)
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

        restExitFormaltiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedExitFormalties.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedExitFormalties))
            )
            .andExpect(status().isOk());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeUpdate);
        ExitFormalties testExitFormalties = exitFormaltiesList.get(exitFormaltiesList.size() - 1);
        assertThat(testExitFormalties.getSecurity()).isEqualTo(UPDATED_SECURITY);
        assertThat(testExitFormalties.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
        assertThat(testExitFormalties.getExitDate()).isEqualTo(UPDATED_EXIT_DATE);
        assertThat(testExitFormalties.getExitInterview()).isEqualTo(UPDATED_EXIT_INTERVIEW);
        assertThat(testExitFormalties.getLibNoDue()).isEqualTo(UPDATED_LIB_NO_DUE);
        assertThat(testExitFormalties.getNoticePeriodServed()).isEqualTo(UPDATED_NOTICE_PERIOD_SERVED);
        assertThat(testExitFormalties.getClearence()).isEqualTo(UPDATED_CLEARENCE);
        assertThat(testExitFormalties.getOrgAssets()).isEqualTo(UPDATED_ORG_ASSETS);
        assertThat(testExitFormalties.getOrgVehical()).isEqualTo(UPDATED_ORG_VEHICAL);
        assertThat(testExitFormalties.getResignLetter()).isEqualTo(UPDATED_RESIGN_LETTER);
        assertThat(testExitFormalties.getShares()).isEqualTo(UPDATED_SHARES);
        assertThat(testExitFormalties.getStaffWelfare()).isEqualTo(UPDATED_STAFF_WELFARE);
        assertThat(testExitFormalties.getWorkForOrg()).isEqualTo(UPDATED_WORK_FOR_ORG);
        assertThat(testExitFormalties.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testExitFormalties.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testExitFormalties.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testExitFormalties.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testExitFormalties.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testExitFormalties.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testExitFormalties.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testExitFormalties.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testExitFormalties.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testExitFormalties.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testExitFormalties.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingExitFormalties() throws Exception {
        int databaseSizeBeforeUpdate = exitFormaltiesRepository.findAll().size();
        exitFormalties.setId(count.incrementAndGet());

        // Create the ExitFormalties
        ExitFormaltiesDTO exitFormaltiesDTO = exitFormaltiesMapper.toDto(exitFormalties);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExitFormaltiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, exitFormaltiesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exitFormaltiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchExitFormalties() throws Exception {
        int databaseSizeBeforeUpdate = exitFormaltiesRepository.findAll().size();
        exitFormalties.setId(count.incrementAndGet());

        // Create the ExitFormalties
        ExitFormaltiesDTO exitFormaltiesDTO = exitFormaltiesMapper.toDto(exitFormalties);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExitFormaltiesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exitFormaltiesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamExitFormalties() throws Exception {
        int databaseSizeBeforeUpdate = exitFormaltiesRepository.findAll().size();
        exitFormalties.setId(count.incrementAndGet());

        // Create the ExitFormalties
        ExitFormaltiesDTO exitFormaltiesDTO = exitFormaltiesMapper.toDto(exitFormalties);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restExitFormaltiesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(exitFormaltiesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ExitFormalties in the database
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteExitFormalties() throws Exception {
        // Initialize the database
        exitFormaltiesRepository.saveAndFlush(exitFormalties);

        int databaseSizeBeforeDelete = exitFormaltiesRepository.findAll().size();

        // Delete the exitFormalties
        restExitFormaltiesMockMvc
            .perform(delete(ENTITY_API_URL_ID, exitFormalties.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExitFormalties> exitFormaltiesList = exitFormaltiesRepository.findAll();
        assertThat(exitFormaltiesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
