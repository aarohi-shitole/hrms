package com.techvg.hrms.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExitFormaltiesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExitFormaltiesDTO.class);
        ExitFormaltiesDTO exitFormaltiesDTO1 = new ExitFormaltiesDTO();
        exitFormaltiesDTO1.setId(1L);
        ExitFormaltiesDTO exitFormaltiesDTO2 = new ExitFormaltiesDTO();
        assertThat(exitFormaltiesDTO1).isNotEqualTo(exitFormaltiesDTO2);
        exitFormaltiesDTO2.setId(exitFormaltiesDTO1.getId());
        assertThat(exitFormaltiesDTO1).isEqualTo(exitFormaltiesDTO2);
        exitFormaltiesDTO2.setId(2L);
        assertThat(exitFormaltiesDTO1).isNotEqualTo(exitFormaltiesDTO2);
        exitFormaltiesDTO1.setId(null);
        assertThat(exitFormaltiesDTO1).isNotEqualTo(exitFormaltiesDTO2);
    }
}
