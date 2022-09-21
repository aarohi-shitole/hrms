package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.Employee;
import com.techvg.hrms.domain.WorkExperience;
import com.techvg.hrms.service.dto.EmployeeDTO;
import com.techvg.hrms.service.dto.WorkExperienceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkExperience} and its DTO {@link WorkExperienceDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkExperienceMapper extends EntityMapper<WorkExperienceDTO, WorkExperience> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "employeeId")
    WorkExperienceDTO toDto(WorkExperience s);

    @Named("employeeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    EmployeeDTO toDtoEmployeeId(Employee employee);
}
