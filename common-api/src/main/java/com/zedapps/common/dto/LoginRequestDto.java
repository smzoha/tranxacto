package com.zedapps.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Shamah M Zoha
 * @since 01-May-23
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String plainPassword;

    // To be used for registration
    private String confirmPlainPassword;

    private Set<String> roles;

}
