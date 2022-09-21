package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeSeperationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSeperation.class);
        EmployeeSeperation employeeSeperation1 = new EmployeeSeperation();
        employeeSeperation1.setId(1L);
        EmployeeSeperation employeeSeperation2 = new EmployeeSeperation();
        employeeSeperation2.setId(employeeSeperation1.getId());
        assertThat(employeeSeperation1).isEqualTo(employeeSeperation2);
        employeeSeperation2.setId(2L);
        assertThat(employeeSeperation1).isNotEqualTo(employeeSeperation2);
        employeeSeperation1.setId(null);
        assertThat(employeeSeperation1).isNotEqualTo(employeeSeperation2);
    }
}
