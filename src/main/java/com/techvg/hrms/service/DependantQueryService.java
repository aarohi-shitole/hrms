package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.Dependant;
import com.techvg.hrms.repository.DependantRepository;
import com.techvg.hrms.service.criteria.DependantCriteria;
import com.techvg.hrms.service.dto.DependantDTO;
import com.techvg.hrms.service.mapper.DependantMapper;
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
 * Service for executing complex queries for {@link Dependant} entities in the database.
 * The main input is a {@link DependantCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DependantDTO} or a {@link Page} of {@link DependantDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DependantQueryService extends QueryService<Dependant> {

    private final Logger log = LoggerFactory.getLogger(DependantQueryService.class);

    private final DependantRepository dependantRepository;

    private final DependantMapper dependantMapper;

    public DependantQueryService(DependantRepository dependantRepository, DependantMapper dependantMapper) {
        this.dependantRepository = dependantRepository;
        this.dependantMapper = dependantMapper;
    }

    /**
     * Return a {@link List} of {@link DependantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DependantDTO> findByCriteria(DependantCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dependant> specification = createSpecification(criteria);
        return dependantMapper.toDto(dependantRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DependantDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DependantDTO> findByCriteria(DependantCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dependant> specification = createSpecification(criteria);
        return dependantRepository.findAll(specification, page).map(dependantMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DependantCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Dependant> specification = createSpecification(criteria);
        return dependantRepository.count(specification);
    }

    /**
     * Function to convert {@link DependantCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Dependant> createSpecification(DependantCriteria criteria) {
        Specification<Dependant> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Dependant_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Dependant_.name));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAge(), Dependant_.age));
            }
            if (criteria.getDateOfBirth() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDateOfBirth(), Dependant_.dateOfBirth));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Dependant_.type));
            }
            if (criteria.getMobile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMobile(), Dependant_.mobile));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Dependant_.address));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Dependant_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Dependant_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Dependant_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Dependant_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Dependant_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Dependant_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Dependant_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Dependant_.freeField3));
            }
            if (criteria.getFreefield4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield4(), Dependant_.freefield4));
            }
            if (criteria.getFreefield5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreefield5(), Dependant_.freefield5));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(Dependant_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
