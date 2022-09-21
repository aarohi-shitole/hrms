package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Address;
import com.techvg.hrms.domain.Organization;
import com.techvg.hrms.domain.Organization;
import com.techvg.hrms.domain.enumeration.OrganizationType;
import com.techvg.hrms.domain.enumeration.Status;
import com.techvg.hrms.repository.OrganizationRepository;
import com.techvg.hrms.service.criteria.OrganizationCriteria;
import com.techvg.hrms.service.dto.OrganizationDTO;
import com.techvg.hrms.service.mapper.OrganizationMapper;
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
 * Integration tests for the {@link OrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationResourceIT {

    private static final String DEFAULT_ORG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORG_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRANCHCODE = "AAAAAAAAAA";
    private static final String UPDATED_BRANCHCODE = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_IFSC_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IFSC_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MICR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MICR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SWIFT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SWIFT_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_IBAN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_IBAN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ROUTING_TRANSIT_NO = "AAAAAAAAAA";
    private static final String UPDATED_ROUTING_TRANSIT_NO = "BBBBBBBBBB";

    private static final Double DEFAULT_REG_NUMBER = 1D;
    private static final Double UPDATED_REG_NUMBER = 2D;
    private static final Double SMALLER_REG_NUMBER = 1D - 1D;

    private static final String DEFAULT_GSTIN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GSTIN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PAN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PAN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TAN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TAN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final OrganizationType DEFAULT_ORG_TYPE = OrganizationType.SOCIETY;
    private static final OrganizationType UPDATED_ORG_TYPE = OrganizationType.CO_SOCIETY;

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_IS_DELETED = "AAAAAAAAAA";
    private static final String UPDATED_IS_DELETED = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.ACTIVE;
    private static final Status UPDATED_STATUS = Status.INACTIVE;

    private static final Instant DEFAULT_LAST_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_4 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_4 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createEntity(EntityManager em) {
        Organization organization = new Organization()
            .orgName(DEFAULT_ORG_NAME)
            .branchcode(DEFAULT_BRANCHCODE)
            .region(DEFAULT_REGION)
            .ifscCode(DEFAULT_IFSC_CODE)
            .micrCode(DEFAULT_MICR_CODE)
            .swiftCode(DEFAULT_SWIFT_CODE)
            .ibanCode(DEFAULT_IBAN_CODE)
            .routingTransitNo(DEFAULT_ROUTING_TRANSIT_NO)
            .regNumber(DEFAULT_REG_NUMBER)
            .gstinNumber(DEFAULT_GSTIN_NUMBER)
            .panNumber(DEFAULT_PAN_NUMBER)
            .tanNumber(DEFAULT_TAN_NUMBER)
            .phone(DEFAULT_PHONE)
            .email(DEFAULT_EMAIL)
            .webSite(DEFAULT_WEB_SITE)
            .fax(DEFAULT_FAX)
            .orgType(DEFAULT_ORG_TYPE)
            .createdOn(DEFAULT_CREATED_ON)
            .createdBy(DEFAULT_CREATED_BY)
            .description(DEFAULT_DESCRIPTION)
            .isDeleted(DEFAULT_IS_DELETED)
            .status(DEFAULT_STATUS)
            .lastModified(DEFAULT_LAST_MODIFIED)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .freeField4(DEFAULT_FREE_FIELD_4);
        return organization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Organization createUpdatedEntity(EntityManager em) {
        Organization organization = new Organization()
            .orgName(UPDATED_ORG_NAME)
            .branchcode(UPDATED_BRANCHCODE)
            .region(UPDATED_REGION)
            .ifscCode(UPDATED_IFSC_CODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .ibanCode(UPDATED_IBAN_CODE)
            .routingTransitNo(UPDATED_ROUTING_TRANSIT_NO)
            .regNumber(UPDATED_REG_NUMBER)
            .gstinNumber(UPDATED_GSTIN_NUMBER)
            .panNumber(UPDATED_PAN_NUMBER)
            .tanNumber(UPDATED_TAN_NUMBER)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .webSite(UPDATED_WEB_SITE)
            .fax(UPDATED_FAX)
            .orgType(UPDATED_ORG_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .description(UPDATED_DESCRIPTION)
            .isDeleted(UPDATED_IS_DELETED)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        return organization;
    }

    @BeforeEach
    public void initTest() {
        organization = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();
        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);
        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrgName()).isEqualTo(DEFAULT_ORG_NAME);
        assertThat(testOrganization.getBranchcode()).isEqualTo(DEFAULT_BRANCHCODE);
        assertThat(testOrganization.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testOrganization.getIfscCode()).isEqualTo(DEFAULT_IFSC_CODE);
        assertThat(testOrganization.getMicrCode()).isEqualTo(DEFAULT_MICR_CODE);
        assertThat(testOrganization.getSwiftCode()).isEqualTo(DEFAULT_SWIFT_CODE);
        assertThat(testOrganization.getIbanCode()).isEqualTo(DEFAULT_IBAN_CODE);
        assertThat(testOrganization.getRoutingTransitNo()).isEqualTo(DEFAULT_ROUTING_TRANSIT_NO);
        assertThat(testOrganization.getRegNumber()).isEqualTo(DEFAULT_REG_NUMBER);
        assertThat(testOrganization.getGstinNumber()).isEqualTo(DEFAULT_GSTIN_NUMBER);
        assertThat(testOrganization.getPanNumber()).isEqualTo(DEFAULT_PAN_NUMBER);
        assertThat(testOrganization.getTanNumber()).isEqualTo(DEFAULT_TAN_NUMBER);
        assertThat(testOrganization.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOrganization.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganization.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testOrganization.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testOrganization.getOrgType()).isEqualTo(DEFAULT_ORG_TYPE);
        assertThat(testOrganization.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testOrganization.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrganization.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganization.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testOrganization.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testOrganization.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testOrganization.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOrganization.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testOrganization.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testOrganization.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testOrganization.getFreeField4()).isEqualTo(DEFAULT_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void createOrganizationWithExistingId() throws Exception {
        // Create the Organization with an existing ID
        organization.setId(1L);
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkOrgNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationRepository.findAll().size();
        // set the field null
        organization.setOrgName(null);

        // Create the Organization, which fails.
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        restOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)))
            .andExpect(jsonPath("$.[*].branchcode").value(hasItem(DEFAULT_BRANCHCODE)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE)))
            .andExpect(jsonPath("$.[*].swiftCode").value(hasItem(DEFAULT_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].ibanCode").value(hasItem(DEFAULT_IBAN_CODE)))
            .andExpect(jsonPath("$.[*].routingTransitNo").value(hasItem(DEFAULT_ROUTING_TRANSIT_NO)))
            .andExpect(jsonPath("$.[*].regNumber").value(hasItem(DEFAULT_REG_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].gstinNumber").value(hasItem(DEFAULT_GSTIN_NUMBER)))
            .andExpect(jsonPath("$.[*].panNumber").value(hasItem(DEFAULT_PAN_NUMBER)))
            .andExpect(jsonPath("$.[*].tanNumber").value(hasItem(DEFAULT_TAN_NUMBER)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].orgType").value(hasItem(DEFAULT_ORG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));
    }

    @Test
    @Transactional
    void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.orgName").value(DEFAULT_ORG_NAME))
            .andExpect(jsonPath("$.branchcode").value(DEFAULT_BRANCHCODE))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.ifscCode").value(DEFAULT_IFSC_CODE))
            .andExpect(jsonPath("$.micrCode").value(DEFAULT_MICR_CODE))
            .andExpect(jsonPath("$.swiftCode").value(DEFAULT_SWIFT_CODE))
            .andExpect(jsonPath("$.ibanCode").value(DEFAULT_IBAN_CODE))
            .andExpect(jsonPath("$.routingTransitNo").value(DEFAULT_ROUTING_TRANSIT_NO))
            .andExpect(jsonPath("$.regNumber").value(DEFAULT_REG_NUMBER.doubleValue()))
            .andExpect(jsonPath("$.gstinNumber").value(DEFAULT_GSTIN_NUMBER))
            .andExpect(jsonPath("$.panNumber").value(DEFAULT_PAN_NUMBER))
            .andExpect(jsonPath("$.tanNumber").value(DEFAULT_TAN_NUMBER))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX))
            .andExpect(jsonPath("$.orgType").value(DEFAULT_ORG_TYPE.toString()))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.lastModified").value(DEFAULT_LAST_MODIFIED.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.freeField4").value(DEFAULT_FREE_FIELD_4));
    }

    @Test
    @Transactional
    void getOrganizationsByIdFiltering() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        Long id = organization.getId();

        defaultOrganizationShouldBeFound("id.equals=" + id);
        defaultOrganizationShouldNotBeFound("id.notEquals=" + id);

        defaultOrganizationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrganizationShouldNotBeFound("id.greaterThan=" + id);

        defaultOrganizationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrganizationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrgNameIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgName equals to DEFAULT_ORG_NAME
        defaultOrganizationShouldBeFound("orgName.equals=" + DEFAULT_ORG_NAME);

        // Get all the organizationList where orgName equals to UPDATED_ORG_NAME
        defaultOrganizationShouldNotBeFound("orgName.equals=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrgNameIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgName in DEFAULT_ORG_NAME or UPDATED_ORG_NAME
        defaultOrganizationShouldBeFound("orgName.in=" + DEFAULT_ORG_NAME + "," + UPDATED_ORG_NAME);

        // Get all the organizationList where orgName equals to UPDATED_ORG_NAME
        defaultOrganizationShouldNotBeFound("orgName.in=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrgNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgName is not null
        defaultOrganizationShouldBeFound("orgName.specified=true");

        // Get all the organizationList where orgName is null
        defaultOrganizationShouldNotBeFound("orgName.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrgNameContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgName contains DEFAULT_ORG_NAME
        defaultOrganizationShouldBeFound("orgName.contains=" + DEFAULT_ORG_NAME);

        // Get all the organizationList where orgName contains UPDATED_ORG_NAME
        defaultOrganizationShouldNotBeFound("orgName.contains=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrgNameNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgName does not contain DEFAULT_ORG_NAME
        defaultOrganizationShouldNotBeFound("orgName.doesNotContain=" + DEFAULT_ORG_NAME);

        // Get all the organizationList where orgName does not contain UPDATED_ORG_NAME
        defaultOrganizationShouldBeFound("orgName.doesNotContain=" + UPDATED_ORG_NAME);
    }

    @Test
    @Transactional
    void getAllOrganizationsByBranchcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where branchcode equals to DEFAULT_BRANCHCODE
        defaultOrganizationShouldBeFound("branchcode.equals=" + DEFAULT_BRANCHCODE);

        // Get all the organizationList where branchcode equals to UPDATED_BRANCHCODE
        defaultOrganizationShouldNotBeFound("branchcode.equals=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByBranchcodeIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where branchcode in DEFAULT_BRANCHCODE or UPDATED_BRANCHCODE
        defaultOrganizationShouldBeFound("branchcode.in=" + DEFAULT_BRANCHCODE + "," + UPDATED_BRANCHCODE);

        // Get all the organizationList where branchcode equals to UPDATED_BRANCHCODE
        defaultOrganizationShouldNotBeFound("branchcode.in=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByBranchcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where branchcode is not null
        defaultOrganizationShouldBeFound("branchcode.specified=true");

        // Get all the organizationList where branchcode is null
        defaultOrganizationShouldNotBeFound("branchcode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByBranchcodeContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where branchcode contains DEFAULT_BRANCHCODE
        defaultOrganizationShouldBeFound("branchcode.contains=" + DEFAULT_BRANCHCODE);

        // Get all the organizationList where branchcode contains UPDATED_BRANCHCODE
        defaultOrganizationShouldNotBeFound("branchcode.contains=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByBranchcodeNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where branchcode does not contain DEFAULT_BRANCHCODE
        defaultOrganizationShouldNotBeFound("branchcode.doesNotContain=" + DEFAULT_BRANCHCODE);

        // Get all the organizationList where branchcode does not contain UPDATED_BRANCHCODE
        defaultOrganizationShouldBeFound("branchcode.doesNotContain=" + UPDATED_BRANCHCODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegionIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where region equals to DEFAULT_REGION
        defaultOrganizationShouldBeFound("region.equals=" + DEFAULT_REGION);

        // Get all the organizationList where region equals to UPDATED_REGION
        defaultOrganizationShouldNotBeFound("region.equals=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegionIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where region in DEFAULT_REGION or UPDATED_REGION
        defaultOrganizationShouldBeFound("region.in=" + DEFAULT_REGION + "," + UPDATED_REGION);

        // Get all the organizationList where region equals to UPDATED_REGION
        defaultOrganizationShouldNotBeFound("region.in=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegionIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where region is not null
        defaultOrganizationShouldBeFound("region.specified=true");

        // Get all the organizationList where region is null
        defaultOrganizationShouldNotBeFound("region.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegionContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where region contains DEFAULT_REGION
        defaultOrganizationShouldBeFound("region.contains=" + DEFAULT_REGION);

        // Get all the organizationList where region contains UPDATED_REGION
        defaultOrganizationShouldNotBeFound("region.contains=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegionNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where region does not contain DEFAULT_REGION
        defaultOrganizationShouldNotBeFound("region.doesNotContain=" + DEFAULT_REGION);

        // Get all the organizationList where region does not contain UPDATED_REGION
        defaultOrganizationShouldBeFound("region.doesNotContain=" + UPDATED_REGION);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIfscCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where ifscCode equals to DEFAULT_IFSC_CODE
        defaultOrganizationShouldBeFound("ifscCode.equals=" + DEFAULT_IFSC_CODE);

        // Get all the organizationList where ifscCode equals to UPDATED_IFSC_CODE
        defaultOrganizationShouldNotBeFound("ifscCode.equals=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIfscCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where ifscCode in DEFAULT_IFSC_CODE or UPDATED_IFSC_CODE
        defaultOrganizationShouldBeFound("ifscCode.in=" + DEFAULT_IFSC_CODE + "," + UPDATED_IFSC_CODE);

        // Get all the organizationList where ifscCode equals to UPDATED_IFSC_CODE
        defaultOrganizationShouldNotBeFound("ifscCode.in=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIfscCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where ifscCode is not null
        defaultOrganizationShouldBeFound("ifscCode.specified=true");

        // Get all the organizationList where ifscCode is null
        defaultOrganizationShouldNotBeFound("ifscCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByIfscCodeContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where ifscCode contains DEFAULT_IFSC_CODE
        defaultOrganizationShouldBeFound("ifscCode.contains=" + DEFAULT_IFSC_CODE);

        // Get all the organizationList where ifscCode contains UPDATED_IFSC_CODE
        defaultOrganizationShouldNotBeFound("ifscCode.contains=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIfscCodeNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where ifscCode does not contain DEFAULT_IFSC_CODE
        defaultOrganizationShouldNotBeFound("ifscCode.doesNotContain=" + DEFAULT_IFSC_CODE);

        // Get all the organizationList where ifscCode does not contain UPDATED_IFSC_CODE
        defaultOrganizationShouldBeFound("ifscCode.doesNotContain=" + UPDATED_IFSC_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByMicrCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where micrCode equals to DEFAULT_MICR_CODE
        defaultOrganizationShouldBeFound("micrCode.equals=" + DEFAULT_MICR_CODE);

        // Get all the organizationList where micrCode equals to UPDATED_MICR_CODE
        defaultOrganizationShouldNotBeFound("micrCode.equals=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByMicrCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where micrCode in DEFAULT_MICR_CODE or UPDATED_MICR_CODE
        defaultOrganizationShouldBeFound("micrCode.in=" + DEFAULT_MICR_CODE + "," + UPDATED_MICR_CODE);

        // Get all the organizationList where micrCode equals to UPDATED_MICR_CODE
        defaultOrganizationShouldNotBeFound("micrCode.in=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByMicrCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where micrCode is not null
        defaultOrganizationShouldBeFound("micrCode.specified=true");

        // Get all the organizationList where micrCode is null
        defaultOrganizationShouldNotBeFound("micrCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByMicrCodeContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where micrCode contains DEFAULT_MICR_CODE
        defaultOrganizationShouldBeFound("micrCode.contains=" + DEFAULT_MICR_CODE);

        // Get all the organizationList where micrCode contains UPDATED_MICR_CODE
        defaultOrganizationShouldNotBeFound("micrCode.contains=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByMicrCodeNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where micrCode does not contain DEFAULT_MICR_CODE
        defaultOrganizationShouldNotBeFound("micrCode.doesNotContain=" + DEFAULT_MICR_CODE);

        // Get all the organizationList where micrCode does not contain UPDATED_MICR_CODE
        defaultOrganizationShouldBeFound("micrCode.doesNotContain=" + UPDATED_MICR_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsBySwiftCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where swiftCode equals to DEFAULT_SWIFT_CODE
        defaultOrganizationShouldBeFound("swiftCode.equals=" + DEFAULT_SWIFT_CODE);

        // Get all the organizationList where swiftCode equals to UPDATED_SWIFT_CODE
        defaultOrganizationShouldNotBeFound("swiftCode.equals=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsBySwiftCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where swiftCode in DEFAULT_SWIFT_CODE or UPDATED_SWIFT_CODE
        defaultOrganizationShouldBeFound("swiftCode.in=" + DEFAULT_SWIFT_CODE + "," + UPDATED_SWIFT_CODE);

        // Get all the organizationList where swiftCode equals to UPDATED_SWIFT_CODE
        defaultOrganizationShouldNotBeFound("swiftCode.in=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsBySwiftCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where swiftCode is not null
        defaultOrganizationShouldBeFound("swiftCode.specified=true");

        // Get all the organizationList where swiftCode is null
        defaultOrganizationShouldNotBeFound("swiftCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsBySwiftCodeContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where swiftCode contains DEFAULT_SWIFT_CODE
        defaultOrganizationShouldBeFound("swiftCode.contains=" + DEFAULT_SWIFT_CODE);

        // Get all the organizationList where swiftCode contains UPDATED_SWIFT_CODE
        defaultOrganizationShouldNotBeFound("swiftCode.contains=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsBySwiftCodeNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where swiftCode does not contain DEFAULT_SWIFT_CODE
        defaultOrganizationShouldNotBeFound("swiftCode.doesNotContain=" + DEFAULT_SWIFT_CODE);

        // Get all the organizationList where swiftCode does not contain UPDATED_SWIFT_CODE
        defaultOrganizationShouldBeFound("swiftCode.doesNotContain=" + UPDATED_SWIFT_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIbanCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where ibanCode equals to DEFAULT_IBAN_CODE
        defaultOrganizationShouldBeFound("ibanCode.equals=" + DEFAULT_IBAN_CODE);

        // Get all the organizationList where ibanCode equals to UPDATED_IBAN_CODE
        defaultOrganizationShouldNotBeFound("ibanCode.equals=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIbanCodeIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where ibanCode in DEFAULT_IBAN_CODE or UPDATED_IBAN_CODE
        defaultOrganizationShouldBeFound("ibanCode.in=" + DEFAULT_IBAN_CODE + "," + UPDATED_IBAN_CODE);

        // Get all the organizationList where ibanCode equals to UPDATED_IBAN_CODE
        defaultOrganizationShouldNotBeFound("ibanCode.in=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIbanCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where ibanCode is not null
        defaultOrganizationShouldBeFound("ibanCode.specified=true");

        // Get all the organizationList where ibanCode is null
        defaultOrganizationShouldNotBeFound("ibanCode.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByIbanCodeContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where ibanCode contains DEFAULT_IBAN_CODE
        defaultOrganizationShouldBeFound("ibanCode.contains=" + DEFAULT_IBAN_CODE);

        // Get all the organizationList where ibanCode contains UPDATED_IBAN_CODE
        defaultOrganizationShouldNotBeFound("ibanCode.contains=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIbanCodeNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where ibanCode does not contain DEFAULT_IBAN_CODE
        defaultOrganizationShouldNotBeFound("ibanCode.doesNotContain=" + DEFAULT_IBAN_CODE);

        // Get all the organizationList where ibanCode does not contain UPDATED_IBAN_CODE
        defaultOrganizationShouldBeFound("ibanCode.doesNotContain=" + UPDATED_IBAN_CODE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRoutingTransitNoIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where routingTransitNo equals to DEFAULT_ROUTING_TRANSIT_NO
        defaultOrganizationShouldBeFound("routingTransitNo.equals=" + DEFAULT_ROUTING_TRANSIT_NO);

        // Get all the organizationList where routingTransitNo equals to UPDATED_ROUTING_TRANSIT_NO
        defaultOrganizationShouldNotBeFound("routingTransitNo.equals=" + UPDATED_ROUTING_TRANSIT_NO);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRoutingTransitNoIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where routingTransitNo in DEFAULT_ROUTING_TRANSIT_NO or UPDATED_ROUTING_TRANSIT_NO
        defaultOrganizationShouldBeFound("routingTransitNo.in=" + DEFAULT_ROUTING_TRANSIT_NO + "," + UPDATED_ROUTING_TRANSIT_NO);

        // Get all the organizationList where routingTransitNo equals to UPDATED_ROUTING_TRANSIT_NO
        defaultOrganizationShouldNotBeFound("routingTransitNo.in=" + UPDATED_ROUTING_TRANSIT_NO);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRoutingTransitNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where routingTransitNo is not null
        defaultOrganizationShouldBeFound("routingTransitNo.specified=true");

        // Get all the organizationList where routingTransitNo is null
        defaultOrganizationShouldNotBeFound("routingTransitNo.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByRoutingTransitNoContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where routingTransitNo contains DEFAULT_ROUTING_TRANSIT_NO
        defaultOrganizationShouldBeFound("routingTransitNo.contains=" + DEFAULT_ROUTING_TRANSIT_NO);

        // Get all the organizationList where routingTransitNo contains UPDATED_ROUTING_TRANSIT_NO
        defaultOrganizationShouldNotBeFound("routingTransitNo.contains=" + UPDATED_ROUTING_TRANSIT_NO);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRoutingTransitNoNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where routingTransitNo does not contain DEFAULT_ROUTING_TRANSIT_NO
        defaultOrganizationShouldNotBeFound("routingTransitNo.doesNotContain=" + DEFAULT_ROUTING_TRANSIT_NO);

        // Get all the organizationList where routingTransitNo does not contain UPDATED_ROUTING_TRANSIT_NO
        defaultOrganizationShouldBeFound("routingTransitNo.doesNotContain=" + UPDATED_ROUTING_TRANSIT_NO);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where regNumber equals to DEFAULT_REG_NUMBER
        defaultOrganizationShouldBeFound("regNumber.equals=" + DEFAULT_REG_NUMBER);

        // Get all the organizationList where regNumber equals to UPDATED_REG_NUMBER
        defaultOrganizationShouldNotBeFound("regNumber.equals=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegNumberIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where regNumber in DEFAULT_REG_NUMBER or UPDATED_REG_NUMBER
        defaultOrganizationShouldBeFound("regNumber.in=" + DEFAULT_REG_NUMBER + "," + UPDATED_REG_NUMBER);

        // Get all the organizationList where regNumber equals to UPDATED_REG_NUMBER
        defaultOrganizationShouldNotBeFound("regNumber.in=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where regNumber is not null
        defaultOrganizationShouldBeFound("regNumber.specified=true");

        // Get all the organizationList where regNumber is null
        defaultOrganizationShouldNotBeFound("regNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where regNumber is greater than or equal to DEFAULT_REG_NUMBER
        defaultOrganizationShouldBeFound("regNumber.greaterThanOrEqual=" + DEFAULT_REG_NUMBER);

        // Get all the organizationList where regNumber is greater than or equal to UPDATED_REG_NUMBER
        defaultOrganizationShouldNotBeFound("regNumber.greaterThanOrEqual=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where regNumber is less than or equal to DEFAULT_REG_NUMBER
        defaultOrganizationShouldBeFound("regNumber.lessThanOrEqual=" + DEFAULT_REG_NUMBER);

        // Get all the organizationList where regNumber is less than or equal to SMALLER_REG_NUMBER
        defaultOrganizationShouldNotBeFound("regNumber.lessThanOrEqual=" + SMALLER_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where regNumber is less than DEFAULT_REG_NUMBER
        defaultOrganizationShouldNotBeFound("regNumber.lessThan=" + DEFAULT_REG_NUMBER);

        // Get all the organizationList where regNumber is less than UPDATED_REG_NUMBER
        defaultOrganizationShouldBeFound("regNumber.lessThan=" + UPDATED_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByRegNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where regNumber is greater than DEFAULT_REG_NUMBER
        defaultOrganizationShouldNotBeFound("regNumber.greaterThan=" + DEFAULT_REG_NUMBER);

        // Get all the organizationList where regNumber is greater than SMALLER_REG_NUMBER
        defaultOrganizationShouldBeFound("regNumber.greaterThan=" + SMALLER_REG_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByGstinNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where gstinNumber equals to DEFAULT_GSTIN_NUMBER
        defaultOrganizationShouldBeFound("gstinNumber.equals=" + DEFAULT_GSTIN_NUMBER);

        // Get all the organizationList where gstinNumber equals to UPDATED_GSTIN_NUMBER
        defaultOrganizationShouldNotBeFound("gstinNumber.equals=" + UPDATED_GSTIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByGstinNumberIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where gstinNumber in DEFAULT_GSTIN_NUMBER or UPDATED_GSTIN_NUMBER
        defaultOrganizationShouldBeFound("gstinNumber.in=" + DEFAULT_GSTIN_NUMBER + "," + UPDATED_GSTIN_NUMBER);

        // Get all the organizationList where gstinNumber equals to UPDATED_GSTIN_NUMBER
        defaultOrganizationShouldNotBeFound("gstinNumber.in=" + UPDATED_GSTIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByGstinNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where gstinNumber is not null
        defaultOrganizationShouldBeFound("gstinNumber.specified=true");

        // Get all the organizationList where gstinNumber is null
        defaultOrganizationShouldNotBeFound("gstinNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByGstinNumberContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where gstinNumber contains DEFAULT_GSTIN_NUMBER
        defaultOrganizationShouldBeFound("gstinNumber.contains=" + DEFAULT_GSTIN_NUMBER);

        // Get all the organizationList where gstinNumber contains UPDATED_GSTIN_NUMBER
        defaultOrganizationShouldNotBeFound("gstinNumber.contains=" + UPDATED_GSTIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByGstinNumberNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where gstinNumber does not contain DEFAULT_GSTIN_NUMBER
        defaultOrganizationShouldNotBeFound("gstinNumber.doesNotContain=" + DEFAULT_GSTIN_NUMBER);

        // Get all the organizationList where gstinNumber does not contain UPDATED_GSTIN_NUMBER
        defaultOrganizationShouldBeFound("gstinNumber.doesNotContain=" + UPDATED_GSTIN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPanNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where panNumber equals to DEFAULT_PAN_NUMBER
        defaultOrganizationShouldBeFound("panNumber.equals=" + DEFAULT_PAN_NUMBER);

        // Get all the organizationList where panNumber equals to UPDATED_PAN_NUMBER
        defaultOrganizationShouldNotBeFound("panNumber.equals=" + UPDATED_PAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPanNumberIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where panNumber in DEFAULT_PAN_NUMBER or UPDATED_PAN_NUMBER
        defaultOrganizationShouldBeFound("panNumber.in=" + DEFAULT_PAN_NUMBER + "," + UPDATED_PAN_NUMBER);

        // Get all the organizationList where panNumber equals to UPDATED_PAN_NUMBER
        defaultOrganizationShouldNotBeFound("panNumber.in=" + UPDATED_PAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPanNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where panNumber is not null
        defaultOrganizationShouldBeFound("panNumber.specified=true");

        // Get all the organizationList where panNumber is null
        defaultOrganizationShouldNotBeFound("panNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByPanNumberContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where panNumber contains DEFAULT_PAN_NUMBER
        defaultOrganizationShouldBeFound("panNumber.contains=" + DEFAULT_PAN_NUMBER);

        // Get all the organizationList where panNumber contains UPDATED_PAN_NUMBER
        defaultOrganizationShouldNotBeFound("panNumber.contains=" + UPDATED_PAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPanNumberNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where panNumber does not contain DEFAULT_PAN_NUMBER
        defaultOrganizationShouldNotBeFound("panNumber.doesNotContain=" + DEFAULT_PAN_NUMBER);

        // Get all the organizationList where panNumber does not contain UPDATED_PAN_NUMBER
        defaultOrganizationShouldBeFound("panNumber.doesNotContain=" + UPDATED_PAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByTanNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where tanNumber equals to DEFAULT_TAN_NUMBER
        defaultOrganizationShouldBeFound("tanNumber.equals=" + DEFAULT_TAN_NUMBER);

        // Get all the organizationList where tanNumber equals to UPDATED_TAN_NUMBER
        defaultOrganizationShouldNotBeFound("tanNumber.equals=" + UPDATED_TAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByTanNumberIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where tanNumber in DEFAULT_TAN_NUMBER or UPDATED_TAN_NUMBER
        defaultOrganizationShouldBeFound("tanNumber.in=" + DEFAULT_TAN_NUMBER + "," + UPDATED_TAN_NUMBER);

        // Get all the organizationList where tanNumber equals to UPDATED_TAN_NUMBER
        defaultOrganizationShouldNotBeFound("tanNumber.in=" + UPDATED_TAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByTanNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where tanNumber is not null
        defaultOrganizationShouldBeFound("tanNumber.specified=true");

        // Get all the organizationList where tanNumber is null
        defaultOrganizationShouldNotBeFound("tanNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByTanNumberContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where tanNumber contains DEFAULT_TAN_NUMBER
        defaultOrganizationShouldBeFound("tanNumber.contains=" + DEFAULT_TAN_NUMBER);

        // Get all the organizationList where tanNumber contains UPDATED_TAN_NUMBER
        defaultOrganizationShouldNotBeFound("tanNumber.contains=" + UPDATED_TAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByTanNumberNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where tanNumber does not contain DEFAULT_TAN_NUMBER
        defaultOrganizationShouldNotBeFound("tanNumber.doesNotContain=" + DEFAULT_TAN_NUMBER);

        // Get all the organizationList where tanNumber does not contain UPDATED_TAN_NUMBER
        defaultOrganizationShouldBeFound("tanNumber.doesNotContain=" + UPDATED_TAN_NUMBER);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where phone equals to DEFAULT_PHONE
        defaultOrganizationShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the organizationList where phone equals to UPDATED_PHONE
        defaultOrganizationShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultOrganizationShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the organizationList where phone equals to UPDATED_PHONE
        defaultOrganizationShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where phone is not null
        defaultOrganizationShouldBeFound("phone.specified=true");

        // Get all the organizationList where phone is null
        defaultOrganizationShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where phone contains DEFAULT_PHONE
        defaultOrganizationShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the organizationList where phone contains UPDATED_PHONE
        defaultOrganizationShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where phone does not contain DEFAULT_PHONE
        defaultOrganizationShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the organizationList where phone does not contain UPDATED_PHONE
        defaultOrganizationShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where email equals to DEFAULT_EMAIL
        defaultOrganizationShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the organizationList where email equals to UPDATED_EMAIL
        defaultOrganizationShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultOrganizationShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the organizationList where email equals to UPDATED_EMAIL
        defaultOrganizationShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where email is not null
        defaultOrganizationShouldBeFound("email.specified=true");

        // Get all the organizationList where email is null
        defaultOrganizationShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where email contains DEFAULT_EMAIL
        defaultOrganizationShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the organizationList where email contains UPDATED_EMAIL
        defaultOrganizationShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganizationsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where email does not contain DEFAULT_EMAIL
        defaultOrganizationShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the organizationList where email does not contain UPDATED_EMAIL
        defaultOrganizationShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebSiteIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where webSite equals to DEFAULT_WEB_SITE
        defaultOrganizationShouldBeFound("webSite.equals=" + DEFAULT_WEB_SITE);

        // Get all the organizationList where webSite equals to UPDATED_WEB_SITE
        defaultOrganizationShouldNotBeFound("webSite.equals=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebSiteIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where webSite in DEFAULT_WEB_SITE or UPDATED_WEB_SITE
        defaultOrganizationShouldBeFound("webSite.in=" + DEFAULT_WEB_SITE + "," + UPDATED_WEB_SITE);

        // Get all the organizationList where webSite equals to UPDATED_WEB_SITE
        defaultOrganizationShouldNotBeFound("webSite.in=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebSiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where webSite is not null
        defaultOrganizationShouldBeFound("webSite.specified=true");

        // Get all the organizationList where webSite is null
        defaultOrganizationShouldNotBeFound("webSite.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebSiteContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where webSite contains DEFAULT_WEB_SITE
        defaultOrganizationShouldBeFound("webSite.contains=" + DEFAULT_WEB_SITE);

        // Get all the organizationList where webSite contains UPDATED_WEB_SITE
        defaultOrganizationShouldNotBeFound("webSite.contains=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByWebSiteNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where webSite does not contain DEFAULT_WEB_SITE
        defaultOrganizationShouldNotBeFound("webSite.doesNotContain=" + DEFAULT_WEB_SITE);

        // Get all the organizationList where webSite does not contain UPDATED_WEB_SITE
        defaultOrganizationShouldBeFound("webSite.doesNotContain=" + UPDATED_WEB_SITE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where fax equals to DEFAULT_FAX
        defaultOrganizationShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the organizationList where fax equals to UPDATED_FAX
        defaultOrganizationShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultOrganizationShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the organizationList where fax equals to UPDATED_FAX
        defaultOrganizationShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where fax is not null
        defaultOrganizationShouldBeFound("fax.specified=true");

        // Get all the organizationList where fax is null
        defaultOrganizationShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByFaxContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where fax contains DEFAULT_FAX
        defaultOrganizationShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the organizationList where fax contains UPDATED_FAX
        defaultOrganizationShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where fax does not contain DEFAULT_FAX
        defaultOrganizationShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the organizationList where fax does not contain UPDATED_FAX
        defaultOrganizationShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrgTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgType equals to DEFAULT_ORG_TYPE
        defaultOrganizationShouldBeFound("orgType.equals=" + DEFAULT_ORG_TYPE);

        // Get all the organizationList where orgType equals to UPDATED_ORG_TYPE
        defaultOrganizationShouldNotBeFound("orgType.equals=" + UPDATED_ORG_TYPE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrgTypeIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgType in DEFAULT_ORG_TYPE or UPDATED_ORG_TYPE
        defaultOrganizationShouldBeFound("orgType.in=" + DEFAULT_ORG_TYPE + "," + UPDATED_ORG_TYPE);

        // Get all the organizationList where orgType equals to UPDATED_ORG_TYPE
        defaultOrganizationShouldNotBeFound("orgType.in=" + UPDATED_ORG_TYPE);
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrgTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where orgType is not null
        defaultOrganizationShouldBeFound("orgType.specified=true");

        // Get all the organizationList where orgType is null
        defaultOrganizationShouldNotBeFound("orgType.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdOn equals to DEFAULT_CREATED_ON
        defaultOrganizationShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the organizationList where createdOn equals to UPDATED_CREATED_ON
        defaultOrganizationShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultOrganizationShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the organizationList where createdOn equals to UPDATED_CREATED_ON
        defaultOrganizationShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdOn is not null
        defaultOrganizationShouldBeFound("createdOn.specified=true");

        // Get all the organizationList where createdOn is null
        defaultOrganizationShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdBy equals to DEFAULT_CREATED_BY
        defaultOrganizationShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the organizationList where createdBy equals to UPDATED_CREATED_BY
        defaultOrganizationShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOrganizationShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the organizationList where createdBy equals to UPDATED_CREATED_BY
        defaultOrganizationShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdBy is not null
        defaultOrganizationShouldBeFound("createdBy.specified=true");

        // Get all the organizationList where createdBy is null
        defaultOrganizationShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdBy contains DEFAULT_CREATED_BY
        defaultOrganizationShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the organizationList where createdBy contains UPDATED_CREATED_BY
        defaultOrganizationShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOrganizationShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the organizationList where createdBy does not contain UPDATED_CREATED_BY
        defaultOrganizationShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where description equals to DEFAULT_DESCRIPTION
        defaultOrganizationShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the organizationList where description equals to UPDATED_DESCRIPTION
        defaultOrganizationShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganizationsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultOrganizationShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the organizationList where description equals to UPDATED_DESCRIPTION
        defaultOrganizationShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganizationsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where description is not null
        defaultOrganizationShouldBeFound("description.specified=true");

        // Get all the organizationList where description is null
        defaultOrganizationShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where description contains DEFAULT_DESCRIPTION
        defaultOrganizationShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the organizationList where description contains UPDATED_DESCRIPTION
        defaultOrganizationShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganizationsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where description does not contain DEFAULT_DESCRIPTION
        defaultOrganizationShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the organizationList where description does not contain UPDATED_DESCRIPTION
        defaultOrganizationShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where isDeleted equals to DEFAULT_IS_DELETED
        defaultOrganizationShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the organizationList where isDeleted equals to UPDATED_IS_DELETED
        defaultOrganizationShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultOrganizationShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the organizationList where isDeleted equals to UPDATED_IS_DELETED
        defaultOrganizationShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where isDeleted is not null
        defaultOrganizationShouldBeFound("isDeleted.specified=true");

        // Get all the organizationList where isDeleted is null
        defaultOrganizationShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByIsDeletedContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where isDeleted contains DEFAULT_IS_DELETED
        defaultOrganizationShouldBeFound("isDeleted.contains=" + DEFAULT_IS_DELETED);

        // Get all the organizationList where isDeleted contains UPDATED_IS_DELETED
        defaultOrganizationShouldNotBeFound("isDeleted.contains=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByIsDeletedNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where isDeleted does not contain DEFAULT_IS_DELETED
        defaultOrganizationShouldNotBeFound("isDeleted.doesNotContain=" + DEFAULT_IS_DELETED);

        // Get all the organizationList where isDeleted does not contain UPDATED_IS_DELETED
        defaultOrganizationShouldBeFound("isDeleted.doesNotContain=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where status equals to DEFAULT_STATUS
        defaultOrganizationShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the organizationList where status equals to UPDATED_STATUS
        defaultOrganizationShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultOrganizationShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the organizationList where status equals to UPDATED_STATUS
        defaultOrganizationShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllOrganizationsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where status is not null
        defaultOrganizationShouldBeFound("status.specified=true");

        // Get all the organizationList where status is null
        defaultOrganizationShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultOrganizationShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the organizationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultOrganizationShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultOrganizationShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the organizationList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultOrganizationShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModified is not null
        defaultOrganizationShouldBeFound("lastModified.specified=true");

        // Get all the organizationList where lastModified is null
        defaultOrganizationShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultOrganizationShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the organizationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the organizationList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy is not null
        defaultOrganizationShouldBeFound("lastModifiedBy.specified=true");

        // Get all the organizationList where lastModifiedBy is null
        defaultOrganizationShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultOrganizationShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the organizationList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultOrganizationShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the organizationList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultOrganizationShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultOrganizationShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the organizationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultOrganizationShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultOrganizationShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the organizationList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultOrganizationShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField1 is not null
        defaultOrganizationShouldBeFound("freeField1.specified=true");

        // Get all the organizationList where freeField1 is null
        defaultOrganizationShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultOrganizationShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the organizationList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultOrganizationShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultOrganizationShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the organizationList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultOrganizationShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultOrganizationShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the organizationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultOrganizationShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultOrganizationShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the organizationList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultOrganizationShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField2 is not null
        defaultOrganizationShouldBeFound("freeField2.specified=true");

        // Get all the organizationList where freeField2 is null
        defaultOrganizationShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultOrganizationShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the organizationList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultOrganizationShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultOrganizationShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the organizationList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultOrganizationShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultOrganizationShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the organizationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultOrganizationShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultOrganizationShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the organizationList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultOrganizationShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField3 is not null
        defaultOrganizationShouldBeFound("freeField3.specified=true");

        // Get all the organizationList where freeField3 is null
        defaultOrganizationShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultOrganizationShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the organizationList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultOrganizationShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultOrganizationShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the organizationList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultOrganizationShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField4IsEqualToSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField4 equals to DEFAULT_FREE_FIELD_4
        defaultOrganizationShouldBeFound("freeField4.equals=" + DEFAULT_FREE_FIELD_4);

        // Get all the organizationList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultOrganizationShouldNotBeFound("freeField4.equals=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField4IsInShouldWork() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField4 in DEFAULT_FREE_FIELD_4 or UPDATED_FREE_FIELD_4
        defaultOrganizationShouldBeFound("freeField4.in=" + DEFAULT_FREE_FIELD_4 + "," + UPDATED_FREE_FIELD_4);

        // Get all the organizationList where freeField4 equals to UPDATED_FREE_FIELD_4
        defaultOrganizationShouldNotBeFound("freeField4.in=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField4IsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField4 is not null
        defaultOrganizationShouldBeFound("freeField4.specified=true");

        // Get all the organizationList where freeField4 is null
        defaultOrganizationShouldNotBeFound("freeField4.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField4ContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField4 contains DEFAULT_FREE_FIELD_4
        defaultOrganizationShouldBeFound("freeField4.contains=" + DEFAULT_FREE_FIELD_4);

        // Get all the organizationList where freeField4 contains UPDATED_FREE_FIELD_4
        defaultOrganizationShouldNotBeFound("freeField4.contains=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrganizationsByFreeField4NotContainsSomething() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList where freeField4 does not contain DEFAULT_FREE_FIELD_4
        defaultOrganizationShouldNotBeFound("freeField4.doesNotContain=" + DEFAULT_FREE_FIELD_4);

        // Get all the organizationList where freeField4 does not contain UPDATED_FREE_FIELD_4
        defaultOrganizationShouldBeFound("freeField4.doesNotContain=" + UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void getAllOrganizationsByAddressIsEqualToSomething() throws Exception {
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            organizationRepository.saveAndFlush(organization);
            address = AddressResourceIT.createEntity(em);
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        organization.setAddress(address);
        organizationRepository.saveAndFlush(organization);
        Long addressId = address.getId();

        // Get all the organizationList where address equals to addressId
        defaultOrganizationShouldBeFound("addressId.equals=" + addressId);

        // Get all the organizationList where address equals to (addressId + 1)
        defaultOrganizationShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    @Test
    @Transactional
    void getAllOrganizationsByOrganizationIsEqualToSomething() throws Exception {
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organizationRepository.saveAndFlush(organization);
            organization = OrganizationResourceIT.createEntity(em);
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        em.persist(organization);
        em.flush();
        organization.setOrganization(organization);
        organizationRepository.saveAndFlush(organization);
        Long organizationId = organization.getId();

        // Get all the organizationList where organization equals to organizationId
        defaultOrganizationShouldBeFound("organizationId.equals=" + organizationId);

        // Get all the organizationList where organization equals to (organizationId + 1)
        defaultOrganizationShouldNotBeFound("organizationId.equals=" + (organizationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrganizationShouldBeFound(String filter) throws Exception {
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].orgName").value(hasItem(DEFAULT_ORG_NAME)))
            .andExpect(jsonPath("$.[*].branchcode").value(hasItem(DEFAULT_BRANCHCODE)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].ifscCode").value(hasItem(DEFAULT_IFSC_CODE)))
            .andExpect(jsonPath("$.[*].micrCode").value(hasItem(DEFAULT_MICR_CODE)))
            .andExpect(jsonPath("$.[*].swiftCode").value(hasItem(DEFAULT_SWIFT_CODE)))
            .andExpect(jsonPath("$.[*].ibanCode").value(hasItem(DEFAULT_IBAN_CODE)))
            .andExpect(jsonPath("$.[*].routingTransitNo").value(hasItem(DEFAULT_ROUTING_TRANSIT_NO)))
            .andExpect(jsonPath("$.[*].regNumber").value(hasItem(DEFAULT_REG_NUMBER.doubleValue())))
            .andExpect(jsonPath("$.[*].gstinNumber").value(hasItem(DEFAULT_GSTIN_NUMBER)))
            .andExpect(jsonPath("$.[*].panNumber").value(hasItem(DEFAULT_PAN_NUMBER)))
            .andExpect(jsonPath("$.[*].tanNumber").value(hasItem(DEFAULT_TAN_NUMBER)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].orgType").value(hasItem(DEFAULT_ORG_TYPE.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].lastModified").value(hasItem(DEFAULT_LAST_MODIFIED.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].freeField4").value(hasItem(DEFAULT_FREE_FIELD_4)));

        // Check, that the count call also returns 1
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrganizationShouldNotBeFound(String filter) throws Exception {
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findById(organization.getId()).get();
        // Disconnect from session so that the updates on updatedOrganization are not directly saved in db
        em.detach(updatedOrganization);
        updatedOrganization
            .orgName(UPDATED_ORG_NAME)
            .branchcode(UPDATED_BRANCHCODE)
            .region(UPDATED_REGION)
            .ifscCode(UPDATED_IFSC_CODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .ibanCode(UPDATED_IBAN_CODE)
            .routingTransitNo(UPDATED_ROUTING_TRANSIT_NO)
            .regNumber(UPDATED_REG_NUMBER)
            .gstinNumber(UPDATED_GSTIN_NUMBER)
            .panNumber(UPDATED_PAN_NUMBER)
            .tanNumber(UPDATED_TAN_NUMBER)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .webSite(UPDATED_WEB_SITE)
            .fax(UPDATED_FAX)
            .orgType(UPDATED_ORG_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .description(UPDATED_DESCRIPTION)
            .isDeleted(UPDATED_IS_DELETED)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);
        OrganizationDTO organizationDTO = organizationMapper.toDto(updatedOrganization);

        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testOrganization.getBranchcode()).isEqualTo(UPDATED_BRANCHCODE);
        assertThat(testOrganization.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testOrganization.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testOrganization.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testOrganization.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testOrganization.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
        assertThat(testOrganization.getRoutingTransitNo()).isEqualTo(UPDATED_ROUTING_TRANSIT_NO);
        assertThat(testOrganization.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testOrganization.getGstinNumber()).isEqualTo(UPDATED_GSTIN_NUMBER);
        assertThat(testOrganization.getPanNumber()).isEqualTo(UPDATED_PAN_NUMBER);
        assertThat(testOrganization.getTanNumber()).isEqualTo(UPDATED_TAN_NUMBER);
        assertThat(testOrganization.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOrganization.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganization.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testOrganization.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testOrganization.getOrgType()).isEqualTo(UPDATED_ORG_TYPE);
        assertThat(testOrganization.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOrganization.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganization.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganization.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOrganization.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrganization.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testOrganization.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testOrganization.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testOrganization.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void putNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .orgName(UPDATED_ORG_NAME)
            .ifscCode(UPDATED_IFSC_CODE)
            .micrCode(UPDATED_MICR_CODE)
            .routingTransitNo(UPDATED_ROUTING_TRANSIT_NO)
            .regNumber(UPDATED_REG_NUMBER)
            .panNumber(UPDATED_PAN_NUMBER)
            .phone(UPDATED_PHONE)
            .webSite(UPDATED_WEB_SITE)
            .createdBy(UPDATED_CREATED_BY)
            .isDeleted(UPDATED_IS_DELETED)
            .status(UPDATED_STATUS)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField4(UPDATED_FREE_FIELD_4);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testOrganization.getBranchcode()).isEqualTo(DEFAULT_BRANCHCODE);
        assertThat(testOrganization.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testOrganization.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testOrganization.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testOrganization.getSwiftCode()).isEqualTo(DEFAULT_SWIFT_CODE);
        assertThat(testOrganization.getIbanCode()).isEqualTo(DEFAULT_IBAN_CODE);
        assertThat(testOrganization.getRoutingTransitNo()).isEqualTo(UPDATED_ROUTING_TRANSIT_NO);
        assertThat(testOrganization.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testOrganization.getGstinNumber()).isEqualTo(DEFAULT_GSTIN_NUMBER);
        assertThat(testOrganization.getPanNumber()).isEqualTo(UPDATED_PAN_NUMBER);
        assertThat(testOrganization.getTanNumber()).isEqualTo(DEFAULT_TAN_NUMBER);
        assertThat(testOrganization.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOrganization.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testOrganization.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testOrganization.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testOrganization.getOrgType()).isEqualTo(DEFAULT_ORG_TYPE);
        assertThat(testOrganization.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testOrganization.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganization.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganization.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganization.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testOrganization.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrganization.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testOrganization.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testOrganization.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testOrganization.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void fullUpdateOrganizationWithPatch() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization using partial update
        Organization partialUpdatedOrganization = new Organization();
        partialUpdatedOrganization.setId(organization.getId());

        partialUpdatedOrganization
            .orgName(UPDATED_ORG_NAME)
            .branchcode(UPDATED_BRANCHCODE)
            .region(UPDATED_REGION)
            .ifscCode(UPDATED_IFSC_CODE)
            .micrCode(UPDATED_MICR_CODE)
            .swiftCode(UPDATED_SWIFT_CODE)
            .ibanCode(UPDATED_IBAN_CODE)
            .routingTransitNo(UPDATED_ROUTING_TRANSIT_NO)
            .regNumber(UPDATED_REG_NUMBER)
            .gstinNumber(UPDATED_GSTIN_NUMBER)
            .panNumber(UPDATED_PAN_NUMBER)
            .tanNumber(UPDATED_TAN_NUMBER)
            .phone(UPDATED_PHONE)
            .email(UPDATED_EMAIL)
            .webSite(UPDATED_WEB_SITE)
            .fax(UPDATED_FAX)
            .orgType(UPDATED_ORG_TYPE)
            .createdOn(UPDATED_CREATED_ON)
            .createdBy(UPDATED_CREATED_BY)
            .description(UPDATED_DESCRIPTION)
            .isDeleted(UPDATED_IS_DELETED)
            .status(UPDATED_STATUS)
            .lastModified(UPDATED_LAST_MODIFIED)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .freeField4(UPDATED_FREE_FIELD_4);

        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganization.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrganization))
            )
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getOrgName()).isEqualTo(UPDATED_ORG_NAME);
        assertThat(testOrganization.getBranchcode()).isEqualTo(UPDATED_BRANCHCODE);
        assertThat(testOrganization.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testOrganization.getIfscCode()).isEqualTo(UPDATED_IFSC_CODE);
        assertThat(testOrganization.getMicrCode()).isEqualTo(UPDATED_MICR_CODE);
        assertThat(testOrganization.getSwiftCode()).isEqualTo(UPDATED_SWIFT_CODE);
        assertThat(testOrganization.getIbanCode()).isEqualTo(UPDATED_IBAN_CODE);
        assertThat(testOrganization.getRoutingTransitNo()).isEqualTo(UPDATED_ROUTING_TRANSIT_NO);
        assertThat(testOrganization.getRegNumber()).isEqualTo(UPDATED_REG_NUMBER);
        assertThat(testOrganization.getGstinNumber()).isEqualTo(UPDATED_GSTIN_NUMBER);
        assertThat(testOrganization.getPanNumber()).isEqualTo(UPDATED_PAN_NUMBER);
        assertThat(testOrganization.getTanNumber()).isEqualTo(UPDATED_TAN_NUMBER);
        assertThat(testOrganization.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOrganization.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testOrganization.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testOrganization.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testOrganization.getOrgType()).isEqualTo(UPDATED_ORG_TYPE);
        assertThat(testOrganization.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOrganization.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganization.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testOrganization.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testOrganization.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testOrganization.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOrganization.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testOrganization.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testOrganization.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testOrganization.getFreeField4()).isEqualTo(UPDATED_FREE_FIELD_4);
    }

    @Test
    @Transactional
    void patchNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();
        organization.setId(count.incrementAndGet());

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(organizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Delete the organization
        restOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, organization.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
