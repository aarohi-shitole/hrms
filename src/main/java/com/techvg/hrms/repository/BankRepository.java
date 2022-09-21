package com.techvg.hrms.repository;

import com.techvg.hrms.domain.Bank;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bank entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BankRepository extends JpaRepository<Bank, Long>, JpaSpecificationExecutor<Bank> {}
