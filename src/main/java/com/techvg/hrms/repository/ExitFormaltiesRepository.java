package com.techvg.hrms.repository;

import com.techvg.hrms.domain.ExitFormalties;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ExitFormalties entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExitFormaltiesRepository extends JpaRepository<ExitFormalties, Long>, JpaSpecificationExecutor<ExitFormalties> {}
