package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Address;
import com.techvg.hrms.domain.enumeration.AddressType;
import com.techvg.hrms.repository.AddressRepository;
import com.techvg.hrms.service.criteria.AddressCriteria;
import com.techvg.hrms.service.dto.AddressDTO;
import com.techvg.hrms.service.mapper.AddressMapper;
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
 * Integration tests for the {@link AddressResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AddressResourceIT {

    private static final AddressType DEFAULT_TYPE = AddressType.CURRENT_ADDRESS;
    private static final AddressType UPDATED_TYPE = AddressType.PERMANENT_ADDRESS;

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final Boolean DEFAULT_HAS_PREFERED = false;
    private static final Boolean UPDATED_HAS_PREFERED = true;

    private static final String DEFAULT_LAND_MARK = "AAAAAAAAAA";
    private static final String UPDATED_LAND_MARK = "BBBBBBBBBB";

    private static final String DEFAULT_PINCODE = "AAAAAAAAAA";
    private static final String UPDATED_PINCODE = "BBBBBBBBBB";

    private static final Double DEFAULT_LATTITUDE = 1D;
    private static final Double UPDATED_LATTITUDE = 2D;
    private static final Double SMALLER_LATTITUDE = 1D - 1D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;
    private static final Double SMALLER_LONGITUDE = 1D - 1D;

    private static final String DEFAULT_MAP_NAV_URL = "AAAAAAAAAA";
    private static final String UPDATED_MAP_NAV_URL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/addresses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAddressMockMvc;

    private Address address;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createEntity(EntityManager em) {
        Address address = new Address()
            .type(DEFAULT_TYPE)
            .address1(DEFAULT_ADDRESS_1)
            .hasPrefered(DEFAULT_HAS_PREFERED)
            .landMark(DEFAULT_LAND_MARK)
            .pincode(DEFAULT_PINCODE)
            .lattitude(DEFAULT_LATTITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .mapNavUrl(DEFAULT_MAP_NAV_URL)
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
        return address;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Address createUpdatedEntity(EntityManager em) {
        Address address = new Address()
            .type(UPDATED_TYPE)
            .address1(UPDATED_ADDRESS_1)
            .hasPrefered(UPDATED_HAS_PREFERED)
            .landMark(UPDATED_LAND_MARK)
            .pincode(UPDATED_PINCODE)
            .lattitude(UPDATED_LATTITUDE)
            .longitude(UPDATED_LONGITUDE)
            .mapNavUrl(UPDATED_MAP_NAV_URL)
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
        return address;
    }

    @BeforeEach
    public void initTest() {
        address = createEntity(em);
    }

    @Test
    @Transactional
    void createAddress() throws Exception {
        int databaseSizeBeforeCreate = addressRepository.findAll().size();
        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isCreated());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate + 1);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAddress.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testAddress.getHasPrefered()).isEqualTo(DEFAULT_HAS_PREFERED);
        assertThat(testAddress.getLandMark()).isEqualTo(DEFAULT_LAND_MARK);
        assertThat(testAddress.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testAddress.getLattitude()).isEqualTo(DEFAULT_LATTITUDE);
        assertThat(testAddress.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testAddress.getMapNavUrl()).isEqualTo(DEFAULT_MAP_NAV_URL);
        assertThat(testAddress.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAddress.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testAddress.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAddress.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAddress.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testAddress.getFreefield4()).isEqualTo(DEFAULT_FREEFIELD_4);
        assertThat(testAddress.getFreefield5()).isEqualTo(DEFAULT_FREEFIELD_5);
    }

    @Test
    @Transactional
    void createAddressWithExistingId() throws Exception {
        // Create the Address with an existing ID
        address.setId(1L);
        AddressDTO addressDTO = addressMapper.toDto(address);

        int databaseSizeBeforeCreate = addressRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAddressMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAddresses() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].hasPrefered").value(hasItem(DEFAULT_HAS_PREFERED.booleanValue())))
            .andExpect(jsonPath("$.[*].landMark").value(hasItem(DEFAULT_LAND_MARK)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].lattitude").value(hasItem(DEFAULT_LATTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].mapNavUrl").value(hasItem(DEFAULT_MAP_NAV_URL)))
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
    void getAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get the address
        restAddressMockMvc
            .perform(get(ENTITY_API_URL_ID, address.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(address.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.hasPrefered").value(DEFAULT_HAS_PREFERED.booleanValue()))
            .andExpect(jsonPath("$.landMark").value(DEFAULT_LAND_MARK))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.lattitude").value(DEFAULT_LATTITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.mapNavUrl").value(DEFAULT_MAP_NAV_URL))
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
    void getAddressesByIdFiltering() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        Long id = address.getId();

        defaultAddressShouldBeFound("id.equals=" + id);
        defaultAddressShouldNotBeFound("id.notEquals=" + id);

        defaultAddressShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.greaterThan=" + id);

        defaultAddressShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAddressShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAddressesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where type equals to DEFAULT_TYPE
        defaultAddressShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the addressList where type equals to UPDATED_TYPE
        defaultAddressShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAddressesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultAddressShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the addressList where type equals to UPDATED_TYPE
        defaultAddressShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllAddressesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where type is not null
        defaultAddressShouldBeFound("type.specified=true");

        // Get all the addressList where type is null
        defaultAddressShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByAddress1IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where address1 equals to DEFAULT_ADDRESS_1
        defaultAddressShouldBeFound("address1.equals=" + DEFAULT_ADDRESS_1);

        // Get all the addressList where address1 equals to UPDATED_ADDRESS_1
        defaultAddressShouldNotBeFound("address1.equals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllAddressesByAddress1IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where address1 in DEFAULT_ADDRESS_1 or UPDATED_ADDRESS_1
        defaultAddressShouldBeFound("address1.in=" + DEFAULT_ADDRESS_1 + "," + UPDATED_ADDRESS_1);

        // Get all the addressList where address1 equals to UPDATED_ADDRESS_1
        defaultAddressShouldNotBeFound("address1.in=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllAddressesByAddress1IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where address1 is not null
        defaultAddressShouldBeFound("address1.specified=true");

        // Get all the addressList where address1 is null
        defaultAddressShouldNotBeFound("address1.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByAddress1ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where address1 contains DEFAULT_ADDRESS_1
        defaultAddressShouldBeFound("address1.contains=" + DEFAULT_ADDRESS_1);

        // Get all the addressList where address1 contains UPDATED_ADDRESS_1
        defaultAddressShouldNotBeFound("address1.contains=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllAddressesByAddress1NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where address1 does not contain DEFAULT_ADDRESS_1
        defaultAddressShouldNotBeFound("address1.doesNotContain=" + DEFAULT_ADDRESS_1);

        // Get all the addressList where address1 does not contain UPDATED_ADDRESS_1
        defaultAddressShouldBeFound("address1.doesNotContain=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllAddressesByHasPreferedIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where hasPrefered equals to DEFAULT_HAS_PREFERED
        defaultAddressShouldBeFound("hasPrefered.equals=" + DEFAULT_HAS_PREFERED);

        // Get all the addressList where hasPrefered equals to UPDATED_HAS_PREFERED
        defaultAddressShouldNotBeFound("hasPrefered.equals=" + UPDATED_HAS_PREFERED);
    }

    @Test
    @Transactional
    void getAllAddressesByHasPreferedIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where hasPrefered in DEFAULT_HAS_PREFERED or UPDATED_HAS_PREFERED
        defaultAddressShouldBeFound("hasPrefered.in=" + DEFAULT_HAS_PREFERED + "," + UPDATED_HAS_PREFERED);

        // Get all the addressList where hasPrefered equals to UPDATED_HAS_PREFERED
        defaultAddressShouldNotBeFound("hasPrefered.in=" + UPDATED_HAS_PREFERED);
    }

    @Test
    @Transactional
    void getAllAddressesByHasPreferedIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where hasPrefered is not null
        defaultAddressShouldBeFound("hasPrefered.specified=true");

        // Get all the addressList where hasPrefered is null
        defaultAddressShouldNotBeFound("hasPrefered.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark equals to DEFAULT_LAND_MARK
        defaultAddressShouldBeFound("landMark.equals=" + DEFAULT_LAND_MARK);

        // Get all the addressList where landMark equals to UPDATED_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.equals=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark in DEFAULT_LAND_MARK or UPDATED_LAND_MARK
        defaultAddressShouldBeFound("landMark.in=" + DEFAULT_LAND_MARK + "," + UPDATED_LAND_MARK);

        // Get all the addressList where landMark equals to UPDATED_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.in=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark is not null
        defaultAddressShouldBeFound("landMark.specified=true");

        // Get all the addressList where landMark is null
        defaultAddressShouldNotBeFound("landMark.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark contains DEFAULT_LAND_MARK
        defaultAddressShouldBeFound("landMark.contains=" + DEFAULT_LAND_MARK);

        // Get all the addressList where landMark contains UPDATED_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.contains=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByLandMarkNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where landMark does not contain DEFAULT_LAND_MARK
        defaultAddressShouldNotBeFound("landMark.doesNotContain=" + DEFAULT_LAND_MARK);

        // Get all the addressList where landMark does not contain UPDATED_LAND_MARK
        defaultAddressShouldBeFound("landMark.doesNotContain=" + UPDATED_LAND_MARK);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode equals to DEFAULT_PINCODE
        defaultAddressShouldBeFound("pincode.equals=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode equals to UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.equals=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode in DEFAULT_PINCODE or UPDATED_PINCODE
        defaultAddressShouldBeFound("pincode.in=" + DEFAULT_PINCODE + "," + UPDATED_PINCODE);

        // Get all the addressList where pincode equals to UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.in=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode is not null
        defaultAddressShouldBeFound("pincode.specified=true");

        // Get all the addressList where pincode is null
        defaultAddressShouldNotBeFound("pincode.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode contains DEFAULT_PINCODE
        defaultAddressShouldBeFound("pincode.contains=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode contains UPDATED_PINCODE
        defaultAddressShouldNotBeFound("pincode.contains=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByPincodeNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where pincode does not contain DEFAULT_PINCODE
        defaultAddressShouldNotBeFound("pincode.doesNotContain=" + DEFAULT_PINCODE);

        // Get all the addressList where pincode does not contain UPDATED_PINCODE
        defaultAddressShouldBeFound("pincode.doesNotContain=" + UPDATED_PINCODE);
    }

    @Test
    @Transactional
    void getAllAddressesByLattitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lattitude equals to DEFAULT_LATTITUDE
        defaultAddressShouldBeFound("lattitude.equals=" + DEFAULT_LATTITUDE);

        // Get all the addressList where lattitude equals to UPDATED_LATTITUDE
        defaultAddressShouldNotBeFound("lattitude.equals=" + UPDATED_LATTITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLattitudeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lattitude in DEFAULT_LATTITUDE or UPDATED_LATTITUDE
        defaultAddressShouldBeFound("lattitude.in=" + DEFAULT_LATTITUDE + "," + UPDATED_LATTITUDE);

        // Get all the addressList where lattitude equals to UPDATED_LATTITUDE
        defaultAddressShouldNotBeFound("lattitude.in=" + UPDATED_LATTITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLattitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lattitude is not null
        defaultAddressShouldBeFound("lattitude.specified=true");

        // Get all the addressList where lattitude is null
        defaultAddressShouldNotBeFound("lattitude.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLattitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lattitude is greater than or equal to DEFAULT_LATTITUDE
        defaultAddressShouldBeFound("lattitude.greaterThanOrEqual=" + DEFAULT_LATTITUDE);

        // Get all the addressList where lattitude is greater than or equal to UPDATED_LATTITUDE
        defaultAddressShouldNotBeFound("lattitude.greaterThanOrEqual=" + UPDATED_LATTITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLattitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lattitude is less than or equal to DEFAULT_LATTITUDE
        defaultAddressShouldBeFound("lattitude.lessThanOrEqual=" + DEFAULT_LATTITUDE);

        // Get all the addressList where lattitude is less than or equal to SMALLER_LATTITUDE
        defaultAddressShouldNotBeFound("lattitude.lessThanOrEqual=" + SMALLER_LATTITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLattitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lattitude is less than DEFAULT_LATTITUDE
        defaultAddressShouldNotBeFound("lattitude.lessThan=" + DEFAULT_LATTITUDE);

        // Get all the addressList where lattitude is less than UPDATED_LATTITUDE
        defaultAddressShouldBeFound("lattitude.lessThan=" + UPDATED_LATTITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLattitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lattitude is greater than DEFAULT_LATTITUDE
        defaultAddressShouldNotBeFound("lattitude.greaterThan=" + DEFAULT_LATTITUDE);

        // Get all the addressList where lattitude is greater than SMALLER_LATTITUDE
        defaultAddressShouldBeFound("lattitude.greaterThan=" + SMALLER_LATTITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude equals to DEFAULT_LONGITUDE
        defaultAddressShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the addressList where longitude equals to UPDATED_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultAddressShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the addressList where longitude equals to UPDATED_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude is not null
        defaultAddressShouldBeFound("longitude.specified=true");

        // Get all the addressList where longitude is null
        defaultAddressShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultAddressShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the addressList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultAddressShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the addressList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude is less than DEFAULT_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the addressList where longitude is less than UPDATED_LONGITUDE
        defaultAddressShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where longitude is greater than DEFAULT_LONGITUDE
        defaultAddressShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the addressList where longitude is greater than SMALLER_LONGITUDE
        defaultAddressShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    void getAllAddressesByMapNavUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where mapNavUrl equals to DEFAULT_MAP_NAV_URL
        defaultAddressShouldBeFound("mapNavUrl.equals=" + DEFAULT_MAP_NAV_URL);

        // Get all the addressList where mapNavUrl equals to UPDATED_MAP_NAV_URL
        defaultAddressShouldNotBeFound("mapNavUrl.equals=" + UPDATED_MAP_NAV_URL);
    }

    @Test
    @Transactional
    void getAllAddressesByMapNavUrlIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where mapNavUrl in DEFAULT_MAP_NAV_URL or UPDATED_MAP_NAV_URL
        defaultAddressShouldBeFound("mapNavUrl.in=" + DEFAULT_MAP_NAV_URL + "," + UPDATED_MAP_NAV_URL);

        // Get all the addressList where mapNavUrl equals to UPDATED_MAP_NAV_URL
        defaultAddressShouldNotBeFound("mapNavUrl.in=" + UPDATED_MAP_NAV_URL);
    }

    @Test
    @Transactional
    void getAllAddressesByMapNavUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where mapNavUrl is not null
        defaultAddressShouldBeFound("mapNavUrl.specified=true");

        // Get all the addressList where mapNavUrl is null
        defaultAddressShouldNotBeFound("mapNavUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByMapNavUrlContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where mapNavUrl contains DEFAULT_MAP_NAV_URL
        defaultAddressShouldBeFound("mapNavUrl.contains=" + DEFAULT_MAP_NAV_URL);

        // Get all the addressList where mapNavUrl contains UPDATED_MAP_NAV_URL
        defaultAddressShouldNotBeFound("mapNavUrl.contains=" + UPDATED_MAP_NAV_URL);
    }

    @Test
    @Transactional
    void getAllAddressesByMapNavUrlNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where mapNavUrl does not contain DEFAULT_MAP_NAV_URL
        defaultAddressShouldNotBeFound("mapNavUrl.doesNotContain=" + DEFAULT_MAP_NAV_URL);

        // Get all the addressList where mapNavUrl does not contain UPDATED_MAP_NAV_URL
        defaultAddressShouldBeFound("mapNavUrl.doesNotContain=" + UPDATED_MAP_NAV_URL);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified equals to DEFAULT_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.equals=" + DEFAULT_LAST_MODIFIED);

        // Get all the addressList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.equals=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified in DEFAULT_LAST_MODIFIED or UPDATED_LAST_MODIFIED
        defaultAddressShouldBeFound("lastModified.in=" + DEFAULT_LAST_MODIFIED + "," + UPDATED_LAST_MODIFIED);

        // Get all the addressList where lastModified equals to UPDATED_LAST_MODIFIED
        defaultAddressShouldNotBeFound("lastModified.in=" + UPDATED_LAST_MODIFIED);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModified is not null
        defaultAddressShouldBeFound("lastModified.specified=true");

        // Get all the addressList where lastModified is null
        defaultAddressShouldNotBeFound("lastModified.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy is not null
        defaultAddressShouldBeFound("lastModifiedBy.specified=true");

        // Get all the addressList where lastModifiedBy is null
        defaultAddressShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultAddressShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the addressList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultAddressShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy equals to DEFAULT_CREATED_BY
        defaultAddressShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the addressList where createdBy equals to UPDATED_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultAddressShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the addressList where createdBy equals to UPDATED_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy is not null
        defaultAddressShouldBeFound("createdBy.specified=true");

        // Get all the addressList where createdBy is null
        defaultAddressShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy contains DEFAULT_CREATED_BY
        defaultAddressShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the addressList where createdBy contains UPDATED_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdBy does not contain DEFAULT_CREATED_BY
        defaultAddressShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the addressList where createdBy does not contain UPDATED_CREATED_BY
        defaultAddressShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdOn equals to DEFAULT_CREATED_ON
        defaultAddressShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the addressList where createdOn equals to UPDATED_CREATED_ON
        defaultAddressShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultAddressShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the addressList where createdOn equals to UPDATED_CREATED_ON
        defaultAddressShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllAddressesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where createdOn is not null
        defaultAddressShouldBeFound("createdOn.specified=true");

        // Get all the addressList where createdOn is null
        defaultAddressShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByIsDeletedIsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isDeleted equals to DEFAULT_IS_DELETED
        defaultAddressShouldBeFound("isDeleted.equals=" + DEFAULT_IS_DELETED);

        // Get all the addressList where isDeleted equals to UPDATED_IS_DELETED
        defaultAddressShouldNotBeFound("isDeleted.equals=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllAddressesByIsDeletedIsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isDeleted in DEFAULT_IS_DELETED or UPDATED_IS_DELETED
        defaultAddressShouldBeFound("isDeleted.in=" + DEFAULT_IS_DELETED + "," + UPDATED_IS_DELETED);

        // Get all the addressList where isDeleted equals to UPDATED_IS_DELETED
        defaultAddressShouldNotBeFound("isDeleted.in=" + UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void getAllAddressesByIsDeletedIsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where isDeleted is not null
        defaultAddressShouldBeFound("isDeleted.specified=true");

        // Get all the addressList where isDeleted is null
        defaultAddressShouldNotBeFound("isDeleted.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField1IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField1 equals to DEFAULT_FREE_FIELD_1
        defaultAddressShouldBeFound("freeField1.equals=" + DEFAULT_FREE_FIELD_1);

        // Get all the addressList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultAddressShouldNotBeFound("freeField1.equals=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField1IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField1 in DEFAULT_FREE_FIELD_1 or UPDATED_FREE_FIELD_1
        defaultAddressShouldBeFound("freeField1.in=" + DEFAULT_FREE_FIELD_1 + "," + UPDATED_FREE_FIELD_1);

        // Get all the addressList where freeField1 equals to UPDATED_FREE_FIELD_1
        defaultAddressShouldNotBeFound("freeField1.in=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField1IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField1 is not null
        defaultAddressShouldBeFound("freeField1.specified=true");

        // Get all the addressList where freeField1 is null
        defaultAddressShouldNotBeFound("freeField1.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField1ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField1 contains DEFAULT_FREE_FIELD_1
        defaultAddressShouldBeFound("freeField1.contains=" + DEFAULT_FREE_FIELD_1);

        // Get all the addressList where freeField1 contains UPDATED_FREE_FIELD_1
        defaultAddressShouldNotBeFound("freeField1.contains=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField1NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField1 does not contain DEFAULT_FREE_FIELD_1
        defaultAddressShouldNotBeFound("freeField1.doesNotContain=" + DEFAULT_FREE_FIELD_1);

        // Get all the addressList where freeField1 does not contain UPDATED_FREE_FIELD_1
        defaultAddressShouldBeFound("freeField1.doesNotContain=" + UPDATED_FREE_FIELD_1);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField2IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField2 equals to DEFAULT_FREE_FIELD_2
        defaultAddressShouldBeFound("freeField2.equals=" + DEFAULT_FREE_FIELD_2);

        // Get all the addressList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultAddressShouldNotBeFound("freeField2.equals=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField2IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField2 in DEFAULT_FREE_FIELD_2 or UPDATED_FREE_FIELD_2
        defaultAddressShouldBeFound("freeField2.in=" + DEFAULT_FREE_FIELD_2 + "," + UPDATED_FREE_FIELD_2);

        // Get all the addressList where freeField2 equals to UPDATED_FREE_FIELD_2
        defaultAddressShouldNotBeFound("freeField2.in=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField2IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField2 is not null
        defaultAddressShouldBeFound("freeField2.specified=true");

        // Get all the addressList where freeField2 is null
        defaultAddressShouldNotBeFound("freeField2.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField2ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField2 contains DEFAULT_FREE_FIELD_2
        defaultAddressShouldBeFound("freeField2.contains=" + DEFAULT_FREE_FIELD_2);

        // Get all the addressList where freeField2 contains UPDATED_FREE_FIELD_2
        defaultAddressShouldNotBeFound("freeField2.contains=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField2NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField2 does not contain DEFAULT_FREE_FIELD_2
        defaultAddressShouldNotBeFound("freeField2.doesNotContain=" + DEFAULT_FREE_FIELD_2);

        // Get all the addressList where freeField2 does not contain UPDATED_FREE_FIELD_2
        defaultAddressShouldBeFound("freeField2.doesNotContain=" + UPDATED_FREE_FIELD_2);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField3IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField3 equals to DEFAULT_FREE_FIELD_3
        defaultAddressShouldBeFound("freeField3.equals=" + DEFAULT_FREE_FIELD_3);

        // Get all the addressList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultAddressShouldNotBeFound("freeField3.equals=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField3IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField3 in DEFAULT_FREE_FIELD_3 or UPDATED_FREE_FIELD_3
        defaultAddressShouldBeFound("freeField3.in=" + DEFAULT_FREE_FIELD_3 + "," + UPDATED_FREE_FIELD_3);

        // Get all the addressList where freeField3 equals to UPDATED_FREE_FIELD_3
        defaultAddressShouldNotBeFound("freeField3.in=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField3IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField3 is not null
        defaultAddressShouldBeFound("freeField3.specified=true");

        // Get all the addressList where freeField3 is null
        defaultAddressShouldNotBeFound("freeField3.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField3ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField3 contains DEFAULT_FREE_FIELD_3
        defaultAddressShouldBeFound("freeField3.contains=" + DEFAULT_FREE_FIELD_3);

        // Get all the addressList where freeField3 contains UPDATED_FREE_FIELD_3
        defaultAddressShouldNotBeFound("freeField3.contains=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAddressesByFreeField3NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freeField3 does not contain DEFAULT_FREE_FIELD_3
        defaultAddressShouldNotBeFound("freeField3.doesNotContain=" + DEFAULT_FREE_FIELD_3);

        // Get all the addressList where freeField3 does not contain UPDATED_FREE_FIELD_3
        defaultAddressShouldBeFound("freeField3.doesNotContain=" + UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void getAllAddressesByFreefield4IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freefield4 equals to DEFAULT_FREEFIELD_4
        defaultAddressShouldBeFound("freefield4.equals=" + DEFAULT_FREEFIELD_4);

        // Get all the addressList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultAddressShouldNotBeFound("freefield4.equals=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllAddressesByFreefield4IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freefield4 in DEFAULT_FREEFIELD_4 or UPDATED_FREEFIELD_4
        defaultAddressShouldBeFound("freefield4.in=" + DEFAULT_FREEFIELD_4 + "," + UPDATED_FREEFIELD_4);

        // Get all the addressList where freefield4 equals to UPDATED_FREEFIELD_4
        defaultAddressShouldNotBeFound("freefield4.in=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllAddressesByFreefield4IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freefield4 is not null
        defaultAddressShouldBeFound("freefield4.specified=true");

        // Get all the addressList where freefield4 is null
        defaultAddressShouldNotBeFound("freefield4.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreefield4ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freefield4 contains DEFAULT_FREEFIELD_4
        defaultAddressShouldBeFound("freefield4.contains=" + DEFAULT_FREEFIELD_4);

        // Get all the addressList where freefield4 contains UPDATED_FREEFIELD_4
        defaultAddressShouldNotBeFound("freefield4.contains=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllAddressesByFreefield4NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freefield4 does not contain DEFAULT_FREEFIELD_4
        defaultAddressShouldNotBeFound("freefield4.doesNotContain=" + DEFAULT_FREEFIELD_4);

        // Get all the addressList where freefield4 does not contain UPDATED_FREEFIELD_4
        defaultAddressShouldBeFound("freefield4.doesNotContain=" + UPDATED_FREEFIELD_4);
    }

    @Test
    @Transactional
    void getAllAddressesByFreefield5IsEqualToSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freefield5 equals to DEFAULT_FREEFIELD_5
        defaultAddressShouldBeFound("freefield5.equals=" + DEFAULT_FREEFIELD_5);

        // Get all the addressList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultAddressShouldNotBeFound("freefield5.equals=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllAddressesByFreefield5IsInShouldWork() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freefield5 in DEFAULT_FREEFIELD_5 or UPDATED_FREEFIELD_5
        defaultAddressShouldBeFound("freefield5.in=" + DEFAULT_FREEFIELD_5 + "," + UPDATED_FREEFIELD_5);

        // Get all the addressList where freefield5 equals to UPDATED_FREEFIELD_5
        defaultAddressShouldNotBeFound("freefield5.in=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllAddressesByFreefield5IsNullOrNotNull() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freefield5 is not null
        defaultAddressShouldBeFound("freefield5.specified=true");

        // Get all the addressList where freefield5 is null
        defaultAddressShouldNotBeFound("freefield5.specified=false");
    }

    @Test
    @Transactional
    void getAllAddressesByFreefield5ContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freefield5 contains DEFAULT_FREEFIELD_5
        defaultAddressShouldBeFound("freefield5.contains=" + DEFAULT_FREEFIELD_5);

        // Get all the addressList where freefield5 contains UPDATED_FREEFIELD_5
        defaultAddressShouldNotBeFound("freefield5.contains=" + UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void getAllAddressesByFreefield5NotContainsSomething() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        // Get all the addressList where freefield5 does not contain DEFAULT_FREEFIELD_5
        defaultAddressShouldNotBeFound("freefield5.doesNotContain=" + DEFAULT_FREEFIELD_5);

        // Get all the addressList where freefield5 does not contain UPDATED_FREEFIELD_5
        defaultAddressShouldBeFound("freefield5.doesNotContain=" + UPDATED_FREEFIELD_5);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAddressShouldBeFound(String filter) throws Exception {
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(address.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].hasPrefered").value(hasItem(DEFAULT_HAS_PREFERED.booleanValue())))
            .andExpect(jsonPath("$.[*].landMark").value(hasItem(DEFAULT_LAND_MARK)))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].lattitude").value(hasItem(DEFAULT_LATTITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].mapNavUrl").value(hasItem(DEFAULT_MAP_NAV_URL)))
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
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAddressShouldNotBeFound(String filter) throws Exception {
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAddressMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAddress() throws Exception {
        // Get the address
        restAddressMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address
        Address updatedAddress = addressRepository.findById(address.getId()).get();
        // Disconnect from session so that the updates on updatedAddress are not directly saved in db
        em.detach(updatedAddress);
        updatedAddress
            .type(UPDATED_TYPE)
            .address1(UPDATED_ADDRESS_1)
            .hasPrefered(UPDATED_HAS_PREFERED)
            .landMark(UPDATED_LAND_MARK)
            .pincode(UPDATED_PINCODE)
            .lattitude(UPDATED_LATTITUDE)
            .longitude(UPDATED_LONGITUDE)
            .mapNavUrl(UPDATED_MAP_NAV_URL)
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
        AddressDTO addressDTO = addressMapper.toDto(updatedAddress);

        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testAddress.getHasPrefered()).isEqualTo(UPDATED_HAS_PREFERED);
        assertThat(testAddress.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testAddress.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testAddress.getLattitude()).isEqualTo(UPDATED_LATTITUDE);
        assertThat(testAddress.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAddress.getMapNavUrl()).isEqualTo(UPDATED_MAP_NAV_URL);
        assertThat(testAddress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAddress.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAddress.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAddress.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAddress.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAddress.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testAddress.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void putNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(addressDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .address1(UPDATED_ADDRESS_1)
            .hasPrefered(UPDATED_HAS_PREFERED)
            .landMark(UPDATED_LAND_MARK)
            .pincode(UPDATED_PINCODE)
            .longitude(UPDATED_LONGITUDE)
            .mapNavUrl(UPDATED_MAP_NAV_URL)
            .createdBy(UPDATED_CREATED_BY)
            .freefield5(UPDATED_FREEFIELD_5);

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testAddress.getHasPrefered()).isEqualTo(UPDATED_HAS_PREFERED);
        assertThat(testAddress.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testAddress.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testAddress.getLattitude()).isEqualTo(DEFAULT_LATTITUDE);
        assertThat(testAddress.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAddress.getMapNavUrl()).isEqualTo(UPDATED_MAP_NAV_URL);
        assertThat(testAddress.getLastModified()).isEqualTo(DEFAULT_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAddress.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testAddress.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testAddress.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testAddress.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testAddress.getFreefield4()).isEqualTo(DEFAULT_FREEFIELD_4);
        assertThat(testAddress.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void fullUpdateAddressWithPatch() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeUpdate = addressRepository.findAll().size();

        // Update the address using partial update
        Address partialUpdatedAddress = new Address();
        partialUpdatedAddress.setId(address.getId());

        partialUpdatedAddress
            .type(UPDATED_TYPE)
            .address1(UPDATED_ADDRESS_1)
            .hasPrefered(UPDATED_HAS_PREFERED)
            .landMark(UPDATED_LAND_MARK)
            .pincode(UPDATED_PINCODE)
            .lattitude(UPDATED_LATTITUDE)
            .longitude(UPDATED_LONGITUDE)
            .mapNavUrl(UPDATED_MAP_NAV_URL)
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

        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAddress.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAddress))
            )
            .andExpect(status().isOk());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
        Address testAddress = addressList.get(addressList.size() - 1);
        assertThat(testAddress.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAddress.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testAddress.getHasPrefered()).isEqualTo(UPDATED_HAS_PREFERED);
        assertThat(testAddress.getLandMark()).isEqualTo(UPDATED_LAND_MARK);
        assertThat(testAddress.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testAddress.getLattitude()).isEqualTo(UPDATED_LATTITUDE);
        assertThat(testAddress.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testAddress.getMapNavUrl()).isEqualTo(UPDATED_MAP_NAV_URL);
        assertThat(testAddress.getLastModified()).isEqualTo(UPDATED_LAST_MODIFIED);
        assertThat(testAddress.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAddress.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAddress.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAddress.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testAddress.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testAddress.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testAddress.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testAddress.getFreefield4()).isEqualTo(UPDATED_FREEFIELD_4);
        assertThat(testAddress.getFreefield5()).isEqualTo(UPDATED_FREEFIELD_5);
    }

    @Test
    @Transactional
    void patchNonExistingAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, addressDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAddress() throws Exception {
        int databaseSizeBeforeUpdate = addressRepository.findAll().size();
        address.setId(count.incrementAndGet());

        // Create the Address
        AddressDTO addressDTO = addressMapper.toDto(address);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAddressMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(addressDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Address in the database
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAddress() throws Exception {
        // Initialize the database
        addressRepository.saveAndFlush(address);

        int databaseSizeBeforeDelete = addressRepository.findAll().size();

        // Delete the address
        restAddressMockMvc
            .perform(delete(ENTITY_API_URL_ID, address.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Address> addressList = addressRepository.findAll();
        assertThat(addressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
