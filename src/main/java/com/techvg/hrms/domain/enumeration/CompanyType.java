package com.techvg.hrms.domain.enumeration;

/**
 * The CompanyType enumeration.
 */
public enum CompanyType {
    GOVERNMENT_SECTOR("Government_Sector"),
    PUBLIC_SECTOR("Public_Sector"),
    PRIVATE_SECTOR("Private_Sector"),
    LOCAL_INDUSTRY("Local_Industry"),
    PROP_INDUSTRY("Prop_Industry");

    private final String value;

    CompanyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
