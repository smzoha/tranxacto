package com.zedapps.txaccount.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Shamah M Zoha
 * @since 27-Jan-23
 */

@Entity
@Table(name = "account_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountType implements Serializable {

    private static final long serialVersionUid = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account_type")
    @SequenceGenerator(name = "seq_account_type", sequenceName = "seq_account_type", allocationSize = 1)
    private long id;

    @NotBlank
    @Size(max = 64)
    @Column(length = 64, nullable = false)
    private String code;

    @NotBlank
    @Size(max = 512)
    @Column(length = 512, nullable = false)
    private String description;
}
