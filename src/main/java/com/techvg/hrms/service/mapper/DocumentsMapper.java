package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Documents;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.Organization;
import com.techvg.hrms.service.dto.DocumentsDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.OrganizationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Documents} and its DTO {@link DocumentsDTO}.
 */
@Mapper(componentModel = "spring")
public interface DocumentsMapper extends EntityMapper<DocumentsDTO, Documents> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    @Mapping(target = "organization", source = "organization", qualifiedByName = "organizationId")
    DocumentsDTO toDto(Documents s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);

    @Named("organizationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrganizationDTO toDtoOrganizationId(Organization organization);
}
