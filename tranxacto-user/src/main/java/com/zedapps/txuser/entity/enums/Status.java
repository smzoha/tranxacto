package com.zedapps.txuser.entity.enums;

/**
 * @author Shamah M Zoha
 * @since 01-May-23
 */
public enum Status {

    ACTIVE("Active"),
    INACTIVE("Inactive"),
    BLOCKED("Blocked"),
    DELETED("Deleted");

    private final String naturalName;

    Status(String naturalName) {
        this.naturalName = naturalName;
    }

    public String getNaturalName() {
        return naturalName;
    }
}
