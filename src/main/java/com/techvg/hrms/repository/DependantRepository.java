package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Dependant;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Dependant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DependantRepository extends JpaRepository<Dependant, Long>, JpaSpecificationExecutor<Dependant> {}
