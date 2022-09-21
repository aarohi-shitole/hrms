package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeSeperationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSeperationDTO.class);
        EmployeeSeperationDTO employeeSeperationDTO1 = new EmployeeSeperationDTO();
        employeeSeperationDTO1.setId(1L);
        EmployeeSeperationDTO employeeSeperationDTO2 = new EmployeeSeperationDTO();
        assertThat(employeeSeperationDTO1).isNotEqualTo(employeeSeperationDTO2);
        employeeSeperationDTO2.setId(employeeSeperationDTO1.getId());
        assertThat(employeeSeperationDTO1).isEqualTo(employeeSeperationDTO2);
        employeeSeperationDTO2.setId(2L);
        assertThat(employeeSeperationDTO1).isNotEqualTo(employeeSeperationDTO2);
        employeeSeperationDTO1.setId(null);
        assertThat(employeeSeperationDTO1).isNotEqualTo(employeeSeperationDTO2);
    }
}
