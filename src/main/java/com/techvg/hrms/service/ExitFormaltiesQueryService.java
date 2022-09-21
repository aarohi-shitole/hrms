package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.ExitFormalties;
import com.techvg.hrms.repository.ExitFormaltiesRepository;
import com.techvg.hrms.service.criteria.ExitFormaltiesCriteria;
import com.techvg.hrms.service.dto.ExitFormaltiesDTO;
import com.techvg.hrms.service.mapper.ExitFormaltiesMapper;
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
 * Service for executing complex queries for {@link ExitFormalties} entities in the database.
 * The main input is a {@link ExitFormaltiesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExitFormaltiesDTO} or a {@link Page} of {@link ExitFormaltiesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExitFormaltiesQueryService extends QueryService<ExitFormalties> {

    private final Logger log = LoggerFactory.getLogger(ExitFormaltiesQueryService.class);

    private final ExitFormaltiesRepository exitFormaltiesRepository;

    private final ExitFormaltiesMapper exitFormaltiesMapper;

    public ExitFormaltiesQueryService(ExitFormaltiesRepository exitFormaltiesRepository, ExitFormaltiesMapper exitFormaltiesMapper) {
        this.exitFormaltiesRepository = exitFormaltiesRepository;
        this.exitFormaltiesMapper = exitFormaltiesMapper;
    }

    /**
     * Return a {@link List} of {@link ExitFormaltiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExitFormaltiesDTO> findByCriteria(ExitFormaltiesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ExitFormalties> specification = createSpecification(criteria);
        return exitFormaltiesMapper.toDto(exitFormaltiesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ExitFormaltiesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExitFormaltiesDTO> findByCriteria(ExitFormaltiesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ExitFormalties> specification = createSpecification(criteria);
        return exitFormaltiesRepository.findAll(specification, page).map(exitFormaltiesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ExitFormaltiesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ExitFormalties> specification = createSpecification(criteria);
        return exitFormaltiesRepository.count(specification);
    }

    /**
     * Function to convert {@link ExitFormaltiesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ExitFormalties> createSpecification(ExitFormaltiesCriteria criteria) {
        Specification<ExitFormalties> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ExitFormalties_.id));
            }
            if (criteria.getSecurity() != null) {
                specification = specification.and(buildSpecification(criteria.getSecurity(), ExitFormalties_.security));
            }
            if (criteria.getFeedback() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFeedback(), ExitFormalties_.feedback));
            }
            if (criteria.getExitDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExitDate(), ExitFormalties_.exitDate));
            }
            if (criteria.getExitInterview() != null) {
                specification = specification.and(buildSpecification(criteria.getExitInterview(), ExitFormalties_.exitInterview));
            }
            if (criteria.getLibNoDue() != null) {
                specification = specification.and(buildSpecification(criteria.getLibNoDue(), ExitFormalties_.libNoDue));
            }
            if (criteria.getNoticePeriodServed() != null) {
                specification = specification.and(buildSpecification(criteria.getNoticePeriodServed(), ExitFormalties_.noticePeriodServed));
            }
            if (criteria.getClearence() != null) {
                specification = specification.and(buildSpecification(criteria.getClearence(), ExitFormalties_.clearence));
            }
            if (criteria.getOrgAssets() != null) {
                specification = specification.and(buildSpecification(criteria.getOrgAssets(), ExitFormalties_.orgAssets));
            }
            if (criteria.getOrgVehical() != null) {
                specification = specification.and(buildSpecification(criteria.getOrgVehical(), ExitFormalties_.orgVehical));
            }
            if (criteria.getResignLetter() != null) {
                specification = specification.and(buildSpecification(criteria.getResignLetter(), ExitFormalties_.resignLetter));
            }
            if (criteria.getShares() != null) {
                specification = specification.and(buildStringSpecification(criteria.getShares(), ExitFormalties_.shares));
            }
            if (criteria.getStaffWelfare() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStaffWelfare(), ExitFormalties_.staffWelfare));
            }
            if (criteria.getWorkForOrg() != null) {
                specification = specification.and(buildSpecification(criteria.getWorkForOrg(), ExitFormalties_.workForOrg));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), ExitFormalties_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), ExitFormalties_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), ExitFormalties_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), ExitFormalties_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), ExitFormalties_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), ExitFormalties_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), ExitFormalties_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), ExitFormalties_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), ExitFormalties_.freeField3));
            }
            if (criteria.getFreefield4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield4(), ExitFormalties_.freefield4));
            }
            if (criteria.getFreefield5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield5(), ExitFormalties_.freefield5));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(ExitFormalties_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
