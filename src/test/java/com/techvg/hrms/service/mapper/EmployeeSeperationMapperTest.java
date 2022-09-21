package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeSeperationMapperTest {

    private EmployeeSeperationMapper employeeSeperationMapper;

    @BeforeEach
    public void setUp() {
        employeeSeperationMapper = new EmployeeSeperationMapperImpl();
    }
}
