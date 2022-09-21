package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Dependant;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.enumeration.DependantType;
import com.techvg.hrms.repository.DependantRepository;
import com.techvg.hrms.service.criteria.DependantCriteria;
import com.techvg.hrms.service.dto.DependantDTO;
import com.techvg.hrms.service.mapper.DependantMapper;
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
 * Integration tests for the {@link DependantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DependantResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AGE = "AAAAAAAAAA";
    private static final String UPDATED_AGE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_OF_BIRTH = "AAAAAAAAAA";
    private static final String UPDATED_DATE_OF_BIRTH = "BBBBBBBBBB";

    private static final DependantType DEFAULT_TYPE = DependantType.MOTHER;
    private static final DependantType UPDATED_TYPE = DependantType.CHILD;

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/dependants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DependantRepository dependantRepository;

    @Autowired
    private DependantMapper dependantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDependantMockMvc;

    private Dependant dependant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dependant createEntity(EntityManager em) {
        Dependant dependant = new Dependant()
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .type(DEFAULT_TYPE)
            .mobile(DEFAULT_MOBILE)
            .address(DEFAULT_ADDRESS)
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
        return dependant;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dependant createUpdatedEntity(EntityManager em) {
        Dependant dependant = new Dependant()
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .type(UPDATED_TYPE)
            .mobile(UPDATED_MOBILE)
            .address(UPDATED_ADDRESS)
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
        return dependant;
    }

    @BeforeEach
    public void initTest() {
        dependant = createEntity(em);
    }

    @Test
    @Transactional
    void createDependant() throws Exception {
        int databaseSizeBeforeCreate = dependantRepository.findAll().size();
        // Create the Dependant
        DependantDTO dependantDTO = dependantMapper.toDto(dependant);
        restDependantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dependantDTO)))
            .andExpect(status().isCreated());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeCreate + 1);
        Dependant testDependant = dependantList.get(dependantList.size() - 1);
        assertThat(testDependant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDependant.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testDependant.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testDependant.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDependant.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testDependant.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDependant.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testDependant.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testDependant.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDependant.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDependant.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testDependant.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testDependant.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testDependant.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testDependant.getFreefield4()).isEqualTo(DEFAULT_FREEFIELD_4);
        assertThat(testDependant.getFreefield5()).isEqualTo(DEFAULT_FREEFIELD_5);
    }

    @Test
    @Transactional
    void createDependantWithExistingId() throws Exception {
        // Create the Dependant with an existing ID
        dependant.setId(1L);
        DependantDTO dependantDTO = dependantMapper.toDto(dependant);

        int databaseSizeBeforeCreate = dependantRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDependantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dependantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDependants() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList
        restDependantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
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
    void getDependant() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get the dependant
        restDependantMockMvc
            .perform(get(ENTITY_API_URL_ID, dependant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dependant.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
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
    void getDependantsByIdFiltering() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        Long id = dependant.getId();

        defaultDependantShouldBeFound("id.equals=" + id);
        defaultDependantShouldNotBeFound("id.notEquals=" + id);

        defaultDependantShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDependantShouldNotBeFound("id.greaterThan=" + id);

        defaultDependantShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDependantShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDependantsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where name equals to DEFAULT_NAME
        defaultDependantShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the dependantList where name equals to UPDATED_NAME
        defaultDependantShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDependantsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDependantShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the dependantList where name equals to UPDATED_NAME
        defaultDependantShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDependantsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where name is not null
        defaultDependantShouldBeFound("name.specified=true");

        // Get all the dependantList where name is null
        defaultDependantShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByNameContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where name contains DEFAULT_NAME
        defaultDependantShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the dependantList where name contains UPDATED_NAME
        defaultDependantShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDependantsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where name does not contain DEFAULT_NAME
        defaultDependantShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the dependantList where name does not contain UPDATED_NAME
        defaultDependantShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllDependantsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where age equals to DEFAULT_AGE
        defaultDependantShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the dependantList where age equals to UPDATED_AGE
        defaultDependantShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllDependantsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where age in DEFAULT_AGE or UPDATED_AGE
        defaultDependantShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the dependantList where age equals to UPDATED_AGE
        defaultDependantShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllDependantsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where age is not null
        defaultDependantShouldBeFound("age.specified=true");

        // Get all the dependantList where age is null
        defaultDependantShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByAgeContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where age contains DEFAULT_AGE
        defaultDependantShouldBeFound("age.contains=" + DEFAULT_AGE);

        // Get all the dependantList where age contains UPDATED_AGE
        defaultDependantShouldNotBeFound("age.contains=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllDependantsByAgeNotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where age does not contain DEFAULT_AGE
        defaultDependantShouldNotBeFound("age.doesNotContain=" + DEFAULT_AGE);

        // Get all the dependantList where age does not contain UPDATED_AGE
        defaultDependantShouldBeFound("age.doesNotContain=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    void getAllDependantsByDateOfBirthIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where dateOfBirth equals to DEFAULT_DATE_OF_BIRTH
        defaultDependantShouldBeFound("dateOfBirth.equals=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the dependantList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultDependantShouldNotBeFound("dateOfBirth.equals=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllDependantsByDateOfBirthIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where dateOfBirth in DEFAULT_DATE_OF_BIRTH or UPDATED_DATE_OF_BIRTH
        defaultDependantShouldBeFound("dateOfBirth.in=" + DEFAULT_DATE_OF_BIRTH + "," + UPDATED_DATE_OF_BIRTH);

        // Get all the dependantList where dateOfBirth equals to UPDATED_DATE_OF_BIRTH
        defaultDependantShouldNotBeFound("dateOfBirth.in=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllDependantsByDateOfBirthIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where dateOfBirth is not null
        defaultDependantShouldBeFound("dateOfBirth.specified=true");

        // Get all the dependantList where dateOfBirth is null
        defaultDependantShouldNotBeFound("dateOfBirth.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByDateOfBirthContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where dateOfBirth contains DEFAULT_DATE_OF_BIRTH
        defaultDependantShouldBeFound("dateOfBirth.contains=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the dependantList where dateOfBirth contains UPDATED_DATE_OF_BIRTH
        defaultDependantShouldNotBeFound("dateOfBirth.contains=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllDependantsByDateOfBirthNotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where dateOfBirth does not contain DEFAULT_DATE_OF_BIRTH
        defaultDependantShouldNotBeFound("dateOfBirth.doesNotContain=" + DEFAULT_DATE_OF_BIRTH);

        // Get all the dependantList where dateOfBirth does not contain UPDATED_DATE_OF_BIRTH
        defaultDependantShouldBeFound("dateOfBirth.doesNotContain=" + UPDATED_DATE_OF_BIRTH);
    }

    @Test
    @Transactional
    void getAllDependantsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where type equals to DEFAULT_TYPE
        defaultDependantShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the dependantList where type equals to UPDATED_TYPE
        defaultDependantShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDependantsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDependantShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the dependantList where type equals to UPDATED_TYPE
        defaultDependantShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllDependantsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where type is not null
        defaultDependantShouldBeFound("type.specified=true");

        // Get all the dependantList where type is null
        defaultDependantShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByMobileIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where mobile equals to DEFAULT_MOBILE
        defaultDependantShouldBeFound("mobile.equals=" + DEFAULT_MOBILE);

        // Get all the dependantList where mobile equals to UPDATED_MOBILE
        defaultDependantShouldNotBeFound("mobile.equals=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllDependantsByMobileIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where mobile in DEFAULT_MOBILE or UPDATED_MOBILE
        defaultDependantShouldBeFound("mobile.in=" + DEFAULT_MOBILE + "," + UPDATED_MOBILE);

        // Get all the dependantList where mobile equals to UPDATED_MOBILE
        defaultDependantShouldNotBeFound("mobile.in=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllDependantsByMobileIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where mobile is not null
        defaultDependantShouldBeFound("mobile.specified=true");

        // Get all the dependantList where mobile is null
        defaultDependantShouldNotBeFound("mobile.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByMobileContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where mobile contains DEFAULT_MOBILE
        defaultDependantShouldBeFound("mobile.contains=" + DEFAULT_MOBILE);

        // Get all the dependantList where mobile contains UPDATED_MOBILE
        defaultDependantShouldNotBeFound("mobile.contains=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllDependantsByMobileNotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where mobile does not contain DEFAULT_MOBILE
        defaultDependantShouldNotBeFound("mobile.doesNotContain=" + DEFAULT_MOBILE);

        // Get all the dependantList where mobile does not contain UPDATED_MOBILE
        defaultDependantShouldBeFound("mobile.doesNotContain=" + UPDATED_MOBILE);
    }

    @Test
    @Transactional
    void getAllDependantsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where address equals to DEFAULT_ADDRESS
        defaultDependantShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the dependantList where address equals to UPDATED_ADDRESS
        defaultDependantShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDependantsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultDependantShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the dependantList where address equals to UPDATED_ADDRESS
        defaultDependantShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDependantsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where address is not null
        defaultDependantShouldBeFound("address.specified=true");

        // Get all the dependantList where address is null
        defaultDependantShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByAddressContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where address contains DEFAULT_ADDRESS
        defaultDependantShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the dependantList where address contains UPDATED_ADDRESS
        defaultDependantShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDependantsByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where address does not contain DEFAULT_ADDRESS
        defaultDependantShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the dependantList where address does not contain UPDATED_ADDRESS
        defaultDependantShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDependantsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultDependantShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the dependantList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDependantShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDependantsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultDependantShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the dependantList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultDependantShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllDependantsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where lastModified is not null
        defaultDependantShouldBeFound("lastModified.specified=true");

        // Get all the dependantList where lastModified is null
        defaultDependantShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultDependantShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the dependantList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDependantShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDependantsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultDependantShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the dependantList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultDependantShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDependantsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where lastModifiedBy is not null
        defaultDependantShouldBeFound("lastModifiedBy.specified=true");

        // Get all the dependantList where lastModifiedBy is null
        defaultDependantShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultDependantShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the dependantList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultDependantShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDependantsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultDependantShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the dependantList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultDependantShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllDependantsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where createdBy equals to DEFAULT_CREATED_BY
        defaultDependantShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the dependantList where createdBy equals to UPDATED_CREATED_BY
        defaultDependantShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDependantsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultDependantShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the dependantList where createdBy equals to UPDATED_CREATED_BY
        defaultDependantShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDependantsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where createdBy is not null
        defaultDependantShouldBeFound("createdBy.specified=true");

        // Get all the dependantList where createdBy is null
        defaultDependantShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where createdBy contains DEFAULT_CREATED_BY
        defaultDependantShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the dependantList where createdBy contains UPDATED_CREATED_BY
        defaultDependantShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDependantsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where createdBy does not contain DEFAULT_CREATED_BY
        defaultDependantShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the dependantList where createdBy does not contain UPDATED_CREATED_BY
        defaultDependantShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllDependantsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where createdOn equals to DEFAULT_CREATED_ON
        defaultDependantShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the dependantList where createdOn equals to UPDATED_CREATED_ON
        defaultDependantShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllDependantsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultDependantShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the dependantList where createdOn equals to UPDATED_CREATED_ON
        defaultDependantShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllDependantsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where createdOn is not null
        defaultDependantShouldBeFound("createdOn.specified=true");

        // Get all the dependantList where createdOn is null
        defaultDependantShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where isDeleted equals to DEFAULT_IS_DELETED
        defaultDependantShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the dependantList where isDeleted equals to UPDATED_IS_DELETED
        defaultDependantShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllDependantsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultDependantShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the dependantList where isDeleted equals to UPDATED_IS_DELETED
        defaultDependantShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllDependantsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where isDeleted is not null
        defaultDependantShouldBeFound("isDeleted.specified=true");

        // Get all the dependantList where isDeleted is null
        defaultDependantShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultDependantShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the dependantList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultDependantShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultDependantShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the dependantList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultDependantShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField1 is not null
        defaultDependantShouldBeFound("freeField1.specified=true");

        // Get all the dependantList where freeField1 is null
        defaultDependantShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultDependantShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the dependantList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultDependantShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultDependantShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the dependantList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultDependantShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultDependantShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the dependantList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultDependantShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultDependantShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the dependantList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultDependantShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField2 is not null
        defaultDependantShouldBeFound("freeField2.specified=true");

        // Get all the dependantList where freeField2 is null
        defaultDependantShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultDependantShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the dependantList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultDependantShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultDependantShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the dependantList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultDependantShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultDependantShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the dependantList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultDependantShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultDependantShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the dependantList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultDependantShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField3 is not null
        defaultDependantShouldBeFound("freeField3.specified=true");

        // Get all the dependantList where freeField3 is null
        defaultDependantShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultDependantShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the dependantList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultDependantShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDependantsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultDependantShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the dependantList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultDependantShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllDependantsByFreefield4IsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freefield4 equals to DEFAULT_FREEFIELD_4
        defaultDependantShouldBeFound("freefield4.equals=" + DEFAULT_FREEFIELD_4);

        // Get all the dependantList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultDependantShouldNotBeFound("freefield4.equals=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllDependantsByFreefield4IsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freefield4 in DEFAULT_FREEFIELD_4 or UPDATED_FREEFIELD_4
        defaultDependantShouldBeFound("freefield4.in=" + DEFAULT_FREEFIELD_4 + "," + UPDATED_FREEFIELD_4);

        // Get all the dependantList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultDependantShouldNotBeFound("freefield4.in=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllDependantsByFreefield4IsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freefield4 is not null
        defaultDependantShouldBeFound("freefield4.specified=true");

        // Get all the dependantList where freefield4 is null
        defaultDependantShouldNotBeFound("freefield4.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByFreefield4ContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freefield4 contains DEFAULT_FREEFIELD_4
        defaultDependantShouldBeFound("freefield4.contains=" + DEFAULT_FREEFIELD_4);

        // Get all the dependantList where freefield4 contains UPDATED_FREEFIELD_4
        defaultDependantShouldNotBeFound("freefield4.contains=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllDependantsByFreefield4NotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freefield4 does not contain DEFAULT_FREEFIELD_4
        defaultDependantShouldNotBeFound("freefield4.doesNotContain=" + DEFAULT_FREEFIELD_4);

        // Get all the dependantList where freefield4 does not contain UPDATED_FREEFIELD_4
        defaultDependantShouldBeFound("freefield4.doesNotContain=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllDependantsByFreefield5IsEqualToSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freefield5 equals to DEFAULT_FREEFIELD_5
        defaultDependantShouldBeFound("freefield5.equals=" + DEFAULT_FREEFIELD_5);

        // Get all the dependantList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultDependantShouldNotBeFound("freefield5.equals=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllDependantsByFreefield5IsInShouldWork() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freefield5 in DEFAULT_FREEFIELD_5 or UPDATED_FREEFIELD_5
        defaultDependantShouldBeFound("freefield5.in=" + DEFAULT_FREEFIELD_5 + "," + UPDATED_FREEFIELD_5);

        // Get all the dependantList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultDependantShouldNotBeFound("freefield5.in=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllDependantsByFreefield5IsNullOrNotNull() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freefield5 is not null
        defaultDependantShouldBeFound("freefield5.specified=true");

        // Get all the dependantList where freefield5 is null
        defaultDependantShouldNotBeFound("freefield5.specified=false");
    }

    @Test
    @Transactional
    void getAllDependantsByFreefield5ContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freefield5 contains DEFAULT_FREEFIELD_5
        defaultDependantShouldBeFound("freefield5.contains=" + DEFAULT_FREEFIELD_5);

        // Get all the dependantList where freefield5 contains UPDATED_FREEFIELD_5
        defaultDependantShouldNotBeFound("freefield5.contains=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllDependantsByFreefield5NotContainsSomething() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        // Get all the dependantList where freefield5 does not contain DEFAULT_FREEFIELD_5
        defaultDependantShouldNotBeFound("freefield5.doesNotContain=" + DEFAULT_FREEFIELD_5);

        // Get all the dependantList where freefield5 does not contain UPDATED_FREEFIELD_5
        defaultDependantShouldBeFound("freefield5.doesNotContain=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllDependantsByEmployeeIsEqualToSomething() throws Exception {
        Employee employee;
        if (TestUtil.findAll(em, Employee.class).isEmpty()) {
            dependantRepository.saveAndFlush(dependant);
            employee = EmployeeResourceIT.createEntity(em);
        } else {
            employee = TestUtil.findAll(em, Employee.class).get(0);
        }
        em.persist(employee);
        em.flush();
        dependant.setEmployee(employee);
        dependantRepository.saveAndFlush(dependant);
        Long employeeId = employee.getId();

        // Get all the dependantList where employee equals to employeeId
        defaultDependantShouldBeFound("employeeId.equals=" + employeeId);

        // Get all the dependantList where employee equals to (employeeId + 1)
        defaultDependantShouldNotBeFound("employeeId.equals=" + (employeeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDependantShouldBeFound(String filter) throws Exception {
        restDependantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dependant.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
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
        restDependantMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDependantShouldNotBeFound(String filter) throws Exception {
        restDependantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDependantMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDependant() throws Exception {
        // Get the dependant
        restDependantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDependant() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        int databaseSizeBeforeUpdate = dependantRepository.findAll().size();

        // Update the dependant
        Dependant updatedDependant = dependantRepository.findById(dependant.getId()).get();
        // Disconnect from session so that the updates on updatedDependant are not directly saved in db
        em.detach(updatedDependant);
        updatedDependant
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .type(UPDATED_TYPE)
            .mobile(UPDATED_MOBILE)
            .address(UPDATED_ADDRESS)
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
        DependantDTO dependantDTO = dependantMapper.toDto(updatedDependant);

        restDependantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dependantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dependantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeUpdate);
        Dependant testDependant = dependantList.get(dependantList.size() - 1);
        assertThat(testDependant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDependant.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testDependant.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testDependant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDependant.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testDependant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDependant.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDependant.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDependant.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDependant.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDependant.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDependant.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testDependant.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testDependant.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testDependant.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testDependant.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingDependant() throws Exception {
        int databaseSizeBeforeUpdate = dependantRepository.findAll().size();
        dependant.setId(count.incrementAndGet());

        // Create the Dependant
        DependantDTO dependantDTO = dependantMapper.toDto(dependant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDependantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dependantDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dependantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDependant() throws Exception {
        int databaseSizeBeforeUpdate = dependantRepository.findAll().size();
        dependant.setId(count.incrementAndGet());

        // Create the Dependant
        DependantDTO dependantDTO = dependantMapper.toDto(dependant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDependantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dependantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDependant() throws Exception {
        int databaseSizeBeforeUpdate = dependantRepository.findAll().size();
        dependant.setId(count.incrementAndGet());

        // Create the Dependant
        DependantDTO dependantDTO = dependantMapper.toDto(dependant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDependantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dependantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDependantWithPatch() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        int databaseSizeBeforeUpdate = dependantRepository.findAll().size();

        // Update the dependant using partial update
        Dependant partialUpdatedDependant = new Dependant();
        partialUpdatedDependant.setId(dependant.getId());

        partialUpdatedDependant
            .address(UPDATED_ADDRESS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freefield4(UPDATED_FREEFIELD_4);

        restDependantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDependant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDependant))
            )
            .andExpect(status().isOk());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeUpdate);
        Dependant testDependant = dependantList.get(dependantList.size() - 1);
        assertThat(testDependant.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDependant.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testDependant.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testDependant.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDependant.getMobile()).isEqualTo(DEFAULT_MOBILE);
        assertThat(testDependant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDependant.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDependant.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testDependant.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDependant.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testDependant.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDependant.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testDependant.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testDependant.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testDependant.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testDependant.getFreefield5()).isEqualTo(DEFAULT_FREEFIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateDependantWithPatch() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        int databaseSizeBeforeUpdate = dependantRepository.findAll().size();

        // Update the dependant using partial update
        Dependant partialUpdatedDependant = new Dependant();
        partialUpdatedDependant.setId(dependant.getId());

        partialUpdatedDependant
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .type(UPDATED_TYPE)
            .mobile(UPDATED_MOBILE)
            .address(UPDATED_ADDRESS)
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

        restDependantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDependant.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDependant))
            )
            .andExpect(status().isOk());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeUpdate);
        Dependant testDependant = dependantList.get(dependantList.size() - 1);
        assertThat(testDependant.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDependant.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testDependant.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testDependant.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDependant.getMobile()).isEqualTo(UPDATED_MOBILE);
        assertThat(testDependant.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDependant.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testDependant.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testDependant.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDependant.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testDependant.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testDependant.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testDependant.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testDependant.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testDependant.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testDependant.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingDependant() throws Exception {
        int databaseSizeBeforeUpdate = dependantRepository.findAll().size();
        dependant.setId(count.incrementAndGet());

        // Create the Dependant
        DependantDTO dependantDTO = dependantMapper.toDto(dependant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDependantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dependantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dependantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDependant() throws Exception {
        int databaseSizeBeforeUpdate = dependantRepository.findAll().size();
        dependant.setId(count.incrementAndGet());

        // Create the Dependant
        DependantDTO dependantDTO = dependantMapper.toDto(dependant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDependantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dependantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDependant() throws Exception {
        int databaseSizeBeforeUpdate = dependantRepository.findAll().size();
        dependant.setId(count.incrementAndGet());

        // Create the Dependant
        DependantDTO dependantDTO = dependantMapper.toDto(dependant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDependantMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dependantDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dependant in the database
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDependant() throws Exception {
        // Initialize the database
        dependantRepository.saveAndFlush(dependant);

        int databaseSizeBeforeDelete = dependantRepository.findAll().size();

        // Delete the dependant
        restDependantMockMvc
            .perform(delete(ENTITY_API_URL_ID, dependant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dependant> dependantList = dependantRepository.findAll();
        assertThat(dependantList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
