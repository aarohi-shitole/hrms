package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Documents;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Documents entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long>, JpaSpecificationExecutor<Documents> {}
