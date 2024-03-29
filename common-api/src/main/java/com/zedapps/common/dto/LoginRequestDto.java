package com.zedapps.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

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

    private String password;

}
