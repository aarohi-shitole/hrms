package com.techvg.hrms.repository;

import com.techvg.hrms.domain.SecurityPermission;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SecurityPermission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SecurityPermissionRepository
    extends JpaRepository<SecurityPermission, Long>, JpaSpecificationExecutor<SecurityPermission> {}
