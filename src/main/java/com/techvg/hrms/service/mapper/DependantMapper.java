package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Dependant;
import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.service.dto.DependantDTO;
import com.techvg.hrms.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dependant} and its DTO {@link DependantDTO}.
 */
@Mapper(componentModel = "spring")
public interface DependantMapper extends EntityMapper<DependantDTO, Dependant> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    DependantDTO toDto(Dependant s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
