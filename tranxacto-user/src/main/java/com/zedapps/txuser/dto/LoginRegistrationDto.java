package com.zedapps.txuser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Shamah M Zoha
 * @since 12-May-23
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRegistrationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String password;

    // To be used for registration
    private String confirmPassword;

    private Set<String> roles;

}
