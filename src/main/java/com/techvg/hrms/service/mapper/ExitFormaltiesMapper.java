package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.ExitFormalties;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.ExitFormaltiesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExitFormalties} and its DTO {@link ExitFormaltiesDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExitFormaltiesMapper extends EntityMapper<ExitFormaltiesDTO, ExitFormalties> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    ExitFormaltiesDTO toDto(ExitFormalties s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
