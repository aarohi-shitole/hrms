package com.techvg.hrms.domain.enumeration;

/**
 * The SeperationStatus enumeration.
 */
public enum SeperationStatus {
    PENDING("Pending"),
    REVOKED("Revoked"),
    ACCEPTED("Accepted"),
    APPROVED("Approved"),
    REJECTED("Rejected");

    private final String value;

    SeperationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
