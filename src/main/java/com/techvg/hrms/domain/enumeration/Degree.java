package com.techvg.hrms.domain.enumeration;

/**
 * The Degree enumeration.
 */
public enum Degree {
    PG("Post_Graduation"),
    GRADUATION("Graduation"),
    HSC,
    SSC,
    DIPLOMA("Diploma"),
    OTHER("Other");

    private final String value;

    Degree(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
