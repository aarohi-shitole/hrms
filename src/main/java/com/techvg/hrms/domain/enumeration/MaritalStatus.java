package com.techvg.hrms.domain.enumeration;

/**
 * The MaritalStatus enumeration.
 */
public enum MaritalStatus {
    MARRIED("Married"),
    SINGLE("Single"),
    DIVORCED("Divorced"),
    WIDOW("Widow"),
    OTHERS("Others");

    private final String value;

    MaritalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
