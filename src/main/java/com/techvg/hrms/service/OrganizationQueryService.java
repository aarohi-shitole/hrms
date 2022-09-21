package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.Organization;
import com.techvg.hrms.repository.OrganizationRepository;
import com.techvg.hrms.service.criteria.OrganizationCriteria;
import com.techvg.hrms.service.dto.OrganizationDTO;
import com.techvg.hrms.service.mapper.OrganizationMapper;
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
 * Service for executing complex queries for {@link Organization} entities in the database.
 * The main input is a {@link OrganizationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OrganizationDTO} or a {@link Page} of {@link OrganizationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OrganizationQueryService extends QueryService<Organization> {

    private final Logger log = LoggerFactory.getLogger(OrganizationQueryService.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    public OrganizationQueryService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
    }

    /**
     * Return a {@link List} of {@link OrganizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OrganizationDTO> findByCriteria(OrganizationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Organization> specification = createSpecification(criteria);
        return organizationMapper.toDto(organizationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OrganizationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OrganizationDTO> findByCriteria(OrganizationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Organization> specification = createSpecification(criteria);
        return organizationRepository.findAll(specification, page).map(organizationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OrganizationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Organization> specification = createSpecification(criteria);
        return organizationRepository.count(specification);
    }

    /**
     * Function to convert {@link OrganizationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Organization> createSpecification(OrganizationCriteria criteria) {
        Specification<Organization> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Organization_.id));
            }
            if (criteria.getOrgName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getOrgName(), Organization_.orgName));
            }
            if (criteria.getBranchcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranchcode(), Organization_.branchcode));
            }
            if (criteria.getRegion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRegion(), Organization_.region));
            }
            if (criteria.getIfscCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIfscCode(), Organization_.ifscCode));
            }
            if (criteria.getMicrCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMicrCode(), Organization_.micrCode));
            }
            if (criteria.getSwiftCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSwiftCode(), Organization_.swiftCode));
            }
            if (criteria.getIbanCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIbanCode(), Organization_.ibanCode));
            }
            if (criteria.getRoutingTransitNo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRoutingTransitNo(), Organization_.routingTransitNo));
            }
            if (criteria.getRegNumber() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRegNumber(), Organization_.regNumber));
            }
            if (criteria.getGstinNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGstinNumber(), Organization_.gstinNumber));
            }
            if (criteria.getPanNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPanNumber(), Organization_.panNumber));
            }
            if (criteria.getTanNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTanNumber(), Organization_.tanNumber));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Organization_.phone));
            }
            if (criteria.getEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmail(), Organization_.email));
            }
            if (criteria.getWebSite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWebSite(), Organization_.webSite));
            }
            if (criteria.getFax() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFax(), Organization_.fax));
            }
            if (criteria.getOrgType() != null) {
                specification = specification.and(buildSpecification(criteria.getOrgType(), Organization_.orgType));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Organization_.createdOn));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Organization_.createdBy));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Organization_.description));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIsDeleted(), Organization_.isDeleted));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Organization_.status));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Organization_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Organization_.lastModifiedBy));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Organization_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Organization_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Organization_.freeField3));
            }
            if (criteria.getFreeField4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField4(), Organization_.freeField4));
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressId(),
                            root -> root.join(Organization_.address, JoinType.LEFT).get(Address_.id)
                        )
                    );
            }
            if (criteria.getOrganizationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizationId(),
                            root -> root.join(Organization_.organization, JoinType.LEFT).get(Organization_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
