package com.zedapps.txuser.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Shamah M Zoha
 * @since 19-Apr-23
 */

@Getter
@AllArgsConstructor
public enum Role {

    SUPER_ADMIN("Super Admin"),
    ADMIN("Admin"),
    ACCOUNT("Account Access"),
    TRANSACTION("Transaction Access");

    private final String naturalName;
}
