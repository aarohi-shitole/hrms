package com.techvg.hrms.domain.enumeration;

/**
 * The BloodGroup enumeration.
 */
public enum BloodGroup {
    A_POSITIVE("A+"),
    B_POSITIVE("B+"),
    O_POSITIVE("O+"),
    O_NEGATIVE("O-"),
    A_NEGATIVE("A-"),
    B_NEGATIVE("B-"),
    AB_POSITIVE("AB+"),
    AB_NEGATIVE("AB-");

    private final String value;

    BloodGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
