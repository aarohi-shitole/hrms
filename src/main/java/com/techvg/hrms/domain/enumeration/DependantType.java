package com.techvg.hrms.domain.enumeration;

/**
 * The DependantType enumeration.
 */
public enum DependantType {
    MOTHER("Mother"),
    CHILD("Child"),
    SISTER("Sister"),
    Daughter("Divorced"),
    SPOUSE("Spouse");

    private final String value;

    DependantType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
