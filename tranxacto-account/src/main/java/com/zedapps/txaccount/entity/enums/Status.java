package com.zedapps.txaccount.entity.enums;

import lombok.Getter;

/**
 * @author Shamah M Zoha
 * @since 27-Jan-23
 */

@Getter
public enum Status {

    ACTIVE("Active"),
    INACTIVE("Inactive"),
    ARCHIVED("Archived");

    private final String naturalName;

    Status(String naturalName) {
        this.naturalName = naturalName;
    }
}
