package com.techvg.hrms.domain.enumeration;

/**
 * The Title enumeration.
 */
public enum Title {
    MR("Mr"),
    MRS("Mrs"),
    MISS("Miss");

    private final String value;

    Title(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
