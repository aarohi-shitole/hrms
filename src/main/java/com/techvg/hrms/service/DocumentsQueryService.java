package com.techvg.hrms.service;

import com.techvg.hrms.domain.*; // for static metamodels
import com.techvg.hrms.domain.Documents;
import com.techvg.hrms.repository.DocumentsRepository;
import com.techvg.hrms.service.criteria.DocumentsCriteria;
import com.techvg.hrms.service.dto.DocumentsDTO;
import com.techvg.hrms.service.mapper.DocumentsMapper;
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
 * Service for executing complex queries for {@link Documents} entities in the database.
 * The main input is a {@link DocumentsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DocumentsDTO} or a {@link Page} of {@link DocumentsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DocumentsQueryService extends QueryService<Documents> {

    private final Logger log = LoggerFactory.getLogger(DocumentsQueryService.class);

    private final DocumentsRepository documentsRepository;

    private final DocumentsMapper documentsMapper;

    public DocumentsQueryService(DocumentsRepository documentsRepository, DocumentsMapper documentsMapper) {
        this.documentsRepository = documentsRepository;
        this.documentsMapper = documentsMapper;
    }

    /**
     * Return a {@link List} of {@link DocumentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DocumentsDTO> findByCriteria(DocumentsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Documents> specification = createSpecification(criteria);
        return documentsMapper.toDto(documentsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DocumentsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DocumentsDTO> findByCriteria(DocumentsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Documents> specification = createSpecification(criteria);
        return documentsRepository.findAll(specification, page).map(documentsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DocumentsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Documents> specification = createSpecification(criteria);
        return documentsRepository.count(specification);
    }

    /**
     * Function to convert {@link DocumentsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Documents> createSpecification(DocumentsCriteria criteria) {
        Specification<Documents> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Documents_.id));
            }
            if (criteria.getReferenceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReferenceId(), Documents_.referenceId));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Documents_.type));
            }
            if (criteria.getSubtype() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSubtype(), Documents_.subtype));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), Documents_.fileName));
            }
            if (criteria.getFilePath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFilePath(), Documents_.filePath));
            }
            if (criteria.getFileUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileUrl(), Documents_.fileUrl));
            }
            if (criteria.getIssuedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIssuedDate(), Documents_.issuedDate));
            }
            if (criteria.getValidUpTo() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValidUpTo(), Documents_.validUpTo));
            }
            if (criteria.getPlaceOfIssued() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlaceOfIssued(), Documents_.placeOfIssued));
            }
            if (criteria.getHasVerified() != null) {
                specification = specification.and(buildSpecification(criteria.getHasVerified(), Documents_.hasVerified));
            }
            if (criteria.getRemark() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRemark(), Documents_.remark));
            }
            if (criteria.getLastModified() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModified(), Documents_.lastModified));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Documents_.lastModifiedBy));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Documents_.createdBy));
            }
            if (criteria.getCreatedOn() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedOn(), Documents_.createdOn));
            }
            if (criteria.getIsDeleted() != null) {
                specification = specification.and(buildSpecification(criteria.getIsDeleted(), Documents_.isDeleted));
            }
            if (criteria.getFreeField1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField1(), Documents_.freeField1));
            }
            if (criteria.getFreeField2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField2(), Documents_.freeField2));
            }
            if (criteria.getFreeField3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFreeField3(), Documents_.freeField3));
            }
            if (criteria.getEmployeeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getEmployeeId(),
                            root -> root.join(Documents_.employee, JoinType.LEFT).get(Employee_.id)
                        )
                    );
            }
            if (criteria.getOrganizationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getOrganizationId(),
                            root -> root.join(Documents_.organization, JoinType.LEFT).get(Organization_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
