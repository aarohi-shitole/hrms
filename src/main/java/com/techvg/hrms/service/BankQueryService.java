package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.Bank;
import com.techvg.hrms.repository.BankRepository;
import com.techvg.hrms.service.criteria.BankCriteria;
import com.techvg.hrms.service.dto.BankDTO;
import com.techvg.hrms.service.mapper.BankMapper;
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
 * Service for executing complex queries for {@link Bank} entities in the database.
 * The main input is a {@link BankCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BankDTO} or a {@link Page} of {@link BankDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BankQueryService extends QueryService<Bank> {

    private final Logger log = LoggerFactory.getLogger(BankQueryService.class);

    private final BankRepository bankRepository;

    private final BankMapper bankMapper;

    public BankQueryService(BankRepository bankRepository, BankMapper bankMapper) {
        this.bankRepository = bankRepository;
        this.bankMapper = bankMapper;
    }

    /**
     * Return a {@link List} of {@link BankDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BankDTO> findByCriteria(BankCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Bank> specification = createSpecification(criteria);
        return bankMapper.toDto(bankRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BankDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BankDTO> findByCriteria(BankCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Bank> specification = createSpecification(criteria);
        return bankRepository.findAll(specification, page).map(bankMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BankCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Bank> specification = createSpecification(criteria);
        return bankRepository.count(specification);
    }

    /**
     * Function to convert {@link BankCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Bank> createSpecification(BankCriteria criteria) {
        Specification<Bank> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Bank_.id));
            }
            if (criteria.getAccountNo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountNo(), Bank_.accountNo));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Bank_.name));
            }
            if (criteria.getBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranch(), Bank_.branch));
            }
            if (criteria.getIfscCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfscCode(), Bank_.ifscCode));
            }
            if (criteria.getMcirCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMcirCode(), Bank_.mcirCode));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Bank_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Bank_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Bank_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Bank_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Bank_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Bank_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Bank_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Bank_.freeField3));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getEmployeeId(), root -> root.join(Bank_.employee, JoinType.LEFT).get(Employee_.id))
                    );
            }
            if (criteria.getOrganizationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizationId(),
                            root -> root.join(Bank_.organization, JoinType.LEFT).get(Organization_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
