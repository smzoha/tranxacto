package com.zedapps.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Shamah M Zoha
 * @since 10-Mar-23
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SupportingDocumentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String type;

    private String size;

    private Date uploadDate;

}
