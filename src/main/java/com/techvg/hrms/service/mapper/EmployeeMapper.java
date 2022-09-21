package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.EmployeeDetails;
import com.techvg.hrms.domain.IncomeTaxSlab;
import com.techvg.hrms.domain.Organization;
import com.techvg.hrms.domain.SecurityPermission;
import com.techvg.hrms.domain.SecurityRole;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.EmployeeDetailsDTO;
import com.techvg.hrms.service.dto.IncomeTaxSlabDTO;
import com.techvg.hrms.service.dto.OrganizationDTO;
import com.techvg.hrms.service.dto.SecurityPermissionDTO;
import com.techvg.hrms.service.dto.SecurityRoleDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "employeeDetails", source = "employeeDetails", qualifiedByName = "employeeDetailsId")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    @Mapping(target = "incomeTaxSlab", source = "incomeTaxSlab", qualifiedByName = "incomeTaxSlabId")
    @Mapping(target = "securityPermissions", source = "securityPermissions", qualifiedByName = "securityPermissionPermissionNameSet")
    @Mapping(target = "securityRoles", source = "securityRoles", qualifiedByName = "securityRoleRoleNameSet")
    EmployeeDTO toDto(Employee s);

    @Mapping(target = "removeSecurityPermission", ignore = true)
    @Mapping(target = "removeSecurityRole", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    @Named("employeeDetailsId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDetailsDTO toDtoEmployeeDetailsId(EmployeeDetails employeeDetails);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);

    @Named("incomeTaxSlabId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IncomeTaxSlabDTO toDtoIncomeTaxSlabId(IncomeTaxSlab incomeTaxSlab);

    @Named("securityPermissionPermissionName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "permissionName", source = "permissionName")
    SecurityPermissionDTO toDtoSecurityPermissionPermissionName(SecurityPermission securityPermission);

    @Named("securityPermissionPermissionNameSet")
    default Set<SecurityPermissionDTO> toDtoSecurityPermissionPermissionNameSet(Set<SecurityPermission> securityPermission) {
        return securityPermission.stream().map(this::toDtoSecurityPermissionPermissionName).collect(Collectors.toSet());
    }

    @Named("securityRoleRoleName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "roleName", source = "roleName")
    SecurityRoleDTO toDtoSecurityRoleRoleName(SecurityRole securityRole);

    @Named("securityRoleRoleNameSet")
    default Set<SecurityRoleDTO> toDtoSecurityRoleRoleNameSet(Set<SecurityRole> securityRole) {
        return securityRole.stream().map(this::toDtoSecurityRoleRoleName).collect(Collectors.toSet());
    }
}
