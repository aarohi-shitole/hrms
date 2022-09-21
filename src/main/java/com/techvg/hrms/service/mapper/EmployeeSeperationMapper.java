package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.EmployeeSeperation;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.EmployeeSeperationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeSeperation} and its DTO {@link EmployeeSeperationDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeSeperationMapper extends EntityMapper<EmployeeSeperationDTO, EmployeeSeperation> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    EmployeeSeperationDTO toDto(EmployeeSeperation s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
