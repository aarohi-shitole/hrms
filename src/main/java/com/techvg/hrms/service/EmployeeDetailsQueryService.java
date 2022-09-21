package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.EmployeeDetails;
import com.techvg.hrms.repository.EmployeeDetailsRepository;
import com.techvg.hrms.service.criteria.EmployeeDetailsCriteria;
import com.techvg.hrms.service.dto.EmployeeDetailsDTO;
import com.techvg.hrms.service.mapper.EmployeeDetailsMapper;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EmployeeDetails} entities in the database.
 * The main input is a {@link EmployeeDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDetailsDTO} or a {@link Page} of {@link EmployeeDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeDetailsQueryService extends QueryService<EmployeeDetails> {

    private final Logger log = LoggerFactory.getLogger(EmployeeDetailsQueryService.class);

    private final EmployeeDetailsRepository employeeDetailsRepository;

    private final EmployeeDetailsMapper employeeDetailsMapper;

    public EmployeeDetailsQueryService(EmployeeDetailsRepository employeeDetailsRepository, EmployeeDetailsMapper employeeDetailsMapper) {
        this.employeeDetailsRepository = employeeDetailsRepository;
        this.employeeDetailsMapper = employeeDetailsMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDetailsDTO> findByCriteria(EmployeeDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeDetails> specification = createSpecification(criteria);
        return employeeDetailsMapper.toDto(employeeDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDetailsDTO> findByCriteria(EmployeeDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeDetails> specification = createSpecification(criteria);
        return employeeDetailsRepository.findAll(specification, page).map(employeeDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeDetails> specification = createSpecification(criteria);
        return employeeDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeDetails> createSpecification(EmployeeDetailsCriteria criteria) {
        Specification<EmployeeDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeDetails_.id));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), EmployeeDetails_.age));
            }
            if (criteria.getFatherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFatherName(), EmployeeDetails_.fatherName));
            }
            if (criteria.getMotherName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMotherName(), EmployeeDetails_.motherName));
            }
            if (criteria.getEmployeeId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployeeId(), EmployeeDetails_.employeeId));
            }
            if (criteria.getYearsOfExperience() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getYearsOfExperience(), EmployeeDetails_.yearsOfExperience));
            }
            if (criteria.getNotes() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNotes(), EmployeeDetails_.notes));
            }
            if (criteria.getBloodGroup() != null) {
                specification = specification.and(buildSpecification(criteria.getBloodGroup(), EmployeeDetails_.bloodGroup));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBirthDate(), EmployeeDetails_.birthDate));
            }
            if (criteria.getDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDesignation(), EmployeeDetails_.designation));
            }
            if (criteria.getExpertise() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpertise(), EmployeeDetails_.expertise));
            }
            if (criteria.getJobDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getJobDescription(), EmployeeDetails_.jobDescription));
            }
            if (criteria.getMaritalStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getMaritalStatus(), EmployeeDetails_.maritalStatus));
            }
            if (criteria.getSecondaryContact() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getSecondaryContact(), EmployeeDetails_.secondaryContact));
            }
            if (criteria.getHobbies() != null) {
                specification = specification.and(buildStringSpecification(criteria.getHobbies(), EmployeeDetails_.hobbies));
            }
            if (criteria.getAreaInterest() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAreaInterest(), EmployeeDetails_.areaInterest));
            }
            if (criteria.getNoOfDependent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNoOfDependent(), EmployeeDetails_.noOfDependent));
            }
            if (criteria.getLanguageKnown() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLanguageKnown(), EmployeeDetails_.languageKnown));
            }
            if (criteria.getNatinality() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNatinality(), EmployeeDetails_.natinality));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), EmployeeDetails_.description));
            }
            if (criteria.getDepartment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepartment(), EmployeeDetails_.department));
            }
            if (criteria.getJoiningDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getJoiningDate(), EmployeeDetails_.joiningDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), EmployeeDetails_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), EmployeeDetails_.createdOn));
            }
            if (criteria.getFreefield1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield1(), EmployeeDetails_.freefield1));
            }
            if (criteria.getFreefield2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield2(), EmployeeDetails_.freefield2));
            }
            if (criteria.getFreefield3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield3(), EmployeeDetails_.freefield3));
            }
            if (criteria.getFreefield4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield4(), EmployeeDetails_.freefield4));
            }
            if (criteria.getFreefield5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield5(), EmployeeDetails_.freefield5));
            }
        }
        return specification;
    }
}
