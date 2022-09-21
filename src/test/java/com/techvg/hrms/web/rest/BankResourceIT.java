package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Bank;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.Organization;
import com.techvg.hrms.repository.BankRepository;
import com.techvg.hrms.service.criteria.BankCriteria;
import com.techvg.hrms.service.dto.BankDTO;
import com.techvg.hrms.service.mapper.BankMapper;
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
 * Integration tests for the {@link BankResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BankResourceIT {

    private static final Long DEFAULT_ACCOUNT_NO = 1L;
    private static final Long UPDATED_ACCOUNT_NO = 2L;
    private static final Long SMALLER_ACCOUNT_NO = 1L - 1L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCH = "AAAAAAAAAA";
    private static final String UPDATED_BRANCH = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MCIR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MCIR_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/banks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankMapper bankMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBankMockMvc;

    private Bank bank;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bank createEntity(EntityManager em) {
        Bank bank = new Bank()
            .accountNo(DEFAULT_ACCOUNT_NO)
            .name(DEFAULT_NAME)
            .branch(DEFAULT_BRANCH)
            .ifscCode(DEFAULT_IFSC_CODE)
            .mcirCode(DEFAULT_MCIR_CODE)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .isDeleted(DEFAULT_IS_DELETED)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return bank;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bank createUpdatedEntity(EntityManager em) {
        Bank bank = new Bank()
            .accountNo(UPDATED_ACCOUNT_NO)
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .ifscCode(UPDATED_IFSC_CODE)
            .mcirCode(UPDATED_MCIR_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return bank;
    }

    @BeforeEach
    public void initTest() {
        bank = createEntity(em);
    }

    @Test
    @Transactional
    void createBank() throws Exception {
        int databaseSizeBeforeCreate = bankRepository.findAll().size();
        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);
        restBankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDTO)))
            .andExpect(status().isCreated());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeCreate + 1);
        Bank testBank = bankList.get(bankList.size() - 1);
        assertThat(testBank.getAccountNo()).isEqualTo(DEFAULT_ACCOUNT_NO);
        assertThat(testBank.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBank.getBranch()).isEqualTo(DEFAULT_BRANCH);
        assertThat(testBank.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testBank.getMcirCode()).isEqualTo(DEFAULT_MCIR_CODE);
        assertThat(testBank.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBank.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testBank.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBank.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testBank.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testBank.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testBank.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testBank.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createBankWithExistingId() throws Exception {
        // Create the Bank with an existing ID
        bank.setId(1L);
        BankDTO bankDTO = bankMapper.toDto(bank);

        int databaseSizeBeforeCreate = bankRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBankMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBanks() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList
        restBankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bank.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].mcirCode").value(hasItem(DEFAULT_MCIR_CODE)))
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
    void getBank() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get the bank
        restBankMockMvc
            .perform(get(ENTITY_API_URL_ID, bank.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bank.getId().intValue()))
            .andExpect(jsonPath("$.accountNo").value(DEFAULT_ACCOUNT_NO.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.branch").value(DEFAULT_BRANCH))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.mcirCode").value(DEFAULT_MCIR_CODE))
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
    void getBanksByIdFiltering() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        Long id = bank.getId();

        defaultBankShouldBeFound("id.equals=" + id);
        defaultBankShouldNotBeFound("id.notEquals=" + id);

        defaultBankShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBankShouldNotBeFound("id.greaterThan=" + id);

        defaultBankShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBankShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBanksByAccountNoIsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where accountNo equals to DEFAULT_ACCOUNT_NO
        defaultBankShouldBeFound("accountNo.equals=" + DEFAULT_ACCOUNT_NO);

        // Get all the bankList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultBankShouldNotBeFound("accountNo.equals=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksByAccountNoIsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where accountNo in DEFAULT_ACCOUNT_NO or UPDATED_ACCOUNT_NO
        defaultBankShouldBeFound("accountNo.in=" + DEFAULT_ACCOUNT_NO + "," + UPDATED_ACCOUNT_NO);

        // Get all the bankList where accountNo equals to UPDATED_ACCOUNT_NO
        defaultBankShouldNotBeFound("accountNo.in=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksByAccountNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where accountNo is not null
        defaultBankShouldBeFound("accountNo.specified=true");

        // Get all the bankList where accountNo is null
        defaultBankShouldNotBeFound("accountNo.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByAccountNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where accountNo is greater than or equal to DEFAULT_ACCOUNT_NO
        defaultBankShouldBeFound("accountNo.greaterThanOrEqual=" + DEFAULT_ACCOUNT_NO);

        // Get all the bankList where accountNo is greater than or equal to UPDATED_ACCOUNT_NO
        defaultBankShouldNotBeFound("accountNo.greaterThanOrEqual=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksByAccountNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where accountNo is less than or equal to DEFAULT_ACCOUNT_NO
        defaultBankShouldBeFound("accountNo.lessThanOrEqual=" + DEFAULT_ACCOUNT_NO);

        // Get all the bankList where accountNo is less than or equal to SMALLER_ACCOUNT_NO
        defaultBankShouldNotBeFound("accountNo.lessThanOrEqual=" + SMALLER_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksByAccountNoIsLessThanSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where accountNo is less than DEFAULT_ACCOUNT_NO
        defaultBankShouldNotBeFound("accountNo.lessThan=" + DEFAULT_ACCOUNT_NO);

        // Get all the bankList where accountNo is less than UPDATED_ACCOUNT_NO
        defaultBankShouldBeFound("accountNo.lessThan=" + UPDATED_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksByAccountNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where accountNo is greater than DEFAULT_ACCOUNT_NO
        defaultBankShouldNotBeFound("accountNo.greaterThan=" + DEFAULT_ACCOUNT_NO);

        // Get all the bankList where accountNo is greater than SMALLER_ACCOUNT_NO
        defaultBankShouldBeFound("accountNo.greaterThan=" + SMALLER_ACCOUNT_NO);
    }

    @Test
    @Transactional
    void getAllBanksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where name equals to DEFAULT_NAME
        defaultBankShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the bankList where name equals to UPDATED_NAME
        defaultBankShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBankShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the bankList where name equals to UPDATED_NAME
        defaultBankShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where name is not null
        defaultBankShouldBeFound("name.specified=true");

        // Get all the bankList where name is null
        defaultBankShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByNameContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where name contains DEFAULT_NAME
        defaultBankShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the bankList where name contains UPDATED_NAME
        defaultBankShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where name does not contain DEFAULT_NAME
        defaultBankShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the bankList where name does not contain UPDATED_NAME
        defaultBankShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByBranchIsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where branch equals to DEFAULT_BRANCH
        defaultBankShouldBeFound("branch.equals=" + DEFAULT_BRANCH);

        // Get all the bankList where branch equals to UPDATED_BRANCH
        defaultBankShouldNotBeFound("branch.equals=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBanksByBranchIsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where branch in DEFAULT_BRANCH or UPDATED_BRANCH
        defaultBankShouldBeFound("branch.in=" + DEFAULT_BRANCH + "," + UPDATED_BRANCH);

        // Get all the bankList where branch equals to UPDATED_BRANCH
        defaultBankShouldNotBeFound("branch.in=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBanksByBranchIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where branch is not null
        defaultBankShouldBeFound("branch.specified=true");

        // Get all the bankList where branch is null
        defaultBankShouldNotBeFound("branch.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByBranchContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where branch contains DEFAULT_BRANCH
        defaultBankShouldBeFound("branch.contains=" + DEFAULT_BRANCH);

        // Get all the bankList where branch contains UPDATED_BRANCH
        defaultBankShouldNotBeFound("branch.contains=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBanksByBranchNotContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where branch does not contain DEFAULT_BRANCH
        defaultBankShouldNotBeFound("branch.doesNotContain=" + DEFAULT_BRANCH);

        // Get all the bankList where branch does not contain UPDATED_BRANCH
        defaultBankShouldBeFound("branch.doesNotContain=" + UPDATED_BRANCH);
    }

    @Test
    @Transactional
    void getAllBanksByIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where ifscCode equals to DEFAULT_IFSC_CODE
        defaultBankShouldBeFound("ifscCode.equals=" + DEFAULT_IFSC_CODE);

        // Get all the bankList where ifscCode equals to UPDATED_IFSC_CODE
        defaultBankShouldNotBeFound("ifscCode.equals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where ifscCode in DEFAULT_IFSC_CODE or UPDATED_IFSC_CODE
        defaultBankShouldBeFound("ifscCode.in=" + DEFAULT_IFSC_CODE + "," + UPDATED_IFSC_CODE);

        // Get all the bankList where ifscCode equals to UPDATED_IFSC_CODE
        defaultBankShouldNotBeFound("ifscCode.in=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where ifscCode is not null
        defaultBankShouldBeFound("ifscCode.specified=true");

        // Get all the bankList where ifscCode is null
        defaultBankShouldNotBeFound("ifscCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByIfscCodeContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where ifscCode contains DEFAULT_IFSC_CODE
        defaultBankShouldBeFound("ifscCode.contains=" + DEFAULT_IFSC_CODE);

        // Get all the bankList where ifscCode contains UPDATED_IFSC_CODE
        defaultBankShouldNotBeFound("ifscCode.contains=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByIfscCodeNotContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where ifscCode does not contain DEFAULT_IFSC_CODE
        defaultBankShouldNotBeFound("ifscCode.doesNotContain=" + DEFAULT_IFSC_CODE);

        // Get all the bankList where ifscCode does not contain UPDATED_IFSC_CODE
        defaultBankShouldBeFound("ifscCode.doesNotContain=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByMcirCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where mcirCode equals to DEFAULT_MCIR_CODE
        defaultBankShouldBeFound("mcirCode.equals=" + DEFAULT_MCIR_CODE);

        // Get all the bankList where mcirCode equals to UPDATED_MCIR_CODE
        defaultBankShouldNotBeFound("mcirCode.equals=" + UPDATED_MCIR_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByMcirCodeIsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where mcirCode in DEFAULT_MCIR_CODE or UPDATED_MCIR_CODE
        defaultBankShouldBeFound("mcirCode.in=" + DEFAULT_MCIR_CODE + "," + UPDATED_MCIR_CODE);

        // Get all the bankList where mcirCode equals to UPDATED_MCIR_CODE
        defaultBankShouldNotBeFound("mcirCode.in=" + UPDATED_MCIR_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByMcirCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where mcirCode is not null
        defaultBankShouldBeFound("mcirCode.specified=true");

        // Get all the bankList where mcirCode is null
        defaultBankShouldNotBeFound("mcirCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByMcirCodeContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where mcirCode contains DEFAULT_MCIR_CODE
        defaultBankShouldBeFound("mcirCode.contains=" + DEFAULT_MCIR_CODE);

        // Get all the bankList where mcirCode contains UPDATED_MCIR_CODE
        defaultBankShouldNotBeFound("mcirCode.contains=" + UPDATED_MCIR_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByMcirCodeNotContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where mcirCode does not contain DEFAULT_MCIR_CODE
        defaultBankShouldNotBeFound("mcirCode.doesNotContain=" + DEFAULT_MCIR_CODE);

        // Get all the bankList where mcirCode does not contain UPDATED_MCIR_CODE
        defaultBankShouldBeFound("mcirCode.doesNotContain=" + UPDATED_MCIR_CODE);
    }

    @Test
    @Transactional
    void getAllBanksByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultBankShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the bankList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBankShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBanksByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultBankShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the bankList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultBankShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllBanksByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where lastModified is not null
        defaultBankShouldBeFound("lastModified.specified=true");

        // Get all the bankList where lastModified is null
        defaultBankShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultBankShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bankList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBankShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultBankShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the bankList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultBankShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where lastModifiedBy is not null
        defaultBankShouldBeFound("lastModifiedBy.specified=true");

        // Get all the bankList where lastModifiedBy is null
        defaultBankShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultBankShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bankList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultBankShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultBankShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the bankList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultBankShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllBanksByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where createdBy equals to DEFAULT_CREATED_BY
        defaultBankShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the bankList where createdBy equals to UPDATED_CREATED_BY
        defaultBankShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBanksByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultBankShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the bankList where createdBy equals to UPDATED_CREATED_BY
        defaultBankShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBanksByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where createdBy is not null
        defaultBankShouldBeFound("createdBy.specified=true");

        // Get all the bankList where createdBy is null
        defaultBankShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where createdBy contains DEFAULT_CREATED_BY
        defaultBankShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the bankList where createdBy contains UPDATED_CREATED_BY
        defaultBankShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBanksByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where createdBy does not contain DEFAULT_CREATED_BY
        defaultBankShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the bankList where createdBy does not contain UPDATED_CREATED_BY
        defaultBankShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllBanksByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where createdOn equals to DEFAULT_CREATED_ON
        defaultBankShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the bankList where createdOn equals to UPDATED_CREATED_ON
        defaultBankShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllBanksByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultBankShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the bankList where createdOn equals to UPDATED_CREATED_ON
        defaultBankShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllBanksByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where createdOn is not null
        defaultBankShouldBeFound("createdOn.specified=true");

        // Get all the bankList where createdOn is null
        defaultBankShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where isDeleted equals to DEFAULT_IS_DELETED
        defaultBankShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the bankList where isDeleted equals to UPDATED_IS_DELETED
        defaultBankShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllBanksByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultBankShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the bankList where isDeleted equals to UPDATED_IS_DELETED
        defaultBankShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllBanksByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where isDeleted is not null
        defaultBankShouldBeFound("isDeleted.specified=true");

        // Get all the bankList where isDeleted is null
        defaultBankShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultBankShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the bankList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultBankShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultBankShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the bankList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultBankShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField1 is not null
        defaultBankShouldBeFound("freeField1.specified=true");

        // Get all the bankList where freeField1 is null
        defaultBankShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultBankShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the bankList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultBankShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultBankShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the bankList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultBankShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultBankShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the bankList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultBankShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultBankShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the bankList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultBankShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField2 is not null
        defaultBankShouldBeFound("freeField2.specified=true");

        // Get all the bankList where freeField2 is null
        defaultBankShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultBankShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the bankList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultBankShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultBankShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the bankList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultBankShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultBankShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the bankList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultBankShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultBankShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the bankList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultBankShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField3 is not null
        defaultBankShouldBeFound("freeField3.specified=true");

        // Get all the bankList where freeField3 is null
        defaultBankShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultBankShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the bankList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultBankShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllBanksByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        // Get all the bankList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultBankShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the bankList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultBankShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllBanksByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            bankRepository.saveAndFlush(bank);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        bank.setEmployee(employee);
        bankRepository.saveAndFlush(bank);
        Long employeeId = employee.getId();

        // Get all the bankList where employee equals to employeeId
        defaultBankShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the bankList where employee equals to (employeeId + 1)
        defaultBankShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    @Test
    @Transactional
    void getAllBanksByOrganizationIsEqualToSomething() throws Exception {
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            bankRepository.saveAndFlush(bank);
            organization = OrganizationResourceIT.createEntity(em);
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        em.persist(organization);
        em.flush();
        bank.setOrganization(organization);
        bankRepository.saveAndFlush(bank);
        Long organizationId = organization.getId();

        // Get all the bankList where organization equals to organizationId
        defaultBankShouldBeFound("organizationId.equals=" + organizationId);

        // Get all the bankList where organization equals to (organizationId + 1)
        defaultBankShouldNotBeFound("organizationId.equals=" + (organizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBankShouldBeFound(String filter) throws Exception {
        restBankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bank.getId().intValue())))
            .andExpect(jsonPath("$.[*].accountNo").value(hasItem(DEFAULT_ACCOUNT_NO.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].branch").value(hasItem(DEFAULT_BRANCH)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].mcirCode").value(hasItem(DEFAULT_MCIR_CODE)))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));

        // Check, that the count call also returns 1
        restBankMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBankShouldNotBeFound(String filter) throws Exception {
        restBankMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBankMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBank() throws Exception {
        // Get the bank
        restBankMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBank() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        int databaseSizeBeforeUpdate = bankRepository.findAll().size();

        // Update the bank
        Bank updatedBank = bankRepository.findById(bank.getId()).get();
        // Disconnect from session so that the updates on updatedBank are not directly saved in db
        em.detach(updatedBank);
        updatedBank
            .accountNo(UPDATED_ACCOUNT_NO)
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .ifscCode(UPDATED_IFSC_CODE)
            .mcirCode(UPDATED_MCIR_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        BankDTO bankDTO = bankMapper.toDto(updatedBank);

        restBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
        Bank testBank = bankList.get(bankList.size() - 1);
        assertThat(testBank.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testBank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBank.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testBank.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testBank.getMcirCode()).isEqualTo(UPDATED_MCIR_CODE);
        assertThat(testBank.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBank.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testBank.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBank.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBank.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testBank.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testBank.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testBank.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bankDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBankWithPatch() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        int databaseSizeBeforeUpdate = bankRepository.findAll().size();

        // Update the bank using partial update
        Bank partialUpdatedBank = new Bank();
        partialUpdatedBank.setId(bank.getId());

        partialUpdatedBank
            .accountNo(UPDATED_ACCOUNT_NO)
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField2(UPDATED_FREE_FIELD_2);

        restBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBank.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBank))
            )
            .andExpect(status().isOk());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
        Bank testBank = bankList.get(bankList.size() - 1);
        assertThat(testBank.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testBank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBank.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testBank.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testBank.getMcirCode()).isEqualTo(DEFAULT_MCIR_CODE);
        assertThat(testBank.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testBank.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testBank.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBank.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBank.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testBank.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testBank.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testBank.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdateBankWithPatch() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        int databaseSizeBeforeUpdate = bankRepository.findAll().size();

        // Update the bank using partial update
        Bank partialUpdatedBank = new Bank();
        partialUpdatedBank.setId(bank.getId());

        partialUpdatedBank
            .accountNo(UPDATED_ACCOUNT_NO)
            .name(UPDATED_NAME)
            .branch(UPDATED_BRANCH)
            .ifscCode(UPDATED_IFSC_CODE)
            .mcirCode(UPDATED_MCIR_CODE)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBank.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBank))
            )
            .andExpect(status().isOk());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
        Bank testBank = bankList.get(bankList.size() - 1);
        assertThat(testBank.getAccountNo()).isEqualTo(UPDATED_ACCOUNT_NO);
        assertThat(testBank.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBank.getBranch()).isEqualTo(UPDATED_BRANCH);
        assertThat(testBank.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testBank.getMcirCode()).isEqualTo(UPDATED_MCIR_CODE);
        assertThat(testBank.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testBank.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testBank.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBank.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testBank.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testBank.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testBank.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testBank.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bankDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bankDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBank() throws Exception {
        int databaseSizeBeforeUpdate = bankRepository.findAll().size();
        bank.setId(count.incrementAndGet());

        // Create the Bank
        BankDTO bankDTO = bankMapper.toDto(bank);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBankMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bankDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bank in the database
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBank() throws Exception {
        // Initialize the database
        bankRepository.saveAndFlush(bank);

        int databaseSizeBeforeDelete = bankRepository.findAll().size();

        // Delete the bank
        restBankMockMvc
            .perform(delete(ENTITY_API_URL_ID, bank.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bank> bankList = bankRepository.findAll();
        assertThat(bankList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
