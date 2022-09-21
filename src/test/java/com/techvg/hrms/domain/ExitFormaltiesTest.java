package com.techvg.hrms.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.techvg.hrms.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExitFormaltiesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExitFormalties.class);
        ExitFormalties exitFormalties1 = new ExitFormalties();
        exitFormalties1.setId(1L);
        ExitFormalties exitFormalties2 = new ExitFormalties();
        exitFormalties2.setId(exitFormalties1.getId());
        assertThat(exitFormalties1).isEqualTo(exitFormalties2);
        exitFormalties2.setId(2L);
        assertThat(exitFormalties1).isNotEqualTo(exitFormalties2);
        exitFormalties1.setId(null);
        assertThat(exitFormalties1).isNotEqualTo(exitFormalties2);
    }
}
