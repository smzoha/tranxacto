package com.zedapps.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Shamah M Zoha
 * @since 20-Feb-23
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String level;

    private String field;

    private String message;

}
