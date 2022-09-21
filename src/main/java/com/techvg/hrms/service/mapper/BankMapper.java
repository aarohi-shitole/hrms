package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Bank;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.Organization;
import com.techvg.hrms.service.dto.BankDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.OrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bank} and its DTO {@link BankDTO}.
 */
@Mapper(componentModel = "spring")
public interface BankMapper extends EntityMapper<BankDTO, Bank> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    BankDTO toDto(Bank s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);
}
