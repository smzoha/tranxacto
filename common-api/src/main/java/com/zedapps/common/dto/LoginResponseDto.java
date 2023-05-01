package com.zedapps.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

/**
 * @author Shamah M Zoha
 * @since 01-May-23
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String status;

    private String email;

    private String firstName;

    private String lastName;

    private Set<String> roles;
}
