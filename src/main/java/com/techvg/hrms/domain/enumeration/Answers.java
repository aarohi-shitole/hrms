package com.techvg.hrms.domain.enumeration;

/**
 * The Answers enumeration.
 */
public enum Answers {
    YES("Yes"),
    NO("No");

    private final String value;

    Answers(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
