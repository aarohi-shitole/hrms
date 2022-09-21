package com.techvg.hrms.domain.enumeration;

/**
 * The Options enumeration.
 */
public enum Options {
    YES("Yes"),
    NO("No");

    private final String value;

    Options(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
