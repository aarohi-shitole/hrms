package com.techvg.hrms.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.techvg.hrms.IntegrationTest;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.EmployeeDetails;
import com.techvg.hrms.domain.IncomeTaxSlab;
import com.techvg.hrms.domain.Organization;
import com.techvg.hrms.domain.OrganizationPolicies;
import com.techvg.hrms.domain.SecurityPermission;
import com.techvg.hrms.domain.SecurityRole;
import com.techvg.hrms.domain.enumeration.Title;
import com.techvg.hrms.repository.EmployeeRepository;
import com.techvg.hrms.service.EmployeeService;
import com.techvg.hrms.service.criteria.EmployeeCriteria;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.mapper.EmployeeMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EmployeeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EmployeeResourceIT {

    private static final Title DEFAULT_TITLE = Title.MR;
    private static final Title UPDATED_TITLE = Title.MRS;

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_GRADE = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD_HASH = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD_HASH = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_DEPARTMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTMENT = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_LANG_KEY = "AAAAAAAAAA";
    private static final String UPDATED_LANG_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_RESET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_RESET_KEY = "BBBBBBBBBB";

    private static final Instant DEFAULT_RESET_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RESET_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/employees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeRepository employeeRepositoryMock;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Mock
    private EmployeeService employeeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .title(DEFAULT_TITLE)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .grade(DEFAULT_GRADE)
            .username(DEFAULT_USERNAME)
            .passwordHash(DEFAULT_PASSWORD_HASH)
            .email(DEFAULT_EMAIL)
            .mobileNo(DEFAULT_MOBILE_NO)
            .department(DEFAULT_DEPARTMENT)
            .imageUrl(DEFAULT_IMAGE_URL)
            .activated(DEFAULT_ACTIVATED)
            .langKey(DEFAULT_LANG_KEY)
            .activationKey(DEFAULT_ACTIVATION_KEY)
            .resetKey(DEFAULT_RESET_KEY)
            .resetDate(DEFAULT_RESET_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON);
        return employee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .grade(UPDATED_GRADE)
            .username(UPDATED_USERNAME)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .department(UPDATED_DEPARTMENT)
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();
        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEmployee.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testEmployee.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployee.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmployee.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testEmployee.getPasswordHash()).isEqualTo(DEFAULT_PASSWORD_HASH);
        assertThat(testEmployee.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEmployee.getMobileNo()).isEqualTo(DEFAULT_MOBILE_NO);
        assertThat(testEmployee.getDepartment()).isEqualTo(DEFAULT_DEPARTMENT);
        assertThat(testEmployee.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testEmployee.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testEmployee.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
        assertThat(testEmployee.getActivationKey()).isEqualTo(DEFAULT_ACTIVATION_KEY);
        assertThat(testEmployee.getResetKey()).isEqualTo(DEFAULT_RESET_KEY);
        assertThat(testEmployee.getResetDate()).isEqualTo(DEFAULT_RESET_DATE);
        assertThat(testEmployee.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployee.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    void createEmployeeWithExistingId() throws Exception {
        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setUsername(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordHashIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setPasswordHash(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY)))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY)))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY)))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(DEFAULT_RESET_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmployeesWithEagerRelationshipsIsEnabled() throws Exception {
        when(employeeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(employeeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllEmployeesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(employeeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restEmployeeMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(employeeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL_ID, employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.passwordHash").value(DEFAULT_PASSWORD_HASH))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobileNo").value(DEFAULT_MOBILE_NO))
            .andExpect(jsonPath("$.department").value(DEFAULT_DEPARTMENT))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.langKey").value(DEFAULT_LANG_KEY))
            .andExpect(jsonPath("$.activationKey").value(DEFAULT_ACTIVATION_KEY))
            .andExpect(jsonPath("$.resetKey").value(DEFAULT_RESET_KEY))
            .andExpect(jsonPath("$.resetDate").value(DEFAULT_RESET_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdOn").value(DEFAULT_CREATED_ON.toString()));
    }

    @Test
    @Transactional
    void getEmployeesByIdFiltering() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        Long id = employee.getId();

        defaultEmployeeShouldBeFound("id.equals=" + id);
        defaultEmployeeShouldNotBeFound("id.notEquals=" + id);

        defaultEmployeeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.greaterThan=" + id);

        defaultEmployeeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEmployeeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEmployeesByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where title equals to DEFAULT_TITLE
        defaultEmployeeShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the employeeList where title equals to UPDATED_TITLE
        defaultEmployeeShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEmployeesByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultEmployeeShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the employeeList where title equals to UPDATED_TITLE
        defaultEmployeeShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllEmployeesByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where title is not null
        defaultEmployeeShouldBeFound("title.specified=true");

        // Get all the employeeList where title is null
        defaultEmployeeShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName equals to DEFAULT_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the employeeList where firstName equals to UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName is not null
        defaultEmployeeShouldBeFound("firstName.specified=true");

        // Get all the employeeList where firstName is null
        defaultEmployeeShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName contains DEFAULT_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName contains UPDATED_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where firstName does not contain DEFAULT_FIRST_NAME
        defaultEmployeeShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the employeeList where firstName does not contain UPDATED_FIRST_NAME
        defaultEmployeeShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultEmployeeShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeList where middleName equals to UPDATED_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultEmployeeShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the employeeList where middleName equals to UPDATED_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName is not null
        defaultEmployeeShouldBeFound("middleName.specified=true");

        // Get all the employeeList where middleName is null
        defaultEmployeeShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName contains DEFAULT_MIDDLE_NAME
        defaultEmployeeShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeList where middleName contains UPDATED_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultEmployeeShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the employeeList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultEmployeeShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName equals to DEFAULT_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the employeeList where lastName equals to UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName is not null
        defaultEmployeeShouldBeFound("lastName.specified=true");

        // Get all the employeeList where lastName is null
        defaultEmployeeShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName contains DEFAULT_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName contains UPDATED_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where lastName does not contain DEFAULT_LAST_NAME
        defaultEmployeeShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the employeeList where lastName does not contain UPDATED_LAST_NAME
        defaultEmployeeShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where grade equals to DEFAULT_GRADE
        defaultEmployeeShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the employeeList where grade equals to UPDATED_GRADE
        defaultEmployeeShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultEmployeeShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the employeeList where grade equals to UPDATED_GRADE
        defaultEmployeeShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where grade is not null
        defaultEmployeeShouldBeFound("grade.specified=true");

        // Get all the employeeList where grade is null
        defaultEmployeeShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where grade contains DEFAULT_GRADE
        defaultEmployeeShouldBeFound("grade.contains=" + DEFAULT_GRADE);

        // Get all the employeeList where grade contains UPDATED_GRADE
        defaultEmployeeShouldNotBeFound("grade.contains=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesByGradeNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where grade does not contain DEFAULT_GRADE
        defaultEmployeeShouldNotBeFound("grade.doesNotContain=" + DEFAULT_GRADE);

        // Get all the employeeList where grade does not contain UPDATED_GRADE
        defaultEmployeeShouldBeFound("grade.doesNotContain=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username equals to DEFAULT_USERNAME
        defaultEmployeeShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the employeeList where username equals to UPDATED_USERNAME
        defaultEmployeeShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultEmployeeShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the employeeList where username equals to UPDATED_USERNAME
        defaultEmployeeShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username is not null
        defaultEmployeeShouldBeFound("username.specified=true");

        // Get all the employeeList where username is null
        defaultEmployeeShouldNotBeFound("username.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username contains DEFAULT_USERNAME
        defaultEmployeeShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the employeeList where username contains UPDATED_USERNAME
        defaultEmployeeShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where username does not contain DEFAULT_USERNAME
        defaultEmployeeShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the employeeList where username does not contain UPDATED_USERNAME
        defaultEmployeeShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordHashIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passwordHash equals to DEFAULT_PASSWORD_HASH
        defaultEmployeeShouldBeFound("passwordHash.equals=" + DEFAULT_PASSWORD_HASH);

        // Get all the employeeList where passwordHash equals to UPDATED_PASSWORD_HASH
        defaultEmployeeShouldNotBeFound("passwordHash.equals=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordHashIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passwordHash in DEFAULT_PASSWORD_HASH or UPDATED_PASSWORD_HASH
        defaultEmployeeShouldBeFound("passwordHash.in=" + DEFAULT_PASSWORD_HASH + "," + UPDATED_PASSWORD_HASH);

        // Get all the employeeList where passwordHash equals to UPDATED_PASSWORD_HASH
        defaultEmployeeShouldNotBeFound("passwordHash.in=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordHashIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passwordHash is not null
        defaultEmployeeShouldBeFound("passwordHash.specified=true");

        // Get all the employeeList where passwordHash is null
        defaultEmployeeShouldNotBeFound("passwordHash.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordHashContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passwordHash contains DEFAULT_PASSWORD_HASH
        defaultEmployeeShouldBeFound("passwordHash.contains=" + DEFAULT_PASSWORD_HASH);

        // Get all the employeeList where passwordHash contains UPDATED_PASSWORD_HASH
        defaultEmployeeShouldNotBeFound("passwordHash.contains=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    void getAllEmployeesByPasswordHashNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where passwordHash does not contain DEFAULT_PASSWORD_HASH
        defaultEmployeeShouldNotBeFound("passwordHash.doesNotContain=" + DEFAULT_PASSWORD_HASH);

        // Get all the employeeList where passwordHash does not contain UPDATED_PASSWORD_HASH
        defaultEmployeeShouldBeFound("passwordHash.doesNotContain=" + UPDATED_PASSWORD_HASH);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email equals to DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the employeeList where email equals to UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email is not null
        defaultEmployeeShouldBeFound("email.specified=true");

        // Get all the employeeList where email is null
        defaultEmployeeShouldNotBeFound("email.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email contains DEFAULT_EMAIL
        defaultEmployeeShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the employeeList where email contains UPDATED_EMAIL
        defaultEmployeeShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where email does not contain DEFAULT_EMAIL
        defaultEmployeeShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the employeeList where email does not contain UPDATED_EMAIL
        defaultEmployeeShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNoIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo equals to DEFAULT_MOBILE_NO
        defaultEmployeeShouldBeFound("mobileNo.equals=" + DEFAULT_MOBILE_NO);

        // Get all the employeeList where mobileNo equals to UPDATED_MOBILE_NO
        defaultEmployeeShouldNotBeFound("mobileNo.equals=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNoIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo in DEFAULT_MOBILE_NO or UPDATED_MOBILE_NO
        defaultEmployeeShouldBeFound("mobileNo.in=" + DEFAULT_MOBILE_NO + "," + UPDATED_MOBILE_NO);

        // Get all the employeeList where mobileNo equals to UPDATED_MOBILE_NO
        defaultEmployeeShouldNotBeFound("mobileNo.in=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo is not null
        defaultEmployeeShouldBeFound("mobileNo.specified=true");

        // Get all the employeeList where mobileNo is null
        defaultEmployeeShouldNotBeFound("mobileNo.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNoContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo contains DEFAULT_MOBILE_NO
        defaultEmployeeShouldBeFound("mobileNo.contains=" + DEFAULT_MOBILE_NO);

        // Get all the employeeList where mobileNo contains UPDATED_MOBILE_NO
        defaultEmployeeShouldNotBeFound("mobileNo.contains=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByMobileNoNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where mobileNo does not contain DEFAULT_MOBILE_NO
        defaultEmployeeShouldNotBeFound("mobileNo.doesNotContain=" + DEFAULT_MOBILE_NO);

        // Get all the employeeList where mobileNo does not contain UPDATED_MOBILE_NO
        defaultEmployeeShouldBeFound("mobileNo.doesNotContain=" + UPDATED_MOBILE_NO);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where department equals to DEFAULT_DEPARTMENT
        defaultEmployeeShouldBeFound("department.equals=" + DEFAULT_DEPARTMENT);

        // Get all the employeeList where department equals to UPDATED_DEPARTMENT
        defaultEmployeeShouldNotBeFound("department.equals=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where department in DEFAULT_DEPARTMENT or UPDATED_DEPARTMENT
        defaultEmployeeShouldBeFound("department.in=" + DEFAULT_DEPARTMENT + "," + UPDATED_DEPARTMENT);

        // Get all the employeeList where department equals to UPDATED_DEPARTMENT
        defaultEmployeeShouldNotBeFound("department.in=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where department is not null
        defaultEmployeeShouldBeFound("department.specified=true");

        // Get all the employeeList where department is null
        defaultEmployeeShouldNotBeFound("department.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where department contains DEFAULT_DEPARTMENT
        defaultEmployeeShouldBeFound("department.contains=" + DEFAULT_DEPARTMENT);

        // Get all the employeeList where department contains UPDATED_DEPARTMENT
        defaultEmployeeShouldNotBeFound("department.contains=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByDepartmentNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where department does not contain DEFAULT_DEPARTMENT
        defaultEmployeeShouldNotBeFound("department.doesNotContain=" + DEFAULT_DEPARTMENT);

        // Get all the employeeList where department does not contain UPDATED_DEPARTMENT
        defaultEmployeeShouldBeFound("department.doesNotContain=" + UPDATED_DEPARTMENT);
    }

    @Test
    @Transactional
    void getAllEmployeesByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultEmployeeShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the employeeList where imageUrl equals to UPDATED_IMAGE_URL
        defaultEmployeeShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllEmployeesByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultEmployeeShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the employeeList where imageUrl equals to UPDATED_IMAGE_URL
        defaultEmployeeShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllEmployeesByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where imageUrl is not null
        defaultEmployeeShouldBeFound("imageUrl.specified=true");

        // Get all the employeeList where imageUrl is null
        defaultEmployeeShouldNotBeFound("imageUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where imageUrl contains DEFAULT_IMAGE_URL
        defaultEmployeeShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the employeeList where imageUrl contains UPDATED_IMAGE_URL
        defaultEmployeeShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllEmployeesByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultEmployeeShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the employeeList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultEmployeeShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    void getAllEmployeesByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where activated equals to DEFAULT_ACTIVATED
        defaultEmployeeShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the employeeList where activated equals to UPDATED_ACTIVATED
        defaultEmployeeShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllEmployeesByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultEmployeeShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the employeeList where activated equals to UPDATED_ACTIVATED
        defaultEmployeeShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllEmployeesByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where activated is not null
        defaultEmployeeShouldBeFound("activated.specified=true");

        // Get all the employeeList where activated is null
        defaultEmployeeShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLangKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where langKey equals to DEFAULT_LANG_KEY
        defaultEmployeeShouldBeFound("langKey.equals=" + DEFAULT_LANG_KEY);

        // Get all the employeeList where langKey equals to UPDATED_LANG_KEY
        defaultEmployeeShouldNotBeFound("langKey.equals=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByLangKeyIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where langKey in DEFAULT_LANG_KEY or UPDATED_LANG_KEY
        defaultEmployeeShouldBeFound("langKey.in=" + DEFAULT_LANG_KEY + "," + UPDATED_LANG_KEY);

        // Get all the employeeList where langKey equals to UPDATED_LANG_KEY
        defaultEmployeeShouldNotBeFound("langKey.in=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByLangKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where langKey is not null
        defaultEmployeeShouldBeFound("langKey.specified=true");

        // Get all the employeeList where langKey is null
        defaultEmployeeShouldNotBeFound("langKey.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByLangKeyContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where langKey contains DEFAULT_LANG_KEY
        defaultEmployeeShouldBeFound("langKey.contains=" + DEFAULT_LANG_KEY);

        // Get all the employeeList where langKey contains UPDATED_LANG_KEY
        defaultEmployeeShouldNotBeFound("langKey.contains=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByLangKeyNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where langKey does not contain DEFAULT_LANG_KEY
        defaultEmployeeShouldNotBeFound("langKey.doesNotContain=" + DEFAULT_LANG_KEY);

        // Get all the employeeList where langKey does not contain UPDATED_LANG_KEY
        defaultEmployeeShouldBeFound("langKey.doesNotContain=" + UPDATED_LANG_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByActivationKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where activationKey equals to DEFAULT_ACTIVATION_KEY
        defaultEmployeeShouldBeFound("activationKey.equals=" + DEFAULT_ACTIVATION_KEY);

        // Get all the employeeList where activationKey equals to UPDATED_ACTIVATION_KEY
        defaultEmployeeShouldNotBeFound("activationKey.equals=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByActivationKeyIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where activationKey in DEFAULT_ACTIVATION_KEY or UPDATED_ACTIVATION_KEY
        defaultEmployeeShouldBeFound("activationKey.in=" + DEFAULT_ACTIVATION_KEY + "," + UPDATED_ACTIVATION_KEY);

        // Get all the employeeList where activationKey equals to UPDATED_ACTIVATION_KEY
        defaultEmployeeShouldNotBeFound("activationKey.in=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByActivationKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where activationKey is not null
        defaultEmployeeShouldBeFound("activationKey.specified=true");

        // Get all the employeeList where activationKey is null
        defaultEmployeeShouldNotBeFound("activationKey.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByActivationKeyContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where activationKey contains DEFAULT_ACTIVATION_KEY
        defaultEmployeeShouldBeFound("activationKey.contains=" + DEFAULT_ACTIVATION_KEY);

        // Get all the employeeList where activationKey contains UPDATED_ACTIVATION_KEY
        defaultEmployeeShouldNotBeFound("activationKey.contains=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByActivationKeyNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where activationKey does not contain DEFAULT_ACTIVATION_KEY
        defaultEmployeeShouldNotBeFound("activationKey.doesNotContain=" + DEFAULT_ACTIVATION_KEY);

        // Get all the employeeList where activationKey does not contain UPDATED_ACTIVATION_KEY
        defaultEmployeeShouldBeFound("activationKey.doesNotContain=" + UPDATED_ACTIVATION_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByResetKeyIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where resetKey equals to DEFAULT_RESET_KEY
        defaultEmployeeShouldBeFound("resetKey.equals=" + DEFAULT_RESET_KEY);

        // Get all the employeeList where resetKey equals to UPDATED_RESET_KEY
        defaultEmployeeShouldNotBeFound("resetKey.equals=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByResetKeyIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where resetKey in DEFAULT_RESET_KEY or UPDATED_RESET_KEY
        defaultEmployeeShouldBeFound("resetKey.in=" + DEFAULT_RESET_KEY + "," + UPDATED_RESET_KEY);

        // Get all the employeeList where resetKey equals to UPDATED_RESET_KEY
        defaultEmployeeShouldNotBeFound("resetKey.in=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByResetKeyIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where resetKey is not null
        defaultEmployeeShouldBeFound("resetKey.specified=true");

        // Get all the employeeList where resetKey is null
        defaultEmployeeShouldNotBeFound("resetKey.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByResetKeyContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where resetKey contains DEFAULT_RESET_KEY
        defaultEmployeeShouldBeFound("resetKey.contains=" + DEFAULT_RESET_KEY);

        // Get all the employeeList where resetKey contains UPDATED_RESET_KEY
        defaultEmployeeShouldNotBeFound("resetKey.contains=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByResetKeyNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where resetKey does not contain DEFAULT_RESET_KEY
        defaultEmployeeShouldNotBeFound("resetKey.doesNotContain=" + DEFAULT_RESET_KEY);

        // Get all the employeeList where resetKey does not contain UPDATED_RESET_KEY
        defaultEmployeeShouldBeFound("resetKey.doesNotContain=" + UPDATED_RESET_KEY);
    }

    @Test
    @Transactional
    void getAllEmployeesByResetDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where resetDate equals to DEFAULT_RESET_DATE
        defaultEmployeeShouldBeFound("resetDate.equals=" + DEFAULT_RESET_DATE);

        // Get all the employeeList where resetDate equals to UPDATED_RESET_DATE
        defaultEmployeeShouldNotBeFound("resetDate.equals=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByResetDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where resetDate in DEFAULT_RESET_DATE or UPDATED_RESET_DATE
        defaultEmployeeShouldBeFound("resetDate.in=" + DEFAULT_RESET_DATE + "," + UPDATED_RESET_DATE);

        // Get all the employeeList where resetDate equals to UPDATED_RESET_DATE
        defaultEmployeeShouldNotBeFound("resetDate.in=" + UPDATED_RESET_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeesByResetDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where resetDate is not null
        defaultEmployeeShouldBeFound("resetDate.specified=true");

        // Get all the employeeList where resetDate is null
        defaultEmployeeShouldNotBeFound("resetDate.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy equals to DEFAULT_CREATED_BY
        defaultEmployeeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the employeeList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployeeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultEmployeeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the employeeList where createdBy equals to UPDATED_CREATED_BY
        defaultEmployeeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy is not null
        defaultEmployeeShouldBeFound("createdBy.specified=true");

        // Get all the employeeList where createdBy is null
        defaultEmployeeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy contains DEFAULT_CREATED_BY
        defaultEmployeeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the employeeList where createdBy contains UPDATED_CREATED_BY
        defaultEmployeeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultEmployeeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the employeeList where createdBy does not contain UPDATED_CREATED_BY
        defaultEmployeeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdOn equals to DEFAULT_CREATED_ON
        defaultEmployeeShouldBeFound("createdOn.equals=" + DEFAULT_CREATED_ON);

        // Get all the employeeList where createdOn equals to UPDATED_CREATED_ON
        defaultEmployeeShouldNotBeFound("createdOn.equals=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedOnIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdOn in DEFAULT_CREATED_ON or UPDATED_CREATED_ON
        defaultEmployeeShouldBeFound("createdOn.in=" + DEFAULT_CREATED_ON + "," + UPDATED_CREATED_ON);

        // Get all the employeeList where createdOn equals to UPDATED_CREATED_ON
        defaultEmployeeShouldNotBeFound("createdOn.in=" + UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void getAllEmployeesByCreatedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where createdOn is not null
        defaultEmployeeShouldBeFound("createdOn.specified=true");

        // Get all the employeeList where createdOn is null
        defaultEmployeeShouldNotBeFound("createdOn.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeesByEmployeeDetailsIsEqualToSomething() throws Exception {
        EmployeeDetails employeeDetails;
        if (TestUtil.findAll(em, EmployeeDetails.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            employeeDetails = EmployeeDetailsResourceIT.createEntity(em);
        } else {
            employeeDetails = TestUtil.findAll(em, EmployeeDetails.class).get(0);
        }
        em.persist(employeeDetails);
        em.flush();
        employee.setEmployeeDetails(employeeDetails);
        employeeRepository.saveAndFlush(employee);
        Long employeeDetailsId = employeeDetails.getId();

        // Get all the employeeList where employeeDetails equals to employeeDetailsId
        defaultEmployeeShouldBeFound("employeeDetailsId.equals=" + employeeDetailsId);

        // Get all the employeeList where employeeDetails equals to (employeeDetailsId + 1)
        defaultEmployeeShouldNotBeFound("employeeDetailsId.equals=" + (employeeDetailsId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationIsEqualToSomething() throws Exception {
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            organization = OrganizationResourceIT.createEntity(em);
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        em.persist(organization);
        em.flush();
        employee.setOrganization(organization);
        employeeRepository.saveAndFlush(employee);
        Long organizationId = organization.getId();

        // Get all the employeeList where organization equals to organizationId
        defaultEmployeeShouldBeFound("organizationId.equals=" + organizationId);

        // Get all the employeeList where organization equals to (organizationId + 1)
        defaultEmployeeShouldNotBeFound("organizationId.equals=" + (organizationId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByIncomeTaxSlabIsEqualToSomething() throws Exception {
        IncomeTaxSlab incomeTaxSlab;
        if (TestUtil.findAll(em, IncomeTaxSlab.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            incomeTaxSlab = IncomeTaxSlabResourceIT.createEntity(em);
        } else {
            incomeTaxSlab = TestUtil.findAll(em, IncomeTaxSlab.class).get(0);
        }
        em.persist(incomeTaxSlab);
        em.flush();
        employee.setIncomeTaxSlab(incomeTaxSlab);
        employeeRepository.saveAndFlush(employee);
        Long incomeTaxSlabId = incomeTaxSlab.getId();

        // Get all the employeeList where incomeTaxSlab equals to incomeTaxSlabId
        defaultEmployeeShouldBeFound("incomeTaxSlabId.equals=" + incomeTaxSlabId);

        // Get all the employeeList where incomeTaxSlab equals to (incomeTaxSlabId + 1)
        defaultEmployeeShouldNotBeFound("incomeTaxSlabId.equals=" + (incomeTaxSlabId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesBySecurityPermissionIsEqualToSomething() throws Exception {
        SecurityPermission securityPermission;
        if (TestUtil.findAll(em, SecurityPermission.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            securityPermission = SecurityPermissionResourceIT.createEntity(em);
        } else {
            securityPermission = TestUtil.findAll(em, SecurityPermission.class).get(0);
        }
        em.persist(securityPermission);
        em.flush();
        employee.addSecurityPermission(securityPermission);
        employeeRepository.saveAndFlush(employee);
        Long securityPermissionId = securityPermission.getId();

        // Get all the employeeList where securityPermission equals to securityPermissionId
        defaultEmployeeShouldBeFound("securityPermissionId.equals=" + securityPermissionId);

        // Get all the employeeList where securityPermission equals to (securityPermissionId + 1)
        defaultEmployeeShouldNotBeFound("securityPermissionId.equals=" + (securityPermissionId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesBySecurityRoleIsEqualToSomething() throws Exception {
        SecurityRole securityRole;
        if (TestUtil.findAll(em, SecurityRole.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            securityRole = SecurityRoleResourceIT.createEntity(em);
        } else {
            securityRole = TestUtil.findAll(em, SecurityRole.class).get(0);
        }
        em.persist(securityRole);
        em.flush();
        employee.addSecurityRole(securityRole);
        employeeRepository.saveAndFlush(employee);
        Long securityRoleId = securityRole.getId();

        // Get all the employeeList where securityRole equals to securityRoleId
        defaultEmployeeShouldBeFound("securityRoleId.equals=" + securityRoleId);

        // Get all the employeeList where securityRole equals to (securityRoleId + 1)
        defaultEmployeeShouldNotBeFound("securityRoleId.equals=" + (securityRoleId + 1));
    }

    @Test
    @Transactional
    void getAllEmployeesByOrganizationPoliciesIsEqualToSomething() throws Exception {
        OrganizationPolicies organizationPolicies;
        if (TestUtil.findAll(em, OrganizationPolicies.class).isEmpty()) {
            employeeRepository.saveAndFlush(employee);
            organizationPolicies = OrganizationPoliciesResourceIT.createEntity(em);
        } else {
            organizationPolicies = TestUtil.findAll(em, OrganizationPolicies.class).get(0);
        }
        em.persist(organizationPolicies);
        em.flush();
        employee.addOrganizationPolicies(organizationPolicies);
        employeeRepository.saveAndFlush(employee);
        Long organizationPoliciesId = organizationPolicies.getId();

        // Get all the employeeList where organizationPolicies equals to organizationPoliciesId
        defaultEmployeeShouldBeFound("organizationPoliciesId.equals=" + organizationPoliciesId);

        // Get all the employeeList where organizationPolicies equals to (organizationPoliciesId + 1)
        defaultEmployeeShouldNotBeFound("organizationPoliciesId.equals=" + (organizationPoliciesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].passwordHash").value(hasItem(DEFAULT_PASSWORD_HASH)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobileNo").value(hasItem(DEFAULT_MOBILE_NO)))
            .andExpect(jsonPath("$.[*].department").value(hasItem(DEFAULT_DEPARTMENT)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].langKey").value(hasItem(DEFAULT_LANG_KEY)))
            .andExpect(jsonPath("$.[*].activationKey").value(hasItem(DEFAULT_ACTIVATION_KEY)))
            .andExpect(jsonPath("$.[*].resetKey").value(hasItem(DEFAULT_RESET_KEY)))
            .andExpect(jsonPath("$.[*].resetDate").value(hasItem(DEFAULT_RESET_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(DEFAULT_CREATED_ON.toString())));

        // Check, that the count call also returns 1
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .grade(UPDATED_GRADE)
            .username(UPDATED_USERNAME)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .department(UPDATED_DEPARTMENT)
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployee.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEmployee.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testEmployee.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testEmployee.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testEmployee.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testEmployee.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
        assertThat(testEmployee.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testEmployee.getResetKey()).isEqualTo(UPDATED_RESET_KEY);
        assertThat(testEmployee.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
        assertThat(testEmployee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployee.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void putNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .department(UPDATED_DEPARTMENT)
            .activationKey(UPDATED_ACTIVATION_KEY);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testEmployee.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmployee.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testEmployee.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testEmployee.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testEmployee.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testEmployee.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testEmployee.getLangKey()).isEqualTo(DEFAULT_LANG_KEY);
        assertThat(testEmployee.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testEmployee.getResetKey()).isEqualTo(DEFAULT_RESET_KEY);
        assertThat(testEmployee.getResetDate()).isEqualTo(DEFAULT_RESET_DATE);
        assertThat(testEmployee.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testEmployee.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeWithPatch() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee using partial update
        Employee partialUpdatedEmployee = new Employee();
        partialUpdatedEmployee.setId(employee.getId());

        partialUpdatedEmployee
            .title(UPDATED_TITLE)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .grade(UPDATED_GRADE)
            .username(UPDATED_USERNAME)
            .passwordHash(UPDATED_PASSWORD_HASH)
            .email(UPDATED_EMAIL)
            .mobileNo(UPDATED_MOBILE_NO)
            .department(UPDATED_DEPARTMENT)
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED)
            .langKey(UPDATED_LANG_KEY)
            .activationKey(UPDATED_ACTIVATION_KEY)
            .resetKey(UPDATED_RESET_KEY)
            .resetDate(UPDATED_RESET_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON);

        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEmployee.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployee))
            )
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testEmployee.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testEmployee.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testEmployee.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testEmployee.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployee.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testEmployee.getPasswordHash()).isEqualTo(UPDATED_PASSWORD_HASH);
        assertThat(testEmployee.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEmployee.getMobileNo()).isEqualTo(UPDATED_MOBILE_NO);
        assertThat(testEmployee.getDepartment()).isEqualTo(UPDATED_DEPARTMENT);
        assertThat(testEmployee.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testEmployee.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testEmployee.getLangKey()).isEqualTo(UPDATED_LANG_KEY);
        assertThat(testEmployee.getActivationKey()).isEqualTo(UPDATED_ACTIVATION_KEY);
        assertThat(testEmployee.getResetKey()).isEqualTo(UPDATED_RESET_KEY);
        assertThat(testEmployee.getResetDate()).isEqualTo(UPDATED_RESET_DATE);
        assertThat(testEmployee.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testEmployee.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
    }

    @Test
    @Transactional
    void patchNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, employeeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();
        employee.setId(count.incrementAndGet());

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEmployeeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(employeeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc
            .perform(delete(ENTITY_API_URL_ID, employee.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
