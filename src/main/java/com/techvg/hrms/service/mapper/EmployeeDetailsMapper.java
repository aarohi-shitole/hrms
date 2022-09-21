package com.techvg.hrms.service.mapper;

import com.techvg.hrms.domain.EmployeeDetails;
import com.techvg.hrms.service.dto.EmployeeDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeDetails} and its DTO {@link EmployeeDetailsDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeDetailsMapper extends EntityMapper<EmployeeDetailsDTO, EmployeeDetails> {}
