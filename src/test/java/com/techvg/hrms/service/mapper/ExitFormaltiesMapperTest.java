package com.techvg.hrms.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExitFormaltiesMapperTest {

    private ExitFormaltiesMapper exitFormaltiesMapper;

    @BeforeEach
    public void setUp() {
        exitFormaltiesMapper = new ExitFormaltiesMapperImpl();
    }
}
