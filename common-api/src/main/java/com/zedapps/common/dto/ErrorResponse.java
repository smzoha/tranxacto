package com.zedapps.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Shamah M Zoha
 * @since 20-Feb-23
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date timestamp;

    private int status;

    private String error;

    private List<ErrorDto> payload;

}
