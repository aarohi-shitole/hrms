package com.techvg.hrms.repository;

import com.techvg.hrms.domain.EmployeeSeperation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EmployeeSeperation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSeperationRepository
    extends JpaRepository<EmployeeSeperation, Long>, JpaSpecificationExecutor<EmployeeSeperation> {}
