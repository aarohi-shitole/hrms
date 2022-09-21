package com.techvg.hrms.domain.enumeration;

/**
 * The OrganizationType enumeration.
 */
public enum OrganizationType {
    SOCIETY("Society"),
    CO_SOCIETY("Co_Society"),
    BANK("Bank"),
    URBAN_BANK("Urban_bank"),
    RURAL_BANK("Rural_bank");

    private final String value;

    OrganizationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
