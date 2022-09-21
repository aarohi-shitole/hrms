package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.EmployeeSeperation;
import com.techvg.hrms.repository.EmployeeSeperationRepository;
import com.techvg.hrms.service.criteria.EmployeeSeperationCriteria;
import com.techvg.hrms.service.dto.EmployeeSeperationDTO;
import com.techvg.hrms.service.mapper.EmployeeSeperationMapper;
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
 * Service for executing complex queries for {@link EmployeeSeperation} entities in the database.
 * The main input is a {@link EmployeeSeperationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeSeperationDTO} or a {@link Page} of {@link EmployeeSeperationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeSeperationQueryService extends QueryService<EmployeeSeperation> {

    private final Logger log = LoggerFactory.getLogger(EmployeeSeperationQueryService.class);

    private final EmployeeSeperationRepository employeeSeperationRepository;

    private final EmployeeSeperationMapper employeeSeperationMapper;

    public EmployeeSeperationQueryService(
        EmployeeSeperationRepository employeeSeperationRepository,
        EmployeeSeperationMapper employeeSeperationMapper
    ) {
        this.employeeSeperationRepository = employeeSeperationRepository;
        this.employeeSeperationMapper = employeeSeperationMapper;
    }

    /**
     * Return a {@link List} of {@link EmployeeSeperationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeSeperationDTO> findByCriteria(EmployeeSeperationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeSeperation> specification = createSpecification(criteria);
        return employeeSeperationMapper.toDto(employeeSeperationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeSeperationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSeperationDTO> findByCriteria(EmployeeSeperationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeSeperation> specification = createSpecification(criteria);
        return employeeSeperationRepository.findAll(specification, page).map(employeeSeperationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeSeperationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeSeperation> specification = createSpecification(criteria);
        return employeeSeperationRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeSeperationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<EmployeeSeperation> createSpecification(EmployeeSeperationCriteria criteria) {
        Specification<EmployeeSeperation> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), EmployeeSeperation_.id));
            }
            if (criteria.getReasonForSeperation() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getReasonForSeperation(), EmployeeSeperation_.reasonForSeperation));
            }
            if (criteria.getSeperationDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getSeperationDate(), EmployeeSeperation_.seperationDate));
            }
            if (criteria.getComment() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComment(), EmployeeSeperation_.comment));
            }
            if (criteria.getSeperationStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getSeperationStatus(), EmployeeSeperation_.seperationStatus));
            }
            if (criteria.getOtherReason() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOtherReason(), EmployeeSeperation_.otherReason));
            }
            if (criteria.getNagotiatedPeriod() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getNagotiatedPeriod(), EmployeeSeperation_.nagotiatedPeriod));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), EmployeeSeperation_.createdBy));
            }
            if (criteria.getUpdatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdatedOn(), EmployeeSeperation_.updatedOn));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), EmployeeSeperation_.createdOn));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), EmployeeSeperation_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getLastModifiedBy(), EmployeeSeperation_.lastModifiedBy));
            }
            if (criteria.getFreefield1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield1(), EmployeeSeperation_.freefield1));
            }
            if (criteria.getFreefield2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield2(), EmployeeSeperation_.freefield2));
            }
            if (criteria.getFreefield3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield3(), EmployeeSeperation_.freefield3));
            }
            if (criteria.getFreefield4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield4(), EmployeeSeperation_.freefield4));
            }
            if (criteria.getFreefield5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield5(), EmployeeSeperation_.freefield5));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(EmployeeSeperation_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
